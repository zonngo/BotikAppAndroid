package appzonngo.com.app.ismcenter.ZonngoApp.recovery.GPS;

/*
 * Created by Marwuin on 6/12/2016.
 */

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import appzonngo.com.app.ismcenter.zonngo2.R;

public class GPSCoordenates extends Service implements
        LocationListener {
    private static String TAG = GPSCoordenates.class.getName(); //get Name class
    private Context mContext;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean isGPSTrackingEnabled = false;
    Location location;
    double latitude;
    double longitude;
    int geocoderMaxResults = 1;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60; // 1 minute
    protected LocationManager locationManager;
    private String provider_info="";

    public GPSCoordenates(Context context) {
        this.mContext = context;
    }

    public Double[] getCoordinates(){
        Double[] coordinates=null;
        if(getLocationManager()){
            if(startCoordinatesService()){
                if(searchCoordinates()){
                    coordinates = new Double[2];
                    coordinates[0]=latitude;
                    coordinates[1]=longitude;
                    //stopCoordinatesService();//QUE NO SIGA ACTUALIZANDO (OJOOOOOOOO)
                }
            }
        } else {
            myDialoges.showSettingsActivateGPS(mContext);
        }

        return coordinates;
    }

    public boolean getLocationManager(){
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (isGPSEnabled) {
                this.isGPSTrackingEnabled = true;
                myDebug.showLog_d(TAG+"->","Application use GPS Service");
                provider_info = LocationManager.GPS_PROVIDER;
            } else if (isNetworkEnabled) {
                this.isGPSTrackingEnabled = true;
                myDebug.showLog_d(TAG+"->","Application use Network State to get GPS coordinates");
                provider_info = LocationManager.NETWORK_PROVIDER;
            }
        } catch (Exception e) {
            myDebug.showLog_e(TAG+"->","Impossible to connect to LocationManager",e);
        }
        return this.isGPSTrackingEnabled;
    }

    public boolean startCoordinatesService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(mContext, R.string.gps_off_permission, Toast.LENGTH_LONG).show();
                myIntents.ACTION_APPLICATION_DETAILS_SETTINGS(mContext);
                return false;
            } else {
                locationManager.requestLocationUpdates(provider_info, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                return true;
            }
        } else {
            locationManager.requestLocationUpdates(provider_info, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            return true;
        }
    }

    public boolean searchCoordinates() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(mContext,R.string.gps_off_permission, Toast.LENGTH_LONG).show();
                myIntents.ACTION_APPLICATION_DETAILS_SETTINGS(mContext);
                return false;
            } else {
                location = locationManager.getLastKnownLocation(provider_info);
            }
        } else {
            location = locationManager.getLastKnownLocation(provider_info);
        }

        int exit=0;
        while((getLatitude()==0.0)||(getLongitude()==0.0)) {
            exit += 1;
            try {
                Thread.sleep(5000);//5 segungos detectando ubicacion
                if (exit == 5){
                    Toast.makeText(mContext,R.string.gps_fail, Toast.LENGTH_LONG).show();
                    return false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    /**
     * SI-SI no ha permiso de GPS FINE o COARSE,
     * se lleva al usuario a la interfaz de permisos de aplicaciones
     * SI-NO, se remueve la aptualizaciones
     */
    public void stopCoordinatesService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(mContext, R.string.gps_off_permission, Toast.LENGTH_SHORT).show();
                myIntents.ACTION_APPLICATION_DETAILS_SETTINGS(mContext);
            } else {
                locationManager.removeUpdates(this);
            }
        } else {
            locationManager.removeUpdates(this);
        }
    }

    public void updateGPSCoordinates() {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
    }

    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }
        return longitude;
    }

    public boolean getIsGPSTrackingEnabled() {
        return this.isGPSTrackingEnabled;
    }

    /**
     * Cambio en la ubicacion
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        this.location=location;
        myDebug.showLog_d(TAG+"->","onLocationChanged: "+location.getLatitude()+" _ "+location.getLongitude());
    }

    /**
     * Cambio status del proveedor (OUT_OF_SERVICE, TEMPORARILY_UNAVAILABLE, AVAILABLE)
     * @param provider
     * @param status
     * @param extras
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        myDebug.showLog_d(TAG+"->","onStatusChanged: ");
    }

    /**
     * GPS es habilitado
     * @param provider
     */
    @Override
    public void onProviderEnabled(String provider) {
        myDebug.showLog_d(TAG+"->","onProviderEnabled: ");
    }

    /**
     * GPS es desactivado
     * @param provider
     */
    @Override
    public void onProviderDisabled(String provider) {

    }

    /**
     * Ejecutar tareas en segundo plano (Class Service)
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
