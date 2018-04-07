package com.stxr.nb_lot.presenter;

import org.eclipse.paho.client.mqttv3.MqttException;

import cn.usr.UsrCloudMqttClientAdapter;

/**
 * Created by stxr on 2018/4/6.
 */

public class TestClient extends UsrCloudMqttClientAdapter implements IClient{
    @Override
    public void login(String name, String password) {
        try {
            Connect(name, password);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void logout() {
        try {
            DisConnectUnCheck();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(String id, byte[] data) {
        try {
            publishForDevId(id, data);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void subscribe(String id) {
        try {
            SubscribeForDevId(id);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disSubscribe() {
        try {
            DisSubscribeforuName();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


}
