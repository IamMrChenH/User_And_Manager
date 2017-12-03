package user_and_manager.chenhao.com.user_and_manager.http;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import user_and_manager.chenhao.com.user_and_manager.config.Config;

/**
 * Created by chenhao on 17-3-16.
 */

public class HttpPost
{


    public interface HttpPosttListenter
    {
        public void HttpPostResultListenter(int status, String result);

    }


    private static final String TAG = "HttpPost";

    public static void post(String action, String content, HttpPosttListenter listenter)
    {

        BufferedWriter writer = null;
        BufferedReader reader = null;
        try
        {
            URL url = new URL(Config.getServerAddress(action));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(2000);
            conn.setDoInput(true);
            conn.setDoOutput(true);


//            writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
//            writer.write(content);
            conn.connect();
            if (!TextUtils.isEmpty(content))
            {
                conn.getOutputStream().write(content.getBytes());
            }


            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String read = new String();
                StringBuffer sb = new StringBuffer();
                while ((read = reader.readLine()) != null)
                {
                    sb.append(read);
                }
                read = sb.toString();
                Log.e(TAG, "result " + read);

                if (listenter != null)
                    listenter.HttpPostResultListenter(200, read);
            }


        } catch (MalformedURLException e)
        {
            e.printStackTrace();
            if (listenter != null)
                listenter.HttpPostResultListenter(404, null);
        } catch (IOException e)
        {
            e.printStackTrace();
            if (listenter != null)
                listenter.HttpPostResultListenter(404, null);
        } finally
        {
            try
            {
                if (writer != null)
                    writer.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            try
            {
                if (reader != null)
                    reader.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    //    String BOUNDARY = UUID.randomUUID().toString();
    public static String PREFIX = "--", LINE_END = "\r\n";
    public static String CONTENT_TYPE = "multipart/form-data";
    public static String CHARSET = "utf-8";


    public static void postFile(String action, File file, HttpPosttListenter listenter)
    {
        HttpURLConnection conn = null;
        DataInputStream in = null;
        DataOutputStream out = null;
        FileInputStream inFile = null;
        Log.e("233", "1111: ");
        try
        {
            Log.e("233", "2222: ");
            URL url = new URL(Config.getServerAddress(8080, action));
            conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(2000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Charset", CHARSET);
            conn.setRequestProperty("Content-Type", CONTENT_TYPE);
            conn.setRequestProperty("fileName", file.getName());
            Log.e("233", "file.getName() : " + file.getName());
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
                    + "<---------->");
            inFile = new FileInputStream(file);
            Log.e("233", "333333: ");


            out = new DataOutputStream(conn.getOutputStream());
            Log.e("233", "44444: ");
            byte[] buff = new byte[1024 * 4];
            int read;
            while (0 < (read = inFile.read(buff)))
            {
                out.write(buff, 0, read);
            }

            Log.e("233", "55555: ");
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                Log.e("233", "66666: ");
                in = new DataInputStream(conn.getInputStream());
                read = in.read(buff);

                String result = new String(buff, 0, read, "utf-8");
                Log.e("233", result);
                listenter.HttpPostResultListenter(200, result);
                Log.e("233", "77777: ");
            } else
            {
                listenter.HttpPostResultListenter(401, null);
            }


        } catch (MalformedURLException e)
        {
            e.printStackTrace();
            //创建URL 出错
            listenter.HttpPostResultListenter(402, null);

        } catch (IOException e)
        {
            e.printStackTrace();
            //openConnection 地址错误
            listenter.HttpPostResultListenter(404, null);

        } finally
        {

            try
            {
                if (in != null)
                {
                    in.close();
                }

                if (out != null)
                {
                    out.close();
                }


                if (inFile != null)
                {
                    inFile.close();
                }

            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }


    }


    public static String getUTF(String s)
    {
        try
        {
            return URLEncoder.encode(s, "utf-8");
        } catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return s;
    }

}
