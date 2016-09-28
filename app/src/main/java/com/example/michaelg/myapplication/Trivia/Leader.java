package com.example.michaelg.myapplication.Trivia;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.michaelg.myapplication.Fragments.Params;
import com.example.michaelg.myapplication.R;
import com.example.michaelg.myapplication.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Leader extends AppCompatActivity {
    String userId;
    private LeaderBoardTask userRank; //= null;

    EditText rank1;
    EditText rank2;
    EditText rank3;
    EditText rank4;
    EditText rank5;
    EditText rank6;
    EditText rank7;
    EditText rank8;
    EditText rank9;
    EditText rank10;

    EditText name1;
    EditText name2;
    EditText name3;
    EditText name4;
    EditText name5;
    EditText name6;
    EditText name7;
    EditText name8;
    EditText name9;
    EditText name10;

    EditText score1;
    EditText score2;
    EditText score3;
    EditText score4;
    EditText score5;
    EditText score6;
    EditText score7;
    EditText score8;
    EditText score9;
    EditText score10;

    EditText userRank1;
    EditText  userName1;
    EditText userScore1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        setContentView(R.layout.trivia_activity_leader);


        //android
        // CHANGE
        Intent intent = getIntent();
        //userType = intent.getIntExtra("Type",0);
        User myuser =(User) intent.getSerializableExtra("user");

        userId = myuser.getuserid();
        userRank=new LeaderBoardTask(userId);

        /*rank1=(EditText) findViewById(R.id.rank1);
        rank2=(EditText) findViewById(R.id.rank2);
        rank3=(EditText) findViewById(R.id.rank3);
        rank4=(EditText) findViewById(R.id.rank4);
        rank5=(EditText) findViewById(R.id.rank5);
        rank6=(EditText) findViewById(R.id.rank6);
        rank7=(EditText) findViewById(R.id.rank7);
        rank8=(EditText) findViewById(R.id.rank8);
        rank9=(EditText) findViewById(R.id.rank9);
        rank10=(EditText) findViewById(R.id.rank10);

        name1=(EditText) findViewById(R.id.name1);
        name2=(EditText) findViewById(R.id.name2);
        name3=(EditText) findViewById(R.id.name3);
        name4=(EditText) findViewById(R.id.name4);
        name5=(EditText) findViewById(R.id.name5);
        name6=(EditText) findViewById(R.id.name6);
        name7=(EditText) findViewById(R.id.name7);
        name8=(EditText) findViewById(R.id.name8);
        name9=(EditText) findViewById(R.id.name9);
        name10=(EditText) findViewById(R.id.name10);

        score1=(EditText) findViewById(R.id.score1);
        score2=(EditText) findViewById(R.id.score2);
        score3=(EditText) findViewById(R.id.score3);
        score4=(EditText) findViewById(R.id.score4);
        score5=(EditText) findViewById(R.id.score5);
        score6=(EditText) findViewById(R.id.score6);
        score7=(EditText) findViewById(R.id.score7);
        score8=(EditText) findViewById(R.id.score8);
        score9=(EditText) findViewById(R.id.score9);
        score10=(EditText) findViewById(R.id.score10);*/


        //userRank1=(EditText) findViewById(R.id.userRank);
        //userName1=(EditText) findViewById(R.id.userName);
        //userScore1=(EditText) findViewById(R.id.userScore);
        userRank.execute();




    }




    /***************************************************************************/
