package com.cskaoyan.zhao.a04notificationdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG ="MyReceiver" ;

    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        Log.i(TAG,intent.getAction());
        Intent intent1 = new Intent();
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent1.setAction("com.cskaoyan.shownews");
        intent1.putExtra("url","http://www.baidu.com");
        context.startActivity(intent1);
     }
}
