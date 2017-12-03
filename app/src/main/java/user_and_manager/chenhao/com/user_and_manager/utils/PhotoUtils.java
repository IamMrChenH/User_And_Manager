package user_and_manager.chenhao.com.user_and_manager.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.text.SimpleDateFormat;

import user_and_manager.chenhao.com.user_and_manager.config.Config;

/**
 * Created by chenhao on 17-3-22.
 */

public class PhotoUtils {


    /**
     * 拍照
     *
     * @param activity
     */
    public static String startPhoto(Activity activity) {
        String filePath;
        filePath = Config.SAVA_PHOTO_PATH + getRandomName() + ".jpg";
        Intent getImageByCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Uri imageUri = Uri.fromFile(new File(filePath));
        //指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
        getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        activity.startActivityForResult(getImageByCamera, 10001);
//        Log.e("233", "startPhoto: " + filePath);
        return filePath;
    }


    public static String getRandomName() {
        StringBuffer buffer = new StringBuffer();
        long time = System.currentTimeMillis();
        String format = new SimpleDateFormat("yyyyMMddHHmmss").format(time);

        return buffer.append("CH-").append(format).toString();
    }


}
