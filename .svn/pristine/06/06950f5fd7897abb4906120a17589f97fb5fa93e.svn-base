package com.huawei.solarsafe.bean.station.kpi;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONObject;

/**
 * Created by p00322 on 2017/2/14.
 * description: 社会贡献Kpi信息获取接口解析
 */
public class SocialContributionInfo extends BaseEntity {
    //节约用煤
    private static final String KEY_COAL = "coal";
    //二氧化碳减排量
    private static final String KEY_CO2 = "CO2";
    //减少森林砍伐
    private static final String KEY_FOREST = "forest";
    //社会贡献Map
    private static final String KEY_SOCIAL_CONTRIBUTION = "SocialContribution";

    //节约用煤
    double coal;
    //二氧化碳减排量
    double co2;
    //减少森林砍伐
    double forest;
    //统一定义的返回码
    ServerRet mRetCode;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        JSONReader jsonReader1 = new JSONReader(jsonReader.getJSONObject(KEY_SOCIAL_CONTRIBUTION));
        coal = jsonReader1.getDouble(KEY_COAL);
        co2 = jsonReader1.getDouble(KEY_CO2);
        forest = jsonReader1.getDouble(KEY_FOREST);
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufengyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        mRetCode = serverRet;
    }

    public double getCoal() {
        return coal;
    }

    public double getCo2() {
        return co2;
    }

    public double getForest() {
        return forest;
    }

    public ServerRet getRetCode() {
        return mRetCode;
    }

    @Override
    public String toString() {
        return "SocialContributionInfo{" +
                "coal=" + coal +
                ", co2=" + co2 +
                ", forest=" + forest +
                ", mRetCode=" + mRetCode +
                '}';
    }
}
