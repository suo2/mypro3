package com.huawei.solarsafe.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * @author wujing
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "fusionHome";
    public static final String TABLE_MESSAGE_INFO = "messageInfo";
    public static final String TABLE_MESSAGE_LASTEST_TIME = "messageLastestTime";
    private static final int DB_VERSION = 1;
    private static DBHelper mInstance;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public synchronized static DBHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DBHelper(context);
        }
        return mInstance;
    }

    ;

    @Override
    public void onCreate(SQLiteDatabase db) {

        /**
         * 聊天记录
         */
        String sql_msg = "Create table IF NOT EXISTS " + DBcolumns.TABLE_MSG
                + "(" + DBcolumns.MSG_ID + " integer primary key autoincrement,"
                + DBcolumns.MSG_STATIONANME + " text,"
                + DBcolumns.MSG_STATIONCODES + " text,"
                + DBcolumns.MSG_ALARMNAME + " text,"
                + DBcolumns.MSG_ALARMLEVEL + " text,"
                + DBcolumns.MSG_AlARMSTATUS + " text,"
                + DBcolumns.MSG_ALARMTYPE + " text,"
                + DBcolumns.MSG_DEVNAME + " text,"
                + DBcolumns.MSG_DEVTYPE + " text,"
                + DBcolumns.MSG_STARTTIEM + " text,"
                + DBcolumns.MSG_ENDTIEM + " text,"
                + DBcolumns.MSG_FILLTERNAME + " text,"
                + DBcolumns.MSG_USERID + " text,"
                + DBcolumns.MSG_TYPE + " text,"
                + DBcolumns.MSG_BAK1 + " text,"
                + DBcolumns.MSG_BAK2 + " text,"
                + DBcolumns.MSG_BAK3 + " text,"
                + DBcolumns.MSG_BAK4 + " text);";
        db.execSQL(sql_msg);

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_MESSAGE_INFO + "(" + "id" + " text," + "keyId" + " text," + "userId" + " text," + "message" + " text,"
                + "msgType" + " text," + "sendTime" + " text," + "readflag" + " text," + "ip" + " text" + ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_MESSAGE_LASTEST_TIME + "(" + "userId" + " text," + "lastestTime" + " text," + "ip" + " text" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_MESSAGE_INFO);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_MESSAGE_LASTEST_TIME);
        onCreate(db);
    }

}
