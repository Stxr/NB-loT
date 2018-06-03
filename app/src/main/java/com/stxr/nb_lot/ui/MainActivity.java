package com.stxr.nb_lot.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stxr.nb_lot.R;
import com.stxr.nb_lot.adapter.InterestAdapter;
import com.stxr.nb_lot.entity.InterestEntity;
import com.stxr.nb_lot.entity.MyID;
import com.stxr.nb_lot.myconst.MyConst;
import com.stxr.nb_lot.presenter.ICallback;
import com.stxr.nb_lot.presenter.UserNBloT;
import com.stxr.nb_lot.utils.InterestUtil;
import com.stxr.nb_lot.utils.PayUtil;
import com.stxr.nb_lot.utils.ToastUtil;
import com.stxr.nb_lot.utils.Tool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class MainActivity extends BaseActivity {
    public static final int REQUEST_CODE = 32;
    @BindView(R.id.btn_subscribe)
    Button button;
    private UserNBloT client;

    @BindView(R.id.rv_interest)
    RecyclerView rv_interest;

    @Override
    int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    String title() {
        return "旅游服务系统";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        client = UserNBloT.getInstance(new ICallback.Receive() {
            //接收消息回调
            @Override
            public void receive(final byte[] data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //2的ascii码的十进制是49
//                        tv_display_code.setText(Tool.bytesToString(data));
                        if (Tool.bytesToString(data).equals("50")) {
                            toast("开锁成功。");
                            Log.e(TAG, "Thread:" + Thread.currentThread().getName());
                        }
                    }
                });
                Log.e(TAG, "receive() called with: data = [" + Arrays.toString(data) + "]");
                Log.e(TAG, "Thread:" + Thread.currentThread().getName());
            }
        });
        client.subscribe(MyConst.ID1);
        requestForInterest();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.scan, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.scan:
                Intent intent = new Intent(MainActivity.this, QRScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫描二维码得到的是json对象
        if (requestCode == REQUEST_CODE && resultCode == QRScanActivity.RESULT_CODE) {
            final String id = data.getStringExtra(QRScanActivity.RESULT);
            final MyID myID = new MyID();
            myID.setId(id);
            final BmobQuery<MyID> query = new BmobQuery<>();
            query.addWhereRelatedTo("lockId", new BmobPointer(currentUser));
            query.findObjects(new FindListener<MyID>() {
                @Override
                public void done(List<MyID> list, BmobException e) {
                    if (list.contains(myID)) {
                        client.send(MyConst.ID1, new byte[]{49});
                        MyID id1 = list.get(list.indexOf(myID));
                        currentUser.getLockId().remove(id1);
                        currentUser.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    toast("删除成功");
                                } else {
                                    toast(e.getMessage());
                                }
                            }
                        });
                    } else {
                        PayUtil payUtil = new PayUtil();
                        payUtil.pay(MainActivity.this,"景区购票","扫码支付", new PayUtil.OnPayResponse() {
                            @Override
                            public void onSuccess(String s) {
                                client.send(MyConst.ID1, new byte[]{49});
                            }
                        });
                    }
                }
            });

        }
    }

    //付款系统
    @OnClick(R.id.btn_subscribe)
    void requestForInterest() {
        new InterestUtil().setOnResponse(new InterestUtil.InterestsResponse() {
            @SuppressWarnings("SpellCheckingInspection")
            @Override
            public void success(String s){
                try {
                    JSONObject object = new JSONObject(s);
                    JSONObject showapi_res_body = object.getJSONObject("showapi_res_body");
                    JSONObject pagebean = showapi_res_body.getJSONObject("pagebean");
                    JSONArray contentlist = pagebean.getJSONArray("contentlist");

                    Gson gson = new Gson();
                    List<InterestEntity> interestEntities = gson.fromJson(contentlist.toString(), new TypeToken<List<InterestEntity>>() {
                    }.getType());
                    Log.d(TAG, "success: " + interestEntities.toString());
                    rv_interest.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    InterestAdapter adapter = new InterestAdapter(interestEntities, MainActivity.this);
                    rv_interest.setAdapter(adapter);
                    adapter.setOnItemClickListener(new InterestAdapter.OnItemClick() {
                        @Override
                        public void onClick(InterestEntity entity) {
//                            ToastUtil.show(MainActivity.this, entity.toString());
                            startActivity(DetailsActivity.newInstance(MainActivity.this, entity));
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).execute("海南");
    }

//    void onSubscribe() {
//        Pay66.createOrder(1, "商品名称", "商品描述", new CommonListener() {
//            @Override
//            public void onStart() {
//                Log.d(TAG, "onStart: ");
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                Log.d(TAG, "onError: " + s + i);
//            }
//
//            @Override
//            public void onSuccess(String s) {
//                String orderId = null;
//                try {
//                    JSONObject object = new JSONObject(s);
//                    JSONObject data = object.getJSONObject("data");
//                    orderId = data.getString("orderId");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Pay66.pay(MainActivity.this, orderId, 1, "AliPay", new CommonListener() {
//                    @Override
//                    public void onStart() {
//
//                    }
//
//                    @Override
//                    public void onError(int i, String s) {
//
//                    }
//
//                    @Override
//                    public void onSuccess(String s) {
//                        Toast.makeText(MainActivity.this, "成功支付", Toast.LENGTH_SHORT).show();
//                        client.send(MyConst.ID1, new byte[]{49});
//                    }
//
//                    @Override
//                    public void onCompleted() {
//
//                    }
//                });
//            }
//
//            @Override
//            public void onCompleted() {
//                Log.d(TAG, "onCompleted: ");
//            }
//        });
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        client.disSubscribe();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}

