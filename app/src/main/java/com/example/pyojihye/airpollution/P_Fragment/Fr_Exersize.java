package com.example.pyojihye.airpollution.P_Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pyojihye.airpollution.R;

/**
 * Created by user on 2016-08-02.
 */
public class Fr_Exersize extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.air_status, container, false);

        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
