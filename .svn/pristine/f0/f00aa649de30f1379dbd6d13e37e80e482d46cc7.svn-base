package com.huawei.solarsafe.utils.device;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by P00507
 * on 2018/1/16.
 */

public class StationPictureDBHelper extends SQLiteOpenHelper {
    public static final String DATA_BASE_NAME = "StationPictureTime.db";
    public static final String TABLE_STATIONPICTURE_INFO = "StationPictureTime";
    private static StationPictureDBHelper instance;
    private static final int VERSION = 1;
    public StationPictureDBHelper(Context context) {
        super(context, DATA_BASE_NAME, null, VERSION);
    }
    public static StationPictureDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new StationPictureDBHelper(context);
        }
        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_STATIONPICTURE_INFO + " (_id INTEGER PRIMARY KEY AUTOINCREMENT"
                + ",stationId TEXT ,stationPicTime TEXT)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if ( oldVersion != newVersion ){
            db.execSQL( "DROP TABLE IF EXISTS " + TABLE_STATIONPICTURE_INFO );
            onCreate(db);
        }
    }
}
