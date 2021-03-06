package com.codershil.newshunt.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.codershil.newshunt.data.MyDbHandler;
import com.codershil.newshunt.singleTons.MySingleTon;
import com.codershil.newshunt.models.News;
import com.codershil.newshunt.adapters.NewsAdapter;
import com.codershil.newshunt.interfaces.NewsItemClicked;
import com.codershil.newshunt.R;
import com.codershil.newshunt.fragments.Category;
import com.codershil.newshunt.fragments.EverythingCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewsItemClicked {

    public static String countryCode;

    private static String url ;

    public static void setUrl(String apiurl){
        url = apiurl ;
    }

    public static String getCountryCode(){
        return countryCode;
    }


    ArrayList<News> newsList = new ArrayList<>() ;
    ArrayList<News> savedNewsList = new ArrayList<>() ;
    RecyclerView newsRecyclerView ;
    NewsAdapter newsAdapter = new NewsAdapter(this) ;
    ProgressBar mProgressBar;

    MyDbHandler db = new MyDbHandler(MainActivity.this);
    SavedNews mSavedNews = new SavedNews();




    // implementing activity lifecycle methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsRecyclerView = findViewById(R.id.recyclerView);
        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);



        // this code is for handling intent coming from country class
        Intent intent = getIntent();
        countryCode = intent.getStringExtra(Countries.getCountryKey());
        url = "https://saurav.tech/NewsAPI/top-headlines/category/general/"+countryCode+".json";
        if (countryCode.equals("ev")){
            url = "https://saurav.tech/NewsAPI/everything/bbc-news.json";
        }

        // changing the title of action bar
        changeTitle();
        // here we are setting up fragment
        setCategoryFragment();

        // here we are loading data into recyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        newsRecyclerView.setLayoutManager(layoutManager);
        newsRecyclerView.setAdapter(newsAdapter);
        loadNews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.savedNews){

            mSavedNews.getFullNews(db);
            Toast.makeText(this, "saved image is clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, SavedNews.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MainActivity.this, Countries.class));
    }



    // fetching news data from the api using volley
    public void loadNews(){
        mProgressBar.setVisibility(View.VISIBLE);
        newsList.clear();
        newsRecyclerView.scrollToPosition(0);
        JsonObjectRequest newsRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String title, author, url , urlToImage ;
                    JSONArray newsJsonArray = response.getJSONArray("articles");
                    for (int i = 0 ;i<newsJsonArray.length();i++){

                        JSONObject newsJsonObject = newsJsonArray.getJSONObject(i);
                        title = newsJsonObject.getString("title");
                        author = newsJsonObject.getString("author");
                        url = newsJsonObject.getString("url");
                        urlToImage = newsJsonObject.getString("urlToImage");
                        newsList.add(new News(author,title,url,urlToImage));
                    }
                    newsAdapter.updateNews(newsList);
                    mProgressBar.setVisibility(View.GONE);
                }

                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressBar.setVisibility(View.GONE);
                error.printStackTrace();
                Toast.makeText(MainActivity.this, "check your internet and restart app", Toast.LENGTH_SHORT).show();
            }
        }) ;

        MySingleTon.getInstance(this).addToRequestQue(newsRequest);
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
    public void deleteButtonClicked(News item,int position) {

    }

    @Override
    public void saveButtonClicked(News item) {
        db.addNews(item);
        savedNewsList = db.getAllNews();
        Toast.makeText(MainActivity.this, "News Saved", Toast.LENGTH_SHORT).show();
    }


    public void setCategoryFragment(){

        if (countryCode.equals("ev")){
            EverythingCategory everythingCategory = new EverythingCategory();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_linear_layout , everythingCategory);
            fragmentTransaction.commit();
        }
        else {
            Category category = new Category();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_linear_layout, category);
            fragmentTransaction.commit();
        }
    }

    public void changeTitle(){
        switch (countryCode) {
            case "ev":
                getSupportActionBar().setTitle("Everything");
                break;
            case "in":
                getSupportActionBar().setTitle("India");
                break;
            case "us":
                getSupportActionBar().setTitle("USA");
                break;
            case "au":
                getSupportActionBar().setTitle("Australia");
                break;
            case "gb":
                getSupportActionBar().setTitle("United Kingdom");
                break;
            case "ru":
                getSupportActionBar().setTitle("Russia");
                break;
            case "fr":
                getSupportActionBar().setTitle("France");
                break;
        }
    }


}