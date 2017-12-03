package user_and_manager.chenhao.com.user_and_manager.base;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import user_and_manager.chenhao.com.user_and_manager.config.Config;
import user_and_manager.chenhao.com.user_and_manager.http.HttpPost;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.traffic.bean.RuleItem;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.user.bean.VioLationItem;
import user_and_manager.chenhao.com.user_and_manager.utils.JasonUtils;

/**
 * Created by chenhao on 17-3-17.
 */

public class BaseData
{
    public static VioLationItem mLationItem;

    public static String cur_carID = "1";

    public static String mName = "";
    /**
     * 昵称
     */
    public static String mNickName = "";
//    /**
//     * 电话
//     */
//    public static String mPhoneNumber = "";
    /**
     * 小车状态
     */
    public static String mCarState = "";

    /**
     * 小车类型
     */
    public static String mCarType = "";
    /**
     * 小车 车牌号
     */
    public static String mCarNumber = "111";
    /**
     * 驾照分数
     */
    public static String mCardScore = "";

    /**
     * 电话
     */
    public static String mCardPhone = "";

    public static String mMoney = "";
    /**
     * 小车速度
     */
    public static String mSpeed = "";
    /**
     * 小车路线
     */
    public static String mLuxian = "";
    /**
     * 小车方向
     */
    public static String mRound = "";
    /**
     * 小车当前位置
     */
    public static String mCureentXY = "";

    /***
     * 收费标准
     * 1次数 2小时
     */
    public static String[] mCast = new String[2];

    /***
     * 空闲车位
     */
    public static String mFreenum = "";

    /***
     * 我是否停车
     */
    public static String mIsPark = "";
    /**
     * 停车场信息
     */
    public static Map<Integer, Integer> mParkadd;


    public static volatile boolean isRun = true;

    private static ExecutorService mExecutorService;

    public static HashMap<String, RuleItem> itemHashMap;

    public static void startGetData()
    {
        mExecutorService = Executors.newSingleThreadExecutor();
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {


                while (isRun)
                {
                    try
                    {
                        final JSONObject newJason = JasonUtils.put(JasonUtils.NewJason(),
                                "carID",
                                BaseData.cur_carID);

                        new Thread()
                        {
                            @Override
                            public void run()
                            {
                                HttpPost.post(Config.ACTION_GET_PARKCAST_ACITON, newJason
                                        .toString(), new
                                        HttpPost.HttpPosttListenter()
                                        {
                                            @Override
                                            public void HttpPostResultListenter(int status,
                                                                                String result)
                                            {
                                                if (status == 200 && JasonUtils.isResult(result))
                                                {
                                                    JSONObject newJason1 = JasonUtils.NewJason
                                                            (result);
                                                    mCast = JasonUtils.jiexi(newJason1, "cast")
                                                            .split
                                                                    ("\\,");
                                                    mFreenum = JasonUtils.jiexi(newJason1,
                                                            "freenum");
                                                    mIsPark = JasonUtils.jiexi(newJason1, "ispark");
                                                    try
                                                    {
                                                        mParkadd = new HashMap<>();

                                                        JSONArray jsonArray = new JSONArray
                                                                (newJason1
                                                                        .getString("parkArray"));
                                                        for (int i = 0; i < jsonArray.length(); i++)
                                                        {
                                                            JSONObject tempJson = jsonArray
                                                                    .getJSONObject(i);

                                                            String key = JasonUtils.get(tempJson,
                                                                    "car")
                                                                    .toString();
                                                            String parkAdd = (String) JasonUtils.get
                                                                    (tempJson,
                                                                            "parkAdd").toString();
                                                            mParkadd.put(Integer.valueOf(parkAdd)
                                                                    , Integer
                                                                            .valueOf(key));
                                                        }


                                                    } catch (JSONException e)
                                                    {
                                                        e.printStackTrace();
                                                    }


                                                }
                                            }
                                        });

                            }
                        }.start();


                        new Thread()
                        {
                            @Override
                            public void run()
                            {
                                HttpPost.post(Config.ACTION_GET_CAR_MESSAGE, newJason.toString(),
                                        new
                                                HttpPost.HttpPosttListenter()
                                                {
                                                    @Override
                                                    public void HttpPostResultListenter(int status,
                                                                                        String result)
                                                    {
                                                        if (status == 200 && JasonUtils.isResult
                                                                (result))
                                                        {
                                                            JSONObject newJason1 = JasonUtils
                                                                    .NewJason
                                                                            (result);
                                                            mName = JasonUtils.jiexi(newJason1,
                                                                    "carName");
                                                            mCarState = JasonUtils.jiexi(newJason1,
                                                                    "carState");
                                                            mMoney = JasonUtils.jiexi(newJason1,
                                                                    "carMoney");
                                                            mCardPhone = JasonUtils.jiexi(newJason1,
                                                                    "carPhone");
                                                            mCardScore = JasonUtils.jiexi(newJason1,
                                                                    "cardScore");
                                                            mCarNumber = JasonUtils.jiexi(newJason1,
                                                                    "carNum");
                                                            mCarType = JasonUtils.jiexi(newJason1,
                                                                    "carType");


                                                        }
                                                    }
                                                });
                            }
                        }.start();

                        new Thread()
                        {
                            @Override
                            public void run()
                            {
                                HttpPost.post(Config.ACTION_GET_CAR_DATA, newJason.toString(), new
                                        HttpPost.HttpPosttListenter()
                                        {
                                            @Override
                                            public void HttpPostResultListenter(int status,
                                                                                String result)
                                            {
                                                if (status == 200 && JasonUtils.isResult(result))
                                                {
                                                    JSONObject newJason1 = JasonUtils.NewJason
                                                            (result);
                                                    mSpeed = JasonUtils.jiexi(newJason1, "Speed");
                                                    mRound = JasonUtils.jiexi(newJason1, "Round");
                                                    mLuxian = JasonUtils.jiexi(newJason1, "Luxian");
                                                    mCureentXY = "( " + JasonUtils.jiexi
                                                            (newJason1, "X") +
                                                            "," + JasonUtils.jiexi(newJason1,
                                                            "Y") + ")";

                                                }
                                            }
                                        });
                            }
                        }.start();

                        sleep(1000);

                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }

            }
        };
        mExecutorService.submit(thread);

    }

    public static void stop()
    {
        try
        {
            isRun = false;
            mExecutorService.shutdownNow();
            mExecutorService = null;

        } catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}
