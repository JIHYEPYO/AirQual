package P_Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pyojihye.airpollution.R;
import com.example.pyojihye.airpollution.activity.MainActivity;

import P_Adapter.Main_Adapter;

/**
 * Created by user on 2016-08-12.
 */
public class Fr_Main extends Fragment {


    public ViewPager viewPager;
    @Nullable


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fr_main_pager,container,false);

        viewPager=new ViewPager(inflater.getContext());
        viewPager=(ViewPager)view.findViewById(R.id.main_pager);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(new Main_Adapter(MainActivity.getInstance().getLayoutInflater(),MainActivity.getInstance().getApplicationContext()));

        return view;
    }
}
