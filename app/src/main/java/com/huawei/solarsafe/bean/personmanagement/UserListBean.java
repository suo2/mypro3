package com.huawei.solarsafe.bean.personmanagement;

import java.io.Serializable;
import java.util.List;

/**
 * Created by P00229 on 2017/4/10.
 */
public class UserListBean {
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


    public static class ListBean implements Serializable {
        private static final long serialVersionUID = -8712232793714834982L;
        private int createDate;
        private String createUser;
        private int updateDate;
        private Object updateUser;
        private int userid;
        private int domainid;
        private Object areaid;
        private String type;
        private String status;
        private Object description;
        private String loginName;
        private String password;
        private String mail;
        private String tel;
        private String userName;
        private long createTime;
        private Object longitude;
        private Object latitude;
        private int twoLevelDomain;

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

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public int getDomainid() {
            return domainid;
        }

        public void setDomainid(int domainid) {
            this.domainid = domainid;
        }

        public Object getAreaid() {
            return areaid;
        }

        public void setAreaid(Object areaid) {
            this.areaid = areaid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public String getLoginName() {
            return loginName;
        }

        public void setLoginName(String loginName) {
            this.loginName = loginName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
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

        public int getTwoLevelDomain() {
            return twoLevelDomain;
        }

        public void setTwoLevelDomain(int twoLevelDomain) {
            this.twoLevelDomain = twoLevelDomain;
        }
    }
}
