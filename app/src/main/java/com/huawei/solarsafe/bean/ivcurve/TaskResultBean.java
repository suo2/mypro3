package com.huawei.solarsafe.bean.ivcurve;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.net.JSONReader;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by P00517 on 2017/7/24.
 */

public class TaskResultBean extends BaseEntity{
    //统一定义的返回码
    ServerRet mRetCode;
    private List<TaskResultInfo> list;
    private int total;
    private int pageNo;
    private int pageSize;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if(jsonObject == null){
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        total = jsonReader.getInt("total");
        pageNo = jsonReader.getInt("pageNo");
        pageSize = jsonReader.getInt("pageSize");
        String temp = jsonReader.getString("list");
        if(!"N/A".equals(temp)){
            Gson gson = new Gson();
            Type type = new TypeToken<List<TaskResultInfo>>(){}.getType();
            list = gson.fromJson(temp,type);
        }
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        this.mRetCode = serverRet;

    }

    public List<TaskResultInfo> getList() {
        return list;
    }

    public void setList(List<TaskResultInfo> list) {
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

}
