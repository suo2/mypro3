package com.huawei.solarsafe.bean.station.kpi;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONObject;

/**
 * Created by p00322 on 2017/2/13
 * description: 电站首页实时Kpi获取接口解析类
 */
public class StationRealKpiInfo extends BaseEntity {
    //当前功率
    private static final String KEY_CURRENT_POWER = "curPower";
    //日发电量
    private static final String KEY_DAILY_CAPACITY = "dailyCapacity";
    //总发电量
    private static final String KEY_TOTAL_CAPACITY = "allCapacity";
    //当日收益
    private static final String KEY_CURRENT_INCOME = "curIncome";
    //实际总装机容量
    private static final String KEY_TOTAL_INSTALLED_CAPACITY = "totalInstalledCapacity";
    //新增字段 自发自用率
    private static final String KEY_TOTAL_OPINIONATED = "opinionated";
    private static final String KEY_TOTAL_INCOME = "totalIncome";

    private static final String KEY_DAY_USE = "dayUse";
    private static final String KEY_DAY_ECONOMIZE_COST  = "dayEconomizeCost";
    private static final String KEY_DAY_SELF_USER_CAP ="dayselfUserCap";

    private static final String KEY_HAS_METER="hasMeter";
    //当前功率
    double curPower;
    //日发电量
    double dailyCap;
    //总发电量
    double totalCap;
    //当日收益
    double curIncome;
    double totalIncome;
    //实际总装机容量
    double totalInstalledCapacity;
    //统一定义的返回码
    ServerRet mRetCode;
    //新增字段 自发自用率
    String opinionated;
    //日用电量
    private double dayUse;
    //日节省电费
    private double dayEconomizeCost;
    private double dayselfUserCap;
    private boolean hasMeter =false;
    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        curPower = jsonReader.getDouble(KEY_CURRENT_POWER);
        dailyCap = jsonReader.getDouble(KEY_DAILY_CAPACITY);
        totalCap = jsonReader.getDouble(KEY_TOTAL_CAPACITY);
        curIncome = jsonReader.getDouble(KEY_CURRENT_INCOME);
        totalInstalledCapacity = jsonReader.getDouble(KEY_TOTAL_INSTALLED_CAPACITY);
        totalIncome = jsonReader.getDouble(KEY_TOTAL_INCOME);
        dayUse = jsonReader.getDouble(KEY_DAY_USE);
        dayEconomizeCost = jsonReader.getDouble(KEY_DAY_ECONOMIZE_COST);
        dayselfUserCap = jsonReader.getDouble(KEY_DAY_SELF_USER_CAP);
        hasMeter = jsonReader.getBoolean(KEY_HAS_METER);
        if (jsonObject.toString().contains("opinionated")) {
            opinionated = jsonReader.getString(KEY_TOTAL_OPINIONATED);
        }
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufengfeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        mRetCode = serverRet;
    }

    public double getCurPower() {
        return curPower;
    }

    public double getDailyCap() {
        return dailyCap;
    }

    public double getTotalCap() {
        return totalCap;
    }

    public double getCurIncome() {
        return curIncome;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public ServerRet getRetCode() {
        return mRetCode;
    }

    public double getTotalInstalledCapacity() {
        return totalInstalledCapacity;
    }

    public String getOpinionated() {
        return opinionated;
    }

    @Override
    public String toString() {
        return "StationRealKpiInfo{" +
                "curPower=" + curPower +
                ", dailyCap=" + dailyCap +
                ", totalCap=" + totalCap +
                ", curIncome=" + curIncome +
                ", totalIncome=" + totalIncome +
                ", mRetCode=" + mRetCode +
                '}';
    }

    public double getDayUse() {
        return dayUse;
    }

    public double getDayEconomizeCost() {
        return dayEconomizeCost;
    }

    public double getDayselfUserCap() {
        return dayselfUserCap;
    }

    public void setDayselfUserCap(double dayselfUserCap) {
        this.dayselfUserCap = dayselfUserCap;
    }

    public boolean isHasMeter() {
        return hasMeter;
    }

}
