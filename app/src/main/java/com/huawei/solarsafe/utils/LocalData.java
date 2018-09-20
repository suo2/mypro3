package com.huawei.solarsafe.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.report.Indicator;
import com.huawei.solarsafe.model.login.ILoginModel;
import com.huawei.solarsafe.presenter.login.LoginPresenter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by p00319 on 2017/2/14.
 */

/**
 * 本地数据工具类
 * 定义和业务无关的常量和方法
 */
public class LocalData {

    private static final String TAG = "LocalData";
    private static final String LOGGER_LAT = "lat";
    private static final String LOGGER_LON = "lon";
    private static final String DV_NAME = "dvNme";
    private static final String DV_MAX_CONNECT_NUMBER = "device_max_connect_number";
    private SharedPreferences sp;
    private static final String LOGINNAME = "loginName";
    private static final String LOGGER_ESN = "esn";
    private static final String LOGGER_SCAN_ESN = "scan_esn";
    private static final String LOGGER_ADDR = "addr";
    private static final String LOGGER_DEV_BIND_STATUS = "devBindStatus";
    private static final String MESSAGE_READ = "message_read";
    private static final String MISSION_READ = "mission_read";
    private static final String DOMAIN_ID = "domainid";
    private static final String HISTORYSIGNALDB = "frist_time_read_data";
    private static final String CRRUCY = "crrucy";
    private static final String LANGUAGE = "language";
    private static final String TIMEZONE = "timeZone";
    private static final String IPHISTORYHTTPS = "iphistoryhttps";
    public static final String DAYREPORT = "day";
    public static final String MOTHEREPORT = "mothe";
    public static final String YEARREPORT = "year";
    public static final String YEARSREPORT = "years";
    private static final String ISONESTATION = "isOneStation";
    private static final String ISONEDEV = "isOneDev";
    private static final String KEY_COMMUNICATION_MODE = "communicationMode";
    // 货币单位
    private static final String STATION_CURRENCY_TYPE = "CURRENCY_TYPE";
    //华为用户
    private static final String IS_HUAWEI_USER = "isHuaWeiUser";
    //是否显示引导页
    private static final String IS_SHOW_GUIDE = "isShowGuide";
    //运维权限管理 存储的值为map对象的json字符串，map的key为userid,值有三个选项("0".通知 "1".关闭 "2".开启)
    private static final String PERMISIION_SETTING = "permisiionSetting";
    //华为用户
    private static final String IS_HOUSEHOLD_USER = "Household_user";
    //guest账号
    public static final String IS_GUEST_USER = "guest_user";
    //是否支持扶贫
    private static final String SUPPORT_POOR = "support_poor";
    //是否打开自动检测跟新
    private static final String CHECK_UPDATE_APP = "checkUpdateApp";
    private static final String IS_FIRST_ZXING = "isFirstZxing";
    private static final String IS_FIRST_IN_MULTI_STATION="isFirstInMultiStation";//是否首次进入多电站首页
    //web端的版本号
    private static final String WEN_BUILD_CODE = "web_buildCode";
    private static final String ET_IP = "et_ip";
    public static final String DOMAIN_BEAN = "domain_bean_by_userid";
    public static final String IS_PUSH_MASSAGE = "ACTION_SHOW_NOTIFICATION";
    public static final String DAYREPORT_INVARTER = "day_intarver";
    public static final String MOTHEREPORT_INVARTER = "mothe_intarver";
    public static final String YEARREPORT_INVARTER = "year_intarver";

    public static final String INVERTER_POWER_KEY="inverter_power_key";
    public static final String STORED_ENERGY_CHARGE_KEY="stored_energy_charge_key";
    public static final String STORED_ENERGY_DISCHARGE_KEY="stored_energy_discharge_key";
    public static final String BUY_POWER_KEY="buy_power_key";
    public static final String INTERNET_POWER_KEY="internet_power_key";
    public static LocalData getInstance() {
        return StorageHandler.INSTANCE;
    }

