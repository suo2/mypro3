/*
 * Copyright (C) TD Tech<br>
 * All Rights Reserved.<br>
 * 
 */
package com.huawei.solarsafe.bean;

import org.json.JSONObject;

/**
 * Create Date: 2014-11-11<br>
 * Create Author: cWX239887<br>
 * Description : 用于构造用户数据的抽象类
 */
public interface IUserDatabuilder {

	/** 数据请求状态 */
	String KEY_SUCCESS = "success";
	/** 数据报文列表 */
	String KEY_DATA = "data";
	/** 错误码关键字 */
	String KEY_ERROR_CODE = "failCode";
	/** 返回消息体*/
	String KEY_MESSAGE = "message";

	/**
	 * 将JSON数据解析成用户期望的数据类型
	 * 
	 * @param jsonObject
	 *            JSON对象
	 * @return 解析成功返回true，否则返回时报
	 * @throws Exception
	 */
	boolean parseJson(JSONObject jsonObject) throws Exception;

	// 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

	/**
	 * 设置统一的返回码
	 *
	 * @param serverRet
	 *            统一定义的返回码
	 */
	void setServerRet(ServerRet serverRet);

	/**
	 * 预处理服务端返回的接口数据，解决不同系统返回头部信息不统一的问题
	 *
	 * @param jsonObject
	 *            返回数据
	 */
	void preParse(JSONObject jsonObject) throws Exception;

}
