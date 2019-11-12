package com.yimaotong.fruitbase.util.update;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fruitbase.R;
import com.yimaotong.fruitbase.bean.UpdateAppBean;

/**
 * Created by  on 2017/10/4.
 */

public class UpdateManager {

    final static String INTENT_KEY = "update_dialog_values";
    private static int versionCode;

    /**
     * 用户忽略版本更新
     */
    public static final String ISDONTUPDATE="isDontUpdate";
    /**
     * 远程版本号
     */
    public static final String SERVICECODE="serViceCode";

    /**
     * 弹出对话框
     */
    public static void showUpdateDialog(final Activity activity, boolean isForce) {
        View view = LayoutInflater.from(activity).inflate(R.layout.lib_update_app_dialog, null);
        TextView btnOk = (TextView) view.findViewById(R.id.btn_ok);
        LinearLayout llClose = (LinearLayout) view.findViewById(R.id.ll_close);
        if (isForce) llClose.setVisibility(View.GONE);
        ImageView ivClose = (ImageView) view.findViewById(R.id.iv_close);
        final AlertDialog dialog = new AlertDialog.Builder(activity, R.style.dialog)
                .setView(view)
                .setCancelable(false)
                .create();
        dialog.show();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                update(activity,);
                dialog.dismiss();
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    /**
     * 弹出dialogFragment,进行下载并安装
     *
     * @param activity
     */
    public static void showUpdateDialogFragment(UpdateAppBean updateAppBean, AppCompatActivity activity) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = activity.getPackageManager()
                    .getPackageInfo(activity.getPackageName(), 0);
            //获取APP版本versionCode
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (updateAppBean.versionInfo.appVersionCode <= versionCode) {
            return;
        } else {
            Bundle bundle = new Bundle();
            bundle.putSerializable(INTENT_KEY, updateAppBean);
            UpdateDialogFragment updateDialogFragment = new UpdateDialogFragment();
            updateDialogFragment.setArguments(bundle);
            updateDialogFragment.show(activity.getSupportFragmentManager(), "dialog");
            //  }
        }
    }

    public static void update(Context context,UpdateAppBean bean) {
        UpdateService.startService(context, bean);
    }
}
