package newsdetailmenupage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cskaoyan.zhao.a04newsappliction.R;
import com.cskaoyan.zhao.a04newsappliction.ShowNewsActivity;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import bean.Categories;
import bean.NewsDetail;
import constants.Const;
import fragment.LeftMenuFragment;
import view.RefreshListView;

/**
 * Created by zhao on 2016/8/25.
 */
public class NewsDetialPage {
    //List<Integer> readlist= new ArrayList<Integer>();

    public View mNewsDetailView;
    Activity mActivity;
    public static final String TAG = "NewsDetialPage";
    List<NewsDetail.DataBean.NewsBean> listDataSet;

    Categories.childrenInfo newsDetailInfo;
    private NewsDetail newsDetail;
    private RefreshListView lv_newsDetailpage_newslist;
    private ViewPager vp_newsdetail_topnews;
    private TextView tv_newsdetail_topnewsTitle;
    private CirclePageIndicator indicator_topnews;
    private View listHeader;
    private MyNewsListAdapter myNewsListAdapter;

    SharedPreferences sp;
    public NewsDetialPage(Activity mActivity,
                          Categories.childrenInfo  newsDetailInfo     )
    {
        this.mActivity = mActivity;
        this.newsDetailInfo=newsDetailInfo;

        this.sp= mActivity.getSharedPreferences("config", Context.MODE_PRIVATE);

        mNewsDetailView = initView();
        initData();
    }
    public  View initView(){

        Log.i(TAG,"initView()");
        View v =View.inflate(mActivity, R.layout.newsdetailpage,null);

        lv_newsDetailpage_newslist = (RefreshListView) v.findViewById(R.id.lv_newsDetailpage_newslist);

        lv_newsDetailpage_newslist.setMyRefreshListener(new RefreshListView.MyRefreshListener() {
            @Override
            public void onRefreshing() {
                //重新去加载该page对应的url，去获取服务器的最新数据
                initData() ;
            }


            //需要去加载更多的新闻
            @Override
            public void onLoadMore() {


                String more = newsDetail.data.getMore();

                if (!more.isEmpty()){

                     String moreUrl  = Const.SERVER_ADDR+more;

                     HttpUtils httpUtils = new HttpUtils();

                     httpUtils.send(HttpRequest.HttpMethod.GET, moreUrl, new RequestCallBack<String>() {
                        @Override
                        public void onSuccess(ResponseInfo<String> responseInfo) {

                            Log.i(TAG,"loadmore data="+responseInfo.result) ;

                            Gson gson = new Gson();
                            newsDetail =gson.fromJson(responseInfo.result,NewsDetail.class);

                            List<NewsDetail.DataBean.NewsBean> news = newsDetail.getData().getNews();
                            listDataSet.addAll(news);
                            myNewsListAdapter.notifyDataSetChanged();

                            lv_newsDetailpage_newslist.onLoadMoreComplete();

                        }
                        @Override
                        public void onFailure(HttpException e, String s) {
                            lv_newsDetailpage_newslist.onLoadMoreComplete();
                            Toast.makeText(mActivity,"连接失败，稍后再试",Toast.LENGTH_SHORT).show();

                        }
                    });


                }else{

                    Toast.makeText(mActivity,"没有更多数据了，休息一会",Toast.LENGTH_SHORT).show();
                    lv_newsDetailpage_newslist.onLoadMoreComplete();
                }

            }
        });

        lv_newsDetailpage_newslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.i(TAG,"onItemClick  position="+position);
                if (position>=2){
                    NewsDetail.DataBean.NewsBean newsBean = listDataSet.get(position-2);

                    String url = newsBean.getUrl();
                    Intent intent = new Intent(mActivity, ShowNewsActivity.class);
                    intent.putExtra("url",url);

                    mActivity.startActivity(intent);

                    //在这里记录下用户看过的新闻。记录当前news的一个id
                    int newsId = newsBean.getId();

                     // readlist.add(id1);
                    //35311,35312 35313
                    //String read="35311,35312,35313,35311,35311,35311,35311,"

                    String readlist = sp.getString("readlist", "");

                    //先看看是否已经记录过该news 已读,如果已经保存过了，则无需做任何事情，反之，则需要保存到sp中
                    boolean contains = readlist.contains(newsId + "");
                    if (!contains){
                        readlist = readlist+newsId+",";
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putString("readlist",readlist);
                        edit.commit();

                        //方法1
                        //myNewsListAdapter.notifyDataSetChanged();

                        //方法2
                        //这是想修改下当前点击的曾item的textview的颜色。
                        TextView tv_listviewnewsdetail_title = (TextView) view.findViewById(R.id.tv_listviewnewsdetail_title);
                        tv_listviewnewsdetail_title.setTextColor(Color.GRAY);

                    }



                }

            }
        });



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

        String url = Const.SERVER_ADDR+ newsDetailInfo.url;
        Log.i(TAG,"initData");

        getDataFromServer(url);

    }


    public void getDataFromServer(String urlparm){

        String url =urlparm;

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                //刷新完成
                lv_newsDetailpage_newslist.onRefreshComplete();
                Log.i(TAG,responseInfo.result);
                parseJsonString(responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.i(TAG,s);
                lv_newsDetailpage_newslist.onRefreshComplete();
                Toast.makeText(mActivity,"加载失败，请稍后再试！",Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void parseJsonString(String result) {


        //Gson
        Gson gson = new Gson();
        newsDetail = gson.fromJson(result, NewsDetail.class);
        //给listview填充数据

        listDataSet=newsDetail.data.getNews();
        myNewsListAdapter = new MyNewsListAdapter();
        lv_newsDetailpage_newslist.setAdapter(myNewsListAdapter);

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
            return listDataSet.size();
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
             bitmapUtils.display(iv_listviewnewsdetail_img,listDataSet.get(position).getListimage());
            tv_listviewnewsdetail_title.setText(listDataSet.get(position).getTitle());
            tv_listviewnewsdetail_pubtime.setText(listDataSet .get(position).getPubdate());


            //点击的时候不会整个listview都刷新，所以点击的时候这里用不上了。
            // 但是这里在下一次重新初始化listview的时候，就会发生作用。用户重新进来还是可以看到哪些新闻上次已读

            int id = listDataSet.get(position).getId();
            //判断一下这id是否在config里，如果已经包含了，就说明这个新闻已经被user 读过 了。
            String readlist = sp.getString("readlist", "");
            boolean contains = readlist.contains(id + "");
            //读过的新闻，去显示灰色
            if (contains){
                tv_listviewnewsdetail_title.setTextColor(Color.GRAY);
            }


            return view;
        }
    }
}
