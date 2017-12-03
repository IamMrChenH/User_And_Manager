package user_and_manager.chenhao.com.user_and_manager.ui.activity.user;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.BaseActivity;
import user_and_manager.chenhao.com.user_and_manager.base.BaseData;
import user_and_manager.chenhao.com.user_and_manager.base.BaseHandle;
import user_and_manager.chenhao.com.user_and_manager.config.Config;
import user_and_manager.chenhao.com.user_and_manager.http.HttpPost;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.user.AddMoneyGridViewAdapter;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.user.bean.AddMoneyItem;
import user_and_manager.chenhao.com.user_and_manager.utils.JasonUtils;
import user_and_manager.chenhao.com.user_and_manager.widget.MyGridView;

public class AddMoneyActivity extends BaseActivity
{

    private ScrollView mScrollView;

    private Toolbar mToolbarView;
    private EditText mSelectEditText;
    private Button mSelectButton;

    private LinearLayout mSelectMoneyLayout;
    private EditText mSelectMoneyEditText;
    private Button mSelectMoneyButton;

    private TextView mFindMoney;
    private TextView mFindRecord;

    private MyGridView mSelectGridView;
    private AddMoneyGridViewAdapter mAddMoneyGridViewAdapter;
    private List<AddMoneyItem> mItems;

    private ImageView imageView1, imageView2;


    private ExecutorService mExecutorService;

    private Handler mUIhHandler = new BaseHandle()
    {
        @Override
        public void handleMessage(Message msg)
        {
            showProgressDialog();
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);
        initDatas();
        initViews();

        initSetListener();

    }

    private void initDatas()
    {

        mExecutorService = Executors.newSingleThreadExecutor();
        mItems = new ArrayList<>();
        mItems.add(new AddMoneyItem(10, 9.98));
        mItems.add(new AddMoneyItem(20, 19.96));
        mItems.add(new AddMoneyItem(30, 29.88));
        mItems.add(new AddMoneyItem(50, 49.90));
        mItems.add(new AddMoneyItem(100, 99.50));
        mItems.add(new AddMoneyItem(200, 196));
        mItems.add(new AddMoneyItem(-1, -1));
        mAddMoneyGridViewAdapter = new AddMoneyGridViewAdapter(this, mItems);
    }

    private void initViews()
    {
        imageView1 = findView(R.id.imager1);
        imageView2 = findView(R.id.imager2);

        mScrollView = findView(R.id.scrollView);
        mScrollView.smoothScrollTo(0, 0);

        mToolbarView = findView(R.id.toolbar);
        mToolbarView.setTitle("充值中心");
        initTool(mToolbarView);

        mSelectEditText = findView(R.id.select_id_text);
        mSelectButton = findView(R.id.select_id_button);
        mSelectGridView = findView(R.id.select_GridView);

        mSelectMoneyLayout = findView(R.id.select_money_layout);
        mSelectMoneyEditText = findView(R.id.select_money_text);
        mSelectMoneyButton = findView(R.id.select_money_button);

        mFindMoney = findView(R.id.find_money);
        mFindRecord = findView(R.id.find_record);


        mSelectEditText.setText(BaseData.cur_carID);
        mSelectGridView.setAdapter(mAddMoneyGridViewAdapter);
    }


    private void initSetListener()
    {
        initSetGridViewItemListener();
        initSelectMoneyListener();
        initFindListener();

        imageView1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startZhiFuBao();
            }
        });
        imageView1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startWeixing();

            }
        });

    }


    private void initSetGridViewItemListener()
    {
        mSelectGridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
            {
                int money = selectMoney(position);
                if (money == 0)
                {
                    startVisibility(mSelectMoneyLayout);
                    return;
                }
                final String number = mSelectEditText.getText().toString();
                if (TextUtils.isEmpty(number))
                {
                    showToast("ID不能为空！");
                    return;
                }

                final int finalMoney = money;
                showProgressDialog();
                mExecutorService.submit(new Runnable()
                {
                    @Override
                    public void run()
                    {

                        JSONObject carID = JasonUtils.put(JasonUtils.NewJason("carID", number),
                                "money", String.valueOf(finalMoney));
                        HttpPost.post(Config.ACTION_ADD_MONEY, carID.toString(), new HttpPost
                                .HttpPosttListenter()
                        {
                            @Override
                            public void HttpPostResultListenter(int status, String result)
                            {
                                status = swResult(status, result);
                                mUIhHandler.obtainMessage(status).sendToTarget();
                            }
                        });
                    }
                });

            }
        });

    }


    private void initSelectMoneyListener()
    {
        mSelectMoneyButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String otherMoneyNumber = mSelectMoneyEditText.getText().toString();
                if (TextUtils.isEmpty(otherMoneyNumber))
                {
                    showToast("不可以为空,请输入充值金额！");
                    return;
                }
                final String number = mSelectEditText.getText().toString();
                if (TextUtils.isEmpty(number))
                {
                    showToast("ID不能为空！");
                    return;
                }

                mExecutorService.submit(new Runnable()
                {
                    @Override
                    public void run()
                    {

                        JSONObject carID = JasonUtils.put(JasonUtils.NewJason("carID", number),
                                "money", String.valueOf(otherMoneyNumber));
                        HttpPost.post(Config.ACTION_ADD_MONEY, carID.toString(), new HttpPost
                                .HttpPosttListenter()
                        {
                            @Override
                            public void HttpPostResultListenter(int status, String result)
                            {
                                if (status == 200)
                                {
                                    if (JasonUtils.isResult(result))
                                    {
                                        //返货成功
                                    } else
                                    {
                                        //反正返回了fail
                                        status = 10086;

                                    }
                                }

                                mUIhHandler.obtainMessage(status).sendToTarget();

                            }
                        });
                    }
                });

            }
        });

    }


    private void initFindListener()
    {
        //查询余额
        mFindMoney.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showToast("真以为我有做啊！");
            }
        });

        //查询充值记录
        mFindRecord.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showToast("真以为我有做啊！");
//                startActivity(new Intent(AddMoneyActivity.this, UserAddRecordActivity.class));

            }
        });

    }

    public static int[] mAddMone = {10, 20, 30, 50, 100, 200};

    public int selectMoney(int position)
    {
        int money = 0;
        try
        {
            money = mAddMone[position];
        } catch (Exception e)
        {
            e.printStackTrace();
            money = 0;
        }

        return money;

    }

    public void startVisibility(View view)
    {

        if (view.getVisibility() == View.VISIBLE)
        {
            view.setVisibility(View.GONE);
        } else
        {
            view.setVisibility(View.VISIBLE);
        }

    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        mExecutorService.shutdownNow();
        mExecutorService = null;
    }
}
