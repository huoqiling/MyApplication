package com.preference;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zyfx_.myapplication.CustomApplication;
import com.example.zyfx_.myapplication.R;
import com.preference.base.BaseSettingActivity;
import com.preference.base.BaseSettingFragment;

/**
 * @Author zhangxin
 * @date 2017/5/9 14:35
 * @description 喜欢的设置
 **/
public class LikeActivity extends BaseSettingActivity {


    @Override
    public PreferenceFragment getSettingFragment() {
        return new LikeSettingFragment();
    }

    @SuppressLint("ValidFragment")
    private class LikeSettingFragment extends BaseSettingFragment implements Preference.OnPreferenceChangeListener {

        private ListPreference jinYongPreference;
        private EditTextPreference fruitsTextPreference;
        private CheckBoxPreference singPreference;
        private PreferenceManager manager;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference_like_setting);
            init();
        }

        private void init() {
            manager = getPreferenceManager();
            jinYongPreference = (ListPreference) findPreference(PreferenceConstant.JIN_YONG);
            fruitsTextPreference = (EditTextPreference) findPreference(PreferenceConstant.FRUITS);
            singPreference = (CheckBoxPreference) findPreference(PreferenceConstant.SING);

            jinYongPreference.setOnPreferenceChangeListener(this);
            fruitsTextPreference.setOnPreferenceChangeListener(this);
            singPreference.setOnPreferenceChangeListener(this);

            jinYongPreference.setSummary(getCacheData(PreferenceConstant.CACHE_JIN_YONG, getActivity().getString(R.string.jinYong0)));
            fruitsTextPreference.setSummary("喜欢的水果是：" + getCacheData(PreferenceConstant.CACHE_FRUITS, "苹果"));
        }

        @Override
        public void onResume() {
            super.onResume();
        }

        @Override
        public void onPause() {
            super.onPause();
        }

        @Override
        public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
            //判断是否是EditTextPreference
            super.onPreferenceTreeClick(preferenceScreen, preference);
            Preference tmp_pre = manager.findPreference(preference.getKey());
            //判断是否是EditTextPreference
            if (tmp_pre instanceof EditTextPreference) {
                EditTextPreference textPreference = (EditTextPreference) tmp_pre;
                EditText ed = textPreference.getEditText();
                Editable etable = ed.getText();
                Selection.setSelection(etable, etable.length());//光标置位

            }
            return true;
        }

        private String getCacheData(String key, String defaultValue) {
            String cacheData = CustomApplication.getInstance().mACache.getAsString(key);
            if (!TextUtils.isEmpty(cacheData)) {
                return cacheData;
            } else {
                return defaultValue + "";
            }
        }

        private void saveCacheData(String key, String value) {
            CustomApplication.getInstance().mACache.put(key, value);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if (preference.getKey().equals(PreferenceConstant.JIN_YONG)) {
                int value = Integer.parseInt(String.valueOf(newValue));
                preference.setSummary(jinYongPreference.getEntries()[value]);
                saveCacheData(PreferenceConstant.CACHE_JIN_YONG, jinYongPreference.getEntries()[value].toString());
            } else if (preference.getKey().equals(PreferenceConstant.FRUITS)) {
                String value = String.valueOf(newValue);
                preference.setSummary("喜欢的水果是：" + value);
                saveCacheData(PreferenceConstant.CACHE_FRUITS, value);
            } else if (preference.getKey().equals(PreferenceConstant.SING)) {
                if (String.valueOf(newValue).equals("true")) {
                    showToast("喜欢唱歌");
                } else {
                    showToast("不喜欢唱歌");
                }
            }
            return true;
        }

        private void showToast(String msg) {
            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
        }
    }
}
