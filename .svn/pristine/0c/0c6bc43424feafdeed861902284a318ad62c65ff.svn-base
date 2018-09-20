package com.huawei.solarsafe.presenter.personal;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.bean.common.LogCallBack;
import com.huawei.solarsafe.bean.common.RetMsg;
import com.huawei.solarsafe.bean.personmanagement.HeaherImagePathBean;
import com.huawei.solarsafe.bean.update.UpdateCountInfo;
import com.huawei.solarsafe.bean.user.info.UserInfo;
import com.huawei.solarsafe.model.personal.UserInfoModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.net.StringCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.JSONReader;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.personal.IMyInfoView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by p00229 on 2017/1/5.
 */
public class MyInfoPresenter extends BasePresenter<IMyInfoView, UserInfoModel> {
    public static final String TAG = MyInfoPresenter.class.getSimpleName();

    public MyInfoPresenter() {
        setModel(new UserInfoModel());
    }

    public void doRequestUserInfo(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestUserInfo(params, new CommonCallback(UserInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                Log.e(TAG, "request UserInfo failed " + e);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
                if (view != null) {
                    view.getData(null);
                }
            }
            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view != null) {
                    view.getData(response);
                }
            }
        });
    }

    public void doRequestChangeUserInfo(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestChangeUserInfo(params, new CommonCallback(ResultInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                Log.e(TAG, "request ResultInfo failed " + e);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
                if (view != null) {
                    view.getData(null);
                }
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view!=null){
                    view.getData(response);
                }
            }
        });
    }

    public void doRequestUserPwdBack(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestUserPwdBack(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
                if (view != null) {
                    view.getData(null);
                }
            }

            @Override
            public void onResponse(Object response, int id) {
                if (response == null) {
                    if (view != null) {
                        view.getData(null);
                    }
                    return;
                }
                ResultInfo resultInfo = new ResultInfo();
                try {
                    JSONReader jsonReader = new JSONReader(new JSONObject((String) response));
                    resultInfo.setSuccess(jsonReader.getBoolean("success"));
                    resultInfo.setData(jsonReader.getString("data"));
                    view.getData(resultInfo);
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: "+e.getMessage());
                }

            }
        });
    }

    public void doRequestUserSendEmail(Map<String, String> params, final String tag) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestUserSendEmail(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
                if (view != null) {
                    view.getData(null);
                }
            }

            @Override
            public void onResponse(Object response, int id) {
                if (response == null) {
                    if (view != null) {
                        view.getData(null);
                    }
                    return;
                }
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                ResultInfo resultInfo = new ResultInfo();
                try {
                    JSONReader jsonReader = new JSONReader(new JSONObject(response.toString()));
                    resultInfo.setSuccess(jsonReader.getBoolean("data"));
                    resultInfo.setTag(tag);
                    resultInfo.setFailCode(jsonReader.getInt("failCode"));
                    view.getData(resultInfo);
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: " + e.toString());
                }
            }
        });
    }

    public void doRequestValidAccount(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestValidAccount(params, new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
                if (view != null) {
                    view.getData(null);
                }
            }

            @Override
            public void onResponse(Object response, int id) {
                if (response == null) {
                    if (view != null) {
                        view.getData(null);
                    }
                    return;
                }
                ResultInfo res = new ResultInfo();
                try {
                    JSONObject json = new JSONObject((String) response);
                    JSONReader reader = new JSONReader(json);
                    String data = reader.getString("data");
                    boolean success = reader.getBoolean("success");
                    if (!success){
                        res.setFailCode(reader.getInt("failCode"));
                    }
                    res.setSuccess(success);
                    res.setData(data);
                    view.getData(res);
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: "+e.getMessage());
                }
            }
        });
    }

    public void doRequestValidVerCode(Map<String, String> params, final String tag) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestValidVerCode(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
                if (view != null) {
                    view.getData(null);
                }
            }

            @Override
            public void onResponse(Object response, int id) {
                if (response == null) {
                    if (view != null) {
                        view.getData(null);
                    }
                    return;
                }
                ResultInfo res = new ResultInfo();
                try {
                    JSONObject json = new JSONObject((String) response);
                    JSONReader reader = new JSONReader(json);
                    boolean success = reader.getBoolean("success");
                    res.setSuccess(success);
                    res.setTag(tag);
                    view.getData(res);
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: "+e.getMessage());
                }
            }
        });
    }


    public void uploadAttachment(String filePath, HashMap<String, String> params) {
        final File sourceFile;
        try {
            sourceFile = new File(filePath);
            if (!sourceFile.exists()) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                boolean mkdir = sourceFile.mkdir();
                if (!mkdir) {
                    return;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "uploadAttachment: "+e.getMessage());
            ToastUtil.showMessage(MyApplication.getContext().getResources().getString(R.string.pic_compress_failed));
            return;
        }
        NetRequest.getInstance().postFileWithParams("/user/uploadImg", sourceFile, params, new LogCallBack() {
            @Override
            public void onFailed(Exception e) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
                if (view != null) {
                    view.uploadResult(false);
                }
            }

            @Override
            public void onSuccess(String response) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                try {
                    Type type = new TypeToken<RetMsg>() {
                    }.getType();
                    Gson gson = new Gson();
                    RetMsg<String> retMsg = gson.fromJson(response, type);
                    if (retMsg.isSuccess() && retMsg.getFailCode() == 0) {
                        view.uploadResult(true);
                        HeaherImagePathBean heaherImagePathBean = new HeaherImagePathBean();
                        heaherImagePathBean.setData(retMsg.getData());
                        view.getData(heaherImagePathBean);
                    } else {
                        view.uploadResult(false);
                    }
                } catch (Exception e) {
                    Log.e("error", e.toString());
                }
            }
        });
    }

    /**
     * 请求带应答升级通知数量
     */
    public void requestTodoUpgradeCount() {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        NetRequest.getInstance().asynPostJson(NetRequest.IP + "/upgrade/getTodoUpgradeCount", new HashMap<String, String>(),
                new CommonCallback(UpdateCountInfo.class) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call,e,id);
                        if (e.toString().contains("java.net.ConnectException")) {
                            ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                        }
                        if (view != null) {
                            view.getData(null);
                        }
                    }

                    @Override
                    public void onResponse(BaseEntity response, int id) {
                        view.getData(response);
                    }
                });
    }

    /**
     * 登出
     */
    public void doRequestLoginOut() {
        model.requestLoginOut(new HashMap<String, String>(), new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
            }

            @Override
            public void onResponse(Object response, int id) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                MyApplication.getOkHttpClient().dispatcher().cancelAll();
                //及时清除敏感数据
                Utils.clearData();
                MyApplication.clearOkClient();
            }
        });
    }
    public void userPasswordReset(Map<String, String> params){

        model.resetUserPassword(params,new StringCallback(){

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                if (view != null) {
                    view.getData(null);
                }
            }

            @Override
            public void onResponse(Object o, int i) {
                String response = (String) o;
                resolveResponseData(response);
            }
        });
    }

    private void resolveResponseData(String response){

        if(TextUtils.isEmpty(response)){
            ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
            if (view != null) {
                view.getData(null);
            }
            return;
        }
        try {
            JSONObject jsonObject = new JSONObject(response);
            if(jsonObject.getBoolean("success")){
                BaseEntity resultInfo = new ResultInfo();
                resultInfo.setSuccess(true);
                view.getData(resultInfo);
                return;
            }else{
                int failCode = jsonObject.getInt("failCode");
                String failMessage = null;
                switch (failCode) {
                    case 305:
                    case 306:
                        MyApplication.reLogin(MyApplication.getApplication().getString(R.string.long_time_no_login));
                        return;
                    case 307:
                        MyApplication.reLogin(MyApplication.getApplication().getString(R.string.other_device_login));
                        return;
                    case 405:
                        MyApplication.reLogin(MyApplication.getApplication().getString(R.string.infomation_changed));
                        return;
                    case 10010:
                        failMessage = MyApplication.getApplication().getString(R.string.faile_code_10010);
                        break;
                    case 10011:
                        failMessage = MyApplication.getApplication().getString(R.string.system_agrate_rgist);
                        break;
                    case 10012:
                        failMessage = MyApplication.getApplication().getString(R.string.faile_code_10012);
                        break;
                    case 10018:
                        failMessage = MyApplication.getApplication().getString(R.string.code_not_edit);
                        break;
                    case 10019:
                        failMessage = MyApplication.getApplication().getString(R.string.dev_not_existence);
                        break;
                    case 10020:
                        failMessage = MyApplication.getApplication().getString(R.string.code_dev_not_maching);
                        break;
                    case 10021:
                        failMessage = MyApplication.getApplication().getString(R.string.dev_alone_bd_str);
                        break;
                    case 10022:
                        failMessage = MyApplication.getApplication().getString(R.string.code_have_used);
                        break;
                    case 10027:
                        failMessage = MyApplication.getApplication().getString(R.string.have_email_to_regist);
                        break;
                    case 10007:
                        failMessage = MyApplication.getApplication().getString(R.string.faile_code_10007);
                        break;
                    case 10061:
                        failMessage = MyApplication.getApplication().getString(R.string.faile_code_10061);
                        break;
                    case 10005:
                        failMessage = MyApplication.getApplication().getString(R.string.faile_code_10005);
                        break;
                    case 10008:
                        failMessage = MyApplication.getApplication().getString(R.string.faile_code_10008);
                        break;
                    case 10060:
                        failMessage = MyApplication.getApplication().getString(R.string.faile_code_10060);
                        break;
                    case 10062:
                        failMessage = MyApplication.getApplication().getString(R.string.faile_code_10062);
                        break;
                    case 10040:
                        failMessage = MyApplication.getApplication().getString(R.string.faile_code_10040);
                        break;
                    case 10063:
                        failMessage = MyApplication.getApplication().getString(R.string.faile_code_10063);
                        break;
                    case 10006:
                        failMessage = MyApplication.getApplication().getString(R.string.faile_code_10006);
                        break;
                    case -1:
                        failMessage = MyApplication.getApplication().getString(R.string.faile_code_load_1);
                        break;
                    case -2:
                        failMessage = MyApplication.getApplication().getString(R.string.faile_code_load_2);
                        break;
                    default:
                        try{
                            failMessage = jsonObject.getString("data");
                        }catch (Exception e){
                            Log.e("failMessage",e.toString());
                        }
                        if(TextUtils.isEmpty(failMessage)){
                            failMessage = MyApplication.getApplication().getString(R.string.password_reset_fail);
                        }
                        break;
                }
                ToastUtil.showMessage(failMessage);
                if (view != null) {
                    view.getData(null);
                }
                return;
            }
        } catch (JSONException e) {
            ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
            if (view != null) {
                view.getData(null);
            }
        }
    }
}
