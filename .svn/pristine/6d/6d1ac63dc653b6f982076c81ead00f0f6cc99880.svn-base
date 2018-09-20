package com.huawei.solarsafe.bean.patrol;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Create Date: 2017/3/2
 * Create Author: p00213
 * Description :
 */
public class PatrolInspectObjList extends BaseEntity{
    private int pageNo;
    private int pageCount;
    private List<PatrolObj> list;

    public List<PatrolObj> getList() {
        return list;
    }

    public void setList(List<PatrolObj> list) {
        this.list = list;
    }

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        list = new ArrayList<>();
        JSONReader jsonReader = new JSONReader(jsonObject);
        JSONArray jsonArray = jsonReader.getJSONArray("list");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONReader jsonReader1 = new JSONReader(jsonArray.getJSONObject(i));
            PatrolObj patrolObj = new PatrolObj();
            patrolObj.setAnnexObjName(jsonReader1.getString("objName"));
            patrolObj.setLastInspectDay(jsonReader1.getInt("lastInspectDay"));
            patrolObj.setAnnexStatus(jsonReader1.getInt("inspectStatus"));
            patrolObj.setsId(jsonReader1.getString("sId"));
            patrolObj.setInspectCount(jsonReader1.getInt("inspectCount"));
            patrolObj.setLastInspectPerson(jsonReader1.getString("lastInspectPerson"));
            patrolObj.setLastAnnexTime(jsonReader1.getLong("lastInspectTime"));
            patrolObj.setLastInspectResult(jsonReader1.getInt("lastInspectResult"));
            patrolObj.setsName(jsonReader1.getString("sName"));
            list.add(patrolObj);
        }
        pageNo = jsonReader.getInt("pageNo");
        pageCount = jsonReader.getInt("pageCount");
        return true;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {

    }
}
