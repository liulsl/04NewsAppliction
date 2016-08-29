package utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zhao on 2016/8/29.
 */

//第二级缓存 文件缓存，
public class FileCacheUtils {


    private static final String TAG ="FileCacheUtils" ;

    public static void saveBitmapToFile(Bitmap bitmap, String url, Activity activity){

        //以它作为文件名  MD5
        String md5FileName = MD5Utils.getMD5(url);
        //
        File file = new File(activity.getCacheDir(),md5FileName);
        try {
            FileOutputStream fos =new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
            fos.close();

            //在这里可有顺便放到内存保留起来
            MemoryCacheUtils.saveBitmapToMem(bitmap,url);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static Bitmap getBitmapFromFile(String url,Activity activity){

        Bitmap bitmap =null;
        String md5FileName = MD5Utils.getMD5(url);
        File file = new File(activity.getCacheDir(),md5FileName);
        if (file.exists()){
              Log.i(TAG," 有文件缓存,直接从本地缓存获取数据") ;
              bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
              MemoryCacheUtils.saveBitmapToMem(bitmap,url);

            return  bitmap;

        }else{
            Log.i(TAG,"没有文件缓存") ;
           return  bitmap;
        }

    }
}
