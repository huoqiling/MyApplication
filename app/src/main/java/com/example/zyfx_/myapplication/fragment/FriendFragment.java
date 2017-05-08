package com.example.zyfx_.myapplication.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.zyfx_.myapplication.R;
import com.example.zyfx_.myapplication.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Author zhangxin
 * @date 2017/3/20 10:13
 * @description 朋友
 **/
public class FriendFragment extends BaseFragment {

    @Bind(R.id.textInput)
    TextInputLayout textInputLayout;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Bind(R.id.friendRoot)
    LinearLayout friendRoot;

    @Override
    protected int getLayout() {
        return R.layout.fragment_friend;
    }

    @Override
    protected void initView(View view) {
        EditText editText = textInputLayout.getEditText();
        if (editText != null) {
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    if (s.length() > 4) {
                        textInputLayout.setError("Password error");
                        textInputLayout.setErrorEnabled(true);
                    } else {
                        textInputLayout.setErrorEnabled(false);
                    }
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }


    }

    @Override
    public void fetchData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.fab)
    public void onClick() {
        Snackbar.make(friendRoot, "Snackbar comes out", Snackbar.LENGTH_LONG)
                .setAction("Action", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Toast comes out", Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }
}
