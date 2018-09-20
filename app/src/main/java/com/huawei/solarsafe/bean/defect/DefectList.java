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
 * Created by p00319 on 2017/2/16.
 */

public class DefectList extends BaseEntity {

    /**
     * 缺陷总条数
     */
    private int total;
    /**
     * 缺陷列表
     */
    private List<DefectDetail> dfList;

    private String data;

    private Gson gson = new Gson();

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        JSONReader jsonReader = new JSONReader(jsonObject);
        total = jsonReader.getInt("total");
        String temp = jsonReader.getString("list");
        Type type = new TypeToken<List<DefectDetail>>(){}.getType();
        dfList = gson.fromJson(temp, type);
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    public List<DefectDetail> getDfList() {
        return dfList;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
