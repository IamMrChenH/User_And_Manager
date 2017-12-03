package user_and_manager.chenhao.com.user_and_manager.ui.fragment.admin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.BaseData;
import user_and_manager.chenhao.com.user_and_manager.base.BaseFragment;
import user_and_manager.chenhao.com.user_and_manager.ui.activity.admin.AddParkRecordActivity;
import user_and_manager.chenhao.com.user_and_manager.ui.activity.admin.AddRecordActivity;
import user_and_manager.chenhao.com.user_and_manager.ui.activity.admin.AdminModiyActivity;
import user_and_manager.chenhao.com.user_and_manager.ui.activity.admin.AdminMoneyActivity;
import user_and_manager.chenhao.com.user_and_manager.ui.activity.admin.AdminParkActivity;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.traffic.adapter.TrafficHomeAdapter;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.traffic.adapter.ViewPagerAdapter;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.traffic.bean.DividerItemDecoration;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.traffic.bean.TrafficItem;
import user_and_manager.chenhao.com.user_and_manager.ui.fragment.traffic.viewFragment
        .HeadViewFragment;

public class AdminHomeFragment extends BaseFragment implements Runnable
{
    @Override
    public int getLayoutId()
    {
        return R.layout.fragment_traffic_home;
    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private DisplayMetrics metrics;
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private TrafficHomeAdapter mTrafficHomeAdapter;
    private List<TrafficItem> mItems;
    private StaggeredGridLayoutManager mManager;

    private ViewPager mPager;
    private List<Fragment> mFragments;

    private TextView mTime;

    public Handler mUIHandler;

    public AdminHomeFragment()
    {
        // Required empty public constructor
    }

    public static AdminHomeFragment newInstance(String param1, String param2)
    {
        AdminHomeFragment fragment = new AdminHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mUIHandler = new Handler(Looper.myLooper());


        initData();
        initView();
        setRecycleListener();

        mUIHandler.post(this);

    }


    private void initData()
    {
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics = new
                DisplayMetrics());

        mItems = new ArrayList<>();
        mItems.add(new TrafficItem(0, R.mipmap.ic_launcher_round, R.drawable.ic_item_back1,
                "收费:", "费率："));
        mItems.add(new TrafficItem(0, R.mipmap.ic_launcher_round, R.drawable.ic_item_back2,
                "明收费:按次", "明费率:50元/次"));


//        mItems.add(new TrafficItem(1, R.mipmap.ic_launcher_round, "事故处理"));

        mItems.add(new TrafficItem(1, R.mipmap.ic_launcher_round, "收益统计"));
        mItems.add(new TrafficItem(2, R.mipmap.ic_launcher_round, "修改费率"));
        mItems.add(new TrafficItem(1, R.mipmap.ic_launcher_round, "查询空位"));
        mItems.add(new TrafficItem(1, R.mipmap.ic_launcher_round, "充值记录"));
        mItems.add(new TrafficItem(1, R.mipmap.ic_launcher_round, "停车记录"));
        mTrafficHomeAdapter = new TrafficHomeAdapter(getActivity(), mItems);


        initRecycleData();


        mFragments = new ArrayList<>();
        mFragments.add(HeadViewFragment.newInstance("1", null));
        mFragments.add(HeadViewFragment.newInstance("2", null));
        mFragments.add(HeadViewFragment.newInstance("3", null));
    }

    public void initRecycleData()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                while (true)
                {


                    try
                    {


                        String type = BaseData.mCast[0];
                        String money = BaseData.mCast[1];

                        if (type.equals("1"))
                        {
                            type = "按次";
                            money = money + "元/次";
                        } else if (type.equals("2"))
                        {
                            type = "小时";
                            money = money + "元/h";
                        }

                        mItems.remove(0);
                        mItems.add(0, new TrafficItem(0, R.mipmap.ic_launcher_round, R.drawable
                                .ic_item_back1,
                                "收费:" + type, "费率：" + money));

                        sleep(1000);


                    } catch (Exception e)
                    {

                    }
                }
            }
        }.start();

    }

    private void initView()
    {
        mTime = findView(getView(), R.id.time);
        SimpleDateFormat sf = new SimpleDateFormat("MM月dd日  EEE");
        mTime.setText(sf.format(System.currentTimeMillis()));


        mPager = findView(getView(), R.id.viewpager);
        mPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), mFragments));


        ViewGroup.LayoutParams lp = mPager.getLayoutParams();
        lp.height = metrics.heightPixels / 2;
        mPager.setLayoutParams(lp);


        mRecyclerView = findView(getView(), R.id.RecytView);
        mRecyclerView.setAdapter(mTrafficHomeAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), 1, 2, Color.GRAY));
        mRecyclerView.setLayoutManager(mManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
    }

    private void setRecycleListener()
    {
//        Toast.makeText(getActivity(), getRandomName(), Toast.LENGTH_SHORT).show();

        mTrafficHomeAdapter.setOnItemClickListener(new TrafficHomeAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                switch (position)
                {
                    case 0:
                        showToast("今日费率");
                        break;
                    case 1:
                        showToast("明日费率");
                        break;
                    case 2:
                        showToast("收益统计");
                        startActivity(new Intent(getActivity(), AdminMoneyActivity.class));
                        break;
                    case 3:
                        showToast("修改费率");
                        startActivity(new Intent(getActivity(), AdminModiyActivity.class));
                        break;
                    case 4:
                        showToast("查询停车场空位");
                        startActivity(new Intent(getActivity(), AdminParkActivity.class));
                        break;
                    case 5:
                        showToast("充值记录");
                        startActivity(new Intent(getActivity(), AddRecordActivity.class));
                        break;
                    case 6:
                        showToast("停车记录");
                        startActivity(new Intent(getActivity(), AddParkRecordActivity.class));
                        break;
                }

            }
        });
    }


    @Override
    public void run()
    {
//        mRecyclerView.setAdapter(mTrafficHomeAdapter);
        mTrafficHomeAdapter.notifyDataSetChanged();
        mUIHandler.postDelayed(this, 1000);
    }
}
