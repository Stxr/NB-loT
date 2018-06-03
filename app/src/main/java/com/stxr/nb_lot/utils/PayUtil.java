package com.stxr.nb_lot.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.eagle.pay66.Pay66;
import com.eagle.pay66.listener.CommonListener;
import com.stxr.nb_lot.myconst.MyConst;
import com.stxr.nb_lot.ui.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by stxr on 2018/5/31.
 */

public class PayUtil {

    private Activity activity;

    public void pay(final Activity activity, String name,String description,final OnPayResponse response) {
        Pay66.createOrder(1, name, description, new CommonListener() {
            @Override
            public void onStart() {
//                Log.d(TAG, "onStart: ");
            }

            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess(String s) {
                String orderId = null;
                try {
                    JSONObject object = new JSONObject(s);
                    JSONObject data = object.getJSONObject("data");
                    orderId = data.getString("orderId");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Pay66.pay(activity, orderId, 1, "AliPay", new CommonListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        response.onSuccess(s);
//                        Toast.makeText(activity, "成功支付", Toast.LENGTH_SHORT).show();
//                        client.send(MyConst.ID1, new byte[]{49});
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
            }

            @Override
            public void onCompleted() {
//                Log.d(TAG, "onCompleted: ");
            }
        });
    }

    public interface OnPayResponse {
        void onSuccess(String s);

//        void onError(int i, String s);

    }
}
