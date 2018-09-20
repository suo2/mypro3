package com.huawei.solarsafe.bean.device;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.net.JSONReader;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by p00507
 * on 2017/6/21.
 */
public class PinnetDevListStatus extends BaseEntity {
    private ServerRet serverRet;
    private List<PinnetDevStatus> list;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        JSONReader jsonReader = new JSONReader(jsonObject);
        String temp = jsonReader.getString("list");
        Gson gson = new Gson();
        Type type = new TypeToken<List<PinnetDevStatus>>() {
        }.getType();
        list = gson.fromJson(temp, type);
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        this.serverRet = serverRet;
    }

    public ServerRet getServerRet() {
        return serverRet;
    }

    public List<PinnetDevStatus> getList() {
        return list;
    }

    public void setList(List<PinnetDevStatus> list) {
        this.list = list;
    }

    public class PinnetDevStatus {
        private String devId;
        private String devRuningStatus;

        public String getDevId() {
            return devId;
        }

        public void setDevId(String devId) {
            this.devId = devId;
        }

        public String getDevRuningStatus() {
            return devRuningStatus;
        }


        @Override
        public String toString() {
            return "PinnetDevStatus{" +
                    "devId='" + devId + '\'' +
                    ", devRuningStatus='" + devRuningStatus + '\'' +
                    '}';
        }
    }
}
