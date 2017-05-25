package com.androidannotations.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Base64;

import com.androidannotations.utils.AESUtil;

import javax.crypto.spec.SecretKeySpec;

/**
 * Created by zyfx_ on 2017/3/14.
 */

public class SecurityStorage {
    // AES密码
    private final static byte[] AES_PASSWORD = "19491001".getBytes();
    private final static int KEY_SIZE = 128;
    private final static SecretKeySpec KEY_SPEC = AESUtil.getSecretKeySpec(AES_PASSWORD, KEY_SIZE);

    private SharedPreferences mStorage;

    public SecurityStorage(@NonNull Context context, @NonNull String preferenceName) {
        mStorage = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);

    }

    /**
     * 删除所有保存的内容
     */
    public void clear() {
        mStorage.edit()
                .clear()
                .apply();
    }

    /**
     * 所有的值都是加密后以String形式保存的
     * 取值时，先对保存的值进行解密，再转换成原类型
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        String encryptValue = encrypt(value);

        mStorage.edit()
                .putString(key, encryptValue)
                .apply();
    }

    public String getString(String key, String defValue) {
        String value = mStorage.getString(key, null);
        value = decrypt(value);

        if (value == null) {
            return defValue;
        }
        return value;
    }

    public Boolean getBoolean(String key, Boolean defValue) {
        String value = getString(key,
                defValue == null ? null : defValue.toString());

        return value == null ? null : Boolean.parseBoolean(value);
    }

    public Integer getInt(String key, Integer defValue) {
        String value = getString(key,
                defValue == null ? null : defValue.toString());

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defValue;
        }
    }

    public double getDouble(String key, Double defValue){
        String value = getString(key,
                defValue == null ? null : defValue.toString());

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defValue;
        }
    }

    public Long getLong(String key, Long defValue) {
        String value = getString(key,
                defValue == null ? null : defValue.toString());

        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return defValue;
        }
    }

    // 加密函数
    protected String encrypt(Object value) {
        if (value == null) {
            return null;
        } else if ("".equals(value)) {
            return "";
        }

        byte[] bytes = AESUtil.encrypt(value.toString().getBytes(), KEY_SPEC);

        if (bytes == null) {
            return null;
        }
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    // 解密函数
    protected String decrypt(String value) {
        if (value == null || "".equals(value)) {
            return value;
        }

        byte[] bytes = Base64.decode(value.getBytes(), Base64.DEFAULT);
        bytes = AESUtil.decrypt(bytes, KEY_SPEC);

        if (bytes == null) {
            return null;
        }
        return new String(bytes);
    }
}
