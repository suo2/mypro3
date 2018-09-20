package com.huawei.solarsafe.logger104.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.huawei.solarsafe.MyApplication;

import java.util.ArrayList;

/**
 * Created by P00517 on 2017/3/21.
 */
public class PntConnectDao {
    private PntDatabaseHelper helper;

    private PntConnectDao() {
        helper = new PntDatabaseHelper(MyApplication.getContext());
    }

    private static class Holder {
        public static final PntConnectDao INSTANCE = new PntConnectDao();
    }

    public static PntConnectDao getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 添加新信息
     *
     * @param info
     */
    public long insert(PntConnectInfoItem info) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PntConnectInfoItem.KEY_DEV_NAME, info.getDeviceName());
        values.put(PntConnectInfoItem.KEY_DEV_ESN, info.getDeviceESN());
        values.put(PntConnectInfoItem.KEY_DEV_TYPE, info.getDeviceType());
        values.put(PntConnectInfoItem.KEY_CONNECT_STATUS, info.getConnectStatus());
        values.put(PntConnectInfoItem.KEY_DEVICE_SAVE_TIME, info.getDeviceSaveTime());
        values.put(PntConnectInfoItem.KEY_PNT_LOCATION, info.getPntLocation());
        values.put(PntConnectInfoItem.KEY_MODBUS_LOCATION, info.getModbusLocation());
        values.put(PntConnectInfoItem.KEY_PNT_ESN, info.getPntESN());
        values.put(PntConnectInfoItem.KEY_PV_INFO, info.getPvInfo());
        values.put(PntConnectInfoItem.KEY_RESERVE_PLACE, info.getReservePlace());
        long num = db.insert(PntDatabaseHelper.TABLE_PNT_CONNECT_INFO, null, values);
        db.close();
        return num;
    }


    /**
     * 根据ESN号更改关联状态
     *
     * @param esn
     * @param status
     */
    public void updateConnectStatusByEsn(String esn, int status,String ipUser) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "update " + PntDatabaseHelper.TABLE_PNT_CONNECT_INFO + " set " +
                PntConnectInfoItem.KEY_CONNECT_STATUS + "=? where " + PntConnectInfoItem.KEY_DEV_ESN + "=? and " +
                PntConnectInfoItem.KEY_RESERVE_PLACE + "=?";
        String[] args = new String[]{status + "", esn,ipUser};
        db.execSQL(sql, args);
        db.close();
    }

    /**
     * 根据关联状态和当前环境及用户查询未关联数采数量
     *
     * @return
     */
    public int queryByStatus(int status, String ipUser) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select count(*) from " + PntDatabaseHelper.TABLE_PNT_CONNECT_INFO + " where " +
                PntConnectInfoItem.KEY_CONNECT_STATUS + "=? and " + PntConnectInfoItem.KEY_PNT_ESN + " is null and " +
                PntConnectInfoItem.KEY_RESERVE_PLACE + "=?";
        String[] args = new String[]{status + "", ipUser};
        Cursor cursor = db.rawQuery(sql, args);
        int count = 0;
        while (cursor.moveToNext()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count;
    }

    /**
     * 查询所有设备的esn号
     *
     * @return
     */
    public ArrayList<String> queryEsns(String ipUser) {
        ArrayList<String> esns = new ArrayList<>();
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select " + PntConnectInfoItem.KEY_DEV_ESN + " from " + PntDatabaseHelper.TABLE_PNT_CONNECT_INFO + " where " +
                PntConnectInfoItem.KEY_RESERVE_PLACE + "=?";
        String[] args = new String[]{ipUser};
        Cursor cursor = db.rawQuery(sql, args);
        while (cursor.moveToNext()) {
            esns.add(cursor.getString(cursor.getColumnIndex(PntConnectInfoItem.KEY_DEV_ESN)));
        }
        cursor.close();
        db.close();
        return esns;
    }

    /**
     * 根据上级数采的ESN号查询下联设备
     *
     * @param pntEsn 上级数采ESN号
     * @return
     */
    public ArrayList<PntConnectInfoItem> queryDeviceByPntESN(String pntEsn,String ipUser) {
        ArrayList<PntConnectInfoItem> infoItems = new ArrayList<>();
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select * from " + PntDatabaseHelper.TABLE_PNT_CONNECT_INFO + " where " + PntConnectInfoItem.KEY_PNT_ESN + "=? and "+
                PntConnectInfoItem.KEY_RESERVE_PLACE + "=?";
        String[] args = new String[]{pntEsn,ipUser};
        Cursor cursor = db.rawQuery(sql, args);
        PntConnectInfoItem infoItem;
        while (cursor.moveToNext()) {
            infoItem = new PntConnectInfoItem(cursor.getString(cursor.getColumnIndex(PntConnectInfoItem.KEY_DEV_NAME)),
                    cursor.getString(cursor.getColumnIndex(PntConnectInfoItem.KEY_DEV_ESN)),
                    cursor.getString(cursor.getColumnIndex(PntConnectInfoItem.KEY_DEV_TYPE)),
                    cursor.getInt(cursor.getColumnIndex(PntConnectInfoItem.KEY_CONNECT_STATUS)),
                    cursor.getLong(cursor.getColumnIndex(PntConnectInfoItem.KEY_DEVICE_SAVE_TIME)),
                    cursor.getString(cursor.getColumnIndex(PntConnectInfoItem.KEY_PNT_LOCATION)),
                    cursor.getInt(cursor.getColumnIndex(PntConnectInfoItem.KEY_MODBUS_LOCATION)),
                    cursor.getString(cursor.getColumnIndex(PntConnectInfoItem.KEY_PNT_ESN)),
                    cursor.getString(cursor.getColumnIndex(PntConnectInfoItem.KEY_PV_INFO)),
                    cursor.getString(cursor.getColumnIndex(PntConnectInfoItem.KEY_RESERVE_PLACE)));
            infoItems.add(infoItem);
        }
        cursor.close();
        db.close();
        return infoItems;
    }

    /**
     * 根据上联数采ESN号是否为空查询未连接数采信息
     *
     * @param status 数采连接状态
     * @return
     */
    public ArrayList<PntConnectInfoItem> queryPntInfo(int status, String iPUser) {
        ArrayList<PntConnectInfoItem> infoItems = new ArrayList<>();
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select * from " + PntDatabaseHelper.TABLE_PNT_CONNECT_INFO + " where " +
                PntConnectInfoItem.KEY_PNT_ESN + " is null and " + PntConnectInfoItem.KEY_CONNECT_STATUS + "= ? and " +
                PntConnectInfoItem.KEY_RESERVE_PLACE + "=?";
        String[] args = new String[]{status + "", iPUser};
        Cursor cursor = db.rawQuery(sql, args);
        PntConnectInfoItem infoItem;
        while (cursor.moveToNext()) {
            infoItem = new PntConnectInfoItem(cursor.getString(cursor.getColumnIndex(PntConnectInfoItem.KEY_DEV_NAME)),
                    cursor.getString(cursor.getColumnIndex(PntConnectInfoItem.KEY_DEV_ESN)),
                    cursor.getString(cursor.getColumnIndex(PntConnectInfoItem.KEY_DEV_TYPE)),
                    cursor.getInt(cursor.getColumnIndex(PntConnectInfoItem.KEY_CONNECT_STATUS)),
                    cursor.getLong(cursor.getColumnIndex(PntConnectInfoItem.KEY_DEVICE_SAVE_TIME)),
                    cursor.getString(cursor.getColumnIndex(PntConnectInfoItem.KEY_PNT_LOCATION)),
                    cursor.getInt(cursor.getColumnIndex(PntConnectInfoItem.KEY_MODBUS_LOCATION)),
                    cursor.getString(cursor.getColumnIndex(PntConnectInfoItem.KEY_PNT_ESN)),
                    cursor.getString(cursor.getColumnIndex(PntConnectInfoItem.KEY_PV_INFO)),
                    cursor.getString(cursor.getColumnIndex(PntConnectInfoItem.KEY_RESERVE_PLACE)));
            infoItems.add(infoItem);
        }
        cursor.close();
        db.close();
        return infoItems;
    }

    /**
     * 根据esn号查询组串容量信息
     *
     * @return
     */
    public String queryPvInfo(String esn, String ipUser) {
        if (TextUtils.isEmpty(esn)) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            return "";
        }
        String pvInfo = null;
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select " + PntConnectInfoItem.KEY_PV_INFO + " from " + PntDatabaseHelper.TABLE_PNT_CONNECT_INFO +
                " where " + PntConnectInfoItem.KEY_DEV_ESN + " =? and " +
                PntConnectInfoItem.KEY_RESERVE_PLACE + "=?";
        String[] args = new String[]{esn,ipUser};
        Cursor cursor = db.rawQuery(sql, args);
        while (cursor.moveToNext()) {
            pvInfo = cursor.getString(cursor.getColumnIndex(PntConnectInfoItem.KEY_PV_INFO));
        }
        cursor.close();
        db.close();
        return pvInfo;
    }

    /**
     * 根据设备esn号更新组串信息
     *
     * @param esn
     */
    public void updatePvInfoByEsn(String esn, String pvInfo,String ipUser) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "update " + PntDatabaseHelper.TABLE_PNT_CONNECT_INFO + " set " +
                PntConnectInfoItem.KEY_PV_INFO + "=? where " + PntConnectInfoItem.KEY_DEV_ESN + "=? and "+
                PntConnectInfoItem.KEY_RESERVE_PLACE + "=?";
        String[] args = new String[]{pvInfo, esn,ipUser};
        db.execSQL(sql, args);
        db.close();
    }

    /**
     * 根据esn号删除对应设备信息
     *
     * @param esn
     */
    public void deleteDeviceInfoByEsn(String esn,String ipUser) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "delete from " + PntDatabaseHelper.TABLE_PNT_CONNECT_INFO + " where " + PntConnectInfoItem.KEY_DEV_ESN + "=? and "+
                PntConnectInfoItem.KEY_RESERVE_PLACE + "=?";
        String[] args = new String[]{esn,ipUser};
        db.execSQL(sql, args);
        db.close();
    }
}
