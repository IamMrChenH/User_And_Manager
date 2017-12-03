package user_and_manager.chenhao.com.user_and_manager;

import android.app.Application;
import android.content.Context;

/**
 * Created by chenhao on 2017/3/29.
 */

public class MyApp extends Application
{
    public static Context sContext;

    @Override
    public void onCreate()
    {
        super.onCreate();
        sContext = getApplicationContext();
    }
}
