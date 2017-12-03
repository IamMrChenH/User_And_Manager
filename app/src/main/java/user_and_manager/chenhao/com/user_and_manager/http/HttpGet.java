package user_and_manager.chenhao.com.user_and_manager.http;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import user_and_manager.chenhao.com.user_and_manager.config.Config;

/**
 * Created by chenhao on 17-3-16.
 */

public class HttpGet
{
    public static String getFileToSDK(String action, String fileName)
    {
        HttpURLConnection conn = null;
        DataInputStream in = null;
        ByteArrayOutputStream outputStream = null;
        FileOutputStream stream = null;
        Log.e("255", "1: ");
        try
        {
            Log.e("255", "2: ");
            URL url = new URL(Config.getServerAddress(8080, action + fileName));
            conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(true);
//            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
//            conn.connect();


            Log.e("255", "3: ");
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                File file = new File(Environment.getExternalStorageDirectory(), fileName);
                stream = new FileOutputStream(file);
                Log.e("255", "4: ");
                InputStream inputStream = conn.getInputStream();
//                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                BufferedInputStream is = new BufferedInputStream(inputStream);
//                new File(inputStream);


                int read = 0;
                byte[] buff = new byte[1024 * 4];
                while (-1 != (read = is.read(buff)))
                {
                    stream.write(buff, 0, read);
                    Log.e("266", "read_lenght" + read);
                }
                stream.close();

//                FileOutputStream bitmap = new FileOutputStream()


                inputStream.close();


//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

//            Log.e("255", "5" + outputStream.size());
                return file.getAbsolutePath();

            }


        } catch (MalformedURLException e)
        {
            e.printStackTrace();
            //创建URL 出错

        } catch (IOException e)
        {
            e.printStackTrace();
            //openConnection 地址错误

        } finally
        {

            try
            {
                if (in != null)
                {
                    in.close();
                }

                if (outputStream != null)
                {
                    outputStream.close();
                }
                if (stream != null)
                {
                    stream.close();
                }


            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }

}
