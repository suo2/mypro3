package com.huawei.solarsafe.bean.user.info;

import com.google.gson.Gson;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

/**
 * Created by p00507
 * on 2017/4/10.
 */
public class CompanyImformationBean extends BaseEntity {
    private String updateDate;
    private String  updateUser;
    private Long id;
    private String companyName;
    private String companyTel;
    private String companyAddress;
    private String  companyWebsite;
    private String filingNo;
    private CompanyImformationBean imformationBean;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        String s = jsonObject.toString();
        Gson gson = new Gson();
        imformationBean = gson.fromJson(s, CompanyImformationBean.class);
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getCompanyName() {
        return companyName;
    }


    public String getCompanyTel() {
        return companyTel;
    }


    public String getCompanyAddress() {
        return companyAddress;
    }


    public String getCompanyWebsite() {
        return companyWebsite;
    }


    public CompanyImformationBean getImformationBean() {
        return imformationBean;
    }

    public String getFilingNo() {
        return filingNo;
    }


    @Override
    public String toString() {
        return "CompanyImformationBean{" +
                ", updateDate='" + updateDate + '\'' +
                ", updateUser='" + updateUser + '\'' +
                ", id=" + id +
                ", companyName='" + companyName + '\'' +
                ", companyTel='" + companyTel + '\'' +
                ", companyAddress='" + companyAddress + '\'' +
                ", companyWebsite='" + companyWebsite + '\'' +
                '}';
    }
}
