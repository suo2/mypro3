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
public class PerPowerRatioChartCompareInfo extends BaseEntity {
    public static final String TAG = "PerMwPowerChartCompareInfo";
    public static final String KEY_STATIONCODE = "stationCode";
    public static final String KEY_YAXIS = "yAxis";
    public static final String KEY_XAXIS = "xAxis";
    public static final String KEY_DATA = "data";
    private ServerRet serverRet;
    private List<PerPowerRatioChartInfo> perPowerRatioChartInfos;
    private String[] xAxis;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        JSONArray jsonArray = jsonReader.getJSONArray(KEY_XAXIS);
        xAxis = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            xAxis[i] = jsonArray.getString(i);
        }
        JSONArray jsonArray1 = jsonReader.getJSONArray(KEY_YAXIS);
        perPowerRatioChartInfos = new ArrayList<>();
        for (int i = 0; i < jsonArray1.length(); i++) {
            JSONReader jr3 = new JSONReader(jsonArray1.getJSONObject(i));
            PerPowerRatioChartInfo info = new PerPowerRatioChartInfo();
            info.setStationCode(jr3.getString(KEY_STATIONCODE));
            JSONArray jsonArray2 = jr3.getJSONArray(KEY_DATA);
            String[] data = new String[jsonArray2.length()];
            for (int j = 0; j < jsonArray2.length(); j++) {
                data[j] = jsonArray2.getString(j);
            }
            info.setData(data);
            perPowerRatioChartInfos.add(info);
        }
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        this.serverRet = serverRet;
    }

    public ServerRet getServerRet() {
        return serverRet;
    }

    public List<PerPowerRatioChartInfo> getPerPowerRatioChartInfos() {
        return perPowerRatioChartInfos;
    }

    public String[] getxAxis() {
        return xAxis;
    }

    public class PerPowerRatioChartInfo {
        String stationCode;
        String[] data;

        public String getStationCode() {
            return stationCode;
        }

        public void setStationCode(String stationCode) {
            this.stationCode = stationCode;
        }

        public String[] getData() {
            return data;
        }

        public void setData(String[] data) {
            this.data = data;
        }
    }
}
