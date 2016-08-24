package menupage;

import android.app.Activity;
import android.view.View;

import bean.Categories;

/**
 * Created by zhao on 2016/8/24.
 */
public abstract class BaseMenuPage {

    public Activity mActivity;
    public View mMenuPageView;
    public Categories.MenuDataInfo menuDataInfo;

    public BaseMenuPage(Activity mActivity,Categories.MenuDataInfo menuDataInfo) {
        this.mActivity = mActivity;
        this.menuDataInfo = menuDataInfo;
        mMenuPageView=initView();

    }

    public  abstract View initView();

    public  abstract  void initData();
}
