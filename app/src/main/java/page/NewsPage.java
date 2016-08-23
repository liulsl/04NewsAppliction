package page;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by zhao on 2016/8/23.
 */
public class NewsPage extends BasePage {


    public NewsPage(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {

        tv_pageview_pageTitle.setText("新闻");

        TextView textView = new TextView(mActivity);
        textView.setText("新闻中间的内容");
        textView.setGravity(Gravity.CENTER);
        ll_viewpage_content.addView(textView);

        bt_pageview_leftbuttum.setVisibility(View.VISIBLE);
        bt_pageview_rightbuttum.setVisibility(View.VISIBLE);

    }
}
