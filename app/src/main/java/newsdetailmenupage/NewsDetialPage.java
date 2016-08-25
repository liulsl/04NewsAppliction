package newsdetailmenupage;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cskaoyan.zhao.a04newsappliction.R;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

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

        // lv_newsDetailpage_newslist.setAdapter();
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
        lv_newsDetailpage_newslist.setAdapter(new MyNewsListAdapter());


    }


    class MyNewsListAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return newsDetail.data.news.size();
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

            TextView tv=new TextView(mActivity);
            tv.setText(newsDetail.data.news.get(position).title);
            return tv;
        }
    }
}
