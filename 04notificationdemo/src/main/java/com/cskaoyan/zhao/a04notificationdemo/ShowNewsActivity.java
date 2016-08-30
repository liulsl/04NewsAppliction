package com.cskaoyan.zhao.a04notificationdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ShowNewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        TextView tv = (TextView) findViewById(R.id.tv);

        tv.setText(url);

    }
}
