package com.huawei.solarsafe.presenter.personmanagement;

import android.text.TextUtils;
import android.util.Log;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.bean.personmanagement.CountUsersByNameBean;
import com.huawei.solarsafe.bean.personmanagement.RoleListBean;
import com.huawei.solarsafe.bean.personmanagement.RoleListInfo;
import com.huawei.solarsafe.bean.personmanagement.UpdatePersonResult;
import com.huawei.solarsafe.bean.personmanagement.UserListInfo;
import com.huawei.solarsafe.model.personmanagement.IPersonManagementModel;
import com.huawei.solarsafe.model.personmanagement.PersonManagementModel;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.net.StringCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.presenter.devicemanagement.DevManagementPresenter;
import com.huawei.solarsafe.utils.JSONReader;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.personmanagement.IPersonManagementView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by P00229 on 2017/4/10.
 */
public class PersonmanagementPresenter extends BasePresenter<IPersonManagementView, IPersonManagementModel> {
    public static final String TAG = DevManagementPresenter.class.getSimpleName();

    public PersonmanagementPresenter() {
        setModel(new PersonManagementModel());
    }

    public void doRequestQueryUsersList(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestQueryUsersList(params, new CommonCallback(UserListInfo.class) {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                Log.e(TAG, "request UserListInfo failed " + e);
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

                if (view != null) {
                    view.getData(response);
                }
            }
        });
    }

    public void doRequestSaveUser(String params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestSaveUser(params, new StringCallback() {
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
            public void onResponse(Object response, int id) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                ResultInfo resultInfo = new ResultInfo();
                try {
                    //【解DTS单】DTS2017112801773 修改人:赵宇凤
                    JSONReader jsonReader = new JSONReader(new JSONObject(response.toString()));
                    resultInfo.success1 = jsonReader.getBoolean("success");
                    resultInfo.setData(jsonReader.getString("data"));
                    resultInfo.setFailCode(jsonReader.getInt("failCode"));
                    if (view != null) {
                        view.getData(resultInfo);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                }
            }
        });
    }

    public void doRequestQueryRoles(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestQueryRoles(params, new CommonCallback(RoleListInfo.class) {
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
                if (view != null) {
                    RoleListInfo roleListInfo = (RoleListInfo) response;
                    roleListInfo.setTag(RoleListInfo.LIST);
                    view.getData(response);
                }
            }
        });
    }

    public void doRequestQueryUserRoles(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestQueryUserRoles(params, new StringCallback() {
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
            public void onResponse(Object response, int id) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                RoleListInfo roleListInfo = new RoleListInfo();
                roleListInfo.setTag(RoleListInfo.NO_LIST);
                RoleListBean roleListBean = new RoleListBean();
                List<RoleListBean.ListBean> list = new ArrayList<RoleListBean.ListBean>();

                try {
                    JSONReader jsonReader = new JSONReader(new JSONObject(response.toString()));
                    JSONArray data = jsonReader.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        RoleListBean.ListBean listBean = new RoleListBean.ListBean();
                        JSONObject jsonObject = data.getJSONObject(i);
                        listBean.setRoleName(jsonObject.getString("roleName"));
                        listBean.setId(jsonObject.getInt("id"));
                        list.add(listBean);
                    }
                    roleListBean.setList(list);
                    roleListInfo.setRoleListBean(roleListBean);
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                }
                view.getData(roleListInfo);
            }
        });
    }

    public void doRequestCountUsersByName(Map<String, String> params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestCountUsersByName(params, new StringCallback() {
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
            public void onResponse(Object response, int id) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                CountUsersByNameBean resultInfo = new CountUsersByNameBean();
                try {
                    JSONReader jsonReader = new JSONReader(new JSONObject(response.toString()));
                    resultInfo.setData(jsonReader.getInt("data"));
                    if (view != null) {
                        view.getData(resultInfo);
                    }
                } catch (JSONException e) {
                    if (view != null) {
                        view.getData(null);
                    }
                    Log.e(TAG, "onResponse: "+e.getMessage());
                }
            }
        });
    }

    public void doRequestUpdatePerson(String params) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        model.requestUpdateUser(params, new StringCallback() {
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
            public void onResponse(Object o, int i) {
                UpdatePersonResult resultInfo = new UpdatePersonResult();
                try {
                    String response = (String) o;
                    JSONReader jsonReader = new JSONReader(new JSONObject(response));
                    resultInfo.setSuccess(jsonReader.getBoolean("success"));
                    String errorMessage = jsonReader.getString("message");
                    if(TextUtils.isEmpty(errorMessage)){
                        errorMessage = jsonReader.getString("data");
                    }
                    resultInfo.setRetMsg(errorMessage);
                    if (view != null) {
                        view.getData(resultInfo);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: "+e.getMessage());
                }
            }
        });
    }
}
