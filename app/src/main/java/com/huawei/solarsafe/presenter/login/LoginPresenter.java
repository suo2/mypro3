package com.huawei.solarsafe.presenter.login;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.DomainBean;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.common.RetMsg;
import com.huawei.solarsafe.bean.user.login.AuthBean;
import com.huawei.solarsafe.bean.user.login.AuthInfo;
import com.huawei.solarsafe.bean.user.login.LoginBean;
import com.huawei.solarsafe.bean.user.login.LogoAndTitle;
import com.huawei.solarsafe.bean.user.login.PswTime;
import com.huawei.solarsafe.model.login.LoginModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.net.StringCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.service.CheckLoginStatusService;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.view.login.ILoginView;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.huawei.solarsafe.utils.LocalData.DOMAIN_BEAN;

//登录行为控制器接口实现类
public class LoginPresenter extends BasePresenter<ILoginView, LoginModel> implements ILoginPresenter {
    public static final String TAG = LoginPresenter.class.getSimpleName();
    public static final String RIGHT_LIST_ID = "right_list_id";

    private Gson gson = new Gson();

    private LocalData localData = LocalData.getInstance();

    public LoginPresenter() {
        setModel(new LoginModel());
    }

    @Override
    public void doLogin(String name, String psw, boolean forceLogin) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        if (view != null && model != null) {
            model.login(name, psw, forceLogin, lgCb);
        }
    }


    @Override
    public void getLogoAndTitle(String type) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.getLogoAndTitle(type, new CommonCallback(LogoAndTitle.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "request StationList failed! " + e);
                if (view != null) {
                    view.getLogoAndTitleFailed(MyApplication.getContext().getString(R.string.get_logo_title_failed), e);
                }
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view != null) {
                    view.getLogoAndTitleSuccess(response);
                }
            }
        });
    }

    /**
     * 登录请求的回调接口
     */
    private Callback<String> lgCb = new Callback<String>() {

        @Override
        public void onAfter(int id) {
            super.onAfter(id);
        }

        @Override
        public String parseNetworkResponse(final Response response, int id) throws Exception {
            List<String> tokens = response.headers().values("Set-Cookie");
            if (tokens != null && tokens.size() > 0){
                for (int i = 0; i < tokens.size(); i++) {
                    String token = tokens.get(i);
                    //判断是否是华为用户,防止获取logo和title的接口获取数据失败，导致首次登陆显示隐私申明错误问题---赵宇凤
                    if (!TextUtils.isEmpty(token) && token.contains("installerRegister")){
                        GlobalConstants.isInstallerRegister = token.split(";")[0].substring(18);
                    }
                }
            }
            //【安全特性】APP加载新的会话格式
            NetRequest.handleToken(response);
            String body = response.body().string();
            return body;
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            String msg = e.getMessage();
            if (msg != null && msg.contains("Socket closed")) {
                if (view != null) {
                    view.loginFailed("");
                }
            } else {
                if (GlobalConstants.HANDSHARKE_MSG.equals(msg)) {
                    if (view != null) {
                        view.loginFailed(GlobalConstants.HANDSHARKE_MSG);
                    }
                } else {
                    if (view != null) {
                        view.loginFailed(NetRequest.NETERROR);
                    }
                }
            }
        }

        @Override
        public void onResponse(String response, int id) {
            if (TextUtils.isEmpty(response)) return;
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Type loginType = new TypeToken<RetMsg<LoginBean>>() {
            }.getType();
            RetMsg<LoginBean> retMsg = null;
            //服务器返回非预期格式处理
            try {
                retMsg = gson.fromJson(response, loginType);
                JSONObject jsonObject = new JSONObject(response);
                String buildCode;
                if (response.contains("buildCode")) {
                    buildCode = jsonObject.getString("buildCode");
                    if (!TextUtils.isEmpty(buildCode)) {
                        localData.setWebBuildCode(buildCode);
                    } else {
                        localData.setWebBuildCode("0");
                    }
                } else {
                    localData.setWebBuildCode("0");
                }
            } catch (JsonSyntaxException e) {
                if (view != null) {
                    view.loginFailed(NetRequest.NETERROR);
                }
                return;
            } catch (JSONException e) {
                Log.e(TAG, "onResponse: " + e.getMessage());
            }

            if (retMsg.isSuccess() && retMsg.getData() != null && retMsg.getFailCode() == 0) {
                final LoginBean loginBean = retMsg.getData();
                //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                GlobalConstants.userId = loginBean.getUserid();
                GlobalConstants.privateSupport = loginBean.getPrivateSupport();
                localData.setDomainId(loginBean.getDomainid() + "");//登录业务结束
                //宋平修改  判断是否是户用用户，是则隐藏设备管理中的电站分区选择功能
                if ("PROPRIETOR".equals(loginBean.getUserType())) {
                    localData.setIsHouseholdUser(true);
                } else {
                    localData.setIsHouseholdUser(false);
                }
                //宋平修改 判断是否是Guest账号
                if("GUEST".equals(loginBean.getUserType())){
                    localData.setIsGuestUser(true);
                }else {
                    localData.setIsGuestUser(false);
                }
                GlobalConstants.userType = loginBean.getUserType();
                if (model == null) return;
                model.getAuth(new CommonCallback(AuthInfo.class) {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        view.loginFailed(MyApplication.getContext().getString(R.string.latest_login_failure));
                    }

                    @Override
                    public void onResponse(BaseEntity response, int id) {
                        if (response == null) {
                            view.loginFailed(MyApplication.getContext().getString(R.string.latest_login_failure));
                            return;
                        }
                        AuthInfo authInfo = (AuthInfo) response;
                        if (!authInfo.isSuccess()){
                            view.loginFailed(MyApplication.getContext().getString(R.string.latest_login_failure));
                            return;
                        }
                        List<AuthBean> list = authInfo.getAuthInfoList();
                        //权限列表持久化，将权限列表合成一个String实例，各个权限ID之间以","分隔
                        if (list == null) {
                            return;
                        }
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < list.size(); i++) {
                            sb.append(list.get(i).getId());
                            sb.append(",");
                        }
                        //去掉末尾“，”
                        if (sb.length() > 1) {
                            sb.setLength(sb.length() - 1);
                        }
                        String strRightsList = sb.toString();
                        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                        //将权限列表持久化保存本地
                        LocalData.getInstance().setRightString(strRightsList);
                        if (view != null) {
                            if (!TextUtils.isEmpty(strRightsList)) {
                                if ("app_appRoot".equals(strRightsList)) {
                                    view.loginFailed(MyApplication.getContext().getString(R.string.not_have_permission));
                                } else {
                                    view.loginSuccess(loginBean.getPrivateSupport());
                                }
                            } else {
                                view.loginFailed(MyApplication.getContext().getString(R.string.not_have_permission));
                            }
                        }
                        //登录成功后开启服务，周期性验证登录状态
                        Intent sIntent = new Intent(MyApplication.getContext(), CheckLoginStatusService.class);
                        MyApplication.getContext().startService(sIntent);
                    }
                });
                model.getDomianById(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                        Log.e("getDomianById", "get crrucy failed! net error!");
                    }

                    @Override
                    public void onResponse(Object response, int id) {
                        if (response == null) {
                            //设置默认币种为人民币
                            LocalData.getInstance().setCrrucy("1");
                            return;
                        }
                        try {
                            Gson gson = new Gson();
                            DomainBean domainBean = gson.fromJson(response.toString(), DomainBean.class);
                            if (domainBean.isSuccess()) {
                                DomainBean.DataBean data = domainBean.getData();
                                if (data != null) {
                                    //当用户只勾选了电站没有勾选域节点时，在修改电站会用到
                                    LocalData.getInstance().setDevList(DOMAIN_BEAN,data);
                                    if (!TextUtils.isEmpty(data.getCurrency())) {
                                        LocalData.getInstance().setCrrucy(data.getCurrency());
                                    }
                                }
                            } else {
                                LocalData.getInstance().setCrrucy("1");
                            }
                        } catch (JsonSyntaxException e) {
                            Log.e(TAG, "onResponse: " + e.toString());
                        }

                    }
                });
            } else if (!retMsg.isSuccess() || retMsg.getData() == null) {
                if (view != null) {
                    switch (retMsg.getFailCode()) {
                        case 10001:
                            view.loginFailed(MyApplication.getContext().getString(R.string.not_have_user));
                            view.clear();
                            break;
                        case 10002:
                            view.loginFailed(MyApplication.getContext().getString(R.string.login_type_error));
                            break;
                        case 10003:
                            view.loginFailed(MyApplication.getContext().getString(R.string.system_error));
                            break;
                        case 10004:
                            view.loginFailed(MyApplication.getContext().getString(R.string.account_locked));
                            break;
                        case 10023:
                            view.loginFailed(MyApplication.getContext().getString(R.string.email_or_password));
                            break;
                        case 10024:
                            view.loginFailed(MyApplication.getContext().getString(R.string.not_use_this_email));
                            break;
                        case 10005:
                            view.loginFailed(MyApplication.getContext().getString(R.string.captcha_error));
                            requestCodeImg();
                            break;
                        //强制登录
                        case 10032:
                        case 10025:
                            view.loginFailed(GlobalConstants.USERHASELOGINING);
                            break;
                        case 10028:
                            view.loginFailed(MyApplication.getContext().getString(R.string.have_com_phone));
                            break;
                        case 10029:
                            view.loginFailed(MyApplication.getContext().getString(R.string.phone_error_or_password_error));
                            break;
                        default:
                            view.loginFailed(MyApplication.getContext().getString(R.string.unknow_error));
                            break;
                    }
                }
            }
        }
    };

    /**
     * 口令检查时效性
     *
     * @param userid 用户id
     */
    @Override
    public void checkPswTime(String userid) {
        model.checkPswTime(userid, new CommonCallback(PswTime.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (view != null) {
                    view.checkPswTime(null,null);
                }
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view != null) {
                    if (response instanceof PswTime){
                        PswTime pswTime = (PswTime) response;
                        view.checkPswTime(pswTime.getNeedReset(),pswTime.getReason());
                    }

                }
            }
        });
    }

    /**
     * 请求是否验证码
     *
     * @param loginName 用户名
     */
    @Override
    public void isNeedCode(String loginName) {
        model.isNeedCode(loginName, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (view != null) {
                    view.isNeedCode(false);
                }
            }

            @Override
            public void onResponse(Object response, int id) {
                if (view != null) {
                    try {
                        if (!TextUtils.isEmpty(response.toString())) {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            boolean isNeedCode = jsonObject.optBoolean("data");
                            view.isNeedCode(isNeedCode);
                        } else {
                            view.isNeedCode(false);
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "onResponse: " + e.getMessage());
                    }
                }
            }
        });
    }

    /**
     * 请求验证码图片
     */
    @Override
    public void requestCodeImg() {
        model.requestCodeimg(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (view != null) {
                    view.requestCodeImg(null);
                }
            }

            @Override
            public void onResponse(Object response, int id) {
                String body = (String) response;
                try {
                    if (view != null) {
                        JSONObject jsonObject = new JSONObject(body);
                        String base64Url = jsonObject.optString("data");
                        view.requestCodeImg(base64Url);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                }
            }
        });
    }
}
