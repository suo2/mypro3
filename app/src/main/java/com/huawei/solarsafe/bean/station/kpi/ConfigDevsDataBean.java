package com.huawei.solarsafe.bean.station.kpi;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * <pre>
 *     author: Tzy
 *     time  : $date$
 *     desc  : 电站视图设备实时信息状态实体类
 * </pre>
 */
public class ConfigDevsDataBean extends BaseEntity{


    /**
     * success : true
     * data : {"djjhy01":{"devEsn":"djjhy01","outputPower":-1,"connectStatus":"CONNECTED","cap":36.22,"runningState":0}}
     * params : null
     * message : null
     * buildCode : 2
     */

    private boolean success;
    private Object params;
    private Object message;
    private String buildCode;
    private HashMap<String,DataBean> data;

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getBuildCode() {
        return buildCode;
    }

    public void setBuildCode(String buildCode) {
        this.buildCode = buildCode;
    }

    public HashMap<String, DataBean> getData() {
        return data;
    }

    public void setData(HashMap<String, DataBean> data) {
        this.data = data;
    }

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject==null || jsonObject.toString().equals("{}")){
            return false;
        }

        data=new GsonBuilder().enableComplexMapKeySerialization().create().fromJson(jsonObject.toString(),new TypeToken<HashMap<String,DataBean>>(){}.getType());

        return true;
    }

    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    public class DataBean{

        /**
         * devEsn : djjhy01
         * outputPower : -1.0
         * connectStatus : CONNECTED
         * cap : 36.22
         * runningState : 0
         */

        private String devEsn;
        private double outputPower;
        private String connectStatus;
        private double cap;
        private int runningState;

        public String getDevEsn() {
            return devEsn;
        }

        public void setDevEsn(String devEsn) {
            this.devEsn = devEsn;
        }

        public double getOutputPower() {
            return outputPower;
        }

        public void setOutputPower(double outputPower) {
            this.outputPower = outputPower;
        }

        public String getConnectStatus() {
            return connectStatus;
        }

        public void setConnectStatus(String connectStatus) {
            this.connectStatus = connectStatus;
        }

        public double getCap() {
            return cap;
        }

        public void setCap(double cap) {
            this.cap = cap;
        }

        public int getRunningState() {
            return runningState;
        }

        public void setRunningState(int runningState) {
            this.runningState = runningState;
        }
    }

}
