package com.pinnet.energy.app;

import android.os.Environment;

import java.io.File;

/**
 * @author P00701
 * @date 2018/8/31
 */

public class Constants {

    //================= PATH ====================


    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ChargerApp";
    public static final String PATH_LOGS = PATH_SDCARD + File.separator + "Logs";
    public static final String PATH_PICTURE = PATH_SDCARD + File.separator + "Picture";

    public static final String PATH_DATA = PATH_SDCARD + File.separator + "data";


    public static final String PATH_CACHE = PATH_DATA + File.separator + "NetCache";

    /**
     * 配置文件名称
     */
    public static final String KEY_PLATFORM_CONFIG = "platform_conf.properties";


    //================= SharePrefUtils ====================
    /**
     * 已登录用户
     */
    public final static String LOGINED_USERNAME = "logined_username";
    public final static String LOGINED_USERID = "logined_id";
    public final static String LOGINED_TOKEN_ID = "tokenId";
    public final static String LOGINED_ACCOUNT = "logined_account";
    // APP_ID 替换为你的应用从官方网站申请到的合法appId
    public static String APP_ID = "wxce3e5ad15c03e1e6";

}
