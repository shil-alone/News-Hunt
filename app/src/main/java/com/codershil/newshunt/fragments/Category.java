package com.codershil.newshunt.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.codershil.newshunt.MainActivity;
import com.codershil.newshunt.R;

public class Category extends Fragment implements View.OnClickListener {

    Button btnEntertainment, bntGeneral ,btnBusiness, btnSports, btnScience, btnTechnology, btnHealth;
    TextView txtCategory;

    String countryCode  = MainActivity.getCountryCode();

    public Category() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        intializeViews(view);
        setClickListners();

        return view;
    }

    private void intializeViews(View view) {
        btnEntertainment = view.findViewById(R.id.btnEntertainment);
        bntGeneral = view.findViewById(R.id.btnGeneral);
        btnBusiness = view.findViewById(R.id.btnBusiness);
        btnSports = view.findViewById(R.id.btnSports);
        btnScience = view.findViewById(R.id.btnScience);
        btnTechnology = view.findViewById(R.id.btnTechnology);
        btnHealth = view.findViewById(R.id.btnHealth);
        txtCategory = view.findViewById(R.id.txtCategory);
    }
    private void setClickListners(){
        bntGeneral.setOnClickListener(this);
        btnEntertainment.setOnClickListener(this);
        btnBusiness.setOnClickListener(this);
        btnSports.setOnClickListener(this);
        btnScience.setOnClickListener(this);
        btnTechnology.setOnClickListener(this);
        btnHealth.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEntertainment:
                MainActivity.setUrl("https://saurav.tech/NewsAPI/top-headlines/category/entertainment/"+countryCode+".json");
                ( (MainActivity)getActivity()).loadNews();
                txtCategory.setText("category : Entertainment");

                break;

            case R.id.btnGeneral:
                MainActivity.setUrl("https://saurav.tech/NewsAPI/top-headlines/category/general/"+countryCode+".json");
                ( (MainActivity)getActivity()).loadNews();
                txtCategory.setText("category : General");
                break;

            case R.id.btnBusiness:
                MainActivity.setUrl("https://saurav.tech/NewsAPI/top-headlines/category/business/"+countryCode+".json");
                ( (MainActivity)getActivity()).loadNews();
                txtCategory.setText("category : Business");
                break;

            case R.id.btnScience:
                MainActivity.setUrl("https://saurav.tech/NewsAPI/top-headlines/category/science/"+countryCode+".json");
                ( (MainActivity)getActivity()).loadNews();
                txtCategory.setText("category : Science");
                break;

            case R.id.btnTechnology:
                MainActivity.setUrl("https://saurav.tech/NewsAPI/top-headlines/category/technology/"+countryCode+".json");
                ( (MainActivity)getActivity()).loadNews();
                txtCategory.setText("category : Technology");
                break;

            case R.id.btnSports:
                MainActivity.setUrl("https://saurav.tech/NewsAPI/top-headlines/category/sports/"+countryCode+".json");
                ( (MainActivity)getActivity()).loadNews();
                txtCategory.setText("category : Sports");
                break;

            case R.id.btnHealth:
                MainActivity.setUrl("https://saurav.tech/NewsAPI/top-headlines/category/health/"+countryCode+".json");
                ( (MainActivity)getActivity()).loadNews();
                txtCategory.setText("category : Health");
                break;


        }
    }
}

