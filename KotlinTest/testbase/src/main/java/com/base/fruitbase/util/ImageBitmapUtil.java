package com.base.fruitbase.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Administrator on 2016/10/11.
 * 保存图片 获取本地图片
 */
public class ImageBitmapUtil {
    static File footPath = UIUtils.getContext().getFilesDir();

    /**
     * 保存头像到本地
     *
     * @param bitmap
     * @param uri
     */
    public static void saveBitmapLoc(Bitmap bitmap, String uri) {
        try {
            String imageName = EncryptUtils.encryptMD5ToString(uri);
            File file = new File(footPath, imageName);
            if (file.exists()) {
                file.delete();
            }
            OutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.flush();
            stream.close();
        } catch (Exception e) {

        }
    }

    /**
     * 根据与用户相关的uri从本地获取头像
     *
     * @param uri
     * @return
     */
    public static Bitmap getBitmapLoc(String uri) {
        Bitmap bitmap2 = null;
        // 从本地取数据
        String imageName = EncryptUtils.encryptMD5ToString(uri);
        File file = new File(footPath, imageName);
        if (file.exists() && file.length() > 0) {
            bitmap2 = BitmapFactory.decodeFile(file.getAbsolutePath());
        }

        return bitmap2;
    }

    /**
     * 保存二维码到本地并显示到图库
     *
     * @param activity
     */
    public static void saveQrToLoc(Activity activity, Bitmap bitmap) {
        if (bitmap == null) {
            ToastUtil.showToast("保存失败，请重新生成二维码");
            return;
        }
        File appDir = new File(Environment.getExternalStorageDirectory(), "万果田园");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            notifyInsertImage(activity, fileName, file);
        } catch (FileNotFoundException e) {
            ToastUtil.showToast("保存失败，请检测内存状态");
        } catch (IOException e) {
            ToastUtil.showToast("保存失败，请重新保存");
        }
    }

    /**
     * 通知图库更新
     *
     * @param activity
     * @param fileName
     * @param file
     */
    private static void notifyInsertImage(Activity activity, String fileName, File file) {
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(activity.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
            // 最后通知图库更新
            activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
            ToastUtil.showToast("海报保存成功，请到本地相册查看");
        } catch (Exception e) {

            ToastUtil.showToast("保存失败，请重新保存");
        }

    }

    public static void save(Activity activity, ImageView qrImgImageView) {
        File file = new File("/sdcard/ErWeiCode/");

        if (!file.exists()) {
            file.mkdirs();
        }
        File imageFile = new File(file, "二维码" + file.getName() + ".png");

        try {
            imageFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(imageFile);
            qrImgImageView.setDrawingCacheEnabled(true);
            Bitmap obmp = Bitmap.createBitmap(qrImgImageView.getDrawingCache());
            qrImgImageView.setDrawingCacheEnabled(false);
            obmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            Toast.makeText(UIUtils.getContext(), "图片已经保存至" + imageFile, Toast.LENGTH_LONG).show();
        } catch (IOException e) {

        }
        // 其次把文件插入到系统图库
        notifyInsertImage(activity, imageFile.getName(), file);
    }
}
