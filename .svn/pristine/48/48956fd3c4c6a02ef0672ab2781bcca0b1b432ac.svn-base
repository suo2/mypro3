package com.huawei.solarsafe.model.personmanagement;

import com.huawei.solarsafe.model.BaseModel;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by P00229 on 2017/4/10.
 */
public interface IPersonManagementModel extends BaseModel {
    String URL_QUERYUSERS = "/user/queryUsers";
    String URL_NEW_SAVEUSER = "/user/saveUser";
    String URL_QUERYROLES = "/role/queryRoles";
    String URL_QUERYUSERROLES = "/role/queryUserRoles ";
    String URL_QUERYUSERDOMAINS = "/domain/queryUserDomains";
    String URL_USER_UPDATEUSER = "/user/updateUser";
    String URL_COUNTUSERBYNAME = "/user/countUsersByName";
    String URL_DOMAINQUERYBYUSERID = "/domain/queryDomainByUserId";

    /**
     * 用户列表查询
     *
     * @param params
     * @param callback
     */
    void requestQueryUsersList(Map<String, String> params, Callback callback);

    /**
     * 新增用户
     *
     * @param params
     * @param callback
     */
    void requestSaveUser(String params, Callback callback);

    /**
     * 查询角色列表
     *
     * @param params
     * @param callback
     */
    void requestQueryRoles(Map<String, String> params, Callback callback);

    /**
     * 查询用户的角色
     *
     * @param params
     * @param callback
     */
    void requestQueryUserRoles(Map<String, String> params, Callback callback);


    /**
     * 修改用户
     *
     * @param params
     * @param callback
     */
    void requestUpdateUser(String params, Callback callback);

    /**
     * 验证用户名是否重复 创建用户需要
     *
     * @param params
     * @param callback
     */
    void requestCountUsersByName(Map<String, String> params, Callback callback);


}
