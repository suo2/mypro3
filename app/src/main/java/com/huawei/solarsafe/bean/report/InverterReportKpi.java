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
 * Created by p00507
 * on 2017/5/26.
 */
public class InverterReportKpi extends BaseEntity{
    //数据列表(JSONARRAY)
    private static final String KEY_LIST = "list";
    //id,主键
    private static final String KEY_ID = "id";
    private static final String KEY_HASMETER = "hasMeter";
    //电站对象(JSONOBJECT)
    private static final String KEY_STATION = "station";
    //Kpi数据对象(JSONOBJECT)
    private static final String KEY_KPI_MODEL = "kpiModel";
    //总条数
    private int total;
    private int pageNo;
    private int pageSize;
    private int pageCount;
    private List<InverterReportKpiList> inverterReportKpiLists;
    //统一定义的返回码
    ServerRet mRetCode;

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

    public List<InverterReportKpiList> getInverterReportKpiLists() {
        return inverterReportKpiLists;
    }


    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        Gson gson = new Gson();
        if (jsonObject == null) {
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        inverterReportKpiLists = new ArrayList<>();
        total = jsonReader.getInt("total");
        pageNo = jsonReader.getInt("pageNo");
        pageSize = jsonReader.getInt("pageSize");
        pageCount = jsonReader.getInt("pageCount");
        JSONArray jsonArray =  jsonReader.getJSONArray(KEY_LIST);
        for (int i = 0; i < jsonArray.length(); i++) {
            InverterReportKpiList inverterReportKpiList = new InverterReportKpiList();
            InverterReportKpiInfo stationReportKpiInfo;
            InverterModel inverterModel ;
            JSONReader jsonReader1 = new JSONReader(jsonArray.getJSONObject(i));
            inverterReportKpiList.setId(jsonReader1.getString(KEY_ID));
            inverterReportKpiList.setHasMeter(jsonReader1.getBoolean(KEY_HASMETER));

            stationReportKpiInfo = gson.fromJson(jsonReader1.getJSONObject(KEY_STATION).toString(),InverterReportKpiInfo.class);
            inverterModel = gson.fromJson(jsonReader1.getJSONObject(KEY_KPI_MODEL).toString(),InverterModel.class);

            inverterReportKpiList.setInverterReportKpiInfo(stationReportKpiInfo);
            inverterReportKpiList.setInverterModel(inverterModel);
            inverterReportKpiLists.add(inverterReportKpiList);
        }

        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        mRetCode = serverRet;
    }
}
