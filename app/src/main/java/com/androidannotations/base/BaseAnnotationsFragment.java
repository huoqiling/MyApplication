package com.androidannotations.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.androidannotations.AnnotationsMainActivity;

public class BaseAnnotationsFragment extends Fragment {

    View view;
    boolean changed;
    protected boolean isRootFragment;
    protected BaseAnnotationsFragment parentFragment;
    protected AnnotationsMainActivity mActivity;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mActivity = (AnnotationsMainActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "is null!");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;

        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    public void onFragmentResume() {
        if (changed) {
            changed = false;
        }
    }

    public void setRootFragment(boolean isRootFragment) {
        this.isRootFragment = isRootFragment;
    }

    public void showFragment(BaseAnnotationsFragment fragment) {
        mActivity.showFragment(fragment);
    }

    public String getStackName() {
        return getClass().getName();
    }

    public void popBackStack() {
        if (isRootFragment) {
            mActivity.removeFragment(this);
        } else {
            if (getFragmentManager() != null) {
                getFragmentManager().popBackStack();
            }
        }

    }
}
