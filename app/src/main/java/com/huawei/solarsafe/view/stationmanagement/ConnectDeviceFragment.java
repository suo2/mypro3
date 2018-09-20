package com.huawei.solarsafe.view.stationmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import com.google.zxing.integration.android.IntentIntegrator;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.bean.stationmagagement.CreateStationArgs;
import com.huawei.solarsafe.bean.stationmagagement.DevInfo;
import com.huawei.solarsafe.bean.stationmagagement.SubDev;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.view.pnlogger.SlideDeleteView;
import com.huawei.solarsafe.view.pnlogger.ZxingActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.huawei.solarsafe.bean.device.DevTypeConstant.MULTIPURPOSE_DEV_STR;

/**
 * Create Date: 2017/4/24
 * Create Author: P00171
 * Description :创建电站-连接设备Fragment
 */
public class ConnectDeviceFragment extends CreateBaseFragmnet implements View.OnClickListener {

    private LinearLayout llBtn;
    private Button btnAdd,btnBindNewDevice;//新增按钮
    private ListView lvContent;
    private DeviceAdapter adapter;//列表数据适配器
    private int curPos;//点击的item序号
    public static final int NEW_DEVICE_CODE=1;//跳转新接入设备界面requestCode

    private final String SCAN_MODULE="scanModule";
    private final int CREATE_STATION_MODULE=1;//新建电站

    //接入设备数据集合,与数据源对应
    private List<DevInfo> subDevList = new ArrayList<>();

    public void setSubDevList(List<DevInfo> subDevList) {
        this.subDevList = subDevList;
    }

    public List<DevInfo> getSubDevList() {
        return subDevList;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_station_connect_device, container, false);
        llBtn= (LinearLayout) view.findViewById(R.id.llBtn);
        llBtn.setOnClickListener(this);
        btnAdd = (Button) view.findViewById(R.id.btn_add);
        btnBindNewDevice= (Button) view.findViewById(R.id.btnBindNewDevice);
        lvContent = (ListView) view.findViewById(R.id.lv_content);
        adapter = new DeviceAdapter(getContext(), new ArrayList<DeviceData>());
        lvContent.setAdapter(adapter);
        btnAdd.setOnClickListener(this);
        btnBindNewDevice.setOnClickListener(this);

        if (((CreateStationActivity)getActivity()).isOneKeyOpenStation){
            refreshData();
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
            case R.id.llBtn://新增方法
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
            case R.id.btnBindNewDevice://跳转新接入设备界面
                Intent intent=new Intent();
                intent.setClass(getActivity(),NewDeviceAccessActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("checkedNewDevice", (Serializable) subDevList);
                intent.putExtras(bundle);
                startActivityForResult(intent,NEW_DEVICE_CODE);
                break;
        }
    }

