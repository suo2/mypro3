package com.huawei.solarsafe.presenter.stationmanagement;

import android.util.Log;

import com.google.gson.Gson;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.common.LogCallBack;
import com.huawei.solarsafe.bean.common.RetMsg;
import com.huawei.solarsafe.bean.station.ChangeStationInfo;
import com.huawei.solarsafe.bean.stationmagagement.ChangeStationBindInvsBean;
import com.huawei.solarsafe.model.stationmanagement.ISingerSelectPowerStationModel;
import com.huawei.solarsafe.bean.stationmagagement.PowerStationListBean;
import com.huawei.solarsafe.bean.stationmagagement.SaveDevCapData;
import com.huawei.solarsafe.model.stationmanagement.SingerSelectPowerStationModel;
import com.huawei.solarsafe.bean.stationmagagement.StationManegementList;
import com.huawei.solarsafe.bean.stationmagagement.SubDev;
import com.huawei.solarsafe.bean.stationmagagement.UpdateStationDeviceReq;
import com.huawei.solarsafe.net.CommonCallback;
import com.huawei.solarsafe.presenter.BasePresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.stationmanagement.ISingerSelectPowerStationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

/**
 * <pre>
 *     author: Tzy
 *     time  : $date$
 *     desc  : 单选电站界面控制器
 * </pre>
 */
public class SingerSelectPowerStationPresenter extends BasePresenter<ISingerSelectPowerStationView,ISingerSelectPowerStationModel> {
    public final static String TAG=SingerSelectPowerStationPresenter.class.getSimpleName();
    Gson gson;

    public SingerSelectPowerStationPresenter(){
        setModel(new SingerSelectPowerStationModel());
        gson=new Gson();
    }

    /**
     * 执行获取电站(带层级)列表请求
     * @param stationName 电站名
     */
    public void doGetPowerStations(String stationName){
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("stationName",stationName);

        model.getPowerStations(hashMap, new CommonCallback(PowerStationListBean.class) {
            @Override
            public void onResponse(BaseEntity response, int id) {
                PowerStationListBean powerStationListBean= (PowerStationListBean) response;
                if (powerStationListBean.getData()==null || powerStationListBean.getData().isEmpty() ){
                    view.getPowerStations(null);
                }else{
                    view.getPowerStations(powerStationListBean);
                }

            }

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                view.getPowerStations(null);
            }
        });
    }

    /**
     * 执行绑定设备到电站请求
     * @param updateStationDeviceReq 请求参数实体类
     */
    public void doRequestUpdateBindDev(UpdateStationDeviceReq updateStationDeviceReq){

        model.requestUpdateBindDev(updateStationDeviceReq, new LogCallBack() {
            @Override
            protected void onFailed(Exception e) {
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                view.updateBindDev(false);
            }

            @Override
            protected void onSuccess(String data) {

                RetMsg retMsg=gson.fromJson(data,RetMsg.class);
                if (retMsg.isSuccess()){
                    view.updateBindDev(true);
                }else{
                    view.updateBindDev(false);
                }

            }
        });
    }

    /**
     * 执行设置设备PV容量请求
     * @param saveDevCapData 请求参数实体类
     */
    public void doRequestSavePvCapacity(SaveDevCapData saveDevCapData){
        model.requestSavePvCapacity(saveDevCapData, new LogCallBack() {
            @Override
            protected void onFailed(Exception e) {
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                view.savePvCapacity(null);
            }

            @Override
            protected void onSuccess(String data) {
                RetMsg retMsg=gson.fromJson(data,RetMsg.class);
                view.savePvCapacity(retMsg);
            }
        });
    }

    /**
     * 执行获取绑定到电站的设备请求
     * @param stationCode 电站编号
     */
    public void doRequestBindInvs(String stationCode){
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("page", "1");
        hashMap.put("pageSize", "50");
        hashMap.put("stationCode", stationCode);

        model.requestBindInvs(hashMap, new LogCallBack() {
            @Override
            protected void onFailed(Exception e) {
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                view.getBindInvs(null);
            }

            @Override
            protected void onSuccess(String data) {
                ArrayList<SubDev> subDevs=new ArrayList<SubDev>();
                ChangeStationBindInvsBean changeStationBindInvsBean = gson.fromJson(data, ChangeStationBindInvsBean.class);
                if (changeStationBindInvsBean!=null && changeStationBindInvsBean.getData()!=null && changeStationBindInvsBean.getData().getList()!=null){
                    List<ChangeStationBindInvsBean.DataBean.ListBean> list = changeStationBindInvsBean.getData().getList();
                    for (ChangeStationBindInvsBean.DataBean.ListBean bean : list) {
                        SubDev subDev = new SubDev();
                        subDev.setBusiName(bean.getBusiName());
                        subDev.setEsnCode(bean.getEsnCode());
                        subDev.setBusiCode(bean.getBusiCode());
                        subDev.setModelVersionCode(bean.getModelVersionCode());
                        subDev.setDevTypeId(bean.getDevTypeId());
                        subDev.setId(bean.getId());
                        subDev.setStationCode(bean.getStationCode());
                        subDev.setAreaId("" + bean.getDomainId());
                        subDev.setTwoLevelDomain(bean.getTwoLevelDomain() + "");
                        subDev.setCapacity(bean.getCapacity());
                        subDevs.add(subDev);
                    }
                    view.getBindInvs(subDevs);
                }else{
                    view.getBindInvs(null);
                }
            }
        });
    }

    /**
     * 执行获取指定电站编号的电站信息请求
     * @param stationCode 电站编号
     */
    public void doRequestPowerStationInfo(String stationCode){

        model.requestPowerStationInfo(stationCode, new LogCallBack() {
            @Override
            protected void onFailed(Exception e) {
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
                view.getPowerStationInfo(null);
            }

            @Override
            protected void onSuccess(String data) {
                StationManegementList stationManegementList = gson.fromJson(data, StationManegementList.class);

                try {
                    ChangeStationInfo changeStationInfo=stationManegementList.getData().getList().get(0);
                    view.getPowerStationInfo(changeStationInfo);
                } catch (Exception e) {
                    view.getPowerStationInfo(null);
                    Log.e(TAG, "onSuccess: "+e.getMessage());
                }
            }
        });
    }
}
