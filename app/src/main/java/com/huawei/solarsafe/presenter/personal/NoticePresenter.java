package com.huawei.solarsafe.presenter.personal;

import android.util.Log;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.bean.notice.InforMationList;
import com.huawei.solarsafe.bean.notice.SystemQueryNoteInfo;
import com.huawei.solarsafe.model.personal.NoticeModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.personal.INoticeView;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by P00229 on 2017/2/16.
 */
public class NoticePresenter extends BasePresenter<INoticeView, NoticeModel> implements INoticePresenter {
    public static final String TAG = NoticePresenter.class.getSimpleName();

    public NoticePresenter() {
        setModel(new NoticeModel());
    }

    @Override
    public void doRequestNoticeContent(String noteid) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestNoticeContent(noteid,new CommonCallback(SystemQueryNoteInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                Log.e(TAG, "request SystemQueryNoteInfo failed !" + e);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
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

    @Override
    public void doRequestInforMationList(Map<String, String> param) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestInforMationList(param, new CommonCallback(InforMationList.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                Log.e(TAG, "request InforMationList failed !" + e);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
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

    @Override
    public void doRequestMarkMessage(String string) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestMarkMessage(string, new CommonCallback(ResultInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                Log.e(TAG, "request ResultInfo failed !" + e);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
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
                view.getData(response);
            }
        });
    }
}
