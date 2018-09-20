package com.huawei.solarsafe.utils.device;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.huawei.solarsafe.utils.LocalData;

/**
 * Created by p00507
 * on 2017/4/13.
 */

public class ExtraVoiceDBHelper extends SQLiteOpenHelper {
    public static final String TABLE_EXTRA_VOICE_INFO = "ExtraVoiceInfo";
    public static final String DATA_BASE_NAME = "ExtraVoiceCfg.db";
    private static final int VERSION = 10;
    private static ExtraVoiceDBHelper instance;

    public static ExtraVoiceDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new ExtraVoiceDBHelper(context);
        }
        return instance;
    }
    public ExtraVoiceDBHelper(Context context){
        super(context, DATA_BASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_EXTRA_VOICE_INFO + " (_id INTEGER PRIMARY KEY AUTOINCREMENT"
                + ",eName TEXT ,zName TEXT,enName TEXT,jaName TEXT,itName TEXT,nlName TEXT)" );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if ( oldVersion != newVersion ){
            LocalData.getInstance().setFristTimeReadData(true);
            db.execSQL( "DROP TABLE IF EXISTS " + TABLE_EXTRA_VOICE_INFO );
            onCreate(db);
        }
    }
}
