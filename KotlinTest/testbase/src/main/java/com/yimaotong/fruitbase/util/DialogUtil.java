package com.yimaotong.fruitbase.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.fruitbase.R;
import com.yimaotong.fruitbase.ui.simpledialog.BaseDialog;
import com.yimaotong.fruitbase.ui.simpledialog.SimpleDialog;
import com.yimaotong.fruitbase.ui.simpledialog.ViewConvertListener;
import com.yimaotong.fruitbase.ui.simpledialog.ViewHolder;

import static com.yimaotong.fruitbase.framework.base.BaseActivity.activity;

/**
 * @time 2017/9/27 19:24
 */
public class DialogUtil {
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;
    private static ImageView act_prompt_iamge;
    private static TextView act_prompt_textview;
    private static AlertDialog dialogNick;

    /**
     * 分享成功之后回调的粮票
     */
    public static void shareSuccess() {
        SimpleDialog.init()
                .setLayoutId(R.layout.dialog_share_success)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, final BaseDialog dialog) {
                        holder.setOnClickListener(R.id.cancel, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        holder.setOnClickListener(R.id.receive, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                ToastUtil.showToast("领取粮票");
                            }
                        });
                    }
                })
                .setAnimStyle(R.style.dialog)
                .setMargin(50)
                .show(activity.getSupportFragmentManager());
    }


    public interface OnPositiveListener {
        void onPositive();
    }
    public interface onDiaLogRedEnvelopes {
        void onRedEnvelopes();
    }
}
