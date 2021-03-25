package com.codershil.newshunt.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.codershil.newshunt.R;

public class Countries extends AppCompatActivity implements View.OnClickListener {

    /**
     * COUNTRY_KEY is the key for save the data in the sharedPreferences
     * isCountrySelected is the variable to directly move to the next acitivity
     */
    public static String COUNTRY_KEY = "COUNTRY";
    int isCountrySelected = 0 ;
    //declaring the views
    LinearLayout btnIndia, btnUSA , btnUK , btnAustralia , btnFrance , btnRussia;


    // declaring shared preference object and sharedPreference file name
    /**
     * mPreferences : SharedPreference Object
     * sharePrefFile : name of the shared preference file
     * isCountry : String holding the key
     * countryString : a string that holds the country name code
     */
    SharedPreferences mPreferences ;
    public static String sharedPrefFile = "com.codershil.newshunt.settingPrefs" ;
    public static String isCountry = "isCountrySelected";
    String countryString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getting data from sharedPreferences
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        isCountrySelected = mPreferences.getInt(isCountry,0);
        countryString = mPreferences.getString(COUNTRY_KEY,"in");
        if (isCountrySelected==1){
            finish();
            startActivity(new Intent(Countries.this,MainActivity.class));
        }
        setContentView(R.layout.activity_countries);
        initializeViews();
        applyClicks();
    }

    // initializing the Views
    public void initializeViews(){
        btnIndia = findViewById(R.id.btnIndia);
        btnUSA = findViewById(R.id.btnUSA);
        btnUK = findViewById(R.id.btnUK);
        btnAustralia = findViewById(R.id.btnAustralia);
        btnFrance = findViewById(R.id.btnFrance);
        btnRussia = findViewById(R.id.btnRussia);
    }

    // applying the clicks on the views
    public void applyClicks(){ btnIndia.setOnClickListener(this);
        btnUSA.setOnClickListener(this);
        btnUK.setOnClickListener(this);
        btnAustralia.setOnClickListener(this);
        btnFrance.setOnClickListener(this);
        btnRussia.setOnClickListener(this);
    }


    // implementing the onClick interface method
    @Override
    public void onClick(View v) {

        //passing the country data from country activity to MainActivity
        SharedPreferences.Editor preferencesEditor = mPreferences.edit() ;
        Intent intent = new Intent(Countries.this, MainActivity.class);
        String country ;
        switch (v.getId()){

            case R.id.btnIndia:
                country = "in";
                preferencesEditor.putInt(isCountry,1);
                preferencesEditor.putString(COUNTRY_KEY,country);
                preferencesEditor.apply();
                startActivity(intent);
                break;

            case R.id.btnUSA:
                country = "us";
                preferencesEditor.putInt(isCountry,1);
                preferencesEditor.putString(COUNTRY_KEY,country);
                preferencesEditor.apply();
                startActivity(intent);
                break;

            case R.id.btnAustralia:
                country = "au";
                preferencesEditor.putInt(isCountry,1);
                preferencesEditor.putString(COUNTRY_KEY,country);
                preferencesEditor.apply();
                startActivity(intent);
                break;

            case R.id.btnUK:
                country = "gb";
                preferencesEditor.putInt(isCountry,1);
                preferencesEditor.putString(COUNTRY_KEY,country);
                preferencesEditor.apply();
                startActivity(intent);
                break;

            case R.id.btnFrance:
                country = "fr";
                preferencesEditor.putInt(isCountry,1);
                preferencesEditor.putString(COUNTRY_KEY,country);
                preferencesEditor.apply();
                startActivity(intent);
                break;

            case R.id.btnRussia:
                country = "ru";
                preferencesEditor.putInt(isCountry,1);
                preferencesEditor.putString(COUNTRY_KEY,country);
                preferencesEditor.apply();
                startActivity(intent);
                break;

        }
    }
}