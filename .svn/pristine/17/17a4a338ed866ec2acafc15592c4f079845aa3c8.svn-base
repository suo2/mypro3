package com.huawei.solarsafe.logger104.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class PntDatabaseHelper extends SQLiteOpenHelper {
    private final static int DB_VERSION = 1;
    private static final String DATABASE_NAME = "PntDatabase.db";
    public static final String TABLE_STATION_INFO = "stationInfoTable";
    public static final String TABLE_SIGN_POINT_INFO = "signPointInfoTable";
    public static final String TABLE_PNT_DEVICE_INFO = "pntDeviceInfoTable";
    public static final String TABLE_PNT_CONNECT_INFO = "pntConnectInfoTable";


    public PntDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        sql = "CREATE TABLE IF NOT EXISTS " + TABLE_STATION_INFO + "("
                + PntStationInfoItem.KEY_UPDATE_TIME + " long,"
                + PntStationInfoItem.KEY_STATION_ID + " text," + PntStationInfoItem.KEY_STATION_NAME + " text," +
                PntStationInfoItem.KEY_PORT + " text," + PntStationInfoItem.KEY_STATION_LOCATION + " text," +
                PntStationInfoItem.KEY_STATION_WHERE + " text" + ")";
        db.execSQL(sql);
        sql = "CREATE TABLE IF NOT EXISTS " + TABLE_SIGN_POINT_INFO + "("
                + SignPointInfoItem.KEY_ID + " long," + SignPointInfoItem.KEY_DEV_TYPE_ID + " long,"
                + SignPointInfoItem.KEY_VENDER + " text," + SignPointInfoItem.KEY_MODEL_CODE + " text," +
                SignPointInfoItem.KEY_MODEL_VERSION + " text," + SignPointInfoItem.KEY_CODE + " text," + SignPointInfoItem.KEY_PROTOCOL_CODE + " text" + ")";
        db.execSQL(sql);
        sql = "CREATE TABLE IF NOT EXISTS " + TABLE_PNT_DEVICE_INFO + "("
                + PntDeviceInfoItem.KEY_DEV_NAME + " text,"
                + PntDeviceInfoItem.KEY_DEV_ESN + " text," + PntDeviceInfoItem.KEY_DEV_MODEL + " text," +
                PntDeviceInfoItem.KEY_DEV_TYPE + " text," + PntDeviceInfoItem.KEY_JOIN_STATUS + " integer)";
        db.execSQL(sql);
        sql = "CREATE TABLE IF NOT EXISTS " + TABLE_PNT_CONNECT_INFO + "(" +
                PntConnectInfoItem.KEY_DEV_NAME + " text," + PntConnectInfoItem.KEY_DEV_ESN + " text," +
                PntConnectInfoItem.KEY_DEV_TYPE + " text," + PntConnectInfoItem.KEY_CONNECT_STATUS + " integer," +
                PntConnectInfoItem.KEY_DEVICE_SAVE_TIME + " long," + PntConnectInfoItem.KEY_PNT_LOCATION + " text," +
                PntConnectInfoItem.KEY_MODBUS_LOCATION + " integer," + PntConnectInfoItem.KEY_PNT_ESN + " text," +
                PntConnectInfoItem.KEY_PV_INFO + " text," + PntConnectInfoItem.KEY_RESERVE_PLACE + " text)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            onCreate(db);
        }
    }

    public synchronized void insertSignPointInfoItem(List<SignPointInfoItem> items) {
        SQLiteDatabase database = getWritableDatabase();
        database.beginTransaction();
        for (SignPointInfoItem item : items) {
            if (item == null) {
                continue;
            }
            if ((item.getId() + "").isEmpty()) {
                continue;
            }
            String whereClums = SignPointInfoItem.KEY_ID + " = ?";
            String[] whereArgs = new String[]{item.getId() + ""};
            database.delete(TABLE_SIGN_POINT_INFO, whereClums, whereArgs);
            ContentValues values = new ContentValues();
            values.put(SignPointInfoItem.KEY_ID, item.getId());
            values.put(SignPointInfoItem.KEY_DEV_TYPE_ID, item.getDevTypeId());
            values.put(SignPointInfoItem.KEY_VENDER, item.getVender());
            values.put(SignPointInfoItem.KEY_MODEL_CODE, item.getModelCode());
            values.put(SignPointInfoItem.KEY_MODEL_VERSION, item.getVersion());
            values.put(SignPointInfoItem.KEY_CODE, item.getCode());
            values.put(SignPointInfoItem.KEY_PROTOCOL_CODE, item.getProtocolCode());
            database.insert(TABLE_SIGN_POINT_INFO, null, values);
        }
        database.setTransactionSuccessful();
        database.endTransaction();
        database.close();

    }

    public synchronized void clearSignPointInfoItem() {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_SIGN_POINT_INFO, null, null);
        database.close();
    }

    public synchronized List<SignPointInfoItem> querySignPointInfoItem() {
        List<SignPointInfoItem> items = new ArrayList<SignPointInfoItem>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(TABLE_SIGN_POINT_INFO, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex(SignPointInfoItem.KEY_ID));
            String vender = cursor.getString(cursor.getColumnIndex(SignPointInfoItem.KEY_VENDER));
            String modelCode = cursor.getString(cursor.getColumnIndex(SignPointInfoItem.KEY_MODEL_CODE));
            String modelVersion = cursor.getString(cursor.getColumnIndex(SignPointInfoItem.KEY_MODEL_VERSION));
            String code = cursor.getString(cursor.getColumnIndex(SignPointInfoItem.KEY_CODE));
            long devTypeId = cursor.getLong(cursor.getColumnIndex(SignPointInfoItem.KEY_DEV_TYPE_ID));
            String protocolCode = cursor.getString(cursor.getColumnIndex(SignPointInfoItem.KEY_PROTOCOL_CODE));
            SignPointInfoItem item = new SignPointInfoItem(id, vender, modelCode, modelVersion, code, devTypeId, protocolCode);
            items.add(item);
        }
        database.close();
        cursor.close();
        return items;
    }

    public synchronized long queryDevTypeIdById(long id) {
        SQLiteDatabase database = getReadableDatabase();
        long devTypeId = Long.MIN_VALUE;
        Cursor cursor = database.query(TABLE_SIGN_POINT_INFO, new String[]{SignPointInfoItem.KEY_DEV_TYPE_ID}, SignPointInfoItem.KEY_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        while (cursor.moveToNext()) {
            devTypeId = cursor.getLong(cursor.getColumnIndex(SignPointInfoItem.KEY_DEV_TYPE_ID));
            break;
        }
        database.close();
        cursor.close();
        return devTypeId;
    }

}
