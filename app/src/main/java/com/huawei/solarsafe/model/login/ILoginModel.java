package com.huawei.solarsafe.model.login;

import com.huawei.solarsafe.model.BaseModel;
import com.zhy.http.okhttp.callback.Callback;

/**
 * Created by P00028 on 2017/1/3.
 */
//登录行为数据操作接口
public interface ILoginModel extends BaseModel {

    String URL_LOGIN = "/user/login";
    String URL_AUTH = "/role/queryUserRolSrc";
    //口令时效性接口
    String URL_CHECK = "/user/checkPswTime";

    //是否需要验证码
    String URL_CODE = "/user/isNeedChecked";
    //请求验证码图片接口
    String URL_CODEIMG = "/user/kaptcha";

    //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
    String LOGO_TITLE = "/enterpriseInfo/getLogoAndTitle";
    String URL_GET_DOMIAN = "/domain/getUserDomain";


    String AAA = "aaa";

    /**
     * 登录请求
     *
     * @param name 用户名
     * @param psw  密码
     */
    void login(String name, String psw, boolean forceLogin, Callback cb);

    void getLogoAndTitle(String type, Callback cb);

    void checkPswTime(String userId, Callback cb);

    void isNeedCode(String loginName, Callback cb);

    void requestCodeimg(Callback cb);
}
