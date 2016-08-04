package P_Manager;

import android.graphics.Color;
import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import P_Data.Air_Data;
import P_Data.User_Data;
import P_Data.Util_STATUS;
import P_Fragment.Fr_R_G_Map;

/**
 * Created by user on 2016-08-03.
 */
public class GMap_Manager {

    public static ArrayList<Circle> mylist=new ArrayList<>();
    public static HashMap user_hash=new HashMap(); //유저 관리 테이블
    public static ArrayList<User_Data> user_array=new ArrayList<>();



        public void Set_Circle2(Air_Data ar,String user_id,LatLng latLng)
        {
            int count=0;
            if(user_array.size()==0)
            {
                CircleOptions circleOptions = new CircleOptions()
                        .center(latLng)
                        .radius(100)
                        .fillColor(Color.TRANSPARENT)
                        .strokeColor(Color.TRANSPARENT);
                Circle circle = Fr_R_G_Map.gMap.addCircle(circleOptions);

                User_Data user_data=new User_Data(user_id,ar,latLng); //id air latlng
                user_data.circle=circle;
                user_array.add(user_data);
                user_data.check=true;
                return;
            }
            for(User_Data data : user_array)
            {
                if(data.id.equals(user_id))
                {
                    String color=Set_Color(ar);
                    data.circle.setStrokeColor(Color.parseColor(color));
                    data.circle.setFillColor(Color.parseColor(color));
                    animateCircle(data.circle,latLng,data.latLng);
                    data.latLng=latLng;
                    data.check=true;
                    return;
                }
                count++;
                if(count==user_array.size()) //user id 가 없으면
                {
                    CircleOptions circleOptions = new CircleOptions()
                            .center(latLng)
                            .radius(100)
                            .fillColor(Color.TRANSPARENT)
                            .strokeColor(Color.TRANSPARENT);
                    Circle circle = Fr_R_G_Map.gMap.addCircle(circleOptions);

                    User_Data user_data=new User_Data(user_id,ar,latLng); //id air latlng
                    user_data.circle=circle;
                    user_data.check=true;

                    user_array.add(user_data);
                }

            }


        }
    public void check_connection()
    {
        if(user_array.size()!=0) {
            for (User_Data data : user_array) {
                if (data.check == false) {
                    data.circle.remove();
                    user_array.remove(data);
                }
                data.check = false;
            }
        }
    }


