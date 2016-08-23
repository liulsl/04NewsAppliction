package fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cskaoyan.zhao.a04newsappliction.R;

import org.w3c.dom.Text;

import bean.Categories;
import bean.MenuTitle;
import page.BasePage;

/**
 * Created by zhao on 2016/8/22.
 */
public class LeftMenuFragment extends Fragment{


    private ListView lv_fragmentleftmenu_menu;


    private  int currentPosition;
   // private MenuTitle titles;

    private Categories categories;

    String[] menuTitles= new String[]{"头条","专题","组图","互动" };
    private MyLeftMenuAdapter myLeftMenuAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        /*TextView textView = new TextView(getActivity());
        textView.setText("leftmenu");*/

        View view = View.inflate(getActivity(), R.layout.fragment_leftmenu, null);

        lv_fragmentleftmenu_menu = (ListView) view.findViewById(R.id.lv_fragmentleftmenu_menu);

        lv_fragmentleftmenu_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


               /* TextView tv_itemmenulist_menutitle = (TextView) view.findViewById(R.id.tv_itemmenulist_menutitle);
                tv_itemmenulist_menutitle.setEnabled(true);*/

                currentPosition=position;
                myLeftMenuAdapter.notifyDataSetChanged();

            }
        });

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


            Categories.MenuDataInfo menuDataInfo = categories.data.get(position);
            /*TextView textView = new TextView(getActivity());
            textView.setText(menuDataInfo.title);*/

            View inflate = View.inflate(getActivity(),R.layout.item_menulist, null);
            TextView tv_itemmenulist_menutitle = (TextView) inflate.findViewById(R.id.tv_itemmenulist_menutitle);

            tv_itemmenulist_menutitle.setText(menuDataInfo.title);

            if (currentPosition!=position)
               tv_itemmenulist_menutitle.setEnabled(false);
            else{
                tv_itemmenulist_menutitle.setEnabled(true);
            }


            return tv_itemmenulist_menutitle;
        }
    }

    public void setMenuData(Categories categories){
        this.categories=categories;
        myLeftMenuAdapter = new MyLeftMenuAdapter();

        lv_fragmentleftmenu_menu.setAdapter(myLeftMenuAdapter);


    }
}
