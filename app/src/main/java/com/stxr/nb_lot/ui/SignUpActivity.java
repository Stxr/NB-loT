package com.stxr.nb_lot.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.stxr.nb_lot.R;
import com.stxr.nb_lot.entity.MyUser;
import com.stxr.nb_lot.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class SignUpActivity extends BaseActivity {
    public static final int SIGN_UP_SUCCESS_RESULT_CODE = 0;
    public static final String ID = "id";
    private static final String TAG = "SignUpActivity";

    @BindView(R.id.edt_id)
    EditText edt_name;
    @BindView(R.id.edt_password)
    EditText edt_password;
    @BindView(R.id.edt_re_password)
    EditText edt_re_password;
    @BindView(R.id.btn_sign_up)
    Button btn_sign_up;
    @BindView(R.id.edt_email)
    EditText edt_email;
    private String name;
    private String email;
    private String password;
    private String rePassword;

    @Override
    int getLayoutResId() {
        return R.layout.activity_sign_up;
    }

    @Override
    String title() {
        return "注册";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.btn_sign_up)
    public void onClick(View v) {
        if (checkForm()) {
            //注册用户
            MyUser user = new MyUser();
            user.setUsername(name);
            user.setPassword(password);
            user.setEmail(email);
            user.signUp(new SaveListener<MyUser>() {
                @Override
                public void done(MyUser myUser, BmobException e) {
                    if (e == null) {
                        ToastUtil.show(SignUpActivity.this, R.string.success_sign_up);
                        returnSignIn();
                    } else if (e.getErrorCode() == 202) {
                        ToastUtil.show(SignUpActivity.this, R.string.username_already_taken);
                    } else if (e.getErrorCode() == 203) {
                        ToastUtil.show(SignUpActivity.this, R.string.email_already_taken);
                    } else {
                        Log.e(TAG, e.toString());
                    }
                }
            });
        } else {
            ToastUtil.show(this, R.string.error_sign_up);
        }
    }

    @OnClick(R.id.tv_link_sign_in)
    public void returnSignIn() {
        Intent intent = new Intent();
        intent.putExtra(ID, name);
        setResult(SIGN_UP_SUCCESS_RESULT_CODE, intent);
        finish();
    }

    /**
     * 对输入进行验证
     *
     * @return
     */
    private boolean checkForm() {

        name = edt_name.getText().toString();
        email = edt_email.getText().toString();
        password = edt_password.getText().toString();
        rePassword = edt_re_password.getText().toString();
        boolean isPass = true;
        if (name.isEmpty()) {
            edt_name.setError("请输入姓名");
            isPass = false;
        } else {
            edt_name.setError(null);
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edt_email.setError("错误的邮箱格式");
            isPass = false;
        } else {
            edt_email.setError(null);
        }
        if (password.isEmpty() || password.length() < 6) {
            edt_password.setError("至少填写6位数的密码");
            isPass = false;
        } else {
            edt_password.setError(null);
        }
        if (rePassword.isEmpty() || !rePassword.equals(password)) {
            edt_re_password.setError("两次密码不一致");
            isPass = false;
        } else {
            edt_re_password.setError(null);
        }
        return isPass;
    }

}
