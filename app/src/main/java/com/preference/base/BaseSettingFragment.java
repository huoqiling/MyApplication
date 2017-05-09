package com.preference.base;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.preference.PreferenceConstant;

/**
 * Created by zyfx_ on 2017/5/9.
 */
public class BaseSettingFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesName(PreferenceConstant.PREFERENCE_NAME);
    }
}
