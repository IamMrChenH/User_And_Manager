package user_and_manager.chenhao.com.user_and_manager.ui.activity.traffic;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.BaseActivity;
import user_and_manager.chenhao.com.user_and_manager.base.BaseHandle;
import user_and_manager.chenhao.com.user_and_manager.config.Config;
import user_and_manager.chenhao.com.user_and_manager.http.HttpPost;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.user.bean.VioLationItem;
import user_and_manager.chenhao.com.user_and_manager.utils.ACharView;
import user_and_manager.chenhao.com.user_and_manager.utils.JasonUtils;

public class StatisticsActivity extends BaseActivity
{
    public HashMap<String, List<VioLationItem>> hashMap;
    private Handler mUIHandler = new BaseHandle()
    {
        @Override
        public void handleMessage(Message msg)
        {

            switch (msg.what)
            {
                case 200:
                    View view = ACharView.PieChart(StatisticsActivity.this, hashMap);
                    layout.addView(view);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Toolbar mToolBarView = findView(R.id.toolbar);
        mToolBarView.setTitle("违规统计表");
        initTool(mToolBarView);
        hashMap = new HashMap<>();
        layout = findView(R.id.content);

        startData();
    }

    public void startData()
    {
        new Thread()
        {

            @Override
            public void run()
            {
                HttpPost.post(Config.ACTION_GET_ALL_VIOLATION, null, new HttpPost
                        .HttpPosttListenter()
                {
                    @Override
                    public void HttpPostResultListenter(int status, String result)
                    {
                        Log.e("299", "result: " + result);
                        switch (swResult(status, result))
                        {
                            case 200:
                                List<VioLationItem> mVioLationItems =
                                        JasonUtils.getListVidolation(result);
                                for (int i = 0; i < mVioLationItems.size(); i++)
                                {
                                    VioLationItem temp = mVioLationItems.get(i);
                                    addVioLationItem(temp.msg).add(temp);
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

    public List<VioLationItem> addVioLationItem(String key)
    {
        List<VioLationItem> temp = hashMap.get(key);
        if (temp == null)
        {
            temp = new ArrayList<>();
            hashMap.put(key, temp);
        }
        return temp;


    }

}
