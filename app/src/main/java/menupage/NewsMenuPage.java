package menupage;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cskaoyan.zhao.a04newsappliction.R;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import bean.Categories;
import newsdetailmenupage.NewsDetialPage;
import page.BasePage;

/**
 * Created by zhao on 2016/8/24.
 */
public class NewsMenuPage extends BaseMenuPage {


    private ViewPager vp_newsmenupage_content;

    List<TextView> newsmenupagelist;
    private TabPageIndicator indicator_newmenupage_title;

    public NewsMenuPage(Activity mActivitym, Categories.MenuDataInfo menuDataInfo) {
        super(mActivitym, menuDataInfo);
    }

    @Override
    public View initView() {

       /* TextView textView = new TextView(mActivity);
        textView.setText(  menuDataInfo.title);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(30);*/

        View view = View.inflate(mActivity, R.layout.newsmenupage, null);

        vp_newsmenupage_content =
                (ViewPager) view.findViewById(R.id.vp_newsmenupage_content);

        indicator_newmenupage_title = (TabPageIndicator) view.findViewById(R.id.indicator_newmenupage_title);
        //IllegalStateException: ViewPager does not have adapter instance.
        //indicator_newmenupage_title.setViewPager(vp_newsmenupage_content);

        return view;
    }

    @Override
    public void initData() {

        /*newsmenupagelist = new ArrayList<>();

        for(int i =0;i< menuDataInfo.children.size();i++){

            TextView textView = new TextView(mActivity);
            Categories.childrenInfo childrenInfo = menuDataInfo.children.get(i);
            textView.setText(childrenInfo.title);
            textView.setTextColor(Color.CYAN);
            textView.setTextSize(50);
            textView.setGravity(Gravity.CENTER);
            newsmenupagelist.add(textView);

        }*/

        vp_newsmenupage_content.setAdapter(new MyNewsMenuPageAdapter());

        //注意，Indicator的使用，必须要在vp设置adapter之后，才能关联到vp
        indicator_newmenupage_title.setViewPager(vp_newsmenupage_content);

    }

    class MyNewsMenuPageAdapter extends PagerAdapter{

        @Override
        public CharSequence getPageTitle(int position) {
            return     menuDataInfo.children.get(position).title; //super.getPageTitle(position);
        }

        @Override
        public int getCount() {
            return  menuDataInfo.children.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

           // TextView textView = newsmenupagelist.get(position);
            NewsDetialPage newsDetialPage = new NewsDetialPage(mActivity, menuDataInfo.children.get(position));
            container.addView(newsDetialPage.mNewsDetailView);
            return newsDetialPage.mNewsDetailView ;//super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
            // super.destroyItem(container, position, object);
        }
    }
}
