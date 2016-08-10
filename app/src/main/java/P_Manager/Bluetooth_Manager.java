package P_Manager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Created by user on 2016-08-08.
 */
public class Bluetooth_Manager {
    private static Bluetooth_Manager uniqueInstance;
    static Handler mHandler;
    public Bluetooth_Manager(Handler handler) {
            mHandler=handler;

    }
    public static Bluetooth_Manager getInstance() {


        return uniqueInstance;
    }
    public static void Set_Data(Bundle bundle) //Connector에서 메인에 달린 핸들러로 전송하는 함수
    {
        Message msg=new Message();
        msg.setData(bundle);
        if(mHandler!=null)
        {
            mHandler.sendMessage(msg);

        }

        //
    }
    public static void Get_Data(String string)
    {

    }
}