    /**
     * 根据SN查询设备回调
     * @param devInfo
     */
    public void devInfoRsult(DevInfo devInfo) {//标记
        if (!devInfo.isExits()) {//查询设备不存在

            ToastUtil.showMessage(getString(R.string.no_device_data));
            DeviceData data = adapter.getDeviceDatas().get(curPos);
            data.isQuery=true;
            adapter.notifyDataSetChanged();
        } else {//查询设备存在

            if (devInfo.isBoundStation()){
                //设备已被其他电站绑定
                ToastUtil.showMessage(getString(R.string.device_is_bind));
            }else{
                SubDev dev = devInfo.getDev();//接入设备对象
                String stationCode = dev.getStationCode();//接入设备绑定的电站编号

                if (MULTIPURPOSE_DEV_STR.equals(devInfo.getDev().getDevTypeId().toString())){
                    //不支持通管机开站
                    ToastUtil.showMessage(getString(R.string.do_not_support_open_station));
                }else{
                    //将接入设备信息填充进数据源对象
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
                    data.ver = dev.getModelVersionCode();
                    data.isQuery = false;
                    data.id = dev.getId() + "";
                    data.devTypeId=dev.getDevTypeId();
                    data.subDevs = devInfo.getSubDevs();
                    //将接入设备对象存入集合
                    subDevList.add(devInfo);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    //扫码结果回调
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

        //查询设备信息
        ((CreateStationActivity) getActivity()).clickQueryDev(result);
    }

    /**
     * 将设置的参数存入CreateStationArgs
     * @param args
     * @return 是否成功
     */
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

        for (DeviceData data : deviceDatas) {
            //检查是否有未填写的item
            if (data.isQuery) {
                ToastUtil.showMessage(getString(R.string.validate_device));
                return false;
            }

            esnList.add(data.id + "@@" + data.name);
            if (data.subDevs != null && data.subDevs.length > 0) {
                for (int i = 0; i < data.subDevs.length; i++) {
                    if (TextUtils.isEmpty(data.subDevs[i].getStationCode())) {
                        if (!data.subDevs[i].getDevTypeId().equals(DevTypeConstant.SMART_LOGGER_TYPE) && (!data.subDevs[i].getDevTypeId().equals(DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE)) && (!data.subDevs[i].getDevTypeId().equals(DevTypeConstant.PINNET_DC))) {
                            esnList.add(data.subDevs[i].getId() + "@@" + data.subDevs[i].getBusiName());
                        }
                    }
                }
            }
        }
        if(deviceNameIsSame(adapter.getDeviceDatas())){
            ToastUtil.showMessage(getString(R.string.xiagua_dev_name_same));
            return false;
        }

        return true;
    }

    //列表数据源实体类
    class DeviceData {
        String esn;
        String name;
        String ver;
        String id;
        Integer devTypeId;
        SubDev[] subDevs;
        boolean isQuery = true;//
    }

    //列表数据适配器
    class DeviceAdapter extends BaseAdapter implements TextWatcher, View.OnFocusChangeListener, SlideDeleteView.OnSlideStateChangeListener, View.OnClickListener {
        private Context context;
        private List<DeviceData> deviceDatas;
        private SlideDeleteView preSd;

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
                holder.deleteDevice = (Button) convertView.findViewById(R.id.delete_device);
                convertView.setTag(holder);
                //Listener
                holder.etEsn.setOnFocusChangeListener(this);
                holder.etEsn.setOnClickListener(this);
                holder.etName.setOnFocusChangeListener(this);
                holder.etver.setOnFocusChangeListener(this);
                holder.ivScan.setOnClickListener(this);
                holder.btnQuery.setOnClickListener(this);
                holder.btnXGDev.setOnClickListener(this);
                holder.deleteDevice.setOnClickListener(this);
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
            holder.deleteDevice.setTag(position);
            if (data.isQuery) {
                holder.btnQuery.setText(getString(R.string.look_up));
                holder.etEsn.setEnabled(true);
            } else {
                holder.btnQuery.setText(getString(R.string.reset));
                holder.etEsn.setEnabled(false);
            }
            holder.btnXGDev.setTag(position);

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
        public void onClose(SlideDeleteView slideDelete) {
            if (preSd == slideDelete) {
                preSd = null;
            }
        }

        @Override
        public void onOpen(SlideDeleteView slideDelete) {
            if (preSd != null && preSd != slideDelete) {
                preSd.showDeleteView(false);
            }
            preSd = slideDelete;
        }

        @Override
        public void onClick(View v) {
            int pos;
            pos = (int) v.getTag();
            DeviceData data = adapter.getDeviceDatas().get(pos);
            switch (v.getId()) {
                case R.id.delete_device:
                    if (!data.isQuery){
                        for (int i=0;i<subDevList.size();i++){
                            if (subDevList.get(i).getDev().getEsnCode().equals(data.esn)){
                                subDevList.remove(i);//删除集合中接入设备对象
                            }
                        }
                    }
                    adapter.getDeviceDatas().remove(pos);//删除数据源实体对象
                    adapter.notifyDataSetChanged();
                    if (preSd != null) {
                        preSd.showDeleteView(false);
                        preSd = null;
                    }
                    break;
                case R.id.et_dev_esn:
                case R.id.iv_scan://扫描按钮
                    curPos = (int) v.getTag();
//                    DeviceData da = adapter.getDeviceDatas().get(curPos);
                    new IntentIntegrator(getActivity())
                            .setOrientationLocked(false)
                            .setCaptureActivity(ZxingActivity.class)
                            .addExtra(SCAN_MODULE,CREATE_STATION_MODULE)
                            .initiateScan();
                    break;
                case R.id.btn_query://查询/重置按钮 //标记
//                    pos = (int) v.getTag();
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
                        //判断设备是否已经存在于数据源中
                        for (int i = 0; i < adapter.getDeviceDatas().size(); i++) {
                            DeviceData tempData = adapter.getDeviceDatas().get(i);
                            if (pos != i) {
                                if (esn.equals(tempData.esn)) {
                                    ToastUtil.showMessage(context.getString(R.string.device_blind_notice));
                                    return;
                                }
                            }
                        }

                        //发送根据SN号查询设备请求
                        curPos = pos;
                        ((CreateStationActivity) getActivity()).clickQueryDev(esn);
                    } else {
                        //重置
                        data.esn = null;
                        data.name = null;
                        data.ver = null;
                        data.subDevs = null;
                        data.isQuery = true;

                        //删除接入设备数据集合中对应的元素
                        for (int i=0;i<subDevList.size();i++){
                            if (subDevList.get(i).getDev().getEsnCode().equals(data.esn)){
                                subDevList.remove(i);//删除集合中接入设备对象
                            }
                        }

                        adapter.notifyDataSetChanged();
                    }
                    break;
                case R.id.btn_xg_dev://下挂设备按钮
//                    pos = (int) v.getTag();
//                    DeviceData data2 = adapter.getDeviceDatas().get(pos);
                    if (data.isQuery) {
                        //查询
                        String esn1 = data.esn;
                        if (esn1 == null || esn1.isEmpty()) {
                            ToastUtil.showMessage(context.getString(R.string.input_esn_and_search));
                            return;
                        }
                    }
                    if (data.subDevs == null || data.subDevs.length == 0) {
                        ToastUtil.showMessage(getString(R.string.dev_not_have_subdev));
                        return;
                    }
                    ArrayList<SubDev> subDevList = new ArrayList<>();
                    for (int i = 0; i < data.subDevs.length; i++) {
                        if (TextUtils.isEmpty(data.subDevs[i].getStationCode())) {
                            if (!data.subDevs[i].getDevTypeId().equals(DevTypeConstant.SMART_LOGGER_TYPE) && (!data.subDevs[i].getDevTypeId().equals(DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE)) && (!data.subDevs[i].getDevTypeId().equals(DevTypeConstant.PINNET_DC))) {
                                subDevList.add(data.subDevs[i]);
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

        public void refreshData(ArrayList<DeviceData> newData){
            deviceDatas.clear();
            deviceDatas.addAll(newData);
            notifyDataSetChanged();
        }

        class ViewHolder {
            EditText etEsn;
            EditText etName;
            TextView etver;
            ImageView ivScan;
            Button btnQuery;
            Button btnXGDev;
            Button deleteDevice;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==NEW_DEVICE_CODE){
                Bundle bundle=data.getExtras();
                ArrayList<DevInfo> tempArrayList= (ArrayList<DevInfo>) bundle.getSerializable("checkedNewDevice");

                if (tempArrayList!=null){
                    subDevList.clear();
                    subDevList.addAll(tempArrayList);

                    refreshData();
                }
            }
        }
    }

    /**
     * 刷新数据
     */
    public void refreshData(){
        ArrayList<DeviceData> tempDeviceDatas=new ArrayList<>();

        for (DevInfo devInfo:subDevList){
            SubDev dev=devInfo.getDev();
            //将接入设备信息填充进数据源对象
            DeviceData deviceData = new DeviceData();
            deviceData.esn=dev.getEsnCode();
            deviceData.name = dev.getBusiName();
            deviceData.ver = dev.getModelVersionCode();
            deviceData.isQuery = false;
            deviceData.id = dev.getId() + "";
            deviceData.devTypeId=dev.getDevTypeId();
            deviceData.subDevs = devInfo.getSubDevs();
            tempDeviceDatas.add(deviceData);
        }

        adapter.refreshData(tempDeviceDatas);
    }

    /**
     * 判断下挂设备名称是否重复，不包括数采、户用逆变器、品联数采
     * @param deviceData
     * @return
     */
    public static boolean deviceNameIsSame(List<DeviceData> deviceData){

        if(deviceData == null || deviceData.size()==0){
            return false;
        }
        for(DeviceData data:deviceData){
            if(data.subDevs == null || data.subDevs.length <2){ //设备至少两个比较才有意义
                continue;
            }else{
                SubDev[] subDevs = data.subDevs;
                for(int i=0;i<subDevs.length-1;i++){
                    if (subDevs[i].getDevTypeId().equals(DevTypeConstant.SMART_LOGGER_TYPE)||(subDevs[i].getDevTypeId().equals(DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE))||(subDevs[i].getDevTypeId().equals(DevTypeConstant.PINNET_DC))){
                        continue;
                    }
                    for(int j=i+1;j<subDevs.length;j++){
                        if (subDevs[j].getDevTypeId().equals(DevTypeConstant.SMART_LOGGER_TYPE)||(subDevs[j].getDevTypeId().equals(DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE))||(subDevs[j].getDevTypeId().equals(DevTypeConstant.PINNET_DC))){
                            continue;
                        }
                        if(subDevs[i].getBusiName() != null && subDevs[j].getBusiName() != null){
                            if(subDevs[i].getBusiName().equals(subDevs[j].getBusiName())){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