//class

    public class LeaderBoardTask extends AsyncTask<Void, Void, JSONObject> {

        private final String userId;

        LeaderBoardTask(String user) {
            userId=user;

        }

        @Override
        protected JSONObject doInBackground(Void... params) {
/*
            if (params.length == 0) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String serverJsonStr = null;
            String format = "json";

            try {
                //we have to change it
                //future change
                //"http://172.20.10.6:8080/LibArab/gamification/leaderBoard?";
                final String FORECAST_BASE_URL =
                        Params.getServer() + "gamification/leaderBoard?";
                //check format
                final String USER = "userId";


                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(USER, userId)
                        .build();

                URL url = new URL(builtUri.toString());
                //Log.v("URL", builtUri.toString());
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
                //Log.d("PROBLEM", serverJsonStr);

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
            }

            JSONObject serverJson = null;
            try {
                serverJson = new JSONObject(serverJsonStr);
                return serverJson;

            } catch (JSONException e) {
                e.printStackTrace();
            }
*/          JSONObject serverJson=null;
            try {
                serverJson.put("dania",true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return serverJson;
        }

        ////////////////////////////////////////////////////////////////////// ON POST EXECUTE
        protected void onPostExecute(final JSONObject leaders) {

                Toast.makeText(getApplicationContext(),leaders.toString(), Toast.LENGTH_LONG).show();
/*
            String jsonRank1;
            String jsonRank2;
            String jsonRank3;
            String jsonRank4;
            String jsonRank5;
            String jsonRank6;
            String jsonRank7;
            String jsonRank8;
            String jsonRank9;
            String jsonRank10;

            String jsonScore1;
            String jsonScore2;
            String jsonScore3;
            String jsonScore4;
            String jsonScore5;
            String jsonScore6;
            String jsonScore7;
            String jsonScore8;
            String jsonScore9;
            String jsonScore10;

            String jsonName1;
            String jsonName2;
            String jsonName3;
            String jsonName4;
            String jsonName5;
            String jsonName6;
            String jsonName7;
            String jsonName8;
            String jsonName9;
            String jsonName10;

            String jsonUserRank;
            String jsonUserName;
            String jsonUserScore;





            userRank = null;
            try {
                JSONArray leadersArray = leaders.getJSONArray("Leaders");


                JSONObject perUser1 = leadersArray.getJSONObject(1);
                jsonRank1 = perUser1.getString("rank");
                jsonScore1 = perUser1.getString("score");
                jsonName1 = perUser1.getString("name");

                JSONObject perUser2 = leadersArray.getJSONObject(2);
                jsonRank2 = perUser2.getString("rank");
                jsonScore2 = perUser2.getString("score");
                jsonName2= perUser2.getString("name");

                JSONObject perUser3 = leadersArray.getJSONObject(3);
                jsonRank3 = perUser3.getString("rank");
                jsonScore3 = perUser3.getString("score");
                jsonName3= perUser3.getString("name");

                JSONObject perUser4 = leadersArray.getJSONObject(4);
                jsonRank4 = perUser4.getString("rank");
                jsonScore4 = perUser4.getString("score");
                jsonName4= perUser4.getString("name");

                JSONObject perUser5 = leadersArray.getJSONObject(5);
                jsonRank5 = perUser5.getString("rank");
                jsonScore5 = perUser5.getString("score");
                jsonName5= perUser5.getString("name");

                JSONObject perUser6 = leadersArray.getJSONObject(6);
                jsonRank6 = perUser6.getString("rank");
                jsonScore6 = perUser6.getString("score");
                jsonName6= perUser6.getString("name");

                JSONObject perUser7 = leadersArray.getJSONObject(7);
                jsonRank7 = perUser7.getString("rank");
                jsonScore7 = perUser7.getString("score");
                jsonName7= perUser7.getString("name");

                JSONObject perUser8 = leadersArray.getJSONObject(8);
                jsonRank8 = perUser8.getString("rank");
                jsonScore8 = perUser8.getString("score");
                jsonName8= perUser8.getString("name");

                JSONObject perUser9 = leadersArray.getJSONObject(9);
                jsonRank9= perUser9.getString("rank");
                jsonScore9 = perUser9.getString("score");
                jsonName9= perUser9.getString("name");

                JSONObject perUser10 = leadersArray.getJSONObject(10);
                jsonRank10 = perUser10.getString("rank");
                jsonScore10 = perUser10.getString("score");
                jsonName10= perUser10.getString("name");

                JSONObject perUser0 = leadersArray.getJSONObject(0);
                jsonUserRank = perUser0.getString("rank");
                jsonUserScore = perUser0.getString("score");
                jsonUserName= perUser0.getString("name");


                rank1.setText(jsonRank1);
                rank2.setText(jsonRank2);
                rank3.setText(jsonRank3);
                rank4.setText(jsonRank4);
                rank5.setText(jsonRank5);
                rank6.setText(jsonRank6);
                rank7.setText(jsonRank7);
                rank8.setText(jsonRank8);
                rank9.setText(jsonRank9);
                rank10.setText(jsonRank10);

                name1.setText(jsonName1);
                name2.setText(jsonName2);
                name3.setText(jsonName3);
                name4.setText(jsonName4);
                name5.setText(jsonName5);
                name6.setText(jsonName6);
                name7.setText(jsonName7);
                name8.setText(jsonName8);
                name9.setText(jsonName9);
                name10.setText(jsonName10);

                score1.setText(jsonScore1);
                score2.setText(jsonScore2);
                score3.setText(jsonScore3);
                score4.setText(jsonScore4);
                score5.setText(jsonScore5);
                score6.setText(jsonScore6);
                score7.setText(jsonScore7);
                score8.setText(jsonScore8);
                score9.setText(jsonScore9);
                score10.setText(jsonScore10);

                userRank1.setText(jsonUserRank);
                userName1.setText(jsonUserName);
                userScore1.setText(jsonUserScore);




                //String id = c.getString("id");

                JSONObject user = leadersArray.getJSONObject(0);
                String userRank = user.getString("rank");
                String userScore = user.getString("score");
                String userName = user.getString("name");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            finish();




            /*
            String answer = null;
            try {
                answer = success.getString("client reply");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (answer.equals("success")) {  //case the user does'nt exist
                //showProgress(false);
                finish();
                Intent intent = new Intent(getApplicationContext(), Menu.class);
                startActivity(intent);

            } else {   // case failed
                try {
                    Toast.makeText(AddQuestion.this, success.getString("message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            */
        }

        @Override
        protected void onCancelled() {
            /*
            userRank = null;
            showProgress(false);*/
        }
    }






}
