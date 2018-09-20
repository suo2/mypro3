package com.huawei.solarsafe.bean.station.kpi;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.net.JSONReaderArrary;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Arrays;

/**
 * 4.15单站功率图数据（白添浩）
 * Created by P00229 on 2017/2/20.
 */
public class StationPowerCountInfo extends BaseEntity {
    public static final String KEY_XDATA = "xdata";
    public static final String KEY_YDATA = "ydata";
    private String[] xData;
    private double[] yData;
    ServerRet serverRet;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        JSONArray jsonArray = jsonReader.getJSONArray(KEY_XDATA);
        JSONReaderArrary jsonReaderArrary = new JSONReaderArrary(jsonArray);
        xData = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            xData[i] = jsonReaderArrary.getString(i);
        }
        JSONArray jsonArray1 = jsonReader.getJSONArray(KEY_YDATA);
        JSONReaderArrary jsonReaderArrary1 = new JSONReaderArrary(jsonArray1);
        yData = new double[jsonArray1.length()];
        for (int i = 0; i < jsonArray1.length(); i++) {
            yData[i] = jsonReaderArrary1.getDouble(i);
        }
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        this.serverRet = serverRet;
    }

    public double[] getyData() {
        return yData;
    }

    @Override
    public String toString() {
        return "StationPowerCountInfo{" +
                "xData=" + Arrays.toString(xData) +
                ", yData=" + Arrays.toString(yData) +
                ", serverRet=" + serverRet +
                '}';
    }

    public ServerRet getServerRet() {
        return serverRet;
    }
}
