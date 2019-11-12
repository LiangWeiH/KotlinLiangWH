package com.yimaotong.fruitbase.util.update;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import com.example.fruitbase.R;
import com.yimaotong.fruitbase.bean.UpdateAppBean;
import com.yimaotong.fruitbase.util.ToastUtil;
import com.yimaotong.fruitbase.util.UIUtils;
import com.yimaotong.fruitbase.util.okhttputils.OkHttpUtils;
import com.yimaotong.fruitbase.util.okhttputils.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;

/**
 * Created by  on 2017/10/4.
 */

public class UpdateService extends Service {

    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    private static final int NOTIFY_ID = 0;
    private boolean isRunning;
    private UpdateAppBean updateAppBean;
    private String downUrl;
    private static Activity activity;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * app内部版本更新
     *
     * @param context
     * @param updateAean
     */
    public static void startService(Context context, UpdateAppBean updateAean) {
        if (!(context instanceof Activity)) {
            ToastUtil.showToast("context必须为Activity");
            return;
        }
        activity = (Activity) context;
        Intent intent = new Intent(context, UpdateService.class);
        intent.putExtra("bean", updateAean);
        context.startService(intent);
    }

    /**
     * app下载调用
     */
    public static void startService(Context context, String url) {
        Intent intent = new Intent(context, UpdateService.class);
        intent.putExtra("downUrl", url);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setUpNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) return super.onStartCommand(intent, flags, startId);
        updateAppBean = (UpdateAppBean) intent.getSerializableExtra("bean");
        downUrl = intent.getStringExtra("downUrl");
        if (updateAppBean == null && TextUtils.isEmpty(downUrl)) {
            ToastUtil.showToast("下载地址为空");
            mNotificationManager.cancel(NOTIFY_ID);
            return super.onStartCommand(intent, flags, startId);
        } else if (updateAppBean != null)
            startDownload(updateAppBean);
        else if (!TextUtils.isEmpty(downUrl))
            startDownload(downUrl);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 创建通知
     */
    private void setUpNotification() {
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("开始下载")
                .setContentText("正在连接服务器")
                .setSmallIcon(R.drawable.app_icon)
                .setLargeIcon(AppUpdateUtils.drawableToBitmap(AppUpdateUtils.getAppIcon(UpdateService.this)))
                .setOngoing(true)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis());
        mNotificationManager.notify(NOTIFY_ID, mBuilder.build());
    }

    int oldRate = 0;

    /**
     * 下载模块,应用于更新
     */
    private void startDownload(UpdateAppBean updateAppBean) {
        if (isRunning) return;
        isRunning = true;
        if (TextUtils.isEmpty(updateAppBean.versionInfo.apkUrl)) {
            ToastUtil.showToast("下载地址为空");
            mNotificationManager.cancel(NOTIFY_ID);
            return;
        }
        String apkUrl = updateAppBean.versionInfo.apkUrl.replace("https:", "http:");
        if (TextUtils.isEmpty(apkUrl)) {
            String contentText = "新版本下载路径错误";
            stop(contentText);
            return;
        }
        String appName = AppUpdateUtils.getApkName(updateAppBean);

        File appDir = AppUpdateUtils.getAppFile(updateAppBean);
        if (appDir == null) return;
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        Log.v("APP下载路径",""+apkUrl);
        OkHttpUtils.get()
                .url(apkUrl)
                .build()
                .execute(new FileCallBack(appDir.getAbsolutePath(), appName) {
                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        int rate = Math.round(progress * 100);
                        if (oldRate != rate) {
                            if (mBuilder != null) {
                                mBuilder.setContentTitle("正在下载：" + AppUpdateUtils.getAppName(UpdateService.this))
                                        .setContentText(rate + "%")
                                        .setProgress(100, rate, false)
                                        .setWhen(System.currentTimeMillis());
                                Notification notification = mBuilder.build();
                                notification.flags = Notification.FLAG_AUTO_CANCEL;
                                mNotificationManager.notify(NOTIFY_ID, notification);
                            }
                            //重新赋值
                            oldRate = rate;
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast("下载apk异常，请重新下载" + e.getMessage());
                        System.out.println("下载apk异常，请重新下载" + e.getMessage());
                        try {
                            mNotificationManager.cancel(NOTIFY_ID);
                            close();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }

                    @Override
                    public void onResponse(File file, int id) {
                        try {

//                            if (AppUpdateUtils.isAppOnForeground(UpdateService.this) || mBuilder == null) {
                            //App前台运行
                            mNotificationManager.cancel(NOTIFY_ID);

//                            // 安装apk---------------------------------------------
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.fromFile(file),
                                    "application/vnd.android.package-archive");
                            activity.startActivityForResult(intent, 88);
//                            TODO 等待测试------------------------------------------
//                            AppUpdateUtils.installApp(activity,file);

//                            } else {
//                                //App后台运行
//                                //更新参数,注意flags要使用FLAG_UPDATE_CURRENT
//                                Intent installAppIntent = AppUpdateUtils.getInstallAppIntent(UpdateService.this, file);
//                                PendingIntent contentIntent = PendingIntent.getActivity(UpdateService.this, 0, installAppIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//                                mBuilder.setContentIntent(contentIntent)
//                                        .setContentTitle(AppUpdateUtils.getAppName(UpdateService.this))
//                                        .setContentText("下载完成，请点击安装")
//                                        .setProgress(0, 0, false)
//                                        //                        .setAutoCancel(true)
//                                        .setDefaults((Notification.DEFAULT_ALL));
//                                Notification notification = mBuilder.build();
//                                notification.flags = Notification.FLAG_AUTO_CANCEL;
//                                mNotificationManager.notify(NOTIFY_ID, notification);
//                            }
                            //下载完自杀
                            activity = null;
                            close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            close();
                        }
                    }
                });
    }

    /**
     * 下载模块，应用于下载
     */
    private void startDownload(String downUrl) {
        if (isRunning) return;
        isRunning = true;
        String apkUrl = updateAppBean.versionInfo.apkUrl.replace("https:", "http:");
        Log.e("APP下载路径",""+apkUrl);
        if (TextUtils.isEmpty(apkUrl)) {
            String contentText = "APP下载路径错误";
            stop(contentText);
            return;
        }
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File appDir = new File(path
                .concat(File.separator + UIUtils.getString(R.string.app_name))
                .concat(File.separator + updateAppBean.versionInfo.appVersionName));
        if (appDir == null) return;
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        OkHttpUtils.get()
                .url(apkUrl)
                .build()
                .execute(new FileCallBack(appDir.getAbsolutePath(), UIUtils.getString(R.string.app_name)) {
                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        int rate = Math.round(progress * 100);
                        if (oldRate != rate) {
                            if (mBuilder != null) {
                                mBuilder.setContentTitle("正在下载：" + AppUpdateUtils.getAppName(UpdateService.this))
                                        .setContentText(rate + "%")
                                        .setProgress(100, rate, false)
                                        .setWhen(System.currentTimeMillis());
                                Notification notification = mBuilder.build();
                                notification.flags = Notification.FLAG_AUTO_CANCEL;
                                mNotificationManager.notify(NOTIFY_ID, notification);
                            }
                            //重新赋值
                            oldRate = rate;
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showToast("下载apk异常，请重新下载" + e.getMessage());
                        try {
                            mNotificationManager.cancel(NOTIFY_ID);
                            close();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }

                    @Override
                    public void onResponse(File file, int id) {
                        try {

                            if (AppUpdateUtils.isAppOnForeground(UpdateService.this) || mBuilder == null) {
                                //App前台运行
                                mNotificationManager.cancel(NOTIFY_ID);

                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    Uri fileUri = FileProvider.getUriForFile(activity,
                                            activity.getApplicationContext().getPackageName() + ".fileProvider", file);
                                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
                                } else {
                                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                                }
//            if (context.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
                                activity.startActivity(intent);
//            }
                            } else {
                                //App后台运行
                                //更新参数,注意flags要使用FLAG_UPDATE_CURRENT
                                Intent installAppIntent = AppUpdateUtils.getInstallAppIntent(UpdateService.this, file);
                                PendingIntent contentIntent = PendingIntent.getActivity(UpdateService.this, 0, installAppIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                mBuilder.setContentIntent(contentIntent)
                                        .setContentTitle(AppUpdateUtils.getAppName(UpdateService.this))
                                        .setContentText("下载完成，请点击安装")
                                        .setProgress(0, 0, false)
                                        //                        .setAutoCancel(true)
                                        .setDefaults((Notification.DEFAULT_ALL));
                                Notification notification = mBuilder.build();
                                notification.flags = Notification.FLAG_AUTO_CANCEL;
                                mNotificationManager.notify(NOTIFY_ID, notification);
                            }
                            //下载完自杀
                            close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            close();
                        }
                    }
                });
    }

    private void stop(String contentText) {
        if (mBuilder != null) {
            mBuilder.setContentTitle(AppUpdateUtils.getAppName(UpdateService.this)).setContentText(contentText);
            Notification notification = mBuilder.build();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            mNotificationManager.notify(NOTIFY_ID, notification);
        }
        close();
    }

    private void close() {
        stopSelf();
        isRunning = false;
    }

    @Override
    public void onDestroy() {
        mNotificationManager = null;
        super.onDestroy();
    }

}
