package com.huawei.solarsafe.bean.report;

import com.google.gson.Gson;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by p00322 on 2017/2/14.
 * description: 电站报表数据列表获取接口解析
 */
public class StationReportKpiList extends BaseEntity {
    //数据列表(JSONARRAY)
    private static final String KEY_LIST = "list";
    //电站对象(JSONOBJECT)
    private static final String KEY_STATION = "station";
    //Kpi数据对象(JSONOBJECT)
    private static final String KEY_KPI_MODEL = "kpiModel";
    //id,主键
    private static final String KEY_ID = "id";
    //总条数
    int total;
    int pageNo;
    int pageSize;
    boolean hasMeter;

    //扶贫对象列表
    List<StationReportKipInfos> stationReportKpiInfoList;
    //统一定义的返回码
    ServerRet mRetCode;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        Gson gson = new Gson();
        if (jsonObject == null) {
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        stationReportKpiInfoList = new ArrayList<>();
        total = jsonReader.getInt("total");
        pageNo = jsonReader.getInt("pageNo");
        pageSize = jsonReader.getInt("pageSize");
        hasMeter = jsonReader.getBoolean("hasMeter");
        JSONArray jsonArray = jsonReader.getJSONArray(KEY_LIST);
        for (int i = 0; i < jsonArray.length(); i++) {
            StationReportKipInfos stationReportKpiInfos = new StationReportKipInfos();
            StationReportKpiInfo stationReportKpiInfo ;
            StationReportModel stationReportModel;
            JSONReader jsonReader1 = new JSONReader(jsonArray.getJSONObject(i));
            stationReportKpiInfos.setId(jsonReader1.getString(KEY_ID));

            stationReportKpiInfo = gson.fromJson(jsonReader1.getJSONObject(KEY_STATION).toString(),StationReportKpiInfo.class);
            stationReportModel = gson.fromJson(jsonReader1.getJSONObject(KEY_KPI_MODEL).toString(),StationReportModel.class);

            stationReportKpiInfos.setStation(stationReportKpiInfo);
            stationReportKpiInfos.setKpiModel(stationReportModel);
            stationReportKpiInfoList.add(stationReportKpiInfos);
        }
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        mRetCode = serverRet;
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

    public boolean isHasMeter() {
        return hasMeter;
    }


    public List<StationReportKipInfos> getStationReportKpiInfoList() {
        return stationReportKpiInfoList;
    }

    public ServerRet getmRetCode() {
        return mRetCode;
    }
}
