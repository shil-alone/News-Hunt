package com.codershil.newshunt.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.codershil.newshunt.adapters.SourceAdapter;
import com.codershil.newshunt.data.MyDbHandler;
import com.codershil.newshunt.data.SourceDatabaseHandler;
import com.codershil.newshunt.interfaces.SourceItemClicked;
import com.codershil.newshunt.models.Source;
import com.codershil.newshunt.singleTons.MySingleTon;
import com.codershil.newshunt.models.News;
import com.codershil.newshunt.adapters.NewsAdapter;
import com.codershil.newshunt.interfaces.NewsItemClicked;
import com.codershil.newshunt.R;
import com.codershil.newshunt.fragments.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewsItemClicked,SourceItemClicked{


    /**
     * countryCode : country code provided by the api eg in,gb,us ,etc
     * url : an api url
     * category : news category which will be changed on button click
     * isNews : indicator for  which method loadNews() or loadSources() to be called onResume()
     */
    public static String countryCode;
    private static String url ;
    private static String category = "general";
    public static byte isNews = 1;


    /**
     * newsList : list of the news objects
     * sourceList : list of the source objects
     * savedNewsList : list of news to be saved on sql lite database
     * savedSourceList : list of source to be saved on sql lite database
     */
    ArrayList<News> newsList = new ArrayList<>() ;
    ArrayList<Source> sourceList = new ArrayList<>() ;
    ArrayList<News> savedNewsList = new ArrayList<>() ;
    ArrayList<Source> savedSourcesList = new ArrayList<>() ;

    // initializing recyclerView for showing the news and setting up the adapter for news and source
    RecyclerView newsRecyclerView ;
    NewsAdapter newsAdapter = new NewsAdapter(this) ;
    SourceAdapter sourceAdapter = new SourceAdapter(this) ;

    // declaring views
    ProgressBar mProgressBar;
    TextView txtCategory ;
    SwipeRefreshLayout swipeRefreshLayout ;

    // declaring database object and other objects for database handling
    MyDbHandler db = new MyDbHandler(MainActivity.this);
    SourceDatabaseHandler sourceDb = new SourceDatabaseHandler(MainActivity.this);
    SavedNews mSavedNews = new SavedNews();
    SavedSources mSavedSources = new SavedSources();
    //declaring SharedPreferences object to save the country code
    SharedPreferences mPreferences ;

    // implementing activity lifecycle methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing views
        newsRecyclerView = findViewById(R.id.recyclerView);
        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        txtCategory = findViewById(R.id.txtCategory);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isNews == 1){
                    loadNews();
                    swipeRefreshLayout.setRefreshing(false);
                    return;
                }
                loadSources();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        // initializing preferences
        mPreferences = getSharedPreferences(Countries.sharedPrefFile,MODE_PRIVATE);

        // this code is for handling intent coming from country class
        countryCode = mPreferences.getString(Countries.COUNTRY_KEY,"in");

        // saving the url and category in savedInstanceState
        if (savedInstanceState!=null){
            url =savedInstanceState.getString("url");
            txtCategory.setText(savedInstanceState.getString("text"));
        }
        // changing the title of action bar
        changeTitle();
        // here we are setting up fragment
        setCategoryFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
            changeTitle();
            changeText();
            if (isNews == 1){
                loadNews();
            }
            else {
                loadSources();
            }
            mPreferences.getString("category","general");
    }

    @Override
    protected void onPause() {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString("category",txtCategory.getText().toString());
        super.onPause();
    }

    // this method is used to store the small amount of data that can be retrieved in the onCreate() method
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("url",url);
        outState.putString("text",txtCategory.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        changeTitle();
        if (isNews == 1){
            loadNews();
            return;
        }
        loadSources();
    }

    // fetching news data from the api using volley
    public void loadNews(){
        /**
         * linearLayoutManager will help us to arrange the news in the recyclerView like horizontal ,vertical,scattered
         */
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        newsRecyclerView.setLayoutManager(layoutManager);
        newsRecyclerView.setAdapter(newsAdapter);
        setUrl();
        mProgressBar.setVisibility(View.VISIBLE);
        newsList.clear();

        // creating JsonObjectRequest and parsing the data and add the news object 
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
                    newsRecyclerView.scrollToPosition(0);
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

        // adding newsRequest to the request queue
        MySingleTon.getInstance(this).addToRequestQue(newsRequest);
    }

    // fetching source data from the api using volley
    public void loadSources(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        newsRecyclerView.setLayoutManager(layoutManager);
        newsRecyclerView.setAdapter(sourceAdapter);

        setUrl();
        mProgressBar.setVisibility(View.VISIBLE);
        sourceList.clear();

        // creating JsonObjectRequest and parsing the data and add the source object
        JsonObjectRequest newsRequest = new JsonObjectRequest(Request.Method.GET, "https://saurav.tech/NewsAPI/sources.json", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String name, description, url ,category,language,country ;
                    JSONArray newsJsonArray = response.getJSONArray("sources");
                    for (int i = 0 ;i<newsJsonArray.length();i++){

                        JSONObject newsJsonObject = newsJsonArray.getJSONObject(i);
                        name = newsJsonObject.getString("name");
                        description = newsJsonObject.getString("description");
                        url = newsJsonObject.getString("url");
                        category = newsJsonObject.getString("category");
                        language = newsJsonObject.getString("language");
                        country = newsJsonObject.getString("country");
                        sourceList.add(new Source(name,description,url,category,language,country));
                    }
                    sourceAdapter.updateSource(sourceList);
                    newsRecyclerView.scrollToPosition(0);
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


    // the function to add the menu on actionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    // this method is called when menu item selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.weather:
                Intent intent = new Intent(MainActivity.this, Weather.class);
                startActivity(intent);
                break;
            case R.id.savedNews:
                mSavedNews.getFullNews(db);
                startActivity(new Intent(MainActivity.this, SavedNews.class));
                break;
            case R.id.savedSource:
                mSavedSources.getFullSources(sourceDb);
                startActivity(new Intent(MainActivity.this, SavedSources.class));
                break;
            case R.id.settings:
                startActivity(new Intent(MainActivity.this, Settings.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    // opening a chrome custom tab for viewing the news using the url in our app
    @Override
    public void newsImageClicked(News item) {
        String url = item.getUrl();
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }

    // this method will share the news by using the sharing intent in text form
    @Override
    public void shareButtonClicked(News item) {
        String title = item.getTitle();
        String url = item.getUrl();
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, title + "\n link to news post : "+url);
        sharingIntent.setType("text/plain");
        startActivity(Intent.createChooser(sharingIntent, "Share this News using"));
    }

    // this method implementation is empty in MaiActivity class
    @Override
    public void deleteButtonClicked(News item,int position) { }

    // this method saved the news object into the database
    @Override
    public void saveButtonClicked(News item) {
        db.addNews(item);
        savedNewsList = db.getAllNews();
        Toast.makeText(MainActivity.this, "News Saved", Toast.LENGTH_SHORT).show();
    }

    //this will open the source url in chrome custom tab
    @Override
    public void sourceItemClicked(Source source) {
        String url = source.getUrl();
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }

    // saves the source in source database
    @Override
    public void sourceSaveButtonClicked(Source source) {
        sourceDb.addSource(source);
        savedSourcesList = sourceDb.getAllSources();
        Toast.makeText(MainActivity.this, "Source Saved", Toast.LENGTH_SHORT).show();
    }

    // method implementation is empty
    @Override
    public void sourceDeleteButtonClicked(Source source, int position) {

    }

    // sets the category fragment in linear layout
    public void setCategoryFragment(){
            Category category = new Category();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_linear_layout, category);
            fragmentTransaction.commit();
    }

    // change the title of actionbar
    public void changeTitle(){

        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(Countries.COUNTRY_KEY,countryCode);
        editor.apply();

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

    //change the category text
    public void changeText(){
        switch (category) {
            case "general":
                txtCategory.setText("Category : General");
                break;
            case "business":
                txtCategory.setText("Category : Business");
                break;
            case "entertainment":
                txtCategory.setText("Category : Entertainment");
                break;
            case "sports":
                txtCategory.setText("Category : Sports");
                break;
            case "science":
                txtCategory.setText("Category : Science");
                break;
            case "technology":
                txtCategory.setText("Category : Technology");
                break;
            case "health":
                txtCategory.setText("Category : Health");
                break;
            case "bbc":
                txtCategory.setText("Category : BBC News");
                break;
            case "fox":
                txtCategory.setText("Category : Fox News");
                break;
            case "google":
                txtCategory.setText("Category : Google News");
                break;
            case "cnn":
                txtCategory.setText("Category : CNN News");
                break;
            case "sources":
                txtCategory.setText("Category : Sources");
                break;
        }
    }

    // sets the url according to button click
    public void setUrl(){
        switch (category) {
            case "bbc":
                url = "https://saurav.tech/NewsAPI/everything/bbc-news.json";
                break;
            case "fox":
                url = "https://saurav.tech/NewsAPI/everything/fox-news.json";
                break;
            case "cnn":
                url = "https://saurav.tech/NewsAPI/everything/cnn.json";
                break;
            case "google":
                url = "https://saurav.tech/NewsAPI/everything/google-news.json";
                break;
            default:
                url = "https://saurav.tech/NewsAPI/top-headlines/category/"+category+ "/"+countryCode+".json";
        }
    }

    //set method for category
    public static void setCategory(String cat){
        category = cat ;
    }

}