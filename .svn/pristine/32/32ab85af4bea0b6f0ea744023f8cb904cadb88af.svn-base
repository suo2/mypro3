package com.huawei.solarsafe.view.stationmanagement.changestationinfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.DomainBean;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.common.LogCallBack;
import com.huawei.solarsafe.bean.common.RetMsg;
import com.huawei.solarsafe.bean.defect.StationBean;
import com.huawei.solarsafe.bean.station.ChangeStationInfo;
import com.huawei.solarsafe.bean.station.StationListBeanForPerson;
import com.huawei.solarsafe.bean.stationmagagement.ChangeStationBaseReq;
import com.huawei.solarsafe.bean.stationmagagement.ChangeStationResultBean;
import com.huawei.solarsafe.bean.stationmagagement.ChangeStationResultInfo;
import com.huawei.solarsafe.bean.stationmagagement.CreateStationArgs;
import com.huawei.solarsafe.bean.stationmagagement.StationListRequest;
import com.huawei.solarsafe.bean.stationmagagement.StationManagementListInfo;
import com.huawei.solarsafe.bean.stationmagagement.StationManegementList;
import com.huawei.solarsafe.model.personmanagement.IPersonManagementModel;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.presenter.stationmanagement.ChangeStationPresenter;
import com.huawei.solarsafe.presenter.stationmanagement.CreateStationPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DatePiker.DatePikerDialog;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.stationmanagement.CreateBaseFragmnet;
import com.huawei.solarsafe.view.stationmanagement.ICreateStationView;
import com.huawei.solarsafe.view.stationmanagement.ResDomainSelectActivity;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.huawei.solarsafe.utils.LocalData.DOMAIN_BEAN;

/**
 * Create Date: 2017/4/24
 * Create Author: P00171
 * Description :创建电站-基础信息Fragment
 */
public class BaseInfoFragment extends CreateBaseFragmnet implements View.OnClickListener, IChangeStationView, ICreateStationView {
    private TextView tvStationDomain;
    private EditText etStationName;
    private EditText etPlanCapacity;
    private TextView tvGridTime;
    private Spinner spGridType;
    private Spinner spStationStatus;
    private Spinner spPovertyStation;
    private EditText etContractPerson;
    private EditText etTel;
    //
    private LinearLayout llStationPlace;
    private DatePikerDialog datePickerDialog;
    /**
     * 并网类型
     */
    private String[] gridTypes;
    /**
     * 电站状态
     */
    private String[] stationStatus;
    /**
     * 扶贫状态
     */
    private String[] povertyStations;
    private final int REQ_CODE_DOMAIN_SELECT = 0x10;
    //记录选择的时间
    private long selectTime = System.currentTimeMillis();
    //初始电站信息
    private ChangeStationInfo changeStationInfo;
    private ChangeStationInfo changeStationInfoNew;
    private ChangeStationPresenter stationPresenter;
    private CreateStationPresenter createStationPresenter;
    private LocalData localData;
    private LinearLayout llPovertyStation;//是否显示扶贫电站的选择
    public void setChangeStationInfo(ChangeStationInfo changeStationInfo) {
        this.changeStationInfo = changeStationInfo;
        stationPresenter = new ChangeStationPresenter();
        stationPresenter.setView(this);
        localData = LocalData.getInstance();
        requestStationList();

    }

