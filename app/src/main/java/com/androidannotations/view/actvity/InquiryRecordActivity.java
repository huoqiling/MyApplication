package com.androidannotations.view.actvity;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.androidannotations.base.BaseAdapterHelper;
import com.androidannotations.base.BaseAnnotationsActivity;
import com.androidannotations.base.BaseRestManager;
import com.androidannotations.base.QuickAdapter;
import com.androidannotations.entitys.InquiryRecordInfo;
import com.androidannotations.net.RestManager;
import com.androidannotations.utils.ToastUtil;
import com.example.zyfx_.myapplication.CustomApplication;
import com.example.zyfx_.myapplication.R;
import com.example.zyfx_.myapplication.bean.BaseEntity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author zhangxin
 * @date 2017/5/24 15:45
 * @description 询价记录
 **/
@EActivity(R.layout.activity_inquiry_record)
public class InquiryRecordActivity extends BaseAnnotationsActivity {

    @ViewById
    ListView listView;

    @Bean
    RestManager restManager;

    @Bean
    ToastUtil toastUtil;

    @App
    CustomApplication application;

    private MyAdapter mAdapter;

    private List<InquiryRecordInfo.DataBean> recordList;


    @AfterViews
    void init() {
        recordList = new ArrayList<>();
        mAdapter = new MyAdapter(application);
        listView.setAdapter(mAdapter);
        getDataInfo();
    }

    @UiThread(delay = 100)
    void getDataInfo() {
        restManager.setActivity(this);
        restManager.getInquiryList(new BaseRestManager.OnRestListener() {
            @Override
            public void onRequestSuccess(BaseEntity baseInfo) {
                InquiryRecordInfo recordInfo = (InquiryRecordInfo) baseInfo;
                if (recordInfo.isSuccess()) {
                    Log.d("zhangx","recordInfo--"+recordInfo.data.get(0).toString());
                    setData(recordInfo.data);
                } else {
                    toastUtil.showTextToast(recordInfo.getMsg());
                }
            }

            @Override
            public void onRequestFail() {
                toastUtil.showTextToast("获取信息失败");
            }

            @Override
            public boolean isShowProgressDialog() {
                return true;
            }

            @Override
            public boolean isCancelable() {
                return true;
            }
        }, 1, 10, 0);
    }

    @UiThread
    void setData(List<InquiryRecordInfo.DataBean> recordList) {
        if (null != recordList && recordList.size() > 0) {
            mAdapter.addAll(recordList);
        }
    }

    public class MyAdapter extends QuickAdapter<InquiryRecordInfo.DataBean> {

        public MyAdapter(Context context) {
            super(context, R.layout.item_inquiry_record);
        }

        @Override
        protected void convert(BaseAdapterHelper helper, InquiryRecordInfo.DataBean item) {
            helper.setText(R.id.tvLandNumber, item.id);
            helper.setText(R.id.tvInquiryPrice, item.askPrice + "π");
            if (item.status == 1) {
                helper.setText(R.id.tvLandStatus, "询价中");
            } else if (item.status == 2) {
                helper.setText(R.id.tvLandStatus, "询价成功");
            } else if (item.status == 3) {
                helper.setText(R.id.tvLandStatus, "询价被拒绝");
            }
        }
    }
}
