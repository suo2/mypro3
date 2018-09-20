package com.huawei.solarsafe.bean.patrol;

import android.util.Log;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Create Date: 2017/3/2
 * Create Author: p00213
 * Description :
 */
public class PatrolInspectHistList extends BaseEntity {
    List<PatrolInspectHistBean> list = new ArrayList<>();

    @Override
    public void preParse(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            setServerRet(ServerRet.ILLEGAL_STATE_EXCEPTION);
            return;
        }

        JSONReader jsonReader = new JSONReader(jsonObject);
        success1 = jsonReader.getBoolean(SUCCESS);
        try {
            parseJsonArray(jsonReader.getJSONArray(KEY_DATA));
        } catch (Exception e) {
            Log.e(TAG, "mapping doesn't exist or is not a JSONObject, pass original json to subclass.", e);
            parseJson(jsonObject);
        }
    }

    public boolean parseJsonArray(JSONArray jsonArray){
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONReader jsonReader1 = new JSONReader(jsonArray.getJSONObject(i));
                PatrolInspectHistBean bean = new PatrolInspectHistBean();
                bean.setInspectId(jsonReader1.getString("inspectId"));
                bean.setTaskId(jsonReader1.getString("taskId"));
                bean.setTaskExecutor(jsonReader1.getString("taskExecutor"));
                bean.setsId(jsonReader1.getString("sId"));
                bean.setObjId(jsonReader1.getString("objId"));
                bean.setObjName((jsonReader1.getString("objId")));
                bean.setsName(jsonReader1.getString("sName"));
                bean.setInspectStartTime(jsonReader1.getLong("inspectStartTime"));
                bean.setInspectEndTime(jsonReader1.getLong("completeTime"));
                bean.setInspectResult(jsonReader1.getInt("inspectResult"));
                bean.setRemark(jsonReader1.getString("remark"));
                bean.setNormalCount(jsonReader1.getInt("normalCount"));
                bean.setUncheckedCount(jsonReader1.getInt("uncheckedCount"));
                bean.setExceptionCount(jsonReader1.getInt("exceptionCount"));
                bean.setTotalCount(jsonReader1.getInt("totalCount"));

                list.add(bean);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        return true;
    }

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        return true;
    }

    public List<PatrolInspectHistBean> getList() {
        return list;
    }

    public void setList(List<PatrolInspectHistBean> list) {
        this.list = list;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {

    }
}
