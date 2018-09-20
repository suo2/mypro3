package com.huawei.solarsafe.model.login;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.service.GetAndroidId;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;

/**
 * Created by P00028 on 2017/1/3.
 */
//登录行为数据操作接口实现类
public class LoginModel implements ILoginModel {

    public static final String TAG = LoginModel.class.getSimpleName();

    public static final String RELOGIN = "reLogin";

    private NetRequest request = NetRequest.getInstance();

    @Override
    public void login(String name, String psw, boolean forceLogin,Callback cb) {
        HashMap<String, String> params = new HashMap<>();
        params.put("loginName", name);
        params.put("password", psw);
        params.put("forceLogin", forceLogin+"");
        params.put("uuid", GetAndroidId.id(MyApplication.getContext()));
        //如果需要验证码  则需将填写的验证码返回服务器  修改人：江东
        if (GlobalConstants.isNeedCode){
            params.put("checkCode",GlobalConstants.checkCode);
        }
        request.asynPostJson(NetRequest.IP + URL_LOGIN, params, cb);
    }

    @Override
    public void getLogoAndTitle(String type, Callback cb) {
        HashMap<String, String> params = new HashMap<>();
        params.put("type", type);
        request.asynPostJson(NetRequest.IP + LOGO_TITLE, params, cb);
    }


    //添加口令时效性
    @Override
    public void checkPswTime(String userId,Callback cb) {
        HashMap<String, String> params = new HashMap<>();
        request.asynPostJson(NetRequest.IP+URL_CHECK,params,cb);
    }

    @Override
    public void isNeedCode(String loginName, Callback cb) {
        HashMap<String, String> params = new HashMap<>();
        params.put("loginName", loginName);
        request.asynPostJson(NetRequest.IP+URL_CODE,params,cb);
    }

    @Override
    public void requestCodeimg(Callback cb) {
        HashMap<String, String> params = new HashMap<>();
        request.asynPostJson(NetRequest.IP+URL_CODEIMG,params,cb);
    }

    /**
     * 获取用户权限信息
     * @param cb
     */
    public void getAuth(Callback cb) {
        HashMap<String, String> params = new HashMap<>();
        request.asynPostJson(NetRequest.IP + URL_AUTH, params, cb);
    }

    public void getDomianById(Callback cb) {
        HashMap<String, String> params = new HashMap<>();
        request.asynPostJson(NetRequest.IP + URL_GET_DOMIAN, params, cb);
    }
}
