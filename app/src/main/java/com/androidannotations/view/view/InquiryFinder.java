package com.androidannotations.view.view;

import com.androidannotations.entitys.InquiryRecordInfo;

import java.util.List;

public interface InquiryFinder {
    List<InquiryRecordInfo.DataBean> findAll();
}