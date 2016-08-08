package P_Data;

import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by user on 2016-08-03.
 */
public class User_Data {

    //유저 아이디 air data,위치 정도,마커?
    public Air_Data air;
    public LatLng latLng;
    public String id;
    public Circle circle;
    public Marker marker;
    public boolean check=false; //user data가 왔는지 안왔는지 체크
    //Marker marker;
    public User_Data(String id,Air_Data air,LatLng latLng) {
        this.id=id;
        this.air=air;
        this.latLng=latLng;
    }
    public User_Data(String id,Air_Data air,LatLng latLng,Circle circle) {
        this.id=id;
        this.air=air;
        this.latLng=latLng;
        this.circle=circle;
    }
}
