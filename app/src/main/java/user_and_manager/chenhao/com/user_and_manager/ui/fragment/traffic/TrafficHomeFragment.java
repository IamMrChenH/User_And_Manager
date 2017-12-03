package user_and_manager.chenhao.com.user_and_manager.ui.fragment.traffic;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import user_and_manager.chenhao.com.user_and_manager.base.BaseFragment;
import user_and_manager.chenhao.com.user_and_manager.ui.activity.traffic.FindAllioLationActivity;
import user_and_manager.chenhao.com.user_and_manager.ui.activity.traffic.StatisticsActivity;
import user_and_manager.chenhao.com.user_and_manager.ui.activity.traffic.UpActivity;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.traffic.adapter.TrafficHomeAdapter;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.traffic.adapter.ViewPagerAdapter;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.traffic.bean.DividerItemDecoration;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.traffic.bean.TrafficItem;
import user_and_manager.chenhao.com.user_and_manager.ui.fragment.traffic.viewFragment
        .HeadViewFragment;

public class TrafficHomeFragment extends BaseFragment
{

    @Override
    public int getLayoutId()
    {
        return R.layout.fragment_traffic_home;
    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private TrafficHomeAdapter mTrafficHomeAdapter;
    private List<TrafficItem> mItems;
    private StaggeredGridLayoutManager mManager;

    private ViewPager mPager;
    private List<Fragment> mFragments;

    private TextView mTime;

    private DisplayMetrics metrics;

    public TrafficHomeFragment()
    {
    }

    public static TrafficHomeFragment newInstance(String param1, String param2)
    {
        TrafficHomeFragment fragment = new TrafficHomeFragment();
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
        initData();
        initView();
        setRecycleListener();

    }


    private void initData()
    {
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics = new
                DisplayMetrics());

        mItems = new ArrayList<>();

        mItems.add(new TrafficItem(0, R.mipmap.ic_launcher_round, R.drawable.ic_item_back1,
                "今日限行: 5和9", "明日限行: 1和6"));
        mItems.add(new TrafficItem(0, R.mipmap.ic_launcher_round, R.drawable.ic_item_back2,
                "多云：30~17°c", "较宜洗车"));

        mItems.add(new TrafficItem(2, R.mipmap.ic_launcher_round, "违法举报"));
        mItems.add(new TrafficItem(1, R.mipmap.ic_launcher_round, "查询违规记录"));
        mItems.add(new TrafficItem(1, R.mipmap.ic_launcher_round, "违规统计"));

        mTrafficHomeAdapter = new TrafficHomeAdapter(getActivity(), mItems);


        mFragments = new ArrayList<>();
        mFragments.add(HeadViewFragment.newInstance("1", null));
        mFragments.add(HeadViewFragment.newInstance("2", null));
        mFragments.add(HeadViewFragment.newInstance("3", null));
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
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), 2, 2, Color.GRAY));
        mRecyclerView.setLayoutManager(mManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));
    }

    private void setRecycleListener()
    {
        mTrafficHomeAdapter.setOnItemClickListener(new TrafficHomeAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                switch (position)
                {
                    case 0:
                        showToast("限行");
                        break;
                    case 1:
                        showToast("天气");
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(), UpActivity.class));
                        break;
                    case 3:
                        showToast("查询违规记录");
                        startActivity(new Intent(getActivity(), FindAllioLationActivity.class));
                        break;
                    case 4:
                        showToast("违规统计");
                        startActivity(new Intent(getActivity(), StatisticsActivity.class));
                        break;
                }

            }
        });
    }


}
