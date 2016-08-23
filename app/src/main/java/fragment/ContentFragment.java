package fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cskaoyan.zhao.a04newsappliction.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import page.BasePage;
import page.GovernmentPage;
import page.HomePage;
import page.NewsPage;
import page.SettingPage;
import page.SmartServicePage;

/**
 * Created by zhao on 2016/8/22.
 */
public class ContentFragment extends Fragment {

    private ViewPager vp_fragmentcontent_content;

    List<BasePage> pageList = new ArrayList<BasePage>();
    private RadioGroup rg_framgentcontent_bottom;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

      /*  TextView textView = new TextView(getActivity());
        textView.setText("content");
        textView.setGravity(Gravity.CENTER);*/

        View view = View.inflate(getActivity(), R.layout.fragment_content, null);

        rg_framgentcontent_bottom = (RadioGroup) view.findViewById(R.id.rg_framgentcontent_bottom);

        vp_fragmentcontent_content =
                (ViewPager) view.findViewById(R.id.vp_fragmentcontent_content);


       // for (int i=0;i<5;i++){

           /* TextView textView= new TextView(getActivity());
            textView.setText("page"+i);*/
            // View content = View.inflate(getActivity(), R.layout.page_content, null);

            pageList.add(new HomePage(getActivity()));
            pageList.add(new NewsPage(getActivity()));
            pageList.add(new SmartServicePage(getActivity()));
            pageList.add(new GovernmentPage(getActivity()));
            pageList.add(new SettingPage(getActivity()));

        // }

       /* Page page = pageList.get(0);
        page.setPageTitle("首页");*/

        vp_fragmentcontent_content.setAdapter(new MyContentAdatper());


        rg_framgentcontent_bottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){


                    case R.id.rb_fragmentcontent_home:
                        vp_fragmentcontent_content.setCurrentItem(0,false);
                        break;

                    case R.id.rb_fragmentcontent_news:
                        vp_fragmentcontent_content.setCurrentItem(1,false);

                        break;
                    case R.id.rb_fragmentcontent_service:
                        vp_fragmentcontent_content.setCurrentItem(2,false);
                        break;
                    case R.id.rb_fragmentcontent_goverment:
                        vp_fragmentcontent_content.setCurrentItem(3,false);
                        break;
                    case R.id.rb_fragmentcontent_setting:
                        vp_fragmentcontent_content.setCurrentItem(4,false);
                        break;

                }

            }
        });

        return view ;//super.onCreateView(inflater, container, savedInstanceState);
    }

    class MyContentAdatper extends PagerAdapter{


        @Override
        public int getCount() {
            return pageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            BasePage page = pageList.get(position);
            container.addView(page.mPageView);
            return page.mPageView;//super.instantiateItem(container, position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
            // super.destroyItem(container, position, object);
        }


    }
}
