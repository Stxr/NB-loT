package com.stxr.nb_lot.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.stxr.nb_lot.R;
import com.stxr.nb_lot.entity.MyUser;
import com.stxr.nb_lot.presenter.ICallback;
import com.stxr.nb_lot.presenter.UserNBloT;
import com.stxr.nb_lot.utils.ShareUtil;
import com.stxr.nb_lot.utils.ToastUtil;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class SignInActivity extends BaseActivity {
    @BindView(R.id.edt_id)
    EditText edt_id;
    @BindView(R.id.edt_password)
    EditText edt_password;
    private UserNBloT client;

    @Override
    int getLayoutResId() {
        return R.layout.activity_sign_in;
    }

    @Override
    String title() {
        return "登录";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        edt_id.setText((String)ShareUtil.get("id", ""));
        edt_password.setText((String)ShareUtil.get("password", ""));
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
        final String id = edt_id.getText().toString();
        final String password = edt_password.getText().toString();
        MyUser user = new MyUser();
        user.setUsername(id);
        user.setPassword(password);
        loadingDialog.show();
        user.login(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if (e == null) {
                    loadingDialog.dismiss();
                    client.login("浮躁的小螃蟹", "star@tang1234");
                    ShareUtil.put("id",id);
                    ShareUtil.put("password", password);
                } else {
                    ToastUtil.show(SignInActivity.this, e.getMessage());
                }
            }
        });
    }

    @OnClick(R.id.tv_sign_up)
    void signUp() {
        startActivity(SignUpActivity.class);
    }
}

