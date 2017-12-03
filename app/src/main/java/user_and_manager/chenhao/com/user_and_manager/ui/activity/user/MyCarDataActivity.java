package user_and_manager.chenhao.com.user_and_manager.ui.activity.user;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.BaseActivity;
import user_and_manager.chenhao.com.user_and_manager.base.BaseData;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.user.UserInfoListAdapter;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.user.bean.UserInfoItem;

public class MyCarDataActivity extends BaseActivity implements Runnable
{

    private Toolbar mToolbarView;
    private ListView mListView;

    private UserInfoListAdapter mUserInfoListAdapter;
    private List<UserInfoItem> mItems;

    private Handler mUIHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car_data);

        mUIHandler = new Handler(Looper.getMainLooper());
        initData();
        initViews();

    }

    private void initData()
    {
        mItems = new ArrayList<>();
        mItems.add(new UserInfoItem(1, "余额", BaseData.mMoney));
        mItems.add(new UserInfoItem(1, "小车类型", BaseData.mCarType));
        mItems.add(new UserInfoItem(1, "车牌号", BaseData.mCarNumber));
        mItems.add(new UserInfoItem(1, "驾照分", BaseData.mCardScore));

        if (BaseData.mCarState.equals("1"))
        {
            mItems.add(new UserInfoItem(1, "当前状态", "行驶中"));
        } else
        {
            mItems.add(new UserInfoItem(1, "当前状态", "停车场中"));
        }

        mItems.add(new UserInfoItem(1, "小车速度", BaseData.mSpeed));
        mItems.add(new UserInfoItem(1, "当前路线", BaseData.mLuxian));
        mItems.add(new UserInfoItem(1, "当前位置（坐标）", BaseData.mCureentXY));
        mUserInfoListAdapter = new UserInfoListAdapter(this, mItems);
    }

    private void initViews()
    {
        mToolbarView = findView(R.id.toolbar);
        mToolbarView.setTitle("我的机动车");
        initTool(mToolbarView);


        mListView = findView(R.id.listview);
        mListView.setAdapter(mUserInfoListAdapter);
        mUIHandler.postDelayed(this, 1000);


    }

    @Override
    public void run()
    {

        initData();
        mListView.setAdapter(mUserInfoListAdapter);
        mUIHandler.postDelayed(this, 1000);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mUIHandler.removeCallbacks(this);
    }
}
