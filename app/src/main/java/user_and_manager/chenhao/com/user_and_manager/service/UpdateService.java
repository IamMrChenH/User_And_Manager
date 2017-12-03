package user_and_manager.chenhao.com.user_and_manager.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import user_and_manager.chenhao.com.user_and_manager.config.Config;
import user_and_manager.chenhao.com.user_and_manager.http.HttpPost;
import user_and_manager.chenhao.com.user_and_manager.utils.JasonUtils;

/**
 * Created by chenhao on 17-3-22.
 */

public class UpdateService extends Service
{
    /**
     * 上传线程池
     */
    private ExecutorService mUpService;
    private Handler mUIHandler;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mUpService = Executors.newFixedThreadPool(Config.UP_THREAD_MAXSIZE);
        mUIHandler = new Handler(Looper.getMainLooper())
        {
            @Override
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);
                switch (msg.what)
                {
                    case 200:
                        showToast("上传200");
                        break;
                    case 401:
                        showToast("上传失败");
                        break;
                    case 402:
                        showToast("地址出错");
                        break;
                    case 404:
                        showToast("无网络");
                        break;
                    default:
                        showToast("？？？？？？");
                        break;
                }

            }
        };

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mUpService.shutdownNow();
        mUpService = null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        try
        {
            String action = intent.getStringExtra("action");
            if (action.equals("up"))
            {
                String filePATH = intent.getStringExtra("filePath");
                addStartThread(filePATH);
            }

        } catch (Exception e)
        {

        }

        Log.e("233", "服务启动");
        return super.onStartCommand(intent, flags, startId);
    }


    public void addStartThread(final String filePath)
    {

        if (filePath == null)
            return;
        mUpService.submit(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    // 获得图片 的宽和高 并不把图片加载到内存中
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(filePath, options);
                    // 使用获得到的inSampleSize 再次解析图片
                    options.inJustDecodeBounds = false;
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

                    BufferedOutputStream outputStream = new BufferedOutputStream(
                            new FileOutputStream(new File(Environment.getExternalStorageDirectory(),
                                    "temp10086.jpg")));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                    outputStream.close();

                } catch (Exception e)
                {
                    e.printStackTrace();
                    Log.e("233", "我异常了 ");//
                }

                HttpPost.postFile(Config.ACTION_UP_DATA, new File(filePath), new HttpPost
                        .HttpPosttListenter()
                {
                    @Override
                    public void HttpPostResultListenter(int status, String result)
                    {
                        Log.e("233", "status = " + status + "   result= " + result);
                        if (status == 200)
                        {
                            if (JasonUtils.isResult(result))
                            {
                                Log.e("233", "上传成功");
                            }
                        }
                        mUIHandler.obtainMessage(status).sendToTarget();

                    }
                });

            }
        });
    }

    private Toast mToast;

    public void showToast(String msg)
    {
        if (mToast != null)
            mToast.cancel();
        mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        mToast.show();
    }
}
