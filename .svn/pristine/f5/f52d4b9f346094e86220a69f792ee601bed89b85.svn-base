package com.huawei.solarsafe.view.stationmanagement.changestationinfo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.bean.station.ChangeStationInfo;
import com.huawei.solarsafe.bean.stationmagagement.ChangeStationBindInvsBean;
import com.huawei.solarsafe.bean.stationmagagement.ChangeStationBindInvsInfo;
import com.huawei.solarsafe.bean.stationmagagement.ChangeStationDevResultBean;
import com.huawei.solarsafe.bean.stationmagagement.ChangeStationDevResultInfo;
import com.huawei.solarsafe.bean.stationmagagement.ChangeStationDeviceBean;
import com.huawei.solarsafe.bean.stationmagagement.ChangeStationDeviceInfo;
import com.huawei.solarsafe.bean.stationmagagement.CreateStationArgs;
import com.huawei.solarsafe.bean.stationmagagement.DevInfo;
import com.huawei.solarsafe.bean.stationmagagement.DeviceData;
import com.huawei.solarsafe.bean.stationmagagement.SaveCapMassage;
import com.huawei.solarsafe.bean.stationmagagement.SaveDevCapData;
import com.huawei.solarsafe.bean.stationmagagement.SubDev;
import com.huawei.solarsafe.bean.stationmagagement.UnbindDevReq;
import com.huawei.solarsafe.bean.stationmagagement.UpdateStationDeviceReq;
import com.huawei.solarsafe.presenter.stationmanagement.ChangeStationPresenter;
import com.huawei.solarsafe.presenter.stationmanagement.CreateStationPresenter;
import com.huawei.solarsafe.utils.DialogUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.CustomViews.dialogplus.DialogPlus;
import com.huawei.solarsafe.view.CustomViews.dialogplus.OnClickListener;
import com.huawei.solarsafe.view.CustomViews.dialogplus.ViewHolder;
import com.huawei.solarsafe.view.pnlogger.SlideDeleteView;
import com.huawei.solarsafe.view.pnlogger.ZxingActivity;
import com.huawei.solarsafe.view.stationmanagement.CreateBaseFragmnet;
import com.huawei.solarsafe.view.stationmanagement.ICreateStationView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Create Date: 2017/4/24
 * Create Author: P00171
 * Description :创建电站-连接设备Fragment
 */
public class ConnectDeviceFragment extends CreateBaseFragmnet implements View.OnClickListener, IChangeStationView, ICreateStationView {
    private Button btnAdd,btnBindNewDevice;
    private LinearLayout llBtn;
    private ListView lvContent;
    private DeviceAdapter adapter;
    private int curPos;
    //可配置组串的设备
    private List<DevInfo> subDevList = new ArrayList<>();
    //电站基本信息
    private ChangeStationInfo changeStationInfo;
    private String stationCode;
    private ChangeStationPresenter changeStationPresenter;
    private LoadingDialog loadingDialog;
    private CreateStationPresenter createStationPresenter;

    private final String SCAN_MODULE="scanModule";
    private final int CHANGE_STATION_MODULE=2;//修改电站

    DialogPlus setDeviceTotalCapacityDialog;//设置设备总容量对话框
    double sumDeviceCapacity;//总设备容量
    int candDeviceCount;//可配置组串设备数量
    boolean isStartPvArithmetic=true;//PV算法自动计算是否成功
    int defaultPvSize=-1;//默认PV数
    String meanCapacity;//每个PV默认容量
    String updateStationDeviceReqJson;
    boolean isSetDeviceDefaultCapacity=false;//是否去自动设置PV容量
    ArrayList<SubDev> setCapacityDevice;//可配置容量的设备
    private boolean isAllHuaweiInverter=false;//待配置设备是否全部是华为逆变器
    private DecimalFormat decimalFormat;

    public void setChangeStationInfo(ChangeStationInfo changeStationInfo) {
        this.changeStationInfo = changeStationInfo;
        if (changeStationInfo != null) {
            stationCode = changeStationInfo.getStationCode();
            if (loadingDialog == null) {
                loadingDialog = new LoadingDialog(getActivity());
            }
            loadingDialog.show();
            requestData();
        }
    }

