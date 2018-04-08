package com.stxr.nb_lot.presenter;

import android.util.Log;

import com.stxr.nb_lot.utils.Tool;

/**
 * Created by stxr on 2018/4/6.
 */

public class UserNBloT {
    private static IClient client;
    private static TestClient testClient;
    private volatile static UserNBloT INSTANCE;
    private String TAG = "UserNBloT";

    public static UserNBloT getInstance(ICallback callback) {
        if (INSTANCE == null) {
            synchronized (UserNBloT.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserNBloT();
                }
            }
        }
        TestCallback testCallback = new TestCallback(callback);
        testClient.setUsrCloudMqttCallback(testCallback);
        client = testClient;
        return INSTANCE;
    }
    private UserNBloT() {
        testClient = new TestClient();
    }

    public void login(String name, String password) {
        client.login(name, password);
    }

    public void send(String id, byte[] data) {
        Log.d(TAG, "send() called with: id = [" + id + "], data = [" + Tool.bytesToString(data) + "]");
        client.send(id, data);
    }

    public void subscribe(String id) {
        client.subscribe(id);
    }

    public void disSubscribe() {
        client.disSubscribe();
    }

}
