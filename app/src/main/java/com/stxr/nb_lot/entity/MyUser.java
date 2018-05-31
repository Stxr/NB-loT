package com.stxr.nb_lot.entity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by stxr on 2018/4/16.
 */

public class MyUser extends BmobUser {
    private BmobRelation lockId;

    public BmobRelation getLockId() {
        return lockId;
    }

    public void setLockId(BmobRelation lockId) {
        this.lockId = lockId;
    }
}
