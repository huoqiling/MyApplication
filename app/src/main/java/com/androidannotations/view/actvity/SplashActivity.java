package com.androidannotations.view.actvity;

import android.widget.ImageView;

import com.androidannotations.base.BaseAnnotationsActivity;
import com.androidannotations.utils.MyCache;
import com.example.zyfx_.myapplication.R;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by zyfx_ on 2017/5/25.
 */
@EActivity(R.layout.activity_splash)
public class SplashActivity extends BaseAnnotationsActivity {

    @ViewById
    ImageView ivSplash;

    @Bean
    MyCache myCache;

    @AfterViews
    void init() {
        Picasso.with(this).load(R.mipmap.iv_putao).into(ivSplash);
        turn();
    }

    @Background(delay = 2000)
    void turn() {
        boolean hasLogin = myCache.getLoginState();
        if (hasLogin) {
            start(AnnotationsMainActivity_.class);
        } else {
            start(SignInActivity_.class);
        }
        finish();
    }
}
