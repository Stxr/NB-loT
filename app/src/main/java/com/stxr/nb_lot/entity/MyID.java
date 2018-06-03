package com.stxr.nb_lot.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by stxr on 2018/6/1.
 */

public class MyID extends BmobObject {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyID myID = (MyID) o;

        return id != null ? id.equals(myID.id) : myID.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
