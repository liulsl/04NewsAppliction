package page;

import android.app.Activity;
import android.view.Gravity;
import android.widget.TextView;

/**
 * Created by zhao on 2016/8/23.
 */
public class SettingPage extends BasePage {


    public SettingPage(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {

        tv_pageview_pageTitle.setText("设置");

        TextView textView = new TextView(mActivity);
        textView.setText("设置中间的内容");
        textView.setGravity(Gravity.CENTER);
        ll_viewpage_content.addView(textView);
    }
}