    public List<DevInfo> getSubDevList() {
        return subDevList;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeStationPresenter = new ChangeStationPresenter();
        changeStationPresenter.setView(this);
        createStationPresenter = new CreateStationPresenter();
        createStationPresenter.setView(this);

        setCapacityDevice=new ArrayList<>();
        decimalFormat=new DecimalFormat("0");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_station_connect_device, container, false);
        btnAdd = (Button) view.findViewById(R.id.btn_add);
        btnBindNewDevice= (Button) view.findViewById(R.id.btnBindNewDevice);
        llBtn= (LinearLayout) view.findViewById(R.id.llBtn);
        llBtn.setOnClickListener(this);
        lvContent = (ListView) view.findViewById(R.id.lv_content);
        adapter = new DeviceAdapter(getContext(), new ArrayList<DeviceData>());
        lvContent.setAdapter(adapter);
        btnAdd.setOnClickListener(this);
        btnAdd.setVisibility(View.GONE);
        llBtn.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
            case R.id.llBtn:
                boolean isQuery=false;
                for (DeviceData deviceData:adapter.getDeviceDatas()){
                    if (deviceData.isQuery){
                        isQuery=true;
                        break;
                    }
                }
                if (!isQuery){
                    adapter.getDeviceDatas().add(0, new DeviceData());
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    public void devInfoRsult(DevInfo devInfo) {
        if (!devInfo.isExits()) {
            //无相关设备数据
            ToastUtil.showMessage(getString(R.string.no_device_data));
            DeviceData data = adapter.getDeviceDatas().get(curPos);
            data.isQuery=true;
            adapter.notifyDataSetChanged();
        } else {

            if (devInfo.isBoundStation()){
                //该设备已被其他电站绑定，请更换设备
                ToastUtil.showMessage(getString(R.string.device_is_bind));
            }else{
                SubDev dev = devInfo.getDev();
                String stationCode = dev.getStationCode();

                if (TextUtils.isEmpty(stationCode) && !TextUtils.isEmpty(dev.getEsnCode())) {
                    //正常，未绑定电站
                    DeviceData data = adapter.getDeviceDatas().get(curPos);

                    //判断当前item是否已经填充数据
                    if (!TextUtils.isEmpty(data.esn)){
                        //删除接入设备集合中对应数据
                        for (int i=0;i<subDevList.size();i++){
                            if (subDevList.get(i).getDev().getEsnCode().equals(data.esn)){
                                subDevList.remove(i);//删除集合中接入设备对象
                            }
                        }
                    }

                    data.esn = dev.getEsnCode();
                    data.name = dev.getBusiName();
                    data.typeId=dev.getDevTypeId();
                    data.ver = dev.getModelVersionCode();
                    data.isQuery = false;
                    data.id = dev.getId() + "";
                    data.subDevs = devInfo.getSubDevs();
                    subDevList.add(devInfo);
                    adapter.notifyDataSetChanged();
                }
            }

        }
    }

    public void scanResult(String result) {
        //判断设备是否已经存在于数据源中
        for (int i = 0; i < adapter.getDeviceDatas().size(); i++) {
            DeviceData tempData = adapter.getDeviceDatas().get(i);
            if (curPos != i) {
                if (result.equals(tempData.esn)) {
                    ToastUtil.showMessage(getActivity().getResources().getString(R.string.device_blind_notice));
                    return;
                }
            }
            if (tempData.subDevs!=null){
                for (SubDev subDev:tempData.subDevs){
                    if (result.equals(subDev.getEsnCode())){
                        ToastUtil.showMessage(getActivity().getResources().getString(R.string.device_blind_notice));
                        return;
                    }
                }
            }
        }
        createStationPresenter.getDevByEsn(result);
    }

    @Override
    public boolean validateAndSetArgs(CreateStationArgs args) {
        List<String> esnList = args.getEsnlist();
        if (esnList == null) {
            esnList = new ArrayList<>();
            args.setEsnlist(esnList);
        } else {
            esnList.clear();
        }
        List<DeviceData> deviceDatas = adapter.getDeviceDatas();

        //判断下挂设备名称是否重复
        if(Utils.deviceNameIsSame(adapter.getDeviceDatas())){
            ToastUtil.showMessage(getString(R.string.xiagua_dev_name_same));
            return false;
        }

        for (DeviceData data : deviceDatas) {
            //判断是否有item未填充数据
            if (data.isQuery) {
                ToastUtil.showMessage(getString(R.string.validate_device));
                return false;
            }
            esnList.add(data.id + "@@" + data.name);
            if (data.subDevs != null && data.subDevs.length > 0) {
                for (int i = 0; i < data.subDevs.length; i++) {
                    if (TextUtils.isEmpty(data.subDevs[i].getStationCode())) {
                        //剔除下挂的数采,品联数采,户用逆变器3中设备
                        if (!data.subDevs[i].getDevTypeId().equals(DevTypeConstant.SMART_LOGGER_TYPE) && (!data.subDevs[i].getDevTypeId().equals(DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE)) && (!data.subDevs[i].getDevTypeId().equals(DevTypeConstant.PINNET_DC))) {
                            esnList.add(data.subDevs[i].getId() + "@@" + data.subDevs[i].getBusiName());
                        }
                    }
                }
            }
        }
        if(Utils.deviceNameIsSame(adapter.getDeviceDatas())){
            ToastUtil.showMessage(getString(R.string.xiagua_dev_name_same));
            return false;
        }
        return true;
    }

    @Override
    public void requestData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("stationCode", stationCode);
        changeStationPresenter.requestStationGetBindDev(params);
    }

    @Override
    public void preStep() {

    }

    @Override
    public void cancelStep() {

    }

    @Override
    public void nextStep() {

    }

