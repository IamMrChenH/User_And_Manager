package user_and_manager.chenhao.com.user_and_manager.ui.activity.user;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.BaseActivity;
import user_and_manager.chenhao.com.user_and_manager.base.BaseData;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.user.UserInfoListAdapter;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.user.bean.UserInfoItem;

public class UserInfoActivity extends BaseActivity
{
    private Toolbar mToolbarView;

    private ListView mListView;
    private UserInfoListAdapter mUserInfoListAdapter;
    private List<UserInfoItem> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);


        initDatas();
        initViews();

    }

    private void initDatas()
    {
        mItems = new ArrayList<>();
        mItems.add(new UserInfoItem(0, "头像", R.mipmap.ic_launcher_round));
        mItems.add(new UserInfoItem(1, "昵称", BaseData.mName));
        mItems.add(new UserInfoItem(1, "电话号码", BaseData.mCardPhone));
        mItems.add(new UserInfoItem(1, "驾照分", BaseData.mCardScore));
        mItems.add(new UserInfoItem(1, "小车类型", BaseData.mCarType));
        mItems.add(new UserInfoItem(1, "车牌号", BaseData.mCarNumber));
        mItems.add(new UserInfoItem(1, "生日", ""));
        mItems.add(new UserInfoItem(1, "所在地", ""));
        mItems.add(new UserInfoItem(1, "职业", ""));
        mItems.add(new UserInfoItem(2, "实名认证", ""));
        mItems.add(new UserInfoItem(0, "账号管理", R.mipmap.arrow));
        mUserInfoListAdapter = new UserInfoListAdapter(this, mItems);
    }

    private void initViews()
    {
        mToolbarView = findView(R.id.toolbar);
        mToolbarView.setTitle("个人信息");
        initTool(mToolbarView);

        mListView = findView(R.id.listview);
        mListView.setAdapter(mUserInfoListAdapter);

    }
}
