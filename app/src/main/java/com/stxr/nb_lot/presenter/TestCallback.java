package com.stxr.nb_lot.presenter;

import android.util.Log;

import cn.usr.UsrCloudMqttCallbackAdapter;

/**
 * Created by stxr on 2018/4/6.
 */

public class TestCallback extends UsrCloudMqttCallbackAdapter {
    public static final String TAG="TestCallback";
    private ICallback callback;

    public TestCallback(ICallback callback) {
        this.callback = callback;
    }

    @Override
    public void onConnectAck(int returnCode, String description) {
        super.onConnectAck(returnCode, description);
        Log.e(TAG, "onConnectAck() called with: returnCode = [" + returnCode + "], description = [" + description + "]");
        if (returnCode == 0) {
            callback.login(true);
        } else if (returnCode == 1) {
            callback.login(false);
        }
    }

    @Override
    public void onSubscribeAck(int messageId, String clientId, String topics, int returnCode) {
        super.onSubscribeAck(messageId, clientId, topics, returnCode);
        Log.e(TAG, "onSubscribeAck() called with: messageId = [" + messageId + "], clientId = [" + clientId + "], topics = [" + topics + "], returnCode = [" + returnCode + "]");
    }

    @Override
    public void onDisSubscribeAck(int messageId, String clientId, String topics, int returnCode) {
        super.onDisSubscribeAck(messageId, clientId, topics, returnCode);
        Log.e(TAG, "onDisSubscribeAck() called with: messageId = [" + messageId + "], clientId = [" + clientId + "], topics = [" + topics + "], returnCode = [" + returnCode + "]");
    }

    @Override
    public void onReceiveEvent(int messageId, String topic, byte[] data) {
        super.onReceiveEvent(messageId, topic, data);
        Log.e(TAG, "onReceiveEvent() called with: messageId = [" + messageId + "], topic = [" + topic + "], data = [" + data + "]");
        callback.receive(data);
    }

    @Override
    public void onPublishDataAck(int messageId, String topic, boolean isSuccess) {
        super.onPublishDataAck(messageId, topic, isSuccess);
        Log.e(TAG, "onPublishDataAck() called with: messageId = [" + messageId + "], topic = [" + topic + "], isSuccess = [" + isSuccess + "]");
    }
}
