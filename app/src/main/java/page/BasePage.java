package page;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cskaoyan.zhao.a04newsappliction.R;

/**
 * Created by zhao on 2016/8/23.
 */
public abstract class BasePage {
    public View mPageView; //就是viewpager里面的每一个page
    public Activity mActivity;
    protected TextView tv_pageview_pageTitle;
    protected LinearLayout ll_viewpage_content;
    protected ImageButton bt_pageview_leftbuttum;
    protected ImageButton bt_pageview_rightbuttum;

    public BasePage(Activity activity ) {
        mActivity =activity;
        initView();
        initData(); //去修改当前这个page的tv_pageview_pageTitle等信息。
    }

    public abstract void initData() ;

    private void initView() {
        mPageView =   View.inflate(mActivity, R.layout.page_content, null);
        tv_pageview_pageTitle = (TextView) mPageView.findViewById(R.id.tv_pageview_pageTitle);
        ll_viewpage_content = (LinearLayout) mPageView.findViewById(R.id.ll_viewpage_content);
        bt_pageview_leftbuttum = (ImageButton) mPageView.findViewById(R.id.bt_pageview_leftbuttum);
        bt_pageview_rightbuttum = (ImageButton) mPageView.findViewById(R.id.bt_pageview_rightbuttum);

    }

    public void setPageTitle(String title){
        tv_pageview_pageTitle.setText(title);
    }
}
