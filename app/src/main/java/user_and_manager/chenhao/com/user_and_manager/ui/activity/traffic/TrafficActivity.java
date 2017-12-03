package user_and_manager.chenhao.com.user_and_manager.ui.activity.traffic;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.HashMap;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.SuperBaseActivity;
import user_and_manager.chenhao.com.user_and_manager.config.Config;
import user_and_manager.chenhao.com.user_and_manager.http.HttpPost;
import user_and_manager.chenhao.com.user_and_manager.service.UpdateService;
import user_and_manager.chenhao.com.user_and_manager.ui.activity.LoginActivity;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.traffic.bean.RuleItem;
import user_and_manager.chenhao.com.user_and_manager.ui.fragment.traffic.TrafficHomeFragment;
import user_and_manager.chenhao.com.user_and_manager.ui.fragment.traffic.TrafficMsgFragment;

import static user_and_manager.chenhao.com.user_and_manager.base.BaseData.itemHashMap;

public class TrafficActivity extends SuperBaseActivity
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
                            .replace(R.id.content, TrafficHomeFragment.newInstance(null, null))
                            .commit();
                    return true;
                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content, TrafficMsgFragment.newInstance(null, null))
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

        mToolbarView = findView(R.id.toolbar);
        initTool(mToolbarView);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, TrafficHomeFragment.newInstance(null, null))
                .commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        mUpdateService = new Intent(this, UpdateService.class);
        startService(mUpdateService);


        new Thread()
        {
            @Override
            public void run()
            {
                HttpPost.post(Config.ACTION_GET_TRAFFICR, null, new HttpPost
                        .HttpPosttListenter()
                {
                    @Override
                    public void HttpPostResultListenter(int status, String result)
                    {
                        if (swResult(status, result) == 200)
                        {
                            itemHashMap = new HashMap<>();
                            itemHashMap = RuleItem.getJasonInstance(result);
                        }

                    }
                });

            }
        }.start();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                startActivity(new Intent(TrafficActivity.this, LoginActivity.class));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        stopService(mUpdateService);
    }
}
