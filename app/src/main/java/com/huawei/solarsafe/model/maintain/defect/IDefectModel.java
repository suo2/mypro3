package com.huawei.solarsafe.model.maintain.defect;

import com.huawei.solarsafe.bean.defect.DefectListReq;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by p00319 on 2017/2/16.
 */
//缺陷相关网络请求接口
public interface IDefectModel {

    //缺陷流程状态
    /** 消缺中 */
    String HANDLING = "defectHandle";//消缺处理中状态
    /** 已退回/待分配 */
    String DISPACHING = "defectWrite";//工单填写中状态
    /** 待确认 */
    String CONFIRMING = "defectConfirm";//消缺确认中状态

    //Operation
    String SUBMIT = "submit";
    String SENDBACK = "back";


    String DefectListUrl = "/defect/listDefect";

    String DefectDetailUrl = "/defect/queryDefect";

    String CanHandleProcUrl="/defect/canHandleProc";


    /**
     * 请求缺陷列表数据
     *
     * @param reqMsg   请求消息的封装体
     * @param callback 请求回调
     */
    void requestDefectList(DefectListReq reqMsg, Callback callback);


    /**
     * 请求单个缺陷详细数据
     *
     * @param param    请求消息的封装体
     * @param callback 请求回调
     */
    void requestDefectDetail(Map<String, String> param, Callback callback);

    /**
     * 检查当前任务可以执行的操作
     * @param param
     * @param callback
     */
    void canHandleProc(Map<String,String> param,Callback callback);
}
