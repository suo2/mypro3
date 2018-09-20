package com.huawei.solarsafe.view.stationmanagement;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.bean.device.PvBean2;
import com.huawei.solarsafe.bean.device.PvListInfo;
import com.huawei.solarsafe.bean.stationmagagement.BindDevPvInfo;
import com.huawei.solarsafe.bean.stationmagagement.CreateStationArgs;
import com.huawei.solarsafe.bean.stationmagagement.DevCapByIdData;
import com.huawei.solarsafe.bean.stationmagagement.DevCapByIdDataBean;
import com.huawei.solarsafe.bean.stationmagagement.SubDev;
import com.huawei.solarsafe.presenter.stationmanagement.ChangeStationPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.MySpinner;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.stationmanagement.changestationinfo.IChangeStationView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupStringSettingFragment extends CreateBaseFragmnet implements View.OnClickListener, PullToRefreshListView.OnRefreshListener2,IChangeStationView {
    private View mainView;
    private ChangeStationPresenter changeStationPresenter;
    private PullToRefreshListView devListView;
    private CheckBox cbCheckAll;
    private Button btnSetting;
    private Button btnCapacitySetting;
    private LinearLayout llContainer;
    private String[] devLxStrings;
    private MySpinner mySpinner;
    private List<SubDev> devList = new ArrayList<>();//原始列表数据源
    private DevListAdapter adapter;
    private View emptyView;
    private List<SubDev> devListCurrent = new ArrayList<>();//真实列表数据源
    private boolean  isCheckMore = false;//是否为详细配置
    private boolean isCheckCap = false;//是否为仅[配置组串容量
    private List<SubDev> devListCheck = new ArrayList<>();//被勾选的item数据
    private ArrayList<String> ensList = new ArrayList<>();//被勾选的item设备id集合
    private ArrayList<Integer> typeIds = new ArrayList<>();//被勾选的item设备类型集合
    private int pvSize = 2;//默认配置组串数
    private int maxSize;//可配置最大组串数
    private int v3MaxSize;//V3逆变器的最大组串数，这个值由服务器返回
    private Dialog popupWindow;
    private EditText etPopCapacity;
    private TextView tvPopCancel;
    private TextView tvPopConfirm;
    private int etidText;//仅配置组串容量 填写的组串数
    private Button btAllPop;
    private LinearLayout llCap2_1,llCap2_2,llCap2_3,llCap2_4,llCap2_5,llCap2_6,llCap2_7,llCap2_8,llCap2_9,llCap2_10;
    private LinearLayout llCap_pv1,llCap_pv2,llCap_pv3,llCap_pv4,llCap_pv5,llCap_pv6,llCap_pv7,llCap_pv8,llCap_pv9,llCap_pv10,
            llCap_pv11,llCap_pv12,llCap_pv13,llCap_pv14,llCap_pv15,llCap_pv16,llCap_pv17,llCap_pv18,llCap_pv19,llCap_pv20;
    private EditText edCap_pv1,edCap_pv2,edCap_pv3,edCap_pv4,edCap_pv5,edCap_pv6,edCap_pv7,edCap_pv8,edCap_pv9,edCap_pv10,
            edCap_pv11, edCap_pv12,edCap_pv13,edCap_pv14,edCap_pv15,edCap_pv16,edCap_pv17,edCap_pv18,edCap_pv19,edCap_pv20;
    private List<LinearLayout> llList = new ArrayList<>();//每个PV框集合
    private List<LinearLayout> llListCap = new ArrayList<>();//每行PV框集合
    private List<EditText> edList = new ArrayList<>();//每个PV输入框集合
    private List<String> sList = new ArrayList<>();//当前设备仅配置组串容量 所有组串配置数据
    private List<String> noNullList = new ArrayList<>();//当前设备仅配置组串容量 有效组串配置数据
    private String[] esnStrings;//被勾选的item设备id数组
    private DevCapByIdDataBean devCapByIdDataBean;
    private int dataNum;//仅配置组串容量 请求返回的配置组串数
    private int saveNum;//仅配置组串容量 实际配置组串数
    private int saveDetailNum;//详情配置的数量
    private List<String> haveSetIdList;
    private List<SubDev> noSetIdList = new ArrayList<>();//没有配置过组串容量的
    private List<SubDev> devListCheckStro = new ArrayList<>();//没有配置过组串容量的前一次历史记录
    private List<String> haveDetailId = new ArrayList<>();
    private Map<String, PvBean2> pvinfoMChildBeanMap;
    //为了拿到配置详情后的设备的组串容量
    private List<Integer> intTagLis;
    private List<Integer> stringCap ;
    //是否已经进入组串详情设置过 对应的三种设备类型
    private boolean isSetCasInv = false;
    private boolean isSetHousInv = false;
    private boolean isSetDcInv = false;
    //一键开站需求新增
    private boolean isHouseholdInverter=false;//选中的设备是否是户用逆变器
    private int householdInverterCount;//列表中户用逆变器数量
    private int alreadySetingHouseholdInverterCount=0;//列表中已配置容量的户用逆变器数量
    private double usedCapacity=0;//已消耗的配置容量
    String meanCapacity;//未配置容量的户用逆变器默认容量
    int defaultPvSize=-1;//未配置容量的户用逆变器默认PV数量
    private boolean isAllHuaweiInverter=false;//待配置设备是否全部是华为逆变器
    double temp;
    private HashMap<String,ArrayList<String>> yetConfigDevicePVDatasMap;//记录手动配置组串容量的设备组串容量数据map
    public boolean isStartPvArithmetic=true;
    private static final String TAG = "GroupStringSettingFragm";
    private DecimalFormat decimalFormat;

    //初始化列表数据源
    public void setDevList(List<SubDev> devList) {
        this.devList = devList;
        cbCheckAll.setChecked(false);
        for (SubDev s : this.devList) {
            s.setCheck(false);
        }
        devListCurrent.clear();
        devListCurrent.addAll(devList);
        for (SubDev s : devListCurrent) {
            s.setCheck(false);
        }
        adapter = new DevListAdapter(devListCurrent);
        if (devListView != null) {
            devListView.setAdapter(adapter);
        }
        if (mySpinner != null) {
            mySpinner.setSelection(0);
        }
        Map<String,List<String>> map = new HashMap<>();
        List<String> idString = new ArrayList<>();//列表数据源设备id集合
        if(devList.size() != 0){
            for (int i = 0; i < devList.size(); i++) {
                idString.add(devList.get(i).getId() + "");
            }
            map.put("devIds",idString);
            showLoading();
            Gson gson = new Gson();
            String s = gson.toJson(map);
            changeStationPresenter.queryDevPVInfo(s);//请求列表中设备组串容量数据
        }
    }

    public GroupStringSettingFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeStationPresenter = new ChangeStationPresenter();
        changeStationPresenter.setView(this);
        yetConfigDevicePVDatasMap=new HashMap<>();
        decimalFormat=new DecimalFormat("0");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_group_string_setting, container, false);
        devListView = (PullToRefreshListView) mainView.findViewById(R.id.dev_listview);
        devListView.setOnRefreshListener(this);
        devListView.setMode(PullToRefreshBase.Mode.BOTH);
        emptyView = View.inflate(getActivity(), R.layout.empty_view, null);
        TextView tvEmpty = (TextView) emptyView.findViewById(R.id.tv_empty);
        tvEmpty.setText(R.string.no_dev_can_setting_groupstring_str);
        devListView.setEmptyView(emptyView);
        adapter = new DevListAdapter(devList);
        devListView.setAdapter(adapter);
        cbCheckAll = (CheckBox) mainView.findViewById(R.id.cb_check_all);
        cbCheckAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbCheckAll.isChecked()) {
                    cbCheckAll.setChecked(true);
                    for (SubDev s : devListCurrent) {
                        s.setCheck(true);
                    }
                } else {
                    cbCheckAll.setChecked(false);
                    for (SubDev s : devListCurrent) {
                        s.setCheck(false);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        btnSetting = (Button) mainView.findViewById(R.id.btn_setting);
        btnSetting.setOnClickListener(this);
        btnCapacitySetting = (Button) mainView.findViewById(R.id.btn_capacity_setting);
        btnCapacitySetting.setOnClickListener(this);
        mySpinner = (MySpinner) mainView.findViewById(R.id.spinner_search_option_sblx);
        devLxStrings = new String[]{getString(R.string.all_of), getString(R.string.zc_invrter_str), getString(R.string.household_inverter_str), getString(R.string.dcjs_str)};
        ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.custom_spiner_text_item,
                devLxStrings);
        spinnerAdapter1.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        mySpinner.setAdapter(spinnerAdapter1);
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cbCheckAll.setChecked(false);
                if (cbCheckAll.isChecked()) {
                    cbCheckAll.setChecked(true);
                    for (SubDev s : devListCurrent) {
                        s.setCheck(true);
                    }
                } else {
                    cbCheckAll.setChecked(false);
                    for (SubDev s : devListCurrent) {
                        s.setCheck(false);
                    }
                }
                if (position == 0) {
                    devListCurrent.clear();
                    devListCurrent.addAll(devList);
                    adapter = new DevListAdapter(devListCurrent);
                    devListView.setAdapter(adapter);
                } else if (position == 1) {
                    devListCurrent.clear();
                    for (SubDev s : devList) {
                        if (s.getDevTypeId().equals(DevTypeConstant.INVERTER_DEV_TYPE) ) {
                            devListCurrent.add(s);
                        }
                    }
                    adapter = new DevListAdapter(devListCurrent);
                    devListView.setAdapter(adapter);
                } else if (position == 2) {
                    devListCurrent.clear();
                    for (SubDev s : devList) {
                        if (s.getDevTypeId() == DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE) {
                            devListCurrent.add(s);
                        }
                    }
                    adapter = new DevListAdapter(devListCurrent);
                    devListView.setAdapter(adapter);
                } else if (position == 3) {
                    devListCurrent.clear();
                    for (SubDev s : devList) {
                        if (s.getDevTypeId() == DevTypeConstant.DCJS_DEV_TYPE) {
                            devListCurrent.add(s);
                        }
                    }
                    adapter = new DevListAdapter(devListCurrent);
                    devListView.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return mainView;
    }

    CreateStationArgs.DevinfoMBean devinfoM = new CreateStationArgs().new DevinfoMBean();//详情的
    Map<String, CreateStationArgs.DevItemBean> devMmap = new HashMap<>();//详情的
    
    CreateStationArgs.PvCapMapBean pvBean = new CreateStationArgs().new PvCapMapBean();//容量的
    HashMap devItemMap = new HashMap<>();

    /**
     * 将设置的参数存入CreateStationArgs
     * @param args
     * @return
     */
    @Override
    public boolean validateAndSetArgs(CreateStationArgs args) {
        if (checkDevAllset()) {//检查所有item是否配置完毕
            //保存详细配置数据
            devinfoM.setSize(saveDetailNum);
            devinfoM.setMap(devMmap);
            args.setDevinfoM(devinfoM);
            HashMap pvMap = new HashMap<>();
            HashMap pvItemMap = new HashMap<>();
            if(intTagLis != null){
                for (int i = 0; i < intTagLis.size(); i++) {
                    String key = intTagLis.get(i) + "";
                    pvItemMap.put(key, stringCap.get(i) + "");
                }
                pvMap.put("map",pvItemMap);
                pvMap.put("size",intTagLis.size());

                for (int i = 0; i < haveDetailId.size(); i++) {
                    for (int j = 0; j < devItemMap.size(); j++) {
                        if(devItemMap.containsKey(haveDetailId.get(i))){
                            devItemMap.remove(haveDetailId.get(i));
                        }
                    }
                    for (int j = 0; j < devList.size(); j++) {
                        if(haveDetailId.get(i).equals(devList.get(j).getId() + "")){
                            HashMap devDataMap = new HashMap<>();
                            devDataMap.put("id",devList.get(j).getId() +"");
                            devDataMap.put("busiCode",devList.get(j).getBusiCode());
                            devDataMap.put("devTypeId",devList.get(j).getDevTypeId() +"");
                            devDataMap.put("esnCode",devList.get(j).getEsnCode() +"");
                            devDataMap.put("pvCapMap",pvMap);
                            devItemMap.put(haveDetailId.get(i),devDataMap);
                        }
                    }
                }
            }
            pvBean.setMap(devItemMap);
            pvBean.setSize(devItemMap.size());
            args.setPvCapMap(pvBean);
            return true;
        } else {
            ToastUtil.showMessage(getString(R.string.have_net_setpv_dev_notice_str));
            return false;
        }
    }

    /**
     * 检查所有item是否配置完毕方法
     * @return
     */
    private boolean checkDevAllset() {
        boolean isSetAll = true;
        for (SubDev s : devList) {
            if (!s.isSet()) {
                isSetAll = false;
            }
        }
        return isSetAll;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_setting://详细配置按钮
                if (devListCurrent == null || devListCurrent.size() == 0) {
                    DialogUtil.showErrorMsg(getActivity(), getString(R.string.no_dev_can_setting_groupstring_str));
                } else {
                    //详情配置
                    isCheckMore = true;
                    isCheckCap = false;
                    getChackedData();//进行配置
                }
                break;

            case R.id.btn_capacity_setting://仅配置容量按钮 
                //仅容量配置
                isCheckMore = false;
                isCheckCap = true;
                getChackedData();//进行配置
                break;
            case R.id.tv_popupwindow_cancel:
                popupWindow.dismiss();
                break;
            case R.id.tv_popupwindow_confirm:
                if (TextUtils.isEmpty(etPopCapacity.getText().toString().trim())) {
                    ToastUtil.showMessage(getString(R.string.input_pv_count));
                    return;
                }
                for (int i = 0; i < saveNum; i++) {
                    if (edList.get(i).getText().toString().trim().length() > 1 && edList.get(i).getText().toString().trim().startsWith("0")){
                        edList.get(i).setError(getString(R.string.not_start_0));
                        return;
                    }
                    if (TextUtils.isEmpty(edList.get(i).getText().toString().trim()) || Double.valueOf(edList.get(i).getText().toString().trim()) < 0 || Double.valueOf(edList.get(i).getText().toString().trim()) > 150000) {
                        edList.get(i).setError(getResources().getString(R.string.cap_hint));
                        return;
                    }
                }
                //当前后两次勾选一样时，回填数据
                devListCheckStro.clear();
                if(devListCheck.size() != 0){
                    devListCheckStro.addAll(devListCheck);
                }
                //没有设置过的，并且是在勾选状态
                noSetIdList.clear();
                if(haveSetIdList.size() == 0){
                    noSetIdList.addAll(devListCheck);
                }else {
                    for (int i = 0; i < devListCheck.size(); i++) {
                        for (int j = 0; j < haveSetIdList.size(); j++) {
                            if(!haveSetIdList.get(j).equals(devListCheck.get(i).getId() + "") && !noSetIdList.contains(devListCheck.get(i))){
                                noSetIdList.add(devListCheck.get(i));
                            }
                        }
                    }
                }
                for (int i = 0; i < noSetIdList.size(); i++) {
                    for (int j = 0; j < devList.size(); j++) {
                        if(noSetIdList.get(i).getId().equals(devList.get(j).getId())){
                            devList.get(j).setSet(true);
                        }
                    }
                }
                HashMap pvMap = new HashMap<>();
                HashMap pvItemMap = new HashMap<>();
                for (int i = 0; i < saveNum; i++) {
                    String key = i + 1 + "";
                    pvItemMap.put(key, edList.get(i).getText().toString().trim());
                }
                pvMap.put("map",pvItemMap);
                pvMap.put("size",saveNum);
                for (int i = 0; i < devListCheck.size(); i++) {
                    HashMap devDataMap = new HashMap<>();
                    devDataMap.put("id",devListCheck.get(i).getId() +"");
                    devDataMap.put("busiCode",devListCheck.get(i).getBusiCode());
                    devDataMap.put("devTypeId",devListCheck.get(i).getDevTypeId() +"");
                    devDataMap.put("esnCode",devListCheck.get(i).getEsnCode() +"");
                    devDataMap.put("pvCapMap",pvMap);

                    devItemMap.put(devListCheck.get(i).getId() +"",devDataMap);
                }
                popupWindow.dismiss();
                break;
            //批量设置
            case R.id.bt_all_popupwindow:
                //【安全特性】f:Switch statement found where one case falls through to the next case    【修改人】zhaoyufeng
                if(!TextUtils.isEmpty(edList.get(0).getText().toString().trim())){
                    for (int i = 1; i < saveNum; i++) {
                        edList.get(i).setText(edList.get(0).getText().toString().trim());
                    }
                }
                ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(llContainer.getWindowToken(), 0);
                break;
            case R.id.llContainer:
                ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            default:
                break;
        }
    }

    /**
     * 进行配置
     */
    private void getChackedData() {
        //清除之前被勾选的数据
        devListCheck.clear();
        ensList.clear();
        typeIds.clear();
        v3MaxSize = 0;
        boolean isOkToSetting = true;//被勾选的设备是否为同一种设备类型
        int typeId = 0;//设备类型序号
        LocalData localData = LocalData.getInstance();
        long userId = GlobalConstants.userId;
        //将被选中的数据添加进相应的集合
        for (SubDev s : devListCurrent) {
            if (s.isCheck()) {
                typeIds.add(s.getDevTypeId());
                ensList.add(s.getId() + "");
                devListCheck.add(s);
                if(!TextUtils.isEmpty(s.getPvNum()) && Integer.valueOf(s.getPvNum()) > v3MaxSize){
                    v3MaxSize = Integer.valueOf(s.getPvNum());
                }
            }
        }
        //将被选中的item设备id添加进数组
        esnStrings = new String[ensList.size()];
        for (int i = 0; i < ensList.size(); i++) {
            esnStrings[i] = ensList.get(i);
        }
        if (esnStrings.length == 0) {
            DialogUtil.showErrorMsg(getActivity(), getString(R.string.select_one_item_notice));
            return;
        }
        //检查勾选的设备是否为同一种设备类型
        isOkToSetting = true;
        for (int i = 0; i < typeIds.size(); i++) {
            if (i == 0) {
                typeId = typeIds.get(0);
            } else {
                if (typeId == typeIds.get(i)) {
                    typeId = typeIds.get(i);
                } else {
                    isOkToSetting = false;
                }
            }
        }
        if (!isOkToSetting) {
            DialogUtil.showErrorMsg(getActivity(), getString(R.string.please_selecte_dev_eq_notice_str_));
            return;
        }
        //确定默认组串数
        if (typeId == DevTypeConstant.INVERTER_DEV_TYPE) {//逆变器类型,组串式
            isHouseholdInverter=true;
            pvSize = 8;
            maxSize = 14;
            if(!isSetCasInv && isCheckMore){
                pvCasListInfo = (PvListInfo) localData.getCasInvBean(userId + "pvCasListInfo");
            }
        } else if (typeId == DevTypeConstant.DCJS_DEV_TYPE) {// 直流汇流箱
            isHouseholdInverter=false;
            pvSize = 14;
            maxSize = 20;
            if(!isSetDcInv && isCheckMore){
                pvDcListInfo = (PvListInfo) localData.getDcInvBean(userId + "pvDcListInfo");
            }
        } else if (typeId == DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE) {// 逆变器类型,户用逆变器
            isHouseholdInverter=true;
            pvSize = 2;
            maxSize = 8;
            if(!isSetHousInv && isCheckMore){
                pvHousListInfo = (PvListInfo) localData.getHouseInvBean(userId + "pvHousListInfo");
            }
        }else{
            isHouseholdInverter=false;
        }
        if(v3MaxSize > maxSize){
            maxSize = v3MaxSize;
        }
        //详细配置
        if(isCheckMore){
            Intent intent = new Intent(getActivity(), GroupStringConfigActivity.class);
            if (idList != null && Arrays.equals(idList, esnStrings) ) {
                intent.putExtra("pvList", pvListInfo);
                if (pvListInfo.getPvList() != null && pvListInfo.getPvList().length > 0) {
                    intent.putExtra("pvSize", pvListInfo.getPvList().length);
                } else {
                    intent.putExtra("pvSize", pvSize);
                }
            } else {
                if (typeId == DevTypeConstant.INVERTER_DEV_TYPE) {
                    if (pvCasListInfo != null) {
                        intent.putExtra("pvList", pvCasListInfo);
                        if (pvCasListInfo.getPvList() != null && pvCasListInfo.getPvList().length > 0) {
                            intent.putExtra("pvSize", pvCasListInfo.getPvList().length);
                        } else {
                            intent.putExtra("pvSize", pvSize);
                        }
                    } else {
                        intent.putExtra("pvSize", pvSize);
                    }
                } else if (typeId == DevTypeConstant.DCJS_DEV_TYPE) {
                    if (pvDcListInfo != null) {
                        intent.putExtra("pvList", pvDcListInfo);
                        if (pvDcListInfo.getPvList() != null && pvDcListInfo.getPvList().length > 0) {
                            intent.putExtra("pvSize", pvDcListInfo.getPvList().length);
                        } else {
                            intent.putExtra("pvSize", pvSize);
                        }
                    } else {
                        intent.putExtra("pvSize", pvSize);
                    }
                } else if (typeId == DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE) {
                    if (pvHousListInfo != null) {
                        intent.putExtra("pvList", pvHousListInfo);
                        if (pvHousListInfo.getPvList() != null && pvHousListInfo.getPvList().length > 0) {
                            intent.putExtra("pvSize", pvHousListInfo.getPvList().length);
                        } else {
                            intent.putExtra("pvSize", pvSize);
                        }
                    } else {
                        intent.putExtra("pvSize", pvSize);
                    }
                }
            }
            intent.putExtra("esnList", esnStrings);
            intent.putExtra("typeId",typeId);
            intent.putExtra("maxSize",maxSize);
            startActivityForResult(intent, 100);
        }
        //仅配置组串容量
        if(isCheckCap){
            showCapacityPopupWindow();//显示仅配置容量对话框
        }
    }

    /**
     * 显示仅配置容量对话框方法
     */
    private void showCapacityPopupWindow() {
        //清空之前显示的数据
        llList.clear();
        llListCap.clear();
        edList.clear();
        View view = View.inflate(getContext(),R.layout.capacity_popwindow_layout,null);
        popupWindow=new Dialog(getContext(),R.style.zxing_help_dilog);
        popupWindow.setContentView(view);
        etPopCapacity = (EditText) view.findViewById(R.id.et_pop_capacity);//组串数量输入框
        etPopCapacity.setFilters(new InputFilter[]{Utils.numberZeroFilter()});
        etPopCapacity.setText(pvSize + "");
        etPopCapacity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String trim = s.toString().trim();
                if(!TextUtils.isEmpty(trim)){
                    try {
                        etidText = Integer.valueOf(trim);//组串数
                    }catch (NumberFormatException e){
                        etidText = maxSize+1;
                        Log.e(TAG, "afterTextChanged: "+e.getMessage());
                    }
                    showUserSetStringSize();//将配置数据展示在对话框中
                }
            }
        });
        saveNum = pvSize;//实际配置组串数等于默认值
        //初始化对话框控件
        initPopupwindow(view);
        //初始化对话框数据
        initPopupwindowPv();
        popupWindow.show();
    }


    /**
     * @param view
     * popupwindow的初始化
     */
    private void initPopupwindow(View view) {
        llContainer= (LinearLayout) view.findViewById(R.id.llContainer);
        llContainer.setOnClickListener(this);
        btAllPop = (Button) view.findViewById(R.id.bt_all_popupwindow);
        btAllPop.setOnClickListener(this);
        tvPopCancel = (TextView) view.findViewById(R.id.tv_popupwindow_cancel);
        tvPopCancel.setOnClickListener(this);
        tvPopConfirm = (TextView) view.findViewById(R.id.tv_popupwindow_confirm);
        tvPopConfirm.setOnClickListener(this);
        llCap2_1 = (LinearLayout) view.findViewById(R.id.ll_cap_pv2_1);
        llCap2_2 = (LinearLayout) view.findViewById(R.id.ll_cap_pv2_2);
        llCap2_3 = (LinearLayout) view.findViewById(R.id.ll_cap_pv2_3);
        llCap2_4 = (LinearLayout) view.findViewById(R.id.ll_cap_pv2_4);
        llCap2_5 = (LinearLayout) view.findViewById(R.id.ll_cap_pv2_5);
        llCap2_6 = (LinearLayout) view.findViewById(R.id.ll_cap_pv2_6);
        llCap2_7 = (LinearLayout) view.findViewById(R.id.ll_cap_pv2_7);
        llCap2_8 = (LinearLayout) view.findViewById(R.id.ll_cap_pv2_8);
        llCap2_9 = (LinearLayout) view.findViewById(R.id.ll_cap_pv2_9);
        llCap2_10 = (LinearLayout) view.findViewById(R.id.ll_cap_pv2_10);
        llListCap.add(llCap2_1);
        llListCap.add(llCap2_2);
        llListCap.add(llCap2_3);
        llListCap.add(llCap2_4);
        llListCap.add(llCap2_5);
        llListCap.add(llCap2_6);
        llListCap.add(llCap2_7);
        llListCap.add(llCap2_8);
        llListCap.add(llCap2_9);
        llListCap.add(llCap2_10);
        llCap_pv1 = (LinearLayout) view.findViewById(R.id.ll_cap_pv1);
        llCap_pv2 = (LinearLayout) view.findViewById(R.id.ll_cap_pv2);
        llCap_pv3 = (LinearLayout) view.findViewById(R.id.ll_cap_pv3);
        llCap_pv4 = (LinearLayout) view.findViewById(R.id.ll_cap_pv4);
        llCap_pv5 = (LinearLayout) view.findViewById(R.id.ll_cap_pv5);
        llCap_pv6 = (LinearLayout) view.findViewById(R.id.ll_cap_pv6);
        llCap_pv7 = (LinearLayout) view.findViewById(R.id.ll_cap_pv7);
        llCap_pv8 = (LinearLayout) view.findViewById(R.id.ll_cap_pv8);
        llCap_pv9 = (LinearLayout) view.findViewById(R.id.ll_cap_pv9);
        llCap_pv10 = (LinearLayout) view.findViewById(R.id.ll_cap_pv10);
        llCap_pv11 = (LinearLayout) view.findViewById(R.id.ll_cap_pv11);
        llCap_pv12 = (LinearLayout) view.findViewById(R.id.ll_cap_pv12);
        llCap_pv13 = (LinearLayout) view.findViewById(R.id.ll_cap_pv13);
        llCap_pv14 = (LinearLayout) view.findViewById(R.id.ll_cap_pv14);
        llCap_pv15 = (LinearLayout) view.findViewById(R.id.ll_cap_pv15);
        llCap_pv16 = (LinearLayout) view.findViewById(R.id.ll_cap_pv16);
        llCap_pv17 = (LinearLayout) view.findViewById(R.id.ll_cap_pv17);
        llCap_pv18 = (LinearLayout) view.findViewById(R.id.ll_cap_pv18);
        llCap_pv19 = (LinearLayout) view.findViewById(R.id.ll_cap_pv19);
        llCap_pv20 = (LinearLayout) view.findViewById(R.id.ll_cap_pv20);
        llList.add(llCap_pv1);
        llList.add(llCap_pv2);
        llList.add(llCap_pv3);
        llList.add(llCap_pv4);
        llList.add(llCap_pv5);
        llList.add(llCap_pv6);
        llList.add(llCap_pv7);
        llList.add(llCap_pv8);
        llList.add(llCap_pv9);
        llList.add(llCap_pv10);
        llList.add(llCap_pv11);
        llList.add(llCap_pv12);
        llList.add(llCap_pv13);
        llList.add(llCap_pv14);
        llList.add(llCap_pv15);
        llList.add(llCap_pv16);
        llList.add(llCap_pv17);
        llList.add(llCap_pv18);
        llList.add(llCap_pv19);
        llList.add(llCap_pv20);
        edCap_pv1 = (EditText) view.findViewById(R.id.ed_cap_pv1);
        edCap_pv2 = (EditText) view.findViewById(R.id.ed_cap_pv2);
        edCap_pv3 = (EditText) view.findViewById(R.id.ed_cap_pv3);
        edCap_pv4 = (EditText) view.findViewById(R.id.ed_cap_pv4);
        edCap_pv5 = (EditText) view.findViewById(R.id.ed_cap_pv5);
        edCap_pv6 = (EditText) view.findViewById(R.id.ed_cap_pv6);
        edCap_pv7 = (EditText) view.findViewById(R.id.ed_cap_pv7);
        edCap_pv8 = (EditText) view.findViewById(R.id.ed_cap_pv8);
        edCap_pv9 = (EditText) view.findViewById(R.id.ed_cap_pv9);
        edCap_pv10 = (EditText) view.findViewById(R.id.ed_cap_pv10);
        edCap_pv11 = (EditText) view.findViewById(R.id.ed_cap_pv11);
        edCap_pv12 = (EditText) view.findViewById(R.id.ed_cap_pv12);
        edCap_pv13 = (EditText) view.findViewById(R.id.ed_cap_pv13);
        edCap_pv14 = (EditText) view.findViewById(R.id.ed_cap_pv14);
        edCap_pv15 = (EditText) view.findViewById(R.id.ed_cap_pv15);
        edCap_pv16 = (EditText) view.findViewById(R.id.ed_cap_pv16);
        edCap_pv17 = (EditText) view.findViewById(R.id.ed_cap_pv17);
        edCap_pv18 = (EditText) view.findViewById(R.id.ed_cap_pv18);
        edCap_pv19 = (EditText) view.findViewById(R.id.ed_cap_pv19);
        edCap_pv20 = (EditText) view.findViewById(R.id.ed_cap_pv20);
        edList.add(edCap_pv1);
        edList.add(edCap_pv2);
        edList.add(edCap_pv3);
        edList.add(edCap_pv4);
        edList.add(edCap_pv5);
        edList.add(edCap_pv6);
        edList.add(edCap_pv7);
        edList.add(edCap_pv8);
        edList.add(edCap_pv9);
        edList.add(edCap_pv10);
        edList.add(edCap_pv11);
        edList.add(edCap_pv12);
        edList.add(edCap_pv13);
        edList.add(edCap_pv14);
        edList.add(edCap_pv15);
        edList.add(edCap_pv16);
        edList.add(edCap_pv17);
        edList.add(edCap_pv18);
        edList.add(edCap_pv19);
        edList.add(edCap_pv20);
        for (int i = 0; i < edList.size(); i++) {
            edList.get(i).setFilters(new InputFilter[]{Utils.numberNumFilter(6)});
        }

    }

    /**
     * 配置仅配置容量对话框数据
     */
    private void initPopupwindowPv() {
        //宋平修改 问题单号：51093（退出后再次进去没有保存之前的数据  这个问题只有新增电站才存在，修改电站，当设置pv容量后就直接发请求了）
        //反填之前配置的数据
        if(devListCheck.size() != 0 && devListCheck.size() == devListCheckStro.size() && devListCheck.containsAll(devListCheckStro)){
            if(devListCheck.size() != 0){
                HashMap devDataMap = (HashMap) devItemMap.get(devListCheck.get(0).getId() + "");
                if(devDataMap != null){
                    HashMap pvMap = (HashMap) devDataMap.get("pvCapMap");
                    if(pvMap != null){
                        HashMap pv = (HashMap) pvMap.get("map");
                         int saveNum = (int) pvMap.get("size");
                        showUserHaveSetString(saveNum);
                        for (int i = 0; i < saveNum; i++) {
                            edList.get(i).setText((String)pv.get(String.valueOf(i + 1)));
                        }
                    }
                }
            }
        }else {
            //默认数据配置
            if(pvSize == 2){
                for (int i = 0; i < 2; i++) {
                    edList.get(i).setText("0");
                }
                llCap2_2.setVisibility(View.GONE);
                llCap2_3.setVisibility(View.GONE);
                llCap2_4.setVisibility(View.GONE);
                llCap2_5.setVisibility(View.GONE);
                llCap2_6.setVisibility(View.GONE);
                llCap2_7.setVisibility(View.GONE);
                llCap2_8.setVisibility(View.GONE);
                llCap2_9.setVisibility(View.GONE);
                llCap2_10.setVisibility(View.GONE);
        }else if(pvSize == 8){
            for (int i = 0; i < 8; i++) {
                edList.get(i).setText("0");
            }
            llCap2_5.setVisibility(View.GONE);
            llCap2_6.setVisibility(View.GONE);
            llCap2_7.setVisibility(View.GONE);
            llCap2_8.setVisibility(View.GONE);
            llCap2_9.setVisibility(View.GONE);
            llCap2_10.setVisibility(View.GONE);
        }else {
            for (int i = 0; i < 14; i++) {
                edList.get(i).setText("0");
            }
            llCap2_8.setVisibility(View.GONE);
            llCap2_9.setVisibility(View.GONE);
            llCap2_10.setVisibility(View.GONE);
        }
            if (isHouseholdInverter){//如果是逆变器
                setDefaultHouseholdInverterCapacity();//配置默认容量
            }
        }
    }

    /**
     * 用户自定义组串数时，显示view
     */
    private void showUserSetStringSize() {
        int numL;//对话框显示行数
        if(etidText > maxSize){
            etPopCapacity.setText(maxSize + "");
            etidText = maxSize;
        }
        saveNum = etidText;
        if(etidText % 2 == 0){
            numL = etidText / 2;
        }else {
            numL = (etidText / 2) +1;
        }
        for (int i = 0; i < numL; i++) {
            llListCap.get(i).setVisibility(View.VISIBLE);
        }
        for (int i = numL; i < 10; i++) {
            llListCap.get(i).setVisibility(View.GONE);
        }

        for (int i = 0; i < etidText; i++) {
            llList.get(i).setVisibility(View.VISIBLE);
        }
        for (int i = etidText; i < 20; i++) {
            llList.get(i).setVisibility(View.INVISIBLE);
        }
        if(etidText != dataNum){
            for (int i = 0; i < etidText; i++) {
                edList.get(i).setText("0");
            }
        }else {
            for (int i = 0; i < etidText; i++) {
                if(TextUtils.isEmpty(sList.get(i))){
                    edList.get(i).setText("0");
                }else {
                    edList.get(i).setText(sList.get(i));
                }
            }
        }
    }
    /**
     * 当第一次配后的组串数，显示view
     */
    private void showUserHaveSetString(int numSet) {
        int numL;//对话框显示行数
        if(numSet > maxSize){
            numSet = maxSize;
        }
        etPopCapacity.setText(numSet + "");
        saveNum = numSet;
        if(numSet % 2 == 0){
            numL = numSet / 2;
        }else {
            numL = (numSet / 2) +1;
        }
        for (int i = 0; i < numL; i++) {
            llListCap.get(i).setVisibility(View.VISIBLE);
        }
        for (int i = numL; i < 10; i++) {
            llListCap.get(i).setVisibility(View.GONE);
        }
        for (int i = 0; i < numSet; i++) {
            llList.get(i).setVisibility(View.VISIBLE);
        }
        for (int i = numSet; i < 20; i++) {
            llList.get(i).setVisibility(View.INVISIBLE);
        }
    }

    private PvListInfo pvListInfo,pvCasListInfo,pvDcListInfo,pvHousListInfo;
    private String[] idList;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == RESULT_OK && requestCode == 100) {
                if (data != null) {
                    Bundle bundle = data.getBundleExtra("bundle");
                    stringCap = bundle.getIntegerArrayList("stringCap");
                    intTagLis = bundle.getIntegerArrayList("intTagList");
                    pvListInfo = (PvListInfo) bundle.getSerializable("pvList");
                    PvBean2[] pvList = null;
                    if (pvListInfo != null) {
                        pvList = pvListInfo.getPvList();
                    }
                    idList = data.getStringArrayExtra("esnList");
                    if(idList != null){
                        setFlashData(pvList, idList);
                    }
                    CreateStationActivity activity = (CreateStationActivity) getActivity();
                    //对三种设备类型是否进行过设置进行标记
                    if (typeIds.get(0) == DevTypeConstant.INVERTER_DEV_TYPE) {
                        activity.setCasPvListInfo(pvListInfo);
                        isSetCasInv = true;
                    } else if (typeIds.get(0) == DevTypeConstant.DCJS_DEV_TYPE) {
                        activity.setDcPvListInfo(pvListInfo);
                        isSetDcInv = true;
                    } else if (typeIds.get(0) == DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE) {
                        activity.setHousPvListInfo(pvListInfo);
                        isSetHousInv = true;
                    }
                }
            }
        } catch (Exception e){
            Log.e(TAG, "onActivityResult: " + e.getMessage());
        }
    }

    //组串设置参数
    private PvBean2[] pvList;
    public void setFlashData(PvBean2[] pvList, String[] esnList) {
        this.pvList = pvList;
        if (pvList != null) {
            CreateStationArgs.PvinfoMChildBean pvinfoMChildBean = new CreateStationArgs().new PvinfoMChildBean();
            for (String esn : esnList) {
                for (SubDev s : devList) {
                    if (esn.equals(s.getId() + "")) {
                        s.setSet(true);
                        if(!haveDetailId.contains(esn)){
                            haveDetailId.add(esn);
                            saveDetailNum ++;
                        }
                    }
                }
                pvinfoMChildBean.setSize(pvList.length);
                pvinfoMChildBeanMap = new HashMap<>();
                for (int i = 0; i < pvList.length; i++) {
                    pvinfoMChildBeanMap.put(String.valueOf(i + 1), pvList[i]);
                }
                pvinfoMChildBean.setMap(pvinfoMChildBeanMap);
            }
            for (String esn : esnList) {
                for (SubDev dev : devList) {
                    if (esn.equals(dev.getId() + "")) {
                        CreateStationArgs.DevItemBean devItemBean = new CreateStationArgs().new DevItemBean();
                        devItemBean.setBusiCode(dev.getBusiCode());
                        devItemBean.setDevTypeId(dev.getDevTypeId() + "");
                        devItemBean.setId(dev.getId() + "");
                        devItemBean.setEsnCode(dev.getStationCode());
                        devItemBean.setPvInfoMap(pvinfoMChildBean);
                        devMmap.put(dev.getId() + "", devItemBean);
                    }
                }
            }
        }

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.e(TAG, "run: " + e.getMessage());
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        devListView.onRefreshComplete();
                    }
                });
            }
        }).start();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.e(TAG, "run: "+e.getMessage() );
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        devListView.onRefreshComplete();
                    }
                });
            }
        }).start();
    }

    @Override
    public void requestData() {

    }

    /**
     * 获取数据回调
     * @param baseEntity
     */
    @Override
    public void getData(BaseEntity baseEntity) {
        if (isAdded()) {
            dismissLoading();
            if (baseEntity == null) {
                return;
            }
            if (baseEntity instanceof DevCapByIdData) {//请求组串配置信息回调
                DevCapByIdData devCapByIdData = (DevCapByIdData) baseEntity;
                devCapByIdDataBean = devCapByIdData.getDataBean();
                //配置组串容量信息
                if (devCapByIdDataBean != null) {
                    //清空之前数据
                    sList.clear();
                    noNullList.clear();
                    sList.add(devCapByIdDataBean.getPv1());
                    sList.add(devCapByIdDataBean.getPv2());
                    sList.add(devCapByIdDataBean.getPv3());
                    sList.add(devCapByIdDataBean.getPv4());
                    sList.add(devCapByIdDataBean.getPv5());
                    sList.add(devCapByIdDataBean.getPv6());
                    sList.add(devCapByIdDataBean.getPv7());
                    sList.add(devCapByIdDataBean.getPv8());
                    sList.add(devCapByIdDataBean.getPv9());
                    sList.add(devCapByIdDataBean.getPv10());
                    sList.add(devCapByIdDataBean.getPv11());
                    sList.add(devCapByIdDataBean.getPv12());
                    sList.add(devCapByIdDataBean.getPv13());
                    sList.add(devCapByIdDataBean.getPv14());
                    sList.add(devCapByIdDataBean.getPv15());
                    sList.add(devCapByIdDataBean.getPv16());
                    sList.add(devCapByIdDataBean.getPv17());
                    sList.add(devCapByIdDataBean.getPv18());
                    sList.add(devCapByIdDataBean.getPv19());
                    sList.add(devCapByIdDataBean.getPv20());
                    for (int i = 0; i < sList.size(); i++) {
                        if (!"null".equals(sList.get(i) + "")) {
                            noNullList.add(sList.get(i));
                        }
                    }
                    dataNum = noNullList.size();
                    //有效配置组串数不为空
                    if (dataNum != 0) {
                        etPopCapacity.setText(dataNum + "");//显示组串数文本
                        showDataCapItem(dataNum);//获取数据后更新对话框UI
                    }
                }
            }
            if(baseEntity instanceof BindDevPvInfo){
                alreadySetingHouseholdInverterCount=0;
                usedCapacity=0;
                BindDevPvInfo bind = (BindDevPvInfo) baseEntity;
                Map<String, String> map = bind.getMap();
                haveSetIdList = bind.getIdList();
                if(haveSetIdList != null && map != null){
                    for (int i = 0; i < haveSetIdList.size(); i++) {
                        for (int j = 0; j < devList.size(); j++) {
                            if(haveSetIdList.get(i).equals(devList.get(j).getId() + "")){
                                devList.get(j).setCapacity(map.get(haveSetIdList.get(i)));
                                devList.get(j).setSet(true);
                                alreadySetingHouseholdInverterCount++;
                                usedCapacity=usedCapacity+Double.valueOf(map.get(haveSetIdList.get(i)));
                            }
                        }
                    }
                }
                devListCurrent.clear();
                devListCurrent.addAll(devList);
                for (SubDev s : devListCurrent) {
                    s.setCheck(false);
                }
                adapter.setDevList(devListCurrent);
                adapter.notifyDataSetChanged();
                computeDefaultHouseholdInverterCapacity();
            }
        }
    }
    /**
     * 设置仅配置容量对话框显示控件数量(仅配置容量 数据回调后更新)
     * @param size
     */
    private void showDataCapItem(int size) {
        saveNum = size;//实际配置组串数
        int numL;//显示行数
        if(size % 2 == 0){
            numL = size / 2;
        }else {
            numL = (size / 2) +1;
        }
        for (int i = 0; i < numL; i++) {
            llListCap.get(i).setVisibility(View.VISIBLE);
        }
        for (int i = numL; i < 10; i++) {
            llListCap.get(i).setVisibility(View.GONE);
        }
        for (int i = 0; i < size; i++) {
            llList.get(i).setVisibility(View.VISIBLE);
        }
        for (int i = size; i < 20; i++) {
            llList.get(i).setVisibility(View.INVISIBLE);
        }
    }

    //列表数据适配器
    class DevListAdapter extends BaseAdapter {
        private List<SubDev> devList;

        public DevListAdapter(List<SubDev> devList) {
            this.devList = devList;
        }

        public void setDevList(List<SubDev> devList) {
            this.devList = devList;
        }

        @Override
        public int getCount() {
            return devList == null ? 0 : devList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.dev_setting_item, null);
                viewHolder.devName = (TextView) convertView.findViewById(R.id.dev_name);
                viewHolder.devLx = (TextView) convertView.findViewById(R.id.dev_lx);
                viewHolder.devSN = (TextView) convertView.findViewById(R.id.dev_sn);
                viewHolder.devModeNo = (TextView) convertView.findViewById(R.id.dev_mode_no);
                viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.cb_check);
                viewHolder.devCap = (TextView) convertView.findViewById(R.id.dev_string_cap);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final SubDev subDev = devList.get(position);
            viewHolder.devName.setText(subDev.getBusiName());
            viewHolder.devModeNo.setText(subDev.getSoftwareVersion());
            viewHolder.devSN.setText(subDev.getEsnCode());
            viewHolder.devCap.setText(subDev.getCapacity());
            if (DevTypeConstant.INVERTER_DEV_TYPE.equals(subDev.getDevTypeId())) {
                viewHolder.devLx.setText(getString(R.string.zc_invrter_str));
            } else if (DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE.equals(subDev.getDevTypeId())) {
                viewHolder.devLx.setText(getString(R.string.household_inverter_str));
            } else if (DevTypeConstant.DCJS_DEV_TYPE.equals(subDev.getDevTypeId())) {
                viewHolder.devLx.setText(getString(R.string.dcjs_str));
            }
            viewHolder.checkBox.setChecked(subDev.isCheck());
            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (subDev.isCheck()) {
                        subDev.setCheck(false);
                        cbCheckAll.setChecked(false);
                    } else {
                        subDev.setCheck(true);
                    }
                    adapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }

        class ViewHolder {
            TextView devName;
            TextView devLx;
            TextView devSN;
            TextView devModeNo;
            CheckBox checkBox;
            TextView devCap;
        }
    }
    private LoadingDialog loadingDialog;

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
     * 计算未配置容量的设备默认容量方法
     */
    public void computeDefaultHouseholdInverterCapacity(){

        //配置的规划容量
        double SumCapacity=Double.valueOf(((CreateStationActivity)getActivity()).getStationBean().getCapacity());
        //接入电站户用逆变器数量
        householdInverterCount=((CreateStationActivity)getActivity()).getStationBean().getHouseholdInverterCount();

        //取消排除已经设置过得设备
        usedCapacity=0;
        alreadySetingHouseholdInverterCount=0;

        double unusedCapacity=SumCapacity-usedCapacity;
        int notSetingHouseholdInverterCount=householdInverterCount-alreadySetingHouseholdInverterCount;

        if (notSetingHouseholdInverterCount>0 && unusedCapacity>0){
            temp=unusedCapacity/notSetingHouseholdInverterCount;
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
            dataNum=defaultPvSize;
            meanCapacity=decimalFormat.format((temp/defaultPvSize*1000));
        }else{
            isStartPvArithmetic=false;
        }

    }

    /**
     * 设置未配置容量的户用逆变器默认容量方法
     */
    public void setDefaultHouseholdInverterCapacity(){
        if (defaultPvSize!=-1){
            sList.clear();
            for (int i=0;i<defaultPvSize;i++){
                sList.add(meanCapacity);
            }

            etPopCapacity.setText(String.valueOf(defaultPvSize));
            showDataCapItem(defaultPvSize);
        }
    }

    /**
     * 设置所有设备默认容量方法
     */
    public void setAllDeviceDefaultCapacity(){
        int tempPvSize;
        String tempCapacity;

        //判断是否启用算法
        isStartPvArithmetic=true;//是否启用PV算法

        //默认所有设备都为户用逆变器时是华为逆变器
        isAllHuaweiInverter=true;

        if (devList.isEmpty()){
            return;
        }

        int typeId=devList.get(0).getDevTypeId();//设备类型编号
        for(SubDev subDev:devList){

                //判断是否为同一设备类型
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

        if (typeId==DevTypeConstant.DCJS_DEV_TYPE){
            isStartPvArithmetic=false;
        }

        //进行配置
        for (SubDev subDev:devList){
            if (subDev.getDevTypeId().toString().equals(DevTypeConstant.DCJS_DEV_TYPE.toString())){//直流汇流箱
                //计算默认容量
                tempPvSize=14;
                tempCapacity="0";

                subDev.setSet(true);//标记已配置组串
                //配置默认容量
                HashMap pvMap = new HashMap<>();
                HashMap pvItemMap = new HashMap<>();
                for (int i = 0; i < tempPvSize; i++) {
                    String key = i + 1 + "";
                    pvItemMap.put(key, tempCapacity);
                }
                pvMap.put("map",pvItemMap);
                pvMap.put("size",tempPvSize);

                HashMap devDataMap = new HashMap<>();
                devDataMap.put("id",subDev.getId() +"");
                devDataMap.put("busiCode",subDev.getBusiCode());
                devDataMap.put("devTypeId",subDev.getDevTypeId() +"");
                devDataMap.put("esnCode",subDev.getEsnCode() +"");
                devDataMap.put("pvCapMap",pvMap);

                devItemMap.put(subDev.getId() +"",devDataMap);

            }else if (subDev.getDevTypeId().toString().equals(DevTypeConstant.INVERTER_DEV_TYPE.toString())){//组串式逆变器
                //计算默认容量
                if (isStartPvArithmetic){
                    computeDefaultHouseholdInverterCapacity();
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
                HashMap pvMap = new HashMap<>();
                HashMap pvItemMap = new HashMap<>();
                for (int i = 0; i < tempPvSize; i++) {
                    String key = i + 1 + "";
                    pvItemMap.put(key, tempCapacity);
                }
                pvMap.put("map",pvItemMap);
                pvMap.put("size",tempPvSize);

                HashMap devDataMap = new HashMap<>();
                devDataMap.put("id",subDev.getId() +"");
                devDataMap.put("busiCode",subDev.getBusiCode());
                devDataMap.put("devTypeId",subDev.getDevTypeId() +"");
                devDataMap.put("esnCode",subDev.getEsnCode() +"");
                devDataMap.put("pvCapMap",pvMap);

                devItemMap.put(subDev.getId() +"",devDataMap);

            }else if (subDev.getDevTypeId().toString().equals(DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE.toString())){//户用逆变器
                //计算默认容量
                if (isStartPvArithmetic){

                    if (isAllHuaweiInverter){
                        SubDev.PvCapacity pvCapacity=subDev.getPvCapacity().get(subDev.getEsnCode());
                        tempPvSize=pvCapacity.getCapNum();
                        tempCapacity=String.valueOf(decimalFormat.format(pvCapacity.getRatedCapacity()*1000/tempPvSize));
                    }else{
                        computeDefaultHouseholdInverterCapacity();
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
                HashMap pvMap = new HashMap<>();
                HashMap pvItemMap = new HashMap<>();
                for (int i = 0; i < tempPvSize; i++) {
                    String key = i + 1 + "";
                    pvItemMap.put(key, tempCapacity);
                }
                pvMap.put("map",pvItemMap);
                pvMap.put("size",tempPvSize);
                HashMap devDataMap = new HashMap<>();
                devDataMap.put("id",subDev.getId() +"");
                devDataMap.put("busiCode",subDev.getBusiCode());
                devDataMap.put("devTypeId",subDev.getDevTypeId() +"");
                devDataMap.put("esnCode",subDev.getEsnCode() +"");
                devDataMap.put("pvCapMap",pvMap);
                devItemMap.put(subDev.getId() +"",devDataMap);
            }
        }
    }
}
