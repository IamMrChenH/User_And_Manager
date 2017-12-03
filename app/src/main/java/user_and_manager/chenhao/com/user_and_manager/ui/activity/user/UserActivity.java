package user_and_manager.chenhao.com.user_and_manager.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.BaseData;
import user_and_manager.chenhao.com.user_and_manager.base.SuperBaseActivity;
import user_and_manager.chenhao.com.user_and_manager.ui.activity.LoginActivity;
import user_and_manager.chenhao.com.user_and_manager.ui.fragment.user.HomeFragment;
import user_and_manager.chenhao.com.user_and_manager.ui.fragment.user.MoreFragment;
import user_and_manager.chenhao.com.user_and_manager.ui.fragment.user.ParkCarFragment;

public class UserActivity extends SuperBaseActivity
{


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
                            .replace(R.id.content, HomeFragment.newInstance(null, null))
                            .commit();

                    return true;
                case R.id.navigation_dashboard:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, ParkCarFragment.newInstance(null, null))
                            .commit();
                    return true;
                case R.id.navigation_notifications:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, MoreFragment.newInstance(null, null))
                            .commit();
                    return true;
            }
            return false;
        }

    };

    private Toolbar mToolbarView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mToolbarView = findView(R.id.toolbar);
        initTool(mToolbarView);

        BaseData.startGetData();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, HomeFragment.newInstance(null, null))
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
                startActivity(new Intent(UserActivity.this, LoginActivity.class));
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
