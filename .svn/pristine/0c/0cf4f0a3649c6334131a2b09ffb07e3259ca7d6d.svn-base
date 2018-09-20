package com.huawei.solarsafe.bean.station;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONObject;

/**
 * Created by p00322 on 2017/2/13.
 * description: 电站首页扶贫完成情况接口解析类
 */
public class PoorCompleteInfo extends BaseEntity {
    //扶贫镇
    private static final String KEY_TOWN = "town";
    //扶贫村
    private static final String KEY_VILLAGE = "village";
    //扶贫户
    private static final String KEY_HOUSEHOLD = "household";
    //扶贫数量Map
    private static final String KEY_PAGENERAL = "PAGeneral";
    //完成
    private static final String KEY_COMPLETED = "completed";
    //未完成
    private static final String KEY_UNCOMPLETED = "uncompleted";
    //扶贫容量Map
    private static final String KEY_PACOMP = "PAComp";
    //扶贫完成时间
    private static final String KEY_UPDATEDATE = "updateDate";
    //扶贫镇
    int town;
    //扶贫村
    int village;
    //扶贫户
    int household;
    //完成
    int completed;
    //未完成
    int uncompleted;
    //扶贫完成时间
    long updateDate;
    //统一定义的返回码
    ServerRet mRetCode;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        JSONReader jsonReader1 = new JSONReader(jsonReader.getJSONObject(KEY_PAGENERAL));
        town = jsonReader1.getInt(KEY_TOWN);
        village = jsonReader1.getInt(KEY_VILLAGE);
        household = jsonReader1.getInt(KEY_HOUSEHOLD);
        JSONReader jsonReader2 = new JSONReader(jsonReader.getJSONObject(KEY_PACOMP));
        completed = jsonReader2.getInt(KEY_COMPLETED);
        uncompleted = jsonReader2.getInt(KEY_UNCOMPLETED);
        updateDate = jsonReader.getLong(KEY_UPDATEDATE);
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufengg

    @Override
    public void setServerRet(ServerRet serverRet) {
        mRetCode = serverRet;
    }

    public int getTown() {
        return town;
    }

    public int getHousehold() {
        return household;
    }

    public int getVillage() {
        return village;
    }

    public int getCompleted() {
        return completed;
    }

    public int getUncompleted() {
        return uncompleted;
    }

    public ServerRet getRetCode() {
        return mRetCode;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public ServerRet getmRetCode() {
        return mRetCode;
    }

    @Override
    public String toString() {
        return "PoorCompleteInfo{" +
                "town=" + town +
                ", village=" + village +
                ", household=" + household +
                ", completed=" + completed +
                ", uncompleted=" + uncompleted +
                ", updateDate=" + updateDate +
                ", mRetCode=" + mRetCode +
                '}';
    }
}
