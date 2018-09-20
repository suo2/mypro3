package com.huawei.solarsafe.view.stationmanagement.changestationinfo;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.bean.station.ChangeStationInfo;
import com.huawei.solarsafe.bean.stationmagagement.ChangeStationBindInvsBean;
import com.huawei.solarsafe.bean.stationmagagement.ChangeStationBindInvsInfo;
import com.huawei.solarsafe.bean.stationmagagement.CreateStationArgs;
import com.huawei.solarsafe.bean.stationmagagement.DevCapByIdData;
import com.huawei.solarsafe.bean.stationmagagement.DevCapByIdDataBean;
import com.huawei.solarsafe.bean.stationmagagement.SaveCapMassage;
import com.huawei.solarsafe.bean.stationmagagement.SaveDevCapData;
import com.huawei.solarsafe.bean.stationmagagement.SubDev;
import com.huawei.solarsafe.presenter.stationmanagement.ChangeStationPresenter;
import com.huawei.solarsafe.utils.MySpinner;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.stationmanagement.CreateBaseFragmnet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * 修改电站信息的组串容量配置
 */
public class GroupStringSettingFragment extends CreateBaseFragmnet implements View.OnClickListener, PullToRefreshListView.OnRefreshListener2, IChangeStationView {
    private View mainView;
    private PullToRefreshListView devListView;
    private CheckBox cbCheckAll;
    private Button btnSetting;
    private Button btnCapacitySetting;
    private String[] devLxStrings;
    private MySpinner mySpinner;
    private List<SubDev> devList = new ArrayList<>();
    private DevListAdapter adapter;
    private View emptyView;
    private ChangeStationInfo changeStationInfo;
    private ChangeStationPresenter changeStationPresenter;
    private List<SubDev> devListCurrent = new ArrayList<>();
    private EditText etPopCapacity;
    private Button btAllPop;
    private LinearLayout llPopContain;
    private TextView tvPopCancel;
    private TextView tvPopConfirm;
    private Dialog popupWindow;
    private LinearLayout llContainer;
    private boolean isOkToSetting = true;
    private ArrayList<Integer> typeIds = new ArrayList<>();
    private int typeId = 0;
    private ArrayList<String> ensList = new ArrayList<>();
    private List<SubDev> devListCheck = new ArrayList<>();
    private int pvSize = 2;
    private String[] esnStrings;
    private int maxSize = 20;
    private int v3MaxSize;//V3逆变器的最大组串数，这个值由服务器返回
    private int etidText;
    private boolean isCheckMore;//点击配置详情
    private boolean isCheckCap;//点击配置容量
    private LinearLayout llCap2_1, llCap2_2, llCap2_3, llCap2_4, llCap2_5, llCap2_6, llCap2_7, llCap2_8, llCap2_9, llCap2_10;
    private LinearLayout llCap_pv1, llCap_pv2, llCap_pv3, llCap_pv4, llCap_pv5, llCap_pv6, llCap_pv7, llCap_pv8, llCap_pv9, llCap_pv10,
            llCap_pv11, llCap_pv12, llCap_pv13, llCap_pv14, llCap_pv15, llCap_pv16, llCap_pv17, llCap_pv18, llCap_pv19, llCap_pv20;
    private EditText edCap_pv1, edCap_pv2, edCap_pv3, edCap_pv4, edCap_pv5, edCap_pv6, edCap_pv7, edCap_pv8, edCap_pv9, edCap_pv10,
            edCap_pv11, edCap_pv12, edCap_pv13, edCap_pv14, edCap_pv15, edCap_pv16, edCap_pv17, edCap_pv18, edCap_pv19, edCap_pv20;
    private List<LinearLayout> llList = new ArrayList<>();
    private List<LinearLayout> llListCap = new ArrayList<>();
    private List<EditText> edList = new ArrayList<>();
    private List<String> sList = new ArrayList<>();
    private List<String> noNullList = new ArrayList<>();
    private DevCapByIdDataBean devCapByIdDataBean;
    private int dataNum;
    private int saveNum;
    private static final String TAG = "GroupStringSettingFragm";

