package com.stxr.nb_lot.view;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;

import com.stxr.nb_lot.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.qrcode.core.QRCodeView;

/**
 * Created by stxr on 2018/4/7.
 */

public class QRScanActivity extends BaseActivity implements QRCodeView.Delegate {
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
        qrScan.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        qrScan.stopCamera();
        super.onStop();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        toast(result);
        vibrate();
        qrScan.startSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }
}
