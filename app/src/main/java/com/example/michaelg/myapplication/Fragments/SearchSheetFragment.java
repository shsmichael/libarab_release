package com.example.michaelg.myapplication.Fragments;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.michaelg.myapplication.ListviewActivity.ListviewActivity;
import com.example.michaelg.myapplication.R;

import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchSheetFragment extends Fragment {
    private boolean str_serchby;


    private final String TAG =this.getClass().getSimpleName();

    public SearchSheetFragment() {
        // Required empty public constructor
    }

    private EditText title ,
            fromyear ,
            toyear;
    private Button searchbutton ;
    private JSONObject returnedjson;
    private Switch searchby;
     String _SEARCH_URL ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_sheet, container, false);
        getActivity().setTitle(R.string.menu_search);
        title = (EditText) view.findViewById(R.id.title_edittext);
        fromyear = (EditText) view.findViewById(R.id.fromyear_editText);
        toyear = (EditText) view.findViewById(R.id.toYear_editText);
        searchbutton = (Button) view.findViewById(R.id.searchbtn);
        searchby = (Switch) view.findViewById(R.id.switch2) ;

        searchby.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(searchby.isChecked())
                {
                    _SEARCH_URL =   Params.server +"search/sheettitle?";
                    str_serchby=true;
                }
                else
                {
                    _SEARCH_URL = Params.server +"search/sheetauthor?";
                    str_serchby=false;
                }
            }});


        searchbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //perform action on click


                Uri builtUrii ;

                if(str_serchby)
                {
                    Uri builtUri =  Uri.parse(_SEARCH_URL).buildUpon()
                            .appendQueryParameter("userId",    "4")
                            // .appendQueryParameter("title",    title.getText().toString())
                            .appendQueryParameter("title",    title.getText().toString())
                            .appendQueryParameter("fromyear", fromyear.getText().toString())
                            .appendQueryParameter("toyear",   toyear.getText().toString())
                            .build();

                    Log.v("URL", builtUri.toString());
                    Intent i = new Intent(v.getContext() ,ListviewActivity.class);
                    i.putExtra("Value1", builtUri.toString());
                    startActivity(i);
                }
                else
                {
                    Uri builtUri=  Uri.parse(_SEARCH_URL).buildUpon()
                            .appendQueryParameter("userId",    "4")
                            .appendQueryParameter("author",    title.getText().toString())
                            .appendQueryParameter("fromyear", fromyear.getText().toString())
                            .appendQueryParameter("toyear",   toyear.getText().toString())
                            .build();
                    Log.v("URL", builtUri.toString());
                    Intent i = new Intent(v.getContext() ,ListviewActivity.class);
                    i.putExtra("Value1", builtUri.toString());
                    startActivity(i);
                }



                //  Search searchtask = new Search(total);
                //  searchtask.execute();
                //check if json fetched
            }

        });
        return view;
    }


    /*public class Search extends AsyncTask<String[], Void, JSONObject> {

        //    private String _ssfor;
        //    private String _sby;
        //     private String _stxt;
        private String _title;
        private String _userID;
        private String _toyear;
        private String _fromyear;


        @Override
        protected JSONObject doInBackground(String[]... strings) {
            String _server_msg = null;
            HttpURLConnection urlConnection = null;
            String serverJsonStr = null;
            BufferedReader reader = null;


            try {
                //final String _SEARCH_URL =
                //        "http://ec2-52-43-108-148.us-west-2.compute.amazonaws.com:8080/useraccount/search/dosearchbytitle?";
                Log.v("doinbackground", "Here 1");

                Uri builtUri = Uri.parse(_SEARCH_URL).buildUpon()
                        .appendQueryParameter("userId", _userID)
                        .appendQueryParameter("title", _title)
                        .appendQueryParameter("fromyear", _fromyear)
                        .appendQueryParameter("toyear", _toyear)
                        .build();
                URL url = new URL(builtUri.toString());
                Log.v("URL", builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }

                serverJsonStr = buffer.toString();
                Log.v("JSON",serverJsonStr);


            } catch (IOException e) {
                Log.e("LOGE", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("LOGE", "Error closing stream", e);
                    }
                }
                final String LOG_TAG = "tag";
                final String LOG_STATUS = "status";

                JSONObject serverJson = null;
                try {
                    serverJson = new JSONObject(serverJsonStr);
                    return serverJson;

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;

            }

        }

        @Override
        protected void onPostExecute(final JSONObject success) {

            try {
                JSONArray list =success.getJSONArray("list");
                //get arraysize (length -1 )
                //int arraysize =list.length();
                //list.length();
                JSONObject book =list.getJSONObject(0);
                JSONObject book2 =list.getJSONObject(1);
                JSONObject book3 =list.getJSONObject(2);

                String bookid =book.getString("recordId");

                Log.e("back from do in back",bookid);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            returnedjson = success;
        }

    }*/
    
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
