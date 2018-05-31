package com.stxr.nb_lot.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by stxr on 2018/5/29.
 */

public class InterestEntity implements Serializable {
    private String summary;
    private String name;
    private String attention;
    private List<Pic> picList;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public List<Pic> getPicList() {
        return picList;
    }

    public void setPicList(List<Pic> picList) {
        this.picList = picList;
    }

    public class Pic implements Serializable {
        String picUrl;
        String picUrlSmall;

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getPicUrlSmall() {
            return picUrlSmall;
        }

        public void setPicUrlSmall(String picUrlSmall) {
            this.picUrlSmall = picUrlSmall;
        }

        @Override
        public String toString() {
            return "Pic{" +
                    "picUrl='" + picUrl + '\'' +
                    ", picUrlSmall='" + picUrlSmall + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "InterestEntity{" +
                "summary='" + summary + '\'' +
                ", name='" + name + '\'' +
                ", attention='" + attention + '\'' +
                ", picList=" + picList +
                '}';
    }
}
