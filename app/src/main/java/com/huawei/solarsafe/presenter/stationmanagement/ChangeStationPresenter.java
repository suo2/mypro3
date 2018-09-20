package com.huawei.solarsafe.presenter.stationmanagement;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.bean.stationmagagement.BindDevPvInfo;
import com.huawei.solarsafe.bean.stationmagagement.CamerasBean;
import com.huawei.solarsafe.bean.stationmagagement.CamerasInfo;
import com.huawei.solarsafe.bean.stationmagagement.ChangeStationBindInvsBean;
import com.huawei.solarsafe.bean.stationmagagement.ChangeStationBindInvsInfo;
import com.huawei.solarsafe.bean.stationmagagement.ChangeStationDevResultBean;
import com.huawei.solarsafe.bean.stationmagagement.ChangeStationDevResultInfo;
import com.huawei.solarsafe.bean.stationmagagement.ChangeStationDeviceBean;
import com.huawei.solarsafe.bean.stationmagagement.ChangeStationDeviceInfo;
import com.huawei.solarsafe.bean.stationmagagement.ChangeStationResultBean;
import com.huawei.solarsafe.bean.stationmagagement.ChangeStationResultInfo;
import com.huawei.solarsafe.bean.stationmagagement.DevCapByIdData;
import com.huawei.solarsafe.bean.stationmagagement.SaveCapMassage;
import com.huawei.solarsafe.model.stationmanagement.ChangeStationModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.StringCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.JSONReader;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.stationmanagement.changestationinfo.IChangeStationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Call;


/**
 * Created by p00229 on 2017/6/6.
 */

public class ChangeStationPresenter extends BasePresenter<IChangeStationView, ChangeStationModel> {
    private static final String TAG = "ChangeStationPresenter";

    public ChangeStationPresenter() {
        setModel(new ChangeStationModel());
    }

