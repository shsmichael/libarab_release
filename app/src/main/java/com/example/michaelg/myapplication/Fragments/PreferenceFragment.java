package com.example.michaelg.myapplication.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.michaelg.myapplication.R;


public  class PreferenceFragment extends com.github.machinarius.preferencefragment.PreferenceFragment {

    private final String TAG =this.getClass().getSimpleName();


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.app_preferences);
    }

}
