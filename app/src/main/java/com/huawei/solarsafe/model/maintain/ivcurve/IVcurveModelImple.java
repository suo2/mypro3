package com.huawei.solarsafe.model.maintain.ivcurve;

import com.huawei.solarsafe.bean.ivcurve.ComparedBean;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by P00507
 * on 2017/7/19.
 */

public interface IVcurveModelImple {
    //获得组串逆变器
    String LISTDEV = "/ivcurve/listDev";
    //获取扫描任务列表
    String IVCURVE_LIST = "/ivcurve/listTask";
    //checkDev
    String CHECK_DEV = "/ivcurve/checkDev";
    //新增任务
    String CREAT_TASK = "/ivcurve/createTask";
    //电站级的故障列表
    String STATION_FAULR_LIST = "/ivcurve/stationFaultList";
    //停止扫描任务
    String STOP_TASK = "/ivcurve/stopTask";
    //获取组串基本信息
    String GET_BASIC_INFOR = "/ivcurve/getBasicInfor";
    //获取任务故障裂变
    String LIST_FAULT = "/ivcurve/listFault";
    //获取任务电站列表
    String STATION_LIST = "/ivcurve/getTaskStations";
    //获取诊断结果列表
    String LIST_TASK_RESULT = "/ivcurve/listTaskResult";
    //电站级故障统计列表及统计图
    String FAULT_STATISC = "/ivcurve/faultStatisc";
    //获取组串的IV曲线
    String GET_STRING_IV = "/ivcurve/getStringIV";
    //获取对比的IV曲线
    String GET_ALL_STRING_IV = "/ivcurve/getAllStringIVForCach";
    //IV曲线获取扫描失败原因
    String  GET_FAIL_CAUSE = "/ivcurve/getFailCause";
    //获取任务电站进度
    String GET_PROCESS = "/ivcurve/getProcess";


    /**
     * 获取扫描任务列表
     * @param param
     * @param callback
     */
    void requestListTask(Map<String,String> param,Callback callback);
	
    /**
     * check设备情况
     * @param param
     * @param callback
     */
    void requestCheckDev(Map<String,String> param,Callback callback);
    /**
     * 新增任务
     * @param param
     * @param callback
     */
    void creatTaskIV(Map<String,String> param,Callback callback);
    /**
     * 电站级的故障列表
     * @param param
     * @param callback
     */
    void requestStationFault(Map<String,String> param,Callback callback);
    /**
     * 组串基本信息
     * @param param
     * @param callback
     */
    void requestBasicInfor(Map<String,String> param,Callback callback);

    /**
     * 停止扫描任务
     * @param taskId
     * @param callback
     */
    void requestStopTask(long taskId,Callback callback);

    /**
     * 获取任务故障列表
     * @param param
     * @param callback
     */
    void requestListFault(Map<String,String> param,Callback callback);

    /**
     * 获取任务电站列表
     * @param taskId
     * @param callback
     */
    void requestStationList(long taskId,Callback callback);

    /**
     * 获取诊断结果列表
     * @param param
     * @param callback
     */
    void requestListTaskResult(Map<String,Long> param,Callback callback);

    /**
     * 电站级故障统计列表及统计图
     * @param param
     * @param callback
     */
    void requestFaultStatics(Map<String,String> param,Callback callback);

    /**
     * 获取组串的IV曲线
     * @param param
     * @param callback
     */
    void requestStringIV(Map<String,String> param,Callback callback);
    /**
     * 获取组串的IV曲线
     * @param param
     * @param callback
     */
    void requestIVCompared(Map<String,List<ComparedBean>> param, Callback callback);

    /**
     * IV曲线获取扫描失败原因
     * @param param
     * @param callback
     */
    void requestFailCause(HashMap<String,Integer> param, Callback callback);

    /**
     * 获取任务电站进度
     * @param param
     * @param callback
     */
    void requestTaskProcess(Map<String,String> param,Callback callback);
}
