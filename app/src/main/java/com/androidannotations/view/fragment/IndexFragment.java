package com.androidannotations.view.fragment;

import android.app.Activity;

import com.androidannotations.base.BaseAnnotationsFragment;
import com.androidannotations.base.BaseRestManager;
import com.androidannotations.net.RestManager;
import com.androidannotations.utils.ToastUtil;
import com.androidannotations.view.actvity.AnnotationsMainActivity;
import com.androidannotations.view.actvity.InquiryRecordActivity_;
import com.example.zyfx_.myapplication.CustomApplication;
import com.example.zyfx_.myapplication.R;
import com.example.zyfx_.myapplication.bean.BaseEntity;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

/**
 * @Author zhangxin
 * @date 2017/5/18 16:28
 * @description 首页
 **/
@EFragment(R.layout.fragment_annotations_index)
public class IndexFragment extends BaseAnnotationsFragment {

    @App
    CustomApplication application;

    @Bean
    RestManager restManager;

    @Bean
    ToastUtil toastUtil;

    AnnotationsMainActivity mainActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mainActivity = (AnnotationsMainActivity) activity;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Click(R.id.btnInquiry)
    void getInquiryInfo() {
        start(InquiryRecordActivity_.class);
    }

    @Click(R.id.btnLoginOut)
    void loginOut() {
        restManager.setActivity(mainActivity);
        restManager.loginOut(new BaseRestManager.OnRestListener() {
            @Override
            public void onRequestSuccess(BaseEntity baseInfo) {
                if (baseInfo.isSuccess()) {
                    toastUtil.showTextToast("退出成功");
                } else {
                    toastUtil.showTextToast(baseInfo.getMsg());
                }
            }

            @Override
            public void onRequestFail() {
                toastUtil.showTextToast("退出失败");
            }

            @Override
            public boolean isShowProgressDialog() {
                return true;
            }

            @Override
            public boolean isCancelable() {
                return true;
            }
        });
    }

}
