package user_and_manager.chenhao.com.user_and_manager.ui.activity.user;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.BaseActivity;
import user_and_manager.chenhao.com.user_and_manager.base.BaseData;
import user_and_manager.chenhao.com.user_and_manager.base.BaseHandle;
import user_and_manager.chenhao.com.user_and_manager.http.HttpGet;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.user.bean.VioLationItem;
import user_and_manager.chenhao.com.user_and_manager.utils.ImageLoader;

public class VioLationDetailsActivity extends BaseActivity
{
    private VioLationItem mItem;
    private ImageView[] mImageViews = new ImageView[3];
    private int mImgId[] = {R.id.imager1, R.id.imager2, R.id.imager3};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vio_lation_details);
        Toolbar toolBarView = findView(R.id.toolbar);
        toolBarView.setTitle("违规详情");
        initTool(toolBarView);

        initData();
        initViews();

    }

    private void initData() {mItem = BaseData.mLationItem;}

    private TextView money;
    private TextView lastfee;
    private TextView time;
    //以上

    private TextView id;
    private TextView score;
    private TextView carNumber;
    private TextView address;
    private TextView action;

    private TextView up_office;

    private TextView up_moneyFlag;
    private TextView up_flag;
    private TextView up_overTime;
    private Button btn_ok;
    private Handler mHandler = new BaseHandle()
    {
        @Override
        public void handleMessage(final Message msg)
        {

            try
            {
                ImageLoader.getInstance().loadImage(msg.obj.toString(), mImageViews[msg.what]);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            super.handleMessage(msg);

        }
    };


    private void initViews()
    {
        for (int i = 0; i < mImgId.length; i++)
        {
            mImageViews[i] = findView(mImgId[i]);
            mImageViews[i].setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    String src = v.getTag().toString();
                    if (!TextUtils.isEmpty(src))
                    {

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(new File(src)), "image/*");
                        startActivity(intent);
                    }
                }
            });
            new ImageAsy().execute(i);


        }

        money = findView(R.id.up_money);
        money.setText("罚款" + mItem.money);

        lastfee = findView(R.id.up_LastFee);
        lastfee.setText("滞纳金0");

        time = findView(R.id.up_time);
        time.setText(getTime(mItem.time));
        //以上


        id = findView(R.id.up_id);
        id.setText("违规编号：" + mItem.id);

        score = findView(R.id.up_score);
        score.setText("扣分：" + mItem.score);

        carNumber = findView(R.id.up_catNumber);
        carNumber.setText("车牌号：" + BaseData.mCarNumber);

        address = findView(R.id.up_address);
        address.setText("地址: " + mItem.vXY);

        action = findView(R.id.up_action);
        action.setText("行为:" + mItem.msg);

        up_office = findView(R.id.up_office);
        up_office.setText("采集机关：" + mItem.vOffice);


        up_moneyFlag = findView(R.id.up_moneyFlag);
        String moneyFlag = "未繳費";
        if (!mItem.moneyFlag.equals("0"))
        {
            moneyFlag = "已繳費";
        }
        up_moneyFlag.setText("缴费状态:" + moneyFlag);


        up_flag = findView(R.id.up_flag);
        String flag = "未处理";
        if (!mItem.flag.equals("0"))
        {
            flag = "已处理";
        }
        up_flag.setText("处理状态：" + flag);

        up_overTime = findView(R.id.up_overTime);
        if (mItem.overTime != null)
        {
            up_overTime.setVisibility(View.VISIBLE);
            up_overTime.setText("处理时间：" + getTime(mItem.overTime));
        } else
        {
            up_overTime.setVisibility(View.GONE);
        }

        btn_ok = findView(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!mItem.moneyFlag.equals("0"))
                {
                    showToast("已缴费，不需重复缴费！");
                    return;
                } else
                {
                    showToast("快去缴费！");
                    startActivityForResult(new Intent(VioLationDetailsActivity.this,
                            UpMoneyActivity.class), 1001);
                }
            }
        });

        try
        {
            if (getIntent().getStringExtra("action").equals("all"))
            {
                btn_ok.setVisibility(View.GONE);
            }


        } catch (Exception e)
        {
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        try
        {
            if (data.getStringExtra("action").equals("shuaxing"))
            {
                setResult(1000, data.putExtra("action", "shuaxing"));
                finish();
            }

        } catch (Exception e)
        {

        }

    }


    class ImageAsy extends AsyncTask<Integer, Void, String[]>
    {

        @Override
        protected String[] doInBackground(Integer... params)
        {
            int position = params[0];

            File file = new File(Environment.getExternalStorageDirectory(), BaseData.mLationItem
                    .photo[position]);

            if (!file.exists())
            {
                String fileToSDK = HttpGet.getFileToSDK("go/", BaseData.mLationItem
                        .photo[position]);

                return new String[]{position + "", fileToSDK};
            } else
            {
                return new String[]{position + "", file.getAbsolutePath()};
            }


        }

        @Override
        protected void onPostExecute(String[] aVoid)
        {
            super.onPostExecute(aVoid);
            Log.e("277", aVoid[0] + "-------" + aVoid[1]);
            if (aVoid != null)
            {
//                ImageLoader.getInstance().loadImage(aVoid[1], mImageViews[Integer
//                        .valueOf(aVoid[0])]);
                mHandler.obtainMessage(Integer.valueOf(aVoid[0]), aVoid[1]).sendToTarget();
            }


        }
    }

}
