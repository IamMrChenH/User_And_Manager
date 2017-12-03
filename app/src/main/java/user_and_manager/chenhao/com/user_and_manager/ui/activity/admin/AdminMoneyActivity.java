package user_and_manager.chenhao.com.user_and_manager.ui.activity.admin;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.BaseActivity;
import user_and_manager.chenhao.com.user_and_manager.base.BaseHandle;
import user_and_manager.chenhao.com.user_and_manager.config.Config;
import user_and_manager.chenhao.com.user_and_manager.http.HttpPost;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.admin.MoneyLogItem;
import user_and_manager.chenhao.com.user_and_manager.utils.ACharView;

public class AdminMoneyActivity extends BaseActivity
{

    private LinearLayout layout;
    private List<MoneyLogItem> logItems;
    public HashMap<String, Integer> hashMap;
    private Handler mUIHandler = new BaseHandle()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == 200)
            {
                layout.addView(ACharView.xychar(getApplication(), hashMap, "收益统计"));
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_money);
        Toolbar mToolBarView = findView(R.id.toolbar);
        mToolBarView.setTitle("停车场统计表");
        initTool(mToolBarView);
        layout = findView(R.id.content);
        hashMap = new HashMap<>();
        startData();
    }

    public void startData()
    {
        new Thread()
        {

            @Override
            public void run()
            {
                HttpPost.post(Config.ACTION_GET_ALL_MONEY_LOG, new JSONObject().toString(), new
                        HttpPost
                                .HttpPosttListenter()
                        {
                            @Override
                            public void HttpPostResultListenter(int status, String result)
                            {
                                switch (swResult(status, result))
                                {
                                    case 200:
                                        logItems = MoneyLogItem.jieXiListItem(result);
                                        for (MoneyLogItem item : logItems)
                                        {
                                            String key = getTime(item.time);
                                            Integer integer = addKeyData(key);
                                            integer += item.money;
                                            hashMap.put(key, integer);
                                        }
                                        mUIHandler.obtainMessage(200).sendToTarget();
                                        break;
                                    case 201:
                                        mUIHandler.obtainMessage(201).sendToTarget();
                                        break;
                                    default:
                                        mUIHandler.obtainMessage(10086).sendToTarget();
                                        break;

                                }

                            }
                        });

            }
        }.start();
    }

    public Integer addKeyData(String key)
    {
        if (hashMap.get(key) == null)
        {
            hashMap.put(key, 0);
        }
        return hashMap.get(key);
    }


}
