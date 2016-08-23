package page;

import android.app.Activity;
import android.view.Gravity;
import android.widget.TextView;

import com.cskaoyan.zhao.a04newsappliction.HomeActivity;

/**
 * Created by zhao on 2016/8/23.
 */
public class GovernmentPage extends BasePage {


    public GovernmentPage(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {

        tv_pageview_pageTitle.setText("政务");

        TextView textView = new TextView(mActivity);
        textView.setText("政务中间的内容");
        textView.setGravity(Gravity.CENTER);
        ll_viewpage_content.addView(textView);


    }
}
