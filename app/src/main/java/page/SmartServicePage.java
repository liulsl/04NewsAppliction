package page;

import android.app.Activity;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by zhao on 2016/8/23.
 */
public class SmartServicePage extends BasePage {


    public SmartServicePage(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {

        tv_pageview_pageTitle.setText("智慧服务");

        TextView textView = new TextView(mActivity);
        textView.setText("智慧服务中间的内容");
        textView.setGravity(Gravity.CENTER);
        ll_viewpage_content.addView(textView);
    }
}
