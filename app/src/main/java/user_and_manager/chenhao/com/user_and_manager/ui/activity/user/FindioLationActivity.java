package user_and_manager.chenhao.com.user_and_manager.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
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
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.user.DioLationListAdapter;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.user.bean.VioLationItem;
import user_and_manager.chenhao.com.user_and_manager.utils.JasonUtils;

public class FindioLationActivity extends BaseActivity
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
                    mDioLationListAdapter = new DioLationListAdapter(FindioLationActivity.this,
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

        getVioLation = new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    JSONObject newJson = JasonUtils.NewJason().put("carID", BaseData.cur_carID);
                    HttpPost.post(Config.ACTION_GET_VIOLATION, newJson.toString(), new HttpPost
                            .HttpPosttListenter()
                    {
                        @Override
                        public void HttpPostResultListenter(int status, String result)
                        {

                            status = swResult(status, result);
                            if (status == 200)
                            {
                                mVioLationItems = JasonUtils
                                        .getListVidolation(result);
                                Collections.reverse(mVioLationItems);
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

                if (mVioLationItems.size() > 0)
                {
                    BaseData.mLationItem = mVioLationItems.get(position);
                    startActivityForResult(new Intent(FindioLationActivity.this,
                            VioLationDetailsActivity
                                    .class), 1003);

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        try
        {
            if (data.getStringExtra("action").equals("shuaxing"))
            {
//                setResult(1000, data.putExtra("action", "shuaxing"));
                showToast("处理完成");
                finish();
            }

        } catch (Exception e)
        {

        }

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mExecutorService.shutdownNow();
        mExecutorService = null;
    }
}
