package com.example.michaelg.myapplication.Trivia;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.michaelg.myapplication.Fragments.Params;
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

public class ConceptActivity extends AppCompatActivity {

    private Question currentQuestion;

    private TextView txtQuestion,tvNoOfQs;
    private RadioButton rbtnA, rbtnB, rbtnC,rbtnD;

    AddQTask quizList;
    ArrayList <Question> ques;
    private int questionId=0;
    private int answeredQsNo=0;
    private Button btnNext;
    private int obtainedScore=0;
    private int numOfQ=0;
    private ArrayList<String> myAnsList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.trivia_activity_concept);
        Toast.makeText(getApplicationContext(), "yes", Toast.LENGTH_LONG).show();
        quizList=new AddQTask("Anya@","123");
        ques=new ArrayList<Question>();


        quizList.execute();
        //Toast.makeText(getApplicationContext(), ques.get(0).getOptionA().toString(), Toast.LENGTH_LONG).show();
        init();
        //Toast.makeText(getApplicationContext(), ques.get(0).getOptionA().toString(), Toast.LENGTH_LONG).show();
        //Initialize the database
        // Toast.makeText(getApplicationContext(), ques.toString(), Toast.LENGTH_LONG).show();

        //currentQuestion=ques.get(questionId);

        //Set question
        // setQuestionsView();

        //Check and Next
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup grp=(RadioGroup)findViewById(R.id.radioGroup1);
                RadioButton answer=(RadioButton)findViewById(grp.getCheckedRadioButtonId());

                Log.e("Answer ID", "Selected Positioned  value - "+grp.getCheckedRadioButtonId());

                if(answer!=null){
                    Log.e("Answer", currentQuestion.getANSWER() + " -- " + answer.getText());
                    //Add answer to the list
                    myAnsList.add(""+answer.getText());

                    if(currentQuestion.getANSWER().equals(answer.getText())){
                        obtainedScore++;
                        Log.e("comments", "Correct Answer");
                        Log.d("score", "Obtained score " + obtainedScore);
                    }else{
                        Log.e("comments", "Wrong Answer");
                    }
                    if(questionId<3){
                        currentQuestion=ques.get(questionId);
                        setQuestionsView();
                    }else{
                        Intent intent = new Intent(ConceptActivity.this, ResultActivity.class);

                        Bundle b = new Bundle();
                        b.putInt("score", obtainedScore);
                        b.putInt("totalQs", ques.size());
                        b.putStringArrayList("myAnsList", myAnsList);
                        intent.putExtras(b);
                        startActivity(intent);
                        finish();

                    }

                }else{
                    Log.e("comments", "No Answer");
                }

                //Need to clear the checked item id
                grp.clearCheck();


            }//end onClick Method
        });

    }


    public void init(){
        tvNoOfQs=(TextView)findViewById(R.id.tvNumberOfQuestions);
        txtQuestion=(TextView)findViewById(R.id.tvQuestion);
        rbtnA=(RadioButton)findViewById(R.id.radio0);
        rbtnB=(RadioButton)findViewById(R.id.radio1);
        rbtnC=(RadioButton)findViewById(R.id.radio2);
        rbtnD=(RadioButton)findViewById(R.id.radio3);

        btnNext=(Button)findViewById(R.id.btnNext);

        myAnsList = new ArrayList<String>();

    }


    private void setQuestionsView()
    {
        rbtnA.setChecked(false);
        rbtnB.setChecked(false);
        rbtnC.setChecked(false);
        rbtnD.setChecked(false);

        answeredQsNo=questionId+1;
        tvNoOfQs.setText("Questions "+answeredQsNo+" of "+ques.size());

        txtQuestion.setText(currentQuestion.getQUESTION());
        rbtnA.setText(currentQuestion.getOptionA());
        rbtnB.setText(currentQuestion.getOptionB());
        rbtnC.setText(currentQuestion.getOptionC());
        rbtnD.setText(currentQuestion.getOptionD());

        questionId++;
    }









    public class AddQTask extends AsyncTask<Void, Void, JSONObject> {
        String userName;
        String itemId;

        AddQTask(String name,String item) {
            userName=name;
            itemId=item;
        }

        protected JSONObject doInBackground(Void... params) {


            Log.v("connect", "CONNECTED");
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String serverJsonStr = null;
            String format = "json";

            try {
                //change
                final String FORECAST_BASE_URL =
                        "http://52.29.110.203:8080/LibArab/gamification/Startquzi?userName=Anya@sk&itemId=123";;

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
        @Override
        protected void onPostExecute(final JSONObject quizListJson) {
            quizList = null;

            if(quizListJson==null)
                Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_LONG).show();
            else {


                quizList = null;
                try {
                    JSONArray itemsRelateQ = quizListJson.getJSONArray("questions");
                    numOfQ=itemsRelateQ.length();

                    for(int i=0;i<itemsRelateQ.length();i++){

                        JSONObject itemQuize = itemsRelateQ.getJSONObject(i);
                        String question = itemQuize.getString("qustion");
                        String answer1 = itemQuize.getString("answer1");
                        String answer2 = itemQuize.getString("answer2");
                        String answer3 = itemQuize.getString("answer3");
                        String answer4 = itemQuize.getString("answer4");
                        String correct = itemQuize.getString("answer1");
                        Question q=new Question(question,answer1,answer2,answer3,answer4,correct);

                        ques.add(q);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                init();

                currentQuestion=ques.get(questionId);

                //Set question
                setQuestionsView();


                //finish();

            }
        }

        @Override
        protected void onCancelled() {
            quizList = null;

        }
    }





}
