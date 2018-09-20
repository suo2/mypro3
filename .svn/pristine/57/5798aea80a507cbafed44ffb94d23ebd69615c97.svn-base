package com.huawei.solarsafe.bean.user.login;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.net.JSONReader;

import org.json.JSONObject;

/**
 * Created by p00507
 * on 2017/4/10.
 */

public class LogoAndTitle extends BaseEntity {
    private String logo;
    private String title;
    private String logoUpdataTime;
    private String filingNo;
    private String installerRegister;
    private int buildCode =-1;
    //统一定义的返回码
    ServerRet mRetCode;
    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if(!jsonObject.has(KEY_DATA)){
            throw new Exception();
        }
        JSONObject dataJson = jsonObject.getJSONObject(KEY_DATA);
        JSONReader jsonReader = new JSONReader(dataJson);
        logo=jsonReader.getString("logo");
        title=jsonReader.getString("title");
        logoUpdataTime = jsonReader.getString("logoUpdataTime");
        filingNo = jsonReader.getString("filingNo");
        installerRegister = jsonReader.getString("installerRegister");
        if(jsonObject.has("buildCode")){
            String buildCodeStr = (String) jsonObject.get("buildCode");
            this.buildCode = Integer.valueOf(buildCodeStr);
        }
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng


    @Override
    public void setServerRet(ServerRet serverRet) {
        mRetCode = serverRet;
    }
    public String getTitle() {
        return title;
    }

    public void setTitleLogo(String title) {
        this.title = title;
    }
    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogoUpdataTime() {
        return logoUpdataTime;
    }

    public void setLogoUpdataTime(String logoUpdataTime) {
        this.logoUpdataTime = logoUpdataTime;
    }

    public String getFilingNo() {
        return filingNo;
    }

    public void setFilingNo(String filingNo) {
        this.filingNo = filingNo;
    }

    public String getInstallerRegister() {
        return installerRegister;
    }

    public void setInstallerRegister(String installerRegister) {
        this.installerRegister = installerRegister;
    }

    @Override
    public String toString() {
        return "LogoAndTitle{" +
                "logo='" + logo + '\'' +
                ", title='" + title + '\'' +
                ", installerRegister='" + installerRegister + '\'' +
                ", mRetCode=" + mRetCode +
                '}';
    }

    public int getBuildCode() {
        return buildCode;
    }
}
