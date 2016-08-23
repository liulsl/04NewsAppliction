package com.cskaoyan.zhao.a04newsappliction;

import android.app.Fragment;
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

    private SlidingMenu slidingMenu;
    private FragmentManager fm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //首先设定slidingmenu显示的内容
        setBehindContentView(R.layout.slidingmenu);

        slidingMenu = getSlidingMenu();
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //TOUCHMODE_FULLSCREEN

        slidingMenu.setBehindOffset(350);


        fm = getFragmentManager();

        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentTransaction.replace(R.id.fl_homeactivity_content,new ContentFragment(),"content");
        fragmentTransaction.replace(R.id.fl_homeactivity_leftmenu,new LeftMenuFragment(),"lefemenu");


        fragmentTransaction.commit();


    }

    //enable = ture  可以拖动，false 无法拖动 侧边栏
    public void setSlidingMenuEnable(boolean enable){
        if (enable){
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }

    //弹出或者收起slidingMenu
    public void toggleSlidingMenu(){
        slidingMenu.toggle();
    }


    public LeftMenuFragment  getLeftMenuFragment (){
        LeftMenuFragment lefemenu = (LeftMenuFragment) fm.findFragmentByTag("lefemenu");
        return  lefemenu;
    }

}
