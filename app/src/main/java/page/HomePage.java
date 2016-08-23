package page;

import android.app.Activity;
import android.view.Gravity;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cskaoyan.zhao.a04newsappliction.HomeActivity;

/**
 * Created by zhao on 2016/8/23.
 */
public class HomePage extends BasePage {


    public HomePage(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {

        tv_pageview_pageTitle.setText("首页");


        TextView textView = new TextView(mActivity);
        textView.setText("首页中间的内容");
        textView.setGravity(Gravity.CENTER);
        ll_viewpage_content.addView(textView);


        /*HomeActivity homeActivity = (HomeActivity) mActivity;
        homeActivity.setSlidingMenuEnable(false);*/
    }
}
