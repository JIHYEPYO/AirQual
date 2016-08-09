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
        // 아직 인스턴스가 만들어지지 않았다면 private으로 선언된 생성자를 이용해서 객체를 만들고
        // uniqueInstance에 그 객체를 대입합니다. 이렇게 하면 인스턴스가 필요한 상황이 닥치기 전에는
        // 아예 인스턴스를 생성하지 않게 되죠. 이런 방법을 "lazy instantiation"이라고 한다.
        //if(uniqueInstance == null)
           //uniqueInstance = new Bluetooth_Manager();

        return uniqueInstance;
    }
    public static void Set_Data(Bundle bundle) //Connector에서 메인에 달린 핸들러로 전송하는 함수
    {
        Message msg=new Message();
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }
    public static void Get_Data(String string)
    {

    }
}
