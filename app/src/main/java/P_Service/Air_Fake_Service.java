package P_Service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

import P_Data.Air_Data;

/**
 *
 * Receive Airdata From Udooboard and send Data Service
 * Created by user on 2016-07-24.
 */
public class Air_Fake_Service extends Service {

    Handler a_handler; //핸들러에서 메인액티비티로 보내기위한 객체
    Message msg;// 핸들러로 보낼 메세지
    public static boolean RECEIVE_DATA_STATUS=false;
    public Air_Fake_Service(Handler a_handler) {
        super();
        this.a_handler=a_handler;
        Air_data_Thread ait=new Air_data_Thread();
        ait.start();

    }
    class Air_data_Thread extends Thread {
        @Override
        public void run() {
            super.run();
            while(true) {
                Air_Data aa=Get_Air_Data();

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while(!RECEIVE_DATA_STATUS) { //데이터 리시브 상태가 false인 경우
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                msg=new Message();
                msg.setData(Get_Data());

                a_handler.sendMessage(msg);
            }
        }
    }
    private Bundle Get_Data() //Json에서 받아올 데이터 일단은 랜덤으로 할당
    {
        //JSON 파싱
        Bundle bundle=new Bundle();

        bundle.putSerializable("data",Get_Air_Data());
        
        return bundle;

    }
    private Air_Data Get_Air_Data() //지금은 랜덤변수로 할당 나중에 JSON파싱과정을 이곳에다가가
    {
        return new Air_Data((int)(Math.random()*500),(int)(Math.random()*500),
                (int)(Math.random()*500),(int)(Math.random()*500),(int)(Math.random()*500),
                (int)(Math.random()*500),(int)(Math.random()*500),new LatLng(32+Math.random(),-117+Math.random()));
        //32,-117
    }
   @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
