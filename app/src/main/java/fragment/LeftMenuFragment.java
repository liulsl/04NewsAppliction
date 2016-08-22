package fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by zhao on 2016/8/22.
 */
public class LeftMenuFragment extends Fragment{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        TextView textView = new TextView(getActivity());
        textView.setText("leftmenu");

        return textView ;//return super.onCreateView(inflater, container, savedInstanceState);
    }
}
