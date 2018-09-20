package com.huawei.solarsafe.utils.device;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.huawei.solarsafe.bean.device.HistorySignalName;
import com.huawei.solarsafe.utils.LocalData;

import java.io.IOException;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;

/**
 * Created by p00507
 * on 2017/4/13.
 */

public class ExtraVoiceDBManager {
    private static final String TAG = "ExtraVoiceDBManager";
    private static final String EXCEPTION = "exception";
    public ExtraVoiceDBHelper mDBHelper = null;
    private static ExtraVoiceDBManager instance = null;
    public boolean isFinish;
    private String eName;
    private String zName;
    private String enName;
    private String jaName;
    private String itName;
    private String nlName;
    private Context context;

    public static ExtraVoiceDBManager getInstance() {
        if (instance == null) {
            instance = new ExtraVoiceDBManager();
        }
        return instance;
    }

    private ExtraVoiceDBManager() {
    }

    public void setContext(Context context) {
        this.context = context;
        mDBHelper = ExtraVoiceDBHelper.getInstance(context);
    }

    public void toWriteData(Context context) {
        if (LocalData.getInstance().getFristTimeReadData()) {
            readExcelToDB(context);
        } else {
            isFinish = true;
        }
    }

    /**
     * 读取excel数据到数据库里
     *
     * @param context
     */
    private void readExcelToDB(final Context context) {
        new Thread() {
            @Override
            public void run() {
                InputStream is = null;
                try {
                    is = context.getAssets().open("history_signal_data.xls");
                    Workbook book = Workbook.getWorkbook(is);
                    book.getNumberOfSheets();
                    // 获得第一个工作表对象
                    Sheet sheet = book.getSheet(0);
                    int Rows = sheet.getRows();//总行数
                    HistorySignalName info = null;
                    for (int i = 1; i < Rows; ++i) {
                        eName = (sheet.getCell(1, i)).getContents();
                        zName = (sheet.getCell(2, i)).getContents();
                        enName = (sheet.getCell(3, i)).getContents();
                        jaName = (sheet.getCell(4, i)).getContents();
                        itName = (sheet.getCell(5, i)).getContents();
                        nlName = (sheet.getCell(6, i)).getContents();
                        info = new HistorySignalName(eName, zName, enName, jaName,itName,nlName);
                        saveInfoToDataBase(info);
                        if (i == Rows - 1) {
                            isFinish = true;
                        }
                    }
                    book.close();
                } catch (Exception e) {
                    Log.e(TAG, EXCEPTION, e);
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                    } catch (IOException e) {
                        Log.e("ExtraVoiceDBManager",e.getMessage());
                    }
                }
            }
        }.start();
        LocalData.getInstance().setFristTimeReadData(false);
    }

    /**
     * 保存该条数据到数据库
     *
     * @param info excel中的某条数据
     */
    private void saveInfoToDataBase(HistorySignalName info) {
        if (mDBHelper == null) {
            return;
        }
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("eName", info.geteName());
            values.put("zName", info.getzName());
            values.put("enName", info.getEnName());
            values.put("jaName", info.getJaName());
            values.put("itName", info.getItName());
            values.put("nlName", info.getNlName());
            db.insert(ExtraVoiceDBHelper.TABLE_EXTRA_VOICE_INFO, null, values);
        } catch (SQLiteException e) {
            Log.e(TAG, EXCEPTION, e);
        } catch (Exception e) {
            Log.e(TAG, EXCEPTION, e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**
     * 根据内容获取 整条数据()
     *
     * @param eNameStr
     * @return
     */
    public HistorySignalName getHistorySignalName(String eNameStr) {
        HistorySignalName info = null;
        if (mDBHelper == null) {
            return info;
        }
        SQLiteDatabase db = mDBHelper.getReadableDatabase();

        if (db == null) {
            return info;
        }

        Cursor cursor = db.rawQuery("select * from ExtraVoiceInfo where eName = ?", new String[]{eNameStr});

        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String eName = cursor.getString(cursor.getColumnIndex("eName"));
                    String zName = cursor.getString(cursor.getColumnIndex("zName"));
                    String enName = cursor.getString(cursor.getColumnIndex("enName"));
                    String jaName = cursor.getString(cursor.getColumnIndex("jaName"));
                    String itName = cursor.getString(cursor.getColumnIndex("itName"));
                    String nlName = cursor.getString(cursor.getColumnIndex("nlName"));
                    info = new HistorySignalName(eName, zName, enName, jaName,itName,nlName);
                } while (cursor.moveToNext());
            }

        } catch (SQLiteException e) {
            Log.e(TAG, EXCEPTION, e);
        } catch (Exception e) {
            Log.e(TAG, EXCEPTION, e);
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
            if (db != null) {
                db.close();
            }
        }
        return info;
    }
}
