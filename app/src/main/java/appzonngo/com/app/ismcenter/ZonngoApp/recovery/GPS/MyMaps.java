package appzonngo.com.app.ismcenter.ZonngoApp.recovery.GPS;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import appzonngo.com.app.ismcenter.ZonngoApp.DataModel.MH_DataModel_ListarProdByLatLog;
import appzonngo.com.app.ismcenter.ZonngoApp.recovery.MH_Principal;
import appzonngo.com.app.ismcenter.zonngo2.R;

/**
 * Created by Marwuin on 24/3/2017.
 */

public class MyMaps {
    MH_Principal vista;
    ///libreria nueva
    ArrayList<LatLng> MarkerPoints;
    private GoogleMap mMap;
    private Marker markerUserPosition;
    private Marker markerPharmacySelected;

    //public static Double latPrueba=-12.065942;
    //public static Double lngPrueba= -77.062849;
    private DataParser dataParser;

    public MyMaps(MH_Principal vista) {
        this.vista = vista;
        MarkerPoints = new ArrayList<>();
        //makeUserPosition(vista.getLatLng());
        //moveCamera(vista.getLatLng());
    }

    public GoogleMap getmMap() {
        return mMap;
    }

    public void setmMap(final GoogleMap mMap) {
        this.mMap = mMap;


        //mMap.setMyLocationEnabled(true);
        // Setting onclick event listener for the map
        /*mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {



                // Checks, whether start and end locations are captured
                if (MarkerPoints.size() >= 2) {
                    LatLng origin = MarkerPoints.get(0);
                    LatLng dest = MarkerPoints.get(1);

                    // Getting URL to the Google Directions API
                    String url = getUrl(origin, dest);
                    Log.d("onMapClick", url.toString());
                    FetchUrl FetchUrl = new FetchUrl();

                    // Start downloading json data from Google Directions API
                    FetchUrl.execute(url);
                    //move map camera
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                }

            }
        });*/
    }

    public void moveCamera(LatLng latLng) {
        CameraUpdate camUpd1 =
                CameraUpdateFactory
                        .newLatLngZoom(latLng, 13);
        mMap.moveCamera(camUpd1);
    }

    public void makeUserPosition(LatLng latLng) {
        if (markerUserPosition != null) {
            markerUserPosition.remove();
        }
        markerUserPosition = mMap.addMarker(new MarkerOptions().position(latLng)
                .title("Mi ubicación")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_user)));
    }

    public void makePharmaciesPosition(List<MH_DataModel_ListarProdByLatLog> listProdFramByLatLng) {
        for (int i = 0; i < listProdFramByLatLng.size(); i++) {
            LatLng latLng = new LatLng(listProdFramByLatLng.get(i).getLat(), listProdFramByLatLng.get(i).getLng());

            Log.e("Name Farmacy", ":" + listProdFramByLatLng.get(i).getDetalleFarmacias().getNombre());
            //if()
            mMap.addMarker(new MarkerOptions().position(latLng)
                            .title(listProdFramByLatLng.get(i).getDetalleFarmacias().getNombre())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_pharmacy))
                    //.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_favorite_border_black_24dp))
            );
        }
    }

    public void makSelectedPharmacyPosition(MH_DataModel_ListarProdByLatLog ProdFramByLatLng) {
        LatLng latLng = new LatLng(ProdFramByLatLng.getLat(), ProdFramByLatLng.getLng());
        if (markerPharmacySelected != null) {
            markerPharmacySelected.remove();
        }
        markerPharmacySelected = mMap.addMarker(new MarkerOptions().position(latLng)
                .title(ProdFramByLatLng.getDetalleFarmacias().getNombre())
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_farmacia_ic)));
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        );


        //para mostrar el titulo mediante programcion
        int zoom = (int) mMap.getCameraPosition().zoom;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom), 4000, null);
        markerPharmacySelected.showInfoWindow();
    }

    public void animateCameraPharmacyPosition(MH_DataModel_ListarProdByLatLog ProdFramByLatLng) {
        LatLng latLng = new LatLng(ProdFramByLatLng.getLat(), ProdFramByLatLng.getLng());
        animateCamera(latLng);
        //CameraUpdate ubication = CameraUpdateFactory.newLatLngZoom(latLng, 10);
        //mMap.animateCamera(ubication);
    }

    public void createRouteToPharmacy(LatLng user, MH_DataModel_ListarProdByLatLog ProdFramByLatLng) {
        LatLng latLng = new LatLng(ProdFramByLatLng.getLat(), ProdFramByLatLng.getLng());
        // Getting URL to the Google Directions API
        String url = getUrl(user, latLng);
        FetchUrl FetchUrl = new FetchUrl();
        // Start downloading json data from Google Directions API
        FetchUrl.execute(url);
        //move map camera

        makSelectedPharmacyPosition(ProdFramByLatLng);

        animateCamera(user);
    }

    public void animateCameraWithOutZoom(LatLng latLng) {
        CameraUpdate ubication = CameraUpdateFactory.newLatLng(latLng);
        mMap.animateCamera(ubication);
    }

    public void animateCamera(LatLng latLng) {
        CameraUpdate ubication = CameraUpdateFactory.newLatLngZoom(latLng, 13);
        mMap.animateCamera(ubication);
    }

    public Marker getMarkerUserPosition() {
        return markerUserPosition;
    }

    public void setMarkerUserPosition(Marker markerUserPosition) {
        this.markerUserPosition = markerUserPosition;
    }

    //SE TRABAJA EN CONSULLTAR LAS COORDENADAS ENTRE DOS PUNTOS

    private String getUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class FetchUrl extends AsyncTask<String, Void, String> {
        public FetchUrl() {
            Log.d("Background Task data", "kkkkkkkkkkkkkkkkkkkkkk");
        }

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                Log.d("Background Task data", "1111111111111111");
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask", jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask", "Executing routes");
                Log.d("ParserTask", routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask", e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(6);
                lineOptions.color(Color.RED);

                Log.d("onPostExecute", "onPostExecute lineoptions decoded");

            }

            // Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null) {
                mMap.addPolyline(lineOptions);
            } else {
                Log.d("onPostExecute", "without Polylines drawn");
            }
        }
    }

}
