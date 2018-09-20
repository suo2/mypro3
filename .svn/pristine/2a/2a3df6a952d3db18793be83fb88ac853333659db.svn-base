package com.huawei.solarsafe.logger104.database;

import android.util.Log;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.bean.pnlogger.SignPointInfo;

import java.util.ArrayList;
import java.util.List;


public class PntDatabase {
    private static final String TAG = "PntDatabase";
    private PntDatabaseHelper pntDatabaseHelper;
    private static final String EXCEPTION = "Exception";


    private PntDatabase() {
        pntDatabaseHelper = new PntDatabaseHelper(MyApplication.getContext());
    }

    private static class Holder {
        public static final PntDatabase INSTANCE = new PntDatabase();
    }

    public static PntDatabase getInstance() {
        return Holder.INSTANCE;
    }


    public void addSignPointInfo(List<SignPointInfo> infos) {
        List<SignPointInfoItem> items = new ArrayList<>();
        for (SignPointInfo info : infos) {
            SignPointInfoItem item = new SignPointInfoItem(info.getId(), info.getVenderName(), info
                    .getModelCode(), info.getModelVersion(), info.getCode(), info.getDevTypeId(), info.getProtocolCode());
            items.add(item);
        }
        pntDatabaseHelper.insertSignPointInfoItem(items);
    }

    public void clearSignPointInfo() {
        try {
            pntDatabaseHelper.clearSignPointInfoItem();
        } catch (Exception e) {
            Log.e(TAG, EXCEPTION, e);
        }
    }


    public List<SignPointInfo> getSignPointInfos() {
        List<SignPointInfo> list = null;
        try {
            list = new ArrayList<>();
            List<SignPointInfoItem> items = pntDatabaseHelper.querySignPointInfoItem();
            for (SignPointInfoItem item : items) {
                SignPointInfo info = new SignPointInfo();
                info.setId(item.getId());
                info.setVenderName(item.getVender());
                info.setModelCode(item.getModelCode());
                info.setModelVersion(item.getVersion());
                info.setCode(item.getCode());
                info.setProtocolCode(item.getProtocolCode());
                list.add(info);
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception", e);
        }
        return list;
    }

    public long getDevTypeIdbyId(long id) {
        return pntDatabaseHelper.queryDevTypeIdById(id);
    }



}
