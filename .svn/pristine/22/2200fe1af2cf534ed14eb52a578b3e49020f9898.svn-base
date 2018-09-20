package com.huawei.solarsafe.bean.defect;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.net.JSONReader;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by p00319 on 2017/2/22.
 */

public class WorkFlowList extends BaseEntity {

    private List<WorkFlowBean> tasks;

    private Gson gson = new Gson();

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {

        JSONReader jsonReader = new JSONReader(jsonObject);
        JSONReader nameReader = new JSONReader(jsonReader.getJSONObject("names"));
        String temp = jsonReader.getString("tasks");
        Type type2 = new TypeToken<List<WorkFlowBean>>(){}.getType();
        tasks = gson.fromJson(temp, type2);
        if(tasks.size() > 0 ) {
            for (WorkFlowBean bean : tasks)
            {
                bean.setAssigneeName(nameReader.getString(bean.getAssignee()));
                bean.setRecipientName(nameReader.getString(bean.getRecipient()));
                if (bean.getWfhts()!=null && bean.getWfhts().size()>0){
                    for (WorkFlowBean.DefectDisposeCituationBean ddcb : bean.getWfhts()){
                        ddcb.setOperatorName(nameReader.getString(ddcb.getOperator()));
                    }
                }
            }
        }
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    public List<WorkFlowBean> getTasks() {
        return tasks;
    }
}
