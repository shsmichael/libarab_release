package com.example.michaelg.myapplication.Trivia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.michaelg.myapplication.R;

public class StartQuiz extends AppCompatActivity{
    //  implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trivia_app_bar_main);


        Button btnStart=(Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent conceptIntent=new Intent(StartQuiz.this,ConceptActivity.class);
                startActivity(conceptIntent);

            }
        });

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;
    }


}
