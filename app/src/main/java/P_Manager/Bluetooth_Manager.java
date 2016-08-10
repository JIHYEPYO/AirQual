package P_Manager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import P_Data.Util_STATUS;

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
        // //NAV_MENU_SELECT=0; //0 DEFAULT 1 MAIN 2 REALTIME DATA 3 CHART 4 REALTIME MAP 5 HISTORY MAP 6 DEVICEMANAGEMENT
        Message msg=new Message();
        msg.setData(bundle);

        if(mHandler!=null)
        {   if(Util_STATUS.NAV_MENU_SELECT==2)
            {
                mHandler.sendMessage(msg);
            }

        }
        //msg.getData().get
            /*String dd=msg.getData().getString("data");
            String [] sensor_data=dd.split(",");
            Air_Data airData=new Air_Data(Integer.parseInt(sensor_data[0]),Integer.parseInt(sensor_data[1]),
                    Integer.parseInt(sensor_data[2]),Integer.parseInt(sensor_data[3]),Integer.parseInt(sensor_data[4]),
                    Integer.parseInt(sensor_data[4]),Integer.parseInt(sensor_data[4]));
            if(AIR_V_CONDITION) {
                p_adapter.rm.Set_realtime(airData);

                p_adapter.rcm.Set_Realchart2(airData);
            }
            else if(!AIR_V_CONDITION)
            {

            }*/
            /*
            Util_STATUS.BLUETOOTH_RECEIVCE==1 //json
            Util_STATUS.BLUETOOTH_RECEIVCE==2 //csv
            */

        //
    }
    public static void Get_Data(String string)
    {

    }
}
