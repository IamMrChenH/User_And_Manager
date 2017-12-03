package user_and_manager.chenhao.com.user_and_manager.ui.activity.admin;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.BaseActivity;
import user_and_manager.chenhao.com.user_and_manager.base.BaseHandle;
import user_and_manager.chenhao.com.user_and_manager.base.BaseListGridAdapter;
import user_and_manager.chenhao.com.user_and_manager.config.Config;
import user_and_manager.chenhao.com.user_and_manager.http.HttpPost;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.admin.MoneyLogItem;

public class AddRecordActivity extends BaseActivity
{
    private ListView mListView;
    private List<MoneyLogItem> logItems;
    private Handler mUIHandler = new BaseHandle()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == 200)
            {
                mListViewAdapter mListViewAdapter = new mListViewAdapter(AddRecordActivity
                        .this, logItems);
                mListView.setAdapter(mListViewAdapter);
            }


            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        Toolbar mToolBarView = findView(R.id.toolbar);
        mToolBarView.setTitle("充值记录");
        initTool(mToolBarView);
        mListView = findView(R.id.listview);
        logItems = new ArrayList<>();
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
                        HttpPost.HttpPosttListenter()
                        {
                            @Override
                            public void HttpPostResultListenter(int status, String result)
                            {
                                status = swResult(status, result);
                                if (status == 200)
                                {
                                    List<MoneyLogItem> tempList = MoneyLogItem.jieXiListItem
                                            (result);
                                    for (MoneyLogItem item : tempList)
                                    {
                                        if (item.type == 1)
                                        {
                                            logItems.add(item);
                                        }
                                    }
                                    Collections.reverse(logItems);
                                }
                                mUIHandler.obtainMessage(status).sendToTarget();
                            }
                        });

            }
        }.start();
    }


    class mListViewAdapter extends BaseListGridAdapter<MoneyLogItem>
    {

        public mListViewAdapter(Context c, List<MoneyLogItem> logItems)
        {

            super(c, logItems);
        }


        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (convertView == null)
            {
                convertView = getLayoutInflater().inflate(R.layout.item_line_1, null);
            }
//            convertView.findViewById(R.id.item_line_imager);
            MoneyLogItem item = getItem(position);
            TextView t1 = (TextView) convertView.findViewById(R.id.item_line_topText);
            t1.setText("小车" + item.cid + "在时间：" + getTime(item.time) + "充值" + item.money + "元");
            return convertView;
        }
    }


}
