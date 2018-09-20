package com.huawei.solarsafe.presenter.maintaince.defect;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

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
import com.huawei.solarsafe.bean.device.DevBean;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.model.maintain.defect.DefectModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.net.NewFileCallBack;
import com.huawei.solarsafe.net.StringCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.maintaince.defects.INewDefectView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by P00319 on 2017/2/20.
 */

public class NewDefectPresenter extends BasePresenter<INewDefectView, DefectModel> {

    private Gson gson = new Gson();
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public NewDefectPresenter() {
        setModel(DefectModel.getInstance());
    }

    public void deleteAttachment(String defectId) {
        Map<String, String> params = new HashMap<>();
        params.put("fileId", defectId);
        NetRequest.getInstance().asynPostJson(NetRequest.IP + "/defect/deleteFile", params, new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                view.onFileDelete(false);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
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
        NetRequest.getInstance().postFileWithParams("/defect/uploadFile", sourceFile, params, new LogCallBack() {
            @Override
            public void onFailed(Exception e) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(NetRequest.TAG, "Upload Defect File Error: ", e);

                ToastUtil.showMessage(R.string.attachment_images_uploaded_failed);
            }

            @Override
            public void onSuccess(String response) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                try {
                    Type type = new TypeToken<RetMsg>() {
                    }.getType();
                    RetMsg retMsg = gson.fromJson(response, type);
                    if (retMsg.isSuccess() && retMsg.getFailCode() == 0) {
                        ToastUtil.showMessage(R.string.attachment_images_uploaded);
                        Utils.deletePicDirectory();
                    } else {
                        ToastUtil.showMessage(R.string.attachment_images_uploaded_failed);
                    }
                } catch (Exception e) {
                    Log.e("error", e.toString());
                }
            }
        });
    }

    public void getDefectDetail(String procId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("procId", procId);
        model.requestDefectDetail(params, new CommonCallback(DefectDetailInfo.class) {

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
                if (response instanceof DefectDetailInfo) {
                    if (((DefectDetailInfo) response).getDetail() != null) {
                        if (view != null) {
                            view.setData(((DefectDetailInfo) response).getDetail());
                        }
                    }
                }
            }
        });
    }

    public void submitDefect(String dfId, DevBean devBean, String desc, ProcessReq.Process process, List<String> alarmIds, String
            alarmType) {
        ProcessReq.Info info = new ProcessReq.Info();
        info.setDefectDesc(desc);
        info.setDefectId(dfId);
        info.setDeviceType(devBean.getDevTypeId());
        info.setDeviceId(devBean.getDevId());
        info.setDeviceTypeName(devBean.getDevTypeId().equals("1") ? "组串式逆变器" : "数采");
        info.setSName(devBean.getStationName());
        info.setSId(devBean.getStationCode());
        info.setDeviceVersion(devBean.getDevVersion());
        if (alarmIds == null) {
            alarmIds = new ArrayList<>();
        }
        info.setAlarmIds(alarmIds);
        info.setAlarmType(alarmType == null ? "" : alarmType);
        info.setDefectGrade("");
        info.setDefectCode("");
        info.setOwnerName("");
        info.setProcState("");
        info.setStartTime("");
        info.setEndTime("");
        info.setOwnerId("");
        info.setDeviceName(devBean.getDevName());
        info.setDealResult("");
        info.setFileId("file");
        info.setAlarmType(alarmType);

        model.submitDefect(process, info, new LogCallBack() {
            @Override
            protected void onFailed(Exception e) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e("Defect", "Save new defect failed", e);
                view.submitFailed();
            }

            @Override
            protected void onSuccess(String data) {
                Gson gson = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    if (jsonObject.getBoolean("success")) {
                        Type type = new TypeToken<RetMsg<SubmitRet>>() {
                        }.getType();
                        RetMsg<SubmitRet> ret = gson.fromJson(data, type);
                        SubmitRet sRet = ret.getData();
                        if (ret.isSuccess() && ret.getFailCode() == 0 && sRet.getDefectId() != 0) {
                            if (view != null) {
                                view.submitSuccess(String.valueOf(sRet.getDefectId()));
                            }
                        } else {
                            if (view != null) {
                                view.submitFailed();
                            }
                        }
                    }
                } catch (JSONException e) {
                    Log.e("error", e.toString());
                }
            }
        });

    }

    public void modifyDefect(DefectDetail detail, ProcessReq.Process process) {
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
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e("Defect", "Save new defect failed", e);
                view.submitFailed();
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
                                view.submitSuccess(String.valueOf(sRet.getDefectId()));
                            }
                        } else {
                            Toast.makeText(MyApplication.getContext(), R.string.submittal_failed, Toast.LENGTH_SHORT).show();
                            if (view != null) {
                                view.submitFailed();
                            }
                        }
                    } else {
                        String errorStr = jsonObject.getString("data");
                        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
                        builder.setMessage(errorStr);
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


    public void getAttachment(String fileId, String dfId) {
        String mFilePath = Environment.getExternalStorageDirectory().getPath() + File.separator + "fusionHome" + File.separator
                + "Picture" + File.separator + "Defects";

        NetRequest.getInstance().downFile(NetRequest.IP + "/defect/downloadFile?defectId=" + dfId, new NewFileCallBack(mFilePath, fileId) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(NetRequest.TAG, "Get Defect Attachment from  failed", e);

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
                    view.loadPicture(response.getPath());
                }
            }
        });
    }

    public void requestWorkFlow(String procId) {
        Map<String, String> params = new HashMap<>();
        params.put("procId", procId);
        if (procId != null) {
            NetRequest.getInstance().asynPostJson(NetRequest.IP + "/workflow/listTasks", params, new CommonCallback(WorkFlowList.class) {

                @Override
                public void onError(Call call, Exception e, int id) {
                    super.onError(call, e, id);

                    if (e.toString().contains("java.net.ConnectException")) {
                        ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                    }
                }

                @Override
                public void onResponse(BaseEntity response, int id) {

                    if (response instanceof WorkFlowList) {
                        if (view != null) {
                            view.loadWorkFlow(((WorkFlowList) response).getTasks());
                        }
                    }
                }
            });
        }
    }

}
