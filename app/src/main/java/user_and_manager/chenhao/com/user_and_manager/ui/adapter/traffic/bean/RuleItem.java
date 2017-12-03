package user_and_manager.chenhao.com.user_and_manager.ui.adapter.traffic.bean;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import user_and_manager.chenhao.com.user_and_manager.utils.JasonUtils;

/**
 * Created by chenhao on 2017/3/25.
 */

public class RuleItem
{
    public String id;
    public String score;
    public String money;
    public String rule;


    public static HashMap<String, RuleItem> mRuleItems = null;


    public static HashMap<String, RuleItem> getJasonInstance(String result)
    {
        HashMap<String, RuleItem> hashMap = new HashMap<>();

        JSONObject newJason = JasonUtils.NewJason(result);
        if (newJason.has("trafficr"))
        {
            try
            {
                JSONArray array = new JSONArray(newJason.getString("trafficr"));
                for (int i = 0; i < array.length(); i++)
                {
                    RuleItem item = jieXiItem(array.getJSONObject(i).toString());
                    Log.e("chenhao", item.rule);
                    hashMap.put(item.rule, item);
                }

            } catch (JSONException e)
            {
                e.printStackTrace();
            }


        }


        return hashMap;
    }

    private static RuleItem jieXiItem(String item)
    {
        RuleItem item1 = new RuleItem();
        try
        {

            JSONObject newJason = JasonUtils.NewJason(item);
            if (newJason.has("id"))
            {
                item1.id = newJason.getString("id");
            }
            if (newJason.has("score"))
            {
                item1.score = newJason.getString("score");
            }
            if (newJason.has("money"))
            {
                item1.money = newJason.getString("money");
            }

            if (newJason.has("rule"))
            {
                item1.rule = newJason.getString("rule");
            }

        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        Log.e("post", item1.toString());
        return item1;
    }


    @Override
    public String toString()
    {
        return "RuleItem{" +
                "id='" + id + '\'' +
                ", score='" + score + '\'' +
                ", money='" + money + '\'' +
                ", rule='" + rule + '\'' +
                '}';
    }
}
