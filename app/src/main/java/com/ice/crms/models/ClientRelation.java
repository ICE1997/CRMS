package com.ice.crms.models;

public class ClientRelation {
    private int clientNo;
    private String clientName;
    private int clientType;
    private int clientStatus;
    private long date;
    private String clientAddr;

    public ClientRelation(int clientNo, String clientName, int clientType, int clientStatus, long date, String clientAddr) {
        this.clientNo = clientNo;
        this.clientName = clientName;
        this.clientType = clientType;
        this.clientStatus = clientStatus;
        this.date = date;
        this.clientAddr = clientAddr;
    }

    public ClientRelation() {

    }

    public int getClientNo() {
        return clientNo;
    }

    public void setClientNo(int clientNo) {
        this.clientNo = clientNo;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getClientType() {
        return clientType;
    }

    public void setClientType(int clientType) {
        this.clientType = clientType;
    }

    public int getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(int clientStatus) {
        this.clientStatus = clientStatus;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getClientAddr() {
        return clientAddr;
    }

    public void setClientAddr(String clientAddr) {
        this.clientAddr = clientAddr;
    }
}
