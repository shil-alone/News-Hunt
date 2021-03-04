package com.codershil.newshunt.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.codershil.newshunt.activities.MainActivity;
import com.codershil.newshunt.R;


public class EverythingCategory extends Fragment implements View.OnClickListener {

    Button btnBbcNews , btnCnn , btnFoxNews , btnGoogleNews;
    TextView txtEverything ;


    public EverythingCategory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_everything_category, container, false);
        initializeViews(view);
        setClickListners();

        return view ;
    }

    public void initializeViews(View view){
        btnBbcNews = view.findViewById(R.id.btnBbcNews);
        btnCnn = view.findViewById(R.id.btnCnn);
        btnFoxNews = view.findViewById(R.id.btnFoxNews);
        btnGoogleNews = view.findViewById(R.id.btnGoogleNews);
        txtEverything = view.findViewById(R.id.txtEverything);
    }

    public void setClickListners(){
        btnBbcNews.setOnClickListener(this);
        btnCnn.setOnClickListener(this);
        btnFoxNews.setOnClickListener(this);
        btnGoogleNews.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnBbcNews:
                txtEverything.setText("Category : BBC News");
                MainActivity.setUrl("https://saurav.tech/NewsAPI/everything/bbc-news.json");
                ( (MainActivity)getActivity()).loadNews();
                break;

            case R.id.btnCnn:
                txtEverything.setText("Category : CNN");
                MainActivity.setUrl("https://saurav.tech/NewsAPI/everything/cnn.json");
                ( (MainActivity)getActivity()).loadNews();
                break;

            case R.id.btnFoxNews:
                txtEverything.setText("Category : Fox News");
                MainActivity.setUrl("https://saurav.tech/NewsAPI/everything/fox-news.json");
                ( (MainActivity)getActivity()).loadNews();
                break;

            case R.id.btnGoogleNews:
                txtEverything.setText("Category : Google News");
                MainActivity.setUrl("https://saurav.tech/NewsAPI/everything/google-news.json");
                ( (MainActivity)getActivity()).loadNews();
                break;

        }


    }
}