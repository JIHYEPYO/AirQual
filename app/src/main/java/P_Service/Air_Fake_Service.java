package P_Service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.example.pyojihye.airpollution.HttpConnection;
import com.example.pyojihye.airpollution.activity.MainActivity;

import P_Data.Util_STATUS;

/**
 *
 * Receive Airdata From Udooboard and send Data Service
 * Created by user on 2016-07-24.
 */
public class Air_Fake_Service extends Service {

    Handler a_handler; //핸들러에서 메인액티비티로 보내기위한 객체
    Message msg;// 핸들러로 보낼 메세지
    public static boolean RECEIVE_DATA_STATUS=false;
    public Air_Fake_Service() {
        super();
        Air_data_Thread ait=new Air_data_Thread();
        ait.start();

    }
    class Air_data_Thread extends Thread {
        @Override
        public void run() {
            super.run();
            while(true) {


                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while(Util_STATUS.NAV_MENU_SELECT!=4) { //데이터 리시브 상태가 false인 경우
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Util_STATUS.HTTP_CONNECT_KIND=5;
                HttpConnection httpConnectionreqconn
                        = new HttpConnection(MainActivity.getInstance(),MainActivity.getInstance().getApplicationContext(),MainActivity.getInstance().htt_handler);
                httpConnectionreqconn.execute();
            }
        }
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
