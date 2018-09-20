package com.huawei.solarsafe.bean.patrol;

import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.net.JSONReader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Create Date: 2017/3/7
 * Create Author: p00213
 * Description :
 */
public class PatrolItemList extends BaseEntity{
    private List<PatrolCheckItem> items;

    public List<PatrolCheckItem> getItems() {
        return items;
    }

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        JSONReader jsonReader = new JSONReader(jsonObject);
        JSONArray jsonArray = jsonReader.getJSONArray("list");
        items = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++)
        {
            JSONReader jr = new JSONReader(jsonArray.getJSONObject(i));
            PatrolCheckItem item = new PatrolCheckItem();
            item.setItemId(jr.getString("itemId"));
            item.setItemName(jr.getString("itemName"));
            items.add(item);
        }

        return true;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

    @Override
    public void setServerRet(ServerRet serverRet) {

    }

    public static class PatrolCheckItem{
        private String itemId;
        private String itemName;

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }
    }
}
