package user_and_manager.chenhao.com.user_and_manager.base;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by chenhao on 2017/3/29.
 */

public class BaseHandle extends Handler
{
    @Override
    public void handleMessage(Message msg)
    {
        super.handleMessage(msg);
        switch (msg.what)
        {
            case 200:
//                ToastUtils.showToast("请求成功");
                Log.e("showToast", "请求成功: ");
                break;
            case 404:
//                ToastUtils.showToast("网络异常！请检查网络是否正常！");
                Log.e("showToast", "网络异常！请检查网络是否正常！: ");
                break;
            case 201:
//                ToastUtils.showToast("网络访问失败！");
                Log.e("showToast", "网络访问失败！: ");
                break;
            default:
//                ToastUtils.showToast("网络访问失败");
                Log.e("showToast", "网络访问失败: ");
                break;

        }


    }
}
