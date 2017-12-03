package user_and_manager.chenhao.com.user_and_manager.ui.fragment.traffic.viewFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import user_and_manager.chenhao.com.user_and_manager.R;
import user_and_manager.chenhao.com.user_and_manager.base.BaseFragment;


public class HeadViewFragment extends BaseFragment
{

    @Override
    public int getLayoutId()
    {
        return R.layout.fragment_head_view;
    }

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    public HeadViewFragment()
    {
    }

    public static HeadViewFragment newInstance(String param1, String param2)
    {
        HeadViewFragment fragment = new HeadViewFragment();
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
        TextView t1 = (TextView) getView().findViewById(R.id.text);
        if (mParam1.equals("1"))
        {
//            getview().setbackgroundresource(r.mipmap.viwe1);
            t1.setText("图片 一");
        } else if (mParam1.equals("2"))
        {
            t1.setText("图片 二");
//            getView().setBackgroundResource(R.mipmap.viwe1);
        } else if (mParam1.equals("3"))
        {
            t1.setText("图片 三");
//            getView().setBackgroundResource(R.mipmap.viwe1);
        }

    }
}
