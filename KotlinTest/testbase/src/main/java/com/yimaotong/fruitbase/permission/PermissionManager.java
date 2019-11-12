package com.yimaotong.fruitbase.permission;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.joker.api.Permissions4M;
import com.joker.api.wrapper.ListenerWrapper;
import com.joker.api.wrapper.Wrapper;

import static com.yimaotong.fruitbase.util.UIUtils.startActivity;

/**
 * 权限请求统一处理类
 * @time 2017/9/19 14:44
 */
public class PermissionManager {

    /**
     * 打电话权限申请码
     */
    private static final int CALL_PHONE_CODE = 1000;
    private static final int CAMERA_CODE = 2000;
    public static final int WRITE_EXTERO_STORE_CODE = 3000;

    /**
     * 申请打电话权限
     */
    public static void callPhonePermission(Activity activity, PermissionListener listener) {
        getSinglePermission(activity, Manifest.permission.CALL_PHONE, CALL_PHONE_CODE, "此功能需要您开启电话权限申请：\n请点击前往设置页面", listener);
    }

    /**
     * 拍照权限
     *
     * @param activity
     */
    public static void capturePhotoPermission(final Activity activity, final PermissionListener listener) {
        getMulitePermission(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                new int[]{CAMERA_CODE, WRITE_EXTERO_STORE_CODE}, new PermissionListener() {
            @Override
            public void onPermissionGranted(int code) {
                if (listener != null)
                    listener.onPermissionGranted(code);
            }

            @Override
            public void onPermissionRationale(int code) {
                super.onPermissionRationale(code);
                if (code == CAMERA_CODE) {
                    new AlertDialog.Builder(activity)
                            .setMessage("相机申请：\n此功能需要您开启相机权限")
                            .setPositiveButton("确定", new DialogInterface
                                    .OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Permissions4M.get(activity)
                                            .requestOnRationale()
                                            .requestPermissions(Manifest.permission
                                                    .CAMERA)
                                            .requestCodes(CAMERA_CODE)
                                            .request();
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();

                } else if (code == WRITE_EXTERO_STORE_CODE) {
                    new AlertDialog.Builder(activity)
                            .setMessage("SD卡存储申请：\n此功能需要您开启SD卡存储权限")
                            .setPositiveButton("确定", new DialogInterface
                                    .OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Permissions4M.get(activity)
                                            .requestOnRationale()
                                            .requestPermissions(Manifest.permission
                                                    .WRITE_EXTERNAL_STORAGE)
                                            .requestCodes(WRITE_EXTERO_STORE_CODE)
                                            .request();
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }
            }
        });
    }

    public static void getCameraPermission(final Activity activity, final PermissionListener listener){
        getSinglePermission(activity,Manifest.permission.CAMERA,CAMERA_CODE,"相机申请：\n" +
                "此功能需要您开启相机权限",listener);
    }

    /**
     * 读取sd卡
     * @param activity
     * @param listener
     */
    public static void readExtroStrogePermission(Activity activity, PermissionListener listener) {
        getSinglePermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE, 1112, "请允许读写权限来进行照片上传", listener);

    }

    /**
     * 申请单个权限
     *
     * @param activity
     * @param permission
     * @param requestCode
     * @param rationaleString
     * @param listener
     */
    public static void getSinglePermission(final Activity activity, String permission, int requestCode,
                                           final String rationaleString, final PermissionListener listener) {
        Permissions4M.get(activity)
                .requestPermissions(permission)
                .requestCodes(requestCode)
                .requestListener(new ListenerWrapper.PermissionRequestListener() {
                    @Override
                    public void permissionGranted(int code) {
                        if (listener != null)
                            listener.onPermissionGranted(code);
                    }

                    @Override
                    public void permissionDenied(int code) {
                        if (listener!=null)
                            listener.onDenied();
                    }

                    @Override
                    public void permissionRationale(int code) {
                    }
                })
                .requestPageType(Permissions4M.PageType.MANAGER_PAGE)
                .requestPage(new Wrapper.PermissionPageListener() {
                    @Override
                    public void pageIntent(int code, final Intent intent) {
                        new AlertDialog.Builder(activity)
                                .setMessage(rationaleString)
                                .setPositiveButton("前往设置页面", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .show();
                    }
                })
                .request();
    }

    /**
     * 多个权限同时申请
     */
    public static void getMulitePermission(final Activity activity, String[] permission, int[] requestCode, final PermissionListener listener) {
        Permissions4M.get(activity)
                .requestPermissions(permission)
                .requestCodes(requestCode)
                .requestListener(new Wrapper.PermissionRequestListener() {
                    @Override
                    public void permissionGranted(int code) {
                        if (listener != null)
                            listener.onPermissionGranted(code);
                    }

                    @Override
                    public void permissionDenied(int code) {
                    }

                    @Override
                    public void permissionRationale(int code) {
                    }
                })
                .requestCustomRationaleListener(new Wrapper.PermissionCustomRationaleListener() {
                    @Override
                    public void permissionCustomRationale(int code) {
                        if (listener != null)
                            listener.onPermissionRationale(code);
                    }
                })
                .request();
    }

    public static void onRequestPermissionsResult(Activity activity, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Permissions4M.onRequestPermissionsResult(activity, requestCode, grantResults);
    }

    public static abstract class PermissionListener {
        public abstract void onPermissionGranted(int code);

        public void onPermissionRationale(int code) {
        }

        public void onDenied(){}
    }
}
