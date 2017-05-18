package com.androidannotations;

import com.androidannotations.base.BaseAnnotationsActivity;
import com.androidannotations.base.BaseAnnotationsFragment;
import com.androidannotations.fragment.MainFragment_;
import com.example.zyfx_.myapplication.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

/**
 * Created by zyfx_ on 2017/5/16.
 */
@EActivity(R.layout.activity_annotations_main)
public class AnnotationsMainActivity extends BaseAnnotationsActivity {

    @Bean
    ToastUtil toastUtil;

    private BaseAnnotationsFragment mainFragment;

    @AfterViews
    void init() {
        showMainFragment();
    }

    private void showMainFragment() {
        if (mainFragment == null) {
            mainFragment = new MainFragment_();
            setRootFragment(mainFragment);
        } else {
            toastUtil.showTextToast("mainFragment already exist");
        }
    }

    public void removeMainFragment() {
        if (mainFragment != null) {
            removeFragment(mainFragment);
            mainFragment = null;
        }
    }

}
