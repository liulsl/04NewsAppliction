package fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cskaoyan.zhao.a04newsappliction.R;

import org.w3c.dom.Text;

import bean.MenuTitle;
import page.BasePage;

/**
 * Created by zhao on 2016/8/22.
 */
public class LeftMenuFragment extends Fragment{


    private ListView lv_fragmentleftmenu_menu;


    private MenuTitle titles;

    String[] menuTitles= new String[]{"头条","专题","组图","互动" };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        /*TextView textView = new TextView(getActivity());
        textView.setText("leftmenu");*/

        View view = View.inflate(getActivity(), R.layout.fragment_leftmenu, null);

        lv_fragmentleftmenu_menu = (ListView) view.findViewById(R.id.lv_fragmentleftmenu_menu);

        return view ;//return super.onCreateView(inflater, container, savedInstanceState);
    }

    class MyLeftMenuAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 4;
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

            TextView textView = new TextView(getActivity());

            switch (position){

                case  0:
                    textView.setText(titles.menutitle1);

                    break;
                case  1:
                    textView.setText(titles.menutitle2);

                    break;
                case  2:
                    textView.setText(titles.menutitle3);
                    break;
                case  3:
                    textView.setText(titles.menutitle4);
                    break;



            }

            return textView;
        }
    }

    public void setMenuData(MenuTitle menuTitle){
        titles=menuTitle;
        lv_fragmentleftmenu_menu.setAdapter(new MyLeftMenuAdapter());


    }
}
