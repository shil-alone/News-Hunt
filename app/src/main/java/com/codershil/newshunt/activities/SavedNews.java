package com.codershil.newshunt.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codershil.newshunt.R;
import com.codershil.newshunt.adapters.SavedNewsAdapter;
import com.codershil.newshunt.data.MyDbHandler;
import com.codershil.newshunt.interfaces.NewsItemClicked;
import com.codershil.newshunt.models.News;

import java.util.ArrayList;
import java.util.Collections;

public class SavedNews extends AppCompatActivity implements NewsItemClicked {

    /**
     * db : the news database handler
     * newsList : a list to store saved news
     * savedNewsRV : a recyclerview to show saved news
     * mSavedNewsAdapter : an adapter for recyclerview
     */

    MyDbHandler db = new MyDbHandler(SavedNews.this);
    static ArrayList<News> newsList ;
    RecyclerView savedNewsRV ;
    SavedNewsAdapter mSavedNewsAdapter = new SavedNewsAdapter(this) ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_news);

        // initializing views and setting up layout manager and adapter for recyclerview
        savedNewsRV = findViewById(R.id.savedNewsRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        savedNewsRV.setLayoutManager(layoutManager);
        savedNewsRV.setAdapter(mSavedNewsAdapter);
        Collections.reverse(newsList);
        mSavedNewsAdapter.updateNews(newsList);
        getSupportActionBar().setTitle("Saved News");

    }

    // method for creating the menu for savedNews activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.saved_news_menu,menu);
        return true;
    }

    // called when menu item selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.deleteAllNews){
            db.deleteAllNews();
            newsList.clear();
            mSavedNewsAdapter.updateNews(newsList);
            Toast.makeText(this, "All News Deleted", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    //  will get all the saved news from the database
    public void getFullNews(MyDbHandler database){
        newsList = database.getAllNews() ;
        db = database ;
    }


    // called when newsImage is clicked
    @Override
    public void newsImageClicked(News item) {
        String url = item.getUrl();
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }

    // will share the news using sharing intent
    @Override
    public void shareButtonClicked(News item) {
        String title = item.getTitle();
        String url = item.getUrl();
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, title + "\n link to news post : "+url);
        sharingIntent.setType("text/plain");
        startActivity(Intent.createChooser(sharingIntent, "Share this News using"));
    }

    // this will delete the news from the database it takes position of the news in adapter
    @Override
    public void deleteButtonClicked(News item,int position) {
        db.deleteNews(item);
        newsList.remove(position);
        Toast.makeText(this, "News Deleted", Toast.LENGTH_SHORT).show();
        mSavedNewsAdapter.updateNews(newsList);
    }

    // no implementation in this activity
    @Override
    public void saveButtonClicked(News item) {

    }



}