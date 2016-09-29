package com.example.michaelg.myapplication.Fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michaelg.myapplication.ListviewActivity.ListviewActivity;
import com.example.michaelg.myapplication.R;
import com.example.michaelg.myapplication.User;

import org.json.JSONObject;
import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchSheetFragment extends Fragment {
    //TODO: @michael we shall be able to save the userIf as private to be able to send it to the next intents
    //private userId;
    //TODO: @michael inorder to recieve it we should get it from the prev intent,
    // to do that we shall call that function -> Exrtra.getString("userId"); from onCreate
    private boolean str_serchby;
    private final String TAG =this.getClass().getSimpleName();

    public SearchSheetFragment() {
        // Required empty public constructor
    }
    private Switch searchby;

    private EditText title ,
            fromyear ,
            toyear;
    private Spinner spinner;
    private Button searchbutton ;
    private JSONObject returnedjson;
    private TextView tv_titleorauthor;
    String _SEARCH_URL ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        final View view = inflater.inflate(R.layout.fragment_search_sheet, container, false);
        getActivity().setTitle(R.string.menu_search);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        title = (EditText) view.findViewById(R.id.title_edittext);
        fromyear = (EditText) view.findViewById(R.id.fromyear_editText);
        toyear = (EditText) view.findViewById(R.id.toYear_editText);
        searchbutton = (Button) view.findViewById(R.id.searchbtn);
        tv_titleorauthor=(TextView) view.findViewById(R.id.tv_title);


        User newUser= (User) getActivity().getIntent().getSerializableExtra("user");
        final String username =newUser.getUsername();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.searcharray,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {


                switch (position){
                    case 0:
                        tv_titleorauthor.setText(R.string.search_title);
                        _SEARCH_URL =   Params.server +"search/sheettitle?";
                        str_serchby=true;
                        break;
                    case 1:
                        tv_titleorauthor.setText(R.string.search_author);
                        _SEARCH_URL = Params.server +"search/sheetauthor?";
                        str_serchby=false;
                    default:
                        break;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
        searchbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //perform action on click

                //TODO: @michael change userId not 4 in both cases
                if(str_serchby)
                {
                    Uri builtUri =  Uri.parse(_SEARCH_URL).buildUpon()
                            .appendQueryParameter("userId",    username)
                            // .appendQueryParameter("title",    title.getText().toString())
                            .appendQueryParameter("title",    title.getText().toString())
                            .appendQueryParameter("fromyear", fromyear.getText().toString())
                            .appendQueryParameter("toyear",   toyear.getText().toString())
                            .build();





                    Log.v("URLBookFRAG", builtUri.toString());
                    Intent i = new Intent(v.getContext() ,ListviewActivity.class);
                    i.putExtra("Value1", builtUri.toString());
                    //TODO: @Michael i.putExtra("userId",userId);
                    startActivity(i);
                }
                else
                {
                    Uri builtUri=  Uri.parse(_SEARCH_URL).buildUpon()
                            .appendQueryParameter("userId",   username)
                            .appendQueryParameter("author",    title.getText().toString())
                            .appendQueryParameter("fromyear", fromyear.getText().toString())
                            .appendQueryParameter("toyear",   toyear.getText().toString())
                            .build();
                    Log.v("URLSheetFRAG", builtUri.toString());
                    Intent i = new Intent(v.getContext() ,ListviewActivity.class);
                    i.putExtra("Value1", builtUri.toString());
                    startActivity(i);
                }



            }

        });
        return view;
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorSearch)));
    }

}
