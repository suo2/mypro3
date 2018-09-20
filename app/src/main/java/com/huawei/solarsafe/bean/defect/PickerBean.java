package com.huawei.solarsafe.bean.defect;

/**
 * Created by P00319 on 2016/10/30.
 */
public class PickerBean {

    /* 显示名称 */
    private String name;
    /* 上传名称 */
    private String useName;
    /* 对应id */
    private String id;
    /**
     * 电话
     */
    private String mobile;
    private int userType;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setUseName(String useName) {
        this.useName = useName;
    }

    public String getUseName() {
        return useName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
