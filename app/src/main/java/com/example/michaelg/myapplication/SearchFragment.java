package com.example.michaelg.myapplication;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.michaelg.myapplication.ListviewActivity.ListviewActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    private EditText title , fromyear , toyear;
    private Button searchbutton ;

    private final String TAG =this.getClass().getSimpleName();
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        title = (EditText) view.findViewById(R.id.title_edittext);
        fromyear = (EditText) view.findViewById(R.id.fromyear_editText);
        toyear = (EditText) view.findViewById(R.id.toYear_editText);
        searchbutton = (Button) view.findViewById(R.id.searchbtn);
        final String _SEARCH_URL = "http://52.29.110.203:8080/LibArab/search/booktitle?";
        searchbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //perform action on click

                Uri builtUri = Uri.parse(_SEARCH_URL).buildUpon()
                        .appendQueryParameter("userId", "23")
                        // .appendQueryParameter("title",    title.getText().toString())
                        .appendQueryParameter("title", "any")
                        .appendQueryParameter("fromyear", fromyear.getText().toString())
                        .appendQueryParameter("toyear", toyear.getText().toString())
                        .build();
                Log.v("URL", builtUri.toString());
                Intent i = new Intent(getActivity(), ListviewActivity.class);
                i.putExtra("Value1", builtUri.toString());
                startActivity(i);

                //  Search searchtask = new Search(total);
                //  searchtask.execute();
                //check if json fetched

            }
        });



        return view;

    }
}
