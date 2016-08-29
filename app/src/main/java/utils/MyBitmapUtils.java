package utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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


    Activity mactivity;

    public MyBitmapUtils(Activity mactivity) {
        this.mactivity = mactivity;
    }

    //从网络上去获取图片，并显示在imageview上
    public void display(final ImageView iv, String  listimageUrl){


        new AsyncTask<Object ,Integer,Bitmap>(){

            ImageView imageview;
            String urlString;

            @Override
            protected Bitmap doInBackground(Object... params) {

                Bitmap bitmap=null;
                 imageview = (ImageView) params[0];
                urlString = (String) params[1];

                try {
                    URL url = new URL(urlString);
                    HttpURLConnection  connection = (HttpURLConnection) url.openConnection();
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
                }

                super.onPostExecute(bitmap);
            }
        }.execute(iv,listimageUrl);



    };

}
