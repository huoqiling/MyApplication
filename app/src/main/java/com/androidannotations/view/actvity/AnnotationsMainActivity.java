package com.androidannotations.view.actvity;

import android.text.TextUtils;
import android.util.Log;

import com.androidannotations.base.BaseAnnotationsActivity;
import com.androidannotations.base.BaseAnnotationsFragment;
import com.androidannotations.base.BaseRestManager;
import com.androidannotations.net.RestManager;
import com.androidannotations.utils.Constant;
import com.androidannotations.utils.MyCache;
import com.androidannotations.utils.ToastUtil;
import com.example.zyfx_.myapplication.R;
import com.example.zyfx_.myapplication.bean.BaseEntity;
import com.example.zyfx_.myapplication.bean.UserInfo;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

/**
 * Created by zyfx_ on 2017/5/16.
 */
@EActivity(R.layout.activity_annotations_main)
public class AnnotationsMainActivity extends BaseAnnotationsActivity {

    @Bean
    ToastUtil toastUtil;

    @Bean
    RestManager restManager;

    @Bean
    MyCache myCache;

    private BaseAnnotationsFragment mainFragment;

    @AfterViews
    void init() {
        showMainFragment();
        tokenLogin();
    }

    @UiThread
    void tokenLogin() {
        String userName = myCache.getUserName();
        String token = myCache.getToken();
        Log.i("zhangx", "userName--" + userName);
        Log.i("zhangx", "token--" + token);
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(token)) {
            restManager.setActivity(this);
            restManager.tokenLogin(new BaseRestManager.OnRestListener() {
                @Override
                public void onRequestSuccess(BaseEntity baseInfo) {
                    UserInfo userResult = (UserInfo) baseInfo;
                    if (userResult.isSuccess()) {

                    } else {
                        myCache.setLoginState(false);
                        start(SignInActivity_.class);
                    }
                }

                @Override
                public void onRequestFail() {
                    toastUtil.showTextToast("token登录失败");
                }

                @Override
                public boolean isShowProgressDialog() {
                    return false;
                }

                @Override
                public boolean isCancelable() {
                    return false;
                }
            }, userName, token);
        }
    }

    private void showMainFragment() {
        if (mainFragment == null) {
            mainFragment = new com.androidannotations.view.fragment.MainFragment_();
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
