package com.huawei.solarsafe.bean.personmanagement;

import java.util.List;

/**
 * <pre>
 *     author: Tzy
 *     time  : $date$
 *     desc  : 创建业主用户请求实体类
 * </pre>
 */
public class CreatePersonInfo {
    String[] roleid;
    List<AreaId> areaid;
    CreatePerson user;

    public void setRoleid(String[] roleid) {
        this.roleid = roleid;
    }

    public List<AreaId> getAreaid() {
        return areaid;
    }

    public void setAreaid(List<AreaId> areaid) {
        this.areaid = areaid;
    }

    public CreatePerson getUser() {
        return user;
    }

    public void setUser(CreatePerson user) {
        this.user = user;
    }

    public CreatePerson newCreatePerson(){
        return new CreatePerson();
    }

    public class CreatePerson{
        String userName;
        String loginName;
        String password;
        String mail;
        String tel;
        String toSend;
        String description;
        String domainid;
        String userType;
        String status;
        String userAvatar;


        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }


        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDomainid() {
            return domainid;
        }

        public void setDomainid(String domainid) {
            this.domainid = domainid;
        }


        public void setToSend(String toSend) {
            this.toSend = toSend;
        }
    }

}
