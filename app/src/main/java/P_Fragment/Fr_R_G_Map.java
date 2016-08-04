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

import com.example.pyojihye.airpollution.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import P_Manager.GMap_Manager;
import P_Utils.Util_STATUS;

/**
 * Created by user on 2016-08-02.
 */
public class Fr_R_G_Map extends Fragment implements OnMapReadyCallback {

    LatLng latLng;

    public Fr_R_G_Map(LatLng latLng) {
        this.latLng = latLng;
    }

    MapView gMapView;
    public static GoogleMap gMap = null;
    //MapFragment

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);
        //MapFragment map = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment_map);
        //map.getMapAsync(this);
        //return view;
        //SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map3);
        //MapFragment map=(MapFragment)getChildFragmentManager().findFragmentById(R.id.map3);
        //MapFragment map=(MapFragment)getChildFragmentManager().findFragmentByTag("map3");
        //MapFragment map=(MapFragment)getChildFragmentManager().findFragmentByTag("map3");
        MapFragment map = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map3);
        map.getMapAsync(this);

        /**
         * Button 등록 과정
         */
        Button btn=(Button)view.findViewById(R.id.CO_button);
        btn.setOnClickListener(onClickListener);
        btn=(Button)view.findViewById(R.id.SO2_button);
        btn.setOnClickListener(onClickListener);
        btn=(Button)view.findViewById(R.id.NO_button);
        btn.setOnClickListener(onClickListener);
        btn=(Button)view.findViewById(R.id.O3_button);
        btn.setOnClickListener(onClickListener);
        btn=(Button)view.findViewById(R.id.PM_button);
        btn.setOnClickListener(onClickListener);
        return view;
        // mapFragment.getMapAsync(this);
        //return inflater.inflate(R.layout.fragment_map,container,false);
    }

    Button.OnClickListener onClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.CO_button :
                    Util_STATUS.GMap_Realtime_set="CO";
                    break ;
                case R.id.SO2_button :
                    Util_STATUS.GMap_Realtime_set="SO2";
                    break ;
                case R.id.NO_button :
                    Util_STATUS.GMap_Realtime_set="NO2";
                    break ;
                case R.id.O3_button :
                    Util_STATUS.GMap_Realtime_set="O3";
                    break ;
                case R.id.PM_button :
                    Util_STATUS.GMap_Realtime_set="PM";
                    break ;
            }
            reset_circle();
        }
    } ;
    public void onMapMarker(LatLng latLng) {
        //gMap.addCircle(new CircleOptions().center(latLng).radius(1000));
    }
    public void reset_circle()
    {
        for (Circle circle : GMap_Manager.mylist) {
            circle.remove();
        }
        GMap_Manager.mylist.clear();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //LatLng position = new LatLng(location.getLatitude(),location.getLongitude());
        gMap = googleMap;
        //gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        googleMap.addMarker(new MarkerOptions().position(latLng).draggable(false));
        //CircleOptions circleOptions=new CircleOptions().center(latLng).radius(10000);
        //Circle circle=googleMap.addCircle(circleOptions);
        googleMap.addCircle(new CircleOptions().center(latLng).radius(100).strokeColor(Color.RED).fillColor(Color.RED));
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        googleMap.setMyLocationEnabled(true);
        //mGoogleMap.setMyLocationEnabled(true);

        //googleMap.addCircle(new CircleOptions().center(latLng).radius(10000));
        //gMap.addCircle(new CircleOptions().center(ar.latLng).radius(1000000));
    }
}
