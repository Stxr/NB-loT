package com.stxr.nb_lot.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.stxr.nb_lot.R;
import com.stxr.nb_lot.entity.QRCodeEntity;
import com.stxr.nb_lot.myconst.MyConst;
import com.stxr.nb_lot.presenter.ICallback;
import com.stxr.nb_lot.presenter.UserNBloT;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    public static final int REQUEST_CODE = 32;
    @BindView(R.id.btn_subscribe)
    Button button;
    private UserNBloT client;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_display_code)
    TextView tv_display_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        toolbar.inflateMenu(R.menu.scan);
        client = UserNBloT.getInstance(new ICallback.Receive() {
            //接收消息回调
            @Override
            public void receive(byte[] data) {
                Log.e(TAG, "receive() called with: data = [" + Arrays.toString(data) + "]");
            }
        });
        client.subscribe(MyConst.ID);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.scan:
//                        startActivity(QRScanActivity.class);
                        Intent intent = new Intent(MainActivity.this, QRScanActivity.class);
                        startActivityForResult(intent, REQUEST_CODE);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫描二维码得到的是json对象
        if (requestCode == REQUEST_CODE && resultCode == QRScanActivity.RESULT_CODE) {
            String json = data.getStringExtra(QRScanActivity.RESULT);
            tv_display_code.setText(json);
            QRCodeEntity code = new Gson().fromJson(json, QRCodeEntity.class);
            client.send(code.getId(),new byte[]{12});
        }
    }

    @OnClick(R.id.btn_subscribe)
    void onSubscribe() {
        client.send(MyConst.ID, new byte[]{12});
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        client.disSubscribe();
    }
}

