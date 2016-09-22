package com.example.michaelg.myapplication.Fragments;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.michaelg.myapplication.R;
import com.example.michaelg.myapplication.ImageAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {

    GridView gridView;
    static final String[] MOBILE_OS = new String[] { "Android", "iOS","Windows", "Blackberry" ,"A" , "B", "C" ,  "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
    private final String TAG =this.getClass().getSimpleName();
    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View myview = inflater.inflate(R.layout.fragment_favorites, container, false);
        getActivity().setTitle(R.string.menu_favorites);

        gridView = (GridView) myview.findViewById(R.id.gridView);

        gridView.setAdapter(new ImageAdapter(myview.getContext(), MOBILE_OS));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                Toast.makeText(myview.getContext(),"" + position, Toast.LENGTH_LONG).show();
            }
        });

        //getActivity().getActionBar().setBackgroundDrawable(new ColorDrawable(0xff00DDED));
        return myview;
    }

}
