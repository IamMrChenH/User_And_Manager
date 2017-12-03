package user_and_manager.chenhao.com.user_and_manager.utils;

import android.widget.Toast;

import user_and_manager.chenhao.com.user_and_manager.MyApp;

/**
 * Created by chenhao on 2017/3/29.
 */

public class ToastUtils {
    private static Toast mToast;

    public static void showToast(String msg) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(MyApp.sContext, msg, Toast.LENGTH_SHORT);
        mToast.show();



    }

}
