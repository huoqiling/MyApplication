package com.androidannotations;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.zyfx_.myapplication.R;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zyfx_ on 2017/5/12.
 */
public class CustomLoading extends AppCompatDialogFragment {

    @Bind(R.id.loading)
    AVLoadingIndicatorView loading;


    private boolean cancellable;

    public CustomLoading setIsCancellable(boolean cancellable) {
        this.cancellable = cancellable;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initDialog();
        View view = inflater.inflate(R.layout.dialog_loading, container);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initDialog() {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(R.color.transparent);
        getDialog().getWindow().setGravity(Gravity.CENTER);
        getDialog().setCanceledOnTouchOutside(cancellable);
        getDialog().setCancelable(cancellable);
    }

    private void initView() {
        loading.smoothToShow();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        loading.smoothToHide();
    }
}
