package view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by zhao on 2016/8/25.
 */
public class TopNewsViewPager extends ViewPager {
    private MotionEvent ev;
    private float startx;
    private float endx;
    private float starty;
    private float endy;

    public TopNewsViewPager(Context context) {
        super(context);
    }

    public TopNewsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        //哪些情况需要父控件去处理，可以拦截
        //哪些情况不需要父控件处理，不要拦截
        //在TopNewsViewPager 发现
        //case 1：
        // 在最后一个page上，
        // 右滑，需要自己处理
        // getParent().requestDisallowInterceptTouchEvent(true);
        // 左滑，让父控件去处理
        // getParent().requestDisallowInterceptTouchEvent(false);

        //case 2：
        //其他中间的page，左右滑动都是自己处理
        // getParent().requestDisallowInterceptTouchEvent(true);

        //case 3：
        // 第一个page上，
        // 右滑，需要父控件去处理
        // getParent().requestDisallowInterceptTouchEvent(false);
        // 左滑，需要自己去处理
       //  getParent().requestDisallowInterceptTouchEvent(true);


        switch (ev.getAction()){

            case MotionEvent.ACTION_DOWN:

                startx = ev.getRawX();
                starty = ev.getRawY();

                break;
            case MotionEvent.ACTION_MOVE:



                endx = ev.getRawX();
                endy =ev.getRawY();

                float dx = Math.abs(endx-startx);
                float dy = Math.abs(endy-starty);

                if (dx>dy){

                    if (endx-startx>0){//往右滑
                        // 在最后一个page上，需要请求父控件不要拦截，自己去处理
                        if (getCurrentItem()!= 0)//最后一个page page总数去减1 getChildCount()
                        {
                            getParent().requestDisallowInterceptTouchEvent(true);
                        }
                    }else{//往左滑
                        if (getCurrentItem()==0){
                            getParent().requestDisallowInterceptTouchEvent(true);
                        }
                    }
                }else{ //竖直方向，listview去处理上下滑动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }



                break;
            case MotionEvent.ACTION_UP:

                break;

        }


        return super.dispatchTouchEvent(ev);
    }
}
