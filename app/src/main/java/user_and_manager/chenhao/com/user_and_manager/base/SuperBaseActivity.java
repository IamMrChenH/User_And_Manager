package user_and_manager.chenhao.com.user_and_manager.base;

import android.content.Intent;

import user_and_manager.chenhao.com.user_and_manager.ui.activity.LoginActivity;

/**
 * Created by chenhao on 2017/3/29.
 */

public class SuperBaseActivity extends BaseActivity
{
    private long time;
    private volatile boolean isExit = false;

    @Override
    public void onBackPressed()
    {
        //TODO something
//        super.onBackPressed();
        if (!isExit)
        {
            time = System.currentTimeMillis();
            showToast("再点击一次就退出了！");
            isExit = true;
        } else if (System.currentTimeMillis() - time < 2000)
        {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        new Thread()
        {
            @Override
            public void run()
            {

                try
                {
                    sleep(2000);
                    isExit = false;
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

            }
        }.start();


    }
}
