package com.stxr.nb_lot;

import android.app.Application;

import java.util.PriorityQueue;

/**
 * Created by stxr on 2018/4/4.
 */

public class MyApplication extends Application {
    private static MyApplication instance;
    @Override

    public void onCreate() {
        super.onCreate();
        instance = this;
    }
    //得到当前的上下文
    public static MyApplication getInstance() {
        return instance;
    }
}
