package com.huawei.solarsafe.bean.stationmagagement;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * <pre>
 *     author: Tzy
 *     time  : $date$
 *     desc  : 域名/电站(带层级)集合实体类
 * </pre>
 */
public class PowerStationListBean extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 5363194192242722747L;
    private ArrayList<PowerStationBean> data;

    public ArrayList<PowerStationBean> getData() {
        return data;
    }

    public void setData(ArrayList<PowerStationBean> data) {
        this.data = data;
    }

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject==null){
            return false;
        }

        String dataStr=jsonObject.getString("data");
        Type listType=new TypeToken<ArrayList<PowerStationBean>>(){}.getType();
        data=new Gson().fromJson(dataStr,listType);

        return true;
    }

    @Override
    public void setServerRet(ServerRet serverRet) {

    }


    /**
     * <pre>
     *     author: Tzy
     *     time  : 2018/2/28
     *     desc  : 域名/电站(带层级)实体类
     * </pre>
     */
    public static class PowerStationBean implements Serializable{


        private static final long serialVersionUID = -5776209791925445529L;
        private String id;
        private String name;
        private String pid;
        private Object check;
        private Object text;
        private String level;
        private String sort;
        private String model;
        private Object unit;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public Object getCheck() {
            return check;
        }

        public void setCheck(Object check) {
            this.check = check;
        }

        public Object getText() {
            return text;
        }

        public void setText(Object text) {
            this.text = text;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }


        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public Object getUnit() {
            return unit;
        }

        public void setUnit(Object unit) {
            this.unit = unit;
        }

    }
}
