package com.cskaoyan.zhao.a04newsappliction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class ShowNewsActivity extends AppCompatActivity {

    private static final String TAG = "ShowNewsActivity";
    private WebSettings settings;
    private SharedPreferences sp;
    private ProgressBar pb_shownewsactivity_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);

        getSupportActionBar().hide();

        pb_shownewsactivity_loading = (ProgressBar) findViewById(R.id.pb_shownewsactivity_loading);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        WebView wv_shownewsactivity_content = (WebView) findViewById(R.id.wv_shownewsactivity_content);
        if (!url.isEmpty())
            wv_shownewsactivity_content.loadUrl(url);


        wv_shownewsactivity_content.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            //页面加载完毕的时候
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pb_shownewsactivity_loading.setVisibility(View.GONE);

                Log.i(TAG,"onPageFinished");
            }


        });

        settings = wv_shownewsactivity_content.getSettings();
        sp = getSharedPreferences("config",MODE_PRIVATE);
        choice = sp.getInt("textsize", 2);
        // settings.setTextSize(WebSettings.TextSize.LARGEST);
        settings.setTextZoom(textsize[choice]);
    }


    public void back(View v){
        finish();
    }

    int choice =2;
    int[] textsize=new int[]{200, 150 ,100 ,75 ,50};
    public void changeSize(View v){


        final String[] choices = new String[]{"超大号","大号","正常","小号","超小号"};
        new AlertDialog.Builder(this)
                .setTitle("修改字体")
                .setSingleChoiceItems(choices, choice, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        choice=which;
                    }
                })
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //200 150 100 75 50

                        SharedPreferences.Editor edit = sp.edit();
                        edit.putInt("textsize",choice);
                        edit.commit();
                        settings.setTextZoom(textsize[choice]);
                    }
                })
                .setNegativeButton("取消",null)
                .show();


    }


    public void share(View v){

        showShare();
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用

       // getString()
        oks.setTitle("one key share");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

       // 启动分享GUI
        oks.show(this);
    }


}
