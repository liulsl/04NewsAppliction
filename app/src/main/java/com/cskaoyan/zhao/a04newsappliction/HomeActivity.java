package com.cskaoyan.zhao.a04newsappliction;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import fragment.ContentFragment;
import fragment.LeftMenuFragment;

//
public class HomeActivity extends SlidingFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //首先设定slidingmenu显示的内容
        setBehindContentView(R.layout.slidingmenu);

        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);  //TOUCHMODE_FULLSCREEN
        slidingMenu.setBehindOffset(300);


       FragmentManager fm = getFragmentManager();

        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentTransaction.replace(R.id.fl_homeactivity_content,new ContentFragment());
        fragmentTransaction.replace(R.id.fl_homeactivity_leftmenu,new LeftMenuFragment());


        fragmentTransaction.commit();


    }
}
