package com.stxr.nb_lot.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.stxr.nb_lot.entity.MyUser;
import com.stxr.nb_lot.view.CustomLoadingDialog;

import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

/**
 * Created by stxr on 2018/4/4.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private Toast toast;
    protected String TAG = getClass().getSimpleName();
    protected CustomLoadingDialog loadingDialog;
    protected MyUser currentUser  = BmobUser.getCurrentUser(MyUser.class);
    abstract int getLayoutResId();

    abstract String title();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        setTitle(title());
        ButterKnife.bind(this);
        loadingDialog = new CustomLoadingDialog(this, "正在加载");
        requestPermission();
    }
    protected void toast(String text) {
        if (toast == null) {
            toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }

    protected void startActivity(Class<?> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    protected void startServiceWithParms(Class<?> service, Bundle bundle) {
        Intent intent = new Intent(this, service);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startService(intent);
    }

    protected void log(String log) {
        Log.e(TAG, log);
    }

    private void requestPermission() {
        //请求相机权限
        String permissions[] = {
                Manifest.permission.CAMERA
        };
        if (needRequestPermission(permissions)) {
            ActivityCompat.requestPermissions(this, permissions, 110);
        }
    }

    private boolean needRequestPermission(String[] permissions) {
        for (String p : permissions) {
            if (ContextCompat.checkSelfPermission(this, p) == PackageManager.PERMISSION_DENIED) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isGrant = true;
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                isGrant = false;
            }
        }
        if (isGrant) {
            toast("权限请求成功");
        } else {
            toast("权限失败成功");
        }
    }
}
