package com.stxr.nb_lot.presenter;

/**
 * Created by stxr on 2018/4/6.
 */

public interface ICallback {
    void login(boolean isSuccess);

    void receive(byte[] data);

    void isSuccess(boolean isSuccess);

    //    void
    abstract class Login implements ICallback {
        @Override
        public void receive(byte[] data) {

        }

        @Override
        public void isSuccess(boolean isSuccess) {

        }
    }

    abstract class Receive implements ICallback {
        @Override
        public void login(boolean isSuccess) {

        }

        @Override
        public void isSuccess(boolean isSuccess) {

        }
    }

}
