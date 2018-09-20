package com.huawei.solarsafe.presenter.devicemanagement;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.bean.ServerRet;
import com.huawei.solarsafe.bean.common.LogCallBack;
import com.huawei.solarsafe.bean.common.RetMsg;
import com.huawei.solarsafe.bean.device.CurrencySignalDataInfoList;
import com.huawei.solarsafe.bean.device.DevAlarmBean;
import com.huawei.solarsafe.bean.device.DevChandeDetailEntity;
import com.huawei.solarsafe.bean.device.DevChangeEntity;
import com.huawei.solarsafe.bean.device.DevDetailBean;
import com.huawei.solarsafe.bean.device.DevHistorySignalData;
import com.huawei.solarsafe.bean.device.DevList;
import com.huawei.solarsafe.bean.device.DevManagerGetSignalDataInfo;
import com.huawei.solarsafe.bean.device.DevParamsBean;
import com.huawei.solarsafe.bean.device.DevParamsInfo;
import com.huawei.solarsafe.bean.device.DevTypeListInfo;
import com.huawei.solarsafe.bean.device.HouseHoldInDevValInfo;
import com.huawei.solarsafe.bean.device.HouseHoldInDevValbean;
import com.huawei.solarsafe.bean.device.HouseholdRequestResult;
import com.huawei.solarsafe.bean.device.HouseholdSetResult;
import com.huawei.solarsafe.bean.device.InitModuleOptionInfo;
import com.huawei.solarsafe.bean.device.PinnetDevListStatus;
import com.huawei.solarsafe.bean.device.PvData;
import com.huawei.solarsafe.bean.device.PvDataInfo;
import com.huawei.solarsafe.bean.device.SignalData;
import com.huawei.solarsafe.bean.device.SmartLoggerInfo;
import com.huawei.solarsafe.bean.device.YhqErrorListBean;
import com.huawei.solarsafe.bean.device.YhqLocationBean;
import com.huawei.solarsafe.bean.device.YhqLocationInfo;
import com.huawei.solarsafe.bean.device.YhqRealTimeDataBean;
import com.huawei.solarsafe.bean.device.YhqShinengResultBean;
import com.huawei.solarsafe.bean.device.YhqShinengResultInfo;
import com.huawei.solarsafe.bean.stationmagagement.StationManagementListInfo;
import com.huawei.solarsafe.bean.stationmagagement.StationManegementList;
import com.huawei.solarsafe.model.devicemanagement.DevManagementModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.StringCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.JSONReader;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.devicemanagement.DevSwitchActivity;
import com.huawei.solarsafe.view.devicemanagement.IDevManagementView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by P00229 on 2017/2/21.
 */
public class DevManagementPresenter extends BasePresenter<IDevManagementView, DevManagementModel> {
    public static final String TAG = DevManagementPresenter.class.getSimpleName();

    public DevManagementPresenter() {
        setModel(new DevManagementModel());
    }

