package com.cskaoyan.zhao.a04screenadapterapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.BoringLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
          TextView tv = (TextView) findViewById(R.id.tv);

          //方法1 可以通过代码获取屏幕的宽度

       /* DisplayMetrics displayMetrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int widthPixels = displayMetrics.widthPixels;*/

        //方法2
        // 已经知道 控件的宽度是160dp 就刚好平分屏幕
        // px = width_in_dp * 密度比值 （1 1.5）

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float density = displayMetrics.density;

        Log.i(TAG,"density="+density);
        tv.setWidth((int)(160*density) );
    }
}
