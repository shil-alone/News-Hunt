package com.codershil.newshunt.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.codershil.newshunt.R;

public class Countries extends AppCompatActivity implements View.OnClickListener {

    public static String COUNTRY_KEY = "COUNTRY";
    Button btnEverything, btnIndia, btnUSA , btnUK , btnAustralia , btnFrance , btnRussia;

    public static String getCountryKey(){
        return COUNTRY_KEY;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);
        initializeViews();
        applyClicks();
    }

    public void initializeViews(){
        btnEverything = findViewById(R.id.btnEverything);
        btnIndia = findViewById(R.id.btnIndia);
        btnUSA = findViewById(R.id.btnUSA);
        btnUK = findViewById(R.id.btnUK);
        btnAustralia = findViewById(R.id.btnAustralia);
        btnFrance = findViewById(R.id.btnFrance);
        btnRussia = findViewById(R.id.btnRussia);
    }

    public void applyClicks(){
        btnEverything.setOnClickListener(this);
        btnIndia.setOnClickListener(this);
        btnUSA.setOnClickListener(this);
        btnUK.setOnClickListener(this);
        btnAustralia.setOnClickListener(this);
        btnFrance.setOnClickListener(this);
        btnRussia.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(Countries.this, MainActivity.class);
        String country ="" ;

        switch (v.getId()){
            case R.id.btnEverything:
                country = "ev";
                intent.putExtra(COUNTRY_KEY,country);
                startActivity(intent);
                break;

            case R.id.btnIndia:
                country = "in";
                intent.putExtra(COUNTRY_KEY ,country);
                startActivity(intent);
                break;

            case R.id.btnUSA:
                country = "us";
                intent.putExtra(COUNTRY_KEY,country);
                startActivity(intent);
                break;

            case R.id.btnAustralia:
                country = "au";
                intent.putExtra(COUNTRY_KEY ,country);
                startActivity(intent);
                break;

            case R.id.btnUK:
                country = "gb";
                intent.putExtra(COUNTRY_KEY ,country);
                startActivity(intent);
                break;

            case R.id.btnFrance:
                country = "fr";
                intent.putExtra(COUNTRY_KEY ,country);
                startActivity(intent);
                break;

            case R.id.btnRussia:
                country = "ru";
                intent.putExtra(COUNTRY_KEY ,country);
                startActivity(intent);
                break;

        }
    }
}