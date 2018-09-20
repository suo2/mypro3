package com.huawei.solarsafe.presenter.stationmanagement;

import android.util.Log;

import com.google.gson.Gson;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.bean.common.LogCallBack;
import com.huawei.solarsafe.bean.stationmagagement.CreateStationArgs;
import com.huawei.solarsafe.bean.stationmagagement.DevInfo;
import com.huawei.solarsafe.bean.stationmagagement.GridPrice;
import com.huawei.solarsafe.bean.stationmagagement.GridPriceInfo;
import com.huawei.solarsafe.bean.stationmagagement.StationManagementListInfo;
import com.huawei.solarsafe.bean.stationmagagement.StationManegementList;
import com.huawei.solarsafe.bean.stationmagagement.SubDev;
import com.huawei.solarsafe.model.stationmanagement.CreateStationModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.StringCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.stationmanagement.ICreateStationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;


/**
 * Create Date: 2017/4/24
 * Create Author: P00171
 * Description :
 */
public class CreateStationPresenter extends BasePresenter<ICreateStationView, CreateStationModel> {
    private static final String TAG = "CreateStationPresenter";

    public CreateStationPresenter() {
        setModel(new CreateStationModel());
    }

    public void requestNameRepeat(String stationName) {
        model.requestNameRepeat(stationName, new CommonCallback(ResultInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
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

    public void requestCreateStation(CreateStationArgs createStationArgs) {
        model.requestCreateStation(createStationArgs, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "Request  error", e);
                if (view != null) {
                    view.createStationFail(-7, null);
                }
            }

            @Override
            public void onResponse(Object response, int id) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    int failCode = jsonObject.getInt("failCode");
                    String data = jsonObject.getString("data");
                    boolean success = jsonObject.getBoolean("success");

                    if (view != null) {
                        if (success) {
                            view.createStationSuccess();
                        }else {
                            view.createStationFail(failCode, data);
                        }
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                }
            }
        });
    }

    public void requestGridPreice(Map<String, String> params) {
        model.requestGridPrice(params, new StringCallback() {
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
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                try {
                    Gson gson = new Gson();
                    GridPrice gridPrice = gson.fromJson(response.toString(), GridPrice.class);
                    if (view != null && gridPrice.isSuccess()) {
                        GridPriceInfo gridPriceInfo = new GridPriceInfo();
                        gridPriceInfo.setGridPrice(gridPrice);
                        view.getData(gridPriceInfo);
                    }
                } catch (Exception e) {
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    Log.e("JsonSyntaxException", e.toString());
                }

            }
        });
    }

    public void requestStationList(String params) {
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
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                try {
                    Gson gson = new Gson();
                    StationManegementList stationManegementList = gson.fromJson(response.toString(), StationManegementList.class);
                    if (view != null && stationManegementList.isSuccess()) {
                        StationManagementListInfo stationManagementListInfo = new StationManagementListInfo();
                        stationManagementListInfo.setStationManegementList(stationManegementList);
                        view.getData(stationManagementListInfo);
                    } else {
                        if (view != null) {
                            view.getData(null);
                        }
                    }
                } catch (Exception e) {
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    Log.e("JsonSyntaxException", e.toString());
                }

            }
        });
    }

    public void uploadStationFile(String filePath, String stationName) {
        if (filePath == null) {
            ToastUtil.showMessage(R.string.select_image);
            return;
        }
        File file = new File(filePath);
        model.uploadStationImg(file, stationName, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (view != null) {
                    view.uploadResult(false, null);
                }
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能打印d和v级别的日志    【修改人】：zhaoyufeng
                Log.e(TAG, "onError: uploadStationFile", e);
            }

            @Override
            public void onResponse(Object response, int id) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                try {
                    JSONObject jsonOb = new JSONObject(response.toString());
                    boolean success = jsonOb.getBoolean("success");
                    if (view != null) {
                        if (success) {
                            view.uploadResult(true, jsonOb.getString("data"));
                        } else {
                            view.uploadResult(false, jsonOb.getString("data"));
                        }
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse:uploadStationFile ", e);
                }
            }
        });
    }

    public void getDevByEsn(String esn) {
        model.getDevByEsn(esn, new CommonCallback(DevInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                } else {
                    ToastUtil.showMessage(e.toString());
                }
                if (view != null) {
                    view.getData(null);
                }
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                DevInfo devInfo = (DevInfo) response;
                view.getData(devInfo);
            }
        });
    }
    public void getStationDevByEsn(String esn) {
        model.getStationDevByEsn(esn, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if (view != null) {
                    view.getData(null);
                }
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能打印d和v级别的日志    【修改人】：zhaoyufeng
                Log.e(TAG, "onError: uploadStationFile", e);
            }

            @Override
            public void onResponse(Object response, int i) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                DevInfo devInfo = new DevInfo();
                SubDev subDev = new SubDev();
                try {
                    JSONObject jsonOb = new JSONObject(response.toString());
                    boolean success = jsonOb.getBoolean("success");
                    if (view != null) {
                        if (success) {
                            devInfo.setExits(true);
                            subDev.setStationCode(jsonOb.getString("data"));
                            devInfo.setDev(subDev);
                        } else {
                            int failCode = jsonOb.getInt("failCode");
                            devInfo.setExits(false);
                            //TODO
                        }
                        view.getData(devInfo);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse:uploadStationFile ", e);
                }
            }
        });
    }

    //请求License
    public void queryLicenseRes() {
        final LocalData localData = LocalData.getInstance();
        Map<String, String> params = new HashMap<>();
        model.queryLicenseRes(params, new LogCallBack() {
            @Override
            protected void onFailed(Exception e) {

            }

            @Override
            protected void onSuccess(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.getBoolean("success")) {
                        String pam = jsonObject.getJSONObject("data").getString("PAM");
                        if ("1".equals(pam)) {
                            localData.setSupportPoor(true);
                        } else {
                            localData.setSupportPoor(false);
                        }
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "onSuccess: " + e.getMessage());
                }
            }
        });
    }
}
