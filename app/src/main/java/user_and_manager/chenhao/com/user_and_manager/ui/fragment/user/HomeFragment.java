package user_and_manager.chenhao.com.user_and_manager.ui.fragment.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.BaseData;
import user_and_manager.chenhao.com.user_and_manager.base.BaseFragment;
import user_and_manager.chenhao.com.user_and_manager.ui.activity.user.AddMoneyActivity;
import user_and_manager.chenhao.com.user_and_manager.ui.activity.user.DriverLicenseActivity;
import user_and_manager.chenhao.com.user_and_manager.ui.activity.user.FindioLationActivity;
import user_and_manager.chenhao.com.user_and_manager.ui.activity.user.MyCarDataActivity;
import user_and_manager.chenhao.com.user_and_manager.ui.activity.user.UserAddMoneyRecordActivity;
import user_and_manager.chenhao.com.user_and_manager.ui.activity.user.UserInfoActivity;
import user_and_manager.chenhao.com.user_and_manager.ui.activity.user.UserMoneyRecordActivity;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.user.HomeListAdapter;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.user.bean.HomeMenuItem;

public class HomeFragment extends BaseFragment {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private volatile static boolean isShowDialog = true;
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(isShowDialog) {
                isShowDialog = false;
                initDialog();
            }


        }
    };

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private ListView mListView;
    private ImageView mHeadImgView;
    private TextView mHeadName;

    private HomeListAdapter mHomeListAdapter;
    private List<HomeMenuItem> mItems;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("用户");
        initData();
        initView();
        initSetListener();
        initThread();
    }

    private void initThread() {
        new Thread() {
            @Override
            public void run() {
                try {
                    while (isShowDialog){
                        sleep(2000);
                        if (Integer.valueOf(BaseData.mMoney) <= 10 ) {
                            mHandler.obtainMessage(0).sendToTarget();
                        }
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private void initDialog() {


            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("温馨提示！")
                    .setMessage("您的账户余额不足，清充值！")
                    .setNegativeButton("充值", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            isShowDialog = false;
                            startActivity(new Intent(getActivity(), AddMoneyActivity.class));
                        }
                    })
                    .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            isShowDialog = false;
                        }
                    });

            builder.show();

    }


    private void initData() {
        mItems = new ArrayList<>();
        mItems.add(new HomeMenuItem(0, "查看违规"));
        mItems.add(new HomeMenuItem(0, "我的资料"));
        mItems.add(new HomeMenuItem(0, "我的机动车"));
        mItems.add(new HomeMenuItem(0, "我的驾驶证"));
        mItems.add(new HomeMenuItem(0, "充值"));
        mItems.add(new HomeMenuItem(0, "充值记录"));
        mItems.add(new HomeMenuItem(0, "消费记录"));
        mItems.add(new HomeMenuItem(0, "手势密码修改"));
        mItems.add(new HomeMenuItem(0, "登录密码修改"));
        mItems.add(new HomeMenuItem(0, "平台吐槽"));
        mItems.add(new HomeMenuItem(0, "当前版本_v1"));
        mHomeListAdapter = new HomeListAdapter(getActivity(), mItems);
    }

    private void initView() {
        mListView = findView(getView(), R.id.listview);
        mHeadImgView = findView(getView(), R.id.head_imager);
        mHeadName = findView(getView(), R.id.head_user_name);

        mListView.setAdapter(mHomeListAdapter);

    }

    private void initSetListener() {
        setListViewItemListener();


    }

    public void setListViewItemListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://查看违规
                        showToast(mItems.get(position).mString);
                        startActivity(new Intent(getActivity(), FindioLationActivity.class));
                        break;
                    case 1://我的资料
                        showToast(mItems.get(position).mString);
                        startActivity(new Intent(getActivity(), UserInfoActivity.class));
                        break;
                    case 2://我的机动车
                        showToast(mItems.get(position).mString);
                        startActivity(new Intent(getActivity(), MyCarDataActivity.class));
                        break;
                    case 3://我的驾照啦
                        showToast(mItems.get(position).mString);
                        startActivity(new Intent(getActivity(), DriverLicenseActivity.class));
                        break;
                    case 4://充值
                        startActivity(new Intent(getActivity(), AddMoneyActivity.class));
                        break;
                    case 5://充值记录
//                        showToast("待加入此-->" + mItems.get(position).mString + "功能");
                        startActivity(new Intent(getActivity(), UserMoneyRecordActivity.class));
                        break;
                    case 6://消费记录
//                        showToast("待加入此-->" + mItems.get(position).mString + "功能");
                        startActivity(new Intent(getActivity(), UserAddMoneyRecordActivity.class));
                        break;
                    case 7://手势密码
                        showToast("待加入此-->" + mItems.get(position).mString + "功能");
                        break;
                    case 8://登录密码
                        showToast("待加入此-->" + mItems.get(position).mString + "功能");
                        break;
                    case 9://吐槽
                        showToast("待加入此-->" + mItems.get(position).mString + "功能");
                        break;
                    case 10://版本
                        showToast("待加入此-->" + mItems.get(position).mString + "功能");
                        break;

                }
            }
        });
    }


}
