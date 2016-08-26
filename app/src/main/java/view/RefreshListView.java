package view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.cskaoyan.zhao.a04newsappliction.R;

/**
 * Created by zhao on 2016/8/26.
 */
public class RefreshListView extends ListView {
    private static final String TAG = "RefreshListView";
    private View refresh_header_view;
    private int measuredHeight;

    public RefreshListView(Context context) {
        super(context);

        initView(context);
    }

    private void initView( Context context) {

      /*  TextView tv = new TextView(context);
        tv.setText("refresh Header");
        tv.setTextSize(20);
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundColor(Color.RED);*/

        refresh_header_view = View.inflate(context, R.layout.refresh_listview_header, null);


        refresh_header_view.measure(0,0);
        measuredHeight = refresh_header_view.getMeasuredHeight();
        refresh_header_view.setPadding(0,-measuredHeight,0,0);
        addHeaderView(refresh_header_view);

    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }


    float startX;
    float startY;

    float endX;
    float endY;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){

            case  MotionEvent.ACTION_DOWN:

                startX= ev.getRawX();
                startY=ev.getRawY();

                Log.i(TAG,"startX="+startX+" startY="+startY);
                break;


            case  MotionEvent.ACTION_MOVE:
                if (startX==0) startX= ev.getRawX();
                if (startY==0) startY= ev.getRawY();
                Log.i(TAG,"startX="+startX+" startY="+startY);

                endX= ev.getRawX();
                endY =ev.getRawY();
                Log.i(TAG,"endX="+endX+" endY="+endY);

                float dx =Math.abs(startX-endX);
                float dy =Math.abs(startY-endY);
                Log.i(TAG,"dx="+dx+" dy="+dy);

                if (dx<dy){

                    if (endY>startY){//下滑

                        Log.i(TAG,"下滑");

                        refresh_header_view.setPadding(0,-measuredHeight+(int)dy,0,0);

                    }



                }



                break;


            case  MotionEvent.ACTION_UP:
                break;


        }


        return super.onTouchEvent(ev);
    }
}
