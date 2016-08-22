package com.cskaoyan.zhao.a04newsappliction;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GuideActivity extends AppCompatActivity {

    private ViewPager vp_guideactivity_guide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        vp_guideactivity_guide = (ViewPager) findViewById(R.id.vp_guideactivity_guide);

        vp_guideactivity_guide.setAdapter(new MyGuidePageAdapter());
    }


    class MyGuidePageAdapter extends PagerAdapter{


        @Override
        public int getCount() {
            return 5;
        }

        //view==object 默认写法
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        //instantiate Item --》给一个position 去实例化一个item
        // 默认返回view
        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            TextView tv = new TextView(GuideActivity.this);
            tv.setText("pager"+position);
            tv.setTextColor(Color.RED);

            //区别于listview 的地方，不仅仅返回回去就行了。
            container.addView(tv);

            return   tv;//super.instantiateItem(container, position);
        }

        //destroyItem 销毁。
        //把对应的page上的view 从contianer 移除去。
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {


            container.removeView((TextView)object);

            // super.destroyItem(container, position, object);
        }
    }
}