    public void requestStationUpdate(String params) {
        model.requestStationUpdate(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if(e.toString().contains("java.net.ConnectException")){
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }else {
                    ToastUtil.showMessage(e.toString());
                }
                if(view != null){
                    view.getData(null);
                }
            }

            @Override
            public void onResponse(Object response, int id) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                try {
                    Gson gson = new Gson();
                    ChangeStationResultBean changeStationResultBean = gson.fromJson(response.toString(), ChangeStationResultBean.class);
                    ChangeStationResultInfo changeStationResultInfo = new ChangeStationResultInfo();
                    changeStationResultInfo.setChangeStationResultBean(changeStationResultBean);
                    if (view != null) {
                        view.getData(changeStationResultInfo);
                    }
                } catch (JsonSyntaxException e) {
                    ChangeStationResultInfo changeStationResultInfo = new ChangeStationResultInfo();
                    Log.e(TAG, "onResponse: " + e.toString() );
                    if (view != null) {
                        view.getData(changeStationResultInfo);
                    }
                }
            }
        });
    }

    public void requestStationGetBindDev(Map<String, String> params) {
        model.requestGetBindDev(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if(e.toString().contains("java.net.ConnectException")){
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }else {
                    ToastUtil.showMessage(e.toString());
                }
                if(view != null){
                    view.getData(null);
                }
            }

            @Override
            public void onResponse(Object response, int id) {
                try {
                    Gson gson = new Gson();
                    ChangeStationDeviceBean changeStationDeviceBean = gson.fromJson(response.toString(), ChangeStationDeviceBean.class);
                    ChangeStationDeviceInfo changeStationDeviceInfo = new ChangeStationDeviceInfo();
                    changeStationDeviceInfo.setChangeStationDeviceBean(changeStationDeviceBean);
                    if (view != null) {
                        view.getData(changeStationDeviceInfo);
                    }
                } catch (JsonSyntaxException e) {
                    if (view != null) {
                        view.getData(null);
                    }
                }
            }
        });
    }

    public void requestStationUnBindDev(String params, final int tag) {
        model.requestStationUnbindDev(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if(e.toString().contains("java.net.ConnectException")){
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }else {
                    ToastUtil.showMessage(e.toString());
                }
                if(view != null){
                    view.getData(null);
                }
            }

            @Override
            public void onResponse(Object response, int id) {
                try {
                    Gson gson = new Gson();
                    ChangeStationDevResultBean changeStationDevResultBean = gson.fromJson(response.toString(), ChangeStationDevResultBean.class);
                    ChangeStationDevResultInfo changeStationDevResultInfo = new ChangeStationDevResultInfo();
                    changeStationDevResultInfo.setChangeStationDevResultBean(changeStationDevResultBean);
                    changeStationDevResultInfo.setPos(tag);
                    if (view != null) {
                        view.getData(changeStationDevResultInfo);
                    }
                } catch (JsonSyntaxException e) {
                    Log.e(TAG, "onResponse: " + e.toString() );
                }
            }
        });
    }

    public void requestUpdateBindDev(String params, final int tag) {
        model.requestUpdateBindDev(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                ToastUtil.showMessage(e.toString());
                if(view != null){
                    view.getData(null);
                }
            }

            @Override
            public void onResponse(Object response, int id) {
                JSONReader jsonReader = null;
                String data = null;
                try {
                    jsonReader = new JSONReader(new JSONObject(response.toString()));
                    Gson gson = new Gson();
                    ChangeStationDevResultBean changeStationDevResultBean = gson.fromJson(response.toString(), ChangeStationDevResultBean.class);
                    ChangeStationDevResultInfo changeStationDevResultInfo = new ChangeStationDevResultInfo();
                    changeStationDevResultInfo.setChangeStationDevResultBean(changeStationDevResultBean);
                    changeStationDevResultInfo.setPos(tag);
                    if (view != null) {
                        view.getData(changeStationDevResultInfo);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "onResponse: " + e.toString() );
                    if (view != null) {
                        data = jsonReader.getString("data");
                        if("-3".equals(data)){
                            ToastUtil.showMessage(MyApplication.getContext().getResources().getString(R.string.save_failed_to_license));
                        }else {
                            ToastUtil.showMessage(MyApplication.getContext().getResources().getString(R.string.save_failed));
                        }
                        view.getData(null);
                    }
                }
            }
        });
    }

    public void requestStationGetStationCameras(Map<String, String> params) {
        model.requestGetStationCameras(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if(e.toString().contains("java.net.ConnectException")){
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }else {
                    ToastUtil.showMessage(e.toString());
                }
                if(view != null){
                    view.getData(null);
                }
            }

            @Override
            public void onResponse(Object response, int id) {
                //解析照相机信息
                try {
                    Gson gson = new Gson();
                    CamerasBean camerasBean = gson.fromJson(response.toString(), CamerasBean.class);
                    CamerasInfo camerasInfo = new CamerasInfo();
                    camerasInfo.setCamerasBean(camerasBean);
                    if (view != null) {
                        view.getData(camerasInfo);
                    }
                } catch (JsonSyntaxException e) {
                    Log.e(TAG, "onResponse: " + e.toString() );
                }
            }
        });
    }

    public void requestUpdateStationCameras(String params) {
        model.requestUpdateStationCameras(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if(e.toString().contains("java.net.ConnectException")){
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }else {
                    ToastUtil.showMessage(e.toString());
                }
                if(view != null){
                    view.getData(null);
                }

            }

            @Override
            public void onResponse(Object response, int id) {
                try {
                    Gson gson = new Gson();
                    ChangeStationResultBean changeStationResultBean = gson.fromJson(response.toString(), ChangeStationResultBean.class);
                    ChangeStationResultInfo changeStationResultInfo = new ChangeStationResultInfo();
                    changeStationResultInfo.setChangeStationResultBean(changeStationResultBean);
                    if (view != null) {
                        view.getData(changeStationResultInfo);
                    }
                } catch (JsonSyntaxException e) {
                    Log.e(TAG, "onResponse: " + e.toString() );
                }
            }
        });
    }


    public void requestGetBindInvs(Map<String, String> params) {
        model.requestGetBindInvs(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if(e.toString().contains("java.net.ConnectException")){
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }else {
                    ToastUtil.showMessage(e.toString());
                }
                if(view != null){
                    view.getData(null);
                }
            }

            @Override
            public void onResponse(Object response, int id) {
                try {
                    Gson gson = new Gson();
                    ChangeStationBindInvsBean changeStationBindInvsBean = gson.fromJson(response.toString(), ChangeStationBindInvsBean.class);
                    ChangeStationBindInvsInfo changeStationBindInvsInfo = new ChangeStationBindInvsInfo();
                    changeStationBindInvsInfo.setChangeStationBindInvsBean(changeStationBindInvsBean);
                    if (view != null) {
                        view.getData(changeStationBindInvsInfo);
                    }
                } catch (JsonSyntaxException e) {
                    view.getData(null);
                    Log.e(TAG, "onResponse: " + e.toString() );
                }
            }
        });
    }


    public void requestUpdatePrice(String params) {
        model.requestUpdatePrice(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if(e.toString().contains("java.net.ConnectException")){
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }else {
                    ToastUtil.showMessage(e.toString());
                }
                if(view != null){
                    view.getData(null);
                }
            }

            @Override
            public void onResponse(Object response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    boolean success = jsonObject.getBoolean("success");
                    ResultInfo resultInfo = new ResultInfo();
                    resultInfo.setSuccess(success);
                    if (view != null) {
                        view.getData(resultInfo);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                }
            }
        });
    }
    public void requestDevCapData(Map<String, String> params) {
        model.requestDevCapBayId(params, new CommonCallback(DevCapByIdData.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "request ListDevInfo failed " + e);
                if(e.toString().contains("java.net.ConnectException")){
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
                if(view != null){
                    view.getData(null);
                }
            }
            @Override
            public void onResponse(BaseEntity response, int id) {
                if(view != null){
                    if (response == null) {
                        view.getData(null);
                        return;
                    }
                    view.getData(response);
                }
            }
        });
    }
    public void saveDevCapData(String params) {
        model.saveDevCap(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if(e.toString().contains("java.net.ConnectException")){
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }else {
                    ToastUtil.showMessage(e.toString());
                }
                if(view != null){
                    view.getData(null);
                }
            }

            @Override
            public void onResponse(Object response, int id) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String message = jsonObject.getString("message");
                    boolean success = jsonObject.getBoolean("success");

                    SaveCapMassage capMassage = new SaveCapMassage();
                    capMassage.setMessage(message);
                    capMassage.setSuccess(success);
                    view.getData(capMassage);
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: "+e.getMessage() );
                }
            }
        });
    }
    public void queryDevPVInfo(String params) {
        model.queryDevCapPVInfo(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if(e.toString().contains("java.net.ConnectException")){
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }else {
                    ToastUtil.showMessage(e.toString());
                }
                if(view != null){
                    view.getData(null);
                }
            }

            @Override
            public void onResponse(Object response, int id) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Map<String,String> map = new HashMap<>();
                List<String> list = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONObject devCapMap = data.getJSONObject("devCapMap");
                    Iterator<String> keys = devCapMap.keys();
                    while(keys.hasNext()){
                        String next = keys.next();
                        map.put(next,devCapMap.getString(next));
                    }
                    if(!data.isNull("pvModuleDevList")){
                        JSONArray pvModuleDevList = data.getJSONArray("pvModuleDevList");
                        if(pvModuleDevList != null){
                            for (int i = 0; i < pvModuleDevList.length(); i++) {
                                list.add(pvModuleDevList.get(i).toString());
                            }
                        }
                    }
                    BindDevPvInfo bindDev = new BindDevPvInfo();
                    bindDev.setIdList(list);
                    bindDev.setMap(map);
                    view.getData(bindDev);
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                }
            }
        });
    }
}
