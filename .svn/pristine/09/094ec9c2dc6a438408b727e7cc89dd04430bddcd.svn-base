package com.huawei.solarsafe.bean.pnlogger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ServerRet;

import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Create Date: 2017/3/9
 * Create Author: P00028
 * Description :
 */
public class PntDomainInfo extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 9142357772962889375L;
    private List<DomainInfo> domainInfos;
    private DomainInfo startDomain;
    private int size;

    @Override
    public boolean parseJson(JSONObject jsonObject) throws Exception {
        String strGson = jsonObject.getJSONArray("data").toString();
        Gson gson = new Gson();
        Type type = new TypeToken<List<DomainInfo>>() {
        }.getType();
        domainInfos = gson.fromJson(strGson, type);
        handleDomain(domainInfos);
        return true;
    }


    public String dimainInfo() {
        StringBuffer buffer = new StringBuffer();
        if (domainInfos != null && domainInfos.size() > 0) {
            for (DomainInfo info : domainInfos) {
                if (info.getModel().equals("STATION")) {
                    continue;
                }
                buffer.append(info.getId() + ",");
                buffer.append(info.getName() + ",");
            }
        }
        return buffer.toString();
    }


    //处理domain的层级关系
    private void handleDomain(List<DomainInfo> domainInfos) {
        if (domainInfos != null && domainInfos.size() > 0) {
            HashMap<String, List<DomainInfo>> assist = new HashMap<>();
            for (DomainInfo info : domainInfos) {
                if (info.getModel().equals("STATION")) {
                    continue;
                }
                String key = info.getLevel();
                if (assist.get(key) != null) {
                    ArrayList<DomainInfo> list = (ArrayList<DomainInfo>) assist.get(key);
                    list.add(info);
                } else {
                    ArrayList<DomainInfo> list = new ArrayList<>();
                    list.add(info);
                    assist.put(key, list);
                }
            }
            Set<String> keys = assist.keySet();
            this.size = keys.size();
            ArrayList<String> tempKeys = new ArrayList<>(keys.size());
            for (String str : keys) {
                tempKeys.add(str);
            }
            Collections.sort(tempKeys);
            int size = tempKeys.size();

            for (int i = 0; i < size - 1; i++) {
                if (i == 0) {
                    startDomain = assist.get(tempKeys.get(i)).get(0);
                    startDomain.setChildDomains(assist.get(tempKeys.get(i + 1)));
                } else {
                    ArrayList<DomainInfo> childDomain = (ArrayList<DomainInfo>) assist.get(tempKeys.get(i));
                    for (DomainInfo var : childDomain) {
                        ArrayList<DomainInfo> nextLevel = (ArrayList<DomainInfo>) assist.get(tempKeys.get(i + 1));
                        ArrayList<DomainInfo> tempLevel = new ArrayList<>();
                        for (DomainInfo nextVar : nextLevel) {
                            String id = var.getId();
                            if (id.equals(nextVar.getPid())) {
                                tempLevel.add(nextVar);
                            }
                        }
                        var.setChildDomains(tempLevel);
                    }
                }
            }
            assist.clear();
        }
    }

    public int getSize() {
        return size;
    }

    public DomainInfo getStartDomain() {
        return startDomain;
    }
    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufengufeng

    @Override
    public void setServerRet(ServerRet serverRet) {
        //Do nothing
    }

    @Override
    public String toString() {
        return "PntDomainInfo{" +
                "domainInfos=" + domainInfos +
                ", startDomain=" + startDomain +
                ", size=" + size +
                '}';
    }
}