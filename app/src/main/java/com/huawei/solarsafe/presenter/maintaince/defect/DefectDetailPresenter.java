package com.huawei.solarsafe.presenter.maintaince.defect;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.common.LogCallBack;
import com.huawei.solarsafe.bean.common.RetMsg;
import com.huawei.solarsafe.bean.defect.DefectDetail;
import com.huawei.solarsafe.bean.defect.DefectDetailInfo;
import com.huawei.solarsafe.bean.defect.ProcessReq;
import com.huawei.solarsafe.bean.defect.SubmitRet;
import com.huawei.solarsafe.bean.defect.WorkFlowList;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.bean.station.kpi.StationInfo;
import com.huawei.solarsafe.model.maintain.defect.DefectModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.net.NewFileCallBack;
import com.huawei.solarsafe.net.StringCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.maintaince.defects.IDefectDetailView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

import static android.R.attr.tag;
import static com.huawei.solarsafe.model.homepage.IStationSingleModel.URL_SINGLESTATION;

/**
 * Created by p00319 on 2017/2/22.
 */

public class DefectDetailPresenter extends BasePresenter<IDefectDetailView, DefectModel> {
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    private Gson gson = new Gson();

    public DefectDetailPresenter() {
        setModel(DefectModel.getInstance());
    }

