package com.huawei.solarsafe.bean.defect;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by p00319 on 2017/3/23.
 */

public class CorrelateDevAlarmList extends BaseEntity {

    private List<CorrelateDevAlarmInfo> list;

    private int total;

    private int pageNo;

    private int pageSize;

    public List<CorrelateDevAlarmInfo> getList() {
        return list;
    }

    public void setList(List<CorrelateDevAlarmInfo> list) {
        this.list = list;
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

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        if ("null".equals(jsonObject.get("list")+"")){
            this.list = new ArrayList<>();
        }else {
            String listString = jsonObject.getJSONArray("list").toString();
            if (listString != null) {
                Type type = new TypeToken<List<CorrelateDevAlarmInfo>>() {
                }.getType();
                this.list = new Gson().fromJson(listString, type);
            }
        }
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {

    }
}
