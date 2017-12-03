package user_and_manager.chenhao.com.user_and_manager.ui.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.BaseData;
import user_and_manager.chenhao.com.user_and_manager.base.SuperBaseActivity;
import user_and_manager.chenhao.com.user_and_manager.ui.activity.LoginActivity;
import user_and_manager.chenhao.com.user_and_manager.ui.fragment.admin.AdminHomeFragment;
import user_and_manager.chenhao.com.user_and_manager.ui.fragment.admin.AdminMsgFragment;

public class AdminActivity extends SuperBaseActivity
{

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener()
    {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.navigation_home:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, AdminHomeFragment.newInstance(null, null))
                            .commit();
                    return true;
                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, AdminMsgFragment.newInstance(null, null))
                            .commit();
                    return true;
                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };
    private Toolbar mToolbarView;
    private Intent mUpdateService;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic);
        BaseData.startGetData();

        mToolbarView = findView(R.id.toolbar);
        mToolbarView.setTitle("停车场管理员");
        initTool(mToolbarView);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, AdminHomeFragment.newInstance(null, null))
                .commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                startActivity(new Intent(AdminActivity.this, LoginActivity.class));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        BaseData.stop();
    }


}
