package com.stxr.nb_lot.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.stxr.nb_lot.presenter.ICallback;
import com.stxr.nb_lot.presenter.UserNBloT;
import com.stxr.nb_lot.utils.Tool;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by stxr on 2018/4/8.
 * 负责与模块连接和通信
 */

public class MyService extends Service implements ICallback {
    private UserNBloT lot;
    private String TAG = "MyService";
    private volatile byte[] receiveData;
    private String id;
    private boolean isConnected = false;

    @Override
    public void onCreate() {
        super.onCreate();
        lot = UserNBloT.getInstance(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getStringExtra("action").equals("login")) {
            id = intent.getStringExtra("id");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    isConnected=connected();
                    if (isConnected) {
                        lot.send(id, new byte[]{4});
                    } else {
                        lot.send(id, new byte[]{5});
                    }
                    receiveData = null;
                }
            }).start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 三次握手建立连接
     *
     * @return
     */
    public boolean connected() {
        lot.send(id, new byte[]{1});
        while (receiveData == null) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!waitMessage(Tool.bytesToString(receiveData), "2", 300)) {
            return false;
        }
        lot.send(id, new byte[]{3});
        return true;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void login(boolean isSuccess) {

    }

    @Override
    public void receive(byte[] data) {
        Log.e(TAG, "receive() called with: data = [" + Tool.bytesToString(data) + "]");
        receiveData = data;
    }

    @Override
    public void isSuccess(boolean isSuccess) {
        Log.e(TAG, "isSuccess() called with: isSuccess = [" + isSuccess + "]");
    }

    /**
     * 匹配消息，相匹配了才执行下一步
     *
     * @param source
     * @param match
     * @param millis
     */
    private boolean waitMessage(String source, String match, long millis) {
        Log.e(TAG, "waitMessage() called with: source = [" + source + "], match = [" + match + "], millis = [" + millis + "]");
        int count = 0;
        while (!source.equals(match)) {
            try {
                Thread.sleep(millis);
                if (++count > 115) {
                    return false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    class MyBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }
}
