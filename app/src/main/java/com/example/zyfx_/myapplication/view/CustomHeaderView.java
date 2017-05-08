package com.example.zyfx_.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zyfx_.myapplication.R;
import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.OnAnimEndListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @Author zhangxin
 * @date 2017/3/17 15:02
 * @description 自定义headview
 **/
public class CustomHeaderView extends FrameLayout implements IHeaderView {


    @Bind(R.id.iv_arrow)
    ImageView ivArrow;

    @Bind(R.id.iv_loading)
    ImageView ivLoading;

    @Bind(R.id.tv)
    TextView refreshTextView;

    public CustomHeaderView(Context context) {
        this(context, null);
    }

    public CustomHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View headView = LayoutInflater.from(context).inflate(R.layout.view_sinaheader, this);
        ButterKnife.bind(this, headView);
        addView(headView);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingDown(float fraction, float maxHeadHeight, float headHeight) {
        if (fraction < 1f) refreshTextView.setText("下拉刷新");
        if (fraction > 1f) refreshTextView.setText("下拉刷新释放");
        ivArrow.setRotation(fraction * headHeight / maxHeadHeight * 180);
    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {
        if (fraction < 1f) {
            refreshTextView.setText("下拉刷新");
            ivArrow.setRotation(fraction * headHeight / maxHeadHeight * 180);
            if (ivArrow.getVisibility() == GONE) {
                ivArrow.setVisibility(VISIBLE);
                ivLoading.setVisibility(GONE);
            }
        }
    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        refreshTextView.setText("正在刷新");
        ivArrow.setVisibility(GONE);
        ivLoading.setVisibility(VISIBLE);
    }

    @Override
    public void onFinish(OnAnimEndListener animEndListener) {
        animEndListener.onAnimEnd();
    }

    @Override
    public void reset() {

    }
}
