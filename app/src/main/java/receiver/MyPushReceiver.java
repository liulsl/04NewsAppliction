package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

public class MyPushReceiver extends BroadcastReceiver {
    private static final String TAG = "MyPushReceiver";

    public MyPushReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
       // throw new UnsupportedOperationException("Not yet implemented");

        Log.i(TAG,intent.getAction());
        if (intent.getAction().equals("cn.jpush.android.intent.NOTIFICATION_OPENED")){


            Bundle bundle = intent.getExtras();
            String json = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Log.i(TAG,json);

            try {
                JSONObject  jsonObj = new JSONObject(json);
                String url = jsonObj.getString("url");

                Log.i(TAG,intent.getAction());
                Intent intent1 = new Intent();
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.setAction("com.cskaoyan.shownews");
                intent1.putExtra("url",url);
                context.startActivity(intent1);
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    
    }
}
