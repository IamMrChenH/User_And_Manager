package user_and_manager.chenhao.com.user_and_manager.ui.activity.traffic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.BaseActivity;
import user_and_manager.chenhao.com.user_and_manager.base.BaseData;
import user_and_manager.chenhao.com.user_and_manager.base.BaseHandle;
import user_and_manager.chenhao.com.user_and_manager.config.Config;
import user_and_manager.chenhao.com.user_and_manager.http.HttpPost;
import user_and_manager.chenhao.com.user_and_manager.ui.activity.user.VioLationDetailsActivity;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.user.DioLationListAdapter;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.user.bean.VioLationItem;
import user_and_manager.chenhao.com.user_and_manager.utils.JasonUtils;

public class FindAllioLationActivity extends BaseActivity
{

    private Toolbar mToolbarView;
    private ListView mListView;
    private DioLationListAdapter mDioLationListAdapter;

    private ExecutorService mExecutorService;
    private Runnable getVioLation;
    private List<VioLationItem> mVioLationItems = null;

    private Handler mUIHandler = new BaseHandle()
    {
        @Override
        public void handleMessage(Message msg)
        {


            switch (msg.what)
            {
                case 200:
                    mDioLationListAdapter = new DioLationListAdapter(FindAllioLationActivity.this,
                            mVioLationItems);
                    mListView.setAdapter(mDioLationListAdapter);
                    break;

            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findio_lation);
        initData();
        initView();
        setItemOnclick();
    }


    private void initData()
    {
        mExecutorService = Executors.newSingleThreadExecutor();

        Log.e("233", "run:");
        getVioLation = new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Log.e("233", "run:");
                    JSONObject newJson = JasonUtils.NewJason().put("carID", BaseData.cur_carID);
                    HttpPost.post(Config.ACTION_GET_ALL_VIOLATION, newJson.toString(), new HttpPost
                            .HttpPosttListenter()
                    {
                        @Override
                        public void HttpPostResultListenter(int status, String result)
                        {
                            if (status == 200)
                            {
                                if (JasonUtils.isResult(result))
                                {
                                    mVioLationItems = JasonUtils
                                            .getListVidolation(result);
                                    Collections.reverse(mVioLationItems);
                                    mUIHandler.obtainMessage(status).sendToTarget();
                                    return;
                                } else
                                {
                                    status = 10086;
                                }
                            }


                            mUIHandler.obtainMessage(status).sendToTarget();

                        }
                    });
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }


            }
        };

        mExecutorService.submit(getVioLation);


        mVioLationItems = new ArrayList<>();
//        vioLationItems.add(new VioLationItem("10", "150", "123456x", System.currentTimeMillis()
// + "", 0 + "", "12345s"));
//        vioLationItems.add(new VioLationItem("10", "150", "123456x", System.currentTimeMillis()
// + "", 0 + "", "12345s"));
//        vioLationItems.add(new VioLationItem("10", "150", "123456x", System.currentTimeMillis()
// + "", 0 + "", "12345s"));
//        vioLationItems.add(new VioLationItem("10", "150", "123456x", System.currentTimeMillis()
// + "", 0 + "", "12345s"));
//        vioLationItems.add(new VioLationItem("10", "150", "123456x", System.currentTimeMillis()
// + "", 0 + "", "12345s"));

        mDioLationListAdapter = new DioLationListAdapter(this, mVioLationItems);

    }

    private void initView()
    {
        mToolbarView = findView(R.id.toolbar);
        mToolbarView.setTitle("违规记录");
        initTool(mToolbarView);
        mListView = findView(R.id.listview);
        mListView.setEmptyView(findView(R.id.empty));
        mListView.setAdapter(mDioLationListAdapter);
    }

    private void setItemOnclick()
    {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                showToast("++" + position);
                if (mVioLationItems.size() > 0)
                {
                    BaseData.mLationItem = mVioLationItems.get(position);
                    startActivityForResult(new Intent(FindAllioLationActivity.this,
                            VioLationDetailsActivity
                                    .class).putExtra("action", "all"), 10002);

                }
            }
        });
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mExecutorService.shutdownNow();
        mExecutorService = null;
    }
}
