package com.stxr.nb_lot.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.stxr.nb_lot.MyApplication;

/**
 * Created by stxr on 2018/3/31.
 */

public class ShareUtil {

    public static String NAME = "ACCOUNT";
    private static Context context = MyApplication.getInstance();
    /**
     * 存入数据
     * @param key
     * @param value
     */
    public static void put(String key,Object value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            editor.putString(key, value.toString());
        }
        editor.apply();
    }

    /**
     *  获取数据
     * @param key
     * @param defaultValue
     * @return
     */
    public static Object get(String key,Object defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        if (defaultValue instanceof String) {
          return sharedPreferences.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Float) {
            return sharedPreferences.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Long) {
            return sharedPreferences.getLong(key, (Long) defaultValue);
        } else {
            return null;
        }
    }

    /**
     * 删除一个值
     * @param key
     */
    public static void remove(String key) {
        SharedPreferences.Editor editor = context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.apply();
    }
}
