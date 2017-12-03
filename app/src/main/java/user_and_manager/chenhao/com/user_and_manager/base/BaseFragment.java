package user_and_manager.chenhao.com.user_and_manager.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import user_and_manager.chenhao.com.user_and_manager.utils.ToastUtils;

/**
 * Created by chenhao on 2017/3/29.
 */

public abstract class BaseFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(getLayoutId(), container, false);
    }

    public abstract int getLayoutId();


    public <T extends View> T findView(int id)
    {
        return (T) getView().findViewById(id);
    }

    public <T extends View> T findView(View view, int id)
    {
        return (T) view.findViewById(id);
    }

    private static Toast mToast;

    public void showToast(String msg)
    {
        ToastUtils.showToast(msg);
    }
}
