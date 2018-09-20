package com.huawei.solarsafe.bean.device;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.net.JSONReaderArrary;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by P00229 on 2017/2/23.
 */
public class DevTypeListInfo extends BaseEntity {
    List<DevType> devTypes;
    ServerRet serverRet;
    public static final String KEY_NAME = "name";
    public static final String KEY_ID = "id";
    public static final String KEY_LIST = "list";

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        JSONReader jsonReader = new JSONReader(jsonObject);
        JSONArray jsonArray = jsonReader.getJSONArray(KEY_LIST);
        JSONReaderArrary jsonReaderArrary = new JSONReaderArrary(jsonArray);
        devTypes = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject1 = jsonReaderArrary.getJSONObject(i);
            JSONReader jsonReader1 = new JSONReader(jsonObject1);
            DevType devType = new DevType();
            devType.setId(jsonReader1.getInt(KEY_ID));
            devType.setName(jsonReader1.getString(KEY_NAME));
            devTypes.add(devType);
        }
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        this.serverRet = serverRet;
    }

    public List<DevType> getDevTypes() {
        return devTypes;
    }

    public ServerRet getServerRet() {
        return serverRet;
    }

    @Override
    public String toString() {
        return "DevTypeListInfo{" +
                "devTypes=" + devTypes +
                ", serverRet=" + serverRet +
                '}';
    }

    public static class DevType {
        int id;
        String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        @Override
        public String toString() {
            return "DevType{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
