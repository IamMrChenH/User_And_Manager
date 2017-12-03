package user_and_manager.chenhao.com.user_and_manager.ui.fragment.traffic;

import android.os.Bundle;
import android.support.annotation.Nullable;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.BaseFragment;


public class TrafficMsgFragment extends BaseFragment
{

    @Override
    public int getLayoutId()
    {
        return R.layout.fragment_traffic_msg;
    }

    
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public TrafficMsgFragment()
    {
    }

    public static TrafficMsgFragment newInstance(String param1, String param2)
    {
        TrafficMsgFragment fragment = new TrafficMsgFragment();
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


    }
}
