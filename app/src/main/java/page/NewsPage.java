package page;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cskaoyan.zhao.a04newsappliction.HomeActivity;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bean.Categories;
import bean.MenuTitle;
import constants.Const;
import fragment.LeftMenuFragment;
import menupage.BaseMenuPage;
import menupage.InteractMenuPage;
import menupage.NewsMenuPage;
import menupage.PictrueMenuPage;
import menupage.TopicMenuPage;
import utils.SharedPrefUtils;

/**
 * Created by zhao on 2016/8/23.
 */
public class NewsPage extends BasePage {


    private static final String TAG = "NewsPage";
    private HomeActivity homeActivity;

    List<BaseMenuPage>  newsMenuPage;



    public NewsPage(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {

        tv_pageview_pageTitle.setText("新闻");
        //
        TextView textView = new TextView(mActivity);
        textView.setText("新闻中间的内容");
        textView.setGravity(Gravity.CENTER);
        ll_viewpage_content.addView(textView);

        bt_pageview_leftbuttum.setVisibility(View.VISIBLE);
        //bt_pageview_rightbuttum.setVisibility(View.VISIBLE);



        homeActivity = (HomeActivity) mActivity;

        bt_pageview_leftbuttum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mActivity,"click",Toast.LENGTH_SHORT).show();
                homeActivity.toggleSlidingMenu();
            }
        });
      /*  HomeActivity homeActivity = (HomeActivity) mActivity;
        homeActivity.setSlidingMenuEnable(true);*/


        //去下载侧边栏的数据

        // 
        //getDataFromServer();

    }

    public void getData(){
        //看看有没有缓存，如果有，则使用cache里的数据
        final String url = Const.SERVER_ADDR+"/categories.json";
        String jsonFromCache = SharedPrefUtils.getJsonFromCache(url, mActivity);
        if (jsonFromCache.isEmpty()){
            //从服务器上去拿数据
            Log.i(TAG,"缓存为空从服务器上去拿数据");
            getDataFromServer(url);
        }else{ //缓存不为空，则直接用缓存去解析json
            Log.i(TAG,"缓存不为空直接用缓存去解析jso");
            parseJsonString(jsonFromCache);
        }



    }


    public void getDataFromServer(String url){

        final String urlkey = url;

        Log.i(TAG, "getDataFromServer="+url);
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                Log.i(TAG,responseInfo.result);
                //应该把从服务器上拿到的JsonString缓存起来
                SharedPrefUtils.saveJsonToCache(urlkey,responseInfo.result,mActivity);
                parseJsonString(responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.i(TAG,s);
                Log.e(TAG,e.toString());

            }
        });


    }

    private void parseJsonString(String result) {


        //Gson
        Gson gson = new Gson();
        Categories categories = gson.fromJson(result, Categories.class);
        Log.i(TAG,categories.toString());
        LeftMenuFragment leftMenuFragment = homeActivity.getLeftMenuFragment();
        leftMenuFragment.setMenuData(categories);

        initMenuPage(categories);

       /* try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray data = jsonObject.getJSONArray("data");
            JSONObject jsonObject1 = data.getJSONObject(0);
            String title1 = jsonObject1.getString("title");
            Log.i(TAG,"menu1="+title1);

            JSONObject jsonObject2 = data.getJSONObject(1);
            String title2 = jsonObject2.getString("title");
            Log.i(TAG,"menu2="+title2);

            JSONObject jsonObject3 = data.getJSONObject(2);
            String title3 = jsonObject3.getString("title");
            Log.i(TAG,"menu3="+title3);

            JSONObject jsonObject4 = data.getJSONObject(3);
            String title4 = jsonObject4.getString("title");
            Log.i(TAG,"menu4="+title4);

            MenuTitle menuTitle = new MenuTitle(title1,title2,title3,title4);

            LeftMenuFragment leftMenuFragment = homeActivity.getLeftMenuFragment();
            leftMenuFragment.setMenuData(menuTitle);

        } catch (JSONException e) {
            e.printStackTrace();
        }*/


    }

    private void initMenuPage(Categories categories) {

        newsMenuPage = new ArrayList<>();

       /* for(int i=0;i< categories.data.size();i++){
            TextView textView = new TextView(mActivity);
            textView.setText( categories.data.get(i).title);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.RED);

            newsMenuPage.add(textView);
        }*/

        newsMenuPage.add(new NewsMenuPage(mActivity,categories.data.get(0)));
        newsMenuPage.add(new TopicMenuPage(mActivity,categories.data.get(1)));

        final PictrueMenuPage pictrueMenuPage = new PictrueMenuPage(mActivity, categories.data.get(2));
        newsMenuPage.add(pictrueMenuPage);
        bt_pageview_rightbuttum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictrueMenuPage.changeUI();
            }
        });

        newsMenuPage.add(new InteractMenuPage(mActivity,categories.data.get(3)));


        //让newspage进来之后默认显示 第一个menupage
        changeNewsPageContent(0);

    }

    public void changeNewsPageContent(int position){


        ll_viewpage_content.removeAllViews();
        BaseMenuPage page = newsMenuPage.get(position);

        if (position==2){
           bt_pageview_rightbuttum.setVisibility(View.VISIBLE);
        }else{
            bt_pageview_rightbuttum.setVisibility(View.GONE);

        }

        page.initData();

        ll_viewpage_content.addView(page.mMenuPageView);
    }
}
