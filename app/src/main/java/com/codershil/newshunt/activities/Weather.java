package com.codershil.newshunt.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.codershil.newshunt.R;
import com.codershil.newshunt.keys.Keys;
import com.codershil.newshunt.singleTons.MySingleTon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Weather extends AppCompatActivity {
    /**
     * lacation manager : get the location of the user
     * geocoder : address using the latitude and longitude
     * address : list of address object
     * baseUrl : base url of api
     */

    private LocationManager mLocationManager;
    Geocoder geocoder;
    public static final int REQUEST_LOCATION = 1;
    List<Address> addresses ;
    double latitude = 79.1;
    double longitude = 21.15;
    String city ;
    private String baseUrl ;

    //declaring views
    TextView txtLocation, txtTemperature, txtDesc, txtHumidity, txtTempMax,
            txtTempMin, txtWind, txtPressure, txtWindDegree, txtClouds;
    ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initializeViews();
        getSupportActionBar().setTitle("Weather");
        // initializing the geocoder
        geocoder = new Geocoder(this, Locale.getDefault());

        // checking for location permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // requesting location
           ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
           ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_LOCATION);
        }
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // check if gps is on or not
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            onGPS();
        }
        else {
            //gps is already on
            getLocation();
            try {
                addresses = geocoder.getFromLocation(latitude,longitude,1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            city = addresses.get(0).getLocality();
            txtLocation.setText("Location : "+ city);
            baseUrl = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&units=metric&appid="+ Keys.getWeatherKey();
        }

        // will load the weather in into ui
        loadWeather();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLocation();
        loadWeather();
    }

    // this method gets the latitude and longitude using the location
    private void getLocation() {
        //check permission again

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // requesting location
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_LOCATION);

        }
        else {
            Location locationGps = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location locationNetwork = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location locationPassive = mLocationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (locationGps!= null){
                latitude = locationGps.getLatitude();
                longitude = locationGps.getLongitude();
            }
            else if (locationNetwork!= null){
                latitude = locationNetwork.getLatitude();
                longitude = locationNetwork.getLongitude();
            }
            else if (locationPassive!= null){
                latitude = locationPassive.getLatitude();
                longitude = locationPassive.getLongitude();
            }
            else {
                Toast.makeText(this, "Unable to get your location !", Toast.LENGTH_SHORT).show();
            }
        }

    }

    // allowing user to on the location service
    private void onGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this) ;
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    //actual function to load weather
    public void loadWeather(){
        mProgressBar.setVisibility(View.VISIBLE);
        // creating stringRequest and parsing the json and loading the data into views
        StringRequest weatherRequest = new StringRequest(Request.Method.GET, baseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String description ,temperature,tempMin,tempMax,humidity,windSpeed,pressure,windDegree,clouds;
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray weatherArray = jsonResponse.getJSONArray("weather");
                    JSONObject weatherObject = weatherArray.getJSONObject(0);
                    description = weatherObject.getString("description");

                    JSONObject mainObj = jsonResponse.getJSONObject("main");

                    temperature= mainObj.getString("temp");
                    tempMin= mainObj.getString("temp_min");
                    tempMax= mainObj.getString("temp_max");
                    pressure= mainObj.getString("pressure");
                    humidity= mainObj.getString("humidity");

                    JSONObject wind = jsonResponse.getJSONObject("wind");
                    windSpeed = wind.getString("speed");
                    windDegree = wind.getString("deg");

                    JSONObject cloudsObj = jsonResponse.getJSONObject("clouds");
                    clouds =cloudsObj.getString("all");



                    txtDesc.setText("Description : "+description);
                    txtTemperature.setText("Temperature : "+temperature+" °C");
                    txtTempMin.setText("Temp Min. : "+tempMin+" °C");
                    txtTempMax.setText("Temp Max. : "+tempMax+" °C");
                    txtPressure.setText("Pressure : "+pressure+" hPa");
                    txtHumidity.setText("Humidity : "+humidity +" %");
                    txtWind.setText("Wind Speed : "+windSpeed+" m/s");
                    txtWindDegree.setText("Wind Degree : "+windDegree+" °");
                    txtClouds.setText("Clouds : "+clouds);

                    mProgressBar.setVisibility(View.GONE);

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(Weather.this, "check your internet and restart app", Toast.LENGTH_SHORT).show();
            }
        });

        MySingleTon.getInstance(Weather.this).addToRequestQue(weatherRequest);

    }

    // initializing the views
    public void initializeViews(){
        txtLocation = findViewById(R.id.txtLocation);
        txtTemperature = findViewById(R.id.txtTemperature);
        txtDesc = findViewById(R.id.txtDesc);
        txtHumidity = findViewById(R.id.txtHumidity);
        txtTempMax = findViewById(R.id.txtTempMax);
        txtTempMin = findViewById(R.id.txtTempMin);
        txtWind = findViewById(R.id.txtWind);
        txtPressure = findViewById(R.id.txtPressure);
        txtWindDegree = findViewById(R.id.txtWindDegree);
        txtClouds = findViewById(R.id.txtClouds);
        mProgressBar = findViewById(R.id.progressBar2);
    }


}