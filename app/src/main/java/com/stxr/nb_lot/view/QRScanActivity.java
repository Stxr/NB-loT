package com.stxr.nb_lot.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.stxr.nb_lot.R;
import com.stxr.nb_lot.entity.QRCodeEntity;
import com.stxr.nb_lot.service.MyService;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.qrcode.core.QRCodeView;

/**
 * Created by stxr on 2018/4/7.
 */

public class QRScanActivity extends BaseActivity implements QRCodeView.Delegate {
    public static final String RESULT = "id";
    public static final int RESULT_CODE = 231;
    @BindView(R.id.qr_scan)
    QRCodeView qrScan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan);
        ButterKnife.bind(this);
        qrScan.setDelegate(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        qrScan.startCamera();
        qrScan.showScanRect();
        qrScan.startSpot();
    }

    @Override
    protected void onDestroy() {
//        Intent intent = new Intent(this, MyService.class);
//        stopService(intent);
        qrScan.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        qrScan.stopCamera();
        super.onStop();
    }

    /**
     * 扫码成功后的回调
     * @param result
     */
    @Override
    public void onScanQRCodeSuccess(String result) {
        Intent intent = new Intent(this, MyService.class);
        Gson gson = new Gson();
        QRCodeEntity code = gson.fromJson(result, QRCodeEntity.class);
        String id = code.getId();
        if (id.length() == 15) { //简单验证id是否合法
            intent.putExtra(RESULT, id);
            intent.putExtra("action", "login");
            startService(intent);
            finish();
        }
//        setResult(RESULT_CODE,intent);
        vibrate();
//        finish();
//        qrScan.startSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(200);
        }
    }

}
