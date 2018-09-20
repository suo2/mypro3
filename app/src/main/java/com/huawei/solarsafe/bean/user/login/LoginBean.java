package com.huawei.solarsafe.bean.user.login;

/**
 * Created by p00319 on 2017/2/14.
 */
//登录请求返回数据实体类
public class LoginBean {

    /** 账号创建日期 */
    private long createDate;
    /** 账号创建时间 */
    private long createTime;
    /** 创建账号的管理员(名) */
    private String createUser;
    /** 描述信息 */
    private String description;
    /** 域id */
    private long domainid;
    /** 纬度 */
    private double latitude;
    /** 登录用户名 */
    private String loginName;
    /** 经度 */
    private double longitude;
    /** 邮箱 */
    private String mail;
    /** 密码 */
    private String password;
    /** 用户状态(active or locked) */
    private String status;
    /** 联系电话 */
    private String tel;
    /** 用户类型(system or normal) */
    private String type;
    /**  */
    private long updateDate;
    /**  */
    private String updateUser;
    /** 姓名 */
    private String userName;
    /**  */
    private String userType;
    /** 用户id */
    private long userid;
    /**隐私申明是否更新 0：不同意；1：同意*/
    private int privateSupport = -1;

    public int getPrivateSupport() {
        return privateSupport;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDomainid() {
        return domainid;
    }

    public void setDomainid(long domainid) {
        this.domainid = domainid;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserType() {
        return userType;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }
}
