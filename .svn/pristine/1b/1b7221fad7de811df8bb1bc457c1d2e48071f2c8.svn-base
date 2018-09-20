package com.huawei.solarsafe.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.bean.patrol.PatrolItem;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Create Date: 2017/3/9
 * Create Author: P00213
 * Description :用于巡检报告页面数据库操作
 */
public class PatrolItemDBManager {
    private static final String TAG = "PatrolItemDBManager";
    private PatrolItemDBHelper helper;
    protected static PatrolItemDBManager instance;
    private static int VERSION = 1;

    private PatrolItemDBManager(Context context) {
        helper = new PatrolItemDBHelper(context, VERSION);
    }

    public static PatrolItemDBManager getInstance() {
        if (instance == null) {
            instance = new PatrolItemDBManager(MyApplication.getContext());
        }
        return instance;
    }

    /**
     * 保存巡检条目结果及备注
     * @param user
     * @param inspectId
     * @param patrolItems
     * @param remark
     * @return
     */
    public boolean savePatrolItem( final String user, final String inspectId, final List<PatrolItem>
            patrolItems, final String remark) {
        if (patrolItems == null) {
            return false;
        }

        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                synchronized (helper) {
                    try {
                        if (patrolItems != null && patrolItems.size() > 0) {
                            SQLiteDatabase database = helper.getWritableDatabase();
                            for(PatrolItem patrolItem : patrolItems) {
                                helper.addItemResult(database, user, inspectId, patrolItem.getAnnexItemId(), patrolItem
                                        .getAnnexItemResult() + "", remark, patrolItem.getAnnexItemName());
                            }
                            database.close();
                        }
                    } catch (Exception e) {
                        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                        Log.e(TAG, "Exception", e);
                    }
                }
            }
        };
        // 创建线程，执行runnable
        new Thread(runnable).start();
        return true;
    }

    /**
     * 删除巡检条目
     * @param user
     * @param inspectId
     * @return
     */
    public boolean deletePatrolItem(final String user, final String inspectId) {

        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                synchronized (helper) {
                    try {

                        SQLiteDatabase database = helper.getWritableDatabase();
                        helper.deletePatrolItems(database, user, inspectId);
                        database.close();
                    } catch (Exception e) {
                        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                        Log.e(TAG, "Exception", e);
                    }
                }
            }
        };
        // 创建线程，执行runnable
        new Thread(runnable).start();
        return true;
    }

    /**
     * 加载（获取）巡检条目及结果
     * @param user
     * @param inspectId
     * @return
     */
    public List<PatrolItem> loadPatrolItem( final String user, final String inspectId) {
        List<PatrolItem> list = null;
        try {
            SQLiteDatabase database = helper.getWritableDatabase();
            list = helper.loadItemResult(database, user, inspectId);
            database.close();
        } catch (Exception e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e(TAG, "Exception", e);
        }
        return list;
    }

    /**
     * 保存图片地址
     * @param user
     * @param inspectId
     * @param path
     * @return
     */
    public boolean addPicPath( String user, String inspectId, String path){
        try {
            SQLiteDatabase database = helper.getWritableDatabase();
            helper.addPicPath(database, user, inspectId, path);
            database.close();
        }catch (Exception e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e(TAG, "Exception", e);
        }
        return true;
    }


    /**
     * 通过匹配图片地址删除其在数据库中的记录
     * @param user
     * @param inspectId
     * @param path
     * @return
     */
    public boolean deletePicPaht2( String user, String inspectId, String path){
        try {
            SQLiteDatabase database = helper.getWritableDatabase();
            helper.deletePicPaht2(database, user, inspectId, path);
            database.close();
        }catch (Exception e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e(TAG, "Exception", e);
        }
        return true;
    }

    /**
     * 删除当前用户当前单站巡检的图片记录
     * @param user
     * @param inspectId
     * @return
     */
    public boolean deleteUserPic( String user, String inspectId){
        try {
            SQLiteDatabase database = helper.getWritableDatabase();
            helper.deleteUserAllPic(database, user, inspectId);
            database.close();
        }catch (Exception e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e(TAG, "Exception", e);
        }
        return true;
    }

    /**
     * 获取当前用户当前单站巡检任务的所有已保存的图片地址记录
     * @param user
     * @param inspectId
     * @return
     */
    public ArrayList<String> getAllPicPath( String user, String inspectId){
        ArrayList<String> paths = null;
        try {
            SQLiteDatabase database = helper.getWritableDatabase();
            paths = helper.getAllPicPath(database, user, inspectId);
            database.close();
        }catch (Exception e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e(TAG, "Exception", e);
        }
        return paths;
    }
}
