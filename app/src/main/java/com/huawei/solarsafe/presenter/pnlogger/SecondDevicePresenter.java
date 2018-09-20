package com.huawei.solarsafe.presenter.pnlogger;

import android.util.Log;

import com.huawei.solarsafe.bean.pnlogger.SecondDeviceBean;
import com.huawei.solarsafe.bean.pnlogger.SecondInfo;
import com.huawei.solarsafe.model.pnlogger.SecondDeviceMode;
import com.huawei.solarsafe.net.StringCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.view.pnlogger.ISecondDeviceView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by P00517 on 2017/5/8.
 */

public class SecondDevicePresenter extends BasePresenter<ISecondDeviceView,SecondDeviceMode> {

    public SecondDevicePresenter(){
        setModel(new SecondDeviceMode());
    }

    private static final String TAG = "SecondDevicePresenter";
    /**
     * 获取下联设备
     * @param esn
     */
    public void getSecondDevice(String esn){
        model.getSecondDevice(esn,new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                view.getDevice(e.toString());
            }

            @Override
            public void onResponse(Object response, int id) {
                String body = response.toString();
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                JSONObject jsonObject = null;
                SecondDeviceBean deviceBean = new SecondDeviceBean();
                try {
                    jsonObject = new JSONObject(body);
                    boolean isOK = jsonObject.getBoolean("success");
                    if (isOK){
                        JSONObject data = jsonObject.getJSONObject("data");
                        if (data != null){

                            JSONArray list = data.getJSONArray("subDevList");
                            if (list == null){
                                return ;
                            }
                            List<SecondInfo> secondInfos = new ArrayList<>();
                            for (int i = 0; i < list.length(); i++) {
                                JSONObject jsonObject1 = list.getJSONObject(i);
                                JSONObject dev = jsonObject1.getJSONObject("dev");
                                if(dev != null){
                                    String devType = dev.getString("devTypeId");//devType
                                    if(devType != null && devType.equals("1")){
                                        SecondInfo secondInfo = new SecondInfo();
                                        secondInfo.setBusiName(dev.getString("busiName"));
                                        secondInfo.setEsnCode(dev.getString("esnCode"));
                                        secondInfo.setModelVersionCode(dev.getString("modelVersionCode"));
                                        secondInfos.add(secondInfo);
                                    }
                                }
                            }
                            deviceBean.setSubDevList(secondInfos);

                        }
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                }
                view.getDevice(deviceBean);
//                if (response instanceof SecondDeviceBean){
//                    SecondDeviceBean deviceBean = (SecondDeviceBean) response;
//
//                }else if (response instanceof Boolean){
//                    view.getDevice(false);
//                }
            }
        });
    }
}
