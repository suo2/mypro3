package com.huawei.solarsafe.bean.stationmagagement;

import java.util.List;

/**
 * Created by p00229 on 2017/6/8.
 */

public class ChangeStationBindInvsBean {
    private boolean success;
    private DataBean data;
    private int failCode;
    private Object params;
    private Object message;

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

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public static class DataBean {
        private int total;
        private int pageNo;
        private int pageSize;
        private int pageCount;

        private List<ListBean> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private long id;
            private Object parentId;
            private String stationCode;
            private int devTypeId;
            private String busiName;
            private String busiCode;
            private String esnCode;
            private String modelVersionCode;
            private String softwareVersion;
            private Object longitude;
            private Object latitude;
            private Object interval;
            private long createDate;
            private long updateDate;
            private Object createUser;
            private Object updateUser;
            private int dbShardingId;
            private String tableShardingId;
            private Object beginDate;
            private Object endDate;
            private int domainId;
            private int twoLevelDomain;
            private String capacity;//装机容量
            private Object baudrate;
            private int endian;
            private String pvNum;

            public String getPvNum() {
                return pvNum;
            }

            public void setPvNum(String pvNum) {
                this.pvNum = pvNum;
            }


            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public Object getParentId() {
                return parentId;
            }

            public void setParentId(Object parentId) {
                this.parentId = parentId;
            }


            public String getStationCode() {
                return stationCode;
            }

            public void setStationCode(String stationCode) {
                this.stationCode = stationCode;
            }

            public int getDevTypeId() {
                return devTypeId;
            }

            public void setDevTypeId(int devTypeId) {
                this.devTypeId = devTypeId;
            }

            public String getBusiName() {
                return busiName;
            }

            public void setBusiName(String busiName) {
                this.busiName = busiName;
            }

            public String getBusiCode() {
                return busiCode;
            }

            public void setBusiCode(String busiCode) {
                this.busiCode = busiCode;
            }


            public String getEsnCode() {
                return esnCode;
            }

            public void setEsnCode(String esnCode) {
                this.esnCode = esnCode;
            }

            public String getModelVersionCode() {
                return modelVersionCode;
            }

            public void setModelVersionCode(String modelVersionCode) {
                this.modelVersionCode = modelVersionCode;
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

            public Object getInterval() {
                return interval;
            }

            public void setInterval(Object interval) {
                this.interval = interval;
            }

            public long getCreateDate() {
                return createDate;
            }

            public void setCreateDate(long createDate) {
                this.createDate = createDate;
            }

            public long getUpdateDate() {
                return updateDate;
            }

            public void setUpdateDate(long updateDate) {
                this.updateDate = updateDate;
            }

            public Object getCreateUser() {
                return createUser;
            }

            public void setCreateUser(Object createUser) {
                this.createUser = createUser;
            }

            public Object getUpdateUser() {
                return updateUser;
            }

            public void setUpdateUser(Object updateUser) {
                this.updateUser = updateUser;
            }

            public int getDbShardingId() {
                return dbShardingId;
            }

            public void setDbShardingId(int dbShardingId) {
                this.dbShardingId = dbShardingId;
            }

            public String getTableShardingId() {
                return tableShardingId;
            }

            public void setTableShardingId(String tableShardingId) {
                this.tableShardingId = tableShardingId;
            }

            public void setBeginDate(Object beginDate) {
                this.beginDate = beginDate;
            }

            public Object getEndDate() {
                return endDate;
            }

            public void setEndDate(Object endDate) {
                this.endDate = endDate;
            }

            public int getDomainId() {
                return domainId;
            }

            public void setDomainId(int domainId) {
                this.domainId = domainId;
            }

            public int getTwoLevelDomain() {
                return twoLevelDomain;
            }

            public void setTwoLevelDomain(int twoLevelDomain) {
                this.twoLevelDomain = twoLevelDomain;
            }

            public Object getBaudrate() {
                return baudrate;
            }

            public void setBaudrate(Object baudrate) {
                this.baudrate = baudrate;
            }

            public int getEndian() {
                return endian;
            }

            public void setEndian(int endian) {
                this.endian = endian;
            }

            public String getCapacity() {
                return capacity;
            }

            public void setCapacity(String capacity) {
                this.capacity = capacity;
            }

            public String getSoftwareVersion() {
                return softwareVersion;
            }

            public void setSoftwareVersion(String softwareVersion) {
                this.softwareVersion = softwareVersion;
            }
        }
    }
}
