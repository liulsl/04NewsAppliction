package bean;

import java.util.ArrayList;

/**
 * Created by zhao on 2016/8/25.
 */
public class NewsDetail {

    int ret ;
    public NewsInfo data;

    public class NewsInfo{
        public String countcommenturl;
        public  String more;
        public   String title;

      public   ArrayList<CommonNews>  news;
    }


    public class CommonNews{
       public int id;
       public String listImage;
       public String pubdate;
       public String title;
       public String url;
    }
}
