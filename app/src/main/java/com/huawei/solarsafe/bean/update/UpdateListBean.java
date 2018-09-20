package com.huawei.solarsafe.bean.update;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by P00517 on 2017/4/10.
 */

public class UpdateListBean extends BaseEntity {
    //列表
    List<UpdateListInfo> updateBeenList;
    //总数
    int total;
    //当前页面
    int pageNo;
    int pageSize;
    int pageCount;
    //统一定义的返回码

    public List<UpdateListInfo> getUpdateBeenList() {
        return updateBeenList;
    }

    public void setUpdateBeenList(List<UpdateListInfo> updateBeenList) {
        this.updateBeenList = updateBeenList;
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

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        return false;
    }

    @Override
    public void setServerRet(ServerRet serverRet) {
    }
}
