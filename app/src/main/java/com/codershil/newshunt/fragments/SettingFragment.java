package com.codershil.newshunt.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.codershil.newshunt.R;
import com.codershil.newshunt.activities.Countries;
import com.codershil.newshunt.activities.MainActivity;

public class SettingFragment extends PreferenceFragmentCompat {

    /**
     * COUNTRY_LIST_KEY : an key for country list defined in the settings layout
     * mPreferenceChangeListener : SharedPreferences.OnSharedPreferenceChangeListener object
     * mPreferences : SharedPreference object
     */
    public static final String COUNTRY_LIST_KEY = "countries_list";
    private SharedPreferences.OnSharedPreferenceChangeListener mPreferenceChangeListener ;
    SharedPreferences mPreferences ;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings_pref);

        // it is called when we change the country from the list preferences
        mPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

                if (key.equals(COUNTRY_LIST_KEY)){
                    Preference country_preference = findPreference(key);
                    country_preference.setSummary(sharedPreferences.getString(key,"your country"));
                    MainActivity.countryCode = sharedPreferences.getString(key,"in");
                }

            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(mPreferenceChangeListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(mPreferenceChangeListener);
    }
}
