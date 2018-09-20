package com.huawei.solarsafe.bean.device;

import android.util.Log;

import com.google.gson.Gson;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by P00517 on 2017/8/16.
 */

public class YhqErrorListBean extends BaseEntity {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if(jsonObject == null){
            return false;
        }
        Gson gson = new Gson();
        if("{}".equals(jsonObject.toString())){
            return false;
        }
        try {
            data = gson.fromJson(jsonObject.toString(), DataBean.class);
        }catch (Exception e){
            Log.e("YhqErrorListBean",e.toString());
        }
        return true;
    }

    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    public static class DataBean {

        private int pageCount;
        private int pageNo;
        private int pageSize;
        private int total;
        private List<ListBean> list;

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
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

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {

            private String faultName;
            private long raisedDate;
            private String stationName;
            private String optName;
            private int status;


            public String getFaultName() {
                return faultName;
            }

            public long getRaisedDate() {
                return raisedDate;
            }


            public String getStationName() {
                return stationName;
            }

            public void setStationName(String stationName) {
                this.stationName = stationName;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getOptName() {
                return optName;
            }

        }
    }

}