    @Override
    public void getData(BaseEntity baseEntity) {
        if (isAdded()) {
            if (loadingDialog != null && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
            if (baseEntity == null) {
                return;
            }
            if (baseEntity instanceof ChangeStationDeviceInfo) {
                ChangeStationDeviceInfo changeStationDeviceInfo = (ChangeStationDeviceInfo) baseEntity;
                ChangeStationDeviceBean changeStationDeviceBean = changeStationDeviceInfo.getChangeStationDeviceBean();
                if (changeStationDeviceBean != null) {
                    ChangeStationDeviceBean.DataBean data = changeStationDeviceBean.getData();
                    if (data != null) {
                        List<ChangeStationDeviceBean.DataBean.CollectorListBean> collectorList = data.getCollectorList();
                        List<ChangeStationDeviceBean.DataBean.CollectorListBean.CollectorBean> aloneDevs = data.getAloneDevs();
                        List<DeviceData> deviceDatas = new ArrayList<>();
                        if (aloneDevs != null && aloneDevs.size() > 0) {
                            for (ChangeStationDeviceBean.DataBean.CollectorListBean.CollectorBean aloneDevsBean : aloneDevs) {
                                if (aloneDevsBean != null) {
                                    DeviceData devData = new DeviceData();
                                    devData.setEsn(aloneDevsBean.getEsnCode());
                                    devData.setName(aloneDevsBean.getBusiName());
                                    devData.setVer(aloneDevsBean.getModelVersionCode());
                                    devData.setId(aloneDevsBean.getId() + "");
                                    devData.setTypeId(aloneDevsBean.getDevTypeId());
                                    devData.setCanEdt(false);
                                    devData.setQuery(false);
                                    devData.setNew(false);
                                    deviceDatas.add(devData);
                                }
                            }
                        }
                        if (collectorList != null && collectorList.size() > 0) {
                            for (ChangeStationDeviceBean.DataBean.CollectorListBean collectorListbean : collectorList) {
                                ChangeStationDeviceBean.DataBean.CollectorListBean.CollectorBean collector = collectorListbean.getCollector();
                                DeviceData devData = new DeviceData();
                                devData.setEsn(collector.getEsnCode());
                                devData.setName(collector.getBusiName());
                                devData.setVer(collector.getModelVersionCode());
                                devData.setId(collector.getId() + "");
                                devData.setTypeId(collector.getDevTypeId());
                                devData.setCanEdt(false);
                                devData.setQuery(false);
                                devData.setNew(false);
                                SubDev[] subDevs;
                                List<ChangeStationDeviceBean.DataBean.CollectorListBean.CollectorBean> subDevlist = collectorListbean.getSubDevlist();
                                if (subDevlist != null && subDevlist.size() > 0) {
                                    subDevs = new SubDev[subDevlist.size()];
                                    for (int i = 0; i < subDevlist.size(); i++) {
                                        subDevs[i] = new SubDev();
                                        ChangeStationDeviceBean.DataBean.CollectorListBean.CollectorBean subDevlistBean = subDevlist.get(i);
                                        if (subDevlistBean != null) {
                                            subDevs[i].setBusiName(subDevlistBean.getBusiName());
                                            subDevs[i].setEsnCode(subDevlistBean.getEsnCode());
                                            subDevs[i].setModelVersionCode(subDevlistBean.getModelVersionCode());
                                            subDevs[i].setDevTypeId(subDevlistBean.getDevTypeId());

                                        }
                                    }
                                    devData.setSubDevs(subDevs);
                                } else {
                                    devData.setSubDevs(null);
                                }
                                deviceDatas.add(devData);
                            }
                        }
                        adapter = new DeviceAdapter(getContext(), deviceDatas);
                        lvContent.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        canEdt(false);
                    }
                }
            } else if (baseEntity instanceof DevInfo) {
                DevInfo devInfo = (DevInfo) baseEntity;
                devInfoRsult(devInfo);
            } else if (baseEntity instanceof ChangeStationDevResultInfo) {//更新接入设备信息回调 //标记
                ChangeStationDevResultInfo changeStationDevResultInfo = (ChangeStationDevResultInfo) baseEntity;
                ChangeStationDevResultBean changeStationDevResultBean = changeStationDevResultInfo.getChangeStationDevResultBean();
                if (changeStationDevResultInfo.getPos() == -1) {
                    if (changeStationDevResultBean != null) {

                        if (changeStationDevResultBean.isSuccess()) {
                            requestData();

                            //判断是否存在未配置PV容量的设备
                            if("0".equals(changeStationDevResultBean.getData().getDifferCount())){
                                ToastUtil.showMessage(getActivity().getResources().getString(R.string.save_success));
                            }else {
                                //显示设置直流容量对话框方法
                                showSetDeviceTotalCapacityDialog(changeStationDevResultBean.getData().getDifferCount());
                            }

                            //tangkun修改，解决保存失败按钮状态异常
                            Activity aty = getActivity();
                            if (aty instanceof ChangeStationInfoActivity) {
                                ((ChangeStationInfoActivity) aty).saveConnectDevice(true);
                            }
                            canEdt(true);
                        } else {
                            DialogUtil.showErrorMsg(getActivity(), getResources().getString(R.string.save_failed));
                            //tangkun修改，解决保存失败按钮状态异常
                            Activity aty = getActivity();
                            if (aty instanceof ChangeStationInfoActivity) {
                                ((ChangeStationInfoActivity) aty).saveConnectDevice(false);
                            }
                            canEdt(true);
                        }
                    }
                }else {
                    if (changeStationDevResultBean != null) {
                        if (changeStationDevResultBean.isSuccess()) {
                            adapter.getDeviceDatas().remove(changeStationDevResultInfo.getPos());
                            adapter.notifyDataSetChanged();
                            DialogUtil.showErrorMsg(getActivity(), getString(R.string.unbind_notice_str));
                        } else {
                            DialogUtil.showErrorMsg(getActivity(), getString(R.string.unbind_failed_notice_str));
                        }
                    }
                }
            }else if (baseEntity instanceof ChangeStationBindInvsInfo){//获取绑定到电站的设备列表回调 //标记
                ChangeStationBindInvsInfo changeStationBindInvsInfo= (ChangeStationBindInvsInfo) baseEntity;
                ChangeStationBindInvsBean changeStationBindInvsBean=changeStationBindInvsInfo.getChangeStationBindInvsBean();
                ArrayList<SubDev> subDevs=new ArrayList<SubDev>();
                if (changeStationBindInvsBean!=null && changeStationBindInvsBean.getData()!=null && changeStationBindInvsBean.getData().getList()!=null) {
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

                    //统计可配置容量的设备
                    statisticsCanSetCapacityDeviceCount(subDevs);

                    if(candDeviceCount>0){
                        //设置设备默认PV容量
                        setDeviceDefaultCapacity();
                    }else{
                        ToastUtil.showMessage(getActivity().getResources().getString(R.string.save_success));
                    }
                }
            }else if (baseEntity instanceof SaveCapMassage){//设置设备PV容量请求回调 //标记
                SaveCapMassage retMsg= (SaveCapMassage) baseEntity;
                if (retMsg.isSuccess() && isStartPvArithmetic){
                    ToastUtil.showMessage(getActivity().getResources().getString(R.string.save_success));
                }else{
                    if (!TextUtils.isEmpty(retMsg.getMessage()) && !"null".equals(retMsg.getMessage())){
                        ToastUtil.showMessage(retMsg.getMessage());
                    }
                    DialogUtils.showSingleBtnDialog(getActivity(), false, getActivity().getResources().getString(R.string.bind_device_reset_pv_msg), new OnClickListener() {
                        @Override
                        public void onClick(DialogPlus dialog,View view) {
                            dialog.dismiss();
                            ChangeStationInfoActivity changeStationInfoActivity= (ChangeStationInfoActivity) getActivity();
                            changeStationInfoActivity.doOnekey();
                        }
                    });
                }
            }
        }

    }

    @Override
    public void createStationSuccess() {

    }

    @Override
    public void createStationFail(int failCode,String data) {

    }

    @Override
    public void uploadResult(boolean ifSuccess, String key) {

    }

    class DeviceAdapter extends BaseAdapter implements TextWatcher, View.OnFocusChangeListener, View.OnClickListener {
        private Context context;
        private List<DeviceData> deviceDatas;
        private SlideDeleteView preSd;
        private boolean canEdt;

        public void setCanEdt(boolean canEdt) {
            this.canEdt = canEdt;
        }

        public List<DeviceData> getDeviceDatas() {
            return deviceDatas;
        }

        public DeviceAdapter(Context context, List<DeviceData> deviceDatas) {
            this.context = context;
            this.deviceDatas = deviceDatas;
        }

        @Override
        public int getCount() {
            return deviceDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return deviceDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            DeviceData data = deviceDatas.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_create_station_connect_device, null);
                holder = new ViewHolder();
                holder.etEsn = (EditText) convertView.findViewById(R.id.et_dev_esn);
                holder.etName = (EditText) convertView.findViewById(R.id.et_dev_name);
                holder.etver = (TextView) convertView.findViewById(R.id.et_dev_ver);
                holder.ivScan = (ImageView) convertView.findViewById(R.id.iv_scan);
                holder.btnQuery = (Button) convertView.findViewById(R.id.btn_query);
                holder.btnXGDev = (Button) convertView.findViewById(R.id.btn_xg_dev);
                holder.delete = (Button) convertView.findViewById(R.id.delete_device);
                convertView.setTag(holder);
                //Listener
                holder.etEsn.setOnFocusChangeListener(this);
                holder.etEsn.setOnClickListener(this);
                holder.etName.setOnFocusChangeListener(this);
                holder.etver.setOnFocusChangeListener(this);
                holder.ivScan.setOnClickListener(this);
                holder.btnQuery.setOnClickListener(this);
                holder.btnXGDev.setOnClickListener(this);
                holder.delete.setOnClickListener(this);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.etEsn.setText(data.esn);
            holder.etEsn.setTag(position);
            holder.etName.setText(data.name);
            if(!TextUtils.isEmpty(data.name)){
                holder.btnXGDev.setVisibility(View.VISIBLE);
            }else {
                holder.btnXGDev.setVisibility(View.GONE);
            }
            holder.etName.setTag(position);
            holder.etver.setText(data.ver);
            holder.etver.setTag(position);
            holder.ivScan.setTag(position);
            holder.btnQuery.setTag(position);
            holder.btnQuery.setTag(R.id.tag1, holder);
            holder.delete.setTag(position);
            if (data.isQuery) {
                holder.btnQuery.setText(getString(R.string.look_up));
                holder.etEsn.setEnabled(true);
            } else {
                holder.btnQuery.setText(getString(R.string.reset));
                holder.etEsn.setEnabled(false);
            }
            holder.btnXGDev.setTag(position);
            if (data.isNew()) {
//                holder.btnQuery.setVisibility(View.VISIBLE);
                holder.ivScan.setVisibility(View.VISIBLE);
                holder.delete.setVisibility(View.VISIBLE);
                holder.etName.setEnabled(true);
            } else {
//                holder.btnQuery.setVisibility(View.GONE);
                holder.ivScan.setVisibility(View.GONE);
                holder.delete.setVisibility(View.GONE);
                holder.etName.setEnabled(false);
            }
            if (canEdt) {
                holder.delete.setVisibility(View.VISIBLE);
            } else {
                holder.delete.setVisibility(View.GONE);
            }
            return convertView;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                //失去焦点,则马上将输入框至放入adapter相应数据源中
                int pos = (int) v.getTag();
                if (pos >= 0 && pos <= adapter.getDeviceDatas().size() - 1) {//在删除侧滑删除后会调用，去掉判断导致崩溃
                    DeviceData data = (DeviceData) adapter.getItem(pos);
                    switch (v.getId()) {
                        case R.id.et_dev_esn:
                            data.esn = ((TextView) v).getText().toString().trim();
                            break;
                        case R.id.et_dev_name:
                            data.name = ((TextView) v).getText().toString().trim();
                            break;
                        case R.id.et_dev_ver:
                            data.ver = ((TextView) v).getText().toString().trim();
                            break;
                    }
                }
            }
        }

