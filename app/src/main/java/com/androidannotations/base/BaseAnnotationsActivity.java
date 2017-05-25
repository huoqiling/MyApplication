package com.androidannotations.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.zyfx_.myapplication.R;

public class BaseAnnotationsActivity extends AppCompatActivity {

    protected PopupWindow popupWindow;

    private BaseAnnotationsFragment rootFragment;
    private BaseAnnotationsFragment currentFragment;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        getSupportFragmentManager().addOnBackStackChangedListener(new OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                FragmentManager fragmentManager = getSupportFragmentManager();
                int count = fragmentManager.getBackStackEntryCount();
                if (count > 0) {
                    BackStackEntry backStackEntry = fragmentManager.getBackStackEntryAt(count - 1);
                    String name = backStackEntry.getName();
                    int id = backStackEntry.getId();
                    BaseAnnotationsFragment fragment = (BaseAnnotationsFragment) fragmentManager.findFragmentByTag(name);
                    if (fragment != null) {
                        BaseAnnotationsActivity.this.currentFragment = fragment;
                        fragment.onFragmentResume();
                    }
                } else {
                    BaseAnnotationsActivity.this.currentFragment = rootFragment;
                    rootFragment.onFragmentResume();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (currentFragment != null) {
            currentFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void setRootFragment(BaseAnnotationsFragment fragment) {
        if (rootFragment != null) {
            removeFragment(rootFragment);
        }
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
        fragment.setRootFragment(true);
        rootFragment = fragment;
        currentFragment = fragment;
    }

    public void removeFragment(BaseAnnotationsFragment fragment) {
        getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
    }

    public void showFragment(BaseAnnotationsFragment fragment) {
        fragment.parentFragment = currentFragment;
        currentFragment = fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        String stackName = fragment.getStackName();

        transaction.add(R.id.container, fragment, stackName);

        transaction.addToBackStack(stackName);
        transaction.commitAllowingStateLoss();
    }

    public BaseAnnotationsFragment getCurrentFragment() {
        return currentFragment;
    }

    public void setTextViewData(TextView textView, String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                textView.setText(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTextViewData(TextView textView, String str, String unit) {
        try {
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(unit)) {
                textView.setText(String.format("%s%s", str, unit));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTextViewHtmlData(TextView textView, String htmlStr) {
        try {
            if (!TextUtils.isEmpty(htmlStr)) {
                textView.setText(Html.fromHtml(htmlStr));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}
