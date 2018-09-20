package com.huawei.solarsafe.presenter.login;

import android.text.TextUtils;
import android.util.Log;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.model.login.InstallerRegistratModelIm;
import com.huawei.solarsafe.net.StringCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.view.login.InstallerRegistratView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by p00507
 * on 2017/8/25.
 */

public class InstallerRegisterPredenterIm extends BasePresenter<InstallerRegistratView ,InstallerRegistratModelIm> implements InstrallerRegistPresenter{
    public static final String TAG = InstallerRegisterPredenterIm.class.getSimpleName();
    public InstallerRegisterPredenterIm() {
        setModel(new InstallerRegistratModelIm());
    }

    @Override
    public void doInstrallerRegist(Map<String, String> map) {
        model.doInstallerRegistrat(map, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                getErrorInfo(e);
                if(view != null){
                    view.getData("error");
                }
            }

            @Override
            public void onResponse(Object response, int id) {
                if(view != null){
                    if(!TextUtils.isEmpty(response.toString())){
                        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                        view.getData(response.toString());
                    } else{
                        view.getDataFiled("error");
                    }
                }
            }
        });
    }
    /**
     * 处理异常信息
     *
     * @param e
     */
    private void getErrorInfo(Exception e) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        Log.e(TAG, "getError: e" + e);
        if (e.getMessage() != null && e.getMessage().contains("Socket closed")) {
            if (view != null) {
                view.getDataFiled("");
            }
        } else if (e.toString().contains("SocketTimeout")) {
            if (view != null) {
                view.getDataFiled(MyApplication.getContext().getString(R.string.request_time_out));
            }
        } else {
            if (view != null) {
                view.getDataFiled(MyApplication.getContext().getString(R.string.net_error_2));
            }
        }
    }
    public void userInstallerRegister(Map<String, String> params){

        model.newInstallerRegister(params,new StringCallback(){


            @Override
            public void onError(Call call, Exception e, int id) {
                if (view != null) {
                    view.getDataFiled(MyApplication.getContext().getString(R.string.net_error));
                }
            }

            @Override
            public void onResponse(Object o, int i) {
                String response = (String) o;
                resolveResponseData(response,false);
            }


        });
    }

    public void getUserVercode(Map<String, String> params){

        model.getUserVercode(params,new StringCallback(){


            @Override
            public void onError(Call call, Exception e, int id) {
                if (view != null) {
                    view.getVerificationCodeFailed(MyApplication.getContext().getString(R.string.net_error));
                }
            }

            @Override
            public void onResponse(Object o, int i) {
                String response = (String) o;
                resolveResponseData(response,true);
            }

        });

    }
    public void checkUserVercode(Map<String, String> params){

        model.checkUserVercode(params,new StringCallback(){

            @Override
            public void onError(Call call, Exception e, int id) {
                if (view != null) {
                    view.getDataFiled(MyApplication.getContext().getString(R.string.net_error));
                }
            }

            @Override
            public void onResponse(Object o, int i) {
                String response = (String) o;
                resolveResponseData(response,false);
            }

        });


    }

    private void resolveResponseData(String response,boolean isVerification){

        if(TextUtils.isEmpty(response)){
            if(isVerification){
                view.getVerificationCodeFailed(MyApplication.getContext().getString(R.string.net_error));
            }else{
                view.getDataFiled(MyApplication.getContext().getString(R.string.net_error));
            }

            return;
        }
        try {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(jsonObject.getBoolean("success")){
                if(isVerification){
                    view.getVerificationCodeSuccess();
                }else{
                    view.getData("success");
                }

                return;
            }else{
                int failCode = jsonObject.getInt("failCode");
                String failMessage;
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
                    case 10063:
                        failMessage = MyApplication.getApplication().getString(R.string.faile_code_10063);
                        break;
                    case 10040:
                        failMessage = MyApplication.getApplication().getString(R.string.faile_code_10040);
                        break;
                    case 10062:
                        failMessage = MyApplication.getApplication().getString(R.string.faile_code_10062);
                        break;
                    case 10006:
                        failMessage = MyApplication.getApplication().getString(R.string.faile_code_10006);
                        break;
                    case -2:
                        failMessage = MyApplication.getApplication().getString(R.string.faile_code_load_1);
                        break;
                    case -1:
                        failMessage = MyApplication.getApplication().getString(R.string.faile_code_load_2);
                        break;
                    case 10064:
                        failMessage = MyApplication.getApplication().getString(R.string.faile_code_10064);
                        break;
                    case 10065:
                        failMessage = MyApplication.getApplication().getString(R.string.faile_code_10065);
                        break;
                    default:
                        if(isVerification){
                            failMessage = MyApplication.getApplication().getString(R.string.get_verification_faile);
                        }else{
                            failMessage = MyApplication.getApplication().getString(R.string.regist_failed);
                        }
                        break;
                }
                if(isVerification){
                    view.getVerificationCodeFailed(failMessage);
                }else{
                    view.getDataFiled(failMessage);
                }
                return;
            }
        } catch (JSONException e) {
            if(isVerification){
                view.getVerificationCodeFailed(MyApplication.getContext().getString(R.string.net_error));
            }else{
                view.getDataFiled(MyApplication.getContext().getString(R.string.net_error));
            }
        }
    }

}
