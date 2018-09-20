package com.huawei.solarsafe.presenter.personal;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.bean.update.UpDateDetailBean;
import com.huawei.solarsafe.bean.update.UpdateDetailInfo;
import com.huawei.solarsafe.bean.update.UpdateDetailVersionInfo;
import com.huawei.solarsafe.bean.update.UpdateListBean;
import com.huawei.solarsafe.bean.update.UpdateListInfo;
import com.huawei.solarsafe.model.personal.DeviceUpdateModel;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.net.StringCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.view.personal.IDeviceUpdateView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by P00517 on 2017/4/10.
 */

public class DeviceUpdateListPresenter extends BasePresenter<IDeviceUpdateView, DeviceUpdateModel> implements IDeviceUpdateListPresenter {

    public DeviceUpdateListPresenter() {
        setModel(new DeviceUpdateModel());
    }

    @Override
    public void dorequestDeviceUpdateList(HashMap<String, Integer> param) {
        model.requestDeviceUpdateList(param, new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        if (view != null) {
                            if (e.getMessage().contains("Socket closed")) {
                                view.requestFailed("");
                            } else {
                                view.requestFailed(NetRequest.NETERROR);
                            }
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {
                        String body = response.toString();
                        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(body);
                            UpdateListBean listBean = new UpdateListBean();
                            boolean success = jsonObject.getBoolean("success");
                            listBean.setSuccess(success);
                            JSONObject data = jsonObject.getJSONObject("data");
                            if (success) {
                                listBean.setPageCount(data.getInt("pageCount"));
                                listBean.setTotal(data.getInt("total"));
                                Gson gson = new Gson();
                                Type type = new TypeToken<List<UpdateListInfo>>() {
                                }.getType();
                                List<UpdateListInfo> updateBeenList = gson.fromJson(data.getString("list"), type);
                                listBean.setUpdateBeenList(updateBeenList);
                                if (view != null){
                                    view.getData(listBean);
                                }
                            } else {
                                if (data != null) {
                                    if (view != null){
                                        view.requestFailed(data.getString("message"));
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "onResponse: " + e.getMessage());
                            view.requestFailed(MyApplication.getContext().getString(R.string.req_fail));
                        }
                    }
                }

        );
    }

    @Override
    public void doRequestDeviceUpdateDetail(HashMap<String, Long> param) {
        model.requestDeviceUpdateDetail(param, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (view != null) {
                    if (e.getMessage().contains("Socket closed")) {
                        view.requestFailed("");
                    } else {
                        view.requestFailed(NetRequest.NETERROR);
                    }
                }
            }

            @Override
            public void onResponse(Object response, int id) {
                String body = response.toString();
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(body);
                    UpDateDetailBean detailBean = new UpDateDetailBean();
                    boolean success = jsonObject.getBoolean("success");
                    detailBean.setSuccess(success);
                    JSONObject dataJson = jsonObject.getJSONObject("data");
                    if (success) {
                        Map<UpdateDetailInfo, UpdateDetailVersionInfo> data = new HashMap<>();
                        Gson gson = new Gson();
                        UpdateDetailInfo updateDetailInfo = gson.fromJson(dataJson.getString("detail"), UpdateDetailInfo.class);
                        UpdateDetailVersionInfo updateDetailVersionInfo = gson.fromJson(dataJson.getString("version"), UpdateDetailVersionInfo.class);
                        data.put(updateDetailInfo, updateDetailVersionInfo);
                        detailBean.setData(data);
                        if (view != null){
                            view.getData(detailBean);
                        }
                    } else {
                        if (dataJson != null) {
                            if (view != null){
                                view.requestFailed(dataJson.getString("message"));
                            }
                        }
                    }
                } catch (JSONException e) {
                    view.requestFailed(MyApplication.getContext().getString(R.string.req_fail));
                }
            }
        });
    }

    @Override
    public void doRequestDeviceUpdateStatus(HashMap<String, Long> param) {
        model.requestDeviceUpdateStatus(param, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (e.getMessage().contains("Socket closed")) {
                    if (view != null) {
                        view.requestFailed("");
                    }
                } else {
                    if (view != null) {
                        view.requestFailed(NetRequest.NETERROR);
                    }
                }
            }

            @Override
            public void onResponse(Object response, int id) {
                String body = response.toString();
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                JSONObject jsonObject;
                try {
                    ResultInfo resultInfo = new ResultInfo();
                    jsonObject = new JSONObject(body);
                    boolean success = jsonObject.getBoolean("success");
                    resultInfo.setSuccess(jsonObject.getBoolean("success"));
                    if (!success) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        if (data != null) {
                            view.requestFailed(data.getString("message"));
                            return;
                        }
                    }
                    view.getData(resultInfo);
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                    view.requestFailed(MyApplication.getContext().getString(R.string.req_fail));
                }
            }
        });
    }

    private static final String TAG = "DeviceUpdateListPresent";

}
