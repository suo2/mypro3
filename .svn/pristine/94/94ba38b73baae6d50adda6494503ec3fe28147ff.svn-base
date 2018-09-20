package com.huawei.solarsafe.bean.stationmagagement;

import java.util.List;

/**
 * Created by P00229 on 2017/5/17.
 */
public class GridPrice {
    private boolean success;
    private int failCode;
    private Object params;
    private Object message;
    private boolean isDefaultPrice=true;

    public boolean isDefaultPrice() {
        return isDefaultPrice;
    }

    public void setDefaultPrice(boolean defaultPrice) {
        isDefaultPrice = defaultPrice;
    }

    private List<DataBean> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }


    public static class DataBean {

        private PriceBean price;

        private List<PriceItemBean> priceItem;

        public PriceBean getPrice() {
            return price;
        }

        public void setPrice(PriceBean price) {
            this.price = price;
        }

        public List<PriceItemBean> getPriceItem() {
            return priceItem;
        }

        public void setPriceItem(List<PriceItemBean> priceItem) {
            this.priceItem = priceItem;
        }

        public static class PriceBean {
            private int createDate;
            private String createUser;
            private int updateDate;
            private Object updateUser;
            private int id;
            private String stationCode;
            private long beginDate;
            private long endDate;
            private int domainId;
            private Object name;

            public int getCreateDate() {
                return createDate;
            }

            public void setCreateDate(int createDate) {
                this.createDate = createDate;
            }

            public String getCreateUser() {
                return createUser;
            }

            public void setCreateUser(String createUser) {
                this.createUser = createUser;
            }

            public int getUpdateDate() {
                return updateDate;
            }

            public void setUpdateDate(int updateDate) {
                this.updateDate = updateDate;
            }

            public Object getUpdateUser() {
                return updateUser;
            }

            public void setUpdateUser(Object updateUser) {
                this.updateUser = updateUser;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getStationCode() {
                return stationCode;
            }

            public void setStationCode(String stationCode) {
                this.stationCode = stationCode;
            }

            public long getBeginDate() {
                return beginDate;
            }

            public void setBeginDate(long beginDate) {
                this.beginDate = beginDate;
            }

            public long getEndDate() {
                return endDate;
            }

            public void setEndDate(long endDate) {
                this.endDate = endDate;
            }

            public int getDomainId() {
                return domainId;
            }

            public void setDomainId(int domainId) {
                this.domainId = domainId;
            }

            public Object getName() {
                return name;
            }

            public void setName(Object name) {
                this.name = name;
            }

        }

        public static class PriceItemBean {
            private int createDate;
            private String createUser;
            private int updateDate;
            private Object updateUser;
            private int id;
            private int beginHour;
            private int endHour;
            private double price;

            public int getCreateDate() {
                return createDate;
            }

            public void setCreateDate(int createDate) {
                this.createDate = createDate;
            }

            public String getCreateUser() {
                return createUser;
            }

            public void setCreateUser(String createUser) {
                this.createUser = createUser;
            }

            public int getUpdateDate() {
                return updateDate;
            }

            public void setUpdateDate(int updateDate) {
                this.updateDate = updateDate;
            }

            public Object getUpdateUser() {
                return updateUser;
            }

            public void setUpdateUser(Object updateUser) {
                this.updateUser = updateUser;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getBeginHour() {
                return beginHour;
            }

            public void setBeginHour(int beginHour) {
                this.beginHour = beginHour;
            }

            public int getEndHour() {
                return endHour;
            }

            public void setEndHour(int endHour) {
                this.endHour = endHour;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

        }
    }
}