        @Override
        public void onClick(View v) {
            final int pos;
            pos = (int) v.getTag();
            DeviceData data = adapter.getDeviceDatas().get(pos);
            switch (v.getId()) {
                case R.id.et_dev_esn:
                case R.id.iv_scan:
                    curPos = (int) v.getTag();
                    DeviceData da = adapter.getDeviceDatas().get(curPos);
                    new IntentIntegrator(getActivity())
                            .setOrientationLocked(false)
                            .setCaptureActivity(ZxingActivity.class)
                            .addExtra(SCAN_MODULE,CHANGE_STATION_MODULE)
                            .initiateScan();
                    break;
                case R.id.btn_query:
//                    DeviceData data = adapter.getDeviceDatas().get(pos);
                    if (data.isQuery) {
                        ViewHolder holder = (ViewHolder) v.getTag(R.id.tag1);
                        holder.etEsn.clearFocus();
                        //查询
                        String esn = data.esn;

                        if (esn == null || esn.isEmpty()) {
                            ToastUtil.showMessage(getString(R.string.input_device_esn));
                            return;
                        }
                        //wujing修改
                        for (int i = 0; i < adapter.getDeviceDatas().size(); i++) {
                            DeviceData data1 = adapter.getDeviceDatas().get(i);
                            if (pos != i) {
                                if (esn.equals(data1.esn)) {
                                    ToastUtil.showMessage(context.getString(R.string.device_blind_notice));
                                    return;
                                }
                            }
                        }
                        curPos = pos;
                        createStationPresenter.getDevByEsn(esn);
                    } else {
                        //重置
                        data.esn = null;
                        data.name = null;
                        data.ver = null;
                        data.subDevs = null;
                        data.isQuery = true;
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case R.id.delete_device:
                    //subDevList.remove(pos);
                    if (!data.isQuery){
                        for (int i=0;i<subDevList.size();i++){
                            if (subDevList.get(i).getDev().getEsnCode().equals(data.esn)){
                                subDevList.remove(i);//删除集合中接入设备对象
                            }
                        }
                    }
                    final DeviceData deviceData = adapter.getDeviceDatas().get(pos);
                    if (TextUtils.isEmpty(deviceData.getEsn()) || deviceData.isNew()) {
                        adapter.getDeviceDatas().remove(pos);
                        adapter.notifyDataSetChanged();
                    } else {
                        DialogUtil.showChooseDialog(getActivity(), "", getString(R.string.unbinddev_notice_str), getString(R.string.sure), getString(R.string.cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UnbindDevReq unbindDevReq = new UnbindDevReq();
                                unbindDevReq.setDevId(deviceData.getId());
                                Gson gson = new Gson();
                                String s = gson.toJson(unbindDevReq);
                                showLoading();
                                changeStationPresenter.requestStationUnBindDev(s, pos);
                            }
                        });

                    }
                    break;
                case R.id.btn_xg_dev:
                    DeviceData data2 = adapter.getDeviceDatas().get(pos);
                    if (data2.isQuery) {
                        //查询
                        String esn = data2.esn;
                        if (esn == null || esn.isEmpty()) {
                            ToastUtil.showMessage(context.getString(R.string.input_esn_and_search));
                            return;
                        }
                    }
                    if (data2.subDevs == null || data2.subDevs.length == 0) {
                        ToastUtil.showMessage(getString(R.string.dev_not_have_subdev));
                        return;
                    }
                    ArrayList<SubDev> subDevList = new ArrayList<>();
                    for (int i = 0; i < data2.subDevs.length; i++) {
                        if (TextUtils.isEmpty(data2.subDevs[i].getStationCode())) {
                            if (!data2.subDevs[i].getDevTypeId().equals(DevTypeConstant.SMART_LOGGER_TYPE) && (!data2.subDevs[i].getDevTypeId().equals(DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE ))&& (!data2.subDevs[i].getDevTypeId().equals(DevTypeConstant.PINNET_DC))) {
                                subDevList.add(data2.subDevs[i]);
                            }
                        }
                    }
                    SubDev[] subdevArray = new SubDev[subDevList.size()];
                    for (int i = 0; i < subDevList.size(); i++) {
                        subdevArray[i] = subDevList.get(i);
                    }
                    DialogUtil.showSubDevsDialog(getActivity(), subdevArray);
                    break;
            }
        }

