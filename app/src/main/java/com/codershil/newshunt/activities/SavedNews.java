package com.codershil.newshunt.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.codershil.newshunt.R;
import com.codershil.newshunt.adapters.SavedNewsAdapter;
import com.codershil.newshunt.data.MyDbHandler;
import com.codershil.newshunt.interfaces.NewsItemClicked;
import com.codershil.newshunt.models.News;

import java.io.Serializable;
import java.util.ArrayList;

public class SavedNews extends AppCompatActivity implements NewsItemClicked {

    ArrayList<News> newsList = MainActivity.savedNews;
    RecyclerView savedNewsRV ;
    SavedNewsAdapter mSavedNewsAdapter = new SavedNewsAdapter(this) ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_news);

        savedNewsRV = findViewById(R.id.savedNewsRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        savedNewsRV.setLayoutManager(layoutManager);
        savedNewsRV.setAdapter(mSavedNewsAdapter);

        mSavedNewsAdapter.updateNews(newsList);
    }


    @Override
    public void newsImageClicked(News item) {
        String url = item.getUrl();
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }

    @Override
    public void shareButtonClicked(News item) {
        String title = item.getTitle();
        String url = item.getUrl();
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, title + "\n link to news post : "+url);
        sharingIntent.setType("text/plain");
        startActivity(Intent.createChooser(sharingIntent, "Share this News using"));
    }

    @Override
    public void deleteButtonClicked(News item) {
    }

    @Override
    public void saveButtonClicked(News item) {

    }


}