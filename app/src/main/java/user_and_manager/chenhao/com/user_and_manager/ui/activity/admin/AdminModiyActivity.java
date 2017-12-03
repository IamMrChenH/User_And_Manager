package user_and_manager.chenhao.com.user_and_manager.ui.activity.admin;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.BaseActivity;
import user_and_manager.chenhao.com.user_and_manager.base.BaseData;
import user_and_manager.chenhao.com.user_and_manager.base.BaseHandle;
import user_and_manager.chenhao.com.user_and_manager.config.Config;
import user_and_manager.chenhao.com.user_and_manager.http.HttpPost;
import user_and_manager.chenhao.com.user_and_manager.utils.JasonUtils;

public class AdminModiyActivity extends BaseActivity
{
    private ExecutorService mExecutorService;

    private Handler mUIHandler = new BaseHandle();


    public void Myfinish()
    {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modiy);
        Toolbar mToolBarView = findView(R.id.toolbar);
        mToolBarView.setTitle("修改费率");
        initTool(mToolBarView);

        mExecutorService = Executors.newSingleThreadExecutor();
        initViews();


    }

    private Spinner mTypeSpinner;
    private EditText mMoneyEditText;
    private Button mBtnOk;

    private void initViews()
    {
        mTypeSpinner = findView(R.id.type);
        mMoneyEditText = findView(R.id.money);
        mBtnOk = findView(R.id.btn_ok);
        try
        {

            mMoneyEditText.setText(BaseData.mCast[1]);
            if (BaseData.mCast[0].equals("1"))
            {//次数
                mTypeSpinner.setSelection(1);

            } else if (BaseData.mCast[0].equals("2"))
            {//小时F
                mTypeSpinner.setSelection(0);

            }

        } catch (Exception e)
        {

        }
        mBtnOk.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String type = null;
                final String money = mMoneyEditText.getText().toString().trim();

                if (mTypeSpinner.getSelectedItem().toString().equals("按次数计算"))
                {
                    type = "1";
                } else if (mTypeSpinner.getSelectedItem().toString().equals("按小时计算"))
                {
                    type = "2";
                }
                final String finalType = type;
                mExecutorService.submit(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        JSONObject put = JasonUtils.put(JasonUtils.put(JasonUtils.NewJason(),
                                "type", finalType),
                                "money", money);

                        HttpPost.post(Config.ACTION_UP_DATEFEES, put.toString(), new HttpPost
                                .HttpPosttListenter()
                        {
                            @Override
                            public void HttpPostResultListenter(int status, String result)
                            {
                                if (status == 200)
                                {
                                    if (!JasonUtils.isResult(result))
                                        status = 201;

                                }

                                mUIHandler.obtainMessage(status).sendToTarget();

                            }
                        });

                    }
                });
            }
        });

    }


}
