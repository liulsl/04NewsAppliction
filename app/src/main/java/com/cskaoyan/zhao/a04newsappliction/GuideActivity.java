package com.cskaoyan.zhao.a04newsappliction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    private ViewPager vp_guideactivity_guide;

    private int[] imgResIds = new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3  };

    private List<ImageView> imageViewList ;
    public static final String TAG ="GuideActivity" ;

    private List<MyPageInfo> myPageInfoList ;
    private Button bt_guideactivity_enter;

    private final int PageCount =3;
    private LinearLayout ll_guideactivity_indicator;
    private View rp_guideactivity;

    class MyPageInfo{

        ImageView pageIv;
        String pageTilte;
        int x;
        //可以加入很多信息。表示当前的page的信息。

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        getSupportActionBar().hide();

        vp_guideactivity_guide = (ViewPager) findViewById(R.id.vp_guideactivity_guide);
        vp_guideactivity_guide.setAdapter(new MyGuidePageAdapter());
        bt_guideactivity_enter = (Button) findViewById(R.id.bt_guideactivity_enter);
        ll_guideactivity_indicator = (LinearLayout) findViewById(R.id.ll_guideactivity_indicator);
        rp_guideactivity = findViewById(R.id.rp_guideactivity);

        initImageList();

        initIndicator();

        //addOnPageChangeListener 当页面变化的时候
        vp_guideactivity_guide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            //当当前的页面滑动的时候调用。
            // postion 当前的page是哪个
            // positionOffset 下一个页面拖出来的比例值
            // 下一个页面拖出来的像素值 positionOffsetPixels
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i(TAG," position="+position +" positionOffset="+positionOffset +" positionOffsetPixels="+positionOffsetPixels);

                //更改小红点的位置： 和 红点leftmargin的关系 0-0 1-40 2-80。
                //拿到小红点已经有的layoutparameter
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) rp_guideactivity.getLayoutParams();
                layoutParams.leftMargin = position*40 + (int)(40*positionOffset);
                rp_guideactivity.setLayoutParams(layoutParams);

            }

            //页面发生变化的时候，会传入当前是哪个page
            @Override
            public void onPageSelected(int position) {

                if (position==PageCount-1){
                    bt_guideactivity_enter.setVisibility(View.VISIBLE);
                }else{
                    bt_guideactivity_enter.setVisibility(View.INVISIBLE);
                }

                Log.i(TAG," onPageSelected="+position);
            }

            //就是viewpage当前的状态。

            // @see ViewPager#SCROLL_STATE_IDLE :静止
            // @see ViewPager#SCROLL_STATE_DRAGGING：拉着拖动的时候
            // @see ViewPager#SCROLL_STATE_SETTLING：松开手之后，回位的状态。
            @Override
            public void onPageScrollStateChanged(int state) {
                Log.i(TAG," state="+state);

            }
        });

        //如果显示了guide，可以加入一个flag到SP

        SharedPreferences config = getSharedPreferences("config", MODE_PRIVATE);
        SharedPreferences.Editor edit = config.edit();
        edit.putBoolean("isShowGuide",true);
        edit.commit();
    }

    private void initIndicator() {

        for (int i=0;i<PageCount;i++){

            View view = new View(this);
            //单位是像素
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20,20);
            if (i!=0){
                layoutParams.leftMargin=20;
            }
            view.setLayoutParams(layoutParams);

            view.setBackgroundResource(R.drawable.graypoint);
            ll_guideactivity_indicator.addView(view);

        }
    }

    private void initImageList() {
        /*imageViewList = new ArrayList<ImageView>();
        for (int i=0;i<3;i++){
            ImageView iv = new ImageView(this);
            iv.setBackgroundResource(imgResIds[i]);
            imageViewList.add(iv);
        }*/

        myPageInfoList = new ArrayList<MyPageInfo>();
        for (int i=0;i<PageCount;i++){
            ImageView iv = new ImageView(this);
            iv.setBackgroundResource(imgResIds[i]);

            MyPageInfo myPageInfo = new MyPageInfo();
            myPageInfo.pageIv=iv;
            myPageInfo.pageTilte="page"+i;
            myPageInfo.x=i;
            myPageInfoList.add(myPageInfo);
        }


    }


    class MyGuidePageAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return PageCount;
        }

        //view==object 默认写法
        //Determines whether a page View is associated with a specific key object
        // * as returned by {@link #instantiateItem(ViewGroup, int)}.
        // view:当前页面上显示的视图view
        // object: instantiateItem 的一个返回值。
        // 实现者需要去判断这两东西是不是有关联。
        @Override
        public boolean isViewFromObject(View view, Object object) {
            MyPageInfo myPageInfo = (MyPageInfo) object;
            return view==myPageInfo.pageIv;
        }

        //instantiate Item --》给一个position 去实例化一个item
        // 默认返回view
        @Override
        public Object instantiateItem(ViewGroup container, int position) {

           /* TextView tv = new TextView(GuideActivity.this);
            tv.setText("pager"+position);
            tv.setTextColor(Color.RED);*/
            //ImageView imageView = imageViewList.get(position);
            //区别于listview 的地方，不仅仅返回回去就行了。
            MyPageInfo myPageInfo = myPageInfoList.get(position);
            container.addView(myPageInfo.pageIv);
            return   myPageInfo;//super.instantiateItem(container, position);
        }

        //destroyItem 销毁。
        //把对应的page上的view 从contianer 移除去。
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            MyPageInfo myPageInfo = (MyPageInfo) object;
            container.removeView( myPageInfo.pageIv);
            // super.destroyItem(container, position, object);
        }
    }

    public void enterHome(View v){

        startActivity(new Intent(this,HomeActivity.class));
        finish();

    }
}
