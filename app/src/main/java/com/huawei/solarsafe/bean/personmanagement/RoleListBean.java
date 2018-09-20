package com.huawei.solarsafe.bean.personmanagement;

import java.util.List;

/**
 * Created by P00229 on 2017/4/13.
 */
public class RoleListBean {
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
        private int createDate;
        private String createUser;
        private int updateDate;
        private Object updateUser;
        private int id;
        private int domainid;
        private String roleName;
        private String status;
        private String type;
        private long createTime;

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

        public int getDomainid() {
            return domainid;
        }

        public void setDomainid(int domainid) {
            this.domainid = domainid;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

    }
}
