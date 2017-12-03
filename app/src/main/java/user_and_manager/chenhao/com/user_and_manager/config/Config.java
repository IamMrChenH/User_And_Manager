package user_and_manager.chenhao.com.user_and_manager.config;

import android.os.Environment;
import android.util.Log;

/**
 * Created by chenhao on 17-3-16.
 */

public class Config
{
    public static String SERVER_IP = "192.168.191.2";
    public static int SERVER_PORT = 8890;

    public static final String ACTION_LOGIN = "Login";
    public static final String ACTION_REGISTER = "Register";


    public static final String ACTION_ADD_VIOLATION = "AddViolation";
    public static final String ACTION_ADD_MONEY = "AddMoney";
    public static final String ACTION_GET_PARKCAST_ACITON = "GetParkCastAciton";

    public static final String ACTION_GET_CAR_MESSAGE = "GetCarMessage";
    public static final String ACTION_GET_VIOLATION = "GetViolation";
    public static final String ACTION_GET_CAR_DATA = "GetCarData";

    public static final String ACTION_GET_ALL_VIOLATION = "GetAllViolation";
    public static final String ACTION_GET_TRAFFICR = "GetTrafficR";
    public static final String ACTION_UPDATEV = "UpdateV";


    public static final String ACTION_GET_ALL_CAR_DATA = "GetAllCarData";
    public static final String ACTION_PARK = "Parking";
    public static final String ACTION_UP_DATA = "up";


    public static final String ACTION_UP_DATEFEES = "UpdateFees";
    public static final String ACTION_GET_ALL_MONEY_LOG = "GetAllMoneyLog";
    public static final String ACTION_GET_MONEY_LOG = "GetMoneyLog";


    public static final String SAVA_PHOTO_PATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/";
    public static final int UP_THREAD_MAXSIZE = 1;

    public static String getServerAddress(String action)
    {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("http://");
        stringBuffer.append(SERVER_IP);
        stringBuffer.append(":");
        stringBuffer.append(SERVER_PORT);
        stringBuffer.append("/type/jason/action/");
        stringBuffer.append(action);
        return stringBuffer.toString();

    }

    public static String getServerAddress(int port, String action)
    {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("http://");
        stringBuffer.append(SERVER_IP);
        stringBuffer.append(":");
        stringBuffer.append(port);
//        stringBuffer.append("/type/jason/action/");
        stringBuffer.append("/");
        stringBuffer.append(action);
        String s = stringBuffer.toString();
        Log.e("233", "getServerAddress: " + s);
        return s;

    }


}
