package com.example.michaelg.myapplication.Trivia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.content.Intent;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.michaelg.myapplication.R;

public class AddQuestion extends AppCompatActivity {
    EditText question;
    EditText correct;
    EditText uncorrect1;
    EditText uncorrect2;
    EditText uncorrect3;
    Button addQuestionButton;

    //private UserLoginTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.ac);
        setContentView(R.layout.trivia_activity_add_question);

        question = (EditText) findViewById(R.id.question_text);
        correct = (EditText) findViewById(R.id.correct_answer);
        uncorrect1 = (EditText) findViewById(R.id.uncorrect_answer1);
        uncorrect2 = (EditText) findViewById(R.id.uncorrect_answer2);
        uncorrect3 = (EditText) findViewById(R.id.uncorrect_answer3);


        String question1 = question.getText().toString();

        addQuestionButton = (Button) findViewById(R.id.button);
        addQuestionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //EditText question = (EditText) findViewById(R.id.question_text);
                //String question1 = question.getText().toString();
                //Toast.makeText(AddQuestion.this, question1, Toast.LENGTH_LONG).show();
                attemptAddQuestion();
            }
        });



    }


    private void attemptAddQuestion(){
        String question1 = question.getText().toString();
        String correctAns=correct.getText().toString();
        String wrongAns1=uncorrect1.getText().toString();
        String wrongAns2=uncorrect2.getText().toString();
        String wrongAns3=uncorrect3.getText().toString();

        String question1_trim;
        String correctAns_trim;
        String wrongAns1_trim;
        String wrongAns2_trim;
        String wrongAns3_trim;

        question1_trim =question1.trim();
        correctAns_trim = correctAns.trim();
        wrongAns1_trim=wrongAns1.trim();
        wrongAns2_trim=wrongAns2.trim();
        wrongAns3_trim=wrongAns3.trim();

        int flag=0;
        /*if(TextUtils.isEmpty(question1)||TextUtils.isEmpty(correctAns)||TextUtils.isEmpty(wrongAns1)||TextUtils.isEmpty(wrongAns2)||TextUtils.isEmpty(wrongAns3)){
            flag=2;
        }*/
        if(question1_trim.length()==0||correctAns_trim.length()==0||wrongAns1_trim.length()==0||wrongAns2_trim.length()==0||wrongAns3_trim.length()==0){
            flag=1;
        }
        if (flag==1)
            Toast.makeText(AddQuestion.this,  "space", Toast.LENGTH_LONG).show();
        if(flag==0) {
            //Toast.makeText(AddQuestion.this, "yes", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), Progress.class);
            startActivity(intent);
            //mAuthTask = new UserLoginTask(question1, correctAns,wrongAns1,wrongAns2,wrongAns3);
            // mAuthTask.execute((Void) null);
        }
    }


/***************************************************************************/
/*
    public class UserLoginTask extends AsyncTask<Void, Void, JSONObject> {

        private final String question1;
        private final String correct1;
        private final String uncorrect11;
        private final String uncorrect21;
        private final String uncorrect31;

        UserLoginTask(String q, String cor,String wrong1,String wrong2,String wrong3) {
            question1=q;
            correct1=cor;
            uncorrect11=wrong1;
            uncorrect21=wrong2;
            uncorrect31=wrong3;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            if (params.length == 0) {
                return null;
            }

            Log.v("connect", "CONNECTED");
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String serverJsonStr = null;
            String format = "json";

            try {
                final String FORECAST_BASE_URL =
                        Params.server + "signIn/doSignIn?";

                final String QUESTION = "question";
                final String CORRECT_ANSWER = "correct answer";
                final String UNCORRECT_ANSWER1 = "uncorrect answer 1";
                final String UNCORRECT_ANSWER2 = "uncorrect answer 2";
                final String UNCORRECT_ANSWER3 = "uncorrect answer 3";


                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(QUESTION, question1)
                        .appendQueryParameter(CORRECT_ANSWER, correct1)
                        .appendQueryParameter(UNCORRECT_ANSWER1, uncorrect11)
                        .appendQueryParameter(UNCORRECT_ANSWER2, uncorrect21)
                        .appendQueryParameter(UNCORRECT_ANSWER3, uncorrect31)
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

            return null;
        }

        ////////////////////////////////////////////////////////////////////// ON POST EXECUTE
        @Override
        protected void onPostExecute(final JSONObject success) {
            mAuthTask = null;

            showProgress(false);
            String answer = null;
            try {
                answer = success.getString("client reply");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (answer.equals("success")) {  //case the user does'nt exist
                showProgress(false);
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
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
*/





}

