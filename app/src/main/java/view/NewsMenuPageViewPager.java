package view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by zhao on 2016/8/25.
 */
public class NewsMenuPageViewPager extends ViewPager {
    private static final String TAG ="NewsMenuPageViewPager" ;

    public NewsMenuPageViewPager(Context context) {
        super(context);
    }

    public NewsMenuPageViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //请求父控件不要拦截。

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //父控件任何情况下都会给到子控件来处理


        //本质上是想去让slidingmenu 在第0个page上 可以滑动，在第0个之外，不能滑动

        //我们虽然想处理，但是我们应该是
        // 只有从第1个之后才处理
        // int currentItem = getCurrentItem();
        /*if (currentItem!=0){
            getParent().requestDisallowInterceptTouchEvent(true);
        }else { // 第0个其实不想处理
            getParent().requestDisallowInterceptTouchEvent(false);
        }*/



        Log.i(TAG,"dispatchTouchEvent()");
        return super.dispatchTouchEvent(ev);
    }
}
