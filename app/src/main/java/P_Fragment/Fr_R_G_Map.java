package P_Fragment;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pyojihye.airpollution.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

import P_Data.Air_Data;
import P_Manager.GMap_Manager;
import P_Manager.Gps_Manager;

/**
 * Created by user on 2016-08-02.
 */
public class Fr_R_G_Map extends Fragment implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener,GoogleMap.OnCircleClickListener,GoogleMap.OnMapClickListener {

    LatLng latLng;

    public Fr_R_G_Map() {
            latLng= Gps_Manager.latLng;
    }

    MapView gMapView;
    public static GoogleMap gMap = null;
    //MapFragment

    View view;

    ArrayList<Button> btn_array=new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_map, container, false);

        MapFragment map = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map3);
        map.getMapAsync(this);

        /**
         * Button 등록 과정
         */
        Button btn=(Button)view.findViewById(R.id.CO_button);
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

        return view;

    }

    Button.OnClickListener onClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button btn;
            Button_reset();
            switch (view.getId()) {

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

                    break ;
            }
            GMap_Manager.Change_Color();


        }
    } ;
    public void Button_reset()
    {

        for(int i=0;i<btn_array.size();i++)
        {
            btn_array.get(i).setBackgroundColor(Color.parseColor("#f2f2f2"));
        }

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));


        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        googleMap.setMyLocationEnabled(false);
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnCircleClickListener(this);
        googleMap.setOnMapClickListener(this);
        gMap.getProjection().getVisibleRegion();
    }






    @Override
    public boolean onMarkerClick(Marker marker) {
        for(int i=0;i<GMap_Manager.user_array.size();i++)
        {

            if((GMap_Manager.user_array.get(i).marker.equals(marker)))
            {
                GMap_Manager.user_array.get(i);
                Air_Data air=GMap_Manager.user_array.get(i).air;

                String data="CO "+String.valueOf(air.co)+" SO2 "+String.valueOf(air.so2)+" O3 "+String.valueOf(air.o3)+
                        " PM2.5 "+String.valueOf(air.pm2_5)+" NO2 "+String.valueOf(air.no2);
                marker.setSnippet(data);


            }

        }
        return false;
    }

    @Override
    public void onCircleClick(Circle circle) {
        for(int i=0;i<GMap_Manager.user_array.size();i++)
        {
            if(circle.equals(GMap_Manager.user_array.get(i).circle))
            {
                GMap_Manager.user_array.get(i);
                Air_Data air=GMap_Manager.user_array.get(i).air;
                String data="co "+String.valueOf(air.co)+" so2 "+String.valueOf(air.so2)+" o3 "+String.valueOf(air.o3)+
                        " pm2_5 "+String.valueOf(air.pm2_5)+" no2 "+String.valueOf(air.no2);
                GMap_Manager.user_array.get(i).marker.setSnippet(data);

            }
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

        Log.d("lalng",String.valueOf(latLng.latitude)+String.valueOf(latLng.longitude));
    }
}
