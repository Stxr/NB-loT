package com.stxr.nb_lot;

import android.app.Application;
import android.util.Log;

import com.eagle.pay66.Pay66;
import com.stxr.nb_lot.myconst.MyConst;
import com.stxr.nb_lot.presenter.ICallback;
import com.stxr.nb_lot.presenter.TestClient;
import com.stxr.nb_lot.presenter.UserNBloT;
import com.stxr.nb_lot.utils.ToastUtil;
import com.stxr.nb_lot.utils.Tool;

import java.util.Arrays;
import java.util.PriorityQueue;

import cn.bmob.v3.Bmob;

/**
 * Created by stxr on 2018/4/4.
 */

public class MyApplication extends Application {

    public static final String TAG = "MyApplication";
    private static MyApplication instance;
//    private UserNBloT client;
    @Override

    public void onCreate() {
        super.onCreate();
        instance = this;
        Bmob.initialize(this, "be7a048b09c62e2abbdcccc15982daa0");
        Pay66.init("f31b1bdfafb14c76846e6422cebbcbcd", getApplicationContext());
//        client = UserNBloT.getInstance(new ICallback.Receive() {
//            //接收消息回调
//            @Override
//            public void receive(final byte[] data) {
//                if (Tool.bytesToString(data).equals("50")) {
//                    ToastUtil.show(instance, "开锁成功");
//                }
//                Log.e(TAG, "receive() called with: data = [" + Arrays.toString(data) + "]");
//                Log.e(TAG, "Thread:" + Thread.currentThread().getName());
//            }
//        });
//        client.subscribe(MyConst.ID1);
    }
    //得到当前的上下文
    public static MyApplication getInstance() {
        return instance;
    }
}
