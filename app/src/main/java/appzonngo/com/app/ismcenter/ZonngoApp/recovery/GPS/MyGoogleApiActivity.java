package appzonngo.com.app.ismcenter.ZonngoApp.recovery.GPS;

/*
 * Created by Marwuin on 15/12/2016.
 */

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import com.google.android.gms.maps.model.LatLng;

import appzonngo.com.app.ismcenter.zonngo2.R;
import appzonngo.com.app.ismcenter.ZonngoApp.recovery.Utilities.Constants;

public class MyGoogleApiActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        LocationListener,
        GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<Status> {

    //private LatLng latLng;

    private AppCompatActivity myContext=MyGoogleApiActivity.this;
    private static String TAG = GPSCoordenates.class.getName(); //get Name class
    // Códigos de petición
    private static final int REQUEST_LOCATION = 1;
    private static final int REQUEST_CHECK_SETTINGS = 2;
    private static final int RC_SIGN_IN = 9001;
    private static final String LOCATION_KEY = "location-key";
    private static final long UPDATE_INTERVAL = 3000;
    private static final long UPDATE_FASTEST_INTERVAL = UPDATE_INTERVAL / 2;

    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private double mLatitude;
    private double mLongitude;

    private boolean cordinatesReady =false;
    private static GoogleApiClient apiClient;

    private MyPermissions mPermissions;

    private static final int REQUEST_PERMISSIONS_ACCESS_FINE_LOCATION= 10;


    private String[] MANIFEST_PERMISSIONS_GPS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private String[] MSG_DENIED_PERMISSIONS_GPS = {
            "Esta aplicación requiere permisos para Ubicación."
    };

    private int[] REQUEST_PERMISSIONS_GPS = {
            REQUEST_PERMISSIONS_ACCESS_FINE_LOCATION
    };

    public void createAPI_Google(Bundle savedInstanceState, View viewSnackBar){
        mPermissions=new MyPermissions(this, viewSnackBar, MANIFEST_PERMISSIONS_GPS, REQUEST_PERMISSIONS_GPS, MSG_DENIED_PERMISSIONS_GPS);

        //if(mPermissions.obtainPermissions()) {//si se activan los permisos GPS
            myDebug.showLog_d("MyGoogleApiActivity-> createAPI_Google: ", " ON ");
            // Establecer punto de entrada para la API de ubicación
            buildGoogleApiClient();
            // Crear configuración de peticiones
            createLocationRequest();
            // Crear opciones de peticiones
            buildLocationSettingsRequest();
            // Verificar ajustes de ubicación actuales
            checkLocationSettings();
            // recupera la ultima ubicacion conocida antes de minimizar la actividad
            updateValuesFromBundle(savedInstanceState);
        //}
    }

    /**
     * configura las conexiones a la API google
     */
    public synchronized void buildGoogleApiClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        apiClient = new GoogleApiClient.Builder(myContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)//login con google
                .addApi(LocationServices.API)
                .enableAutoManage(myContext, this)
                .build();
    }

    /**
     * configurando (Intervalo de actualización, Intervalo de actualización rápida, Prioridad)
     */
    public void createLocationRequest() {
        mLocationRequest = new LocationRequest()
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(UPDATE_FASTEST_INTERVAL)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     *  especifica los servicios de ubicación que la app usará
     */
    public void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest).setAlwaysShow(true);
        mLocationSettingsRequest = builder.build();
    }

    /**
     * Pedir al usuario cambiar los ajustes de ubicación
     */
    public void checkLocationSettings() {
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        apiClient, mLocationSettingsRequest
                );

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                Status status = result.getStatus();

                switch (status.getStatusCode()) {
                    //El primero significa que tod ha salido bien. En consecuencia allí realizas la petición de ubicación.
                    case LocationSettingsStatusCodes.SUCCESS:
                        myDebug.showLog_d("myGoogleApi-> checkLocationSettings", "Los ajustes de ubicación satisfacen la configuración.");
                        startLocationUpdates();
                        break;
                    //lanzamos un diálogo de ayuda. Donde pasarás como parámetro la actividad y un código de petición
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            myDebug.showLog_d("myGoogleApi-> checkLocationSettings", "Los ajustes de ubicación no satisfacen la configuración." +
                                    "Se mostrará un diálogo de ayuda.");
                            status.startResolutionForResult(myContext, REQUEST_CHECK_SETTINGS);
                            //la respuesta se procesa en el metodo onActivityResult de la actividad
                        } catch (IntentSender.SendIntentException e) {
                            myDebug.showLog_d("myGoogleApi-> checkLocationSettings", "El Intent del diálogo no funcionó.");
                            // Sin operaciones
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        myDebug.showLog_d("myGoogleApi-> checkLocationSettings", "Los ajustes de ubicación no son apropiados.");
                        break;
                }
            }
        });
    }

    /**
     * Se obtiene la info guardada al minimizar la app, se accede
     * con la llave LOCATION_KEY (mLastLocation)
     * @param savedInstanceState
     */
    public void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(LOCATION_KEY)) {
                mLastLocation = savedInstanceState.getParcelable(LOCATION_KEY);

                updateLocationUI();
            }
        }
    }

    /**
     * Determina si el cliente esta conectado a la API de google
     * @param bundle
     */
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.e("GoogleSignIn", "onConnected: ");
        // Obtenemos la última ubicación al ser la primera vez
        processLastLocation();
        // Iniciamos las actualizaciones de ubicación
        startLocationUpdates();
    }

    /**
     * Determina si el cliente esta desconectado a la API de google
     * @param i
     */
    @Override
    public void onConnectionSuspended(int i) {
        Log.e("GoogleSignIn", "onConnectionSuspended: ");
        apiClient.connect();
    }

    /**
     * procesa posible errores de conexion
     * @param connectionResult
     */
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(myContext, "Error de conexion!", Toast.LENGTH_SHORT).show();
        myDebug.showLog_d("myGoogleApi-> GoogleSignIn","\"OnConnectionFailed: \"" + connectionResult);
    }

    /**
     * Obtener ultima ubicacion conocida
     */
    private void processLastLocation() {
        getLastLocation();
        if (mLastLocation != null) {
            updateLocationUI();
        }
    }

    /**
     * Si tiene permisos, obtiene la ultima ubicacion conocida
     */
    private void getLastLocation() {
        if(mPermissions.obtainPermissions()){
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(apiClient);
        }
    }

    /**
     * activa la actualizacion de coordenadas segun la configuracion "mLocationRequest"
     */
    private void startLocationUpdates() {
        if(mPermissions.obtainPermissions()){
            LocationServices.FusedLocationApi.requestLocationUpdates(apiClient, mLocationRequest, this);
        }
    }

    /**
     * refrezca la ultima ubicacion conocida
     */
    private void updateLocationUI() {
        mLatitude=mLastLocation.getLatitude();
        mLongitude=mLastLocation.getLongitude();
        //actualiza en otra clase oyente
        publishBroadcastReceiverGPS();
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public Double[] getmCoordinates() {
        Double[] coordinates=new Double[2];
        coordinates[0]=mLatitude;
        coordinates[1]=mLongitude;
        return coordinates;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    @Override
    public void onLocationChanged(Location location) {
        myDebug.showLog_d("myGoogleApi-> onLocationChanged",
                String.format("Nueva ubicación: (%s, %s)", location.getLatitude(), location.getLongitude()));
        mLastLocation = location;
        updateLocationUI();
        cordinatesReady = true;
    }


    public boolean isCordinatesReady(Context mContext) {
        if(!cordinatesReady) {
            if(mPermissions.obtainPermissions())//si ya los permisos estan activos, es un eror de actualizacion
                Toast.makeText(mContext, R.string.gps_fail, Toast.LENGTH_LONG).show();

        }
        return cordinatesReady;
    }



    public void setCordinatesReady(boolean cordinatesReady) {
        this.cordinatesReady = cordinatesReady;
    }

    @Override
    public void onResult(@NonNull Status status) {
        if (status.isSuccess()) {
            myDebug.showLog_d("myGoogleApi-> onResult", "Detección de actividad iniciada");
        } else {
            myDebug.showLog_d("myGoogleApi-> onResult", "Error al iniciar/remover la detección de actividad: "
                    + status.getStatusMessage());
        }
    }

    /**
     * detiene actualizaciones GPS
     */
    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(apiClient, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (apiClient != null)
            apiClient.connect();
    }

    @Override
    protected void onStop() {
        if (apiClient != null && apiClient.isConnected()) {
            apiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        myDebug.showLog_d("myGoogleApi-> onActivityResult", "El usuario permitió el cambio de ajustes de ubicación.");
                        processLastLocation();
                        startLocationUpdates();
                        break;
                    case Activity.RESULT_CANCELED:
                        myDebug.showLog_d("myGoogleApi-> onActivityResult", "El usuario no permitió el cambio de ajustes de ubicación");
                        break;
                }
                break;
            /*case RC_SIGN_IN:
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

                if (result.isSuccess()) {
                    //Usuario2 logueado --> Mostramos sus datos
                    GoogleSignInAccount profile = result.getSignInAccount();

                    // get profile information
                    String name = "";
                    String email = "";
                    String uriPicture = "";
                    if (profile.getDisplayName() != null) {
                        name = profile.getDisplayName();
                    }
                    if (profile.getEmail() != null) {
                        email = profile.getEmail();
                    }
                    if (profile.getPhotoUrl() != null) {
                        uriPicture = profile.getPhotoUrl().toString();
                    }

                    TelephonyManager tMgr = (TelephonyManager) myContext.getSystemService(Context.TELEPHONY_SERVICE);
                    String mPhoneNumber = tMgr.getLine1Number();
                    String getSimSerialNumber = tMgr.getSimSerialNumber();
                    String deviceID = tMgr.getDeviceId();

                    Log.e("GoogleSignInAccount",
                            "\nacct.getDisplayName(): " + profile.getDisplayName()+
                                    "\nacct.getEmail(): " + profile.getEmail()+
                                    "\nacct.getFamilyName(): " + profile.getFamilyName()+
                                    "\nacct.getGivenName(): " + profile.getGivenName()+
                                    "\nacct.getId(): " + profile.getId()+
                                    "\nacct.getIdToken(): " + profile.getIdToken()+
                                    "\nacct.getServerAuthCode(): " + profile.getServerAuthCode() +
                                    "\nacct.mPhoneNumber(): " + mPhoneNumber +//bloquean el numero de telefono
                                    "\nacct.getSimSerialNumber(): " + getSimSerialNumber+
                                    "\nacct.deviceID(): " + deviceID
                    );

                    // save profile information to preferences
                    SharedPreferences prefs = myContext.getSharedPreferences("com.example.ld.abacorealtime", Context.MODE_PRIVATE);
                    prefs.edit().putString("com.example.ld.abacorealtime.nombre", name).apply();
                    prefs.edit().putString("com.example.ld.abacorealtime.email", email).apply();
                    prefs.edit().putString("com.example.ld.abacorealtime.uriPicture", uriPicture).apply();
                    // redirect to map screen
                    //startActivity(new Intent(LoginActivityMH.this, MenuActivityMH.class));
                } else {
                    //Usuario2 no logueado --> Lo mostramos como "Desconectado"
                    Log.e("Google","rechazo");
                }

                break;*/
        }
    }
/*
    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
*/
    public LatLng getLatLng() {
        return new LatLng(mLatitude,mLongitude);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(mPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)){
            //si acepto un permiso especifico, se verifica si a todos estan ready
            startLocationUpdates();//all permission ready?
        }
    }

    /**
     * desregistra al cliente y detiene actualizaciones
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (apiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    /**
     * registra al cliente e inicia actualizaciones
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (apiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Protegemos la ubicación actual antes del cambio de configuración
        outState.putParcelable(LOCATION_KEY, mLastLocation);
        //Esta info se busca en OnCreate
        super.onSaveInstanceState(outState);
    }

    public boolean getLocationManager(){
        boolean isGPSTrackingEnabled=false;
        try {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean  isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (isGPSEnabled) {
                isGPSTrackingEnabled = true;
                myDebug.showLog_d(TAG+"->","Application use GPS Service");
            } else if (isNetworkEnabled) {
                isGPSTrackingEnabled = true;
                myDebug.showLog_d(TAG+"->","Application use Network State to get GPS coordinates");
            }
        } catch (Exception e) {
            myDebug.showLog_e(TAG+"->","Impossible to connect to LocationManager",e);
        }
        if (!isGPSTrackingEnabled)
            myDebug.showLog_d(TAG+"->","GPS OF");
        return isGPSTrackingEnabled;
    }

    public void publishBroadcastReceiverGPS() {
        //LOS QUE SE SUSCRIBAN A BROADCAST_RECEIVER_TOKEN, RECIBIRAN LA NOTIFICACION
        final Intent intent = new Intent(Constants.BROADCAST_RECEIVER_GPS);
        intent.putExtra(Constants.UPDATE_LOCATION, true);

        final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.sendBroadcast(intent);
    }
}
