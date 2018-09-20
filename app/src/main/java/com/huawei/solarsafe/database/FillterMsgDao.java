package com.huawei.solarsafe.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.huawei.solarsafe.bean.FillterMsg;

import java.util.ArrayList;

public class FillterMsgDao {
    private DBHelper helper;

    public FillterMsgDao(Context context) {
        helper = new DBHelper(context);
    }

    /**
     * 添加新信息
     *
     * @param msg
     */
    public int insert(FillterMsg msg) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBcolumns.MSG_STATIONANME, msg.getStationName());
        values.put(DBcolumns.MSG_STATIONCODES, msg.getStationCodes());
        values.put(DBcolumns.MSG_ALARMNAME, msg.getAlarmName());
        values.put(DBcolumns.MSG_AlARMSTATUS, msg.getAlarmStatus());
        values.put(DBcolumns.MSG_ALARMLEVEL, msg.getAlarmLevel());
        values.put(DBcolumns.MSG_ALARMTYPE, msg.getAlarmType());
        values.put(DBcolumns.MSG_DEVNAME, msg.getDevName());
        values.put(DBcolumns.MSG_DEVTYPE, msg.getDevType());
        values.put(DBcolumns.MSG_STARTTIEM, msg.getStartTime());
        values.put(DBcolumns.MSG_ENDTIEM, msg.getEndTime());
        values.put(DBcolumns.MSG_FILLTERNAME, msg.getFillterName());
        values.put(DBcolumns.MSG_USERID, msg.getUserId());
        values.put(DBcolumns.MSG_TYPE, msg.getType());
        values.put(DBcolumns.MSG_BAK1, msg.getBak1());
        values.put(DBcolumns.MSG_BAK2, msg.getBak2());
        values.put(DBcolumns.MSG_BAK3, msg.getBak3());
        values.put(DBcolumns.MSG_BAK4, msg.getBak4());
        db.insert(DBcolumns.TABLE_MSG, null, values);
        int id = queryTheLastMsgId();
        db.close();
        return id;
    }

    /**
     * 根据msgid，删除过滤条件
     *
     * @return
     */
    public long deleteMsgById(int msgid) {
        SQLiteDatabase db = helper.getWritableDatabase();
        long row = db.delete(DBcolumns.TABLE_MSG, DBcolumns.MSG_ID + " = ?", new String[]{"" + msgid});
        db.close();
        return row;
    }

    /**
     * 根据userId查询过滤条件列表
     *
     * @return
     */
    public ArrayList<FillterMsg> queryMsg(String userId, String type) {
        ArrayList<FillterMsg> list = new ArrayList<FillterMsg>();
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select * from " + DBcolumns.TABLE_MSG + " where " + DBcolumns.MSG_USERID + "=?" + " and " + DBcolumns.MSG_TYPE + "=?";
        String[] args = new String[]{userId, type};
        Cursor cursor = db.rawQuery(sql, args);
        FillterMsg msg ;
        while (cursor.moveToNext()) {
            msg = new FillterMsg();
            msg.setId(cursor.getInt(cursor.getColumnIndex(DBcolumns.MSG_ID)));
            msg.setStationName(cursor.getString(cursor.getColumnIndex(DBcolumns.MSG_STATIONANME)));
            msg.setStationCodes(cursor.getString(cursor.getColumnIndex(DBcolumns.MSG_STATIONCODES)));
            msg.setAlarmName(cursor.getString(cursor.getColumnIndex(DBcolumns.MSG_ALARMNAME)));
            msg.setAlarmStatus(cursor.getString(cursor.getColumnIndex(DBcolumns.MSG_AlARMSTATUS)));
            msg.setAlarmLevel(cursor.getString(cursor.getColumnIndex(DBcolumns.MSG_ALARMLEVEL)));
            msg.setAlarmType(cursor.getString(cursor.getColumnIndex(DBcolumns.MSG_ALARMTYPE)));
            msg.setDevName(cursor.getString(cursor.getColumnIndex(DBcolumns.MSG_DEVNAME)));
            msg.setDevType(cursor.getString(cursor.getColumnIndex(DBcolumns.MSG_DEVTYPE)));
            msg.setStartTime(cursor.getString(cursor.getColumnIndex(DBcolumns.MSG_STARTTIEM)));
            msg.setEndTime(cursor.getString(cursor.getColumnIndex(DBcolumns.MSG_ENDTIEM)));
            msg.setFillterName(cursor.getString(cursor.getColumnIndex(DBcolumns.MSG_FILLTERNAME)));
            msg.setUserId(cursor.getString(cursor.getColumnIndex(DBcolumns.MSG_USERID)));
            msg.setType(cursor.getString(cursor.getColumnIndex(DBcolumns.MSG_TYPE)));
            msg.setBak1(cursor.getString(cursor.getColumnIndex(DBcolumns.MSG_BAK1)));
            msg.setBak2(cursor.getString(cursor.getColumnIndex(DBcolumns.MSG_BAK2)));
            msg.setBak3(cursor.getString(cursor.getColumnIndex(DBcolumns.MSG_BAK3)));
            msg.setBak4(cursor.getString(cursor.getColumnIndex(DBcolumns.MSG_BAK4)));
            list.add(msg);
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * 查询最新一条记录的id
     *
     * @return
     */
    public int queryTheLastMsgId() {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "select " + DBcolumns.MSG_ID + " from " + DBcolumns.TABLE_MSG + " order by " + DBcolumns.MSG_ID + " desc limit 1";
        String[] args = new String[]{};
        Cursor cursor = db.rawQuery(sql, args);
        int id = -1;
        if (cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex(DBcolumns.MSG_ID));
        }
        cursor.close();
        db.close();
        return id;
    }

}
