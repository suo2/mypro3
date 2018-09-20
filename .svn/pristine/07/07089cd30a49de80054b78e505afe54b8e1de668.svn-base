package com.huawei.solarsafe.bean.patrol;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.utils.JSONReader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Create Date: 2017/3/4
 * Create Author: p00213
 * Description : 巡检报告封装类
 */
public class PatrolGisBean extends BaseEntity {

    List<String> picIds;
    List<GisBean> gisBeans;

    public List<String> getPicIds() {
        return picIds;
    }

    public List<GisBean> getGisBeans() {
        return gisBeans;
    }

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        picIds = new ArrayList<>();
        gisBeans = new ArrayList<>();
        JSONReader jsonReader = new JSONReader(jsonObject);
        JSONArray jsonArray1 = jsonReader.getJSONArray("pictureIds");
        for(int i = 0; i < jsonArray1.length(); i++)
        {
            String picId = jsonArray1.getString(i);
            picIds.add(picId);
        }
        JSONArray jsonArray2 = jsonReader.getJSONArray("itemReportList");
        for(int j = 0; j < jsonArray2.length(); j++)
        {
            JSONReader jr = new JSONReader(jsonArray2.getJSONObject(j));
            GisBean gisBean = new GisBean();
            gisBean.setInspectId(jr.getString("inspectId"));
            gisBean.setItemCode(jr.getString("itemCode"));
            gisBean.setItemId(jr.getString("itemId"));
            gisBean.setItemName(jr.getString("itemName"));
            gisBean.setItemResult(jr.getInt("itemResult"));
            gisBean.setRemark(jr.getString("remark"));

            gisBeans.add(gisBean);
        }
        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    public static class GisBean{
        String inspectId;
        String itemId;
        String itemCode;
        String itemName;
        int itemResult;
        String remark;

        public String getInspectId() {
            return inspectId;
        }

        public void setInspectId(String inspectId) {
            this.inspectId = inspectId;
        }

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }


        public void setItemCode(String itemCode) {
            this.itemCode = itemCode;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public int getItemResult() {
            return itemResult;
        }

        public void setItemResult(int itemResult) {
            this.itemResult = itemResult;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        @Override
        public String toString() {
            return "GisBean{" +
                    "inspectId='" + inspectId + '\'' +
                    ", itemId='" + itemId + '\'' +
                    ", itemCode='" + itemCode + '\'' +
                    ", itemName='" + itemName + '\'' +
                    ", itemResult=" + itemResult +
                    ", remark='" + remark + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PatrolGisBean{" +
                "picIds=" + picIds +
                ", gisBeans=" + gisBeans +
                '}';
    }
}
