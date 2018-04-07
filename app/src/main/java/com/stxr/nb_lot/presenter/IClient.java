package com.stxr.nb_lot.presenter;

/**
 * Created by stxr on 2018/4/6.
 */

public interface IClient {
    void login(String name, String password);

    void logout();

    void send(String id, byte[] data);

    void subscribe(String id);

    void disSubscribe();
}
