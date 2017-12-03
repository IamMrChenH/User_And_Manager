package user_and_manager.chenhao.com.user_and_manager.ui.fragment.admin;

import android.os.Bundle;
import android.support.annotation.Nullable;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.BaseFragment;


public class AdminMsgFragment extends BaseFragment
{
    @Override
    public int getLayoutId()
    {
        return R.layout.fragment_admin_msg;
    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AdminMsgFragment()
    {
        // Required empty public constructor
    }

    public static AdminMsgFragment newInstance(String param1, String param2)
    {
        AdminMsgFragment fragment = new AdminMsgFragment();
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
