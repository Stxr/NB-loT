package com.stxr.nb_lot.utils;

/**
 * Created by stxr on 2018/4/8.
 */

public class Tool {
    public static String bytesToString(byte[] data) {
        if (data == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (byte aData : data) {
            builder.append(aData);
        }
        return builder.toString();
    }
}
