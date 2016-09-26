package com.example.michaelg.myapplication.Fragments;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.michaelg.myapplication.LoginActivity;
import com.example.michaelg.myapplication.MainActivity;
import com.example.michaelg.myapplication.R;
import com.example.michaelg.myapplication.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    User user = new User("eden","sharvit","male","8/4/86");


    private final String TAG =this.getClass().getSimpleName();

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView textView_userprofile_fname;
        textView_userprofile_fname = (TextView) rootView.findViewById(R.id.tv_userprofile_f_name);
        textView_userprofile_fname.setText(user.getFirstname());
        TextView textView_userprofile_lname;
        textView_userprofile_lname = (TextView) rootView.findViewById(R.id.tv_userprofile_l_name);
        textView_userprofile_lname.setText(user.getLastname());


        return rootView;
    }




    @Override
    public void onResume() {
        super.onResume();

        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){

                    getView().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
                    MenuFragment menufragment = new MenuFragment();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    Bundle bundle = new Bundle();
                    //bundle.putInt("Type", userType);
                    menufragment.setArguments(bundle);
                    manager.beginTransaction().replace(R.id.fragment_main,
                            menufragment,
                            menufragment.getTag()
                    ).commit();

                    return true;
                }
                return false;
            }
        });
    }
}
