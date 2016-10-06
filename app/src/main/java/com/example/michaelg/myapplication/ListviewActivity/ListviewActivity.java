package com.example.michaelg.myapplication.ListviewActivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michaelg.myapplication.Fragments.Params;
import com.example.michaelg.myapplication.R;
import com.example.michaelg.myapplication.Item.ViewPagerActivity;
import com.example.michaelg.myapplication.User;
//import com.example.misho.login.Item.ViewPagerActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class ListviewActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    int index;
    ArrayList<Book> bookList;
    String myURL="any";
    private String ID;
    private String user;
    private String searchfor;
    private String fromyear;
    String _SEARCH_URL ;
    private String toyear;
    private String txt;
    private TextView resultstitle;
    private int totalhits;
    private String searchby;
    private  int counter;

    bookAdapter adapter;
    private FloatingActionButton nxt, prev;



    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listviewactivity);

    /*    if (user == "Guest") {
            user="guest@lib";

        }*/
        bookList = new ArrayList<Book>();
        Bundle extras = getIntent().getExtras();
         if(extras != null) {
             //TODO: after we recieve the userId from the prev intent that is searchBookFragment/searchSheet..
             user=extras.getString("userId");
              myURL = extras.getString("Value1");
             fromyear=extras.getString("fromyear");
             toyear=extras.getString("toyear");
             searchfor=extras.getString("searchfor");
             txt=extras.getString("txt");
             _SEARCH_URL=extras.getString("searchurl");
             index=extras.getInt("index");
             searchby= extras.getString("searchby");


         }


        //new JSONAsyncTask().execute("http://ec2-52-43-108-148.us-west-2.compute.amazonaws.com:8080/useraccount/search/dosearchbytitle?userid=123123&title=me&fromyear=1960&toyear=1970");
        //new JSONAsyncTask().execute("http://52.29.110.203:8080/LibArab/search/booktitle?userId=23&title=any");
       Log.v("ListviewActivity URL:",myURL);


        new JSONAsyncTask().execute(myURL);

        ListView listview = (ListView)findViewById(R.id.list);
        adapter = new bookAdapter(getApplicationContext(), R.layout.row2, bookList);
        listview.setAdapter(adapter);
      //  Log.v("ABC","CCC");
        resultstitle = (TextView) findViewById(R.id.textView11) ;
        resultstitle.setText("Results ["+Integer.toString(index)+"-"+ Integer.toString(index+24)+"]");


                prev= (FloatingActionButton) findViewById(R.id.fab1);
        nxt= (FloatingActionButton) findViewById(R.id.fab);
        if(index==0)
        {
            prev.hide();
        }

   /*     if(index>0)
        {
            prev.show();
        }*/



        //  Log.v("TAA",Integer.toString(nxt.getId()));

        nxt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //perform action on click
                index=index+24;
                if(index>0)
                {
                    prev.show();
                }

                if(index>totalhits)
                    nxt.hide();

                Uri builtUri =  Uri.parse(_SEARCH_URL).buildUpon()
                        .appendQueryParameter("userId",    user)
                        // .appendQueryParameter("title",    title.getText().toString())
                        .appendQueryParameter(searchby,    txt)
                        .appendQueryParameter("fromyear", fromyear)
                        .appendQueryParameter("toyear",   toyear)
                        .appendQueryParameter("index", Integer.toString(index) )

                        .build();

                Log.v("URLBookFRAG", builtUri.toString());
                Log.v("Iam","Heere");