    public boolean setDvName(String dvName) {
        return sp.edit().putString(DV_NAME, dvName).commit();
    }
    public String getDvName() {
        return sp.getString(DV_NAME, null);
    }

    private static class StorageHandler {
        public static final LocalData INSTANCE = new LocalData();
    }

    private LocalData() {
        sp = MyApplication.getContext().getSharedPreferences("fusionHome", Context.MODE_PRIVATE);
    }

    /**
     * 设置是否第一次进入二维码扫描界面
     *
     * @param isFirst
     */
    public void setsFirstZxing(boolean isFirst) {
        sp.edit().putBoolean(IS_FIRST_ZXING, isFirst).commit();
    }

    /**
     * 是否第一次进入二维码扫描界面
     *
     * @return
     */
    public boolean isFirstZxing() {
        return sp.getBoolean(IS_FIRST_ZXING, true);
    }

    /**
     * 设置是否第一次进入多电站首页
     * @param isFirst
     */
    public void setFirstInMultiStation(boolean isFirst){
        sp.edit().putBoolean(IS_FIRST_IN_MULTI_STATION,isFirst).commit();
    }

    /**
     * 是否第一次进入多电站首页
     * @return
     */
    public boolean isFirstInMultiStation(){
        return sp.getBoolean(IS_FIRST_IN_MULTI_STATION,true);
    }

    /**
     * 存取根秘钥创建更新时间
     */
    public boolean setRootKeyTime(long time) {
        return sp.edit().putString(ILoginModel.AAA, AESUtil.encrypt(String.valueOf(time))).commit();
    }

    public long getRootKeyTime() {
        String string = sp.getString(ILoginModel.AAA, "");
        String decrypt = AESUtil.decrypt(string);
        if (TextUtils.isEmpty(string)) return 0;
        try {
            return  Long.parseLong(decrypt);
        }catch (NumberFormatException e){
            return 0;
        }
    }

    /**
     * 判断是否是第一次去读取数据
     */
    public boolean setFristTimeReadData(Boolean b) {
        return sp.edit().putBoolean(HISTORYSIGNALDB, b).commit();
    }

    public boolean getFristTimeReadData() {
        return sp.getBoolean(HISTORYSIGNALDB, true);
    }

    public boolean setIpHistoryHttps(String ips) {
        return sp.edit().putString(IPHISTORYHTTPS, ips).commit();
    }

    public String getIpHistoryHttps() {
        return sp.getString(IPHISTORYHTTPS, "intl.fusionsolar.huawei.com");//有华为默认的
    }

    //获取货币类型
    public int getCurrencyType() {
        return sp.getInt(STATION_CURRENCY_TYPE, 0);
    }

    public void setLoginName(String loginName) {
        sp.edit().putString(LOGINNAME, loginName).apply();
    }

    public String getLoginName() {
        return sp.getString(LOGINNAME, "");
    }

    public void setLoginPsw(String psw) {
        sp.edit().putString("passwordLogin", AESUtil.encrypt(psw)).apply();
    }

    public String getLoginPsw() {
        return AESUtil.decrypt(sp.getString("passwordLogin", ""));
    }

    public void setLoginTime(long time) {
        sp.edit().putLong("loginTime", time).apply();
    }

    public long getLoginTime() {
        return sp.getLong("loginTime",Long.MIN_VALUE);
    }

    public void setIp(String ip) {
        sp.edit().putString("ip", ip).commit();
    }

    public String getIp() {
        return sp.getString("ip", "intl.fusionsolar.huawei.com");
    }

    public String getProtocol() {
        return sp.getString("protocol", "");
    }

    public void setProtocol(String protocol) {
        sp.edit().putString("protocol", protocol).apply();
    }


    public boolean getAutomaticLogin() {
        return sp.getBoolean(GlobalConstants.AUTOMATIC_LOGIN, false);
    }

