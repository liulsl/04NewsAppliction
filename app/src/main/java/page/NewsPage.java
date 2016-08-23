package page;

import android.app.Activity;
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

import bean.Categories;
import bean.MenuTitle;
import fragment.LeftMenuFragment;

/**
 * Created by zhao on 2016/8/23.
 */
public class NewsPage extends BasePage {


    private static final String TAG = "NewsPage";
    private HomeActivity homeActivity;

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
        bt_pageview_rightbuttum.setVisibility(View.VISIBLE);

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


    public void getDataFromServer(){

        String url ="http://10.0.2.2:8080/zhbj/categories.json";
                
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
        Categories categories = gson.fromJson(result, Categories.class);
        Log.i(TAG,categories.toString());
        LeftMenuFragment leftMenuFragment = homeActivity.getLeftMenuFragment();
        leftMenuFragment.setMenuData(categories);

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
}
