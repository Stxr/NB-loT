package com.stxr.nb_lot.entity;

/**
 * Created by stxr on 2018/4/8.
 */

public class QRCodeEntity {
    private String id;
    private String port;
    private String ip;

    public QRCodeEntity() {
    }

    public QRCodeEntity(String id, String port, String ip) {
        this.id = id;
        this.port = port;
        this.ip = ip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
