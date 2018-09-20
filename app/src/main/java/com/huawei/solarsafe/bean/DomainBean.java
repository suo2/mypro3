package com.huawei.solarsafe.bean;

import java.io.Serializable;

/**
 * Created by P00229 on 2017/6/26.
 */

public class DomainBean  implements Serializable {


    private static final long serialVersionUID = -1889974747201807573L;
    private boolean success;

    private DataBean data;
    private int failCode;
    private Object params;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getFailCode() {
        return failCode;
    }

    public void setFailCode(int failCode) {
        this.failCode = failCode;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }

    public static class DataBean implements Serializable{
        private static final long serialVersionUID = -4586126866937206767L;
        private int id;
        private String description;
        private int pid;
        private int level;
        private Object type;
        private Object createTime;
        private Object longitude;
        private Object latitude;
        private Object radius;
        private boolean check;
        private String currency;
        private Object twoLevelDomain;
        private String domainName;
        private String supportPoor;

        public String getDomainName() {
            return domainName;
        }

        public void setDomainName(String domainName) {
            this.domainName = domainName;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getSupportPoor() {
            return supportPoor;
        }

        public void setSupportPoor(String supportPoor) {
            this.supportPoor = supportPoor;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }


        public Object getLongitude() {
            return longitude;
        }

        public void setLongitude(Object longitude) {
            this.longitude = longitude;
        }

        public Object getLatitude() {
            return latitude;
        }

        public void setLatitude(Object latitude) {
            this.latitude = latitude;
        }

        public Object getRadius() {
            return radius;
        }

        public void setRadius(Object radius) {
            this.radius = radius;
        }


        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }

        public String getCurrency() {
            return currency;
        }

        public Object getTwoLevelDomain() {
            return twoLevelDomain;
        }

        public void setTwoLevelDomain(Object twoLevelDomain) {
            this.twoLevelDomain = twoLevelDomain;
        }

    }
}
