package com.example.michaelg.myapplication.Trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.michaelg.myapplication.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trivia_activity_main);
    }

    public void start(View view) {
        Intent intent = new Intent(getApplicationContext(), StartQuiz.class);
        startActivity(intent);
    }

    public void create(View view) {
        Intent intent = new Intent(getApplicationContext(), AddQuestion.class);
        startActivity(intent);
    }

    public void leaderboard(View view) {
        Intent intent = new Intent(getApplicationContext(), Leader.class);
        startActivity(intent);
    }
}
