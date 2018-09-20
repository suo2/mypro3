package com.huawei.solarsafe.presenter.personal;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.model.personal.ChangePswModel;
import com.huawei.solarsafe.net.StringCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.view.personal.IChangePswView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.Call;

/**
 * Create Date: 2017/3/15
 * Create Author: P00438
 * Description : 修改密码
 */
public class ChangePswPresenter extends BasePresenter<IChangePswView, ChangePswModel> {

    public ChangePswPresenter() {
        setModel(new ChangePswModel());
    }

    public void doRequestChangeUserPassword(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestChangeUserPassword(params, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                if (view != null){
                    view.getFileData(MyApplication.getContext().getString(R.string.net_error));
                }
            }

            @Override
            public void onResponse(Object response, int id) {
                String body = response.toString();
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    ResultInfo resultInfo = new ResultInfo();
                    boolean success = jsonObject.getBoolean("success");
                    resultInfo.setFailCode(jsonObject.getInt("failCode"));
                    resultInfo.setSuccess(success);
                    if (!success) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        resultInfo.setRetMsg(jsonObject1.getString("errorMsg"));
                    }
                    if (view != null) {
                        view.getData(resultInfo);
                    }

                } catch (JSONException e) {
                    if (view != null){
                        view.getFileData(MyApplication.getContext().getString(R.string.net_error));
                    }
                }
            }
        });
    }
}
