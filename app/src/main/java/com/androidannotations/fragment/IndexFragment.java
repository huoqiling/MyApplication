package com.androidannotations.fragment;

import android.content.Intent;

import com.androidannotations.SignInActivity_;
import com.androidannotations.base.BaseAnnotationsFragment;
import com.example.zyfx_.myapplication.CustomApplication;
import com.example.zyfx_.myapplication.R;

import org.androidannotations.annotations.App;
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

    @Click
    void btnLogin(){
        Intent intent = new Intent(application, SignInActivity_.class);
        startActivity(intent);
    }

}
