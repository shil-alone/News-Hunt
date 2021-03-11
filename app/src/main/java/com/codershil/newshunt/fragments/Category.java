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

public class Category extends Fragment implements View.OnClickListener {

    Button btnEntertainment, bntGeneral ,btnBusiness, btnSports, btnScience, btnTechnology, btnHealth;
    Button btnBbcNews , btnCnn , btnFoxNews , btnGoogleNews;
    TextView txtCategory;

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
        btnBbcNews = view.findViewById(R.id.btnBbcNews);
        btnCnn = view.findViewById(R.id.btnCnn);
        btnFoxNews = view.findViewById(R.id.btnFoxNews);
        btnGoogleNews = view.findViewById(R.id.btnGoogleNews);
    }
    private void setClickListners(){
        bntGeneral.setOnClickListener(this);
        btnEntertainment.setOnClickListener(this);
        btnBusiness.setOnClickListener(this);
        btnSports.setOnClickListener(this);
        btnScience.setOnClickListener(this);
        btnTechnology.setOnClickListener(this);
        btnHealth.setOnClickListener(this);
        btnBbcNews.setOnClickListener(this);
        btnCnn.setOnClickListener(this);
        btnFoxNews.setOnClickListener(this);
        btnGoogleNews.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnEntertainment:
                MainActivity.setCategory("entertainment");
                ( (MainActivity)getActivity()).loadNews();
                ( (MainActivity)getActivity()).changeText();

                break;

            case R.id.btnGeneral:
                MainActivity.setCategory("general");
                ( (MainActivity)getActivity()).loadNews();
                ( (MainActivity)getActivity()).changeText();
                break;

            case R.id.btnBusiness:
                MainActivity.setCategory("business");
                ( (MainActivity)getActivity()).loadNews();
                ( (MainActivity)getActivity()).changeText();
                break;

            case R.id.btnScience:
                MainActivity.setCategory("science");
                ( (MainActivity)getActivity()).loadNews();
                ( (MainActivity)getActivity()).changeText();
                break;

            case R.id.btnTechnology:
                MainActivity.setCategory("technology");
                ( (MainActivity)getActivity()).loadNews();
                ( (MainActivity)getActivity()).changeText();
                break;

            case R.id.btnSports:
                MainActivity.setCategory("sports");
                ( (MainActivity)getActivity()).loadNews();
                ( (MainActivity)getActivity()).changeText();
                break;

            case R.id.btnHealth:
                MainActivity.setCategory("health");
                ( (MainActivity)getActivity()).loadNews();
                ( (MainActivity)getActivity()).changeText();
                break;

            case R.id.btnBbcNews:
                MainActivity.setCategory("bbc");
                ( (MainActivity)getActivity()).loadNews();
                ( (MainActivity)getActivity()).changeText();
                break;

            case R.id.btnCnn:
                MainActivity.setCategory("cnn");
                ( (MainActivity)getActivity()).loadNews();
                ( (MainActivity)getActivity()).changeText();
                break;

            case R.id.btnFoxNews:
                MainActivity.setCategory("fox");
                ( (MainActivity)getActivity()).loadNews();
                ( (MainActivity)getActivity()).changeText();
                break;

            case R.id.btnGoogleNews:
                MainActivity.setCategory("google");
                ( (MainActivity)getActivity()).loadNews();
                ( (MainActivity)getActivity()).changeText();
                break;


        }
    }
}

