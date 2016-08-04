package P_Manager;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by user on 2016-08-02.
 */
public class Gps_Manager {
    public LocationManager locationManager;
    Location location;
    Context mContext;
    LatLng latLng=null;


    public Gps_Manager(Context mContext) {
        this.mContext = mContext;
        //locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location==null)
        {
            location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        }
        latLng=new LatLng(location.getLatitude(),location.getLongitude());
    }
    public LatLng get_LatLng()
    {
        return latLng;
    }
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // arrayPoints.add(new LatLng(Double.parseDouble(intent.getStringExtra("LAT")),Double.parseDouble(intent.getStringExtra("LANG"))));
             latLng= new LatLng(location.getLatitude(), location.getLongitude());

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };
}
