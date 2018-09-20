package com.huawei.solarsafe.presenter.maintaince.patrol;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.common.LogCallBack;
import com.huawei.solarsafe.bean.common.RetMsg;
import com.huawei.solarsafe.bean.patrol.PatrolGisBean;
import com.huawei.solarsafe.bean.patrol.PatrolItemList;
import com.huawei.solarsafe.bean.patrol.PatrolReport;
import com.huawei.solarsafe.bean.patrol.PatrolSingleInspec;
import com.huawei.solarsafe.model.maintain.patrol.PatrolGisModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.maintaince.patrol.IPatrolGisView;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Create Date: 2017/3/3
 * Create Author: p00213
 * Description :
 */
public class PatrolGisPresenter extends BasePresenter<IPatrolGisView, PatrolGisModel> implements IPatrolGisPresenter {


    public void setModel() {
        super.setModel(new PatrolGisModel());
    }

    @Override
    public void doRequestItem(Map<String, String> params) {
        model.requestCheckItems(params, new CommonCallback(PatrolItemList.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                getErrorInfo(e);
                if (view != null) {
                    view.getData(null);
                }
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response == null) {
                    if (view != null) {
                        view.getData(null);
                    }
                }
                if (view != null) {
                    view.getData(response);
                }
            }
        });
    }

    @Override
    public void doRequestItemReport(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestListItemReport(params, new CommonCallback(PatrolGisBean.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "request InspectReport failed " + e);
                getErrorInfo(e);
                if (view != null) {
                    view.getData(null);
                }
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response == null) {
                    view.getData(null);
                    return;
                }
                if (view != null) {
                    view.getData(response);
                }
            }
        });
    }

    @Override
    public void doRequestPic(boolean ifOri, String picId, String inspectId) {
        MyApplication.imageLoadRelated();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(NetRequest.IP + "/fileManager/downloadCompleteInmage" + "?fileId=" +
                        picId + "&serviceId=" + 3))
                .setProgressiveRenderingEnabled(true)
                .build();

        DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(request,
                MyApplication.getContext());
        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            public void onNewResultImpl(@Nullable Bitmap bitmap) {
                view.loadPicture(bitmap);
            }


            @Override
            public void onFailureImpl(DataSource dataSource) {
            }
        }, CallerThreadExecutor.getInstance());
    }


    @Override
    public void doCompleteSingleInspec(PatrolReport patrolReport) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestComplInspect(patrolReport, new CommonCallback(PatrolSingleInspec.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                getErrorInfo(e);
                if (view != null) {
                    view.getData(null);
                }
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response == null) {
                    view.getData(null);
                    return;
                }
                if (view != null) {
                    view.getData(response);
                }
            }
        });
    }

    public void uploadAttachment(String filePath, String inspectId) {
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
        params.put("inspectId", inspectId);
        NetRequest.getInstance().postFileWithParams("/inspect/uploadFile", sourceFile, params, new LogCallBack() {
            @Override
            public void onFailed(Exception e) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(NetRequest.TAG, "Upload inspect File Error: ", e);
                if (view != null) {
                    view.uploadFileFailed(e.getMessage());
                }
                Toast.makeText(MyApplication.getContext(), R.string.image_upload_fail, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String response) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                try {
                    Type type = new TypeToken<RetMsg>() {
                    }.getType();
                    Gson gson = new Gson();
                    RetMsg retMsg = gson.fromJson(response, type);
                    if (retMsg.isSuccess() && retMsg.getFailCode() == 0) {
                        if (view != null)
                            view.uploadFileSuccess();
                    } else {
                        if (view != null)
                            view.uploadFileFailed(retMsg.getData().toString());
                    }
                } catch (Exception e) {
                    Log.e("error", e.toString());
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
        if (e.toString().contains("java.net.ConnectException")) {
            ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
        }
    }
}
