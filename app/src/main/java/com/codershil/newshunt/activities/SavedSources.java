package com.codershil.newshunt.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codershil.newshunt.R;
import com.codershil.newshunt.adapters.SavedNewsAdapter;
import com.codershil.newshunt.adapters.SavedSourceAdapter;
import com.codershil.newshunt.data.MyDbHandler;
import com.codershil.newshunt.data.SourceDatabaseHandler;
import com.codershil.newshunt.interfaces.SourceItemClicked;
import com.codershil.newshunt.models.News;
import com.codershil.newshunt.models.Source;

import java.util.ArrayList;
import java.util.Collections;

public class SavedSources extends AppCompatActivity implements SourceItemClicked {

    /**
     * db : the news database handler
     * sourceList : a list to store saved sources
     * savedSourceRV : a recyclerview to show saved source item
     * SavedSourceAdapter : an adapter for recyclerview
     */
    SourceDatabaseHandler db = new SourceDatabaseHandler(SavedSources.this);
    static ArrayList<Source> sourceList = new ArrayList<>() ;
    RecyclerView savedSourceRV ;
    SavedSourceAdapter mSavedSourceAdapter= new SavedSourceAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_sources);

        // initializing views and setting up layout manager and adapter for recyclerview
        savedSourceRV = findViewById(R.id.saveSourceRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        savedSourceRV.setLayoutManager(layoutManager);
        savedSourceRV.setAdapter(mSavedSourceAdapter);
        Collections.reverse(sourceList);
        mSavedSourceAdapter.updateSource(sourceList);
        getSupportActionBar().setTitle("Saved Sources");

    }

    // method for creating the menu for savedNews activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.saved_source_menu,menu);
        return true;
    }

    // called when menu item selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.deleteAllSources){
            db.deleteAllSources();
            sourceList.clear();
            mSavedSourceAdapter.updateSource(sourceList);
            Toast.makeText(this, "All Sources Deleted", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    //  will get all the saved sources from the database
    public void getFullSources(SourceDatabaseHandler database){
        sourceList = database.getAllSources() ;
        db = database ;
    }

    // opens the chrome custom tab in with the saved source page in our app
    @Override
    public void sourceItemClicked(Source source) {
        String url = source.getUrl();
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }

    // no implementation in this activity
    @Override
    public void sourceSaveButtonClicked(Source source) {

    }

    // delete the news from the database and update recyclerview
    @Override
    public void sourceDeleteButtonClicked(Source source,int position) {
        db.deleteSource(source);
        sourceList.remove(position);
        Toast.makeText(this, "Source Deleted", Toast.LENGTH_SHORT).show();
        mSavedSourceAdapter.updateSource(sourceList);
    }
}