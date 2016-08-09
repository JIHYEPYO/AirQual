package P_Data;

/**
 * Created by user on 2016-08-02.
 */
public class Util_STATUS {


    //public static
    public static String GMap_Realtime_set="CO"; //show google real time circle data
    public static boolean AIR_V_CONDITION=false;
    public static boolean BLUETOOTH_CONNECTION=false;
    public static boolean REAL_MAP_STATE=false;
    public static String Chart_Select="CO"; //Show data chart select
    public static boolean RECEIVE_DATA_STATUS=false;
    public static boolean HAVE_UDOODeviceID=false;   //Save Udoo mac to preference
    public static boolean HAVE_HEARTDeviceID=false; //Save heart mac to preference
    public static int SELECT_BLUETOOTH=0; //0 UDOO 1 HEART
    public static int HTTP_CONNECT_KIND=0; //0 DEFAULT 1 SIGN IN& SIGN UP  2 DEVICE REGISTER // 3 CONNECTION REQUEST
                                             //4 RESPONSE HISTORY DATA 5 GET REAL TIME USER DATA
    public static int BLUETOOTH_RECEIVCE=0; //ready to receive bluetooth data
                                              //0 not ready 1 json 2 csv

    //public static boolean

    public Util_STATUS() {

    }


}
