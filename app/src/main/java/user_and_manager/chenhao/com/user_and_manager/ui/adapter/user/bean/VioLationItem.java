package user_and_manager.chenhao.com.user_and_manager.ui.adapter.user.bean;

/**
 * Created by chenhao on 17-3-19.
 */

public class VioLationItem
{

//    {
//        "violations":[{
//        "score":"3", "money":"150", "id":0, "time":"1489314131243", "flag":"0", "msg":
//        "??????????50%"
//    }],"result":"ok"
//    }

    public String id;
    public String time;
    public String msg;
    public String overTime;
    public String flag;
    public String carId;
    public String score;
    public String money;

    public String vXY;
    public String vID;
    public String vOffice;
    public String moneyFlag;
    public String[] photo;


    public VioLationItem()
    {
    }

    @Override
    public String toString()
    {
        return "VioLationItem{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", msg='" + msg + '\'' +
                ", overTime='" + overTime + '\'' +
                ", flag='" + flag + '\'' +
                ", carId='" + carId + '\'' +
                ", score='" + score + '\'' +
                ", money='" + money + '\'' +
                ", vXY='" + vXY + '\'' +
                ", vID='" + vID + '\'' +
                ", vOffice='" + vOffice + '\'' +
                ", moneyFlag='" + moneyFlag + '\'' +
                '}';
    }
}
