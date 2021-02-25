package com.codershil.newshunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.codershil.newshunt.fragments.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewsItemClicked{

    public static String countryCode;

    private static String url ;

    public static void setUrl(String apiurl){
        url = apiurl ;
    }

    public static String getCountryCode(){
        return countryCode;
    }



    ArrayList<News> newsList = new ArrayList<>() ;
    RecyclerView newsRecyclerView ;
    NewsAdapter newsAdapter = new NewsAdapter(this) ;


    // implementing activity lifecycle methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsRecyclerView = findViewById(R.id.recyclerView);

        // this code is for handling intent coming from country class
        Intent intent = getIntent();
        countryCode = intent.getStringExtra(Countries.getCountryKey());

        url = "https://saurav.tech/NewsAPI/top-headlines/category/general/"+countryCode+".json";
        if (countryCode.equals("ev")){
            url = "https://saurav.tech/NewsAPI/everything/bbc-news.json";
        }

        // changing the title of action bar
        changeTitle(countryCode);

        // here we are setting up fragment
        setCategoryFragment(countryCode);

        // here we are loading data into recyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        newsRecyclerView.setLayoutManager(layoutManager);
        newsRecyclerView.setAdapter(newsAdapter);
        loadNews();
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




    // this method is actual method for getting data from api and show it in the recyclerView
    public void loadNews(){
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

                }

                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MainActivity.this, "check your internet and restart app", Toast.LENGTH_SHORT).show();
            }
        }) ;

        MySingleTon.getInstance(this).addToRequestQue(newsRequest);
    } // load news method ends here

    @Override
    public void newImageClicked(News item) {
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

    public void setCategoryFragment( String countryCode){

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

    public void changeTitle(String countryCode){
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