package com.example.michaelg.myapplication.Item;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.michaelg.myapplication.R;

public class BookinfoActivity extends AppCompatActivity {

    TextView txtAuthor,
            txtTitle,
            txtPublisher,
            txtCreationdate,
            txtSource,
            txtWebLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookinfo);

        Intent intent = getIntent();
        String author = intent.getStringExtra("author");
        String recordid = intent.getStringExtra("recordId");
        String title = intent.getStringExtra("title");
        String creationdate = intent.getStringExtra("creationdate");
        String publisher = intent.getStringExtra("publisher");
        String source = intent.getStringExtra("source");
        String webLink = intent.getStringExtra("webLink");
        txtAuthor = (TextView) findViewById(R.id.textView_author);
        txtTitle = (TextView) findViewById(R.id.textView_title);
        txtPublisher = (TextView) findViewById(R.id.textView_publisher);
        txtCreationdate = (TextView) findViewById(R.id.textView_creationdate);
        txtSource = (TextView) findViewById(R.id.textView_source);
        txtWebLink = (TextView) findViewById(R.id.textView_weblink);

        txtTitle.setText(       "Title        : " + title);
        txtAuthor.setText(      "Author       : " + author);
        txtCreationdate.setText("Creation Date: " + creationdate);
        txtPublisher.setText(   "Publisher    : " + publisher);
        txtSource.setText(      "Source       : " + source);
        txtWebLink.setText(     "Web Link     : " + webLink);

    }
}
