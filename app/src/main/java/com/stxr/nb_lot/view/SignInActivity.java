package com.stxr.nb_lot.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.stxr.nb_lot.R;
import com.stxr.nb_lot.presenter.ICallback;
import com.stxr.nb_lot.presenter.UserNBloT;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends BaseActivity{
    @BindView(R.id.edt_id)
    EditText edt_id;
    @BindView(R.id.edt_password)
    EditText edt_password;
    private UserNBloT client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        client = UserNBloT.getInstance(new ICallback.Login() {
            @Override
            public void login(boolean isSuccess) {
                if (isSuccess) {
                    startActivity(MainActivity.class);
                    finish();
                } else {
                    toast("登陆失败");
                }
            }

            @Override
            public void receive(byte[] data) {
                Log.e(TAG, "receive() called with: data = [" + Arrays.toString(data) + "]");
            }
        });
    }

    @OnClick(R.id.btn_sign_in)
    void signIn() {
        String id = edt_id.getText().toString();
        String password = edt_password.getText().toString();
        client.login(id,password);
    }

}

