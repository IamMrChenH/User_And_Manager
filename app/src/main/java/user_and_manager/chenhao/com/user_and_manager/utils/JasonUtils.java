package user_and_manager.chenhao.com.user_and_manager.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import user_and_manager.chenhao.com.user_and_manager.ui.adapter.user.bean.VioLationItem;

/**
 * Created by chenhao on 17-3-17.
 */

public class JasonUtils
{

    public static JSONObject NewJason()
    {
        return new JSONObject();
    }

    public static JSONObject NewJason(String result)
    {
        try
        {
            return new JSONObject(result);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject NewJason(String flag, String result)
    {
        try
        {

            JSONObject object = new JSONObject();
            object.put(flag, result);
            return object;
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }


    public static JSONObject put(JSONObject object, String action, String msg)
    {
        try
        {
            object.put(action, msg);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        return object;
    }


    public static boolean isResult(String result)
    {
        JSONObject newJason = NewJason(result);
        try
        {
            if (newJason.has("result") && newJason.getString("result").equals("ok"))
            {
                return true;
            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return false;
    }


    public static String getResultCarId(String result)
    {
        JSONObject newJason = NewJason(result);
        if (newJason.has("carID"))
        {
            try
            {
                return newJason.getString("carID");
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static String jiexi(JSONObject obj, String key)
    {

        if (obj.has(key))
        {
            try
            {
                return obj.getString(key);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }


    public static Object get(JSONObject jsonObject, String key)
    {
        try
        {
            return jsonObject.get(key);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    public static VioLationItem getVioLationItem(String result)
    {
        JSONObject newJason = NewJason(result);
        VioLationItem item = new VioLationItem();
        if (newJason.has("id"))
        {
            item.id = get(newJason, "id").toString();
        }
        if (newJason.has("vTime"))
        {
            item.time = get(newJason, "vTime").toString();
        }
        if (newJason.has("vMsg"))
        {
            item.msg = get(newJason, "vMsg").toString();
        }
        if (newJason.has("overTime"))
        {
            item.overTime = get(newJason, "overTime").toString();
        }
        if (newJason.has("flag"))
        {
            item.flag = get(newJason, "flag").toString();
        }
        if (newJason.has("carId"))
        {
            item.carId = get(newJason, "carId").toString();
        }

        if (newJason.has("score"))
        {
            item.score = get(newJason, "score").toString();
        }
        if (newJason.has("money"))
        {
            item.money = get(newJason, "money").toString();
        }
        if (newJason.has("vXY"))
        {
            item.vXY = get(newJason, "vXY").toString();
        }
        if (newJason.has("vID"))
        {
            item.vID = get(newJason, "vID").toString();
        }
        if (newJason.has("vOffice"))
        {
            item.vOffice = get(newJason, "vOffice").toString();
        }
        if (newJason.has("moneyFlag"))
        {
            item.moneyFlag = get(newJason, "moneyFlag").toString();
        }
        if (newJason.has("photo"))
        {
            String src = get(newJason, "photo").toString();
            JSONArray array = null;
            try
            {
                array = new JSONArray(src);
                int lenght = array.length();
                item.photo = new String[lenght];

                for (int i = 0; i < lenght; i++)
                {
                    item.photo[i] = array.getString(i);
                    Log.e("jiexi", "---->" + item.photo[i]);
                }
            } catch (JSONException e)
            {
                e.printStackTrace();
            }


        }


        Log.e("233", "getVioLationItem: " + item.toString());

        return item;
    }

    public static List<VioLationItem> getListVidolation(String result)
    {
        List<VioLationItem> mLationItems = null;
        JSONArray newSONArray = null;
        JSONObject newJason = NewJason(result);
        if (isResult(result) && newJason.has("violations"))
        {
            try
            {
                newSONArray = new JSONArray(newJason.getString("violations"));
                mLationItems = new ArrayList<>();
                for (int i = 0; i < newSONArray.length(); i++)
                {
                    String tempString = newSONArray.getJSONObject(i).toString();
                    VioLationItem vioLationItem = getVioLationItem(tempString);
                    mLationItems.add(vioLationItem);

                }
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        return mLationItems;
    }
}
