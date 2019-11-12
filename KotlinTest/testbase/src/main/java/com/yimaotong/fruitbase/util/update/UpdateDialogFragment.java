package com.yimaotong.fruitbase.util.update;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.fruitbase.R;
import com.yimaotong.fruitbase.bean.UpdateAppBean;
import com.yimaotong.fruitbase.permission.PermissionManager;
import com.yimaotong.fruitbase.util.ColorUtil;
import com.yimaotong.fruitbase.util.DrawableUtil;
import com.yimaotong.fruitbase.util.SPUtils;
import com.yimaotong.fruitbase.util.ToastUtil;
import com.yimaotong.fruitbase.util.UIUtils;

/**
 * Created by Vector
 * on 2017/7/19 0019.
 */

public class UpdateDialogFragment extends DialogFragment implements View.OnClickListener {
    public static final String TIPS = "请授权访问存储空间权限，否则App无法更新";
    public static boolean isShow = false;
    private TextView mContentTextView;
    private TextView mUpdateOkButton;
    private UpdateAppBean mUpdateApp;
    private ImageView mIvClose;
    private TextView mTitleTextView;
    private LinearLayout mLlClose;
    //默认色
    private int mDefaultColor = 0xffe94339;
    private int mDefaultPicResId = R.drawable.lib_update_app_top_bg;
    private ImageView mTopIv;
    private TextView mIgnore;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isShow = true;
//        setStyle(DialogFragment.STYLE_NO_TITLE | DialogFragment.STYLE_NO_FRAME, 0);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.UpdateAppDialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        //点击window外的区域 是否消失
        getDialog().setCanceledOnTouchOutside(false);
        //是否可以取消,会影响上面那条属性
//        setCancelable(false);
//        //window外可以点击,不拦截窗口外的事件
//        getDialog().getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                //TODO 连续调用两次?????
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    //禁用
                    if (mUpdateApp != null && TextUtils.equals(mUpdateApp.versionInfo.isPush, "01")) {
                        againQuit();
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            }
        });

        Window dialogWindow = getDialog().getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        lp.height = (int) (displayMetrics.heightPixels * 0.8f);
        dialogWindow.setAttributes(lp);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lib_update_app_dialog, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        //提示内容
        mContentTextView = (TextView) view.findViewById(R.id.tv_update_info);
        //标题
        mTitleTextView = (TextView) view.findViewById(R.id.tv_title);
        //更新按钮
        mUpdateOkButton = (TextView) view.findViewById(R.id.btn_ok);
        //关闭按钮
        mIvClose = (ImageView) view.findViewById(R.id.iv_close);
        //关闭按钮+线 的整个布局
        mLlClose = (LinearLayout) view.findViewById(R.id.ll_close);
        //顶部图片
        mTopIv = (ImageView) view.findViewById(R.id.iv_top);
        //忽略
        mIgnore = (TextView) view.findViewById(R.id.tv_ignore);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        mUpdateApp = (UpdateAppBean) getArguments().getSerializable(UpdateManager.INTENT_KEY);
        //设置主题色
        initTheme();

        if (mUpdateApp != null) {
            //弹出对话框
            String newVersion = mUpdateApp.versionInfo.appVersionName;
            String targetSize = mUpdateApp.versionInfo.targetSize;
            String updateLog = mUpdateApp.versionInfo.appDescribe;

            String msg = "";

            if (!TextUtils.isEmpty(targetSize)) {
                msg = "新版本大小：" + targetSize + "\n\n";
            }

            if (!TextUtils.isEmpty(updateLog)) {
                msg += updateLog;
            }

            //更新内容
            mContentTextView.setText(msg);
            //标题
            mTitleTextView.setText(String.format("是否升级到%s版本？", newVersion));
            //强制更新
            if (TextUtils.equals(mUpdateApp.versionInfo.isPush, "01")) {
                mLlClose.setVisibility(View.GONE);
                mIgnore.setVisibility(View.GONE);
            } else {
                //不是强制更新时，才生效
                mIgnore.setVisibility(View.VISIBLE);
            }

            initEvents();
        }
    }

    /**
     * 初始化主题色
     */
    private void initTheme() {


//        final int color = getArguments().getInt(UpdateAppManager.THEME_KEY, -1);
//
//        final int topResId = getArguments().getInt(UpdateAppManager.TOP_IMAGE_KEY, -1);
//
//
//        if (-1 == topResId) {
//            if (-1 == color) {
        //默认红色
//        setDialogTheme(mDefaultColor, mDefaultPicResId);
//            } else {
//                setDialogTheme(color, mDefaultPicResId);
//            }
//
//        } else {
//            if (-1 == color) {
//                //自动提色
////                Palette.from(AppUpdateUtils.drawableToBitmap(this.getResources().getDrawable(topResId))).generate(new Palette.PaletteAsyncListener() {
////                    @Override
////                    public void onGenerated(Palette palette) {
////                        int mDominantColor = palette.getDominantColor(mDefaultColor);
////                        setDialogTheme(mDominantColor, topResId);
////                    }
////                });
//                setDialogTheme(mDefaultColor, topResId);
//            } else {
//                //更加指定的上色
//                setDialogTheme(color, topResId);
//            }
//        }
    }

    /**
     * 设置
     *
     * @param color    主色
     * @param topResId 图片
     */
    private void setDialogTheme(int color, int topResId) {
        mTopIv.setImageResource(topResId);
        mUpdateOkButton.setBackgroundDrawable(DrawableUtil.getDrawable(UIUtils.dip2px(4), color));
        //随背景颜色变化
        mUpdateOkButton.setTextColor(ColorUtil.isTextColorDark(color) ? Color.BLACK : Color.WHITE);
    }

    private void initEvents() {
        mUpdateOkButton.setOnClickListener(this);
        mIvClose.setOnClickListener(this);
        mIgnore.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_ok) {
//            //权限判断是否有访问外部存储空间权限
//            int flag = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            if (flag != PackageManager.PERMISSION_GRANTED) {
//                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                    // 用户拒绝过这个权限了，应该提示用户，为什么需要这个权限。
//                    Toast.makeText(getActivity(), TIPS, Toast.LENGTH_LONG).show();
//                } else {
//                    // 申请授权。
//                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//                }
//
//            } else {
//                installApp();
//                mUpdateOkButton.setVisibility(View.GONE);
//            }

            PermissionManager.getSinglePermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE, 1,
                    TIPS, new PermissionManager.PermissionListener() {
                        @Override
                        public void onPermissionGranted(int code) {
                            ToastUtil.showToast("正在下载...");
                            installApp();
//                            mUpdateOkButton.setVisibility(View.GONE);
                        }
                    });

        } else if (i == R.id.iv_close) {
            SPUtils.putBoolean(UpdateManager.ISDONTUPDATE, true);
            dismiss();
        } else if (i == R.id.tv_ignore) {
            dismiss();
            SPUtils.putBoolean(UpdateManager.ISDONTUPDATE, true);
        }
    }

    private void installApp() {
        downloadApp();
//        dismiss();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //升级
                installApp();
                mUpdateOkButton.setVisibility(View.GONE);
            } else {
                //提示，并且关闭
                Toast.makeText(getActivity(), TIPS, Toast.LENGTH_LONG).show();
                dismiss();

            }
        }

    }

    /**
     * 开启后台服务下载
     */

    private void downloadApp() {
        //使用ApplicationContext延长他的生命周期
        UpdateManager.update(getContext(), mUpdateApp);
    }

    @Override
    public void show(FragmentManager manager, String tag) {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            if (manager.isDestroyed())
                return;
        }

        try {
            super.show(manager, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        isShow = false;
        super.onDestroyView();
    }

    /**
     * 是否已经点击过一次
     */
    boolean isPressedOnce;
    private long secondTime;
    private long firstTime;

    /**
     * 再点一次退出
     */
    private void againQuit() {
        if (isPressedOnce) {
            // 说明刚才已经点了一次 这是第二次进入
            // 先拿到第二次点的时间
            secondTime = System.currentTimeMillis();
            System.out.println("--------->>>>" + secondTime + ">>>>" + firstTime);
            if (secondTime - firstTime > 2000) {
                // 说明上一次点击作废了
                isPressedOnce = true;
                // 0时间2秒  1 4秒
                ToastUtil.showToast("再点一次退出");
                firstTime = System.currentTimeMillis();
            } else if (secondTime - firstTime > 300) {
                ToastUtil.cancelToast();
                //返回桌面
                UIUtils.startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME));
                getActivity().finish();
                Process.killProcess(Process.myPid());
                // 界面结束之后让这些状态保持刚开始的样子
                isPressedOnce = false;
                firstTime = 0;
                secondTime = 0;
            }
        } else {
            // 说明是第一次进入
            isPressedOnce = true;
            // 0时间2秒  1 4秒
            ToastUtil.showToast("再点一次退出");
            firstTime = System.currentTimeMillis();
        }
    }
}
