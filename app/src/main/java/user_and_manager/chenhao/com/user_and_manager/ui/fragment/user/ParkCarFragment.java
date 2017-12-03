package user_and_manager.chenhao.com.user_and_manager.ui.fragment.user;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.BaseData;
import user_and_manager.chenhao.com.user_and_manager.base.BaseFragment;
import user_and_manager.chenhao.com.user_and_manager.config.Config;
import user_and_manager.chenhao.com.user_and_manager.http.HttpPost;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.user.ParkCarAdapter;
import user_and_manager.chenhao.com.user_and_manager.ui.adapter.user.bean.ParkCatItem;
import user_and_manager.chenhao.com.user_and_manager.utils.JasonUtils;

import static user_and_manager.chenhao.com.user_and_manager.base.BaseData.mParkadd;

public class ParkCarFragment extends BaseFragment implements Runnable
{
    @Override
    public int getLayoutId()
    {
        return R.layout.fragment_park_car;
    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    private GridView mGridView;
    private ParkCarAdapter mParkCarAdapter;
    private List<ParkCatItem> mItems;

    private Handler mUIHandler;
    private Toast mToast;

    private ExecutorService mService;


    public ParkCarFragment()
    {
    }

    public static ParkCarFragment newInstance(String param1, String param2)
    {
        ParkCarFragment fragment = new ParkCarFragment();
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
        getActivity().setTitle("停车位");
        initData();
        initView();
        initSetGridViewListener();

    }


    private void initData()
    {
        mService = Executors.newSingleThreadExecutor();
        mItems = new ArrayList<>();
        for (int i = 0; i < 10; i++)
        {
            mItems.add(new ParkCatItem(((i + 1) + "")));
        }

        mParkCarAdapter = new ParkCarAdapter(getActivity(), mItems);

    }

    private void initView()
    {
        mUIHandler = new Handler(Looper.getMainLooper())
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);

                switch (msg.what)
                {
                    case 200:
                        showToast("预约成功!");
                        break;
                    case 201:
                        showToast("出库成功!");
                        break;
                    case 401:
                        showToast("预约失败!");
                        break;
                    case 402:
                        showToast("出库失败!");
                        break;
                    default:
                        showToast("请检查网络!");
                        break;
                }
            }
        };
        mGridView = findView(getView(), R.id.gridview);
        mGridView.setAdapter(mParkCarAdapter);
        mUIHandler.postDelayed(this, 1000);

    }

    private void initSetGridViewListener()
    {
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
            {
                if (mParkadd.get(position) != null)
                {
                    int i = BaseData.mParkadd.get(position);
                    showToast("只能预约空闲车位！");
                } else
                {
//                    showToast("预约车位成功！");
                    mService.submit(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            JSONObject newJason = JasonUtils.NewJason();
                            JasonUtils.put(newJason, "carID", BaseData.cur_carID);
                            JasonUtils.put(newJason, "parkTime", "10000");
                            JasonUtils.put(newJason, "isPark", "" + (position + 1));
                            HttpPost.post(Config.ACTION_PARK, newJason.toString(), new HttpPost
                                    .HttpPosttListenter()
                            {
                                @Override
                                public void HttpPostResultListenter(int status, String result)
                                {
                                    if (status == 200)
                                    {
                                        if (JasonUtils.isResult(result))
                                        {
                                            mUIHandler.obtainMessage(200).sendToTarget();
                                            return;
                                        } else
                                        {
                                            status = 401;
                                        }
                                    }

                                    mUIHandler.obtainMessage(status).sendToTarget();
                                }
                            });
                        }
                    });

                }
            }
        });


        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (mParkadd.get(position) != null)
                {
                    int i = BaseData.mParkadd.get(position);
//                    showToast("只能预约空闲车位！");
                    mService.submit(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            JSONObject newJason = JasonUtils.NewJason();
                            JasonUtils.put(newJason, "carID", BaseData.cur_carID);
                            JasonUtils.put(newJason, "isPark", "0");
                            HttpPost.post(Config.ACTION_PARK, newJason.toString(), new HttpPost
                                    .HttpPosttListenter()
                            {
                                @Override
                                public void HttpPostResultListenter(int status, String result)
                                {
                                    if (status == 200)
                                    {
                                        if (JasonUtils.isResult(result))
                                        {
                                            mUIHandler.obtainMessage(201).sendToTarget();
                                            return;
                                        } else
                                        {
                                            status = 402;
                                        }
                                    }

                                    mUIHandler.obtainMessage(status).sendToTarget();
                                }
                            });
                        }
                    });
                    return true;

                } else
                {
                    showToast("只能出库小车！");
                    return true;
                }

//                return false;
            }
        });


    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
//        super.setUserVisibleHint(isVisibleToUser);
        Log.e("233", "setUserVisibleHint: --->" + isVisibleToUser);

    }


    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
//        Log.e("233", "onDestroyView: --->");
        mUIHandler.removeCallbacks(this);
    }


    @Override
    public void run()
    {

        mParkCarAdapter.notifyDataSetChanged();
        Log.e("run", "刷新");
        mUIHandler.postDelayed(this, 1000);
    }


}
