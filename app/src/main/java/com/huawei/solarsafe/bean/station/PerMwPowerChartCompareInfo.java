package com.huawei.solarsafe.bean.station;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by P00229 on 2017/2/21.
 */
public class PerMwPowerChartCompareInfo extends BaseEntity {
    public static final String TAG = "PerMwPowerChartCompareInfo";
    public static final String KEY_LIST = "list";
    public static final String KEY_STATIONCODE = "stationCode";
    public static final String KEY_YDATA = "yData";
    private List<PerMwPowerChartInfo> perMwPowerChartInfos;
    private ServerRet serverRet;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        perMwPowerChartInfos = new ArrayList<>();
        JSONArray jsonArray = jsonReader.getJSONArray(KEY_LIST);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            JSONReader jr2 = new JSONReader(jsonObject1);
            PerMwPowerChartInfo per = new PerMwPowerChartInfo();
            per.setStationCode(jr2.getString(KEY_STATIONCODE));
            JSONArray jsonArray1 = jr2.getJSONArray(KEY_YDATA);
            String[] data = new String[jsonArray1.length()];
            for (int j = 0; j < jsonArray1.length(); j++) {
                data[j] = jsonArray1.getString(j);
            }
            per.setyData(data);
            perMwPowerChartInfos.add(per);
        }
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    public List<PerMwPowerChartInfo> getPerMwPowerChartInfos() {
        return perMwPowerChartInfos;
    }


    public ServerRet getServerRet() {
        return serverRet;
    }

    @Override
    public void setServerRet(ServerRet serverRet) {
        this.serverRet = serverRet;
    }

    public class PerMwPowerChartInfo {
        String stationCode;
        String[] yData;

        public String[] getyData() {
            return yData;
        }

        public void setyData(String[] yData) {
            this.yData = yData;
        }

        public String getStationCode() {
            return stationCode;
        }

        public void setStationCode(String stationCode) {
            this.stationCode = stationCode;
        }
    }
}
