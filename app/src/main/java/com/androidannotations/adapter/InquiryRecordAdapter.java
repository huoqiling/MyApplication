package com.androidannotations.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidannotations.entitys.InquiryRecordInfo;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhangxin
 * @date 2017/5/24 15:50
 * @description
 **/
@EBean
public class InquiryRecordAdapter extends BaseAdapter {

    @RootContext
    Context context;

    private List<InquiryRecordInfo.DataBean> recordList;

    @AfterInject
    void init() {
        recordList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Object getItem(int i) {
        return recordList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
//        InquiryRecordView recordView ;
//        if (convertView == null) {
//            recordView = InquiryRecordView_.build(context);
//        } else {
//            recordView = (InquiryRecordView) convertView;
//        }
//        recordView.bindData(recordList.get(i));
        return convertView;
    }

    public void update(List<InquiryRecordInfo.DataBean> recordList) {
        this.recordList.clear();
        this.recordList.addAll(recordList);
    }

    public void append(List<InquiryRecordInfo.DataBean> recordList) {
        this.recordList.addAll(recordList);
        notifyDataSetChanged();
    }

    public void delete(InquiryRecordInfo.DataBean dataBean) {
        this.recordList.remove(dataBean);
        notifyDataSetChanged();
    }
}
