package com.androidannotations.view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.androidannotations.base.BaseFrameLayout;
import com.androidannotations.entitys.InquiryRecordInfo;
import com.example.zyfx_.myapplication.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by zyfx_ on 2017/5/24.
 */
@EViewGroup(R.layout.item_inquiry_record)
public class InquiryRecordView extends BaseFrameLayout {

    @ViewById
    TextView tvLandStatus;

    @ViewById
    TextView tvInquiryPrice;

    @ViewById
    TextView tvLandNumber;

    public InquiryRecordView(Context context) {
        this(context, null);
    }

    public InquiryRecordView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InquiryRecordView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void bindData(InquiryRecordInfo.DataBean dataBean) {
        setTextViewData(tvLandNumber, dataBean.id);
        setTextViewData(tvInquiryPrice, dataBean.askPrice);
        if (dataBean.status == 1) {
            setTextViewData(tvLandStatus, "询价中");
        } else if (dataBean.status == 2) {
            setTextViewData(tvLandStatus, "询价成功");
        } else if (dataBean.status == 3) {
            setTextViewData(tvLandStatus, "询价被拒绝");
        }
    }
}
