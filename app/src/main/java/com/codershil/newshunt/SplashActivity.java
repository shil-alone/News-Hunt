package com.codershil.newshunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.codershil.newshunt.activities.Countries;
import com.codershil.newshunt.activities.MainActivity;

public class SplashActivity extends AppCompatActivity {

    /**
     * mPreferences : a SharedPreferences object
     * isCountrySelected : an integer variable which is used to check whether country is selected
     */
    SharedPreferences mPreferences ;
    int isCountrySelected = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //hiding the action bar
        getSupportActionBar().hide();

        //getting the value of isCountrySelected from sharedPreferences
        mPreferences = getSharedPreferences(Countries.sharedPrefFile, MODE_PRIVATE);
        isCountrySelected = mPreferences.getInt(Countries.isCountry,0);

        // thread for making delay on splash activity and intent handling
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    // this will freeze the activity for 3 seconds
                    sleep(3000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    finish();
                    if(isCountrySelected == 1) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }else {
                        startActivity(new Intent(SplashActivity.this, Countries.class));
                    }
                }
            }
        };
        thread.start();

    }
}