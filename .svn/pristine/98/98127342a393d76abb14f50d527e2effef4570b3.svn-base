package com.huawei.solarsafe.bean.ivcurve;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by P00517 on 2017/7/21.
 */

public class FaultListBean extends BaseEntity{
    //统一定义的返回码
    ServerRet mRetCode;
    private int total;
    private int pageNo;
    private int pageSize;
    private int pageCount;
    private List<FaultListInfo> list;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if(jsonObject == null){
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        total = jsonReader.getInt("total");
        pageNo = jsonReader.getInt("pageNo");
        pageCount = jsonReader.getInt("pageCount");
        pageSize = jsonReader.getInt("pageSize");
        String temp = jsonReader.getString("list");
        if(!"N/A".equals(temp)){//为了防止返回回来的数据data = null，而出现解析出错
            Gson gson = new Gson();
            Type type = new TypeToken<List<FaultListInfo>>(){}.getType();
            list = gson.fromJson(temp,type);
        }
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufengyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        this.mRetCode = serverRet;
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

    public List<FaultListInfo> getList() {
        return list;
    }

    public void setList(List<FaultListInfo> list) {
        this.list = list;
    }
}
