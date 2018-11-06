package P_Manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;

import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;

import P_Data.Air_Data;
import P_Data.DBHelper;
import P_Data.Util_STATUS;
import P_Fragment.Fr_H_G_Map;

/**
 * Created by user on 2016-08-08.
 */
public class HGMap_Manager {
    static SQLiteDatabase db;
    static DBHelper helper;
    Context mcontext;
    public static ArrayList<Air_Data> history_data=new ArrayList<>();
    public static ArrayList<Air_Data> combine_history_data=new ArrayList<>();
    public HGMap_Manager(Context mcontext) {
        this.mcontext = mcontext;
        helper=new DBHelper(mcontext);
        db=helper.getWritableDatabase();
    }



    public static void Set_history_Map(int num) //여기서 디비안에거 다 가져
    {
        //Fr_H_G_Map.hgMap;
        if(history_data.size()>0)
        {
            for (int i=0;i<history_data.size();i++)
            {
                history_data.remove(i);
            }
        }
        if(num==0)
        {
            Cursor cursor;
            long today_start_time=helper.startOfDay()/1000;
            long before_day=(helper.startOfDay()/1000-86400);
            Date now=new Date();
            long z=now.getTime()/1000;
            cursor=db.rawQuery("SELECT * FROM Air_data where regdate between "+before_day+" AND "+z,null);

            //ListPrice BETWEEN 15 AND 25
            //+ "regdate INTEGER, "
            cursor.moveToNext();

            while(cursor.moveToNext())
            {
                // 0 index 1 time 2  CO 3 SO2 4 NO2 5 O3 6 PM 7 LAT 8 LON
                int time=cursor.getInt(1);
                double co=cursor.getInt(2);
                double so2=cursor.getInt(3);
                double no2=cursor.getInt(4);
                double o3=cursor.getInt(5);
                double pm=cursor.getInt(6);
                LatLng latLng=new LatLng(cursor.getDouble(7),cursor.getDouble(8));
                history_data.add(new Air_Data(time,0,co,so2,no2,o3,pm,latLng));
            }
            Set_circle();
        }
        else if(num==1)
        {
            Cursor cursor;
            long today_start_time=helper.startOfDay()/1000;
            long before_day=(helper.startOfDay()/1000-86400);
            Date now=new Date();
            long z=now.getTime()/1000;
            cursor=db.rawQuery("SELECT * FROM Air_data where regdate between "+before_day+" AND "+z,null);

            //ListPrice BETWEEN 15 AND 25
            //+ "regdate INTEGER, "
            cursor.moveToNext();

            while(cursor.moveToNext())
            {
                // 0 index 1 time 2  CO 3 SO2 4 NO2 5 O3 6 PM 7 LAT 8 LON
                int time=cursor.getInt(1);
                double co=cursor.getInt(2);
                double so2=cursor.getInt(3);
                double no2=cursor.getInt(4);
                double o3=cursor.getInt(5);
                double pm=cursor.getInt(6);
                LatLng latLng=new LatLng(cursor.getDouble(7),cursor.getDouble(8));
                combine_history_data.add(new Air_Data(time,0,co,so2,no2,o3,pm,latLng));
            }
            Set_combine_circle();
        }

    }
    public static ArrayList<Circle> add_circle=new ArrayList<>();
    public static ArrayList<Air_Data> air_datas=new ArrayList<>();
    public static ArrayList<Marker> add_marker=new ArrayList<>();
    public static  void Set_circle()
    {

        Circle circle;
        for(int i=0;i<add_circle.size();i++)
        {
            add_circle.get(i).remove();
            add_marker.get(i).remove();
        }
        for(int i=0;i<add_circle.size();i++)
        {
            add_circle.remove(i);
            add_marker.remove(i);
        }
        for(int i=0;i<history_data.size();i++)
        {     Air_Data ar = history_data.get(i);
              //(32.8824585,-117.2347713)
              //ar.latLng=new LatLng(ar.latLng.latitude,ar.latLng.longitude*1000);
           // ar.latLng=new LatLng(((double)(Math.round(((float)ar.latLng.latitude*1000))/1000)),(Math.round(((float)ar.latLng.longitude*1000))/1000));
            float lat=((float)Math.round((ar.latLng.latitude*3000))/3000);
            float lng=((float)Math.round((ar.latLng.longitude*3000))/3000);
            ar.latLng=new LatLng(lat,lng);
            //ar.latLng=new LatLng(0,0);

            //ar.latLng=new LatLng(((float)Math.round((ar.latLng.latitude*3000))/3000),((float)Math.round((ar.latLng.longitude*3000))/3000));
            String color= Set_Color();
            if(add_circle.size()==0) {

                color = Set_Color();
                CircleOptions circleOptions = new CircleOptions()
                        .center(ar.latLng)
                        .radius(70)
                        .fillColor(Color.parseColor(color))
                        .strokeColor(Color.TRANSPARENT);
                circle = Fr_H_G_Map.hgMap.addCircle(circleOptions);
                Marker marker= Fr_H_G_Map.hgMap.addMarker(new MarkerOptions().position(ar.latLng).draggable(false).visible(true).snippet(""));
                add_marker.add(marker);
                add_circle.add(circle);
                air_datas.add(ar);
            }
            // (32.8824585,-117.2347713)
            //
            else
            {
                for(int z=0;z<add_circle.size();z++)
                {

                    if(add_circle.get(z).getCenter().longitude==ar.latLng.longitude&&
                            add_circle.get(z).getCenter().latitude==ar.latLng.latitude)
                    {
                        //여기안에 air 데이터로 AQI 계산할거임 그위치의 데이터
                        air_datas.add(ar);
                        break;
                    }
                    // (32.8824585,-117.2347713)
                    else if(z==add_circle.size()-1)
                    {
                        CircleOptions circleOptions = new CircleOptions()
                                .center(ar.latLng)
                                .radius(70)
                                .fillColor(Color.parseColor(color))
                                .strokeColor(Color.TRANSPARENT);
                        circle = Fr_H_G_Map.hgMap.addCircle(circleOptions);
                        Marker marker= Fr_H_G_Map.hgMap.addMarker(new MarkerOptions().position(ar.latLng).draggable(false).visible(true).snippet(""));
                        add_marker.add(marker);
                        add_circle.add(circle);
                        air_datas.add(ar);
                    }
                }
            }
        }

    }
    public static void Set_combine_circle()
    {
        Circle circle;
        for(int i=0;i<add_circle.size();i++)
        {
            add_circle.get(i).remove();
            add_marker.get(i).remove();
        }
        for(int i=0;i<add_circle.size();i++)
        {
            add_circle.remove(i);
            add_marker.remove(i);
        }
        for(int i=0;i<combine_history_data.size();i++)
        {
            Air_Data ar = combine_history_data.get(i);
            //(32.8824585,-117.2347713)
            //ar.latLng=new LatLng(ar.latLng.latitude,ar.latLng.longitude*1000);
            // ar.latLng=new LatLng(((double)(Math.round(((float)ar.latLng.latitude*1000))/1000)),(Math.round(((float)ar.latLng.longitude*1000))/1000));
            //ar.latLng=new LatLng(((float)Math.round((ar.latLng.latitude*300))/300),((float)Math.round((ar.latLng.longitude*300))/300));
            float lat=((float)Math.round((ar.latLng.latitude*500))/500);
            float lng=((float)Math.round((ar.latLng.longitude*500))/500);
            ar.latLng=new LatLng(lat,lng);
            String color= Set_Color();
            if(add_circle.size()==0) {

                color = Set_Color();
                CircleOptions circleOptions = new CircleOptions()
                        .center(ar.latLng)
                        .radius(180)
                        .fillColor(Color.parseColor(color))
                        .strokeColor(Color.TRANSPARENT);
                circle = Fr_H_G_Map.hgMap.addCircle(circleOptions);
                circle.setClickable(true);
                add_circle.add(circle);
                air_datas.add(ar);
                Marker marker= Fr_H_G_Map.hgMap.addMarker(new MarkerOptions().position(ar.latLng).draggable(false).visible(true).snippet(""));

                add_marker.add(marker);
            }
            // (32.8824585,-117.2347713)
            //
            else
            {
                for(int z=0;z<add_circle.size();z++)
                {


                    if(add_circle.get(z).getCenter().longitude==ar.latLng.longitude&&
                            add_circle.get(z).getCenter().latitude==ar.latLng.latitude)
                    {
                        //여기안에 air 데이터로 AQI 계산할거임 그위치의 데이터
                        air_datas.add(ar);
                       break;
                    }
                    // (32.8824585,-117.2347713)
                    else if(z==add_circle.size()-1)
                    {
                        CircleOptions circleOptions = new CircleOptions()
                                .center(ar.latLng)
                                .radius(180)
                                .fillColor(Color.parseColor(color))
                                .strokeColor(Color.TRANSPARENT);
                        circle = Fr_H_G_Map.hgMap.addCircle(circleOptions);
                        circle.setClickable(true);
                        add_circle.add(circle);
                        air_datas.add(ar);
                        Marker marker= Fr_H_G_Map.hgMap.addMarker(new MarkerOptions().position(ar.latLng).draggable(false).visible(true).snippet(""));
                        add_marker.add(marker);
                    }
                }
            }

        }

    }

