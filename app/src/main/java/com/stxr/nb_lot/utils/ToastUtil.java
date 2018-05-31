package com.stxr.nb_lot.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by stxr on 2018/3/30.
 * Toast封装
 */

public class ToastUtil {
    private static Toast toast;

    @SuppressLint("ShowToast")
    public static void show(Context context, String message) {
        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }
        toast.setText(message);
        toast.show();
    }
    @SuppressLint("ShowToast")
    public static void show(Context context, int message) {
        if (toast == null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }
        toast.setText(message);
        toast.show();
    }
}
