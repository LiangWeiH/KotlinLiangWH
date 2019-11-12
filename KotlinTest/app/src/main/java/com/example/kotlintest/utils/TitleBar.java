package com.example.kotlintest.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kotlintest.R;
import com.yimaotong.fruitbase.framework.base.BaseActivity;
import com.yimaotong.fruitbase.util.UIUtils;

/**
 * @author LiangWH
 * @time 2019/9/22 10:02
 */
public class TitleBar extends LinearLayout {

    private TextView mTitle;
    private ImageView back;
    private RelativeLayout rootView;
    private OnBackListener listener;
    private View line;
    private TextView title_right;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        String title = typedArray.getString(R.styleable.TitleBar_title);
        String rightTitle = typedArray.getString(R.styleable.TitleBar_rightTitle);
        boolean isShowTitle = typedArray.getBoolean(R.styleable.TitleBar_isShowTitle, false);
        boolean isShowBack = typedArray.getBoolean(R.styleable.TitleBar_isShowBack, false);
        final boolean isAnimation = typedArray.getBoolean(R.styleable.TitleBar_isAnimation, false);
        final boolean isShowLine = typedArray.getBoolean(R.styleable.TitleBar_isShowLine, true);
        final boolean isShowTightText = typedArray.getBoolean(R.styleable.TitleBar_isShowTightText, false);
        int backImg = typedArray.getResourceId(R.styleable.TitleBar_backImg, R.drawable.act_login_back);
        int backColor = typedArray.getResourceId(R.styleable.TitleBar_backColor, R.color.transpant);
        int titleColor = typedArray.getResourceId(R.styleable.TitleBar_titleColor, R.color.black);
        LayoutInflater.from(getContext()).inflate(R.layout.titlebar, this, true);
        mTitle = (TextView) findViewById(R.id.title);
        back = (ImageView) findViewById(R.id.back);
        title_right = (TextView) findViewById(R.id.title_right);
        line = findViewById(R.id.line);
        rootView = (RelativeLayout) findViewById(R.id.rootView);
        typedArray.recycle();
        if (isShowBack) {
            back.setVisibility(VISIBLE);
            back.setImageResource(backImg);
            back.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onBack();
                    else if (getContext() instanceof BaseActivity) {
                        if (isAnimation)
                            doRationAnimation();
                        else {
                            ((BaseActivity) getContext()).finishActivity();
                        }
                    }
                }
            });
        }
        if (!isShowLine)
            line.setVisibility(GONE);
        rootView.setBackgroundResource(backColor);

        mTitle.setTextColor(UIUtils.getColor(titleColor));
        title_right.setTextColor(UIUtils.getColor(titleColor));

        if (isShowTitle)
            if (!TextUtils.isEmpty(title))
                setTitle(title);
        if (isShowTightText){
            if (!TextUtils.isEmpty(rightTitle))
                title_right.setText(rightTitle);
        }else {
            title_right.setVisibility(GONE);
        }
    }

    public void setTitleColor(int color) {
        mTitle.setTextColor(color);
    }

    /**
     * 设置右侧显示
     */
    public void setRightTitle(String title) {
        title_right.setText(title);
    }

    /**
     * 设置右侧显示,并设置相应事件
     */
    public void setRightTitle(String title, OnClickListener listener) {
        title_right.setText(title);
        title_right.setOnClickListener(listener);
    }

    public void setRightTitleListener(OnClickListener listener) {
        title_right.setOnClickListener(listener);
    }

    /**
     * 返回按钮监听
     *
     * @param listener
     */
    public void setOnBackListener(OnBackListener listener) {
        this.listener = listener;
    }

    public interface OnBackListener {
        void onBack();
    }

    /**
     * 做旋转动画
     */
    public void doRationAnimation() {
        Animation animation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ((BaseActivity) getContext()).finishActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        if (back != null)
            back.startAnimation(animation);
        else
            ((BaseActivity) getContext()).finishActivity();
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }
}
