package bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhao on 2016/8/23.
 */

//1. 类里出现的成员变量的命名 需要与json字符串里的 key 一样。如果写错了，不一样会怎样？不会报错，只是不能找到同名的key去给他赋值。
//2. 如果你的bean里面多写了某个字段，也不会报错。不会报错，只是不能找到同名的key去给他赋值。
//3. 如果你的bean里少了某个字段，也不会报错，只是无法得到该字段的值。


public class Categories {

    public int retcode;

    public  ArrayList<MenuDataInfo> data;


    public class childrenInfo{

      public  int id;
      public  String title;
      public  int type;
      public  String url;
    }

    public class MenuDataInfo{
       public   int id;
       public  String title;
       public  int type;
       public  String url;
       public  String url1;
       public  List<childrenInfo> children;

        @Override
        public String toString() {
            return "MenuDataInfo{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", type=" + type +
                    ", url='" + url + '\'' +
                    ", url1='" + url1 + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "Categories{" +
                "retcode=" + retcode +
                ", data=" + data +
                '}';
    }
}
