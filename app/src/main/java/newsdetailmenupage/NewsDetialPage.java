package newsdetailmenupage;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cskaoyan.zhao.a04newsappliction.R;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.viewpagerindicator.CirclePageIndicator;

import bean.Categories;
import bean.NewsDetail;
import fragment.LeftMenuFragment;

/**
 * Created by zhao on 2016/8/25.
 */
public class NewsDetialPage {

    public View mNewsDetailView;
    Activity mActivity;
    public static final String TAG = "NewsDetialPage";

    Categories.childrenInfo newsDetailInfo;
    private NewsDetail newsDetail;
    private ListView lv_newsDetailpage_newslist;
    private ViewPager vp_newsdetail_topnews;
    private TextView tv_newsdetail_topnewsTitle;
    private CirclePageIndicator indicator_topnews;
    private View listHeader;

    public NewsDetialPage(Activity mActivity,
                          Categories.childrenInfo  newsDetailInfo     )
    {
        this.mActivity = mActivity;
        this.newsDetailInfo=newsDetailInfo;

        mNewsDetailView = initView();
        initData();
    }
    public  View initView(){

        View v =View.inflate(mActivity, R.layout.newsdetailpage,null);

        lv_newsDetailpage_newslist = (ListView) v.findViewById(R.id.lv_newsDetailpage_newslist);


        listHeader = View.inflate(mActivity, R.layout.item_listview_header, null);
        vp_newsdetail_topnews = (ViewPager) listHeader.findViewById(R.id.vp_newsdetail_topnews);
        tv_newsdetail_topnewsTitle = (TextView) listHeader.findViewById(R.id.tv_newsdetail_topnewsTitle);
        // lv_newsDetailpage_newslist.setAdapter();
        indicator_topnews = (CirclePageIndicator) listHeader.findViewById(R.id.indicator_topnews);

        //把整个headerview放到listivew里面。最前面
        lv_newsDetailpage_newslist.addHeaderView(listHeader);
        return  v;
    }
    private void initData() {

        //http://localhost:8080/zhbj/10007/list_1.json

        String url ="http://10.0.2.2:8080/zhbj"+ newsDetailInfo.url;
        getDataFromServer(url);

    }


    public void getDataFromServer(String urlparm){

        String url =urlparm;

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                Log.i(TAG,responseInfo.result);
                parseJsonString(responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.i(TAG,s);

            }
        });


    }

    private void parseJsonString(String result) {


        //Gson
        Gson gson = new Gson();
        newsDetail = gson.fromJson(result, NewsDetail.class);
        //给listview填充数据
        lv_newsDetailpage_newslist.setAdapter(new MyNewsListAdapter());

        //给ViewPager填充数据
        vp_newsdetail_topnews.setAdapter(new MyTopNewsViewPagerAdapter());
        indicator_topnews.setViewPager(vp_newsdetail_topnews);
        vp_newsdetail_topnews.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tv_newsdetail_topnewsTitle.setText(newsDetail.getData().getTopnews().get(position).getTitle());
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    class MyTopNewsViewPagerAdapter extends PagerAdapter{


        BitmapUtils bitmapUtils;
        public MyTopNewsViewPagerAdapter() {

             bitmapUtils = new BitmapUtils(mActivity);
        }

        @Override
        public int getCount() {
            return newsDetail.getData().getTopnews().size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView imageView = new ImageView(mActivity);

            //把图片裁剪为合适适合父控件的大小。（可能不会等比例）
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            String imagUrl = newsDetail.getData().getTopnews().get(position).getTopimage();
            Log.i(TAG,"imgurl="+imagUrl);
            bitmapUtils.display(imageView,imagUrl);

            container.addView(imageView);
            return imageView;//super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
           // super.destroyItem(container, position, object);
        }
    }

    class MyNewsListAdapter extends BaseAdapter{


        BitmapUtils bitmapUtils;

        public MyNewsListAdapter() {
              bitmapUtils = new BitmapUtils(mActivity);

        }


        @Override
        public int getCount() {
            return newsDetail.data.getNews().size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            View view = View.inflate(mActivity, R.layout.item_listview_newsdetail, null);
            ImageView iv_listviewnewsdetail_img = (ImageView) view.findViewById(R.id.iv_listviewnewsdetail_img);
            TextView tv_listviewnewsdetail_title = (TextView) view.findViewById(R.id.tv_listviewnewsdetail_title);
            TextView tv_listviewnewsdetail_pubtime = (TextView) view.findViewById(R.id.tv_listviewnewsdetail_pubtime);


            //加载数据
             bitmapUtils.display(iv_listviewnewsdetail_img,newsDetail.data.getNews().get(position).getListimage());
            tv_listviewnewsdetail_title.setText(newsDetail.data.getNews().get(position).getTitle());
            tv_listviewnewsdetail_pubtime.setText(newsDetail.data.getNews().get(position).getPubdate());

            return view;
        }
    }
}
