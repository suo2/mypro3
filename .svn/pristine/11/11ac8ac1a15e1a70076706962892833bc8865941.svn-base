package com.huawei.solarsafe.presenter.maintaince.ivcurve;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.bean.ivcurve.AllIVData;
import com.huawei.solarsafe.bean.ivcurve.ComparedBean;
import com.huawei.solarsafe.bean.ivcurve.FaultListBean;
import com.huawei.solarsafe.bean.ivcurve.FaultStaticsBean;
import com.huawei.solarsafe.bean.ivcurve.IVCurveBean;
import com.huawei.solarsafe.bean.ivcurve.IVCurveInfo;
import com.huawei.solarsafe.bean.ivcurve.IVFailCauseInfo;
import com.huawei.solarsafe.bean.ivcurve.IvcurveInterverInfo;
import com.huawei.solarsafe.bean.ivcurve.StationFaultList;
import com.huawei.solarsafe.bean.ivcurve.StationListInfo;
import com.huawei.solarsafe.bean.ivcurve.StringIVLists;
import com.huawei.solarsafe.bean.ivcurve.TaskResultBean;
import com.huawei.solarsafe.model.maintain.ivcurve.IVcurveModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.StringCallback;
import com.huawei.solarsafe.presenter.BasePresenter;

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
 * Created by P00507
 * on 2017/7/19.
 */

public class CreatIVNewTeskPresenter extends BasePresenter<CreatIVNewTeskView, IVcurveModel> {
    public static final String TAG = CreatIVNewTeskPresenter.class.getSimpleName();

    public CreatIVNewTeskPresenter() {
        setModel(new IVcurveModel());
    }

