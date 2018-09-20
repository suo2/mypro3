package com.huawei.solarsafe.model.devicemanagement;

import com.huawei.solarsafe.model.BaseModel;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;
import java.util.Map;

/**
 * Created by P00229 on 2017/2/21.
 */
public interface IDevManagementModel extends BaseModel {
    String URL_LISTDEV = "/devManager/listDev";
    String PINNET_URL_LISTDEV = "/devManager/queryHangingDeviceByParentId";
    String URL_PINNET_DEV_STATUS = "/devManager/listDevStatus";
    String URL_GETSINGNALDATA = "/devManager/getSignalData";
    String URL_DEVTYPE = "/signalconf/getDevTypeInfo";
    String URL_QUERYDEVHISTORY = "/devManager/queryDevHistoryData";//设备历史信息
    String URL_HOUSEHOLDINVPARAM = "/devManager/householdInvParam";
    String URL_HISTORYSIGNALDATA = "/signalconf/queryDevUnifiedSignals";
    String URL_DEV_ALARM_DATA = "/devAlarm/queryDevAlarm";
    String URL_DEV_DETAIL_DATA = "/devManager/queryDevDetail";
    String URL_QUERYDEVCHANGEDETAIL = "/devManager/queryDevChangeDetail";
    String URL_DEVCHANGE = "/devManager/devChange";
    String URL_SMART_LOGGER_INFO = "/devManager/querySmartLoggerInfo";
    //重启操作
    String URL_RESTART_OP = "/pinnetDc/remoteReset";
    //二次密码验证
    String URL_RESTARE_TWO_PWORD = "/user/secondAuth";
    //获取设备厂家
    String URL_INITMODULEOPTIONS = "/devManager/initModuleOptions";
    //查询厂家对应信号下的相关参数设置
    String URL_GETDEFAULTMODULE = "/devManager/getDefaultModule";
    //保存组串配置
    String URL_SAVEPVMODULE = "/devManager/savePvModule";
    //获取逆变器参数设置信息
    String URL_GETDEVPARAMS = "/devManager/getDevParams";
    //获取户用逆变器回写参数
    String URL_SETHOUSHOLD_PARAMS_VAL = "/devManager/getDevParamVal";
    //优化器定位
    String URL_YHQ_LOCATION = "/devManager/optLocation";
    //优化器使能
    String URL_YHQ_SHINENG = "/devManager/optEnable";
    //优化器实时数据
    String URL_YHQ_REAL_TIME_DATA = "/devManager/getDevsSignalData";
    //优化器故障列表
    String URL_YHQ_ERROR_LIST = "/devManager/optFaultList";
    //多个优化器历史信息对比
    String URL_OPT_HISTORY_DATA = "/devManager/queryOptHistoryData";
    //更新组串式逆变器下联设备
    String URL_REFRESH_INVERTER = "/dpu/refreshInverter";

    /**
     * 获取电站列表
     *
     */
    String URL_GET_STATION_LIST = "/station/page";

    /**
     * 设备列表查询
     */
    void requestDevList(Map<String, String> params, Callback callback);

    void requestGetSignalData(Map<String, String> params, Callback callback);

    void requestDevType(Map<String, String> params, Callback callback);

    void requestqueryDevHistoryData(Map<String, String> params, Callback callback);

    void requestHouseholdInvParam(String string, Callback callback);

    void requestDevHistorySingalData(Map<String, String> params, Callback callback);

    /**
     * 设备告警信息
     */
    void requestDevAlarmData(Map<String, String> params, Callback callback);

    /**
     * 设备详细信息
     */
    void requestDevDetailData(Map<String, String> params, Callback callback);

    /**
     * 品联数采信息
     */
    void ruerySmartLoggerInfo(Map<String, String> params, Callback callback);

    /**
     * 品联数采下挂设备信息
     */
    void requestPinnetDevList(Map<String, String> params, Callback callback);

    /**
     * 品联数采下挂设备状态
     */
    void requestPinnetDevListStatus(Map<String, List<String>> params, Callback callback);

    /**
     * 二次密码验证
     */
    void rueryTwoPassWordData(Map<String, String> params, Callback callback);

    /**
     * 品联数采重启
     */
    void rueryRestartData(Map<String, String> params, Callback callback);

    /**
     * 获取设备厂家
     */
    void requestInitModuleOption(String params, Callback callback);

    /**
     * 保存设备组串配置信息
     */
    void requestSaveModule(String params, Callback callback);

    /**
     * 查询厂家对应信号下的相关参数设置
     */
    void requestGetDefultModule(String params, Callback callback);

    /**
     * 获取逆变器参数信息
     */
    void requestGetDevParams(String params, Callback callback);

    void requestHouseholdInvParamVal(String string, Callback callback);

    void requestYHQLocation(Map<String, String> params, Callback callback);

    void requestYHQShineng(Map<String, String> params, Callback callback);

    /**
     * 优化器实时数据
     */
    void requestYHQRealTimeData(Map<String,String> params,Callback callback);

    /**
     * 优化器故障列表
     */
    void requestYHQErrorList(Map<String,String> param,Callback callback);

    void requestOptHistoryData(Map<String,String> param,Callback callback);

    void requestRefreshInverter(Map<String,String> param, Callback callback);
}
