package application;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by zhao on 2016/8/30.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
