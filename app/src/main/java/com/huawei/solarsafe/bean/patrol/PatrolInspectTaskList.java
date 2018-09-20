package com.huawei.solarsafe.bean.patrol;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by p00322 on 2017/2/22.
 * description: 查询任务信息列表接口解析
 */
public class PatrolInspectTaskList extends BaseEntity {


    private int offset;
    private int pageSize;
    private int total;
    //统一定义的返回码
    ServerRet mRetCode;
    private List<PatrolInspectTaskBean> list;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        list = new ArrayList<>();
        JSONReader jsonReader = new JSONReader(jsonObject);
        JSONArray jsonArray = jsonReader.getJSONArray("userObjects");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONReader jsonReader1 = new JSONReader(jsonArray.getJSONObject(i));
            JSONObject jsonObject1 = jsonReader1.getJSONObject("inspectTask");
            JSONObject jsonObject2 = jsonReader1.getJSONObject("currentUser");

            JSONReader jr1 = new JSONReader(jsonObject1);
            JSONReader jr2 = new JSONReader(jsonObject2);
            PatrolInspectTaskBean bean = new PatrolInspectTaskBean();
            bean.setCurrentAssignee(jr1.getString("currentAssignee"));
            bean.setBusiState(jr1.getString("busiState"));
            bean.setStartTime(jr1.getLong("startTime"));
            bean.setEndTime(jr1.getLong("endTime"));
            bean.setProcName(jr1.getString("procName"));
            bean.setExeptionCount(jsonReader1.getInt("exeptionCount"));
            bean.setProcState(jr1.getString("procState"));
            bean.setRemark(jr1.getString("remark"));
            bean.setProcDesc(jr1.getString("procDesc"));
            bean.setProcId(jr1.getString("procId"));
            bean.setSId(jr1.getString("sId"));
            bean.setId(jr1.getLong("id"));
            bean.setProcId(jr1.getString("procId"));
            bean.setAssigner(jr2.getString("userName"));
            bean.setCurrentTaskId("currentTaskId");
            list.add(bean);
        }

        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        mRetCode = serverRet;
    }

    public ServerRet getRetCode() {
        return mRetCode;
    }

    public List<PatrolInspectTaskBean> getList() {
        return list;
    }

    public void setList(List<PatrolInspectTaskBean> list) {
        this.list = list;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
