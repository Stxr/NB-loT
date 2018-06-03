package com.stxr.nb_lot.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stxr.nb_lot.R;
import com.stxr.nb_lot.entity.InterestEntity;
import com.stxr.nb_lot.entity.MyID;
import com.stxr.nb_lot.entity.MyUser;
import com.stxr.nb_lot.myconst.MyConst;
import com.stxr.nb_lot.presenter.UserNBloT;
import com.stxr.nb_lot.utils.PayUtil;
import com.stxr.nb_lot.utils.ToastUtil;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by stxr on 2018/5/31.
 */

public class DetailsActivity extends BaseActivity {
    public static final String INTEREST = "interest";
    private InterestEntity interestEntity;

    @BindView(R.id.tv_ticket_name)
    TextView tv_ticket_name;
    @BindView(R.id.iv_ticket_pic)
    ImageView iv_ticket_pic;
    @BindView(R.id.tv_ticket_attention)
    TextView tv_ticket_attention;
    @BindView(R.id.tv_ticket_buy)
    TextView tv_ticket_buy;
    @BindView(R.id.tv_ticket_price)
    TextView tv_ticket_price;

    @Override
    int getLayoutResId() {
        return R.layout.activity_detail;
    }

    @Override
    String title() {
        return "门票详情";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        interestEntity = (InterestEntity) intent.getSerializableExtra(INTEREST);
        tv_ticket_name.setText(interestEntity.getName());
        tv_ticket_attention.setText(interestEntity.getAttention());
        Glide.with(this).load(interestEntity.getPicList().get(0).getPicUrl()).into(iv_ticket_pic);
    }

    public static Intent newInstance(Context context, InterestEntity entity) {
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(INTEREST, entity);
        return intent;
    }

    @OnClick(R.id.tv_ticket_buy)
    void pay() {
        PayUtil payUtil = new PayUtil();
        payUtil.pay(DetailsActivity.this,interestEntity.getName(),"购票", new PayUtil.OnPayResponse() {
            @Override
            public void onSuccess(String s) {
                final MyID id = new MyID();
                id.setId(MyConst.ID1);
                id.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            MyUser user = BmobUser.getCurrentUser(MyUser.class);
                            BmobRelation relation = new BmobRelation();
                            relation.add(id);
                            user.setLockId(relation);
                            user.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        ToastUtil.show(DetailsActivity.this, "成功支付");
                                    } else {
                                        ToastUtil.show(DetailsActivity.this, e.getMessage());
                                    }
                                }
                            });
                        }
                    }
                });

//                UserNBloT.getClient().send(MyConst.ID1, new byte[]{49});
            }
        });
    }
}
