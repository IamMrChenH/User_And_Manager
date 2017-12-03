package user_and_manager.chenhao.com.user_and_manager.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.BaseActivity;
import user_and_manager.chenhao.com.user_and_manager.base.BaseData;
import user_and_manager.chenhao.com.user_and_manager.base.BaseHandle;
import user_and_manager.chenhao.com.user_and_manager.config.Config;
import user_and_manager.chenhao.com.user_and_manager.http.HttpPost;
import user_and_manager.chenhao.com.user_and_manager.utils.JasonUtils;

public class UpMoneyActivity extends BaseActivity
{

    private EditText mMoneyEditTextl;
    private Button mBtn_ok;

    private ImageView imageView1, imageView2;


    private String money;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_money);
        Toolbar mToolbarView = findView(R.id.toolbar);
        mToolbarView.setTitle("缴费平台");
        initTool(mToolbarView);


        initData();
        initViews();


    }

    private void initData()
    {


    }

    private void initViews()
    {

        imageView1 = findView(R.id.imager1);
        imageView2 = findView(R.id.imager2);

        mMoneyEditTextl = findView(R.id.select_money_text);
        mBtn_ok = findView(R.id.btn_ok);
        mMoneyEditTextl.setText(BaseData.mLationItem.money);
        mBtn_ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String trim = mMoneyEditTextl.getText().toString().trim();

                if (TextUtils.isEmpty(trim))
                {
                    showToast("违规缴费不能为空");
                } else
                {
                    startThread();
                }
            }
        });
        setImageClick();

    }

    public void setImageClick()
    {
        imageView1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startWeixing();
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startZhiFuBao();
            }
        });
    }


    public Handler mHandler = new BaseHandle()
    {
        @Override
        public void handleMessage(Message msg)
        {

            showProgressDialog();
            switch (msg.what)
            {
                case 200:
                    showToast("缴费成功");
                    setResult(10086, new Intent().putExtra("action", "shuaxing"));
                    finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };


    public void startThread()
    {
        showProgressDialog();
        new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    JSONObject object = JasonUtils.NewJason();
                    JasonUtils.put(object, "vID", BaseData.mLationItem.id );
                    JasonUtils.put(object, "flag", "1");
                    JasonUtils.put(object, "overTime", System.currentTimeMillis() + "");
                    JasonUtils.put(object, "moneyFlag", "1");
                    JasonUtils.put(object, "score", BaseData.mLationItem.score);
//                    object.put("money", Double.valueOf(BaseData.mLationItem.money));

                    object.put("money", BaseData.mLationItem.money);
                    Log.e("qwe", "run: " + object.toString());


                    HttpPost.post(Config.ACTION_UPDATEV, object.toString(), new HttpPost
                            .HttpPosttListenter()
                    {
                        @Override
                        public void HttpPostResultListenter(int status, String result)
                        {
                            status = swResult(status, result);
                            mHandler.obtainMessage(status).sendToTarget();
                        }
                    });
                } catch (JSONException e)
                {
                    e.printStackTrace();
                    mHandler.obtainMessage(10086).sendToTarget();
                }
            }
        }.start();
    }


}