    public void setAutomaticLogin(boolean automaticlogin) {
        sp.edit().putBoolean(GlobalConstants.AUTOMATIC_LOGIN, automaticlogin).commit();
    }

    public boolean getMessageRead() {
        return sp.getBoolean(MESSAGE_READ, false);
    }

    public void setMessagRead(boolean isRead) {
        sp.edit().putBoolean(MESSAGE_READ, isRead).commit();
    }

    public void setEsn(String esn) {
        sp.edit().putString(LOGGER_ESN, esn).commit();
    }

    public void setScanEsn(String esn) {
        sp.edit().putString(LOGGER_SCAN_ESN, esn).commit();
    }

    public void setPntAddr(String addr) {
        sp.edit().putString(LOGGER_ADDR, addr).commit();
    }

    public String getPntAddr() {
        return sp.getString(LOGGER_ADDR, null);
    }

    public void setLat(String setLat) {
        sp.edit().putString(LOGGER_LAT, AESUtil.encrypt(setLat)).commit();
    }

    public void setLon(String setLon) {
        sp.edit().putString(LOGGER_LON, AESUtil.encrypt(setLon)).commit();
    }

    public String getEsn() {
        return sp.getString(LOGGER_ESN, "");
    }

    public String getScanEsn() {
        return sp.getString(LOGGER_SCAN_ESN, "");
    }

    public String getLat() {
        return AESUtil.decrypt(sp.getString(LOGGER_LAT, ""));
    }

    public String getLon() {
        return AESUtil.decrypt(sp.getString(LOGGER_LON, ""));
    }


    public void setCommunicationMode(byte b) {
        sp.edit().putInt(KEY_COMMUNICATION_MODE, b).commit();
    }

    public byte getCommunicationMode() {
        return (byte) sp.getInt(KEY_COMMUNICATION_MODE, Byte.MIN_VALUE);
    }

    public int getDevBindStatus() {
        return sp.getInt(LOGGER_DEV_BIND_STATUS, Integer.MIN_VALUE);
    }

    public void setDevBindStatus(int devBindStatus) {
        sp.edit().putInt(LOGGER_DEV_BIND_STATUS, devBindStatus).commit();
    }

    public void setMissionRead(boolean isRead) {
        sp.edit().putBoolean(MISSION_READ, isRead).commit();
    }

    public String getRightString() {
        return sp.getString(LoginPresenter.RIGHT_LIST_ID, "");
    }

    public void setRightString(String rightListId) {
        sp.edit().putString(LoginPresenter.RIGHT_LIST_ID, rightListId).commit();
    }

    public String getDomainId() {
        return sp.getString(DOMAIN_ID, "");
    }

    public void setDomainId(String rightListId) {
        sp.edit().putString(DOMAIN_ID, rightListId).commit();
    }

    public String getCrrucy() {
        return sp.getString(CRRUCY, "1");
    }

    public void setCrrucy(String crrucy) {
        sp.edit().putString(CRRUCY, crrucy).commit();
    }


    /**
     * @return 获得当前的时区
     */
    public String getTimezone() {
        return sp.getString(TIMEZONE, "8");
    }

    /**
     * @param timezone 设置当前的时区
     */
    public void setTimezone(String timezone) {
        sp.edit().putString(TIMEZONE, timezone).commit();
    }

    /**
     * @return
     * 是否是华为用户(安装商)
     */
    public boolean getIsHuaWeiUser() {
        return sp.getBoolean(IS_HUAWEI_USER, true);
    }

    public void setIsHuaWeiUser(boolean isHuaWeiUser) {
        sp.edit().putBoolean(IS_HUAWEI_USER, isHuaWeiUser).commit();
    }

