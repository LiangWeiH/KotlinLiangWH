package com.example.kotlintest.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kotlintest.R;
import com.lcodecore.tkrefreshlayout.IBottomView;

/**
 * @author LiangWH
 * @time 2017/11/9 16:46
 */
public class FruitLoadMoreBottomView extends FrameLayout implements IBottomView {

    private ImageView refreshArrow;
    private TextView refreshTextView;
    private ImageView loadingView;

    public FruitLoadMoreBottomView(Context context) {
        this(context, null);
    }

    public FruitLoadMoreBottomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FruitLoadMoreBottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public View getView() {
        return this;
    }

    private void init() {
        View rootView = View.inflate(getContext(), R.layout.load_more, null);
        refreshArrow = (ImageView) rootView.findViewById(R.id.iv_arrow);
        refreshTextView = (TextView) rootView.findViewById(R.id.tv);
        loadingView = (ImageView) rootView.findViewById(R.id.iv_loading);
        addView(rootView);
    }

    @Override
    public void onPullingUp(float fraction, float maxBottomHeight, float bottomHeight) {
        if (fraction > -1f) refreshTextView.setText(R.string.pulluploadmore);
        if (fraction < -1f) refreshTextView.setText(R.string.releaseloadmore);
        float rotation = fraction * bottomHeight / maxBottomHeight * 180;
        if (rotation < -180)
            rotation = -180;
        refreshArrow.setRotation(rotation);
    }

    @Override
    public void startAnim(float maxBottomHeight, float bottomHeight) {
        refreshTextView.setText(R.string.loadmoreing);
        refreshArrow.setVisibility(GONE);
        loadingView.setVisibility(VISIBLE);
    }

    @Override
    public void onPullReleasing(float fraction, float maxBottomHeight, float bottomHeight) {
        if (fraction > -1f) {
            refreshTextView.setText(R.string.pulluploadmore);
            float rotation = fraction * bottomHeight / maxBottomHeight * 180;
            if (rotation < -180)
                rotation = -180;
            refreshArrow.setRotation(rotation);
            if (refreshArrow.getVisibility() == GONE) {
                refreshArrow.setVisibility(VISIBLE);
                loadingView.setVisibility(GONE);
            }
        }
    }

    @Override
    public void onFinish() {
        loadingView.setVisibility(GONE);
        refreshTextView.setText("加载完成");
    }

    @Override
    public void reset() {
        refreshArrow.setVisibility(VISIBLE);
        loadingView.setVisibility(GONE);
        refreshTextView.setText(R.string.pulluploadmore);
    }
}
