package com.cskaoyan.zhao.a04newsappliction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class ShowNewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        WebView wv_shownewsactivity_content = (WebView) findViewById(R.id.wv_shownewsactivity_content);
        if (!url.isEmpty())
            wv_shownewsactivity_content.loadUrl(url);
    }
}
