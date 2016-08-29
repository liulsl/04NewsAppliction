package com.cskaoyan.zhao.a04newsappliction;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

public class SplashActivity extends Activity {

    private RelativeLayout rl_splashactivity_bg;
    private SharedPreferences config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        rl_splashactivity_bg = (RelativeLayout) findViewById(R.id.rl_splashactivity_bg);


        showAnimation();

        //不能在oncreate里直接跳到下一个页面。
//        startActivity(new Intent(this,GuideActivity.class));



    }

    private void showAnimation() {
        AnimationSet as = new AnimationSet(false);

        RotateAnimation rotateAnimation =
        new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(2000);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(2000);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(2000);

        as.addAnimation(rotateAnimation);
        as.addAnimation(scaleAnimation);
        as.addAnimation(alphaAnimation);

        rl_splashactivity_bg.setAnimation(as);


        //
        config = getSharedPreferences("config", MODE_PRIVATE);

        //as.setRepeatCount(3);
        as.start();

        as.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //动画开始播放的时候call
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画结束的时候call
                //如果之前已经有进入过guide，就让他直接进入主页面。

                boolean isShowGuide = config.getBoolean("isShowGuide", false);

                if (isShowGuide){
                    startActivity(new Intent(SplashActivity.this,HomeActivity.class));
                }else{
                    startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                }
                finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //动画结束的时候call

            }
        });
    }
}