    public void doRequestDevList(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestDevList(params, new CommonCallback(DevList.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                super.onError(call, e, id);
                Log.e(TAG, "request ListDevInfo failed " + e);
                if (view != null) {
                    DevList devList = new DevList();
                    view.getData(devList);
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

    public void doRequestPinnetDevList(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestPinnetDevList(params, new CommonCallback(DevList.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                super.onError(call, e, id);
                Log.e(TAG, "request ListDevInfo failed " + e);
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

    public void doRequestPinnetDevListStatus(Map<String, List<String>> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng

        model.requestPinnetDevListStatus(params, new CommonCallback(PinnetDevListStatus.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                super.onError(call, e, id);
                Log.e(TAG, "request ListDevInfo failed " + e);
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

    public void doRequestHistroySignalData(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestDevHistorySingalData(params, new LogCallBack() {
            @Override
            protected void onFailed(Exception e) {
                if (view != null) {
                    view.getHistorySignalData(null);
                }
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
            }

            @Override
            protected void onSuccess(String data) {
                Gson gson = new Gson();
                try {
                    JSONReader jsonReader = new JSONReader(new JSONObject(data));
                    boolean success = jsonReader.getBoolean("success");
                    if (success) {
                        Type type = new TypeToken<RetMsg<List<DevHistorySignalData>>>() {
                        }.getType();
                        RetMsg<List<DevHistorySignalData>> retMsg = gson.fromJson(data, type);
                        List<DevHistorySignalData> dataList = retMsg.getData();
                        view.getHistorySignalData(dataList);
                    } else {
                        view.getHistorySignalData(null);
                    }
                } catch (JSONException e) {
                    view.getHistorySignalData(null);
                }

            }
        });
    }

    public void doRequestGetSignalData(Map<String, String> params, String tag, final String tag1) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestGetSignalData(params, new CommonCallback(DevManagerGetSignalDataInfo.class, tag) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (view != null) {
                    view.getData(null);
                }
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view != null) {
                    if (response == null) {
                        view.getData(null);
                        return;
                    }
                    response.setTag1(tag1);
                    view.getData(response);
                }
            }
        });
    }

    public void doRequestGetSignalData(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestGetSignalData(params, new CommonCallback(DevManagerGetSignalDataInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (view != null) {
                    view.getData(null);
                }
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view != null)
                    view.getData(response);
            }
        });
    }

    public void doRequestDevType(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestDevType(params, new CommonCallback(DevTypeListInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (view != null) {
                    view.getData(null);
                }
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view != null) {
                    view.getData(response);
                }
            }
        });
    }

    public void doRequestqueryDevHistoryData(Map<String, String> params, final String tag) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestqueryDevHistoryData(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                Log.e(TAG, "request DevHistoryBean failed " + e.getMessage());
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
            }

            @Override
            public void onResponse(Object response, int id) {
                if (response == null) {
                    return;
                }
                try {
                    JSONReader jsonReader = new JSONReader(new JSONObject((String) response));
                    JSONObject jsonObject = jsonReader.getJSONObject("data");
                    Iterator<String> keys = jsonObject.keys();
                    List<SignalData> list = new ArrayList<SignalData>();
                    int num = 0;
                    while (keys.hasNext()) {
                        num++;
                        if (num > 288) {
                            break;
                        }
                        SignalData signal = new SignalData();
                        signal.setTag(tag);
                        String key = keys.next();
                        JSONArray eList = jsonObject.getJSONArray(key);
                        if (!"-".equals(eList.get(0)) && !"null".equals(eList.get(0) + "")) {
                            signal.setTime(String.valueOf(eList.get(0)));
                        } else {
                            signal.setTime(String.valueOf(Float.MIN_VALUE));
                        }
                        if (!"-".equals(eList.get(1)) && !"null".equals(eList.get(1) + "")) {
                            signal.setSalesman(String.valueOf(eList.get(1)));
                        } else {
                            signal.setSalesman(String.valueOf(Float.MIN_VALUE));
                        }

                        if (eList.length() - 2 == 0) {
                            signal.setSignal1(String.valueOf(Float.MIN_VALUE));
                            signal.setSignal2(String.valueOf(Float.MIN_VALUE));
                            signal.setSignal3(String.valueOf(Float.MIN_VALUE));
                            signal.setSignal4(String.valueOf(Float.MIN_VALUE));
                            signal.setSignal5(String.valueOf(Float.MIN_VALUE));
                        }
                        if (eList.length() - 3 == 0) {
                            initData3(signal, eList);
                        }
                        if (eList.length() - 4 == 0) {
                            initData4(signal, eList);
                        }
                        if (eList.length() - 5 == 0) {
                            initData5(signal, eList);
                        }
                        if (eList.length() - 6 == 0) {
                            initData6(signal, eList);
                        }
                        if (eList.length() - 7 == 0) {
                            initData7(signal, eList);
                        }
                        list.add(signal);
                    }
                    view.getgetHistoryData(list);
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                }
            }
        });
    }

    private void initData3(SignalData signal, JSONArray eList) throws JSONException {
        signal.setSignal2(String.valueOf(Float.MIN_VALUE));
        signal.setSignal3(String.valueOf(Float.MIN_VALUE));
        signal.setSignal4(String.valueOf(Float.MIN_VALUE));
        signal.setSignal5(String.valueOf(Float.MIN_VALUE));
        if ("null".equals(eList.get(2) + "") || "-".equals(eList.get(2))) {
            signal.setSignal1(String.valueOf(Float.MIN_VALUE));
        } else {
            signal.setSignal1(String.valueOf(eList.get(2)));
        }
    }

    private void initData4(SignalData signal, JSONArray eList) throws JSONException {
        signal.setSignal3(String.valueOf(Float.MIN_VALUE));
        signal.setSignal4(String.valueOf(Float.MIN_VALUE));
        signal.setSignal5(String.valueOf(Float.MIN_VALUE));
        if ("null".equals(eList.get(2) + "") || "-".equals(eList.get(2))) {
            signal.setSignal1(String.valueOf(Float.MIN_VALUE));
        } else {
            signal.setSignal1(String.valueOf(eList.get(2)));
        }
        if ("null".equals(eList.get(3) + "") || "-".equals(eList.get(3))) {
            signal.setSignal2(String.valueOf(Float.MIN_VALUE));
        } else {
            signal.setSignal2(String.valueOf(eList.get(3)));
        }
    }

    private void initData5(SignalData signal, JSONArray eList) throws JSONException {
        signal.setSignal4(String.valueOf(Float.MIN_VALUE));
        signal.setSignal5(String.valueOf(Float.MIN_VALUE));
        if ("null".equals(eList.get(2) + "") || "-".equals(eList.get(2))) {
            signal.setSignal1(String.valueOf(Float.MIN_VALUE));
        } else {
            signal.setSignal1(String.valueOf(eList.get(2)));
        }
        if ("null".equals(eList.get(3) + "") || "-".equals(eList.get(3))) {
            signal.setSignal2(String.valueOf(Float.MIN_VALUE));
        } else {
            signal.setSignal2(String.valueOf(eList.get(3)));
        }
        if ("null".equals(eList.get(4) + "") || "-".equals(eList.get(4))) {
            signal.setSignal3(String.valueOf(Float.MIN_VALUE));
        } else {
            signal.setSignal3(String.valueOf(eList.get(4)));
        }
    }

    private void initData6(SignalData signal, JSONArray eList) throws JSONException {
        signal.setSignal5(String.valueOf(Float.MIN_VALUE));
        if ("null".equals(eList.get(2) + "") || "-".equals(eList.get(2))) {
            signal.setSignal1(String.valueOf(Float.MIN_VALUE));
        } else {
            signal.setSignal1(String.valueOf(eList.get(2)));
        }
        if ("null".equals(eList.get(3) + "") || "-".equals(eList.get(3))) {
            signal.setSignal2(String.valueOf(Float.MIN_VALUE));
        } else {
            signal.setSignal2(String.valueOf(eList.get(3)));
        }
        if ("null".equals(eList.get(4) + "") || "-".equals(eList.get(4))) {
            signal.setSignal3(String.valueOf(Float.MIN_VALUE));
        } else {
            signal.setSignal3(String.valueOf(eList.get(4)));
        }
        if ("null".equals(eList.get(5) + "") || "-".equals(eList.get(5))) {
            signal.setSignal4(String.valueOf(Float.MIN_VALUE));
        } else {
            signal.setSignal4(String.valueOf(eList.get(5)));
        }
    }

    private void initData7(SignalData signal, JSONArray eList) throws JSONException {
        if ("null".equals(eList.get(2) + "") || "-".equals(eList.get(2))) {
            signal.setSignal1(String.valueOf(Float.MIN_VALUE));
        } else {
            signal.setSignal1(String.valueOf(eList.get(2)));
        }
        if ("null".equals(eList.get(3) + "") || "-".equals(eList.get(3))) {
            signal.setSignal2(String.valueOf(Float.MIN_VALUE));
        } else {
            signal.setSignal2(String.valueOf(eList.get(3)));
        }
        if ("null".equals(eList.get(4) + "") || "-".equals(eList.get(4))) {
            signal.setSignal3(String.valueOf(Float.MIN_VALUE));
        } else {
            signal.setSignal3(String.valueOf(eList.get(4)));
        }
        if ("null".equals(eList.get(5) + "") || "-".equals(eList.get(5))) {
            signal.setSignal4(String.valueOf(Float.MIN_VALUE));
        } else {
            signal.setSignal4(String.valueOf(eList.get(5)));
        }
        if ("null".equals(eList.get(6) + "") || "-".equals(eList.get(6))) {
            signal.setSignal5(String.valueOf(Float.MIN_VALUE));
        } else {
            signal.setSignal5(String.valueOf(eList.get(6)));
        }
    }

    /**
     * 请求设备告警信息
     *
     * @param params
     */
    public void doRequestDevAlarm(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestDevAlarmData(params, new CommonCallback(DevAlarmBean.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                Log.e(TAG, "request DevAlarm failed " + e);
                if (view != null) {
                    view.getData(null);
                }
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view != null)
                    view.getData(response);
            }
        });
    }

    /**
     * 请求设备详细信息
     *
     * @param params
     */
    public void doRequestDevDetail(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestDevDetailData(params, new CommonCallback(DevDetailBean.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                Log.e(TAG, "request DevDetail failed " + e);
                if (view != null) {
                    view.getData(null);
                }
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view != null) {
                    view.getData(response);
                }
            }
        });
    }

    public void doRequestHouseholdInvParam(String params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestHouseholdInvParam(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                Log.e(TAG, "request HouseholdRequestResult failed " + e);
                if (view != null) {
                    view.getData(null);
                }
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
            }

            @Override
            public void onResponse(Object response, int id) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                try {
                    Gson gson = new Gson();
                    HouseholdRequestResult householdRequestResult = new HouseholdRequestResult();
                    HouseholdSetResult householdSetResult = gson.fromJson(response.toString(), HouseholdSetResult.class);
                    householdRequestResult.setHouseholdSetResult(householdSetResult);
                    householdRequestResult.setResultString(response.toString());
                    if (view != null) {
                        view.getData(householdRequestResult);
                    }
                } catch (JsonSyntaxException e) {
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    Log.e("JsonSyntaxException", e.getMessage());
                }

            }
        });
    }

    public void doRequestDevChangeDetail(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        if (view instanceof DevSwitchActivity) {
            ((DevSwitchActivity) view).showLoading();
        }
        model.requestDevChangeDetail(params, new CommonCallback(DevChandeDetailEntity.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                Log.e(TAG, "request DevChangeDetail failed " + e);
                if (view instanceof DevSwitchActivity) {
                    ((DevSwitchActivity) view).dismissLoading();
                }
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view == null) return;
                if (view instanceof DevSwitchActivity) {
                    ((DevSwitchActivity) view).dismissLoading();
                }
                if (response == null) {
                    return;
                }
                view.getData(response);
            }
        });
    }

    public void doDevChange(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        if (view instanceof DevSwitchActivity) {
            ((DevSwitchActivity) view).showLoading();
        }
        model.requestDevChange(params, new CommonCallback(DevChangeEntity.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                Log.e(TAG, "request DevChangeDetail failed " + e);
                if (view instanceof DevSwitchActivity) {
                    ((DevSwitchActivity) view).dismissLoading();
                }
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view == null) return;
                if (view instanceof DevSwitchActivity) {
                    ((DevSwitchActivity) view).dismissLoading();
                }
                if (response == null) {
                    return;
                }
                view.getData(response);
            }
        });
    }

    public void doQuerySmartLoggerInfo(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.ruerySmartLoggerInfo(params, new CommonCallback(SmartLoggerInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                Log.e(TAG, "request doQuerySmartLoggerInfo failed " + e);
                if (view != null) {
                    view.getData(null);
                }
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view != null) {
                    view.getData(response);
                }
            }
        });
    }

    public void doRequestInitModuleOption(String params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestInitModuleOption(params, new CommonCallback(InitModuleOptionInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (view != null) {
                    view.getData(null);
                }
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view != null) {
                    view.getData(response);
                }
            }
        });
    }

    public void doRequestGetDefaultModule(String params, final int tag) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestGetDefultModule(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (view != null) {
                    view.getData(null);
                }
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
            }

            @Override
            public void onResponse(Object response, int id) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                try {
                    Gson gson = new Gson();
                    PvData pvData = gson.fromJson(response.toString(), PvData.class);
                    if (pvData.isSuccess()) {
                        PvDataInfo pvDataInfo = new PvDataInfo();
                        pvDataInfo.setPvData(pvData);
                        pvDataInfo.setTag(tag + "");
                        if (view != null)
                            view.getData(pvDataInfo);
                    }
                } catch (JsonSyntaxException e) {
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    Log.e("JsonSyntaxException", e.toString());
                }

            }
        });
    }

    public void doRequestSaveModule(String params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestSaveModule(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (view != null) {
                    view.getData(null);
                }
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
            }

            @Override
            public void onResponse(Object response, int id) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                try {
                    ResultInfo resultInfo = new ResultInfo();
                    JSONReader jsonReader = new JSONReader(new JSONObject(response.toString()));
                    resultInfo.setSuccess(jsonReader.getBoolean("success"));
                    if (view != null) {
                        view.getData(resultInfo);
                    }
                } catch (JSONException e) {
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    Log.e("JsonSyntaxException", e.toString());
                }

            }
        });
    }

    public void doRueryTwoPassWordData(final Context context, Map<String, String> params, final String esnCode) {
        final AlertDialog.Builder builder2 = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        builder2.setTitle(context.getString(R.string.prompt));
        builder2.setMessage(context.getString(R.string.verify_password_faild));
        builder2.setPositiveButton(context.getString(R.string.determine), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.rueryTwoPassWordData(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                builder2.show();
            }

            @Override
            public void onResponse(Object response, int id) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                JSONReader jsonReader = null;
                boolean success = false;
                String auth = null;
                try {
                    jsonReader = new JSONReader(new JSONObject(response.toString()));
                    success = jsonReader.getBoolean("success");
                    JSONObject jsonObject = jsonReader.getJSONObject("data");
                    auth = jsonObject.getString("auth");
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能打印d和v级别的日志    【修改人】：zhaoyufeng
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                }
                if (success) {
                    Map<String, String> esnMap = new HashMap<String, String>();
                    esnMap.put("esnCode", esnCode);
                    esnMap.put("auth", auth);
                    doRueryRestartData(context, esnMap);
                } else {
                    builder2.show();
                }
            }
        });
    }

    public void doRueryUserOperation(final Context context, Map<String, String> params, final String devId, final boolean yhqShineng) {
        final AlertDialog.Builder builder2 = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        builder2.setTitle(context.getString(R.string.prompt));
        builder2.setMessage(context.getString(R.string.verify_password_faild));
        builder2.setPositiveButton(context.getString(R.string.determine), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.rueryTwoPassWordData(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                builder2.show();
            }

            @Override
            public void onResponse(Object response, int id) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                JSONReader jsonReader;
                boolean success = false;
                String auth = null;
                try {
                    jsonReader = new JSONReader(new JSONObject(response.toString()));
                    success = jsonReader.getBoolean("success");
                    JSONObject jsonObject = jsonReader.getJSONObject("data");
                    auth = jsonObject.getString("auth");
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                }
                if (success) {
                    if (!yhqShineng) {
                        //由禁能变为使能
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("devId", devId);
                        params.put("operateVal", "1");
                        params.put("auth", auth);
                        doRequestYHQShineng(params);
                    } else {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("devId", devId);
                        params.put("operateVal", "0");
                        params.put("auth", auth);
                        doRequestYHQShineng(params);
                    }
                } else {
                    builder2.show();
                }
            }
        });
    }

    public void doRueryRestartData(final Context context, Map<String, String> params) {
        final AlertDialog.Builder builder4 = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        builder4.setTitle(context.getString(R.string.prompt));
        builder4.setMessage(context.getString(R.string.operation_failed));
        builder4.setPositiveButton(context.getString(R.string.determine), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.rueryRestartData(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能打印d和v级别的日志    【修改人】：zhaoyufeng
                Log.e("doRueryRestartData", e.toString());
                builder4.show();
            }

            @Override
            public void onResponse(Object response, int id) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                JSONReader jsonReader;
                boolean success = false;
                try {
                    jsonReader = new JSONReader(new JSONObject(response.toString()));
                    success = jsonReader.getBoolean("success");
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                }
                if (success) {
                    AlertDialog.Builder builder3 = new AlertDialog.Builder(context, R.style.MyDialogTheme);
                    builder3.setTitle(context.getString(R.string.prompt));
                    builder3.setMessage(context.getString(R.string.operation_succeeded));
                    builder3.setPositiveButton(context.getString(R.string.determine), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder3.show();
                } else {
                    builder4.show();
                }
            }
        });
    }

    public void doRequestGetDevParams(String params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestGetDevParams(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (view != null) {
                    view.getData(null);
                }
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
            }

            @Override
            public void onResponse(Object response, int id) {
                Gson gson = new Gson();
                DevParamsBean devParamsBean = gson.fromJson(response.toString(), DevParamsBean.class);
                DevParamsInfo devParamsInfo = new DevParamsInfo();
                devParamsInfo.setDevParamsBean(devParamsBean);
                if (view != null) {
                    view.getData(devParamsInfo);
                }
            }
        });
    }

    public void doRequestGetDevParamsVal(String params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestHouseholdInvParamVal(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (view != null) {
                    view.getData(null);
                }
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
            }

            @Override
            public void onResponse(Object response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    if (jsonObject.get("data") instanceof Boolean) {
                        view.getData(new HouseHoldInDevValInfo());
                        return;
                    }
                    Gson gson = new Gson();
                    HouseHoldInDevValbean houseHoldInDevValbean = gson.fromJson(response.toString(), HouseHoldInDevValbean.class);
                    HouseHoldInDevValInfo houseHoldInDevValInfo = new HouseHoldInDevValInfo();
                    houseHoldInDevValInfo.setHouseHoldInDevValbean(houseHoldInDevValbean);
                    if (view != null) {
                        view.getData(houseHoldInDevValInfo);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: " + e.toString());
                }
            }
        });
    }

    public void doRequestYHQLocation(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestYHQLocation(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                Log.e(TAG, "request HouseholdRequestResult failed " + e);
                if (view != null) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.pnloger_et_site));
                    view.getData(null);
                }
            }

            @Override
            public void onResponse(Object response, int id) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    if (jsonObject.getBoolean("success")) {
                        Gson gson = new Gson();
                        YhqLocationBean yhqLocationBean = gson.fromJson(response.toString(), YhqLocationBean.class);
                        YhqLocationInfo yhqLocationInfo = new YhqLocationInfo();
                        yhqLocationInfo.setYhqLocationBean(yhqLocationBean);
                        if (view != null) {
                            view.getData(yhqLocationInfo);
                        }
                    } else {
                        ToastUtil.showMessage(MyApplication.getContext().getString(R.string.pnloger_et_site));
                        if (view != null)
                            view.getData(null);
                    }
                } catch (JsonSyntaxException e) {
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    Log.e("JsonSyntaxException", e.toString());
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: " + e.getMessage()
                    );
                }

            }
        });
    }

    public void doRequestYHQShineng(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestYHQShineng(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                Log.e(TAG, "request HouseholdRequestResult failed " + e);
                if (view != null) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.operation_failed));
                    view.getData(null);
                }
            }

            @Override
            public void onResponse(Object response, int id) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    if (jsonObject.getBoolean("success")) {
                        Gson gson = new Gson();
                        YhqShinengResultBean yhqShinengResultBean = gson.fromJson(response.toString(), YhqShinengResultBean.class);
                        YhqShinengResultInfo yhqShinengResultInfo = new YhqShinengResultInfo();
                        yhqShinengResultInfo.setYhqShinengResultBean(yhqShinengResultBean);
                        if (view != null) {
                            view.getData(yhqShinengResultInfo);
                        }
                    } else {
                        ToastUtil.showMessage(MyApplication.getContext().getString(R.string.operation_failed));
                        if (view != null)
                            view.getData(null);
                    }
                } catch (JsonSyntaxException e) {
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    Log.e("JsonSyntaxException", e.toString());
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                }

            }
        });
    }

    /**
     * 优化器实时数据
     *
     * @param params
     */
    public void doRequestYHQRealTimeData(Map<String, String> params, final List<String> devIds) {
        model.requestYHQRealTimeData(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (view != null) {
                    view.getData(null);
                }
                if (!e.getMessage().contains("403")){
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
            }

            @Override
            public void onResponse(Object response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    YhqRealTimeDataBean yhqRealTimeDataBean = new YhqRealTimeDataBean();
                    yhqRealTimeDataBean.setSuccess(jsonObject.getBoolean("success"));
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    YhqRealTimeDataBean.DataBean dataBean = new YhqRealTimeDataBean.DataBean();
                    ArrayList<YhqRealTimeDataBean.DataBean.DevIdBean> devIdBeens = new ArrayList<YhqRealTimeDataBean.DataBean.DevIdBean>();
                    Gson  gson = new Gson();;
                    for (int i = 0; i < devIds.size(); i++) {
                        String id1 = devIds.get(i);
                        JSONObject jsonObject2 = jsonObject1.getJSONObject(id1);
                        YhqRealTimeDataBean.DataBean.DevIdBean devIdBean = gson.fromJson(jsonObject2.toString(),YhqRealTimeDataBean.DataBean.DevIdBean.class);
                        devIdBean.setDevId(id1);
                        devIdBeens.add(devIdBean);
                    }
                    dataBean.setDevIdBean(devIdBeens);
                    yhqRealTimeDataBean.setDataBean(dataBean);

                    if (view != null) {
                        view.getData(yhqRealTimeDataBean);
                    }
                } catch (JsonSyntaxException|JSONException e) {
                    Log.e(TAG, "request DevHistoryBean failed " + e);
                }
            }
        });
    }

    /**
     * 优化器故障列表
     *
     * @param params
     */
    public void doRequestYHQErrorList(Map<String, String> params) {
        model.requestYHQErrorList(params, new CommonCallback(YhqErrorListBean.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                Log.e(TAG, "request DevHistoryBean failed " + e);
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

    public void doRequestOptHistoryData(Map<String, String> params, final String tag) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestOptHistoryData(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                Log.e(TAG, "request DevHistoryBean failed " + e);
                if (view != null) {
                    view.getOptHistoryData(null);
                }
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
            }

            @Override
            public void onResponse(Object response, int id) {
                if (response == null) {
                    if (view != null)
                        view.getOptHistoryData(null);
                } else {
                    try {
                        JSONReader jsonReader = new JSONReader(new JSONObject((String) response));
                        JSONObject jsonObject = jsonReader.getJSONObject("data");
                        Iterator<String> keys = jsonObject.keys();
                        List<SignalData> list = new ArrayList<SignalData>();
                        List<List<SignalData>> listData = new ArrayList<>();
                        int num = 0;
                        //第一层动态key
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONObject jsonObject1 = jsonObject.getJSONObject(key);
                            Iterator<String> keysValue = jsonObject1.keys();
                            //第二次动态key
                            while (keysValue.hasNext()) {
                                num++;
                                if (num > 288) {
                                    break;
                                }
                                SignalData signal = new SignalData();
                                signal.setTag(tag);
                                String key1 = keysValue.next();
                                JSONArray eList = jsonObject1.getJSONArray(key1);
                                if (eList.length() != 0) {
                                    if ("null".equals(eList.get(0) + "") || "-".equals(eList.get(0))) {
                                        signal.setSignal1(String.valueOf(Float.MIN_VALUE));
                                    } else {
                                        signal.setSignal1(String.valueOf(eList.get(0)));
                                    }
                                }
                                list.add(signal);
                            }
                            listData.add(list);
                        }
                        if (view != null)
                            view.getOptHistoryData(listData);
                    } catch (JSONException e) {
                        if (view != null)
                            view.getOptHistoryData(null);
                        Log.e(TAG, "onResponse: " + e.getMessage());
                    }
                }
            }
        });
    }

    public void doRequestGetDevsSignalData(HashMap<String, String> params, final String tag) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestYHQRealTimeData(params, new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                Log.e(TAG, "request DevHistoryBean failed " + e);
                if (view != null) {
                    view.getData(null);
                }
                if (!e.getMessage().contains("403")){
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
            }

            @Override
            public void onResponse(Object response, int id) {
                if (response == null) {
                    if (view != null)
                        view.getData(null);
                } else {
                    try {
                        JSONReader jsonReader = new JSONReader(new JSONObject((String) response));
                        JSONObject jsonObject = jsonReader.getJSONObject("data");
                        Iterator<String> keys = jsonObject.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONObject jsonObject1 = jsonObject.getJSONObject(key);
                            Gson gson = new Gson();
                            DevManagerGetSignalDataInfo devManagerGetSignalDataInfo = gson.fromJson(jsonObject1.toString(), DevManagerGetSignalDataInfo.class);
                            devManagerGetSignalDataInfo.setTag(tag);
                            devManagerGetSignalDataInfo.setDevManagerGetSignalDataInfo(devManagerGetSignalDataInfo);
                            devManagerGetSignalDataInfo.setServerRet(ServerRet.OK);
                            if (view != null)
                                view.getData(devManagerGetSignalDataInfo);
                        }
                    } catch (JSONException e) {
                        if (view != null)
                            view.getData(null);
                        Log.e(TAG, "onResponse: " + e.getMessage());
                    }
                }
            }
        });
    }

    public void doRequestCurrencySignalData(Map<String, String> params) {
        model.requestGetSignalData(params, new CommonCallback(CurrencySignalDataInfoList.class) {
            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view != null)
                    view.getData(response);
            }
        });
    }

    /**
     * 刷新组串式逆变器下联设备
     *
     * @param params
     */
    public void doRequestRefreshInverter(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestRefreshInverter(params, new CommonCallback(ResultInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
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


    public void requestStationList(String param, int page, int pageSize) {
        Map<String, String> params = new HashMap<>();
        params.put("stationName", param);
        params.put("page", "" + page);
        params.put("pageSize", "" + pageSize);
        model.requestStationList(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                if (view != null) {
                    view.getData(null);
                }
            }

            @Override
            public void onResponse(Object response, int id) {
                try {
                    Gson gson = new Gson();
                    StationManegementList stationManegementList = gson.fromJson(response.toString(), StationManegementList.class);
                    StationManagementListInfo stationManagementListInfo = new StationManagementListInfo();
                    if (view != null && stationManegementList.isSuccess()) {
                        stationManagementListInfo.setStationManegementList(stationManegementList);
                        view.getData(stationManagementListInfo);
                    } else {
                        if (view != null) {
                            view.getData(stationManagementListInfo);
                        }
                    }
                } catch (Exception e) {
                    if (view != null) {
                        StationManagementListInfo stationManagementListInfo = new StationManagementListInfo();
                        view.getData(stationManagementListInfo);
                    }
                }
            }
        });
    }
}