package com.example.michaelg.myapplication.Trivia;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.michaelg.myapplication.Item.ViewPagerActivity;
import com.example.michaelg.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class triviaListview extends AppCompatActivity {
    private ArrayList<ItemsQ>items=new ArrayList<ItemsQ>();
    ListviewQAdapter adapter;
    private getQuizeTask quizes;
    //Array of options --> Array adapter--> Listview
    //listview :(views:trivia_q_items.xml)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia_listview);
        quizes=new getQuizeTask();

        quizes.execute();
        /*adapter=new ListviewQAdapter(
                this,//context for the activity
                R.layout.trivia_q_items,//layout to use
                items);//items to be displayed*/
    }

    private void populateListView() {
        //creat list of items

/////////////////////////////////////////////////////////////////////////////////
        /////////// add async task object
/////////////////////////////////////////////////////////////////////////////////
        //build adapter
        adapter=new ListviewQAdapter(
                this,//context for the activity
                R.layout.trivia_q_items,//layout to use
                items);//items to be displayed



        //configure the list view
        ListView list=(ListView)findViewById(R.id.listview);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
                // TODO Auto-generated method stub
                //Toast.makeText(getApplicationContext(), items.get(position).getAuthor(), Toast.LENGTH_LONG).show();
                Intent intent1=new Intent(getApplicationContext(),StartQuiz.class);
                intent1.putExtra("auther",items.get(position).getAuthor());
                intent1.putExtra("itemName",items.get(position).getItemName());
                // Remember that variable (user) is the private variable above that is sent by the search

                startActivity(intent1);

            }
        });



    }


    public class getQuizeTask extends AsyncTask<Void, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(Void... params) {
            Log.v("connect", "CONNECTED");
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String serverJsonStr = null;
            String format = "json";

            try {
                //change
                final String FORECAST_BASE_URL =
                        "http://52.29.110.203:8080/LibArab/gamification/getQuziItems";;

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
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

                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                serverJsonStr = buffer.toString();
                Log.d("PROBLEM", serverJsonStr);

            } catch (IOException e) {

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
            }

            JSONObject serverJson = null;
            try {
                serverJson = new JSONObject(serverJsonStr);
                return serverJson;

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        ////////////////////////////////////////////////////////////////////// ON POST EXECUTE
        protected void onPostExecute(final JSONObject itemsQ) {
            if(itemsQ==null){
                Toast.makeText(getApplicationContext(),"null yes", Toast.LENGTH_LONG).show();
            }
            //else Toast.makeText(getApplicationContext(),"done", Toast.LENGTH_LONG).show();

            else {
                //Toast.makeText(getApplicationContext(),itemsQ.toString(), Toast.LENGTH_LONG).show();

                quizes = null;
                try {
                    JSONArray itemsRelateQ = itemsQ.getJSONArray("items");
                    //Toast.makeText(getApplicationContext(),itemsQ.length(), Toast.LENGTH_LONG).show();
                    for(int i=0;i<itemsRelateQ.length();i++){
                        JSONObject itemQuize = itemsRelateQ.getJSONObject(i);

                        String auther1 = itemQuize.getString("author ");
                        String itemName1 = itemQuize.getString("name ");

                        ItemsQ item=new ItemsQ();
                        item.setAuthor(auther1);
                        item.setItemName(itemName1);

                        items.add(item);
                    }
                    populateListView();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //finish();

            }
        }



    }
}
