package com.huawei.solarsafe.presenter.maintaince.patrol;

import android.util.Log;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.defect.TodoTaskList;
import com.huawei.solarsafe.model.maintain.patrol.PatrolModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.maintaince.todotasks.ITodoTaskView;

import java.util.Map;

import okhttp3.Call;

/**
 * Created by p00322 on 2017/2/22.
 */
public class PatrolPresenter extends BasePresenter<ITodoTaskView, PatrolModel> implements IPatrolPresenter {

    public PatrolPresenter() {
        setModel(PatrolModel.getInstance());
    }

    @Override
    public void doRequestProcess(Map<String, String> param) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestTodoTasks(param, new CommonCallback(TodoTaskList.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call,e,id);
                if (view != null) {
                    view.getData(null);
                }
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "request DefectProcessList failed " + e);
                if (e.toString().contains("java.net.ConnectException")) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                }
            }

            @Override
            public void onResponse(BaseEntity response, int id) {
                if (response == null) {
                    if (view != null) {
                        view.getData(null);
                    }
                }
                if (view != null)
                    view.getData(response);
            }
        });
    }
}
