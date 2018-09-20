package com.huawei.solarsafe.bean.alarm;

import com.google.gson.Gson;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by P00229 on 2017/3/1.
 */
public class RealTimeAlarmListInfo extends BaseEntity {

    private List<ListBean> list;
    private int total;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject == null) {
            return false;
        }
        Gson gson = new Gson();
        RealTimeAlarmListInfo realTimeAlarmListInfo = gson.fromJson(jsonObject.toString(), RealTimeAlarmListInfo.class);
        list = realTimeAlarmListInfo.getList();
        total = realTimeAlarmListInfo.getTotal();
        return true;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private int createDate;
        private String createUser;
        private int updateDate;
        private String updateUser;
        private long id;
        private long devId;
        private String stationId;
        private String stationName;
        private String busiCode;
        private int alarmId;
        private String alarmName;
        private String devName;
        private int devTypeId;
        private String devTypeName;
        private long happenTime;
        private long createTime;
        private long recoveredTime;
        private int alarmState;
        private String esnCode;
        private String modelVersionName;
        private String repairSuggestion;
        private int severityId;
        private int domainId;
        public boolean isShowCheck, isChecked;
        private String timeZone;
        private boolean mainCascaded;
        private String invType;
        private long  dtsSaving;//夏令时的偏差

        public long getDtsSaving() {
            return dtsSaving;
        }

        public void setDtsSaving(long dtsSaving) {
            this.dtsSaving = dtsSaving;
        }

        public boolean isShowCheck() {
            return isShowCheck;
        }

        public void setIsShowCheck(boolean isShowCheck) {
            this.isShowCheck = isShowCheck;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setIsChecked(boolean isChecked) {
            this.isChecked = isChecked;
        }

        public int getCreateDate() {
            return createDate;
        }

        public void setUpdateUser(String updateUser) {
            this.updateUser = updateUser;
        }

        public void setDevTypeName(String devTypeName) {
            this.devTypeName = devTypeName;
        }


        public long getRecoveredTime() {
            return recoveredTime;
        }

        public void setCreateDate(int createDate) {
            this.createDate = createDate;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public int getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(int updateDate) {
            this.updateDate = updateDate;
        }

        public Object getUpdateUser() {
            return updateUser;
        }


        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public long getDevId() {
            return devId;
        }

        public void setDevId(long devId) {
            this.devId = devId;
        }

        public String getStationId() {
            return stationId;
        }

        public void setStationId(String stationId) {
            this.stationId = stationId;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(String stationName) {
            this.stationName = stationName;
        }

        public String getBusiCode() {
            return busiCode;
        }

        public void setBusiCode(String busiCode) {
            this.busiCode = busiCode;
        }

        public int getAlarmId() {
            return alarmId;
        }

        public void setAlarmId(int alarmId) {
            this.alarmId = alarmId;
        }

        public String getAlarmName() {
            return alarmName;
        }

        public void setAlarmName(String alarmName) {
            this.alarmName = alarmName;
        }

        public String getDevName() {
            return devName;
        }

        public void setDevName(String devName) {
            this.devName = devName;
        }

        public int getDevTypeId() {
            return devTypeId;
        }

        public void setDevTypeId(int devTypeId) {
            this.devTypeId = devTypeId;
        }

        public String getDevTypeName() {
            return devTypeName;
        }

        public long getHappenTime() {
            return happenTime;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getAlarmState() {
            return alarmState;
        }

        public void setAlarmState(int alarmState) {
            this.alarmState = alarmState;
        }

        public String getEsnCode() {
            return esnCode;
        }

        public void setEsnCode(String esnCode) {
            this.esnCode = esnCode;
        }

        public String getModelVersionName() {
            return modelVersionName;
        }

        public String getRepairSuggestion() {
            return repairSuggestion;
        }

        public void setRepairSuggestion(String repairSuggestion) {
            this.repairSuggestion = repairSuggestion;
        }
        public int getSeverityId() {
            return severityId;
        }

        public int getDomainId() {
            return domainId;
        }

        public void setDomainId(int domainId) {
            this.domainId = domainId;
        }

        public String getTimeZone() {
            return timeZone;
        }

        public void setTimeZone(String timeZone) {
            this.timeZone = timeZone;
        }

        public boolean isMainCascaded() {
            return mainCascaded;
        }

        public String getInvType() {
            return invType;
        }

        public void setInvType(String invType) {
            this.invType = invType;
        }
    }
}
