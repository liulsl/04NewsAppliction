package menupage;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import bean.Categories;
import page.BasePage;

/**
 * Created by zhao on 2016/8/24.
 */
public class NewsMenuPage extends BaseMenuPage {


    public NewsMenuPage(Activity mActivitym, Categories.MenuDataInfo menuDataInfo) {
        super(mActivitym, menuDataInfo);
    }

    @Override
    public View initView() {

        TextView textView = new TextView(mActivity);
        textView.setText(  menuDataInfo.title);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(30);
        return textView;
    }

    @Override
    public void initData() {

    }
}
