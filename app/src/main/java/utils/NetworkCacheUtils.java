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

//第三级缓存，网络缓存。
public class NetworkCacheUtils {
    public static final String TAG = "NetworkCacheUtils";


    public static void  getBitmapFromNet(ImageView iv, String  imageUrl , Activity activity){


        final Activity activity_final= activity;
        new AsyncTask<Object ,Integer,Bitmap>(){

            ImageView imageview;
            String urlString;

            @Override
            protected Bitmap doInBackground(Object... params) {

                Log.i(TAG,"从网络缓存获取！");
                Bitmap bitmap=null;
                imageview = (ImageView) params[0];
                urlString = (String) params[1];

                try {
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(5000);
                    connection.setConnectTimeout(5000);
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode==200){
                        InputStream inputStream = connection.getInputStream();
                        bitmap = BitmapFactory.decodeStream(inputStream);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {

                if (bitmap!=null){
                    imageview.setImageBitmap(bitmap);
                    //把bitmap图片的数据放到本地，生成二级缓存
                    FileCacheUtils.saveBitmapToFile(bitmap,urlString,activity_final);
                }

                super.onPostExecute(bitmap);
            }
        }.execute(iv, imageUrl);


    }

}
