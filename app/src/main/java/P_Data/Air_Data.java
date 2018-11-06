package P_Data;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by user on 2016-07-25.
 */
public class Air_Data implements Serializable {

    public  int time;
    public  double co2;
    public  double co;
    public  double so2;
    public  double no2;
    public  double pm2_5;
    public  double o3;
    public  int temp;
    public LatLng latLng;

    public Air_Data()
    {

    }

    public Air_Data(int time, double co2, double co, double so2, double no2, double pm2_5, double o3,LatLng latLng) {
        this.time = time;
        this.co2 = co2;
        this.co = co;
        this.so2 = so2;
        this.no2 = no2;
        this.pm2_5 = pm2_5;
        this.o3 = o3;
        this.latLng=latLng;
    }
}