    /**
     * 获取扫描任务列表
     *
     * @param param
     */
    public void requestListTask(Map<String, String> param) {
        model.requestListTask(param, new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        getErrorInfo(e);
                    }

                    @Override
                    public void onResponse(Object response, int id) {
                        String body = response.toString();
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(body);
                            IVCurveBean ivCurveBean = new IVCurveBean();
                            boolean success = jsonObject.getBoolean("success");
                            ivCurveBean.setSuccess(success);
                            int failCode = jsonObject.getInt("failCode");
                            ivCurveBean.setFailCode(failCode);
                            if (success) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                if (jsonObject1 != null && jsonObject1.length() > 0) {
                                    Gson gson = new Gson();
                                    Type type = new TypeToken<List<IVCurveInfo>>() {
                                    }.getType();
                                    List<IVCurveInfo> ivCurveInfos = gson.fromJson(jsonObject1.getString("list"), type);
                                    ivCurveBean.setList(ivCurveInfos);
                                    ivCurveBean.setPageCount(jsonObject1.getInt("pageCount"));
                                    ivCurveBean.setPageNo(jsonObject1.getInt("pageNo"));
                                    ivCurveBean.setTotal(jsonObject1.getInt("total"));
                                    ivCurveBean.setPageSize(jsonObject1.getInt("pageSize"));
                                }
                            }
                            if (view != null) {
                                view.getData(ivCurveBean);
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "onResponse: " + e.getMessage());
                        }
                    }
                }
        );
    }

    public void requestIVDevList(Map<String, String> param) {
        model.requestCheckDev(param, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                getErrorInfo(e);
            }

            @Override
            public void onResponse(Object response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    if (jsonObject.getBoolean("success")) {
                        String msg = jsonObject.getString("data");
                        view.getData(msg);
                    } else {
                        view.getData("CheckFailed");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                }
            }
        });
    }

    public void creatTaskIV(Map<String, String> param) {
        model.creatTaskIV(param, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                getErrorInfo(e);
            }

            @Override
            public void onResponse(Object response, int id) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    if (jsonObject.getBoolean("success")) {
                        view.getData("CreatIVNewTeskSuccess");
                    } else {
                        view.getData("CreatIVNewTeskFailed");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                }
            }
        });
    }

    public void requestGetStationFaultList(Map<String, String> param) {
        model.requestStationFault(param, new CommonCallback(StationFaultList.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                getErrorInfo(e);
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response == null) {
                    if (view != null) {
                        view.getDataFailed(id + "");
                    }
                    return;
                }
                if (view != null) {
                    view.getData(response);
                }
            }
        });

    }

    /**
     * 停止扫描任务
     *
     * @param taskId
     */
    public void requestStopScanTask(long taskId) {
        model.requestStopTask(taskId, new CommonCallback(ResultInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                getErrorInfo(e);
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view != null) {
                    view.getData(response);
                }
            }
        });
    }

    /**
     * 获取组串基本信息
     */
    public void requestBasicInfor(Map<String, String> param) {
        model.requestBasicInfor(param, new CommonCallback(IvcurveInterverInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                getErrorInfo(e);
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response == null) {
                    view.getDataFailed(id + "");
                    return;
                }
                if (view != null) {
                    view.getData(response);
                }
            }
        });
    }

    /**
     * 获取组串的iv曲线
     */
    public void requestStringIV(Map<String, String> param) {
        model.requestStringIV(param, new CommonCallback(StringIVLists.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                getErrorInfo(e);
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response == null) {
                    view.getDataFailed(id + "");
                    return;
                }
                if (view != null) {
                    view.getData(response);
                }
            }
        });

    }

    /**
     * 获取iv曲线对比数据
     */
    public void requestIVCompared(Map<String, List<ComparedBean>> param) {
        model.requestIVCompared(param, new CommonCallback(AllIVData.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                getErrorInfo(e);
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response == null) {
                    view.getDataFailed(id + "");
                    return;
                }
                if (view != null) {
                    view.getData(response);
                }
            }
        });

    }

    /**
     * 获取故障任务列表
     *
     * @param param
     */
    public void requestFaultList(Map<String, String> param) {
        model.requestListFault(param, new CommonCallback(FaultListBean.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                getErrorInfo(e);
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response == null) {
                    view.getDataFailed(id + "");
                    return;
                }
                if (view != null) {
                    view.getData(response);
                }
            }
        });

    }

    /**
     * 获取任务电站列表
     *
     * @param taskId
     */
    public void requestTaskStationList(long taskId) {
        model.requestStationList(taskId, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                getErrorInfo(e);
            }

            @Override
            public void onResponse(Object response, int id) {
                String body = response.toString();
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                StationListInfo stationListInfo = new StationListInfo();
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(body);
                    stationListInfo.setSuccess(jsonObject.getBoolean("success"));
                    stationListInfo.setFailCode(jsonObject.getInt("failCode"));
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    if (jsonObject1 != null) {
                        Iterator<String> keys = jsonObject1.keys();
                        List<String> stationName = new ArrayList<>();
                        List<String> stationCode = new ArrayList<>();
                        if (stationListInfo.isSuccess()) {
                            while (keys.hasNext()) {
                                String code = keys.next();
                                stationCode.add(code);
                                stationName.add(jsonObject1.getString(code));
                            }
                        }
                        stationListInfo.setStationCode(stationCode);
                        stationListInfo.setStationName(stationName);
                    }
                    if (view != null) {
                        view.getData(stationListInfo);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                }
            }
        });
    }

    /**
     * 获取诊断结果列表
     *
     * @param param TaskResultBean resultBean = new TaskResultBean();
     */
    public void requestTaskResuleList(Map<String, Long> param) {
        model.requestListTaskResult(param, new CommonCallback(TaskResultBean.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                getErrorInfo(e);
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view != null) {
                    if (response != null) {
                        TaskResultBean resultBean = (TaskResultBean) response;
                        if (resultBean.isSuccess()) {
                            view.getData(response);
                        } else {
                            view.getDataFailed(MyApplication.getContext().getString(R.string.request_failed));
                        }
                    }
                }
            }
        });
    }

    /**
     * 电站级故障列表及统计图
     *
     * @param param
     */
    public void requestFaultStaticsList(Map<String, String> param) {
        model.requestFaultStatics(param, new CommonCallback(FaultStaticsBean.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                getErrorInfo(e);
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (view != null) {
                    view.getData(response);
                }
            }
        });
    }

    /**
     * IV曲线获取扫描失败原因
     *
     * @param param
     */
    public void requestIVFailCause(HashMap<String, Integer> param) {
        model.requestFailCause(param, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                getErrorInfo(e);
            }

            @Override
            public void onResponse(Object response, int id) {
                String body = response.toString();
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                IVFailCauseInfo failCauseInfo = new IVFailCauseInfo();
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(body);
                    failCauseInfo.setSuccess(jsonObject.getBoolean("success"));
                    failCauseInfo.setFailCode(jsonObject.getInt("failCode"));
                    if (jsonObject.getBoolean("success")) {
                        String data = jsonObject.getString("data");
                        if (!"null".equals(data) && !TextUtils.isEmpty(data)) {
                            Gson gson = new Gson();
                            failCauseInfo = gson.fromJson(data, IVFailCauseInfo.class);
                        }
                    }
                    if (view != null) {
                        view.getData(failCauseInfo);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                }
            }
        });
    }

    /**
     * 获取任务进度
     *
     * @param param
     */
    public void requestStationProcess(Map<String, String> param) {
        model.requestTaskProcess(param, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                getErrorInfo(e);
            }

            @Override
            public void onResponse(Object response, int id) {
                String body = response.toString();
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(body);
                    IVCurveBean ivCurveBean = new IVCurveBean();
                    boolean success = jsonObject.getBoolean("success");
                    ivCurveBean.setSuccess(success);
                    int failCode = jsonObject.getInt("failCode");
                    ivCurveBean.setFailCode(failCode);
                    if (success) {
                        Type type = new TypeToken<List<IVCurveInfo>>() {
                        }.getType();
                        List<IVCurveInfo> ivCurveInfos = new Gson().fromJson(jsonObject.getString("data"),type);
                        ivCurveBean.setList(ivCurveInfos);
                    }
                    if (view != null) {
                        view.getData(ivCurveBean);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
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
        if (view != null) {
            if (e.toString().contains("SocketTimeout")) {
                view.getDataFailed(MyApplication.getContext().getString(R.string.request_time_out));
            } else {
                view.getDataFailed(MyApplication.getContext().getString(R.string.net_error));
            }
        }
    }
}
