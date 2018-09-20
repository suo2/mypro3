package com.huawei.solarsafe.view.stationmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.common.RetMsg;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.bean.station.ChangeStationInfo;
import com.huawei.solarsafe.bean.stationmagagement.DevInfo;
import com.huawei.solarsafe.bean.stationmagagement.PowerStationListBean;
import com.huawei.solarsafe.bean.stationmagagement.SaveDevCapData;
import com.huawei.solarsafe.bean.stationmagagement.SubDev;
import com.huawei.solarsafe.bean.stationmagagement.UpdateStationDeviceReq;
import com.huawei.solarsafe.presenter.stationmanagement.SingerSelectPowerStationPresenter;
import com.huawei.solarsafe.utils.DialogUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.CustomViews.dialogplus.DialogPlus;
import com.huawei.solarsafe.view.CustomViews.dialogplus.OnClickListener;
import com.huawei.solarsafe.view.CustomViews.dialogplus.ViewHolder;
import com.huawei.solarsafe.view.CustomViews.treelist.Node;
import com.huawei.solarsafe.view.CustomViews.treelist.OnTreeNodeClickListener;
import com.huawei.solarsafe.view.CustomViews.treelist.TreeRecyclerAdapter;
import com.huawei.solarsafe.view.login.MyEditText;
import com.huawei.solarsafe.view.stationmanagement.changestationinfo.ChangeStationInfoActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SingerSelectPowerStationActivity extends BaseActivity implements ISingerSelectPowerStationView, View.OnClickListener {

    private com.huawei.solarsafe.view.login.MyEditText tvSearch;
    private android.support.v7.widget.RecyclerView recyclerView;
    private android.support.v4.widget.SwipeRefreshLayout swipeRefreshLayout;
    private android.view.View divider;
    private android.widget.Button btnCancel;
    private android.widget.Button btnConfirm;
    private android.widget.LinearLayout llBtn;
    private android.widget.LinearLayout llEmpty;

    SingerSelectPowerStationPresenter singerSelectPowerStationPresenter;
    PowerStationListBean.PowerStationBean checkedpPowerStationBean;//被选中的电站实体类
    SingerSelectPowerStationAdapter singerSelectPowerStationAdapter;
    ArrayList<Node> nodeList;
    ArrayList<DevInfo> checkedNewDevice;//被选择的设备
    ArrayList<SubDev> setCapacityDevice;//可配置容量的设备
    ArrayList<SubDev> stationBindDevice;//绑定到电站的设备
    DialogPlus setDeviceTotalCapacityDialog;//设置设备总容量对话框
    double sumDeviceCapacity;//总设备容量
    int candDeviceCount;//可配置组串设备数量
    boolean isStartPvArithmetic=true;//是否启用PV算法
    int defaultPvSize=-1;//默认PV数
    String meanCapacity;//每个PV默认容量
    boolean isNewEquipment=false;//是否通过新增设备入口进入
    boolean coerceDialogShowState=false;//强制操作对话框显示状态

    private boolean isAllHuaweiInverter=false;//待配置设备是否全部是华为逆变器
    private DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle=getIntent().getExtras();
        checkedNewDevice= (ArrayList<DevInfo>) bundle.getSerializable("checkedNewDevice");

        try {
            isNewEquipment=getIntent().getBooleanExtra("isNewEquipment",false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        decimalFormat=new DecimalFormat("0");

        singerSelectPowerStationPresenter=new SingerSelectPowerStationPresenter();
        singerSelectPowerStationPresenter.onViewAttached(this);

        nodeList=new ArrayList<>();
        singerSelectPowerStationAdapter=new SingerSelectPowerStationAdapter(recyclerView,SingerSelectPowerStationActivity.this,nodeList,1,R.drawable.icon_tree_down,R.drawable.icon_tree_up);

        initTree();

        tvSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                singerSelectPowerStationPresenter.doGetPowerStations(editable.toString());
            }
        });

        setCapacityDevice=new ArrayList<>();
        stationBindDevice=new ArrayList<>();

        //显示计算容量失败对话框的时,Activity被放在后台杀死的情况下恢复数据
        if (savedInstanceState!=null){
            checkedpPowerStationBean= (PowerStationListBean.PowerStationBean) savedInstanceState.getSerializable("checkedpPowerStationBean");
            coerceDialogShowState=savedInstanceState.getBoolean("coerceDialogShowState");
            if (coerceDialogShowState){
                DialogUtils.showSingleBtnDialog(SingerSelectPowerStationActivity.this, false, getResources().getString(R.string.bind_device_reset_pv_msg), new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog,View view) {
                        //发送获取指定电站编号的电站信息请求
                        singerSelectPowerStationPresenter.doRequestPowerStationInfo(checkedpPowerStationBean.getId());
                        dialog.dismiss();
                        coerceDialogShowState=false;
                    }
                });
            }
        }else{
            checkedpPowerStationBean=null;
        }
        requestData();
    }

    /**
     * 初始化树状图方法
     */
    private void initTree(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(SingerSelectPowerStationActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(singerSelectPowerStationAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkedpPowerStationBean=null;
                requestData();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_singer_select_power_station;
    }

    @Override
    protected void initView() {
        this.llEmpty = (LinearLayout) findViewById(R.id.llEmpty);
        this.llBtn = (LinearLayout) findViewById(R.id.llBtn);
        this.btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(this);
        this.btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        this.divider = findViewById(R.id.divider);
        this.swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.tvSearch = (MyEditText) findViewById(R.id.tvSearch);

//        tv_left.setText(getResources().getString(R.string.back));
//        tv_left.setOnClickListener(this);
        tv_left.setVisibility(View.GONE);
        tv_title.setText(getResources().getString(R.string.station_choice_another_name));
        tv_right.setVisibility(View.GONE);
        tvSearch.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30), new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                for (int j = i; i < i1; i++) {
                    if (Character.toString(charSequence.charAt(i)).equals(",") || Character.toString(charSequence.charAt(i)).equals("<")
                            || Character.toString(charSequence.charAt(i)).equals(">") || Character.toString(charSequence.charAt(i)).equals("/")
                            || Character.toString(charSequence.charAt(i)).equals("&") || Character.toString(charSequence.charAt(i)).equals("'")
                            || Character.toString(charSequence.charAt(i)).equals("\"") || Character.toString(charSequence.charAt(i)).equals("，")
                            || Character.toString(charSequence.charAt(i)).equals("{") || Character.toString(charSequence.charAt(i)).equals("}")
                            || Character.toString(charSequence.charAt(i)).equals("|") || Character.toString(charSequence.charAt(i)).equals("\\r")
                            || Character.toString(charSequence.charAt(i)).equals("\\n")) {
                        return "";
                    }
                }
                return null;
            }
        }, Utils.getEmojiFilter()});

    }

    /**
     *请求树状图数据
     */
    @Override
    public void requestData() {
        singerSelectPowerStationPresenter.doGetPowerStations(tvSearch.getText().toString());
    }

    /**
     * 请求树状图数据回调
     * @param powerStationListBean 回调数据
     */
    @Override
    public void getPowerStations(PowerStationListBean powerStationListBean) {

        if (powerStationListBean!=null){
            boolean hasPowerStation=false;
            ArrayList<PowerStationListBean.PowerStationBean> tempList=powerStationListBean.getData();

            for (PowerStationListBean.PowerStationBean powerStationBean:tempList){
                if ("STATION".equals(powerStationBean.getModel())){
                    hasPowerStation=true;
                    break;
                }
            }

            if (hasPowerStation){
                nodeList.clear();
                nodeList.addAll(convertToTreeDatas(tempList));

                if (TextUtils.isEmpty(tvSearch.getText().toString())){//判断是否精确搜索
                    singerSelectPowerStationAdapter.addDataAll(nodeList,1);//搜索全部,展开1级
                }else{
                    singerSelectPowerStationAdapter.addDataAll(nodeList,99);//精确搜索,展开所有层级
                }

                llEmpty.setVisibility(View.GONE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);
//                divider.setVisibility(View.VISIBLE);
//                llBtn.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
                return;
            }
        }

        nodeList.clear();
        checkedpPowerStationBean=null;
        singerSelectPowerStationAdapter.addDataAll(nodeList,0);
        swipeRefreshLayout.setVisibility(View.GONE);
//        divider.setVisibility(View.GONE);
//        llBtn.setVisibility(View.GONE);
        llEmpty.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 绑定设备到现有电站回调
     * @param b 是否成功
     */
    @Override
    public void updateBindDev(boolean b) {
        if (b){
            //发送获取绑定到电站的设备请求
            singerSelectPowerStationPresenter.doRequestBindInvs(checkedpPowerStationBean.getId());

        }else{
            dismissLoading();
            ToastUtil.showMessage(getResources().getString(R.string.add_to_existing_power_station_failure_msg));
        }
    }

    /**
     * 获取绑定到电站的设备列表回调
     * @param subDevs 绑定到电站设备集合
     */
    @Override
    public void getBindInvs(ArrayList<SubDev> subDevs) {
        if (subDevs!=null){
            stationBindDevice.clear();
            stationBindDevice.addAll(subDevs);

            //统计可配置容量的设备
            statisticsCanSetCapacityDeviceCount();

            if(candDeviceCount>0){
                //设置设备默认PV容量
                setDeviceDefaultCapacity();
            }else{
                dismissLoading();
                Intent intent=new Intent(SingerSelectPowerStationActivity.this,AddDeviceFeedbackActivity.class);
                intent.putExtra("checkedNewDevice",checkedNewDevice);
                intent.putExtra("checkedpPowerStationBean",checkedpPowerStationBean);
                intent.putExtra("isNewEquipment",isNewEquipment);
                startActivity(intent);
                finish();
            }
        }else{
            dismissLoading();
        }
    }

    /**
     * 设置设备PV容量请求回调
     * @param retMsg 回调数据
     */
    @Override
    public void savePvCapacity(RetMsg retMsg) {
        dismissLoading();
        if (retMsg!=null){
            ToastUtil.showMessage(retMsg.getMessage());
            if (retMsg.isSuccess() && isStartPvArithmetic){
                Intent intent=new Intent(SingerSelectPowerStationActivity.this,AddDeviceFeedbackActivity.class);
                intent.putExtra("checkedNewDevice",checkedNewDevice);
                intent.putExtra("checkedpPowerStationBean",checkedpPowerStationBean);
                intent.putExtra("isNewEquipment",isNewEquipment);
                startActivity(intent);
                finish();
            }else{
                coerceDialogShowState=true;
                DialogUtils.showSingleBtnDialog(SingerSelectPowerStationActivity.this, false, getResources().getString(R.string.bind_device_reset_pv_msg), new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog,View view) {
                        
                        showLoading();
                        
                        //发送获取指定电站编号的电站信息请求
                        singerSelectPowerStationPresenter.doRequestPowerStationInfo(checkedpPowerStationBean.getId());
                        dialog.dismiss();
                        coerceDialogShowState=false;
                    }
                });
            }
        }
    }

    /**
     * 获取指定电站编号的电站信息请求回调
     * @param changeStationInfo 电站信息
     */
    @Override
    public void getPowerStationInfo(ChangeStationInfo changeStationInfo) {
        dismissLoading();
        if (changeStationInfo!=null){
            Intent intent = new Intent(SingerSelectPowerStationActivity.this, ChangeStationInfoActivity.class);
            intent.putExtra("id", changeStationInfo.getStationCode());
            intent.putExtra("changeStationInfo", changeStationInfo);
            intent.putExtra("isOneKey",true);
            startActivity(intent);
            finish();
        }
    }

    /**
     * 将普通格式数据源转换为树状图数据源格式
     * @param tempList
     */
    private ArrayList<Node> convertToTreeDatas(ArrayList<PowerStationListBean.PowerStationBean> tempList){
        ArrayList<Node> convertNodeList=new ArrayList<>();

        for (PowerStationListBean.PowerStationBean bean:tempList){
            convertNodeList.add(new Node<String,PowerStationListBean.PowerStationBean>(bean.getId(),bean.getPid(),"",bean));
        }

        return convertNodeList;
    }

    @Override
    public void onClick(View view) {
        int viewId=view.getId();
        switch (viewId){
//            case R.id.tv_left:
//                finish();
//                break;
            case R.id.btnCancel:
                finish();
                break;
            case R.id.btnConfirm://确定按钮
                //检查是否选择电站
                if (checkedpPowerStationBean==null){
                    ToastUtil.showMessage(getResources().getString(R.string.please_station_choice));
                }else{
                    //显示设置设备总容量对话框
                    showSetDeviceTotalCapacityDialog();
                }
                break;
        }
    }

    /**
     * 显示设置直流容量对话框
     */
    private void showSetDeviceTotalCapacityDialog(){
        if (setDeviceTotalCapacityDialog==null){
            setDeviceTotalCapacityDialog=DialogPlus.newDialog(SingerSelectPowerStationActivity.this)
                    .setContentHolder(new ViewHolder(R.layout.dialog_set_device_total_capacity))
                    .setGravity(Gravity.CENTER)
                    .setCancelable(false)
                    .setContentBackgroundResource(R.drawable.shape_dialog_center_default_bg)
                    .setMargin(Utils.dp2Px(SingerSelectPowerStationActivity.this,50f),0,Utils.dp2Px(SingerSelectPowerStationActivity.this,50f),0)
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
                        setDeviceTotalCapacityDialog.dismiss();

                        //显示确认提醒对话框
                        DialogUtils.showTwoBtnDialog(SingerSelectPowerStationActivity.this, true, getResources().getString(R.string.affirm_warn), getResources().getString(R.string.ask_add_device_to_station), new OnClickListener() {
                            @Override
                            public void onClick(DialogPlus dialog,View view) {

                                if (checkedpPowerStationBean!=null && checkedpPowerStationBean.getPid()!=null){

                                    showLoading();

                                    //配置绑定设备请求参数
                                    UpdateStationDeviceReq updateStationDeviceReq = new UpdateStationDeviceReq();
                                    updateStationDeviceReq.setDomainId(checkedpPowerStationBean.getPid());
                                    updateStationDeviceReq.setStationCode(checkedpPowerStationBean.getId());

                                    ArrayList<String> esnList=new ArrayList<>();
                                    for (DevInfo devInfo:checkedNewDevice){
                                        esnList.add(devInfo.getDev().getId()+"@@"+devInfo.getDev().getBusiName());
                                        if (devInfo.getSubDevs()!=null && devInfo.getSubDevs().length>0){
                                            for (SubDev subDev:devInfo.getSubDevs()){
                                                if (TextUtils.isEmpty(subDev.getStationCode())){
                                                    esnList.add(subDev.getId()+"@@"+subDev.getBusiName());
                                                }
                                            }
                                        }
                                    }
                                    updateStationDeviceReq.setEsnList(esnList);

                                    //发送绑定设备到电站请求
                                    singerSelectPowerStationPresenter.doRequestUpdateBindDev(updateStationDeviceReq);
                                    dialog.dismiss();
                                }else{
                                    ToastUtil.showMessage(getResources().getString(R.string.add_to_existing_power_station_failure_msg));
                                }
                            }
                        });
                    }
                }
            });
        }

        setDeviceTotalCapacityDialog.show();
    }

    /**
     * 统计可以配置容量的设备
     */
    private void statisticsCanSetCapacityDeviceCount(){
        //判断接入设备集合中哪些是可进行组串详情配置的设备
        setCapacityDevice.clear();
        candDeviceCount=0;

        ArrayList<SubDev> tempList=new ArrayList<>();
        //过滤出可配置容量的设备
        for (DevInfo devInfo : checkedNewDevice) {
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
                if (checkSubDev.getEsnCode().equals(subDev.getEsnCode())){
                    setCapacityDevice.add(subDev);
                }
            }
        }

        candDeviceCount=setCapacityDevice.size();
    }

    /**
     * 计算设备默认容量方法
     */
    public void computeDefaultCapacity(){
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
        singerSelectPowerStationPresenter.doRequestSavePvCapacity(data);
    }

    /**
     * <pre>
     *     author: Tzy
     *     time  : 2018/2/28
     *     desc  : 单选电站树状图数据适配器
     * </pre>
     */
    class SingerSelectPowerStationAdapter extends TreeRecyclerAdapter {
        public SingerSelectPowerStationAdapter(RecyclerView mTree, Context context, List<Node> datas, int defaultExpandLevel, int iconExpand, int iconNoExpand) {
            super(mTree, context, datas, defaultExpandLevel, iconExpand, iconNoExpand);
        }

        public SingerSelectPowerStationAdapter(RecyclerView mTree, Context context, List<Node> datas, int defaultExpandLevel) {
            super(mTree, context, datas, defaultExpandLevel);
        }

        @Override
        public void onBindViewHolder(final Node node, RecyclerView.ViewHolder holder, int position) {
            SingerSelectPowerStationViewHolder viewHolder= (SingerSelectPowerStationViewHolder) holder;

            PowerStationListBean.PowerStationBean bean= (PowerStationListBean.PowerStationBean) node.getBean();

            //设置收起/展开图标
            if (node.getIcon()==-1){
                viewHolder.imgTreeState.setVisibility(View.GONE);
            }else{
                viewHolder.imgTreeState.setVisibility(View.VISIBLE);
                viewHolder.imgTreeState.setImageResource(node.getIcon());
            }

            //设置单选按钮状态
            if (node.isChecked()){
                viewHolder.rbSelect.setChecked(true);
            }else{
                viewHolder.rbSelect.setChecked(false);
            }

            if ("Msg.&topdomain".equals(bean.getName())){
                viewHolder.tvName.setText(getResources().getString(R.string.topdomain));
            }else{
                viewHolder.tvName.setText(bean.getName());
            }

            //取消过滤
//            //判断是否是域名而且是叶子节点
//            if ("DOMAIN".equals(bean.getModel()) && node.isLeaf()){//如果是,隐藏该item(没办法,服务器返回了下面没有符合要求的电站的域名,只有自己过滤了)
//                viewHolder.imgTreeState.setVisibility(View.VISIBLE);
//                viewHolder.imgTreeState.setImageResource(R.drawable.icon_tree_up);
//            }

            //判断是电站或域
            if ("STATION".equals(bean.getModel())){
                viewHolder.rbSelect.setVisibility(View.VISIBLE);
                viewHolder.imgPowerStation.setVisibility(View.VISIBLE);
            }else{
                viewHolder.rbSelect.setVisibility(View.GONE);
                viewHolder.imgPowerStation.setVisibility(View.GONE);
            }

            //设置item点击事件
            setOnTreeNodeClickListener(new OnTreeNodeClickListener() {
                @Override
                public void onClick(Node node, int position) {
                    PowerStationListBean.PowerStationBean clickBean= (PowerStationListBean.PowerStationBean) node.getBean();

                    //判断是否是电站
                    if ("STATION".equals(clickBean.getModel())){

                        if (node.isChecked()){
                            checkedpPowerStationBean=null;
                            node.setChecked(false);
                        }else{
                            checkedpPowerStationBean=clickBean;
                            for (Node temp:mAllNodes){
                                temp.setChecked(false);
                            }
                            node.setChecked(true);
                        }

                        notifyDataSetChanged();

                    }
                }
            });
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view=View.inflate(mContext,R.layout.list_singer_select_power_station,null);
            return new SingerSelectPowerStationViewHolder(view);
        }

        class SingerSelectPowerStationViewHolder extends RecyclerView.ViewHolder {
            RadioButton rbSelect;
            ImageView imgTreeState,imgPowerStation;
            TextView tvName;

            public SingerSelectPowerStationViewHolder(View itemView) {
                super(itemView);
                rbSelect= (RadioButton) itemView.findViewById(R.id.rbSelect);
                imgTreeState= (ImageView) itemView.findViewById(R.id.imgTreeState);
                imgPowerStation= (ImageView) itemView.findViewById(R.id.imgPowerStation);
                tvName= (TextView) itemView.findViewById(R.id.tvName);
            }
        }
    }


    /**
     * 在Activity被系统杀死的时候保存一些必要的数据
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("checkedpPowerStationBean",checkedpPowerStationBean);
        outState.putBoolean("coerceDialogShowState",coerceDialogShowState);
        super.onSaveInstanceState(outState);
    }
}
