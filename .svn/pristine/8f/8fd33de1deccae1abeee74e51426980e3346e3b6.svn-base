package com.huawei.solarsafe.bean.device;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 4.9设备离散率展示
 * Created by P00229 on 2017/2/21.
 */
public class DispersionListInfo extends BaseEntity {
    public static final String TAG = "DispersionListInfo";
    public static final String KEY_STATIONANME = "stationName";
    public static final String KEY_STATIONCODE = "stationCode";
    public static final String KEY_DEVICENAME = "deviceName";
    public static final String KEY_DISPERSION = "dispersion";
    public static final String KEY_PV1 = "pv1";
    public static final String KEY_PV2 = "pv2";
    public static final String KEY_PV3 = "pv3";
    public static final String KEY_PV4 = "pv4";
    public static final String KEY_PV5 = "pv5";
    public static final String KEY_PV6 = "pv6";
    public static final String KEY_PV7 = "pv7";
    public static final String KEY_PV8 = "pv8";
    public static final String KEY_PV9 = "pv9";
    public static final String KEY_PV10 = "pv10";
    public static final String KEY_PV11 = "pv11";
    public static final String KEY_PV12 = "pv12";
    public static final String KEY_PV13 = "pv13";
    public static final String KEY_PV14 = "pv14";
    public static final String KEY_PV15 = "pv15";
    public static final String KEY_PV16 = "pv16";
    public static final String KEY_PV17 = "pv17";
    public static final String KEY_PV18 = "pv18";
    public static final String KEY_PV19 = "pv19";
    public static final String KEY_PV20= "pv20";
    public static final String KEY_PHOTCU= "avgPhotcU";
    public static final String KEY_LIST = "list";

    private List<EquipmentDispersionInfo> dispersionInfos;
    private int total;//总条数
    private int pageNo;//当前页
    private int pageSize;//每页显示的条数
    private int pageCount;//总页数
    private int devTypeId;//类型
    private ServerRet serverRet;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        dispersionInfos = new ArrayList<>();
        JSONReader jsonReader = new JSONReader(jsonObject);
        total = jsonReader.getInt("total");
        pageNo = jsonReader.getInt("pageNo");
        pageSize = jsonReader.getInt("pageSize");
        pageCount = jsonReader.getInt("pageCount");
        JSONArray jsonArray = jsonReader.getJSONArray(KEY_LIST);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
            EquipmentDispersionInfo dispersionInfo = new EquipmentDispersionInfo();
            dispersionInfo.setStationName(jsonObject1.getString(KEY_STATIONANME));
            dispersionInfo.setStationCode(jsonObject1.getString(KEY_STATIONCODE));
            dispersionInfo.setDispersion(jsonObject1.getString(KEY_DISPERSION));
            dispersionInfo.setDeviceName(jsonObject1.getString(KEY_DEVICENAME));
            dispersionInfo.setPv1(jsonObject1.getString(KEY_PV1));
            dispersionInfo.setPv2(jsonObject1.getString(KEY_PV2));
            dispersionInfo.setPv3(jsonObject1.getString(KEY_PV3));
            dispersionInfo.setPv4(jsonObject1.getString(KEY_PV4));
            dispersionInfo.setPv5(jsonObject1.getString(KEY_PV5));
            dispersionInfo.setPv6(jsonObject1.getString(KEY_PV6));
            dispersionInfo.setPv7(jsonObject1.getString(KEY_PV7));
            dispersionInfo.setPv8(jsonObject1.getString(KEY_PV8));
            dispersionInfo.setPv9(jsonObject1.getString(KEY_PV9));
            dispersionInfo.setPv10(jsonObject1.getString(KEY_PV10));
            dispersionInfo.setPv11(jsonObject1.getString(KEY_PV11));
            dispersionInfo.setPv12(jsonObject1.getString(KEY_PV12));
            dispersionInfo.setPv13(jsonObject1.getString(KEY_PV13));
            dispersionInfo.setPv14(jsonObject1.getString(KEY_PV14));
            dispersionInfo.setPv15(jsonObject1.getString(KEY_PV15));
            dispersionInfo.setPv16(jsonObject1.getString(KEY_PV16));
            dispersionInfo.setPv17(jsonObject1.getString(KEY_PV17));
            dispersionInfo.setPv18(jsonObject1.getString(KEY_PV18));
            dispersionInfo.setPv19(jsonObject1.getString(KEY_PV19));
            dispersionInfo.setPv20(jsonObject1.getString(KEY_PV20));
            dispersionInfo.setAvgPhotcU(jsonObject1.getString(KEY_PHOTCU));
            dispersionInfos.add(dispersionInfo);

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

    public List<EquipmentDispersionInfo> getDispersionInfos() {
        return dispersionInfos;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getDevTypeId() {
        return devTypeId;
    }

    public void setDevTypeId(int devTypeId) {
        this.devTypeId = devTypeId;
    }
}
