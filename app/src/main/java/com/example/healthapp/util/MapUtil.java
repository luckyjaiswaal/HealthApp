package com.example.healthapp.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MapUtil {

    public static Marker addMarker(GoogleMap googleMap, LatLng latLng, String title){
        return googleMap.addMarker(new MarkerOptions().position(latLng).title(title));
    }

    public static Marker addMarker(GoogleMap googleMap, LatLng latLng, String title, BitmapDrawable drawable){
        int height = 60;
        int width = 60;
        Bitmap b = drawable.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        return googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title)
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
    }

    public static void animateCamera(GoogleMap googleMap, LatLng latLng, int zoomLvl){
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLvl));
    }

    public static void viewDirections(Context context, double lat, double lng){
        Uri navigationIntentUri = Uri.parse("google.navigation:q=" + lat + "," + lng);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, navigationIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        context.startActivity(mapIntent);
    }

    public static void zoomMapToShowAllMarkers(GoogleMap mMap, ArrayList<Marker> markers){
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        int padding = 50; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mMap.animateCamera(cu);
    }

    public interface NearbyPlacesResult{
        void addedMarkers(ArrayList<Marker> markers);
    }

    // type = restaurant, mosques etc (https://developers.google.com/places/web-service/supported_types)
    // details >> https://developers.google.com/places/web-service/search
    public static void getNearbyPlaces(GoogleMap mMap, Marker userMarker, int radiusMeters, int drawable,
                                       String type, String apiKey, NearbyPlacesResult nearbyPlacesResult){
        String placesSearchStr = "https://maps.googleapis.com/maps/api/place/nearbysearch/" +
                "json?location=" + userMarker.getPosition().latitude + "," + userMarker.getPosition().longitude +
                "&radius="+radiusMeters+"&sensor=true" +
                "&types="+type+
                "&key="+apiKey;
        Log.e("tag", "url:"+placesSearchStr);
        new GetPlaces(mMap, userMarker, drawable, nearbyPlacesResult).execute(placesSearchStr);
    }

    static class GetPlaces extends AsyncTask<String, Void, String> {
        String searchUrl;
        ArrayList<Marker> markers = new ArrayList<>();
        boolean updateFinished = true;
        String nextToken;
        GoogleMap mMap;
        Marker userMarker;
        NearbyPlacesResult nearbyPlacesResult;
        int drawable;

        public GetPlaces(GoogleMap mMap, Marker userMarker, int drawable, NearbyPlacesResult nearbyPlacesResult){
            this.mMap = mMap;
            this.userMarker = userMarker;
            this.nearbyPlacesResult = nearbyPlacesResult;
            this.drawable = drawable;
        }

        @Override
        protected String doInBackground(String... placesURL) {
            //fetch markerOptions
            updateFinished = false;
            StringBuilder placesBuilder = new StringBuilder();
            for (String placeSearchURL : placesURL) {
                try {
                    searchUrl = placeSearchURL;
                    URL requestUrl = new URL(placeSearchURL);
                    HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = null;
                        InputStream inputStream = connection.getInputStream();
                        if (inputStream == null) {
                            return "";
                        }
                        reader = new BufferedReader(new InputStreamReader(inputStream));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            placesBuilder.append(line + "\n");
                        }
                        if (placesBuilder.length() == 0) {
                            return "";
                        }
                        Log.d("test", placesBuilder.toString());
                    } else {
                        Log.i("test", "Unsuccessful HTTP Response Code: " + responseCode);
                        return "";
                    }
                } catch (MalformedURLException e) {
                    Log.e("test", "Error processing Places API URL", e);
                    return "";
                } catch (IOException e) {
                    Log.e("test", "Error connecting to Places API", e);
                    return "";
                }
            }
            return placesBuilder.toString();
        }

        //process data retrieved from doInBackground
        protected void onPostExecute(String result) {
            //parse place data returned from Google Places
            try {
                //parse JSON
                //create JSONObject, pass stinrg returned from doInBackground
                JSONObject resultObject = new JSONObject(result);
                //get "results" array
                JSONArray placesArray = resultObject.getJSONArray("results");
                if(resultObject.has("next_page_token")) {
                    nextToken = resultObject.getString("next_page_token");
                }
                else {
                    nextToken = null;
                }
                Log.e("tag", "nextToken: "+nextToken);
                //marker options for each place returned
                //loop through markerOptions
                if(placesArray.length() == 0){
                    updateFinished = true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new GetPlaces(mMap, userMarker, drawable, nearbyPlacesResult).execute(searchUrl);
                            Log.e("tag", "1: next page...");
                            nextToken = null;
                        }
                    }, 1000);
                    return;
                }
                for (int p = 0; p < placesArray.length(); p++) {
                    //parse each place
                    //if any values are missing we won't show the marker
                    boolean missingValue = false;
                    LatLng placeLL = null;
                    String placeName = "";
                    String vicinity = "";
                    try {
                        //attempt to retrieve place data values
                        missingValue = false;
                        //get place at this index
                        JSONObject placeObject = placesArray.getJSONObject(p);
                        //get location section
                        JSONObject loc = placeObject.getJSONObject("geometry")
                                .getJSONObject("location");
                        //read lat lng
                        placeLL = new LatLng(Double.valueOf(loc.getString("lat")),
                                Double.valueOf(loc.getString("lng")));
                        //get types
                        JSONArray types = placeObject.getJSONArray("types");
                        //loop through types
                        for (int t = 0; t < types.length(); t++) {
                            //what type is it
                            String thisType = types.get(t).toString();
                            //check for particular types - set icons
                            if (thisType.contains("mosque")) {
                                //			currIcon = masjidIcon;
                                break;
                            } else if (thisType.contains("health")) {
                                //	currIcon = drinkIcon;
                                break;
                            } else if (thisType.contains("doctor")) {
                                //	currIcon = shopIcon;
                                break;
                            }
                        }
                        //vicinity
                        vicinity = placeObject.getString("vicinity");
                        //name
                        placeName = placeObject.getString("name");
                    } catch (JSONException jse) {
                        Log.e("PLACES", "missing value");
                        missingValue = true;
                        jse.printStackTrace();
                    }
                    //if values missing we don't display
                    if (missingValue) {
                        //
                    }
                    else{
                        MarkerOptions markerOptions = new MarkerOptions()
                                .position(placeLL)
                                .title(placeName)
                                .snippet(vicinity)
                                .icon(BitmapDescriptorFactory.fromResource(drawable));
                        markers.add(mMap.addMarker(markerOptions));
                    }
                }
            } catch (Exception e) {
                Log.e("tag", "error");
                e.printStackTrace();
                //Toast.makeText(MosqueHome.this, "Could not fetch data from server", Toast.LENGTH_LONG).show();
            }
            updateFinished = true;
            if(nextToken != null){
                searchUrl = searchUrl.split("&pagetoken=")[0];
                searchUrl = searchUrl+"&pagetoken="+nextToken;
                Log.e("tag", "url:"+searchUrl);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new GetPlaces(mMap, userMarker, drawable, nearbyPlacesResult).execute(searchUrl);
                        Log.e("tag", "next page...");
                        nextToken = null;
                    }
                }, 1000);
            }
            Log.e("size", "size:"+markers.size());

            // zoom map to show all
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Marker marker : markers) {
                builder.include(marker.getPosition());
            }
            if(userMarker != null) {
                builder.include(userMarker.getPosition());
            }
            LatLngBounds bounds = builder.build();
            int padding = 50; // offset from edges of the map in pixels
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            mMap.animateCamera(cu);

            nearbyPlacesResult.addedMarkers(markers);
        }
    }

}