    /**
     * 存放实体类以及任意类型
     *
     * @param key
     * @param obj 组串式逆变器
     */
    public void setCasInvBean(String key, Object obj) {
        if (obj instanceof Serializable) {// obj必须实现Serializable接口，否则会出问题
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);
                String serStr = baos.toString("ISO-8859-1");
                serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
                oos.close();
                baos.close();
                sp.edit().putString(key, serStr).commit();
            } catch (IOException e) {
                // 【安全特性编号】   codeDex CID:159211    【修改人】：yangbinjie
                Log.e("LocalData", "setCasInvBean: " + e.getMessage());
            }
        } else {
            throw new IllegalArgumentException(
                    "the obj must implement Serializble");
        }

    }

    public Object getCasInvBean(String key) {
        Object obj = null;
        String base64 = sp.getString(key, "");
        try {
            if (base64.equals("")) {
                return null;
            }
            String redStr = java.net.URLDecoder.decode(base64, "UTF-8");
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                    redStr.getBytes("ISO-8859-1"));
            ObjectInputStream objectInputStream = new ObjectInputStream(
                    byteArrayInputStream);
            obj = objectInputStream.readObject();
            byteArrayInputStream.close();
            objectInputStream.close();
        } catch (Exception e) {
            // 【安全特性编号】   codeDex CID:159207    【修改人】：yangbinjie
            Log.e("LocalData", "getCasInvBean: " + e.getMessage());
        }
        return obj;
    }

    /**
     * 存放实体类以及任意类型
     *
     * @param key
     * @param obj 直流汇流箱
     */
    public void setDcInvBean(String key, Object obj) {
        if (obj instanceof Serializable) {// obj必须实现Serializable接口，否则会出问题
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);
                String serStr = baos.toString("ISO-8859-1");
                serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
                oos.close();
                baos.close();
                sp.edit().putString(key, serStr).commit();
            } catch (IOException e) {
                // 【安全特性编号】   codeDex CID:159197    【修改人】：yangbinjie
                Log.e("LocalData", "setDcInvBean: " + e.getMessage());
            }

        } else {
            throw new IllegalArgumentException(
                    "the obj must implement Serializble");
        }

    }

    public Object getDcInvBean(String key) {
        Object obj = null;
        String base64 = sp.getString(key, "");
        try {
            if (base64.equals("")) {
                return null;
            }
            String redStr = java.net.URLDecoder.decode(base64, "UTF-8");
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                    redStr.getBytes("ISO-8859-1"));
            ObjectInputStream objectInputStream = new ObjectInputStream(
                    byteArrayInputStream);
            obj = objectInputStream.readObject();
            byteArrayInputStream.close();
            objectInputStream.close();
        } catch (Exception e) {
            // 【安全特性编号】   codeDex CID:159212    【修改人】：yangbinjie
            Log.e("LocalData", "getDcInvBean: " + e.getMessage());
        }
        return obj;
    }

    /**
     * 存放实体类以及任意类型
     *
     * @param key
     * @param obj 式逆变器
     */
    public void setHouseInvBean(String key, Object obj) {
        if (obj instanceof Serializable) {// obj必须实现Serializable接口，否则会出问题
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);
                String serStr = baos.toString("ISO-8859-1");
                serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
                oos.close();
                baos.close();
                sp.edit().putString(key, serStr).commit();
            } catch (IOException e) {
                // 【安全特性编号】   codeDex CID:159202   【修改人】：yangbinjie
                Log.e("LocalData", "setHouseInvBean: " + e.getMessage());
            }

        } else {
            throw new IllegalArgumentException(
                    "the obj must implement Serializble");
        }

    }

    public Object getHouseInvBean(String key) {
        Object obj = null;
        String base64 = sp.getString(key, "");
        try {
            if (base64.equals("")) {
                return null;
            }
            String redStr = java.net.URLDecoder.decode(base64, "UTF-8");
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                    redStr.getBytes("ISO-8859-1"));
            ObjectInputStream objectInputStream = new ObjectInputStream(
                    byteArrayInputStream);
            obj = objectInputStream.readObject();
            byteArrayInputStream.close();
            objectInputStream.close();
        } catch (Exception e) {
            // 【安全特性编号】   codeDex CID:159210    【修改人】：yangbinjie
            Log.e("LocalData", "getHouseInvBean : " + e.getMessage());
        }
        return obj;
    }
    /**
     * 保存List
     * @param tag
     * 日
     */
    public void setDateDataList(String tag, LinkedList<Indicator> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        sp.edit().putString(tag, strJson).commit();
    }

    /**
     * 获取List
     *
     * @param tag
     * @return 日
     */
    public LinkedList<Indicator> getDateDataList(String tag) {
        LinkedList<Indicator> datalist = new LinkedList();
        String strJson = sp.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<LinkedList<Indicator>>() {
        }.getType());
        return datalist;
    }

    /**
     * 保存List
     *
     * @param tag
     * @param datalist 月
     */
    public void setMotheDataList(String tag, LinkedList<Indicator> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        sp.edit().putString(tag, strJson).commit();
    }

    /**
     * 获取List
     *
     * @param tag
     * @return 月
     */
    public LinkedList<Indicator> getMotheDataList(String tag) {
        LinkedList<Indicator> datalist = new LinkedList();
        String strJson = sp.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<LinkedList<Indicator>>() {
        }.getType());
        return datalist;
    }

    /**
     * 保存List
     *
     * @param tag
     * @param datalist 年
     */
    public void setYearDataList(String tag, LinkedList<Indicator> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        sp.edit().putString(tag, strJson).commit();
    }

    /**
     * 获取List
     *
     * @param tag
     * @return 年
     */
    public LinkedList<Indicator> getYearDataList(String tag) {
        LinkedList<Indicator> datalist = new LinkedList();
        String strJson = sp.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<LinkedList<Indicator>>() {
        }.getType());
        return datalist;
    }

    /**
     * 保存List
     *
     * @param tag
     * @param datalist 生命周期
     */
    public void setYearsDataList(String tag, LinkedList<Indicator> datalist) {
        if (null == datalist || datalist.size() <= 0)
            return;

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        sp.edit().putString(tag, strJson).commit();
    }

    /**
     * 获取List
     *
     * @param tag
     * @return 生命周期
     */
    public LinkedList<Indicator> getYearsDataList(String tag) {
        LinkedList<Indicator> datalist = new LinkedList();
        String strJson = sp.getString(tag, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<LinkedList<Indicator>>() {
        }.getType());
        return datalist;
    }

    //是否是单电站
    public boolean getIsOneStation() {
        return sp.getBoolean(ISONESTATION, false);
    }

    public void setIsOneStation(boolean isOneStation) {
        sp.edit().putBoolean(ISONESTATION, isOneStation).commit();
    }

    //是否是单设备
    public boolean getIsOneDev() {
        return sp.getBoolean(ISONEDEV, false);
    }

    public void setIsOneDev(boolean isOneDev) {
        sp.edit().putBoolean(ISONEDEV, isOneDev).commit();
    }

    /**
     * 存放实体类以及任意类型
     *
     * @param key
     * @param obj 设备集合
     */
    public void setDevList(String key, Object obj) {
        if (obj instanceof Serializable) {// obj必须实现Serializable接口，否则会出问题
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);
                String serStr = baos.toString("ISO-8859-1");
                serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
                oos.close();
                baos.close();
                sp.edit().putString(key, serStr).commit();
            } catch (IOException e) {
                // 【安全特性编号】   codeDex CID:159204   【修改人】：yangbinjie
                Log.e("LocalData", "setDevList: " + e.getMessage());
            }

        } else {
            throw new IllegalArgumentException(
                    "the obj must implement Serializble");
        }

    }

    public Object getDevList(String key) {
        Object obj = null;
        String base64 = sp.getString(key, "");
        try {
            if (base64.equals("")) {
                return null;
            }
            String redStr = java.net.URLDecoder.decode(base64, "UTF-8");
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                    redStr.getBytes("ISO-8859-1"));
            ObjectInputStream objectInputStream = new ObjectInputStream(
                    byteArrayInputStream);
            obj = objectInputStream.readObject();
            byteArrayInputStream.close();
            objectInputStream.close();
        } catch (Exception e) {
            // 【安全特性编号】   codeDex CID:159198    【修改人】：yangbinjie
            Log.e("LocalData", "getDevList: " + e.getMessage());
        }
        return obj;
    }

    public void setIsShowGuide(boolean isShowGuide) {
        sp.edit().putBoolean(IS_SHOW_GUIDE, isShowGuide).commit();
    }

    public boolean getIsShowGuide() {
        return sp.getBoolean(IS_SHOW_GUIDE, false);
    }

    public void setPermisiionSetting(Map<String, String> map) {
        Gson gson = new Gson();
        sp.edit().putString(PERMISIION_SETTING, map == null ? "" : gson.toJson(map)).commit();
    }

    public Map<String ,String> getPermisiionSetting() {
		try{
            String jsonStr = sp.getString(PERMISIION_SETTING, "");
            if(TextUtils.isEmpty(jsonStr)){
                return new HashMap<>();
            }
            Gson gson = new Gson();
            return gson.fromJson(jsonStr, Map.class);
        }catch (ClassCastException | JsonSyntaxException e){
			Log.e(TAG, "getPermisiionSetting: "+e.getMessage());
            return new HashMap<>();
        }
    }

    //是否是户用业主
    public void setIsHouseholdUser(boolean isHouseholdUser) {
        sp.edit().putBoolean(IS_HOUSEHOLD_USER, isHouseholdUser).commit();
    }

    public boolean getIsHouseholdUser() {
        return sp.getBoolean(IS_HOUSEHOLD_USER, false);
    }
    //是否是Guest用户
    public void setIsGuestUser(boolean isHouseholdUser){
        sp.edit().putBoolean(IS_GUEST_USER,isHouseholdUser).commit();
    }
    public boolean getIsGuestUser(){
        return sp.getBoolean(IS_GUEST_USER,false);
    }
    //是否支持扶贫
    public void setSupportPoor(boolean supportPoor) {
        sp.edit().putBoolean(SUPPORT_POOR, supportPoor).commit();
    }

    public boolean getSupportPoor() {
        return sp.getBoolean(SUPPORT_POOR, false);
    }


    //是否关闭自动检测更新
    public  void setCloseCheckUpdate(boolean isUpdate){
        sp.edit().putBoolean(CHECK_UPDATE_APP, isUpdate).commit();
    }
    public  boolean getCloseCheckUpdate(){
       return sp.getBoolean(CHECK_UPDATE_APP, true);
    }

    //web端的版本号
    public void setWebBuildCode(String huaweiIp) {
        sp.edit().putString(WEN_BUILD_CODE, huaweiIp).commit();
    }

    public String getWebBuildCode() {
        return sp.getString(WEN_BUILD_CODE,"0");
    }

    public void setDeviceMaxConnectNum(int num){
        sp.edit().putInt(DV_MAX_CONNECT_NUMBER,num).commit();
    }
    public int getDeviceMaxConnectNum(int defaultMaxNum){  //数采A版默认40    B/C版默认10
        return  sp.getInt(DV_MAX_CONNECT_NUMBER,defaultMaxNum);
    }

    public void setEtIp(boolean supportPoor) {
        sp.edit().putBoolean(ET_IP, supportPoor).commit();
    }

    public boolean getEtIp() {
        return sp.getBoolean(ET_IP, false);
    }
    //判断是否是发送了广播
    public void setIsShowPushMassage (String key ,boolean isPushShow){
        sp.edit().putBoolean(key, isPushShow).commit();
    }
    public boolean getIsShowPushMassage(String key) {
        return sp.getBoolean(key, false);
    }
    public void setPowerCurveSelect(String key,boolean value){
        sp.edit().putBoolean(key, value).commit();
    }
    public boolean getPowerCurveSelect(String key,boolean defValue){
        return sp.getBoolean(key, defValue);
    }
}
