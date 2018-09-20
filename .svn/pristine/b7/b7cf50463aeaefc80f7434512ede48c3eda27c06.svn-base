package com.huawei.solarsafe.bean.station.kpi;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONObject;

/**
 * Created by P00229 on 2017/2/20.
 */
public class StationStatusAllInfo extends BaseEntity {
    public static final String KEY_HEALTH = "health";
    public static final String KEY_TROUBLE = "trouble";
    public static final String KEY_DISCONNECTED = "disconnected";
    /**
     * 电站状态总汇 健康个数
     */
    private int health;
    /**
     * 电站状态总汇 异常个数
     */
    private int trouble;
    /**
     * 电站状态总汇 断连个数
     */
    private int disconnected;
    /**
     * 统一返回头
     */
    private ServerRet serverRet;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        health = jsonReader.getInt(KEY_HEALTH);
        trouble = jsonReader.getInt(KEY_TROUBLE);
        disconnected = jsonReader.getInt(KEY_DISCONNECTED);
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        this.serverRet = serverRet;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getTrouble() {
        return trouble;
    }

    public int getDisconnected() {
        return disconnected;
    }

    public void setDisconnected(int disconnected) {
        this.disconnected = disconnected;
    }

    public ServerRet getServerRet() {
        return serverRet;
    }

    @Override
    public String toString() {
        return "StationStatusAllInfo{" +
                "health=" + health +
                ", trouble=" + trouble +
                ", disconnected=" + disconnected +
                ", serverRet=" + serverRet +
                '}';
    }
}
