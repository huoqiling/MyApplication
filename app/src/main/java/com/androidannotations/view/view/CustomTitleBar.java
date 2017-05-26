package com.androidannotations.view.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidannotations.base.BaseFrameLayout;
import com.example.zyfx_.myapplication.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import butterknife.OnClick;

/**
 * @author: zhangxin
 * @time: 2017/4/7 15:16
 * @description: 自定义标题栏
 */
@EViewGroup(R.layout.view_title_bar)
public class CustomTitleBar extends BaseFrameLayout {

    @ViewById(R.id.btnBack)
    ImageView btnBack;

    @ViewById(R.id.tvTitle)
    TextView tvTitle;

    @ViewById(R.id.btnImage)
    ImageView btnImage;

    @ViewById(R.id.btnRight)
    TextView btnRight;


    private String titleText;
    private String rightButtonText;
    private int rightButtonTextColor = Color.parseColor("#19191a");
    private boolean showBackButton;
    private boolean showImages;
    private int imageIds = 0;

    private OnCustomTitleBarListener titleBarListener;

    public void setOnCustomTitleBarListener(OnCustomTitleBarListener titleBarListener) {
        this.titleBarListener = titleBarListener;
    }

    public CustomTitleBar(Context context) {
        this(context, null);
    }

    public CustomTitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomTitleBar);
        titleText = a.getString(R.styleable.CustomTitleBar_titleText);
        rightButtonText = a.getString(R.styleable.CustomTitleBar_rightButtonText);
        rightButtonTextColor = a.getColor(R.styleable.CustomTitleBar_rightButtonTextColor, rightButtonTextColor);
        showBackButton = a.getBoolean(R.styleable.CustomTitleBar_showBackButton, true);
        showImages = a.getBoolean(R.styleable.CustomTitleBar_showRightImages, false);
        imageIds = a.getResourceId(R.styleable.CustomTitleBar_rightImageSrc, 0);
        a.recycle();
    }

    @AfterViews
    void initView() {
        setTitleText(titleText);
        setRightBtnText(rightButtonText);
        if (!showBackButton) {
            invisibleView(btnBack);
        }
        if (!showImages) {
            invisibleView(btnImage);
        }
        if (imageIds > 0) {
            setRightImageResource(imageIds);
        }
    }

    public void setTitleText(String text) {
        if (!TextUtils.isEmpty(text)) {
            tvTitle.setText(text);
        }
    }

    public void setRightBtnText(String text) {
        if (!TextUtils.isEmpty(text)) {
            btnRight.setText(text);
            btnRight.setTextColor(rightButtonTextColor);
            showView(btnRight);
            goneView(btnImage);
        } else {
            goneView(btnRight);
        }
    }

    /**
     * 设置右边图片
     *
     * @param drawableId
     */
    public void setRightImageResource(int drawableId) {
        btnImage.setImageResource(drawableId);
    }

    /**
     * 隐藏右边图片
     */
    public void hideRightImage() {
        goneView(btnImage);
    }

    /**
     * 隐藏返回按钮
     */
    public void hideBackButton() {
        goneView(btnBack);
    }

    /**
     * 隐藏右边按钮
     */
    public void hideRightButton() {
        goneView(btnRight);
    }

    /**
     * 显示右边图片
     */
    public void showRightImage() {
        showView(btnImage);
    }

    /**
     * 显示返回按钮
     */
    public void showBackButton() {
        showView(btnBack);
    }

    /**
     * 显示右边按钮
     */
    public void showRightButton() {
        showView(btnRight);
    }

    @OnClick({R.id.btnBack, R.id.btnImage, R.id.btnRight})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                if (null != titleBarListener) {
                    titleBarListener.onBackClick();
                }
                break;
            case R.id.btnImage:
                if (null != titleBarListener) {
                    titleBarListener.onRightImageClick();
                }
                break;
            case R.id.btnRight:
                if (null != titleBarListener) {
                    titleBarListener.onRightTextClick();
                }
                break;
        }
    }


    public interface OnCustomTitleBarListener {
        void onBackClick();

        void onRightTextClick();

        void onRightImageClick();
    }
}
