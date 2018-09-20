package com.huawei.solarsafe.bean.station.kpi;

import com.google.gson.Gson;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

import java.util.List;

/**
 * <pre>
 *     author: Tzy
 *     time  : $date$
 *     desc  : 电站视图实体类
 * </pre>
 */
public class StationViewConfiguraBean extends BaseEntity {

    /**
     * success : true
     * data : [{"id":2,"stationCode":"AD18971142B140D9B224CB56229BDE12","name":"测试","schemaData":"sddfs","createTime":1531539771639}]
     * failCode : 0
     * params : null
     * message : null
     * buildCode : 2
     */
    private StationViewConfiguraBean stationViewConfiguraBean;
    private boolean success;
    private Object params;
    private String message;
    private int buildCode;
    private List<DataBean> data;

    public StationViewConfiguraBean getStationViewConfiguraBean() {
        return stationViewConfiguraBean;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getBuildCode() {
        return buildCode;
    }

    public void setBuildCode(int buildCode) {
        this.buildCode = buildCode;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        if (jsonObject==null || jsonObject.toString().equals("{}")){
            throw new Exception();
        }

        stationViewConfiguraBean=new Gson().fromJson(jsonObject.toString(),StationViewConfiguraBean.class);

        if (stationViewConfiguraBean.getData()!=null && stationViewConfiguraBean.getData().size()>0){
            String datas=stationViewConfiguraBean.getData().get(0).getSchemaData();
            //服务器返回的数据格式有问题,处理数据格式
            datas=datas.replace("twaverUtil.twaverNode","twaver.Node");
//            //java赋值给js时会将"\"识别成转义符号,所以做处理
//            datas=datas.replace("\\","\\\\");

            stationViewConfiguraBean.getData().get(0).setSchemaData(datas);
        }

        return true;
    }

    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    public static class DataBean {
        /**
         * id : 2
         * stationCode : AD18971142B140D9B224CB56229BDE12
         * name : 测试
         * schemaData : {"version":"5.8.4","platform":"html5","dataBox":{"class":"twaver.ElementBox","layers":[{"name":"Default","visible":true,"editable":true,"movable":true}]},"datas":[{"class":"twaverUtil.twaverNode","ref":0,"p":{"name":"组件","image":"assembly","location":{"x":157.5,"y":77},"angle":30},"c":{"nodetype":"assembly","assemblyBgColor":"#437DC6","assemblyColor":"#498ADD","assemblyOptColor":"#498ADD","dipangle":"45","directionangle":"30","modelVersion":"1","orientation":"东"}},{"class":"twaverUtil.twaverNode","ref":1,"p":{"name":"组件","image":"assembly","location":{"x":239.5,"y":77},"angle":30},"c":{"nodetype":"assembly","assemblyBgColor":"#437DC6","assemblyColor":"#498ADD","assemblyOptColor":"#498ADD","dipangle":"45","directionangle":"30"}},{"class":"twaverUtil.twaverNode","ref":2,"p":{"name":"组件","image":"assembly","location":{"x":321.5,"y":77},"angle":30},"c":{"nodetype":"assembly","assemblyBgColor":"#437DC6","assemblyColor":"#498ADD","assemblyOptColor":"#498ADD","dipangle":"45","directionangle":"30"}},{"class":"twaverUtil.twaverNode","ref":3,"p":{"name":"组件","image":"assembly","location":{"x":403.5,"y":77},"angle":30},"c":{"nodetype":"assembly","assemblyBgColor":"#437DC6","assemblyColor":"#498ADD","assemblyOptColor":"#498ADD","dipangle":"45","directionangle":"30"}},{"class":"twaverUtil.twaverNode","ref":4,"p":{"name":"组件","image":"assembly","location":{"x":157.5,"y":217},"angle":30},"c":{"nodetype":"assembly","assemblyBgColor":"#437DC6","assemblyColor":"#498ADD","assemblyOptColor":"#498ADD","dipangle":"45","directionangle":"30"}},{"class":"twaverUtil.twaverNode","ref":5,"p":{"name":"组件","image":"assembly","location":{"x":239.5,"y":217},"angle":30},"c":{"nodetype":"assembly","assemblyBgColor":"#437DC6","assemblyColor":"#498ADD","assemblyOptColor":"#498ADD","dipangle":"45","directionangle":"30"}},{"class":"twaverUtil.twaverNode","ref":6,"p":{"name":"组件","image":"assembly","location":{"x":321.5,"y":217},"angle":30},"c":{"nodetype":"assembly","assemblyBgColor":"#437DC6","assemblyColor":"#498ADD","assemblyOptColor":"#498ADD","dipangle":"45","directionangle":"30"}},{"class":"twaverUtil.twaverNode","ref":7,"p":{"name":"组件","image":"assembly","location":{"x":403.5,"y":217},"angle":30},"c":{"nodetype":"assembly","assemblyBgColor":"#437DC6","assemblyColor":"#498ADD","assemblyOptColor":"#498ADD","dipangle":"45","directionangle":"30"}},{"class":"twaverUtil.twaverNode","ref":8,"p":{"name":"组件","image":"assembly","location":{"x":157.5,"y":357},"angle":30},"c":{"nodetype":"assembly","assemblyBgColor":"#437DC6","assemblyColor":"#498ADD","assemblyOptColor":"#498ADD","dipangle":"45","directionangle":"30"}},{"class":"twaverUtil.twaverNode","ref":9,"p":{"name":"组件","image":"assembly","location":{"x":239.5,"y":357},"angle":30},"c":{"nodetype":"assembly","assemblyBgColor":"#437DC6","assemblyColor":"#498ADD","assemblyOptColor":"#498ADD","dipangle":"45","directionangle":"30"}},{"class":"twaverUtil.twaverNode","ref":10,"p":{"name":"组件","image":"assembly","location":{"x":321.5,"y":357},"angle":30},"c":{"nodetype":"assembly","assemblyBgColor":"#437DC6","assemblyColor":"#498ADD","assemblyOptColor":"#498ADD","dipangle":"45","directionangle":"30"}},{"class":"twaverUtil.twaverNode","ref":11,"p":{"name":"组件","image":"assembly","location":{"x":403.5,"y":357},"angle":30},"c":{"nodetype":"assembly","assemblyBgColor":"#437DC6","assemblyColor":"#498ADD","assemblyOptColor":"#498ADD","dipangle":"45","directionangle":"30"}},{"class":"twaverUtil.twaverNode","ref":12,"p":{"name":"组件","image":"assembly","location":{"x":157.5,"y":497},"angle":30},"c":{"nodetype":"assembly","assemblyBgColor":"#437DC6","assemblyColor":"#498ADD","assemblyOptColor":"#498ADD","dipangle":"45","directionangle":"30"}},{"class":"twaverUtil.twaverNode","ref":13,"p":{"name":"组件","image":"assembly","location":{"x":239.5,"y":497},"angle":30},"c":{"nodetype":"assembly","assemblyBgColor":"#437DC6","assemblyColor":"#498ADD","assemblyOptColor":"#498ADD","dipangle":"45","directionangle":"30"}},{"class":"twaverUtil.twaverNode","ref":14,"p":{"name":"组件","image":"assembly","location":{"x":321.5,"y":497},"angle":30},"c":{"nodetype":"assembly","assemblyBgColor":"#437DC6","assemblyColor":"#498ADD","assemblyOptColor":"#498ADD","dipangle":"45","directionangle":"30"}},{"class":"twaverUtil.twaverNode","ref":15,"p":{"name":"组件","image":"assembly","location":{"x":403.5,"y":497},"angle":30},"c":{"nodetype":"assembly","assemblyBgColor":"#437DC6","assemblyColor":"#498ADD","assemblyOptColor":"#498ADD","dipangle":"45","directionangle":"30"}},{"class":"twaverUtil.twaverNode","ref":16,"p":{"name":"组件","image":"assembly","location":{"x":157.5,"y":637},"angle":30},"c":{"nodetype":"assembly","assemblyBgColor":"#437DC6","assemblyColor":"#498ADD","assemblyOptColor":"#498ADD","dipangle":"45","directionangle":"30"}},{"class":"twaverUtil.twaverNode","ref":17,"p":{"name":"组件","image":"assembly","location":{"x":239.5,"y":637},"angle":30},"c":{"nodetype":"assembly","assemblyBgColor":"#437DC6","assemblyColor":"#498ADD","assemblyOptColor":"#498ADD","dipangle":"45","directionangle":"30"}},{"class":"twaverUtil.twaverNode","ref":18,"p":{"name":"组件","image":"assembly","location":{"x":321.5,"y":637},"angle":30},"c":{"nodetype":"assembly","assemblyBgColor":"#437DC6","assemblyColor":"#498ADD","assemblyOptColor":"#498ADD","dipangle":"45","directionangle":"30"}},{"class":"twaverUtil.twaverNode","ref":19,"p":{"name":"组件","image":"assembly","location":{"x":403.5,"y":637},"angle":30},"c":{"nodetype":"assembly","assemblyBgColor":"#437DC6","assemblyColor":"#498ADD","assemblyOptColor":"#498ADD","dipangle":"45","directionangle":"30"}},{"class":"twaverUtil.twaverNode","ref":20,"p":{"name":"组件","image":"assembly","location":{"x":157.5,"y":777},"angle":30},"c":{"nodetype":"assembly","assemblyBgColor":"#437DC6","assemblyColor":"#498ADD","assemblyOptColor":"#498ADD","dipangle":"45","directionangle":"30"}},{"class":"twaverUtil.twaverNode","ref":21,"p":{"name":"组件","image":"assembly","location":{"x":239.5,"y":777},"angle":30},"c":{"nodetype":"assembly","assemblyBgColor":"#437DC6","assemblyColor":"#498ADD","assemblyOptColor":"#498ADD","dipangle":"45","directionangle":"30"}},{"class":"twaverUtil.twaverNode","ref":22,"p":{"name":"组件","image":"assembly","location":{"x":321.5,"y":777},"angle":30},"c":{"nodetype":"assembly","assemblyBgColor":"#437DC6","assemblyColor":"#498ADD","assemblyOptColor":"#498ADD","dipangle":"45","directionangle":"30"}},{"class":"twaverUtil.twaverNode","ref":23,"p":{"name":"组件","image":"assembly","location":{"x":403.5,"y":777},"angle":30},"c":{"nodetype":"assembly","assemblyBgColor":"#437DC6","assemblyColor":"#498ADD","assemblyOptColor":"#498ADD","dipangle":"45","directionangle":"30"}},{"class":"twaverUtil.twaverNode","ref":24,"p":{"name":"逆变器e","image":"inverter","location":{"x":622,"y":205},"name2":"eee01"},"s":{"label2.position":"center","label2.color":"black"},"c":{"nodetype":"inverter","isBindDev":true,"bindNode":"tree0_1","esnCode":"eee01","devId":-156770317898421}},{"class":"twaverUtil.twaverNode","ref":25,"p":{"name":"逆变器","image":"inverter","location":{"x":752,"y":205}},"c":{"nodetype":"inverter"}}]}
         * createTime : 1531539771639
         */

        private int id;
        private String stationCode;
        private String name;
        private String schemaData;
        private long createTime;
        private TwaverDatasBean twaverDatasBean;

        public TwaverDatasBean getTwaverDatasBean() {
            return twaverDatasBean;
        }

        public void setTwaverDatasBean(TwaverDatasBean twaverDatasBean) {
            this.twaverDatasBean = twaverDatasBean;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStationCode() {
            return stationCode;
        }

        public void setStationCode(String stationCode) {
            this.stationCode = stationCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSchemaData() {
            return schemaData;
        }

        public void setSchemaData(String schemaData) {
            this.schemaData = schemaData;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }
    }

}
