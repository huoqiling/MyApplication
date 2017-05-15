package com.androidannotations;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.androidannotations.base.BaseRestManager;
import com.androidannotations.net.RestManager;
import com.example.zyfx_.myapplication.R;
import com.example.zyfx_.myapplication.bean.BaseEntity;
import com.example.zyfx_.myapplication.bean.UserInfo;
import com.example.zyfx_.myapplication.util.DensityUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * @Author zhangxin
 * @date 2017/5/12 11:16
 * @description 登录
 **/
@EActivity(R.layout.activity_sign_in)
public class SignInActivity extends AppCompatActivity {

    @ViewById
    LinearLayout loginRoot;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById
    ScrollView scrollView;

    @ViewById
    TextInputEditText etName;

    @ViewById
    TextInputEditText etPassword;

    @Bean
    RestManager restManager;

    @Bean
    ToastUtil toastUtil;

    @AfterViews
    void init() {
        controlKeyboardLayout(loginRoot, scrollView, 100);
    }

    /**
     * @param root       最外层布局，需要调整的布局
     * @param scrollView 被键盘遮挡的scrollToView，滚动root,使scrollToView在root可视区域的底部
     */
    public void controlKeyboardLayout(final View root, final ScrollView scrollView, final int dpHeight) {

        root.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if ((oldBottom - bottom) > 100) {
                    scrollView.smoothScrollTo(0, DensityUtil.dip2px(SignInActivity.this, dpHeight));
                } else if ((bottom - oldBottom) > 100) {
                    scrollView.smoothScrollTo(0, 0);
                }
            }
        });
    }

    @Click(R.id.btnLogin)
    void signIn() {
        getUserInfo();
    }

    void getUserInfo() {
        String userName = etName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(userName)) {
            toastUtil.showTextToast("请输入用户名");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            toastUtil.showTextToast("请输入密码");
            return;
        }
        try {
            restManager.setActivity(this);
            BaseRestManager.OnRestListener restListener = new BaseRestManager.OnRestListener() {
                @Override
                public void onRequestSuccess(BaseEntity baseInfo) {
                    UserInfo userResult = (UserInfo) baseInfo;
                    if (userResult.isSuccess()) {
                        toastUtil.showTextToast("登录成功");
//                        restManager.setSesstionId(userResult.getData().data.appLoginToken);
                    } else {
                        toastUtil.showTextToast(userResult.getMsg());
                    }
                }

                @Override
                public void onRequestFail() {
                    toastUtil.showTextToast("登录失败");
                }

                @Override
                public boolean isShowProgressDialog() {
                    return true;
                }
            };
            restManager.login(restListener, userName, password);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