    public void animateCircle(final Circle circle, final LatLng toPosition,
                              final LatLng startPosition) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 1000;
        final Interpolator interpolator = new LinearInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startPosition.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startPosition.latitude;
                circle.setCenter(new LatLng(lat,lng));
                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 100);
                } else {

                }
            }
        });

    }


        public String Set_Color(Air_Data ar)
        {
            String color;
            switch (Util_STATUS.GMap_Realtime_set)
            {
                case "CO":
                {
                    color= (ar.co< 51) ? "#7000e400" : (ar.co < 101) ? "#70e9e918" : (ar.co < 151) ? "#70ff7e00" : (ar.co < 200) ? "#70ff0000" :
                            (ar.co < 301) ? "#708f3f97" : (ar.co < 500) ? "#707e0023" : "#707e0023";
                    break;
                }
                case "SO2":
                {
                    color= (ar.so2< 51) ? "#7000e400" : (ar.so2 < 101) ? "#70e9e918" : (ar.so2 < 151) ? "#70ff7e00" : (ar.so2 < 200) ? "#70ff0000" :
                            (ar.so2 < 301) ? "#708f3f97" : (ar.so2 < 500) ? "#707e0023" : "#707e0023";
                    break;
                }
                case "NO2":
                {
                    color= (ar.no2< 51) ? "#7000e400" : (ar.no2 < 101) ? "#70e9e918" : (ar.no2 < 151) ? "#70ff7e00" : (ar.no2 < 200) ? "#70ff0000" :
                            (ar.no2 < 301) ? "#708f3f97" : (ar.no2 < 500) ? "#707e0023" : "#707e0023";
                    break;
                }
                case "O3":
                {
                    color= (ar.o3< 51) ? "#7000e400" : (ar.o3 < 101) ? "#70e9e918" : (ar.o3 < 151) ? "#70ff7e00" : (ar.o3 < 200) ? "#70ff0000" :
                            (ar.o3 < 301) ? "#708f3f97" : (ar.o3 < 500) ? "#707e0023" : "#707e0023";

                    break;
                }
                case "PM":
                {
                    color= (ar.pm2_5< 51) ? "#7000e400" : (ar.pm2_5 < 101) ? "#70e9e918" : (ar.pm2_5 < 151) ? "#70ff7e00" : (ar.pm2_5 < 200) ? "#70ff0000" :
                            (ar.pm2_5 < 301) ? "#708f3f97" : (ar.pm2_5 < 500) ? "#707e0023" : "#707e0023";
                    break;
                }
                default:
                {
                    color="#70e400";

                }

            }
            return color;
        }

        /** this is before data**/
        public void Set_Circle(Air_Data ar)
        {

            String color;
            final Circle myCircle;

            switch (Util_STATUS.GMap_Realtime_set)
            {
                case "CO":
                {
                    color=return_Color(ar.co);
                    myCircle= Fr_R_G_Map.gMap.addCircle(new CircleOptions().center(ar.latLng).
                            radius(10000).strokeColor(Color.parseColor(color)).fillColor(Color.parseColor(color)));

                    mylist.add(myCircle);
                    animateCircle(myCircle,new LatLng(ar.latLng.latitude+0.001,ar.latLng.longitude+0.001),ar.latLng);
                    //animateCircle(myCircle,new LatLng(ar.latLng.latitude+1,ar.latLng.longitude+1),ar.latLng);

                    break;
                }
                case "SO2":
                {
                    color=return_Color(ar.so2);
                    myCircle= Fr_R_G_Map.gMap.addCircle(new CircleOptions().center(ar.latLng).
                            radius(100).strokeColor(Color.parseColor(color)).fillColor(Color.parseColor(color)));
                    mylist.add(myCircle);

                    break;
                }
                case "NO2":
                {
                    color=return_Color(ar.no2);
                    myCircle= Fr_R_G_Map.gMap.addCircle(new CircleOptions().center(ar.latLng).
                            radius(100).strokeColor(Color.parseColor(color)).fillColor(Color.parseColor(color)));
                    mylist.add(myCircle);
                    break;
                }
                case "O3":
                {
                    color=return_Color(ar.o3);
                    myCircle= Fr_R_G_Map.gMap.addCircle(new CircleOptions().center(ar.latLng).
                            radius(100).strokeColor(Color.parseColor(color)).fillColor(Color.parseColor(color)));

                    mylist.add(myCircle);
                    Vector vector=new Vector();
                    HashMap hashMap=new HashMap();
                    hashMap.put("dd",myCircle);
                    hashMap.remove(hashMap.get("dd"));
                    //Hashmap 객체에 키값으로 유저 아이디 air data,위치 정도?
                    break;
                }
                case "PM":
                {
                    color=return_Color(ar.pm2_5);
                    myCircle= Fr_R_G_Map.gMap.addCircle(new CircleOptions().center(ar.latLng).
                            radius(100).strokeColor(Color.parseColor(color)).fillColor(Color.parseColor(color)));
                    mylist.add(myCircle);

                    break;
                }
                default:
                {
                    color="#70e400";
                    myCircle= Fr_R_G_Map.gMap.addCircle(new CircleOptions().center(ar.latLng).
                            radius(100).strokeColor(Color.parseColor(color)).fillColor(Color.parseColor(color)));
                    mylist.add(myCircle);

                }

            }
     }


        public String return_Color(int data)
        {
            return (data< 51) ? "#7000e400" : (data < 101) ? "#70e9e918" : (data < 151) ? "#70ff7e00" : (data < 200) ? "#70ff0000" :
                    (data < 301) ? "#708f3f97" : (data < 500) ? "#707e0023" : "#707e0023";

        }
}