    public void get_AQI()
    {

    }
    public static String Set_Color()
    {
        String color;
        switch (Util_STATUS.GMap_Realtime_set)
        {
            case "CO":
            {
                color= (Util_STATUS.AQI_CO< 51) ? "#7000e400" : (Util_STATUS.AQI_CO < 101) ? "#70e9e918" : (Util_STATUS.AQI_CO < 151) ? "#70ff7e00" : (Util_STATUS.AQI_CO < 200) ? "#70ff0000" :
                        (Util_STATUS.AQI_CO < 301) ? "#708f3f97" : (Util_STATUS.AQI_CO < 500) ? "#707e0023" : "#707e0023";
                break;
            }
            case "SO2":
            {
                color= (Util_STATUS.AQI_SO2< 51) ? "#7000e400" : (Util_STATUS.AQI_SO2 < 101) ? "#70e9e918" : (Util_STATUS.AQI_SO2 < 151) ? "#70ff7e00" : (Util_STATUS.AQI_SO2 < 200) ? "#70ff0000" :
                        (Util_STATUS.AQI_SO2 < 301) ? "#708f3f97" : (Util_STATUS.AQI_SO2 < 500) ? "#707e0023" : "#707e0023";
                break;
            }
            case "NO2":
            {
                color= (Util_STATUS.AQI_NO2< 51) ? "#7000e400" : (Util_STATUS.AQI_NO2 < 101) ? "#70e9e918" : (Util_STATUS.AQI_NO2< 151) ? "#70ff7e00" : (Util_STATUS.AQI_NO2< 200) ? "#70ff0000" :
                        (Util_STATUS.AQI_NO2 < 301) ? "#708f3f97" : (Util_STATUS.AQI_NO2< 500) ? "#707e0023" : "#707e0023";
                break;
            }
            case "O3":
            {
                color= (Util_STATUS.AQI_O3< 51) ? "#7000e400" : (Util_STATUS.AQI_O3 < 101) ? "#70e9e918" : (Util_STATUS.AQI_O3 < 151) ? "#70ff7e00" : (Util_STATUS.AQI_O3< 200) ? "#70ff0000" :
                        (Util_STATUS.AQI_O3 < 301) ? "#708f3f97" : (Util_STATUS.AQI_O3< 500) ? "#707e0023" : "#707e0023";

                break;
            }
            case "PM":
            {
                color= (Util_STATUS.AQI_PM< 51) ? "#7000e400" : (Util_STATUS.AQI_PM < 101) ? "#70e9e918" : (Util_STATUS.AQI_PM< 151) ? "#70ff7e00" : (Util_STATUS.AQI_PM< 200) ? "#70ff0000" :
                        (Util_STATUS.AQI_PM < 301) ? "#708f3f97" : (Util_STATUS.AQI_PM < 500) ? "#707e0023" : "#707e0023";
                break;
            }
            default:
            {
                color="#70e400";

            }

        }
        return color;
    }

    public static void Change_Color()
    {
        for(int i=0;i<add_circle.size();i++)
        {
            add_circle.get(i).setFillColor(Color.parseColor(Set_Color()));
        }
    }




}
