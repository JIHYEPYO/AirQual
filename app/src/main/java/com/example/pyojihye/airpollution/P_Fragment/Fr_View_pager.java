package com.example.pyojihye.airpollution.P_Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pyojihye.airpollution.activity.MainActivity;
import com.example.pyojihye.airpollution.R;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;


/**
 * Created by user on 2016-08-02.
 */

public class Fr_View_pager extends Fragment {

    VerticalViewPager v_viewpager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fr_viewpager_layout,container,false);

        //v_viewpager=new VerticalViewPager(getApplicationContext());
        v_viewpager=new VerticalViewPager(inflater.getContext());
        //v_viewpager=(VerticalViewPager)findViewById(R.id.pager);
        v_viewpager=(VerticalViewPager)view.findViewById(R.id.pager);
        //p_adapter=new Pager_Adapter(getLayoutInflater(),getApplicationContext(),pHandler);
        //v_viewpager.setAdapter(p_adapter);   
        v_viewpager.setCurrentItem(0);
        v_viewpager.setOffscreenPageLimit(5);
        v_viewpager.setAdapter(MainActivity.pa);

        //Air_Data_Service ADS=new Air_Data_Service(pHandler);
        return view;



        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
