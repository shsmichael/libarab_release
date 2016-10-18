package com.example.michaelg.myapplication.Trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.michaelg.myapplication.R;

public class Progress extends AppCompatActivity {

    Button mainButton ;
    Button addQuestionButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trivia_activity_progress);

        mainButton = (Button) findViewById(R.id.main);
        mainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //EditText question = (EditText) findViewById(R.id.question_text);
                //String question1 = question.getText().toString();
                //Toast.makeText(MainActivity.this, question1, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        // TODO: 18/10/2016 Dania fix button view
       /* addQuestionButton = (Button) findViewById(R.id.button);
        addQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //EditText question = (EditText) findViewById(R.id.question_text);
                //String question1 = question.getText().toString();
                //Toast.makeText(MainActivity.this, question1, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), AddQuestion.class);
                startActivity(intent);
            }
        });
        */
    }
}
