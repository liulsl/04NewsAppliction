package utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zhao on 2016/8/29.
 */
public class MyBitmapUtils {


    private static final String TAG = "MyBitmapUtils";
    Activity mactivity;

    public MyBitmapUtils(Activity mactivity) {
        this.mactivity = mactivity;
    }

    //从网络上去获取图片，并显示在imageview上
    public void display(final ImageView iv, String   imageUrl){


        //imageUrl
        //先看看内存中的集合中是否有数据，如果有，就直接使用内存缓存
        Bitmap bitmapFromMem = MemoryCacheUtils.getBitmapFromMem(imageUrl);

        if (bitmapFromMem!=null){
            Log.i(TAG,"使用内存缓存的数据");
            iv.setImageBitmap(bitmapFromMem);
            //有内存缓存，则直接从内存获取数据，获取完毕之后直接return

            return;
        }


        // 先看看文件缓存 有没有数据，如果有 就直接使用文件缓存
        Bitmap bitmapFromFile = FileCacheUtils.getBitmapFromFile(imageUrl, mactivity);
        if (bitmapFromFile!=null){
            //有本地缓存，则直接从本地获取数据，获取完毕之后直接return
            iv.setImageBitmap(bitmapFromFile);
            return;
        }

        // 如果没有在去访问网络，使用网络缓存
        NetworkCacheUtils.getBitmapFromNet(iv, imageUrl,mactivity);


    };

}