        class ViewHolder {
            EditText etEsn;
            EditText etName;
            TextView etver;
            ImageView ivScan;
            Button btnQuery;
            Button btnXGDev;
            Button delete;
        }
    }

    public void updateInfo() {//更新接入设备信息 //标记
        UpdateStationDeviceReq updateStationDeviceReq = new UpdateStationDeviceReq();
        List<String> esnList = new ArrayList<>();
        List<DeviceData> deviceDatas = adapter.getDeviceDatas();
        for (DeviceData data : deviceDatas) {
            if (data.isQuery) {
                ToastUtil.showMessage(getString(R.string.validate_device));
                return;
            }
            if (data.isNew()) {
                esnList.add(data.id + "@@" + data.name);
                if (data.subDevs != null && data.subDevs.length > 0) {
                    for (int i = 0; i < data.subDevs.length; i++) {
                        if (TextUtils.isEmpty(data.subDevs[i].getStationCode())) {
                            //剔除下挂的数采,品联数采,户用逆变器3种设备
                            if (!data.subDevs[i].getDevTypeId().equals(DevTypeConstant.SMART_LOGGER_TYPE) && (!data.subDevs[i].getDevTypeId().equals(DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE)) && (!data.subDevs[i].getDevTypeId().equals(DevTypeConstant.PINNET_DC))) {
                                esnList.add(data.subDevs[i].getId() + "@@" + data.subDevs[i].getBusiName());
                            }
                        }
                    }
                }
            }
        }
        updateStationDeviceReq.setEsnList(esnList);
        updateStationDeviceReq.setStationCode(changeStationInfo.getStationCode());
        updateStationDeviceReq.setDomainId(changeStationInfo.getDomainId() + "");
        updateStationDeviceReqJson = new Gson().toJson(updateStationDeviceReq);
        showLoading();
        //发送绑定设备请求
        changeStationPresenter.requestUpdateBindDev(updateStationDeviceReqJson, -1);

    }

    public void canEdt(boolean canEdt) {
        if (canEdt) {
            btnAdd.setVisibility(View.VISIBLE);
            llBtn.setVisibility(View.VISIBLE);
        } else {
            btnAdd.setVisibility(View.GONE);
            llBtn.setVisibility(View.GONE);
        }
        adapter.setCanEdt(canEdt);
        adapter.notifyDataSetChanged();
    }


    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getActivity());
        }
        loadingDialog.show();
    }

    public void dismissLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getActivity());
        }
        loadingDialog.dismiss();
    }

    /**
     * 显示设置直流容量对话框方法
     */
    private void showSetDeviceTotalCapacityDialog(String differCount){
        if (setDeviceTotalCapacityDialog==null){
            setDeviceTotalCapacityDialog=DialogPlus.newDialog(getActivity())
                    .setContentHolder(new ViewHolder(R.layout.dialog_set_device_total_capacity_change_station_info))
                    .setGravity(Gravity.CENTER)
                    .setCancelable(false)
                    .setContentBackgroundResource(R.drawable.shape_dialog_center_default_bg)
                    .setMargin(Utils.dp2Px(getActivity(),50f),0,Utils.dp2Px(getActivity(),50f),0)
                    .setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(DialogPlus dialog, View view) {
                            switch (view.getId()){
                                case R.id.btnNegative:
                                    dialog.dismiss();
                                    break;
                            }
                        }
                    })
                    .create();

            View view=setDeviceTotalCapacityDialog.getHolderView();

            //输入框添加过滤条件
            final EditText tvValue= (EditText) view.findViewById(R.id.tvValue);
            tvValue.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
            tvValue.setFilters(new InputFilter[]{new InputFilter() {
                @Override
                public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                    if (spanned.toString().length() == 0 && charSequence.equals(".")) {
                        return "0.";
                    }
                    if (spanned.toString().length() == 1 && spanned.toString().equals("0") && charSequence.equals("0")) {
                        return "";
                    }
                    if (spanned.toString().contains(".")) {
                        int index = spanned.toString().indexOf(".");
                        int mlength = spanned.toString().substring(index).length();
                        if (mlength == 4) {
                            return "";
                        }
                    }
                    return null;
                }
            }});

            Button btnPositive= (Button) view.findViewById(R.id.btnPositive);
            btnPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String temp=tvValue.getText().toString().trim();

                    if (TextUtils.isEmpty(temp)){
                        ToastUtil.showMessage(getResources().getString(R.string.please_input_capacity_of_device));
                    }else if(Float.valueOf(temp) <= 0 || Float.valueOf(temp) > 100000000){
                        tvValue.setError(getResources().getString(R.string.capacity_notice));
                    }else{
                        sumDeviceCapacity=Double.valueOf(temp);

                        tvValue.setText("");
                        setDeviceTotalCapacityDialog.dismiss();

                        loadingDialog.show();
                        //发送获取绑定到电站的设备请求
                        HashMap<String, String> params = new HashMap<>();
                        params.put("page", "1");
                        params.put("pageSize", "1000");
                        params.put("stationCode", changeStationInfo.getStationCode());
                        changeStationPresenter.requestGetBindInvs(params);

                    }
                }
            });
        }
        TextView tvMsg= (TextView) setDeviceTotalCapacityDialog.getHolderView().findViewById(R.id.tvMsg);
        tvMsg.setText(String.format(getActivity().getResources().getString(R.string.update_bind_devs_success_but_exist_devs_not_set_capacity),differCount));

        setDeviceTotalCapacityDialog.show();
    }

    /**
     * 统计可以配置容量的设备
     */
    private void statisticsCanSetCapacityDeviceCount(ArrayList<SubDev> stationBindDevice){
        //判断接入设备集合中哪些是可进行组串详情配置的设备
        setCapacityDevice.clear();
        candDeviceCount=0;

        ArrayList<SubDev> tempList=new ArrayList<>();

        //筛选出新接入设备可配置容量的设备
        for (DevInfo devInfo : subDevList) {
            SubDev s = devInfo.getDev();
            if (s != null) {
                if (s.getDevTypeId().equals(DevTypeConstant.INVERTER_DEV_TYPE) || s.getDevTypeId().equals(DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE) || s.getDevTypeId().equals(DevTypeConstant.DCJS_DEV_TYPE)) {
                    tempList.add(s);
                }

            }

            //下挂设备
            SubDev[] subDevs = devInfo.getSubDevs();
            if (subDevs != null && subDevs.length > 0) {
                for (SubDev subDev : subDevs) {
                    if (subDev.getDevTypeId().equals(DevTypeConstant.INVERTER_DEV_TYPE) || subDev.getDevTypeId().equals(DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE) || subDev.getDevTypeId().equals(DevTypeConstant.DCJS_DEV_TYPE)) {
                        tempList.add(subDev);
                    }
                }
            }
        }

        //将可配置容量的设备完整信息添加进集合
        for (SubDev checkSubDev:tempList){

            for (SubDev subDev:stationBindDevice){
                //找到新接入的设备
                if (checkSubDev.getEsnCode().equals(subDev.getEsnCode())){
                    //筛选出未配置PV容量的设备
                    if (TextUtils.isEmpty(subDev.getCapacity())){
                        setCapacityDevice.add(subDev);
                    }
                }
            }
        }

        candDeviceCount=setCapacityDevice.size();
    }

    /**
     * 设置设备默认容量方法
     */
    public void setDeviceDefaultCapacity(){
        int tempPvSize;
        String tempCapacity;

        //构建请求参数
        SaveDevCapData data = new SaveDevCapData();
        HashMap pvMap = new HashMap<>();
        HashMap pvItemMap = new HashMap<>();

        //判断是否启用算法
        isStartPvArithmetic=true;//是否启用PV算法

        //默认所有设备都为户用逆变器时是华为逆变器
        isAllHuaweiInverter=true;

        if (setCapacityDevice.isEmpty()){
            return;
        }

        int typeId=setCapacityDevice.get(0).getDevTypeId();//设备类型编号

        for(SubDev subDev:setCapacityDevice){
            //检查设备型号是否一致
            if (typeId!=subDev.getDevTypeId()){
                isStartPvArithmetic=false;
            }

            //判断是否全部为华为逆变器
            if (DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE.toString().equals(String.valueOf(typeId))){
                if (subDev.getPvCapacity()==null){
                    isAllHuaweiInverter=false;
                }
            }
        }

        //检查设备型号是否是直流汇流箱
        if (typeId==DevTypeConstant.DCJS_DEV_TYPE){
            isStartPvArithmetic=false;
        }

        //进行配置
        for (SubDev subDev:setCapacityDevice){
            if (subDev.getDevTypeId().equals(DevTypeConstant.DCJS_DEV_TYPE)){//直流汇流箱
                //计算默认容量
                tempPvSize=14;
                tempCapacity="0";

                subDev.setSet(true);//标记已配置组串

                //配置默认容量
                for (int i = 0; i < tempPvSize; i++) {
                    String key = i + 1 + "";
                    pvItemMap.put(key, tempCapacity);
                }
                pvMap.put("map",pvItemMap);
                pvMap.put("size",tempPvSize);

                HashMap devMap = new HashMap<>();
                HashMap devItemMap = new HashMap<>();
                for (int i = 0; i < setCapacityDevice.size(); i++) {
                    HashMap<String, String> devDataMap = new HashMap<>();
                    devDataMap.put("id", setCapacityDevice.get(i).getId() + "");
                    devDataMap.put("stationCode", setCapacityDevice.get(i).getStationCode());
                    devDataMap.put("busiCode", setCapacityDevice.get(i).getBusiCode());
                    devDataMap.put("devTypeId", setCapacityDevice.get(i).getDevTypeId() + "");
                    devDataMap.put("twoLevelDomain", setCapacityDevice.get(i).getTwoLevelDomain());
                    devDataMap.put("esnCode", setCapacityDevice.get(i).getEsnCode() + "");

                    devItemMap.put(setCapacityDevice.get(i).getId() + "", devDataMap);
                }

                devMap.put("map", devItemMap);
                devMap.put("size", setCapacityDevice.size());
                data.setPvCapMap(pvMap);
                data.setDevInfo(devMap);

            }else if (subDev.getDevTypeId().equals(DevTypeConstant.INVERTER_DEV_TYPE)){//组串式逆变器
                //计算默认容量
                if (isStartPvArithmetic){
                    computeDefaultCapacity();
                    if (defaultPvSize!=-1){
                        tempPvSize=defaultPvSize;
                        tempCapacity=meanCapacity;
                    }else{
                        tempPvSize=8;
                        tempCapacity="0";
                    }
                }else{
                    tempPvSize=8;
                    tempCapacity="0";
                }

                subDev.setSet(true);//标记已配置组串
                //配置默认容量
                for (int i = 0; i < tempPvSize; i++) {
                    String key = i + 1 + "";
                    pvItemMap.put(key, tempCapacity);
                }
                pvMap.put("map",pvItemMap);
                pvMap.put("size",tempPvSize);

                HashMap devMap = new HashMap<>();
                HashMap devItemMap = new HashMap<>();
                for (int i = 0; i < setCapacityDevice.size(); i++) {
                    HashMap<String, String> devDataMap = new HashMap<>();
                    devDataMap.put("id", setCapacityDevice.get(i).getId() + "");
                    devDataMap.put("stationCode", setCapacityDevice.get(i).getStationCode());
                    devDataMap.put("busiCode", setCapacityDevice.get(i).getBusiCode());
                    devDataMap.put("devTypeId", setCapacityDevice.get(i).getDevTypeId() + "");
                    devDataMap.put("twoLevelDomain", setCapacityDevice.get(i).getTwoLevelDomain());
                    devDataMap.put("esnCode", setCapacityDevice.get(i).getEsnCode() + "");

                    devItemMap.put(setCapacityDevice.get(i).getId() + "", devDataMap);
                }

                devMap.put("map", devItemMap);
                devMap.put("size", setCapacityDevice.size());
                data.setPvCapMap(pvMap);
                data.setDevInfo(devMap);

            }else if (subDev.getDevTypeId().equals( DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE)){//户用逆变器
                //计算默认容量
                if (isStartPvArithmetic){

                    if (isAllHuaweiInverter){
                        SubDev.PvCapacity pvCapacity=subDev.getPvCapacity().get(subDev.getEsnCode());
                        tempPvSize=pvCapacity.getCapNum();
                        tempCapacity=String.valueOf(decimalFormat.format(pvCapacity.getRatedCapacity()*1000/tempPvSize));
                    }else{
                        computeDefaultCapacity();
                        if (defaultPvSize==-1 || defaultPvSize>8){
                            isStartPvArithmetic=false;
                            tempPvSize=2;
                            tempCapacity="0";
                        }else {
                            tempPvSize=defaultPvSize;
                            tempCapacity=meanCapacity;
                        }
                    }
                }else{
                    tempPvSize=2;
                    tempCapacity="0";
                }

                subDev.setSet(true);//标记已配置组串
                //配置默认容量
                for (int i = 0; i < tempPvSize; i++) {
                    String key = i + 1 + "";
                    pvItemMap.put(key, tempCapacity);
                }
                pvMap.put("map",pvItemMap);
                pvMap.put("size",tempPvSize);

                HashMap devMap = new HashMap<>();
                HashMap devItemMap = new HashMap<>();
                for (int i = 0; i < setCapacityDevice.size(); i++) {
                    HashMap<String, String> devDataMap = new HashMap<>();
                    devDataMap.put("id", setCapacityDevice.get(i).getId() + "");
                    devDataMap.put("stationCode", setCapacityDevice.get(i).getStationCode());
                    devDataMap.put("busiCode", setCapacityDevice.get(i).getBusiCode());
                    devDataMap.put("devTypeId", setCapacityDevice.get(i).getDevTypeId() + "");
                    devDataMap.put("twoLevelDomain", setCapacityDevice.get(i).getTwoLevelDomain());
                    devDataMap.put("esnCode", setCapacityDevice.get(i).getEsnCode() + "");

                    devItemMap.put(setCapacityDevice.get(i).getId() + "", devDataMap);
                }

                devMap.put("map", devItemMap);
                devMap.put("size", setCapacityDevice.size());
                data.setPvCapMap(pvMap);
                data.setDevInfo(devMap);
            }
        }

        //发送设置设备PV容量请求
        changeStationPresenter.saveDevCapData(new Gson().toJson(data));
    }

    /**
     * 计算设备默认容量方法
     */
    public void computeDefaultCapacity(){
        defaultPvSize=-1;
        meanCapacity="";
        DecimalFormat decimalFormat=new DecimalFormat("0");
        double temp = 0;
        if (candDeviceCount>0 && sumDeviceCapacity>0){
            temp=sumDeviceCapacity/candDeviceCount;
            if (temp>0 && temp<=10){
                defaultPvSize=2;
            }else if (temp>10 && temp <=20){
                defaultPvSize=3;
            }else if (temp>20 && temp<=40){
                defaultPvSize=8;
            }else if (temp>40 && temp<=60){
                defaultPvSize=12;
            }

        }

        if (defaultPvSize!=-1){
            meanCapacity=decimalFormat.format((temp/defaultPvSize*1000));
        }else{
            isStartPvArithmetic=false;
        }

    }

}
