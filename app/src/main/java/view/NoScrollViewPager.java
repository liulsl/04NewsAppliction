package view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by zhao on 2016/8/23.
 */
public class NoScrollViewPager extends ViewPager {

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    // 禁用viewpager的左右滑动
    // true
    // false
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return  false; // super.onTouchEvent(ev);
    }
}