    public void setChangeStationInfo(ChangeStationInfo changeStationInfo) {
        this.changeStationInfo = changeStationInfo;
        if (changeStationInfo != null) {
            canEdt(false);
            showLoading();
            requestData();
            if (mySpinner != null) {
                mySpinner.setSelection(0);
            }
        }
    }

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
    }

    public GroupStringSettingFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeStationPresenter = new ChangeStationPresenter();
        changeStationPresenter.setView(this);
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
        cbCheckAll.setVisibility(View.GONE);
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
        btnSetting.setVisibility(View.GONE);
        btnSetting.setOnClickListener(this);
        btnCapacitySetting = (Button) mainView.findViewById(R.id.btn_capacity_setting);
        btnCapacitySetting.setVisibility(View.GONE);
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

        ChangeStationInfoActivity changeStationInfoActivity= (ChangeStationInfoActivity) getActivity();
        if (changeStationInfoActivity.isOneKey){
            changeStationInfoActivity.doOnekey();
        }

        return mainView;
    }

    @Override
    public boolean validateAndSetArgs(CreateStationArgs args) {
        if (checkDevAllset()) {
            CreateStationArgs.PvinfoMBean pvinfoM = new CreateStationArgs().new PvinfoMBean();
            pvinfoM.setSize(devList.size());
            pvinfoM.setMap(pvinfoMMap);
            CreateStationArgs.DevinfoMBean devinfoM = new CreateStationArgs().new DevinfoMBean();
            Map<String, CreateStationArgs.DevItemBean> devMmap = new HashMap<>();
            devinfoM.setSize(devList.size());
            for (SubDev dev : devList) {
                CreateStationArgs.DevItemBean devItemBean = new CreateStationArgs().new DevItemBean();
                devItemBean.setBusiCode(dev.getBusiCode());
                devItemBean.setDevTypeId(dev.getDevTypeId() + "");
                devItemBean.setId(dev.getId() + "");
                devItemBean.setPvInfoMap(pvinfoMChildBean);
                devMmap.put(dev.getId() + "", devItemBean);
            }
            devinfoM.setMap(devMmap);
            args.setDevinfoM(devinfoM);
            return true;
        } else {
            ToastUtil.showMessage(getString(R.string.have_net_setpv_dev_notice_str));
            return false;
        }
    }

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
            //详细配置
            case R.id.btn_setting:
                if (devListCurrent == null || devListCurrent.size() == 0) {
                    DialogUtil.showErrorMsg(getActivity(), getString(R.string.no_dev_can_setting_groupstring_str));
                } else {
                    isCheckMore = true;
                    isCheckCap = false;
                    getChackedData();
                }
                break;
            //容量配置
            case R.id.btn_capacity_setting:
                isCheckMore = false;
                isCheckCap = true;
                getChackedData();
                break;
            //popupwindow的取消键
            case R.id.tv_popupwindow_cancel:
                ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(llContainer.getWindowToken(), 0);
                popupWindow.dismiss();
                break;
            //popupwindow的确认键
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
                showLoading();
                Gson gson = new Gson();
                SaveDevCapData data = new SaveDevCapData();

                HashMap pvMap = new HashMap<>();
                HashMap pvItemMap = new HashMap<>();
                for (int i = 0; i < saveNum; i++) {
                    String key = i + 1 + "";
                    pvItemMap.put(key, edList.get(i).getText().toString().trim());
                }
                pvMap.put("map", pvItemMap);
                pvMap.put("size", saveNum);
                HashMap devMap = new HashMap<>();
                HashMap devItemMap = new HashMap<>();
                for (int i = 0; i < devListCheck.size(); i++) {
                    HashMap<String, String> devDataMap = new HashMap<>();
                    devDataMap.put("id", devListCheck.get(i).getId() + "");
                    devDataMap.put("stationCode", devListCheck.get(i).getStationCode());
                    devDataMap.put("busiCode", devListCheck.get(i).getBusiCode());
                    devDataMap.put("devTypeId", devListCheck.get(i).getDevTypeId() + "");
                    devDataMap.put("twoLevelDomain", devListCheck.get(i).getTwoLevelDomain());
                    devDataMap.put("esnCode", devListCheck.get(i).getEsnCode() + "");

                    devItemMap.put(devListCheck.get(i).getId() + "", devDataMap);
                }
                devMap.put("map", devItemMap);
                devMap.put("size", devListCheck.size());

                data.setPvCapMap(pvMap);
                data.setDevInfo(devMap);
                String s = gson.toJson(data);
                changeStationPresenter.saveDevCapData(s);
                break;
            //批量设置
            case R.id.bt_all_popupwindow:
                if (!TextUtils.isEmpty(edList.get(0).getText().toString().trim())) {
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
     * 勾选的设备信息
     */
    private void getChackedData() {
        typeIds.clear();
        typeId = 0;
        v3MaxSize = 0;
        ensList.clear();
        devListCheck.clear();
        String modelVersionCode = null;
        boolean isOneVersion = true;
        for (SubDev s : devListCurrent) {
            if (s.isCheck()) {
                typeIds.add(s.getDevTypeId());
                ensList.add(s.getId() + "");
                devListCheck.add(s);
                if(!TextUtils.isEmpty(s.getPvNum()) && Integer.valueOf(s.getPvNum()) > v3MaxSize){
                    v3MaxSize = Integer.valueOf(s.getPvNum());
                }
                if(!TextUtils.isEmpty(modelVersionCode)  && !modelVersionCode.equals(s.getModelVersionCode())) {
                    isOneVersion =false;
                }
                modelVersionCode = s.getModelVersionCode();
            }
        }
        esnStrings = new String[ensList.size()];
        for (int i = 0; i < ensList.size(); i++) {
            esnStrings[i] = ensList.get(i);
        }
        if (esnStrings.length == 0) {
            DialogUtil.showErrorMsg(getActivity(), getString(R.string.select_one_item_notice));
            return;
        }
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
        if (typeId == DevTypeConstant.INVERTER_DEV_TYPE) {
            if(isOneVersion && "SUN2000_1.0".equals(modelVersionCode)){
                pvSize = 6;
                maxSize = 6;
            }else {
                pvSize = 8;
                maxSize = 14;
            }
        } else if (typeId == DevTypeConstant.DCJS_DEV_TYPE) {
            pvSize = 14;
            maxSize = 20;
        } else if (typeId == DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE) {
            pvSize = 2;
            maxSize = 8;
        }
        if(isOneVersion && v3MaxSize != 0){
            maxSize = v3MaxSize;
            pvSize = v3MaxSize;
        }
        if (isCheckMore) {
            Intent intent = new Intent(getActivity(), GroupStringConfigActivityChange.class);
            intent.putExtra("esnList", esnStrings);
            intent.putExtra("pvSize", pvSize);
            intent.putExtra("maxSize",maxSize);
            intent.putParcelableArrayListExtra("devList", (ArrayList<? extends Parcelable>) devListCheck);
            startActivityForResult(intent, 100);
        }
        if (isCheckCap) {
            showCapacityPopupWindow();
        }
    }

    /**
     * 弹出仅配置容量的popupWindow
     */
    private void showCapacityPopupWindow() {
        llList.clear();
        llListCap.clear();
        edList.clear();
        View view = View.inflate(getContext(),R.layout.capacity_popwindow_layout,null);

        popupWindow=new Dialog(getContext(),R.style.zxing_help_dilog);

        popupWindow.setContentView(view);

        etPopCapacity = (EditText) view.findViewById(R.id.et_pop_capacity);
        etPopCapacity.setHint(String.format(getResources().getString(R.string.pv_size_input_range,maxSize)));
        etPopCapacity.setFilters(new InputFilter[]{Utils.numberZeroFilter()});//禁止以0开头
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
        initPopupwindow(view);
        initPopupwindowPv();
        saveNum = pvSize;
        popupWindow.show();
        //请求数据
        if(esnStrings.length == 1){
            showLoading();
            HashMap<String, String> params = new HashMap<>();
            params.put("devId", esnStrings[0]);
            changeStationPresenter.requestDevCapData(params);
        }
    }


    /**
     * @param view
     * popupwindow的初始化
     */
    private void initPopupwindow(View view) {
        llContainer= (LinearLayout) view.findViewById(R.id.llContainer);
        llContainer.setClickable(true);
        llContainer.setOnClickListener(this);
        btAllPop = (Button) view.findViewById(R.id.bt_all_popupwindow);
        btAllPop.setOnClickListener(this);
        llPopContain = (LinearLayout) view.findViewById(R.id.ll_pop_contain);
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
     * popupwindow的pv初始化
     */
    private void initPopupwindowPv() {
        if (pvSize == 2) {
            edList.get(0).setText("0");
            edList.get(1).setText("0");
            llCap2_2.setVisibility(View.GONE);
            llCap2_3.setVisibility(View.GONE);
            llCap2_4.setVisibility(View.GONE);
            llCap2_5.setVisibility(View.GONE);
            llCap2_6.setVisibility(View.GONE);
            llCap2_7.setVisibility(View.GONE);
            llCap2_8.setVisibility(View.GONE);
            llCap2_9.setVisibility(View.GONE);
            llCap2_10.setVisibility(View.GONE);
        } else if (pvSize == 8) {
            for (int i = 0; i < 8; i++) {
                edList.get(i).setText("0");
            }
            llCap2_5.setVisibility(View.GONE);
            llCap2_6.setVisibility(View.GONE);
            llCap2_7.setVisibility(View.GONE);
            llCap2_8.setVisibility(View.GONE);
            llCap2_9.setVisibility(View.GONE);
            llCap2_10.setVisibility(View.GONE);
        } else if(pvSize == 6){
            for (int i = 0; i < 6; i++) {
                edList.get(i).setText("0");
            }
            llCap2_4.setVisibility(View.GONE);
            llCap2_5.setVisibility(View.GONE);
            llCap2_6.setVisibility(View.GONE);
            llCap2_7.setVisibility(View.GONE);
            llCap2_8.setVisibility(View.GONE);
            llCap2_9.setVisibility(View.GONE);
            llCap2_10.setVisibility(View.GONE);
        }else if(pvSize == 12){
            for (int i = 0; i < 12; i++) {
                edList.get(i).setText("0");
            }
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
    }

    /**
     * 用户自定义组串数时，显示view
     */
    private void showUserSetStringSize() {
        int numL;
        if (etidText > maxSize) {
            etPopCapacity.setText(maxSize + "");
            etidText = maxSize;
            ToastUtil.showMessage(String.format(getResources().getString(R.string.pv_size_input_range_msg,maxSize)));
        }
        saveNum = etidText;
        if (etidText % 2 == 0) {
            numL = etidText / 2;
        } else {
            numL = (etidText / 2) + 1;
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
        if (etidText != dataNum) {
            for (int i = 0; i < etidText; i++) {
                edList.get(i).setText("0");
            }
        } else {
            for (int i = 0; i < etidText; i++) {
                if (TextUtils.isEmpty(sList.get(i))) {
                    edList.get(i).setText("0");
                } else {
                    edList.get(i).setText(sList.get(i));
                }
            }
        }
    }

    private Map<String, CreateStationArgs.PvinfoMChildBean> pvinfoMMap = new HashMap<>();
    private CreateStationArgs.PvinfoMChildBean pvinfoMChildBean = new CreateStationArgs().new PvinfoMChildBean();

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
    public void requestData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("page", "1");
        params.put("pageSize", "15");
        params.put("stationCode", changeStationInfo.getStationCode());
        changeStationPresenter.requestGetBindInvs(params);
    }

    @Override
    public void getData(BaseEntity baseEntity) {
        if (isAdded()) {
            dismissLoading();
            if (baseEntity == null) {
                return;
            }
            if (baseEntity instanceof ChangeStationBindInvsInfo) {
                ChangeStationBindInvsInfo changeStationBindInvsInfo = (ChangeStationBindInvsInfo) baseEntity;
                ChangeStationBindInvsBean changeStationBindInvsBean = changeStationBindInvsInfo.getChangeStationBindInvsBean();
                if (changeStationBindInvsBean != null) {
                    ChangeStationBindInvsBean.DataBean data = changeStationBindInvsBean.getData();
                    if (data != null) {
                        List<ChangeStationBindInvsBean.DataBean.ListBean> list = data.getList();
                        if (list != null && list.size() > 0) {
                            List<SubDev> subDevList = new ArrayList<>();
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
                                subDev.setPvNum(bean.getPvNum());
                                subDev.setSoftwareVersion(bean.getSoftwareVersion());
                                subDevList.add(subDev);
                            }
                            setDevList(subDevList);
                        }
                    }
                }
            }
            if (baseEntity instanceof DevCapByIdData) {
                DevCapByIdData devCapByIdData = (DevCapByIdData) baseEntity;
                devCapByIdDataBean = devCapByIdData.getDataBean();
                if (devCapByIdDataBean != null) {
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
                    if (dataNum != 0) {
                        etPopCapacity.setText(dataNum + "");
                        showDataCapItem(dataNum);
                    }
                }
            }
            if (baseEntity instanceof SaveCapMassage) {
                SaveCapMassage massage = (SaveCapMassage) baseEntity;
                String s = massage.getMessage();
                boolean success = massage.isSuccess();
                AlertDialog.Builder build = new AlertDialog.Builder(getContext());
                build.setTitle(MyApplication.getContext().getString(R.string.hint));
                if (success) {
                    if(TextUtils.isEmpty(s) || "null".equals(s)){
                        build.setMessage(MyApplication.getContext().getString(R.string.save_success));
                    }else {
                        build.setMessage(s);
                    }
                } else {
                    build.setMessage(s);
                }
                build.setPositiveButton(MyApplication.getContext().getString(R.string.sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        popupWindow.dismiss();
                    }
                });
                build.show();
            }
        }
    }

    /**
     * @param size 获取数据后显示view
     */
    private void showDataCapItem(int size) {
        saveNum = size;
        int numL;
        if (size % 2 == 0) {
            numL = size / 2;
        } else {
            numL = (size / 2) + 1;
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


    class DevListAdapter extends BaseAdapter {
        private List<SubDev> devList;

        public DevListAdapter(List<SubDev> devList) {
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
            if (canEdt) {
                viewHolder.checkBox.setVisibility(View.VISIBLE);
            } else {
                viewHolder.checkBox.setVisibility(View.GONE);
            }
            viewHolder.checkBox.setChecked(subDev.isCheck());
            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (subDev.isCheck()) {
                        devList.get(position).setCheck(false);
                        cbCheckAll.setChecked(false);
                    } else {
                        devList.get(position).setCheck(true);
                        for(int i=0;i<devList.size();i++){
                            if(!devList.get(i).isCheck()){
                                break;
                            }else{
                                if(i==devList.size()-1){
                                    cbCheckAll.setChecked(true);
                                }
                            }
                        }
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

    public void updateInfo() {

    }

    private boolean canEdt;

    public void canEdt(boolean canEdt) {
        this.canEdt = canEdt;
        if (canEdt) {
            btnSetting.setVisibility(View.VISIBLE);
            cbCheckAll.setVisibility(View.VISIBLE);
            btnCapacitySetting.setVisibility(View.VISIBLE);
        } else {
            btnSetting.setVisibility(View.GONE);
            btnCapacitySetting.setVisibility(View.GONE);
            cbCheckAll.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
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
}
