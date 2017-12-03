package user_and_manager.chenhao.com.user_and_manager.ui.adapter.admin;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import user_and_manager.chenhao.com.user_and_manager.utils.JasonUtils;

/**
 * Created by chenhao on 2017/3/26.
 */

public class MoneyLogItem
{


    public int money;
    public String mark;
    public long time;
    public int type;
    public int cid;


    public MoneyLogItem()
    {
    }

    public MoneyLogItem(int money, String mark, long time, int type, int cid)
    {
        this.money = money;
        this.mark = mark;
        this.time = time;
        this.type = type;
        this.cid = cid;
    }

    public static List<MoneyLogItem> jieXiListItem(String result)
    {
        List<MoneyLogItem> moneyLogItems = new ArrayList<>();

        JSONObject object = JasonUtils.NewJason(result);
        if (object.has("moneyLogs"))
        {
            try
            {
                JSONArray array = new JSONArray(object.getString("moneyLogs"));
                for (int i = 0; i < array.length(); i++)
                {
                    MoneyLogItem item = getItem(array.getString(i));
                    moneyLogItems.add(item);
                }


            } catch (JSONException e)
            {
                e.printStackTrace();
            }

        }
        return moneyLogItems;

    }

    @Override
    public String toString()
    {
        return "MoneyLogItem{" +
                "money=" + money +
                ", mark='" + mark + '\'' +
                ", time=" + time +
                ", type=" + type +
                ", cid=" + cid +
                '}';
    }

    private static MoneyLogItem getItem(String item)
    {

        try
        {
            JSONObject object = JasonUtils.NewJason(item);
            int money = Integer.valueOf(hasKey(object, "money").toString());

            String mark = hasKey(object, "mark").toString();
            long time = Long.valueOf(hasKey(object, "time").toString());
            int type = Integer.valueOf(hasKey(object, "type").toString());
            int cid = Integer.valueOf(hasKey(object, "cid").toString());
            MoneyLogItem logItem = new MoneyLogItem(money, mark, time, type, cid);

            Log.e("item", "" + logItem.toString());
            return logItem;
        } catch (Exception e)
        {
            e.printStackTrace();
        }


        return null;
    }

    private static Object hasKey(JSONObject jsonObject, String key)
    {
        try
        {
            if (jsonObject.has(key))
            {

                return jsonObject.get(key);

            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return "无";
    }


}
