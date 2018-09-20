package com.huawei.solarsafe.presenter.pnlogger;


import android.util.Log;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.pnlogger.BErrorInfo;
import com.huawei.solarsafe.bean.pnlogger.BPnloggerInfo;
import com.huawei.solarsafe.bean.pnlogger.BSecondDeviceInfo;
import com.huawei.solarsafe.bean.pnlogger.PnloggerResultInfo;
import com.huawei.solarsafe.logger104.bean.SecondLineDevice;
import com.huawei.solarsafe.model.pnlogger.BSecondMode;
import com.huawei.solarsafe.net.JSONReader;
import com.huawei.solarsafe.net.StringCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.pnlogger.IBSecondView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by P00517 on 2017/5/15.
 */

public class BSecondPresenter extends BasePresenter<IBSecondView, BSecondMode> {

    public BSecondPresenter() {
        setModel(new BSecondMode());
    }

    /**
     * 获取下联设备信息
     *
     * @param esn
     */
    public void getSecondDeviceInfo(String esn) {
        model.getSecondDeviceInfo(esn, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                getError(e);
            }

            @Override
            public void onResponse(Object response, int id) {
                String body = response.toString();
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                BSecondDeviceInfo lineDevice = new BSecondDeviceInfo();
                List<SecondLineDevice> devices = new ArrayList<>();
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(body);
                    boolean success = jsonObject.getBoolean("success");
                    lineDevice.setSuccess(success);
                    lineDevice.setFailCode(jsonObject.getInt("failCode"));
                    if (success) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        if (jsonObject1 != null) {
                            lineDevice.setEnd(jsonObject1.getBoolean("isEnd"));
                            JSONArray jsonArray = jsonObject1.getJSONArray("secondDeviceList");
                            if (jsonArray != null) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    SecondLineDevice device = new SecondLineDevice();
                                    JSONReader jsonReader = new JSONReader(jsonArray.getJSONObject(i));
                                    device.setModbusAddr(Integer.parseInt(jsonReader.getString("modbusAddr")));
                                    device.setDeviceName(jsonReader.getString("devName"));
                                    device.setDeviceESN(jsonReader.getString("esnCode"));
                                    device.setSignPointFlag(Long.parseLong(jsonReader.getString("pointType")));
                                    device.setConnPort(Byte.parseByte(jsonReader.getString("port")));
                                    device.setProtocolType(Byte.parseByte(jsonReader.getString("proType")));
                                    device.setEndian(jsonReader.getInt("endian"));
                                    device.setBaudRate(jsonReader.getInt("baudRate"));
                                    devices.add(device);
                                }
                                lineDevice.setSecondLineDevices(devices);
                            }
                        }
                    }
                    if (view != null) {
                        view.getData(lineDevice);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                }
            }
        });
    }


    private static final String TAG = "BSecondPresenter";

    /**
     * 配置下联设备信息
     *
     * @param esn
     * @param secondDeviceInfo
     */
    public void setSecondDeviceInfo(String esn, List<SecondLineDevice> secondDeviceInfo) {
        model.setSecondDeviceInfo(esn, secondDeviceInfo, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                getError(e);
            }

            @Override
            public void onResponse(Object response, int id) {
                if (response == null) {
                    view.getData(null);
                } else {
                    String body = response.toString();
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(body);
                        BErrorInfo errorInfo = new BErrorInfo();
                        boolean isSuccess = jsonObject.getBoolean("success");
                        errorInfo.setSuccess(isSuccess);
                        errorInfo.setFailCode(jsonObject.getInt("failCode"));
                        view.getData(errorInfo);
                    } catch (JSONException e) {
                        Log.e(TAG, "onResponse: " + e.getMessage());
                    }
                }
            }
        });
    }

    /**
     * 获取数采配置信息
     *
     * @param esn
     */
    public void getPnloggerInfo(String esn, final int type) {
        model.getPnloggerInfo(esn, type, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                getError(e);
            }

            @Override
            public void onResponse(Object response, int id) {
                if (response == null) {
                    view.getData(null);
                } else {
                    String body = response.toString();
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    BPnloggerInfo pnloggerInfo = new BPnloggerInfo();
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(body);
                        boolean isSuccess = jsonObject.getBoolean("success");
                        pnloggerInfo.setSuccess(isSuccess);
                        pnloggerInfo.setFailCode(jsonObject.getInt("failCode"));
                        if (isSuccess) {
                            dealType(type, pnloggerInfo, jsonObject.getJSONObject("data"));
                        }
                        view.getData(pnloggerInfo);
                    } catch (JSONException e) {
                        Log.e(TAG, "onResponse: " + e.getMessage());
                    }
                }
            }
        });
    }

    /**
     * 获取数采信息
     *
     * @param type
     * @param pnloggerInfo
     * @param data
     */
    private void dealType(int type, BPnloggerInfo pnloggerInfo, JSONObject data) {
        try {
            switch (type) {
                case 0://公共地址
                    pnloggerInfo.setPubAddr(data.getString("pubAddr"));
                    break;
                case 1://名称，esn，型号，类型，IP
                    pnloggerInfo.setDevName(data.getString("devName"));
                    pnloggerInfo.setDevEsn(data.getString("devEsn"));
                    pnloggerInfo.setDevModel(data.getString("devModel"));
                    pnloggerInfo.setDevType(data.getString("devType"));
                    pnloggerInfo.setDevIp(data.getString("devIp"));
                    break;
                case 2://当前版本
                    pnloggerInfo.setCurrentVersion(data.getString("currentVersion"));
                    pnloggerInfo.setLastVersion(data.getString("lastVersion"));
                    break;
                case 3://通信方式
                    pnloggerInfo.setInternet(data.getInt("internet"));
                    break;
                case 4://上报号码
                    pnloggerInfo.setPhone(data.getString("phone"));
                    break;
                case 5://域名，端口
                    pnloggerInfo.setDomainName(data.getString("domainName"));
                    pnloggerInfo.setPort(data.getInt("port"));
                    break;
            }
        } catch (JSONException e) {
            Log.e(TAG, "dealType: " + e.getMessage());
        }
    }

    /**
     * 配置数采信息
     *
     * @param param
     */
    public void setPnloggerInfo(Map<String, String> param) {
        model.setPnloggerInfo(param, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                getError(e);
            }

            @Override
            public void onResponse(Object response, int id) {
                if (response == null) {
                    view.getData(null);
                } else {
                    String body = response.toString();
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(body);
                        BErrorInfo errorInfo = new BErrorInfo();
                        errorInfo.setTag(errorInfo.TAG_SET_PNLOGGER_INFO);
                        boolean isSuccess = jsonObject.getBoolean("success");
                        errorInfo.setSuccess(isSuccess);
                        errorInfo.setFailCode(jsonObject.getInt("failCode"));
                        view.getData(errorInfo);
                    } catch (JSONException e) {
                        Log.e(TAG, "onResponse: " + e.getMessage());
                    }
                }
            }
        });
    }

    /**
     * 数采下联设备大小端，波特率
     *
     * @param param
     */
    public void setPnloggerSecondInfo2(Map<String, String> param) {
        model.setPnloggerSecondInfo2(param, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                getError(e);
            }

            @Override
            public void onResponse(Object response, int id) {
                if (response == null) {
                    view.getData(null);
                } else {
                    String body = response.toString();
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(body);
                        BErrorInfo errorInfo = new BErrorInfo();
                        boolean isSuccess = jsonObject.getBoolean("success");
                        errorInfo.setSuccess(isSuccess);
                        errorInfo.setFailCode(jsonObject.getInt("failCode"));
                        view.getData(errorInfo);
                    } catch (JSONException e) {
                        Log.e(TAG, "onResponse: " + e.getMessage());
                    }
                }
            }
        });
    }

    /**
     * 查询数采配置反馈信息
     *
     * @param esn
     */
    public void setPnloggerUpdateInfo(String esn) {
        model.setPnloggerUpdateInfo(esn, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                getError(e);
            }

            @Override
            public void onResponse(Object response, int id) {
                if (response == null) {
                    view.getData(null);
                } else {
                    String body = response.toString();
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(body);
                        PnloggerResultInfo resultInfo = new PnloggerResultInfo();
                        resultInfo.setSuccess(jsonObject.getBoolean("success"));
                        resultInfo.setFailCode(jsonObject.getInt("failCode"));
                        if (jsonObject.getBoolean("success")) {
                            JSONObject json = jsonObject.getJSONObject("data");
                            resultInfo.setStep(json.getInt("step"));
                            resultInfo.setStepInfo(json.getString("stepInfo"));
                        }
                        view.getData(resultInfo);
                    } catch (JSONException e) {
                        Log.e(TAG, "onResponse: " + e.getMessage());
                    }
                }
            }
        });
    }

    /**
     * 导表
     *
     * @param esn
     */
    public void importPnloggerTable(String esn) {
        model.importTable(esn, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                getError(e);
            }

            @Override
            public void onResponse(Object response, int id) {
                if (response == null) {
                    view.getData(null);
                } else {
                    String body = response.toString();
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(body);
                        BErrorInfo errorInfo = new BErrorInfo();
                        boolean isSuccess = jsonObject.getBoolean("success");
                        errorInfo.setSuccess(isSuccess);
                        errorInfo.setFailCode(jsonObject.getInt("failCode"));
                        view.getData(errorInfo);
                    } catch (JSONException e) {
                        Log.e(TAG, "onResponse: " + e.getMessage());
                    }
                }
            }
        });
    }

    /**
     * 错误处理
     *
     * @param e
     */
    private void getError(Exception e) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        Log.e(TAG, "getError: e" + e);
        if (e.getMessage() != null && e.getMessage().contains("Socket closed")) {
            if (view != null) {
                view.requestFailed("");
            }
        } else if (e.toString().contains("SocketTimeout")) {
            if (view != null) {
                view.requestFailed(MyApplication.getContext().getString(R.string.request_time_out));
            }
        } else {
            if (view != null) {
                view.requestFailed(MyApplication.getContext().getString(R.string.net_error_2));
            }
        }
    }

    /**
     * 处理异常
     *
     * @param failCode
     * @throws JSONException
     */
    public void dealFailCode(int failCode) {
        switch (failCode) {
            case 701://数采ESN不正确
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.be_sure_esn_is_right));
                break;
            case 702://数采与管理系统未连接
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.pnl_system_net_is_normal));
                break;
            case 703://命令执行超时
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.out_time));
                break;
            case 704://数采名字重复
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.data_logger_name_repeat));
                break;
            case 1://服务器内部错误
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.srv_internal_error));
                break;
            case 805:
                //设备数量超出最大限制数
                break;
            default:
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.request_failed));
                break;
        }

    }
}
