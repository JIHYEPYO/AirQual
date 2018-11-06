package P_Fragment;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pyojihye.airpollution.AQI_calculate;
import com.example.pyojihye.airpollution.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

import P_Data.Air_Data;
import P_Data.Util_STATUS;
import P_Manager.HGMap_Manager;

/**
 * Created by user on 2016-08-03.
 */
public class Fr_H_G_Map extends Fragment implements OnMapReadyCallback,GoogleMap.OnCircleClickListener,GoogleMap.OnMarkerClickListener {
    @Nullable
    LatLng latLng;
    public static GoogleMap hgMap = null;
    //MapFragment
    ArrayList<Button> btn_array=new ArrayList<>();
    View view;
    public Fr_H_G_Map(LatLng latLng) {
        this.latLng = latLng;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map2, container, false);
        MapFragment map = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map4);
        map.getMapAsync(this);
        Button btn=(Button)view.findViewById(R.id.history_combine);
        btn.setOnClickListener(onClickListener);
        btn_array.add(btn);
        btn=(Button)view.findViewById(R.id.history_road);
        btn.setOnClickListener(onClickListener);
        btn_array.add(btn);
        btn=(Button)view.findViewById(R.id.CO_button);
        btn_array.add(btn);
        btn.setOnClickListener(onClickListener);
        btn=(Button)view.findViewById(R.id.SO2_button);
        btn_array.add(btn);
        btn.setOnClickListener(onClickListener);
        btn=(Button)view.findViewById(R.id.NO_button);
        btn_array.add(btn);
        btn.setOnClickListener(onClickListener);
        btn=(Button)view.findViewById(R.id.O3_button);
        btn_array.add(btn);
        btn.setOnClickListener(onClickListener);
        btn=(Button)view.findViewById(R.id.PM_button);
        btn_array.add(btn);
        btn.setOnClickListener(onClickListener);

        //MapFragment map = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment_map);
        //map.getMapAsync(this);
        //return view;
        //SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map3);
        //MapFragment map=(MapFragment)getChildFragmentManager().findFragmentById(R.id.map3);
        //MapFragment map=(MapFragment)getChildFragmentManager().findFragmentByTag("map3");
        //MapFragment map=(MapFragment)getChildFragmentManager().findFragmentByTag("map3");

        return view;
    }
    public void Button_reset()
    {

        for(int i=0;i<btn_array.size();i++)
        {
            btn_array.get(i).setBackgroundColor(Color.parseColor("#f2f2f2"));
        }

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        hgMap = googleMap;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));


        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        googleMap.setMyLocationEnabled(true);
        HGMap_Manager.Set_history_Map(1);
        googleMap.setOnCircleClickListener(this);
        googleMap.setOnMarkerClickListener(this);
        hgMap.getProjection().getVisibleRegion();

    }
    Button.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button btn;
            Button_reset();
            HGMap_Manager.Change_Color();
            switch (view.getId())
            {

                case R.id.history_combine:
                {

                    HGMap_Manager.Set_history_Map(1);

                    break;
                }
                case R.id.history_road:
                {

                    HGMap_Manager.Set_history_Map(0);
                    break;
                }
                case R.id.CO_button :
                    P_Data.Util_STATUS.GMap_Realtime_set="CO";
                    btn=((Button)view.findViewById(R.id.CO_button));
                    btn.setBackgroundColor(Color.DKGRAY);

                    break ;
                case R.id.SO2_button :
                    P_Data.Util_STATUS.GMap_Realtime_set="SO2";
                    btn=((Button)view.findViewById(R.id.SO2_button));
                    btn.setBackgroundColor(Color.DKGRAY);

                    break ;
                case R.id.NO_button :
                    P_Data.Util_STATUS.GMap_Realtime_set="NO2";
                    btn=((Button)view.findViewById(R.id.NO_button));
                    btn.setBackgroundColor(Color.DKGRAY);

                    break ;
                case R.id.O3_button :
                    P_Data.Util_STATUS.GMap_Realtime_set="O3";
                    btn=((Button)view.findViewById(R.id.O3_button));
                    btn.setBackgroundColor(Color.DKGRAY);

                    break ;
                case R.id.PM_button :
                    P_Data.Util_STATUS.GMap_Realtime_set="PM";
                    btn=((Button)view.findViewById(R.id.PM_button));
                    btn.setBackgroundColor(Color.DKGRAY);

            }

        }
    };

    @Override
    public void onCircleClick(Circle circle) {

        //circle의 위도 경도구해서 user_array안에 값다 읽어서 airdata에 더함
        LatLng latLng=circle.getCenter();
        circle.getCenter();
        double co=0.0;
        double so2=0.0;
        double no2=0.0;
        double o3=0.0;
        double pm=0.0;
        double count=0.0;
        for(int i=0;i<HGMap_Manager.history_data.size();i++)
        {
             Air_Data ar=HGMap_Manager.history_data.get(i);
            if(ar.latLng.equals(latLng))
            {

                co+=ar.co;
                so2+=ar.so2;
                no2+=ar.no2;
                o3+=ar.o3;
                pm+=ar.pm2_5;
                count++;
            }
        }
        for(int i=0;i<count;i++)
        {
            co=co/count;
            so2=so2/count;
            no2=no2/count;
            o3=o3/count;
            pm=pm/count;
        }
        //double receive_CO, double receive_SO2, double receive_NO2, double receive_O3, double receive_PM
        //
        AQI_calculate.setAirdata(co,so2,no2,o3,pm);
        for(int i=0;i<HGMap_Manager.add_marker.size();i++)
        {
            if(circle.getCenter().equals(HGMap_Manager.add_marker.get(i).getPosition()))
            {
                //HGMap_Manager.add_marker.get(i).setVisible(true);
                String data="CO AQI"+ Util_STATUS.AQI_CO;
                HGMap_Manager.add_marker.get(i).setSnippet("data");
                //HGMap_Manager.add_marker.get(i).setSnippet(String.valueOf(AQI_calculate.SO2_AQI_Cal()));
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        LatLng latLng=marker.getPosition();
        for(int i=0;i<HGMap_Manager.add_marker.size();i++)
        {
            if(HGMap_Manager.add_marker.get(i).getPosition().equals(latLng))
            {

                String data="CO AQI"+ Util_STATUS.AQI_CO;
                HGMap_Manager.add_marker.get(i).setSnippet("data");
            }
        }
        /*
        if((GMap_Manager.user_array.get(i).marker.equals(marker)))
        {
            GMap_Manager.user_array.get(i);
            Air_Data air=GMap_Manager.user_array.get(i).air;

            String data="CO "+String.valueOf(air.co)+" SO2 "+String.valueOf(air.so2)+" O3 "+String.valueOf(air.o3)+
                    " PM2.5 "+String.valueOf(air.pm2_5)+" NO2 "+String.valueOf(air.no2);
            marker.setSnippet(data);


        }*/
        return false;
    }
}
