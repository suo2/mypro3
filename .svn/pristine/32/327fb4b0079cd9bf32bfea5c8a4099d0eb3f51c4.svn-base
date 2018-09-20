package com.huawei.solarsafe.bean.patrol;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Create Date: 2017/3/6
 * Create Author: P00171
 * Description :
 */
//新建巡检单查询电站信息返回实体类
public class PatrolObjForCreateTask implements ClusterItem{
    private String objId;
    private String objName;
    private String sId;
    private String sName;
    private double longitude;
    private double latitude;
    private long domainId;
    private int inspectStatus;
    private int timeZone;
    private String status;

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public String getSId() {
        return sId;
    }

    public void setSId(String sId) {
        this.sId = sId;
    }

    public String getSName() {
        return sName;
    }

    public void setSName(String sName) {
        this.sName = sName;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public long getDomainId() {
        return domainId;
    }

    public void setDomainId(long domainId) {
        this.domainId = domainId;
    }

    public int getInspectStatus() {
        return inspectStatus;
    }

    public void setInspectStatus(int inspectStatus) {
        this.inspectStatus = inspectStatus;
    }

    public int getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(int timeZone) {
        this.timeZone = timeZone;
    }


    @Override
    public LatLng getPosition() {
        return new LatLng(latitude,longitude);
    }


    public String getTitle() {
        return "";
    }


    public String getSnippet() {
        return "";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