    public void updateDefect(DefectDetail detail, ProcessReq.Process process) {
        ProcessReq.Info info = new ProcessReq.Info();
        info.setPreTaskOpDesc(detail.getPreTaskOpDesc());
        info.setDefectDesc(detail.getDefectDesc());
        info.setDefectId(String.valueOf(detail.getDefectId()));
        info.setDeviceType(detail.getDeviceType());
        info.setDeviceId(detail.getDeviceId());
        Map<Integer, String> devTypeMap = DevTypeConstant.getDevTypeMap(MyApplication.getContext());
        String deviceType = devTypeMap.get(Integer.valueOf(detail.getDeviceType()));
        info.setDeviceTypeName(deviceType);
        info.setSName(detail.getSName());
        info.setSId(detail.getSId());
        info.setDeviceVersion(detail.getDeviceVersion());
        info.setAlarmIds(detail.getAlarmIds());
        info.setAlarmType(detail.getAlarmType());
        info.setDefectGrade(detail.getDefectGrade());
        info.setDefectCode(detail.getDefectCode());
        info.setOwnerName(detail.getOwnerName());
        info.setProcState(detail.getProcState());
        info.setStartTime(String.valueOf(detail.getStartTime()));
        info.setEndTime(String.valueOf(detail.getEndTime()));
        info.setOwnerId(detail.getOwnerId());
        info.setDeviceName(detail.getDeviceName());
        info.setDealResult(detail.getDealResult());
        info.setFileId(detail.getFileId());

        if (detail.isOper()) {
            info.setOper(true);
        } else {
            info.setOper(false);
        }

        process.setCurrentTaskId(detail.getCurrentTaskId());
        model.submitDefect(process, info, new LogCallBack() {
            @Override
            protected void onFailed(Exception e) {
                if (view != null)
                    view.updateFailed();
            }

            @Override
            protected void onSuccess(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.getBoolean("success")) {
                        Type type = new TypeToken<RetMsg<SubmitRet>>() {
                        }.getType();
                        RetMsg<SubmitRet> ret = gson.fromJson(data, type);
                        SubmitRet sRet = ret.getData();
                        if (ret.isSuccess() && ret.getFailCode() == 0) {
                            Toast.makeText(MyApplication.getContext(), R.string.submit_ok, Toast.LENGTH_SHORT).show();
                            if (view != null) {
                                view.updateSuccess(String.valueOf(sRet.getDefectId()));
                            }
                        } else {

                            //根据请求返回的message弹吐司
                            if (TextUtils.isEmpty(ret.getMessage())) {
                                Toast.makeText(MyApplication.getContext(), R.string.submittal_failed, Toast.LENGTH_SHORT).show();
                            } else {

                                if ("notdeal".equals(ret.getMessage())) {
                                    Toast.makeText(MyApplication.getContext(), R.string.defect_notdeal, Toast.LENGTH_SHORT).show();
                                } else if ("notallsame".equals(ret.getMessage())) {
                                    Toast.makeText(MyApplication.getContext(), R.string.defect_notallsame, Toast.LENGTH_SHORT).show();
                                } else if ("back".equals(ret.getMessage())) {
                                    Toast.makeText(MyApplication.getContext(), R.string.defect_back, Toast.LENGTH_SHORT).show();
                                } else if ("notsameop".equals(ret.getMessage())) {
                                    Toast.makeText(MyApplication.getContext(), R.string.defect_notsameop, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MyApplication.getContext(), R.string.submittal_failed, Toast.LENGTH_SHORT).show();
                                }
                            }

                            if (view != null) {
                                view.updateFailed();
                            }
                        }
                    } else {
                        String errorStr = jsonObject.getString("data");
                        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
                        //宋平修改
                        if ("20001".equals(errorStr)) {
                            builder.setMessage(MyApplication.getContext().getString(R.string.defect_detail_delete));
                        } else if ("20002".equals(errorStr)) {
                            builder.setMessage(MyApplication.getContext().getString(R.string.commit_defect_detail_delete));
                        } else if (!TextUtils.isEmpty(errorStr)) {
                            builder.setMessage(errorStr);
                        } else {
                            builder.setMessage(context.getResources().getString(R.string.modify_filed));
                        }
                        builder.setPositiveButton(context.getString(R.string.determine), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.show();
                    }
                } catch (JSONException e) {
                    Log.e("error", e.toString());
                }
            }
        });
    }

    public void uploadAttachment(String filePath, String defectId) {
        final File sourceFile = new File(filePath);
        if (!sourceFile.exists()) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e(NetRequest.TAG, "there is no image file!");
            boolean mkdir = sourceFile.mkdir();
            if (!mkdir) {
                return;
            }
        }
        Map<String, String> params = new HashMap<>();
        params.put("defectId", defectId);
        params.put("fileId", "file");
        params.put("fileName", sourceFile.getName());
        NetRequest.getInstance().postFileWithParams("/defect/uploadFile", sourceFile, params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(NetRequest.TAG, "Upload Defect File Error: ", e);
                ToastUtil.showMessage(MyApplication.getContext().getResources().getString(R.string.attachment_images_uploaded_failed));
            }

            @Override
            public void onResponse(Object response, int id) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    if (!jsonObject.getBoolean("success")) {
                        ToastUtil.showMessage(R.string.attachment_images_uploaded_failed);
                    } else {
                        ToastUtil.showMessage(R.string.attachment_images_uploaded);
                        Utils.deletePicDirectory();
                    }
                } catch (JSONException e) {
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    Log.e(NetRequest.TAG, tag + " Convert to json error ", e);
                }
            }
        });
    }

    public void deleteAttachment(String defectId) {
        Map<String, String> params = new HashMap<>();
        params.put("fileId", defectId);
        NetRequest.getInstance().asynPostJson(NetRequest.IP + "/defect/deleteFile", params, new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                view.onFileDelete(false);
            }

            @Override
            public void onResponse(Object response, int id) {
                try {
                    if (new JSONObject(response.toString()).getBoolean("success")) {
                        view.onFileDelete(true);
                    } else {
                        view.onFileDelete(false);
                    }
                } catch (JSONException e) {
                    // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                    Log.e(NetRequest.TAG, response + " Convert to json failed ", e);
                }
            }
        });
    }

    //请求缺陷单处理流水
    public void requestWorkFlow(String procId) {
        Map<String, String> params = new HashMap<>();
        params.put("procId", procId);
        if (procId != null) {
            NetRequest.getInstance().asynPostJson(NetRequest.IP + "/workflow/listTasks", params, new CommonCallback(WorkFlowList.class) {

                @Override
                public void onError(Call call, Exception e, int id) {
                    super.onError(call, e, id);
                    if (view != null) {
                        view.loadWorkFlow(null);
                    }
                    if (e.toString().contains("java.net.ConnectException")) {
                        ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                    }
                }

                @Override
                public void onResponse(BaseEntity response, int id) {
                    if (view != null) {
                        if (response == null) {
                            view.loadWorkFlow(null);
                        } else if (response instanceof WorkFlowList) {
                            view.loadWorkFlow(((WorkFlowList) response).getTasks());
                        }
                    }
                }
            });
        }
    }

    public void requestStationInfo(String sid) {
        Map<String, String> params = new HashMap<>();
        params.put("stationCode", sid);
        NetRequest.getInstance().asynPostJson(NetRequest.IP + URL_SINGLESTATION, params, new CommonCallback(StationInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "request StationList failed! " + e);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response instanceof StationInfo) {
                    if (view != null) {
                        view.setStaionPos(new LatLng(((StationInfo) response).getLatitude(), ((StationInfo) response).getLongitude()));
                    }
                }
            }
        });
    }

    public void requestDefectDetail(String defectId, String procId) {
        Map<String, String> params = new HashMap<>();
        if (defectId != null) {
            params.put("defectId", defectId);
        } else if (procId != null) {
            params.put("procId", procId);
        } else {
            return;
        }
        NetRequest.getInstance().asynPostJson(NetRequest.IP + "/defect/queryDefect", params, new CommonCallback(DefectDetailInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response == null) {
                    return;
                }
                if (view != null) {
                    view.loadDefectDetail(response);
                }
            }
        });
    }

    public void getAttachment(String fileId, String dfId) {
        final String mFilePath = Environment.getExternalStorageDirectory().getPath() + File.separator + "fusionHome" + File.separator
                + "Picture" + File.separator + "Defects";

        NetRequest.getInstance().downFile(NetRequest.IP + "/defect/downloadFile?defectId=" + dfId, new NewFileCallBack(mFilePath, fileId) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(NetRequest.TAG, "Get Defect Attachment from failed", e);
                if (view != null) {
                    view.loadPicture(null);
                }
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
            }

            @Override
            public void onResponse(File response, int id) {
                if (view != null) {
                    if (response != null) {
                        view.loadPicture(response.getPath());
                    } else {
                        view.loadPicture(null);
                    }
                }
            }
        });
    }

    public void canHandleProc(Map<String, String> map, boolean isOp) {

        if (Integer.valueOf(LocalData.getInstance().getWebBuildCode()) > 0) {
            if (isOp) {
                if(view != null){
                    view.checkCanHandleProc("isOp");
                }
            } else {
                model.canHandleProc(map, new LogCallBack() {
                    @Override
                    protected void onFailed(Exception e) {
                    }

                    @Override
                    protected void onSuccess(String data) {
                        Type type = new TypeToken<RetMsg<String>>() {
                        }.getType();

                        RetMsg<String> retMsg = gson.fromJson(data, type);
                        if(view != null){
                            if (retMsg.isSuccess()) {
                                view.checkCanHandleProc(retMsg.getMessage());
                            } else {
                                view.checkCanHandleProc("error");
                            }
                        }
                    }
                });
            }

        } else {
            if(view != null){
                view.checkCanHandleProc("isOld");
            }
        }
    }
}