//
                Intent i = new Intent(v.getContext() ,ListviewActivity.class);
                i.putExtra("Value1", builtUri.toString());
                i.putExtra("searchurl",_SEARCH_URL);
                i.putExtra("userid",user);
                i.putExtra("txt", txt);
                i.putExtra("fromyear", fromyear);
                i.putExtra("toyear", toyear);
                i.putExtra("index", index);
                i.putExtra("searchby", searchby);

                //TODO: @Michael i.putExtra("userId",userId);
                startActivity(i);


            }

        });

        prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                index=index-24;
                //perform action on click
                if(index==0)
                {
                    prev.hide();
                }


                Uri builtUri =  Uri.parse(_SEARCH_URL).buildUpon()
                        .appendQueryParameter("userId",    user)
                        // .appendQueryParameter("title",    title.getText().toString())
                        .appendQueryParameter(searchby,    txt)
                        .appendQueryParameter("fromyear", fromyear)
                        .appendQueryParameter("toyear",   toyear)
                        .appendQueryParameter("index", Integer.toString(index) )

                        .build();

                Log.v("URLBookFRAG", builtUri.toString());
                Log.v("Iam","Heere");
                Intent i = new Intent(v.getContext() ,ListviewActivity.class);
                i.putExtra("Value1", builtUri.toString());
                i.putExtra("searchurl",_SEARCH_URL);
                i.putExtra("userid",user);
                i.putExtra("txt", txt);
                i.putExtra("fromyear", fromyear);
                i.putExtra("toyear", toyear);
                i.putExtra("index", index);
                i.putExtra("searchby", searchby);

                //TODO: @Michael i.putExtra("userId",userId);
                startActivity(i);



            }

        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), bookList.get(position).getRecordid(), Toast.LENGTH_LONG).show();
                Intent intent1=new Intent(getApplicationContext(),ViewPagerActivity.class);
                intent1.putExtra("recordId",bookList.get(position).getRecordid());
                intent1.putExtra("author",bookList.get(position).getAuthor());
                intent1.putExtra("title",bookList.get(position).getTitle());
                intent1.putExtra("creationdate",bookList.get(position).getCreationdate());
                intent1.putExtra("publisher",bookList.get(position).getPublisher());
                intent1.putExtra("webLink",bookList.get(position).getWeblink());
                intent1.putExtra("type",searchfor);
                intent1.putExtra("source",bookList.get(position).getSource());
                // Remember that variable (user) is the private variable above that is sent by the search

                startActivity(intent1);
            }
        });



    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    class JSONAsyncTask extends AsyncTask<String, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ListviewActivity.this);
            dialog.setMessage("Loading, please wait");
            dialog.setTitle("Connecting server");
            dialog.show();
            dialog.setCancelable(false);
        }


        @Override
        protected String  doInBackground(String... urls) {
            String total_hits = "";
            try {

                //------------------>>
                HttpGet httppost = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);

                    JSONObject jsono = new JSONObject(data);
                    String res = jsono.getString("result");
                     total_hits= jsono.getString("totalHits");
                    Log.v("totalHits", (total_hits));

                    if (res.equals("false"))
                        return total_hits;

                    JSONArray jarray = jsono.getJSONArray("docs");

                    for (int i = 0; i < jarray.length(); i++)
                    {
                        JSONObject object = jarray.getJSONObject(i);
                        Book currentbook = new Book();
                        currentbook.setRecordid(object.getString("recordId"));
                        String  author = new String(object.getString("author").getBytes("ISO-8859-1"), "UTF-8");
                        author = author.replace("?","");
                        //currentbook.setTitle(new String(object.getString("title").getBytes("ISO-8859-1"), "UTF-8"));
                        String  title = new String(object.getString("title").getBytes("ISO-8859-1"), "UTF-8");
                        title = title.replace("?","");
                        currentbook.setTitle(title);
                        currentbook.setCreationdate(new String (object.getString("creationdate").getBytes("ISO-8859-1"), "UTF-8"));
                        currentbook.setPublisher(new String(object.getString("publisher").getBytes("ISO-8859-1"), "UTF-8"));
                        currentbook.setAuthor(author);
                        if(object.has("libWebLink"))
                        {
                            currentbook.setWeblink(object.getString("libWebLink"));

                        }
                        else
                        {
                            currentbook.setWeblink(object.getString("webLink"));

                        }

                        currentbook.setSource(object.getString("source"));
                     //   currentbook.setThumbnail(object.getString());
                        //  currentbook.setAuthor(new String(object.getString("author").getBytes("ISO-8859-1"), "UTF-8"));
                        bookList.add(currentbook);
                    }
                    return total_hits;
                }

                //------------------>>

            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return total_hits;
        }

        protected void onPostExecute(String total ) {
            dialog.cancel();
            totalhits=Integer.parseInt(total);
            adapter.notifyDataSetChanged();
            if(total.equals("0"))
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }
}