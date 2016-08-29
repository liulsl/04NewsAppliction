package utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * Created by zhao on 2016/8/29.
 */

//第一级缓存
public class MemoryCacheUtils {
    private static final String TAG = "MemoryCacheUtils";
    //static  HashMap<String,Bitmap>  memCache= new HashMap<String, Bitmap>();
    //改进版(防止OOM)
    // static  HashMap<String,SoftReference<Bitmap>>  memCache= new HashMap<String, SoftReference<Bitmap>>();
    // 改进第二版

       //Lru: Leaset Recently Used
       static LruCache<String,Bitmap> memCache= new LruCache<String,Bitmap>(2000000){
           @Override
           protected int sizeOf(String key, Bitmap value) {
                int size =   value.getByteCount(); // super.sizeOf(key, value);
                return  size;
           }
       };

    //使用这种写法，我们的所有的bitmap都维护在memCache这个集合里。GC的时候，任何bitmap都无法释放。
    //随着加载的图片越来越多，该集合中维护的bitmap可能会造成OOM

    //强引用，java中的引用默认都是强引用。（我们之前讲的垃圾回收机制。是基于强引用）
             //对于GC回收来说，如果该对象有另一个对象的到它的强引用，则该对象无法回收。
    //软引用，当GC回收内存的时候，一般不会去回收软引用，但是当内存不够用的时候，就可以去回收软引用的内存。
    //弱引用，GC回收的时候，即便是内存够的时候，也有可能回收。
    //虚引用 （一般用不上。）对于引用对象之间的关系，没有任何影响。配合引用队列使用。每次要回收这种对象，会
    //把这个对象加入到一个引用队列。程序可以通过遍历这个引用队列，来看队列中是否有对应的虚引用。如果有说明马上要被回收。




    public static void saveBitmapToMem(Bitmap bitmap,String url){


       // memCache.put(url,new SoftReference<Bitmap>(bitmap));

        Bitmap put = memCache.put(url, bitmap);
        //Log.i(TAG,""+put);
    }

    public static  Bitmap getBitmapFromMem(String url){

       // SoftReference<Bitmap> bitmapSoftReference = memCache.get(url);

      /*  if (bitmapSoftReference!=null){
            Bitmap bitmap = bitmapSoftReference.get();
            return bitmap;
        }*/

        Bitmap bitmap = memCache.get(url);

        if (bitmap!=null)
            Log.i(TAG,bitmap.toString());
        else
            Log.i(TAG,"bitmap is null");

        return  bitmap;

    }

}
