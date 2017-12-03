package user_and_manager.chenhao.com.user_and_manager.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

import java.text.SimpleDateFormat;
import java.util.Locale;

import user_and_manager.chenhao.com.user_and_manager.utils.JasonUtils;
import user_and_manager.chenhao.com.user_and_manager.utils.ToastUtils;

/**
 * Created by chenhao on 17-3-16.
 */

public class BaseActivity extends AppCompatActivity
{
    private static ProgressDialog mProgressDialog;

    public void showProgressDialog()
    {
        if (mProgressDialog != null)
        {
            mProgressDialog.dismiss();

        } else
        {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }


    }


    public <T extends View> T findView(int id)
    {

        return (T) findViewById(id);
    }

    public void initTool(Toolbar toolbar)
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    public void showToast(String msg)
    {
        ToastUtils.showToast(msg);

    }

    public int swResult(int status, String result)
    {
        if (status == 200)
        {
            if (!JasonUtils.isResult(result))
                status = 201;
        }

        return status;
    }

    public void addAnimation(View view)
    {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(3500);
        alphaAnimation.setFillAfter(true);

        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, -50, 0);
        translateAnimation.setDuration(3000);
        translateAnimation.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.addAnimation(alphaAnimation);
        set.addAnimation(translateAnimation);

        view.setAnimation(set);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

    }

    /*************************************************************************/
    public String getTime(long date)
    {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss",
                Locale.CHINA);
        Long aLong = null;
        try
        {
            return sdr.format(date);
        } catch (NumberFormatException e)
        {
            e.printStackTrace();
            return "无";
        }
    }

    public String getTime(String date)
    {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss",
                Locale.CHINA);
        Long aLong = null;
        try
        {
            aLong = Long.valueOf(date);
            return "时间:" + sdr.format(aLong);
        } catch (NumberFormatException e)
        {
            e.printStackTrace();
            return "无";
        }

    }

    public void startZhiFuBao()
    {

        try
        {
            Intent intent = new Intent();
            //qq com.tencent.mobileqq
            //
            intent = getPackageManager().getLaunchIntentForPackage("com.eg.android.AlipayGphone");

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent
                    .FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

//        intent.setComponent(new ComponentName("com.eg.android.AlipayGphone", null));
            startActivity(intent);
        } catch (Exception e)
        {
            showToast("没有安装支付宝！快去安装。");
        }

    }

    public void startWeixing()
    {

        try
        {
            Intent intent = new Intent();
            //qq com.tencent.mobileqq
            //
            intent = getPackageManager().getLaunchIntentForPackage("ccom.tencent.mm");

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent
                    .FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

//        intent.setComponent(new ComponentName("com.eg.android.AlipayGphone", null));
            startActivity(intent);
        } catch (Exception e)
        {
            showToast("没有安装微信！快去安装。");
        }

    }
}
