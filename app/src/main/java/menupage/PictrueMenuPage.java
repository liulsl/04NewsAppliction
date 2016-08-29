package menupage;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cskaoyan.zhao.a04newsappliction.R;
import com.google.gson.Gson;
import com.google.gson.internal.Primitives;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import bean.Categories;
import bean.PictureNews;
import constants.Const;
import utils.MyBitmapUtils;
import utils.SharedPrefUtils;

/**
 * Created by zhao on 2016/8/24.
 */
public class PictrueMenuPage extends BaseMenuPage {

    public static final String TAG = "PictrueMenuPage";
    private PictureNews pictureNews;
    private ListView lv_pitruemenupage_content;
    private GridView gv_pitruemenupage_content;

    public PictrueMenuPage(Activity mActivitym, Categories.MenuDataInfo menuDataInfo) {
        super(mActivitym, menuDataInfo);
    }

    @Override
    public View initView() {

       /* TextView textView = new TextView(mActivity);
        textView.setText(  menuDataInfo.title);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(30);
        textView.setTextColor(Color.YELLOW);
        return textView;*/

        View picture_menu_page = View.inflate(mActivity, R.layout.picture_menu_page, null);

        lv_pitruemenupage_content = (ListView) picture_menu_page.findViewById(R.id.lv_pitruemenupage_content);

        gv_pitruemenupage_content = (GridView) picture_menu_page.findViewById(R.id.gv_pitruemenupage_content);
        return  picture_menu_page;

    }

    @Override
    public void initData() {

        //http://localhost:8080/zhbj/photos/photos_1.json
        String url = Const.SERVER_ADDR + "/photos/photos_1.json";

        String jsonFromCache = SharedPrefUtils.getJsonFromCache(url, mActivity);
        if (jsonFromCache.isEmpty())
            getDataFromServer(url);
        else
            parseJson(jsonFromCache);
    }

    private void getDataFromServer(String url) {
        HttpUtils httpUtils = new HttpUtils( );

        final String keyUrl = url;

        httpUtils.send(HttpRequest.HttpMethod.GET,
                Const.SERVER_ADDR + "/photos/photos_1.json",
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {

                        Log.i(TAG,responseInfo.result);

                        SharedPrefUtils.saveJsonToCache(keyUrl,responseInfo.result,mActivity);
                        parseJson(responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {

                        Toast.makeText(mActivity,"加载失败，请稍后重试！",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void parseJson(String result) {


        Gson gson = new Gson();
        pictureNews = gson.fromJson(result, PictureNews.class);


        MyPictureListAdapter myPictureListAdapter = new MyPictureListAdapter();
        lv_pitruemenupage_content.setAdapter(myPictureListAdapter);

        gv_pitruemenupage_content.setAdapter(myPictureListAdapter);

    }


    boolean flag =true;
    public void changeUI(){

        if (flag){
            gv_pitruemenupage_content.setVisibility(View.VISIBLE);
            lv_pitruemenupage_content.setVisibility(View.INVISIBLE);
            flag=false;
        }else {
            gv_pitruemenupage_content.setVisibility(View.INVISIBLE);
            lv_pitruemenupage_content.setVisibility(View.VISIBLE);
            flag=true;
        }

    }

    class MyPictureListAdapter extends BaseAdapter{

       // BitmapUtils bitmapUtils;
        MyBitmapUtils myBitmapUtils;
        public MyPictureListAdapter() {

            myBitmapUtils = new MyBitmapUtils(mActivity);
        }

        @Override
        public int getCount() {
            return pictureNews.data.news.size();
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


            PictureNews.DataBean.NewsBean newsBean = pictureNews.data.news.get(position);

            String listimage = newsBean.listimage;
            String title = newsBean.title;

            View view = View.inflate(mActivity, R.layout.item_listview_picturenews, null);

            ImageView iv_listviewpicturenews_img = (ImageView) view.findViewById(R.id.iv_listviewpicturenews_img);
            TextView tv_listviewpicturenews_title = (TextView) view.findViewById(R.id.tv_listviewpicturenews_title);

            myBitmapUtils.display(iv_listviewpicturenews_img,listimage);
            tv_listviewpicturenews_title.setText(title);

            return view;
        }
    }
}
