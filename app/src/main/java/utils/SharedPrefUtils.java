package utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by zhao on 2016/8/29.
 */
public class SharedPrefUtils {

    public static void saveJsonToCache(String key, String value, Context ctx){

        SharedPreferences sp = ctx.getSharedPreferences("Jsoncache",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key,value);
        edit.commit();
    }

    public static  String getJsonFromCache(String key,Context ctx){
        SharedPreferences sp = ctx.getSharedPreferences("Jsoncache",Context.MODE_PRIVATE);
        String jsonString = sp.getString(key, "");
        return  jsonString;

    }

}
