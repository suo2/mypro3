package com.huawei.solarsafe.bean.report;

import com.google.gson.Gson;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONObject;

/**
 * Created by p00322 on 2017/2/14.
 * description: 电站报表数据汇总列表获取接口解析类
 */
public class StationKpiChartList extends BaseEntity {
    boolean hasMeter;
    ServerRet mRetCode;
    KpiCharList kpiChartList;


    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        hasMeter = jsonReader.getBoolean("hasMeter");
        JSONObject jsonObject1 = jsonReader.getJSONObject("data");
        String s = jsonObject1.toString();
        Gson gson = new Gson();
        kpiChartList = gson.fromJson(s,KpiCharList.class);
        return true;
    }

    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    public boolean isHasMeter() {
        return hasMeter;
    }

    public ServerRet getmRetCode() {
        return mRetCode;
    }

    public void setmRetCode(ServerRet mRetCode) {
        this.mRetCode = mRetCode;
    }

    public KpiCharList getKpiChartList() {
        return kpiChartList;
    }

}
