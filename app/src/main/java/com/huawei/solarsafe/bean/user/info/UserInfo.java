package com.huawei.solarsafe.bean.user.info;

import com.google.gson.Gson;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

/**
 * Created by p00229 on 2017/1/5.
 */
public class UserInfo extends BaseEntity {

    private UserInfo userInfo;
    private int createDate;
    private String createUser;
    private int updateDate;
    private Object updateUser;
    private long userid;
    private long domainid;
    private String type;
    private String status;
    private String description;
    private String loginName;
    private String password;
    private String mail;
    private String tel;
    private String userName;
    private String userAvatar;
    private long createTime;
    private Object longitude;
    private Object latitude;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        String s = jsonObject.toString();
        Gson gson = new Gson();
        userInfo = gson.fromJson(s, UserInfo.class);
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    public UserInfo getUserInfo() {
        return userInfo;
    }


    public void setUserid(long userid) {
        this.userid = userid;
    }

    public void setDomainid(long domainid) {
        this.domainid = domainid;
    }

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

    public long getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public long getDomainid() {
        return domainid;
    }

    public void setDomainid(int domainid) {
        this.domainid = domainid;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
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

    public Object getUserAvatar() {
        return userAvatar;
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

}
