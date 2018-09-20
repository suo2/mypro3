/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.huawei.solarsafe.bean;

/**
 * Create Date: 2014-10-14<br>
 * Create Author: cWX239887<br>
 * Description :统一定义的返回码
 */
public enum ServerRet
{
    /** 处理成功 */
    OK(0, "OK"),
    /** 内部错误 */
    INTERNAL_ERROR(001, "Internal error"),
    /** 用户名或密码错误（登录使用） */
    USERNAME_OR_PASSWORD_ERROR(101, "Username or password error"),
    /** 账号被禁用 */
    USER_DISABLED(106, "user disabled"),
    /** 用户不能在本终端登录 */
    CANNOT_LOGIN(102, "Cannot login"),
    /** 登录错误次数超限，账户被锁定 */
    ACCOUNT_LOCKED(103, "Account is locked"),
    /** 用户名不合法（修改信息使用） */
    ILLEGAL_USERNAME(201, "Illegal username"),
    /** 用户名重复 */
    USERNAME_CONFLICT(202, "Username conflict"),
    /** 密码不合法 */
    ILLEGAL_PASSWORD(203, "Illegal password"),
    /** 用户未登录 */
    USER_NOT_LOGIN(204, "User didn't login"),
    /** 已使用过的密码 */
    USED_PASSWORD(206, "Used password"),
    /** 新旧密码相同 */
    SAME_PASSWORD(207, "Same password"),
    /** 客户端协议错误 */
    CLIENT_PROTOCOL_EXCEPTION(1001, "ClientProtocolException"),
    /** 资源未找到 */
    NOTFOUND(1002, "NOT FOUND"),
    /** 未授权 */
    UNAUTHORIZED(1003, "Unauthorized"),
    /** IO错误 */
    IO_EXCEPTION(1004, "IOException"),
    /** JSON 错误 */
    JSON_EXCEPTION(1005, "JSONException"),
    /** 状态错误 */
    ILLEGAL_STATE_EXCEPTION(1006, "IllegalStateException"),
    /** 客户端主动终止请求 */
    CLIENT_ABORT_REQUEST(1007, "ClientAbortRequest"), MTR_GET_PIC_INFO_FAIL(290020, "获取图元绑定信息失败"), MTR_GET_DEV_MODEL_FAIL(290021, "获取设备模型信息失败"), MTR_GET_DEV_TYPE_FAIL(
            290023, "获取设备类型信息失败"), MTR_GET_DEV_OEM_FAIL(290024, "获取设备厂商信息失败"), MTR_DEL_DEV_RELATION_FAIL(290025, "删除设备绑定关系失败"), MTR_OPT_DEV_FAIL(
            290028, "操作设备失败"), MTR_DEL_DEV_FAIL(290031, "删除设备失败"), MTR_GET_DEV_LIST_FAIL(290032, "获取设备列表失败"), MTR_GET_DEV_BASE_INFO_FAIL(290034,
            "通过Mdn获取设备基本信息失败"), MTR_GET_DEV_REMOTE_INFO_FAIL(290050, "获取设备遥测信数据失败"), MTR_GET_SUNSHINE_DEV_FAIL(290051, "获取未阳光化设备失败"), MTR_ADD_DEV_FAIL(
            290053, "添加设备失败"), MTR_GET_DEV_TYPE_LIST_FAIL(290054, "获取设备类型列表失败"), MTR_DEL_PIC_FAIL(290055, "删除图元失败"), MTR_GET_DEV_CONNETION_FAIL(
            290056, "获取设备连接状态失败"), MTR_SUBSCRIBE_DEV_REMOTE_FAIL(290057, "订阅设备的遥测数据失败"), MTR_UPDATE_DEV_BASE_INFO_FAIL(290058, "更新设备基本信息失败"), MTR_PARAM_CANT_EMPTY(
            290059, "参数值不能为空"), MTR_DUPLICATE_WARING(290060, "该设备已经加入系统，请确认业务编号，名称等没有和其他设备重复"), MTR_CANCEL_DEV_SUBSCRIBE(290061, "取消设备订阅失败"), MTR_GET_DEV_SIGNAL_FAIL(
            290062, "获取设备信号值失败"), MTR_DUPLICATE_BUS_CODE(290063, "设备业务编码重复"), MTR_DUPLICATE_NAME_CODE(290064, "设备名称重复"), MTR_DUPLICATE_ESN_CODE(
            290065, "设备esn重复"), MTR_DUPLICATE_IP_CODE(290066, "设备ip和二级地址重复"),
    USER_NOT_EXIT(21001, "用户不存在"), NO_STATIONS(21002, "用户无电站资源"), NO_DATA(21003, "无数据"), PARAMS_ERROR(21004, "必填参数为空或参数输入错误"), NO_PATROL_PLANT(21005, "无待巡检的电站"),
    PROCESS_FAIL(21006, "执行流程失败"), TASK_ERROR(21007, "任务为空或任务处理人不是当前用户"), USER_ALREADY_HAS_TASK(21008, "用户执行的任务还未完结，不能重新开启新任务"), TASK_NOT_INIT(21009, "当前任务不是未开启状态，不能启动"),
    /** 无效的返回值类型,返回的报文数据解析到错误的返回码 */
    INVALID_RET(-1, "Invalid result code");

    /** 返回码 */
    private final long mRetCode;
    /** 该类型对应的消息 */
    private final String mMessage;

    ServerRet(long code, String message)
    {
        this.mRetCode = code;
        this.mMessage = message;
    }

    /**
     * 获取返回码对应的消息
     * 
     * @return 消息
     */
    public String getMessage()
    {
        return mMessage;
    }

    public long getRetCode()
    {
        return mRetCode;
    }

    /**
     * 解析返回报文中的retCode字段，并转换成对应的SvrRet枚举类型
     * 
     * @param originalCode
     *            返回报文中的 retCode字段
     * @return 返回对应的SvrRet枚举类型
     */
    public static ServerRet parseString(long originalCode)
    {
        ServerRet targetResultCode = INVALID_RET;
        for (ServerRet svrRet : values())
        {
            if (svrRet.mRetCode == originalCode)
            {
                targetResultCode = svrRet;
                break;
            }
        }
        return targetResultCode;
    }

}
