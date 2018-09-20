package com.huawei.solarsafe.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.huawei.solarsafe.bean.patrol.PatrolItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Create Date: 2017/3/9
 * Create Author: P00213
 * Description : 用于巡检报告页面数据库操作
 */
public class PatrolItemDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "PatrolItemDBHelper";
    public static final String TABLE_ITEM_RESULT = "itemResultTable";
    public static final String TABLE_PIC_PATH = "picPathTable";


    public PatrolItemDBHelper(Context context, int ver){
        super(context, TAG, null, ver);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ITEM_RESULT + "(" + "user" + " text," + "inspectId" + " text," + "remark" + " text,"+ "itemId"
                + " text," + "itemName" + " text," + "result" + " int" + ")");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PIC_PATH + "(" + "user" + " text," + "inspectId" + " text,"
                + "picPath" + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_ITEM_RESULT);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_PIC_PATH);
    }

    /**
     * 添加巡检条目
     * @param database
     * @param user
     * @param inspectId
     * @param itemId
     * @param result
     * @param remark
     * @param itemName
     * @return
     */
    public static boolean addItemResult(SQLiteDatabase database, String user, String inspectId, String itemId, String result, String remark, String itemName){
        String table = PatrolItemDBHelper.TABLE_ITEM_RESULT;
        database.delete(table, "user = ? and inspectId = ? and itemId = ?", new String[]{user, inspectId, itemId});
        ContentValues values = new ContentValues();
        values.put("user", user);
        values.put("inspectId", inspectId);
        values.put("remark", remark);
        values.put("itemId", itemId);
        values.put("result", result);
        values.put("itemName", itemName);
        database.insert(TABLE_ITEM_RESULT, null, values);

        return true;
    }

    /**
     * 删除巡检条目
     * @param database
     * @param user
     * @param inspectId
     * @return
     */
    public static boolean deletePatrolItems(SQLiteDatabase database, String user, String inspectId){
        String table = PatrolItemDBHelper.TABLE_ITEM_RESULT;
        database.delete(table, "user = ? and inspectId = ?", new String[]{user, inspectId});
        return true;
    }

    /**
     * 加载巡检条目
     * @param database
     * @param user
     * @param inspectId
     * @return
     */
    public static List<PatrolItem> loadItemResult(SQLiteDatabase database, String user, String inspectId){
        List<PatrolItem> lists = new ArrayList<>();
        Cursor cursor = database.query(TABLE_ITEM_RESULT, null, "user = ? and inspectId = ?", new String[] { user, inspectId }, null, null, null);
        if (cursor == null)
        {
            return null;
        }

        while (cursor.moveToNext())
        {
            PatrolItem patrolItem = new PatrolItem(cursor.getString(cursor.getColumnIndex("itemId")), cursor.getInt(cursor.getColumnIndex("result")), cursor.getString(cursor.getColumnIndex("remark")));
            patrolItem.setAnnexItemName(cursor.getString(cursor.getColumnIndex("itemName")));
            lists.add(patrolItem);
        }

        cursor.close();
        return lists;
    }

    /**
     * 添加图片地址
     * @param database
     * @param user
     * @param inspectId
     * @param path
     * @return
     */
    public static boolean addPicPath(SQLiteDatabase database, String user, String inspectId, String path){
        String table = PatrolItemDBHelper.TABLE_PIC_PATH;
        ContentValues values = new ContentValues();
        values.put("user", user);
        values.put("inspectId", inspectId);
        values.put("picPath", path);
        database.insert(table, null, values);

        return true;
    }


    public static boolean deletePicPaht2(SQLiteDatabase database, String user, String inspectId, String path){
        database.delete(PatrolItemDBHelper.TABLE_PIC_PATH, "user = ? and inspectId = ? and picPath = ?", new
                String[]{user, inspectId, path});
        return true;
    }

    //删除该用户该巡检电站的照片
    public static boolean deleteUserAllPic(SQLiteDatabase database, String user, String inspectId){
        database.delete(PatrolItemDBHelper.TABLE_PIC_PATH, "user = ? and inspectId = ?", new
                String[]{user, inspectId});
        return true;
    }

    /**
     * 获取该用户该巡检单站任务的所有图片地址
     * @param database
     * @param user
     * @param inspectId
     * @return
     */
    public static ArrayList<String> getAllPicPath(SQLiteDatabase database, String user, String inspectId){
        ArrayList<String> paths = new ArrayList<>();
        Cursor cursor = database.query(TABLE_PIC_PATH, null, "user = ? and inspectId = ?", new String[]{user, inspectId}, null, null, null);
        while(cursor.moveToNext()){
            paths.add(cursor.getString(cursor.getColumnIndex("picPath")));
        }
        cursor.close();
        return paths;
    }

}