    private void flashData() {
        int domainId = changeStationInfoNew.getDomainId();
        if (stationList != null && stationList.size() > 0) {
            for (StationBean s : stationList) {
                if (s.getId().equals(domainId + "")) {
                    s.setCheck("true");
                    if("Msg.&topdomain".equals(s.getName())){
                        tvStationDomain.setText(MyApplication.getContext().getString(R.string.topdomain));
                    }else {
                        tvStationDomain.setText(s.getName());
                    }
                    tvStationDomain.setTag(s.getId());
                    if ("POOR".equals(s.getSupportPoor())) {
                        llPovertyStation.setVisibility(View.VISIBLE);
                        povertyStations = new String[]{getString(R.string.poverty_station), getString(R.string.nonpoverty_station)};
                        ArrayAdapter povertyStationAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, povertyStations);
                        povertyStationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spPovertyStation.setAdapter(povertyStationAdapter);
                        int aidType = changeStationInfoNew.getAidType();
                        if (0 == aidType) {
                            spPovertyStation.setSelection(0);
                        } else{
                            spPovertyStation.setSelection(1);
                        }
                    } else {
                        llPovertyStation.setVisibility(View.GONE);
                        povertyStations = new String[]{getString(R.string.nonpoverty_station)};
                        ArrayAdapter povertyStationAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, povertyStations);
                        povertyStationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spPovertyStation.setAdapter(povertyStationAdapter);
                        spPovertyStation.setSelection(0);
                    }
                }
            }
        }
        stationListBeanForPerson.setStationBeans(stationList);
        etStationName.setText(changeStationInfoNew.getStationName());
        etPlanCapacity.setText(changeStationInfoNew.getCapacity() + "");
        selectTime = changeStationInfoNew.getDevoteDate();
        tvGridTime.setText(Utils.getFormatTimeYYMMDD(selectTime));
        tvGridTime.setTag(selectTime);
        String combineType = changeStationInfoNew.getCombineType();
        String combineTypeName = null;
        if ("1".equals(combineType)) {
            combineTypeName = getString(R.string.ground);
        } else if ("2".equals(combineType)) {
            combineTypeName = getString(R.string.distributed);
        } else if ("3".equals(combineType)) {
            combineTypeName = getString(R.string.household_string);
        }
        if(gridTypes != null){
            for (int i = 0; i < gridTypes.length; i++) {
                if(gridTypes[i].equals(combineTypeName)){
                    spGridType.setSelection(i);
                    break;
                }
            }
        }
        String buildState = changeStationInfoNew.getBuildState();
        if ("1".equals(buildState)) {
            spStationStatus.setSelection(1);
        } else if ("2".equals(buildState)) {
            spStationStatus.setSelection(2);
        } else if ("3".equals(buildState)) {
            spStationStatus.setSelection(0);
        }
        etContractPerson.setText(changeStationInfoNew.getStationLinkman());
        etTel.setText(changeStationInfoNew.getLinkmanPho());
        changeCanEdt(false);
    }

    private TextView tvPlanCapacityHint;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createStationPresenter = new CreateStationPresenter();
        createStationPresenter.onViewAttached(this);
        requestStationList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_station_base_info, container, false);
        llStationPlace= (LinearLayout) view.findViewById(R.id.llStationPlace);
        llStationPlace.setVisibility(View.GONE);

        llPovertyStation = (LinearLayout) view.findViewById(R.id.llyt_poverty_station);
        gridTypes = new String[]{ getString(R.string.household_string), getString(R.string.distributed),getString(R.string.ground)};
        stationStatus = new String[]{getString(R.string.grid),getString(R.string.planning), getString(R.string.bulding)};
        povertyStations = new String[]{getString(R.string.poverty_station), getString(R.string.nonpoverty_station)};
        //电站归属
        tvStationDomain = (TextView) view.findViewById(R.id.tv_station_domain);
        tvStationDomain.setOnClickListener(this);
        //电站名称
        etStationName = (EditText) view.findViewById(R.id.et_station_name);
        //规划容量
        etPlanCapacity = (EditText) view.findViewById(R.id.et_plan_capacity);
        //并网时间
        tvGridTime = (TextView) view.findViewById(R.id.tv_grid_time);
        tvGridTime.setOnClickListener(this);
        //并网类型
        spGridType = (Spinner) view.findViewById(R.id.sp_grid_type);
        //电站状态
        spStationStatus = (Spinner) view.findViewById(R.id.sp_station_status);
        ArrayAdapter stationStatusAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, stationStatus);
        stationStatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStationStatus.setAdapter(stationStatusAdapter);
        spStationStatus.setSelection(0);
        //扶贫类型
        spPovertyStation = (Spinner) view.findViewById(R.id.sp_poverty_station);
        ArrayAdapter povertyStationAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, povertyStations);
        povertyStationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPovertyStation.setAdapter(povertyStationAdapter);
        spPovertyStation.setSelection(1);
        //并网类型
        ArrayAdapter gridTypeAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, gridTypes);
        gridTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGridType.setAdapter(gridTypeAdapter);
        //联系人
        etContractPerson = (EditText) view.findViewById(R.id.et_contract_person);
        etContractPerson.setFilters(new InputFilter[]{new InputFilter.LengthFilter(32),Utils.getEmojiFilter()});

        //联系电话
        etTel = (EditText) view.findViewById(R.id.et_tel);
        etTel.setFilters(new InputFilter[]{new InputFilter.LengthFilter(25)});
        //特殊字符不能输入
        etStationName.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                for (int j = i; i < i1; i++) {
                    if (Character.toString(charSequence.charAt(i)).equals(",") || Character.toString(charSequence.charAt(i)).equals("<")
                            || Character.toString(charSequence.charAt(i)).equals(">") || Character.toString(charSequence.charAt(i)).equals("/")
                            || Character.toString(charSequence.charAt(i)).equals("&") || Character.toString(charSequence.charAt(i)).equals("'")
                            || Character.toString(charSequence.charAt(i)).equals("\"") || Character.toString(charSequence.charAt(i)).equals("，")) {
                        return "";
                    }
                }
                return null;
            }
        },Utils.getEmojiFilter()});
        etPlanCapacity.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
        //自能输入数字，保留3位小数
        etPlanCapacity.setFilters(new InputFilter[]{new InputFilter() {
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
        spGridType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(getString(R.string.household_string).equals(spGridType.getSelectedItem())){
                    ((ChangeStationInfoActivity)getActivity()).hindRadioButton(false);
                }else {
                    ((ChangeStationInfoActivity)getActivity()).hindRadioButton(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        tvPlanCapacityHint= (TextView) view.findViewById(R.id.tvPlanCapacityHint);
        etPlanCapacity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                tvPlanCapacityHint.setSelected(hasFocus);
            }
        });
        etPlanCapacity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()==0){
                    tvPlanCapacityHint.setVisibility(View.VISIBLE);
                }else{
                    tvPlanCapacityHint.setVisibility(View.GONE);
                }
            }
        });

        requestStationList();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQ_CODE_DOMAIN_SELECT) {
                if (data != null) {
                    try {
                        stationListBeanForPerson = (StationListBeanForPerson) data.getSerializableExtra("stationList");
                    } catch (Exception e){
                    }
                    tvStationDomain.setText("");
                    tvStationDomain.setTag(null);
                    for (StationBean s : stationListBeanForPerson.getStationBeans()) {
                        if ("true".equals(s.getCheck())) {
                            if ("Msg.&topdomain".equals(s.getName())) {
                                tvStationDomain.setText(MyApplication.getContext().getString(R.string.topdomain));
                            } else {
                                tvStationDomain.setText(s.getName());
                            }
                            tvStationDomain.setTag(s.getId());
                            //导入了扶贫权限并且二级域要勾选支持扶贫
                            if ("POOR".equals(s.getSupportPoor())) {
                                llPovertyStation.setVisibility(View.VISIBLE);
                                povertyStations = new String[]{getString(R.string.poverty_station), getString(R.string.nonpoverty_station)};
                                ArrayAdapter povertyStationAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, povertyStations);
                                povertyStationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spPovertyStation.setAdapter(povertyStationAdapter);
                                spPovertyStation.setSelection(1);
                            } else {
                                llPovertyStation.setVisibility(View.GONE);
                                povertyStations = new String[]{getString(R.string.nonpoverty_station)};
                                ArrayAdapter povertyStationAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, povertyStations);
                                povertyStationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spPovertyStation.setAdapter(povertyStationAdapter);
                                spPovertyStation.setSelection(0);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 显示日期选择框
     */
    public void showDateDialog() {
        //wujng修改部分
        datePickerDialog = new DatePikerDialog(getActivity(), Utils.getFormatTimeYYMMDD(System.currentTimeMillis()), true, new DatePikerDialog.OnDateFinish() {
            @Override
            public void onDateListener(long date) {
                tvGridTime.setText(Utils.getFormatTimeYYMMDD(date));
                tvGridTime.setTag(date);
                selectTime = date;
            }

            @Override
            public void onSettingClick() {

            }
        });
        datePickerDialog.updateTime(selectTime, -1);
        datePickerDialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_station_domain:
                if (canEdt) {
                    clickSelectDomain();
                } else {
                    return;
                }
                break;
            case R.id.tv_grid_time:
                if (canEdt && !"3".equals(changeStationInfo.getDataFrom())) {
                    showDateDialog();
                }
                break;
        }
    }

    public void clickSelectDomain() {
        if (stationList == null) {
            ToastUtil.showMessage(getString(R.string.not_get_domain_info));
            return;
        }
        if (stationListBeanForPerson.getStationBeans() == null) {
            ToastUtil.showMessage(getString(R.string.not_get_domain_info));
            return;
        }
        //wujing修改
        Intent intent = new Intent(getActivity(), ResDomainSelectActivity.class);
        intent.putExtra("stationList", stationListBeanForPerson);
        startActivityForResult(intent, REQ_CODE_DOMAIN_SELECT);
    }

    public boolean validateAndSetArgs(CreateStationArgs args) {
        CreateStationArgs.StationBean stationBean = args.getStation();
        if (stationBean == null) {
            stationBean = args.new StationBean();
            args.setStation(stationBean);
        }
        Object tag;
        //电站归属
        tag = tvStationDomain.getTag();
        if (tag == null) {
            ToastUtil.showMessage(getString(R.string.select_station_domain));
            return false;
        }
        stationBean.setDomainId((String) tag);
        //电站名称
        String stationName = etStationName.getText().toString().trim();
        if (stationName.isEmpty()) {
            ToastUtil.showMessage(R.string.input_station_name);
            return false;
        }
        stationBean.setStationName(stationName);
        //规划容量
        String planCapacity = etPlanCapacity.getText().toString().trim();
        /**wujing修改部分*/
        try {
            if (planCapacity.isEmpty()) {
                ToastUtil.showMessage(getString(R.string.please_input_Dc_capacity_of_power_station));
                return false;
            } else if (Float.valueOf(planCapacity) <= 0 || Float.valueOf(planCapacity) > 100000000) {
                etPlanCapacity.setError(getResources().getString(R.string.capacity_notice));
                return false;
            }
            stationBean.setCapacity(planCapacity);
        } catch (NumberFormatException e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e("planCapacity", "NumberFormatException");
        }
        //并网时间
        tag = tvGridTime.getTag();
        if (tag == null) {
            ToastUtil.showMessage(getString(R.string.select_grid_time));
            return false;
        }
        stationBean.setGridTime(String.valueOf((long) tag));
        String id;
        //并网类型
        id = getGridTypeId();
        if (id == null) {
            ToastUtil.showMessage(getString(R.string.select_grid_type));
            return false;
        }
        stationBean.setCombinedType(id);
        //电站状态
        id = getStationStatusId();
        if (id == null) {
            ToastUtil.showMessage(getString(R.string.select_station_status));
            return false;
        }
        stationBean.setStationStatus(id);
        //扶贫电站
        id = getPovertyTypeId();
        if (id == null) {
            ToastUtil.showMessage(getString(R.string.select_poverty_type));
            return false;
        }
        boolean b = checkStation(getStationName());
        if(!b){
            return false;
        }
        stationBean.setAidType(id);
        //联系人
        String contractPerson = etContractPerson.getText().toString().trim();
        stationBean.setContact(contractPerson);
        //联系电话
        String tel = etTel.getText().toString().trim();
        stationBean.setPhone(tel);
        return true;
    }

    /**
     * 获取并网类型提交数据
     *
     * @return
     */
    private String getGridTypeId() {
        String gridTypeId;
        String gridTypeTxt = spGridType.getSelectedItem().toString();
        if (gridTypeTxt.equals(getString(R.string.ground))) {
            gridTypeId = "1";
        } else if (gridTypeTxt.equals(getString(R.string.distributed))) {
            gridTypeId = "2";
        } else if (gridTypeTxt.equals(getString(R.string.household_string))) {
            gridTypeId = "3";
        } else {
            gridTypeId = null;
        }
        return gridTypeId;
    }

    /**
     * 获取电站类型提交数据
     *
     * @return
     */
    private String getStationStatusId() {
        String stationStatusId;
        String gridTypeTxt = spStationStatus.getSelectedItem().toString();
        if (gridTypeTxt.equals(getString(R.string.planning))) {
            stationStatusId = "1";
        } else if (gridTypeTxt.equals(getString(R.string.bulding))) {
            stationStatusId = "2";
        } else if (gridTypeTxt.equals(getString(R.string.grid))) {
            stationStatusId = "3";
        } else {
            stationStatusId = null;
        }
        return stationStatusId;
    }

    /**
     * 获取扶贫类型提交数据
     *
     * @return
     */
    private String getPovertyTypeId() {
        String povertyId;
        if("0".equals(LocalData.getInstance().getWebBuildCode())){
            String gridTypeTxt = spPovertyStation.getSelectedItem().toString();
            if (gridTypeTxt.equals(getString(R.string.poverty_station))) {
                povertyId = "0";
            } else if (gridTypeTxt.equals(getString(R.string.nonpoverty_station))) {
                povertyId = "1";
            } else {
                povertyId = null;
            }
        }else {
            if(localData.getSupportPoor()){
                String gridTypeTxt = spPovertyStation.getSelectedItem().toString();
                if (gridTypeTxt.equals(getString(R.string.poverty_station))) {
                    povertyId = "0";
                } else if (gridTypeTxt.equals(getString(R.string.nonpoverty_station))) {
                    povertyId = "1";
                } else {
                    povertyId = null;
                }
            }else {
                povertyId = "1";
            }
        }
        return povertyId;
    }

    /**
     * 获取新增电站名称
     *
     * @return
     */
    public String getStationName() {
        return etStationName.getText().toString().trim();
    }
    /**
     * 校验电站名称
     *
     * @return
     */
    public boolean checkStation(String stationName) {
        if (!checkStationNameIsOk(stationName)) {
            etStationName.setError(getResources().getString(R.string.station_check_notice));
            return false;
        } else {
            return true;
        }
    }
    /**
     * 校验电站名称
     *
     * @return
     */
    public boolean checkStationNameIsOk(String stationName) {
        if (TextUtils.isEmpty(stationName)) {
            return false;
        } else try {
            if (stationName.getBytes("GBK").length > 60) {
                return false;
            } else if (Utils.checkSpecialCharacter(stationName)) {
                return false;
            }
        } catch (UnsupportedEncodingException e) {
            Log.e("BaseInfoFragment",e.getMessage());
        }
        return true;
    }

    //wujing添加
    private List<StationBean> stationList = new ArrayList<>();
    private StationListBeanForPerson stationListBeanForPerson = new StationListBeanForPerson();

    /**
     * 请求资源域
     */
    private void requestStationList() {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0001_F03_Android_S01  删除Android的demo账号    【修改人】：zhaoyufeng
        Map<String, String> params = new HashMap<>();
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        params.put("mdfUserId", String.valueOf(GlobalConstants.userId));
        NetRequest.getInstance().asynPostJson(NetRequest.IP + IPersonManagementModel.URL_DOMAINQUERYBYUSERID, params, new LogCallBack() {

            @Override
            protected void onFailed(Exception e) {
            }

            @Override
            protected void onSuccess(String data) {
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                try {
                    Gson gson = new Gson();
                    Type type = new TypeToken<RetMsg<List<StationBean>>>() {
                    }.getType();
                    RetMsg<List<StationBean>> retMsg = gson.fromJson(data, type);
                    stationList = retMsg.getData();
                    if(stationList == null || stationList.size() == 0){
                        DomainBean.DataBean dataBean = (DomainBean.DataBean) LocalData.getInstance().getDevList(DOMAIN_BEAN);
                        if(dataBean != null){
                            StationBean stationBean = new StationBean();
                            stationBean.setId(dataBean.getId() + "");
                            stationBean.setName(dataBean.getDomainName());
                            stationBean.setSupportPoor(dataBean.getSupportPoor());
                            stationList.add(stationBean);
                        }
                    }
                    stationListBeanForPerson.setStationBeans(stationList);
                    if (changeStationInfo != null) {
                        Gson gson2 = new Gson();
                        String[] stationCodes = new String[]{changeStationInfo.getStationCode()};
                        StationListRequest stationListRequest = new StationListRequest(1, 10, stationCodes, "");
                        String s = gson2.toJson(stationListRequest);
                        showLoading();
                        createStationPresenter.requestStationList(s);
                    }
                } catch (Exception e) {
                    Log.e("error", e.toString());
                }
            }
        });
    }

    public void updateInfo() {
        //修改数据提交
        requestData();

    }

    private boolean canEdt;

    //改变变界面各元素是否可编辑
    public void changeCanEdt(boolean canEdt) {
        this.canEdt = canEdt;
        //如果是710电站，只允许修改联系人、联系电话、扶贫电站、电站归属
        if ("3".equals(changeStationInfo.getDataFrom())) {
            etContractPerson.setEnabled(canEdt);
            etTel.setEnabled(canEdt);
            spPovertyStation.setEnabled(canEdt);
            etStationName.setEnabled(false);
            etPlanCapacity.setEnabled(false);
            spGridType.setEnabled(false);
            spStationStatus.setEnabled(false);
        } else {
            etStationName.setEnabled(canEdt);
            etPlanCapacity.setEnabled(canEdt);
            spGridType.setEnabled(canEdt);
            spPovertyStation.setEnabled(canEdt);
            spStationStatus.setEnabled(canEdt);
            etContractPerson.setEnabled(canEdt);
            etTel.setEnabled(canEdt);
        }
    }


    @Override
    public void requestData() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getActivity());
        }
        loadingDialog.show();
        ChangeStationBaseReq changeStationBaseReq = new ChangeStationBaseReq();
        ChangeStationBaseReq.StationBean stationBean = new ChangeStationBaseReq.StationBean();
        Object tag;
        //电站归属
        tag = tvStationDomain.getTag();
        if (tag == null) {
            ToastUtil.showMessage(getString(R.string.select_station_domain));
            loadingDialog.dismiss();
            return;
        }
        stationBean.setDomainId((String) tag);
        //电站名称
        String stationName = etStationName.getText().toString().trim();
        if (stationName.isEmpty()) {
            ToastUtil.showMessage(R.string.input_station_name);
            loadingDialog.dismiss();
            return;
        }
        stationBean.setStationName(stationName);
        //规划容量
        String planCapacity = etPlanCapacity.getText().toString().trim();
        /**wujing修改部分*/
        try {
            if (planCapacity.isEmpty()) {
                ToastUtil.showMessage(getString(R.string.please_input_Dc_capacity_of_power_station));
                loadingDialog.dismiss();
                return;
            } else if (Float.valueOf(planCapacity) <= 0 || Float.valueOf(planCapacity) > 100000000) {
                etPlanCapacity.setError(getResources().getString(R.string.capacity_notice));
                return;
            }
            stationBean.setCapacity(planCapacity);
        } catch (NumberFormatException e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e("planCapacity", "NumberFormatException");
        }
        //并网时间
        tag = tvGridTime.getTag();
        if (tag == null) {
            ToastUtil.showMessage(getString(R.string.select_grid_time));
            loadingDialog.dismiss();
            return;
        }
        stationBean.setGridTime(String.valueOf((long) tag));
        String id;
        //并网类型
        id = getGridTypeId();
        if (id == null) {
            ToastUtil.showMessage(getString(R.string.select_grid_type));
            loadingDialog.dismiss();
            return;
        }
        stationBean.setCombinedType(id);
        //电站状态
        id = getStationStatusId();
        if (id == null) {
            ToastUtil.showMessage(getString(R.string.select_station_status));
            loadingDialog.dismiss();
            return;
        }
        stationBean.setStationStatus(id);
        //扶贫电站
        id = getPovertyTypeId();
        if (id == null) {
            loadingDialog.dismiss();
            ToastUtil.showMessage(getString(R.string.select_poverty_type));
            return;
        }
        stationBean.setAidType(id);
        //联系人
        String contractPerson = etContractPerson.getText().toString().trim();
        stationBean.setContact(contractPerson);
        //联系电话
        String tel = etTel.getText().toString().trim();
        stationBean.setPhone(tel);
        stationBean.setId(changeStationInfo.getId() + "");
        Gson gson = new Gson();
        changeStationBaseReq.setStation(stationBean);
        changeStationBaseReq.setType("BASIC");
        String s = gson.toJson(changeStationBaseReq);
        stationPresenter.requestStationUpdate(s);
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
            if (baseEntity instanceof ChangeStationResultInfo) {
                ChangeStationResultInfo c = (ChangeStationResultInfo) baseEntity;
                ChangeStationResultBean changeStationResultBean = c.getChangeStationResultBean();
                if (changeStationResultBean != null) {
                    if (changeStationResultBean.isSuccess()) {
                        DialogUtil.showErrorMsg(getActivity(), getResources().getString(R.string.save_success));
                    } else {
                        DialogUtil.showErrorMsg(getActivity(), getResources().getString(R.string.save_failed));
                    }
                }
            } else if (baseEntity instanceof StationManagementListInfo) {
                StationManagementListInfo stationManagementListInfo = (StationManagementListInfo) baseEntity;
                StationManegementList stationManegementList = stationManagementListInfo.getStationManegementList();
                if (stationManegementList != null) {
                    StationManegementList.DataBean data = stationManegementList.getData();
                    if (data != null) {
                        List<ChangeStationInfo> list = data.getList();
                        if (list != null && list.size() > 0) {
                            changeStationInfoNew = list.get(0);
                            flashData();
                        }
                    }
                }
            }
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
        if (loadingDialog != null) {
            loadingDialog.dismiss();
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
}
