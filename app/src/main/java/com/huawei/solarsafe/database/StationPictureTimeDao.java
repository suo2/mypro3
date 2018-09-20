package com.huawei.solarsafe.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.huawei.solarsafe.bean.station.kpi.StationInfo;
import com.huawei.solarsafe.utils.device.StationPictureDBHelper;

/**
 * Created by P00507
 * on 2018/1/16.
 */

public class StationPictureTimeDao {
    public static final String STATION_PICTURE_ID = "stationId";
    public static final String STATION_PICTURE_TIME = "stationPicTime";
    private static final String EXCEPTION = "exception";
    private StationPictureDBHelper dbHelper;
    private static StationPictureTimeDao instance = null;

    public static StationPictureTimeDao getInstance(){
        if(instance == null){
            instance = new StationPictureTimeDao();
        }
        return instance;
    }
    public void setContext(Context context){
        dbHelper = new StationPictureDBHelper(context);
    }

    /**
     * 插入数据
     */
    public void insert(StationInfo stationInfo){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(STATION_PICTURE_ID, stationInfo.getsId());
            values.put(STATION_PICTURE_TIME, stationInfo.getPictureUpdataTime());
            db.insert(StationPictureDBHelper.TABLE_STATIONPICTURE_INFO, null, values);
        } catch (Exception e) {
            Log.e("StationPictureTimeDao" ,e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }
    /**
     * 根据内容获取 整条数据()
     *
     * @param stationId
     * @return
     */
    public String getStationPicTime(String stationId) {
        String stationPicTime = null;
        if (dbHelper == null) {
            return null;
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        if (db == null) {
            return null;
        }
        Cursor cursor = db.rawQuery("select * from StationPictureTime where stationId = ?", new String[]{stationId});

        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    stationPicTime = cursor.getString(cursor.getColumnIndex("stationPicTime"));
                } while (cursor.moveToNext());
            }
        }  catch (Exception e) {
            Log.e("StationPictureTimeDao", EXCEPTION, e);
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
            //  这里的db!=null   修改人:江东
            db.close();
        }
        return stationPicTime;
    }


    /**
     * 根据msgid，删除过滤条件
     *
     * @return
     */
    public void deleteMsgById(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long row = db.delete(StationPictureDBHelper.TABLE_STATIONPICTURE_INFO, STATION_PICTURE_ID + " = ?", new String[]{"" + id});
        db.close();
    }
}
