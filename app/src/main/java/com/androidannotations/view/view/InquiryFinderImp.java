package com.androidannotations.view.view;

import com.androidannotations.entitys.InquiryRecordInfo;
import com.androidannotations.net.RestManager;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.List;

@EBean
public class InquiryFinderImp implements InquiryFinder{

    @Bean
    RestManager restManager;

    @Override
    public List<InquiryRecordInfo.DataBean> findAll() {
        return null;
    }
}