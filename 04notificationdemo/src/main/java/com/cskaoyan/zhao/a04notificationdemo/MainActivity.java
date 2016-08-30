package com.cskaoyan.zhao.a04notificationdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void showNotification(View v){

        Notification.Builder builder = new Notification.Builder(this);

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("this is my notification title");
        builder.setContentText("this is my notification content");


        //方法1
       /* Intent intent = new Intent();
        intent.setAction("com.cskaoyan.shownews");
        intent.putExtra("url","http://www.baidu.com");
        PendingIntent pendingIntent = PendingIntent.getActivity(this,100,intent,PendingIntent.FLAG_ONE_SHOT);
*/
        //方法2：

        Intent intent = new Intent();
        intent.setAction("com.cskaoyan.sendbroadcast");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,100,intent,PendingIntent.FLAG_ONE_SHOT);


        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);


        Notification notification = builder.build();
        //notification.flags=Notification.FLAG_NO_CLEAR;

        // Notification notification = new Notification()
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(1000,notification);
    }

}
