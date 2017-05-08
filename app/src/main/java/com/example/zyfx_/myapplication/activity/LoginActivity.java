package com.example.zyfx_.myapplication.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.zyfx_.myapplication.R;
import com.example.zyfx_.myapplication.base.BaseActivity;
import com.example.zyfx_.myapplication.bean.LzyResponse;
import com.example.zyfx_.myapplication.bean.UserInfo;
import com.example.zyfx_.myapplication.net.DialogCallback;
import com.example.zyfx_.myapplication.util.DensityUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.etName)
    TextInputEditText etName;

    @Bind(R.id.etPassword)
    TextInputEditText etPassword;

    @Bind(R.id.btnLogin)
    Button btnLogin;

    @Bind(R.id.scrollView)
    ScrollView scrollView;

    @Bind(R.id.loginRoot)
    LinearLayout loginRoot;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {
        setBackToolbar(toolbar, "");
        controlKeyboardLayout(loginRoot, scrollView, 80);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void onBackListener() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnLogin)
    public void onClick() {
        final String userName = etName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(userName)) {
            showToast("请输入用户名");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            showToast("请输入密码");
            return;
        }

        HttpParams httpParams = new HttpParams();
        httpParams.put("username", userName);
        httpParams.put("password", password);


        OkGo.post("http://wallet.pigamegroup.com/user/applogin")
                .tag(this)
                .params(httpParams)
                .execute(new DialogCallback<UserInfo>(this) {

                    @Override
                    public void onSuccess(UserInfo userInfo, Call call, Response response) {
                        super.onSuccess(userInfo, call, response);
                        try {
                            Log.d("zhangx", userInfo.data.user.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
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
                    scrollView.smoothScrollTo(0, DensityUtil.dip2px(LoginActivity.this, dpHeight));
                } else if ((bottom - oldBottom) > 100) {
                    scrollView.smoothScrollTo(0, 0);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
    }

}
