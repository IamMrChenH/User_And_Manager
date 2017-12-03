package user_and_manager.chenhao.com.user_and_manager.ui.activity.user;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.BaseActivity;

/**
 * 我的驾照
 */
public class DriverLicenseActivity extends BaseActivity {
    private Toolbar mToolbarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_license);
        initData();
        initViews();

    }

    private void initData() {
    }

    private void initViews() {
        mToolbarView = findView(R.id.toolbar);
        mToolbarView.setTitle("我的驾照");
        initTool(mToolbarView);

    }
}
