package view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cskaoyan.zhao.a04newsappliction.R;

/**
 * Created by zhao on 2016/8/26.
 */
public class RefreshListView extends ListView {
    private static final String TAG = "RefreshListView";
    private View refresh_header_view;
    private int measuredHeight;


    private  final int INITSTATE=0;
    private  final int NEED_RELEASE=1;
    private  final int REFRESHING=2;

    private int current_state=INITSTATE;
    private TextView tv_refreshlistviewheader_hint;
    private ImageView iv_refreshlistviewheader_img;
    private RotateAnimation rotateAnimation;
    private ProgressBar pb_refreshlistviewheader_freshing;

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

        tv_refreshlistviewheader_hint = (TextView) refresh_header_view.findViewById(R.id.tv_refreshlistviewheader_hint);

        iv_refreshlistviewheader_img = (ImageView) refresh_header_view.findViewById(R.id.iv_refreshlistviewheader_img);

         pb_refreshlistviewheader_freshing = (ProgressBar) refresh_header_view.findViewById(R.id.pb_refreshlistviewheader_freshing);
        refresh_header_view.measure(0,0);
        measuredHeight = refresh_header_view.getMeasuredHeight();
        refresh_header_view.setPadding(0,-measuredHeight,0,0);
        addHeaderView(refresh_header_view);

        rotateAnimation = new RotateAnimation(0,180, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setFillAfter(true);

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

                    if (-measuredHeight+(int)dy>0&&current_state!=NEED_RELEASE){
                        //就已经完全拉出来了。
                        current_state=NEED_RELEASE;
                        Log.i(TAG,"状态变为需要松手");
                        updateHeaderView();
                    }
                }
                break;


            case  MotionEvent.ACTION_UP:

                //如果已经完全拉出来了，就让他刷新
                if (current_state==NEED_RELEASE){
                    current_state=REFRESHING;
                    Log.i(TAG,"状态变为正在刷新");
                    refresh_header_view.setPadding(0,0,0,0);

                    updateHeaderView();
                }

                //如果只拉出来一点点，就让他弹回去，恢复到原位（隐藏）
                if (current_state==INITSTATE){
                    Log.i(TAG,"状态变为初始状态，回到原位");
                    refresh_header_view.setPadding(0,-measuredHeight ,0,0);
                }

                break;


        }


        return super.onTouchEvent(ev);
    }

    private void updateHeaderView() {

        switch (current_state){
            case NEED_RELEASE:
                tv_refreshlistviewheader_hint.setText("松开手，就可以刷新了");
                iv_refreshlistviewheader_img.setAnimation(rotateAnimation);
                rotateAnimation.start();
                break;

            case REFRESHING:
                tv_refreshlistviewheader_hint.setText("正在刷新");
                //iv没有消失
                //rotateAnimation.cancel();
                iv_refreshlistviewheader_img.clearAnimation();
                iv_refreshlistviewheader_img.setVisibility(INVISIBLE);
                pb_refreshlistviewheader_freshing.setVisibility(VISIBLE);
                break;
        }
    }
}
