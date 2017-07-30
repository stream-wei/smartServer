package com.stream.netty.db.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by stream on 2017/7/30.
 */
public class MacRecord implements Serializable {
    
    private int id;
    
    private String macId;
    
    private String imei;
    
    private String sessionId;
    
    private Date createDate;
    
    private Date updateDate;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getMacId() {
        return macId;
    }
    
    public void setMacId(String macId) {
        this.macId = macId;
    }
    
    public String getImei() {
        return imei;
    }
    
    public void setImei(String imei) {
        this.imei = imei;
    }
    
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public Date getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    public Date getUpdateDate() {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    
    @Override
    public String toString() {
        return "MacRecord=[id=" + id + ",macId=" + macId + ",imei=" + imei + ",sessionId=" + sessionId + "]";
    }
}
