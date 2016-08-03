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

import com.example.pyojihye.airpollution.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by user on 2016-08-02.
 */
public class Fr_GMap extends Fragment implements OnMapReadyCallback {

    LatLng latLng;

    public Fr_GMap(LatLng latLng) {
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

        return view;
        // mapFragment.getMapAsync(this);
        //return inflater.inflate(R.layout.fragment_map,container,false);
    }

    public void onMapMarker(LatLng latLng) {
        //gMap.addCircle(new CircleOptions().center(latLng).radius(1000));
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
