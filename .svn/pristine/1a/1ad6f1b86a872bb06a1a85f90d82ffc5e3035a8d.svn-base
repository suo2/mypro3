package com.huawei.solarsafe.view.stationmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.Constant;
import com.huawei.solarsafe.bean.device.DefaultModuleRequestInfo;
import com.huawei.solarsafe.bean.device.DevHistorySignalData;
import com.huawei.solarsafe.bean.device.InitModuleOptionInfo;
import com.huawei.solarsafe.bean.device.PvBean2;
import com.huawei.solarsafe.bean.device.PvData;
import com.huawei.solarsafe.bean.device.PvDataInfo;
import com.huawei.solarsafe.bean.device.PvFactory;
import com.huawei.solarsafe.bean.device.PvListInfo;
import com.huawei.solarsafe.bean.device.SignalData;
import com.huawei.solarsafe.bean.stationmagagement.Pv;
import com.huawei.solarsafe.presenter.devicemanagement.DevManagementPresenter;
import com.huawei.solarsafe.utils.MySpinner;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DatePiker.DatePikerDialog;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.devicemanagement.IDevManagementView;
import com.huawei.solarsafe.view.login.MyEditText;

import java.util.ArrayList;
import java.util.List;

public class GroupStringConfigActivity extends BaseActivity implements View.OnClickListener, IDevManagementView, View.OnFocusChangeListener {
    private MyEditText groupStringSize;
    private Button btnMidification;
    private Button btnAllMidification;
    private ArrayList<String> sjStrings;
    private ArrayList<String> xhStrings;
    private ArrayList<String> glStrings;
    private String factoryStr;
    private String moduleStr;
    private String powerStr;
    private Button btnCancel, btnSure;
    private int pvSize = 2;
    private List<Pv> pvList;
    private DevManagementPresenter devManagementPresenter;
    private String[] esnList;
    private InitModuleOptionInfo initModuleOptionInfo;
    private List<PvFactory.PvModel> pvModelList;
    private List<PvFactory.PvPower> pvPowerList;
    private LinearLayout pvContainer;
    private DatePikerDialog datePikerDialog;
    private ArrayList<String> moduleTypes;
    private int maxSize = 20;
    private PvListInfo pvListInfo;
    private List<Integer> intTagLis =new ArrayList<>();
    private List<Integer> stringCap = new ArrayList<>();
    private static final String TAG = "GroupStringConfigActivi";
    private int typeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        devManagementPresenter = new DevManagementPresenter();
        devManagementPresenter.setView(this);
        Intent intent = getIntent();
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                esnList = intent.getStringArrayExtra("esnList");
                pvSize = intent.getIntExtra("pvSize", 2);
                pvListInfo = (PvListInfo) intent.getSerializableExtra("pvList");
                typeId = intent.getIntExtra("typeId",0);
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
            maxSize = intent.getIntExtra("maxSize",8);
            groupStringSize.setText(pvSize + "");
            initData();
            for (int i = 0; i < pvSize; i++) {
                addPvView(i);
            }
            requestData();
        }
    }

    private void initModuleTypes() {
        moduleTypes = new ArrayList<>();
        moduleTypes.add(getString(R.string.polycrystal));
        moduleTypes.add(getString(R.string.monocrystal));
        moduleTypes.add(getString(R.string.n_monocrystal));
        moduleTypes.add(getString(R.string.perc_polycrystal));
        moduleTypes.add(getString(R.string.single_monocrystal));
        moduleTypes.add(getString(R.string.double_polycrystal));
        moduleTypes.add(getString(R.string.monocrystal_four_grid_60));
        moduleTypes.add(getString(R.string.monocrystal_four_grid_72));
        moduleTypes.add(getString(R.string.polycrystal_four_grid_60));
        moduleTypes.add(getString(R.string.polycrystal_four_grid_72));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_string_config;
    }

    @Override
    protected void initView() {
        initModuleTypes();
        tv_title.setText(R.string.group_string_detail_setting_str);
        tv_left.setOnClickListener(this);
        groupStringSize = (MyEditText) findViewById(R.id.ed_group_string_size);
        btnMidification = (Button) findViewById(R.id.btn_modification);
        btnAllMidification = (Button) findViewById(R.id.btn_all_modification);
        btnMidification.setOnClickListener(this);
        btnAllMidification.setOnClickListener(this);
        pvContainer = (LinearLayout) findViewById(R.id.ll_pv_container);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        btnSure = (Button) findViewById(R.id.btn_sure);
        btnCancel.setOnClickListener(this);
        btnSure.setOnClickListener(this);
    }

    ViewHolder viewHolder;
    long selectedTime = System.currentTimeMillis();

    private void addPvView(final int positionx) {
        viewHolder = new ViewHolder();
        View convertView = LayoutInflater.from(GroupStringConfigActivity.this).inflate(R.layout.group_string_expand_parent_item, null);
        viewHolder.closeOropenImage = (ImageView) convertView.findViewById(R.id.iv_open_or_close);
        viewHolder.spinnerCj = (MySpinner) convertView.findViewById(R.id.spinner_search_option_zjcs);
        viewHolder.spinnerXh = (Spinner) convertView.findViewById(R.id.spinner_search_option_zjxh);
        viewHolder.spinnerGl = (Spinner) convertView.findViewById(R.id.spinner_search_option_zjgl);
        viewHolder.pvpSize = (MyEditText) convertView.findViewById(R.id.ed_zczj_size);
        viewHolder.pvcap = (TextView) convertView.findViewById(R.id.ed_zc_capicity);
        viewHolder.pvName = (TextView) convertView.findViewById(R.id.tv_pv_name);
        viewHolder.childItemView = (LinearLayout) convertView.findViewById(R.id.child_item);
        viewHolder.spinnerType = (Spinner) convertView.findViewById(R.id.spinner_search_option_zjtype);
        viewHolder.zdyType = (RelativeLayout) convertView.findViewById(R.id.rl_xl_zdy);
        viewHolder.noZdytype = (RelativeLayout) convertView.findViewById(R.id.rl_xl_no_zdy);
        viewHolder.pvCj = (MyEditText) convertView.findViewById(R.id.ed_zj_cs);
        viewHolder.pvXh = (MyEditText) convertView.findViewById(R.id.ed_zj_xh);
        viewHolder.pvGl = (MyEditText) convertView.findViewById(R.id.ed_zj_gl);
        viewHolder.pvType = (MyEditText) convertView.findViewById(R.id.ed_zj_lx);
        viewHolder.pvVTemp = (MyEditText) convertView.findViewById(R.id.ed_zj_dywdxs);
        viewHolder.pvITemp = (MyEditText) convertView.findViewById(R.id.ed_zj_dlwdxs);
        viewHolder.pvGlTemp = (MyEditText) convertView.findViewById(R.id.ed_zj_fzglwdxs);
        viewHolder.pvMaxGlPotintV = (MyEditText) convertView.findViewById(R.id.ed_zj_zdglddy);
        viewHolder.pvMaxGlPintI = (MyEditText) convertView.findViewById(R.id.ed_zj_zdgldl);
        viewHolder.firstYear = (MyEditText) convertView.findViewById(R.id.ed_zj_snsjl);
        viewHolder.secondyear = (MyEditText) convertView.findViewById(R.id.ed_zj_znsjl);
        viewHolder.pvDcSize = (MyEditText) convertView.findViewById(R.id.ed_zj_dcpsm);
        viewHolder.pvKlV = (MyEditText) convertView.findViewById(R.id.ed_zj_kldy);
        viewHolder.pvDlI = (MyEditText) convertView.findViewById(R.id.ed_zj_dldl);
        viewHolder.pvNccEfficiency = (MyEditText) convertView.findViewById(R.id.ed_zj_bczjzhxl);
        viewHolder.pvFillFactor = (MyEditText) convertView.findViewById(R.id.ed_zj_tcyz);
        viewHolder.pvCj.setOnFocusChangeListener(this);
        viewHolder.pvXh.setOnFocusChangeListener(this);
        viewHolder.pvGl.setOnFocusChangeListener(this);
        viewHolder.pvVTemp.setOnFocusChangeListener(this);
        viewHolder.pvITemp.setOnFocusChangeListener(this);
        viewHolder.pvGlTemp.setOnFocusChangeListener(this);
        viewHolder.pvMaxGlPotintV.setOnFocusChangeListener(this);
        viewHolder.pvMaxGlPintI.setOnFocusChangeListener(this);
        viewHolder.firstYear.setOnFocusChangeListener(this);
        viewHolder.secondyear.setOnFocusChangeListener(this);
        viewHolder.pvDcSize.setOnFocusChangeListener(this);
        viewHolder.pvKlV.setOnFocusChangeListener(this);
        viewHolder.pvDlI.setOnFocusChangeListener(this);
        viewHolder.pvNccEfficiency.setOnFocusChangeListener(this);
        viewHolder.pvFillFactor.setOnFocusChangeListener(this);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(GroupStringConfigActivity.this, R.layout.report_spinner_item, moduleTypes);
        viewHolder.spinnerType.setAdapter(spinnerAdapter);
        viewHolder.spinnerType.setTag(positionx);
        viewHolder.spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int tag = (int) parent.getTag();
                Pv pv = pvList.get(tag);
                pv.setModuleType((position + 1) + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                int tag = (int) parent.getTag();
                Pv pv = pvList.get(tag);
                pv.setModuleType(1 + "");
            }
        });
        viewHolder.pvCreateDate = (TextView) convertView.findViewById(R.id.ed_zj_tcrq);
        viewHolder.pvCreateDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupStringSize.requestFocus();
                datePikerDialog = new DatePikerDialog(GroupStringConfigActivity.this, Utils.getFormatTimeYYMMDD(System.currentTimeMillis()), new DatePikerDialog.OnDateFinish() {
                    @Override
                    public void onDateListener(long date) {
                        selectedTime = date;
                        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                        View childAt = pvContainer.getChildAt(positionx);
                        View childItemView = childAt.findViewById(R.id.child_item);
                        TextView pvDate = (TextView) childAt.findViewById(R.id.ed_zj_tcrq);
                        pvDate.setTag(selectedTime);
                        pvDate.setText(Utils.getFormatTimeYYMMDD(selectedTime));
                        pvList.get(positionx).setModuleProductionDate(selectedTime);
                    }
                    @Override
                    public void onSettingClick() {
                    }
                });
                datePikerDialog.updateTime(selectedTime, -1);
                datePikerDialog.show(-1);
            }
        });
        convertView.setTag(viewHolder);
        pvContainer.addView(convertView);
        final Pv pv = pvList.get(positionx);
        viewHolder.pvName.setText(pv.getName());
        if (pv.isExpand) {
            viewHolder.closeOropenImage.setImageResource(R.drawable.domain_zd_icon);
            viewHolder.childItemView.setVisibility(View.VISIBLE);
        } else {
            viewHolder.closeOropenImage.setImageResource(R.drawable.domain_zk_icon);
            viewHolder.childItemView.setVisibility(View.GONE);
        }
        viewHolder.pvpSize.setTag(positionx);
        viewHolder.pvGl.setTag(positionx);
        viewHolder.pvGl.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                int tag = (int) v.getTag();
                View childAt = pvContainer.getChildAt(tag);
                View childItemView = childAt.findViewById(R.id.child_item);
                MyEditText pvpsize = (MyEditText) childAt.findViewById(R.id.ed_zczj_size);
                TextView pvCap = (TextView) childAt.findViewById(R.id.ed_zc_capicity);
                MyEditText pvGl = (MyEditText) childItemView.findViewById(R.id.ed_zj_gl);
                String trim = pvpsize.getText().toString().trim();
                String power = pvGl.getText().toString().trim();
                try {
                    if (!TextUtils.isEmpty(power) && !TextUtils.isEmpty(trim)) {
                        pvCap.setText(Integer.valueOf(trim) * Double.valueOf(power) + "");
                    } else {
                        pvCap.setText("0.0");
                    }
                } catch (NumberFormatException e) {
                    pvCap.setText("0.0");
                    Log.e(TAG, "onFocusChange: " + e.toString() );
                }
                if (!hasFocus) {
                    pvGl.setClearIconVisible(false);
                }
            }
        });
        viewHolder.pvpSize.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                int tag = (int) v.getTag();
                View childAt = pvContainer.getChildAt(tag);
                View childItemView = childAt.findViewById(R.id.child_item);
                ImageView imageView = (ImageView) childAt.findViewById(R.id.iv_open_or_close);
                MyEditText pvpsize = (MyEditText) childAt.findViewById(R.id.ed_zczj_size);
                TextView pvCap = (TextView) childAt.findViewById(R.id.ed_zc_capicity);
                String trim = pvpsize.getText().toString().trim();
                if (!TextUtils.isEmpty(trim)) {
                    try {
                        int size = Integer.valueOf(trim);
                        pvList.get(tag).setSize(size);
                        if (!TextUtils.isEmpty(pvList.get(tag).getPvGl())) {
                            pvList.get(tag).setPvcap(size * Double.valueOf(pvList.get(tag).getPvGl()));
                            pvCap.setText(size * Double.valueOf(pvList.get(tag).getPvGl()) + "");
                        } else {
                            pvCap.setText("0.0");
                        }
                    } catch (NumberFormatException e) {
                        pvCap.setText("0.0");
                        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                        Log.e("NumberFormatException", e.toString());
                    }
                }
                if (hasFocus) {
                    pvList.get(tag).setExpand(true);
                    imageView.setImageResource(R.drawable.domain_zk_icon);
                    childItemView.setVisibility(View.VISIBLE);
                }

            }
        });
        viewHolder.closeOropenImage.setOnClickListener(this);
        viewHolder.closeOropenImage.setTag(positionx);
        viewHolder.closeOropenImage.setTag(R.id.tag_parent_view, pvContainer);
        viewHolder.closeOropenImage.setTag(R.id.tag_child_view, viewHolder.childItemView);
        ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<String>(GroupStringConfigActivity.this, R.layout.report_spinner_item, pv.getSjStrings());
        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<String>(GroupStringConfigActivity.this, R.layout.report_spinner_item, pv.getXhStrings());
        ArrayAdapter<String> spinnerAdapter3 = new ArrayAdapter<String>(GroupStringConfigActivity.this, R.layout.report_spinner_item, pv.getGlStrings());
        viewHolder.spinnerCj.setAdapter(spinnerAdapter1);
        viewHolder.spinnerXh.setAdapter(spinnerAdapter2);
        viewHolder.spinnerGl.setAdapter(spinnerAdapter3);
        viewHolder.spinnerCj.setTag(positionx);
        viewHolder.spinnerXh.setTag(positionx);
        viewHolder.spinnerGl.setTag(positionx);
        viewHolder.spinnerCj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int tag = (int) parent.getTag();
                factoryStr = parent.getItemAtPosition(position).toString();
                Pv pv1 = pvList.get(tag);
                pv1.setPvcj(parent.getItemAtPosition(position).toString());
                if (factoryStr != null) {
                    if (factoryStr.equals(getString(R.string.zdy_str))) {
                        pv1.getXhStrings().clear();
                        pv1.getXhStrings().add(getString(R.string.zdy_str));
                        pv1.getGlStrings().clear();
                        pv1.getGlStrings().add(getString(R.string.zdy_str));
                        pv1.setPvXh(getString(R.string.zdy_str));
                        pv1.setPvGl(getString(R.string.zdy_str));
                        View childAt = pvContainer.getChildAt(tag);
                        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<String>(GroupStringConfigActivity.this, R.layout.report_spinner_item, pv1.getXhStrings());
                        ArrayAdapter<String> spinnerAdapter3 = new ArrayAdapter<String>(GroupStringConfigActivity.this, R.layout.report_spinner_item, pv1.getGlStrings());
                        Spinner spinnerXh = (Spinner) childAt.findViewById(R.id.spinner_search_option_zjxh);
                        Spinner spinnerGl = (Spinner) childAt.findViewById(R.id.spinner_search_option_zjgl);
                        spinnerXh.setAdapter(spinnerAdapter2);
                        spinnerGl.setAdapter(spinnerAdapter3);
                        spinnerXh.setSelection(0, true);
                        spinnerGl.setSelection(0, true);
                        TextView pvcap = (TextView) childAt.findViewById(R.id.ed_zc_capicity);
                        pvcap.setText("0.0");
                        if (!pv1.isNoClearData()) {
                            changePvMode(tag, true, null);
                        }
                    } else {
                        List<PvFactory> pvFactoryList = initModuleOptionInfo.getPvFactoryList();
                        for (PvFactory p : pvFactoryList) {
                            if (factoryStr.equals(p.getFactotyName())) {
                                pvModelList = p.getPvModelList();
                                if (pvModelList != null && pvModelList.size() > 0) {
                                    pv1.getXhStrings().clear();
                                    for (int i = 0; i < pvModelList.size(); i++) {
                                        pv1.getXhStrings().add(pvModelList.get(i).getModelName());
                                    }
                                    ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<String>(GroupStringConfigActivity.this, R.layout.report_spinner_item, pv1.getXhStrings());
                                    View childAt = pvContainer.getChildAt(tag);
                                    Spinner spinnerXh = (Spinner) childAt.findViewById(R.id.spinner_search_option_zjxh);
                                    spinnerXh.setAdapter(spinnerAdapter2);
                                    spinnerXh.setSelection(pv1.getXhPosition(), true);
                                    //宋平修改  崩溃问题
                                    moduleStr = pv1.getXhStrings().get(pvList.get(tag).getXhPosition());
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                factoryStr = getString(R.string.zdy_str);
            }
        });
        viewHolder.spinnerXh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int tag = (int) parent.getTag();
                moduleStr = parent.getItemAtPosition(position).toString();
                pvList.get(tag).setPvXh(parent.getItemAtPosition(position).toString());
                if (moduleStr != null && pvModelList != null) {
                    for (PvFactory.PvModel model : pvModelList) {
                        if (moduleStr.equals(model.getModelName())) {
                            pvPowerList = model.getPvPowerList();
                            if (pvPowerList != null && pvPowerList.size() > 0) {
                                pvList.get(tag).getGlStrings().clear();
                                for (PvFactory.PvPower power : pvPowerList) {
                                    pvList.get(tag).getGlStrings().add(power.getPower());
                                }
                                ArrayAdapter<String> spinnerAdapter3 = new ArrayAdapter<String>(GroupStringConfigActivity.this, R.layout.report_spinner_item, pvList.get(tag).getGlStrings());
                                View childAt = pvContainer.getChildAt(tag);
                                Spinner spinnerGl = (Spinner) childAt.findViewById(R.id.spinner_search_option_zjgl);
                                spinnerGl.setAdapter(spinnerAdapter3);
                                spinnerGl.setSelection(pvList.get(tag).getGlPosition(), true);
                                powerStr = pvList.get(tag).getGlStrings().get(pvList.get(tag).getGlPosition());
                            }
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                moduleStr = getString(R.string.zdy_str);
            }
        });
        viewHolder.spinnerGl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int tag = (int) parent.getTag();
                powerStr = parent.getItemAtPosition(position).toString();
                pvList.get(tag).setPvGl(parent.getItemAtPosition(position).toString());
                //f:UCF_USELESS_CONTROL_FLOW    赵宇凤

                if (!getString(R.string.zdy_str).equals(parent.getItemAtPosition(position).toString())){
                    DefaultModuleRequestInfo defaultModule = new DefaultModuleRequestInfo();
                    defaultModule.setManufacturer(pvList.get(tag).getPvcj());
                    defaultModule.setModuleVersion(pvList.get(tag).getPvXh());
                    defaultModule.setStandardPower(pvList.get(tag).getPvGl());
                    Gson gson = new Gson();
                    String s = gson.toJson(defaultModule);
                    devManagementPresenter.doRequestGetDefaultModule(s, tag);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                moduleStr = getString(R.string.zdy_str);
            }
        });

    }

    private void changePvMode(int tag, boolean zdyTag, PvDataInfo pvDataInfo) {
        View childAt = pvContainer.getChildAt(tag);
        RelativeLayout zdyType = (RelativeLayout) childAt.findViewById(R.id.rl_xl_zdy);
        RelativeLayout noZdytype = (RelativeLayout) childAt.findViewById(R.id.rl_xl_no_zdy);
        MyEditText pvCj = (MyEditText) childAt.findViewById(R.id.ed_zj_cs);
        MyEditText pvXh = (MyEditText) childAt.findViewById(R.id.ed_zj_xh);
        MyEditText pvGl = (MyEditText) childAt.findViewById(R.id.ed_zj_gl);
        MyEditText pvType = (MyEditText) childAt.findViewById(R.id.ed_zj_lx);
        MyEditText pvVTemp = (MyEditText) childAt.findViewById(R.id.ed_zj_dywdxs);
        MyEditText pvITemp = (MyEditText) childAt.findViewById(R.id.ed_zj_dlwdxs);
        MyEditText pvGlTemp = (MyEditText) childAt.findViewById(R.id.ed_zj_fzglwdxs);
        MyEditText pvMaxGlPotintV = (MyEditText) childAt.findViewById(R.id.ed_zj_zdglddy);
        MyEditText pvMaxGlPintI = (MyEditText) childAt.findViewById(R.id.ed_zj_zdgldl);
        MyEditText firstYear = (MyEditText) childAt.findViewById(R.id.ed_zj_snsjl);
        MyEditText secondyear = (MyEditText) childAt.findViewById(R.id.ed_zj_znsjl);
        MyEditText pvDcSize = (MyEditText) childAt.findViewById(R.id.ed_zj_dcpsm);
        MyEditText pvKlV = (MyEditText) childAt.findViewById(R.id.ed_zj_kldy);
        MyEditText pvDlI = (MyEditText) childAt.findViewById(R.id.ed_zj_dldl);
        TextView pvCreateDate = (TextView) childAt.findViewById(R.id.ed_zj_tcrq);
        TextView pvCap = (TextView) childAt.findViewById(R.id.ed_zc_capicity);
        MyEditText pvNccEfficiency = (MyEditText) childAt.findViewById(R.id.ed_zj_bczjzhxl);
        MyEditText pvFillFactor = (MyEditText) childAt.findViewById(R.id.ed_zj_tcyz);
        pvCap.setEnabled(false);
        if (pvDataInfo != null) {
            PvData pvData = pvDataInfo.getPvData();
            if (pvData != null) {
                PvData.DataBean data = pvData.getData();
                if (data != null) {
                    pvCj.setText(data.getManufacturer());
                    pvXh.setText(data.getModuleVersion());
                    pvGl.setText(data.getStandardPower() + "");
                    pvType.setText(getModuleTypeNameById(data.getModuleType()));
                    pvVTemp.setText(data.getVoltageTempCoef() + "");
                    pvITemp.setText(data.getCurrentTempCoef() + "");
                    pvGlTemp.setText(data.getMaxPowerTempCoef() + "");
                    pvMaxGlPotintV.setText(data.getMaxPowerPointVoltage() + "");
                    pvMaxGlPintI.setText(data.getMaxPowerPointCurrent() + "");
                    firstYear.setText(data.getFirstDegradationDrate() + "");
                    secondyear.setText(data.getSecondDegradationDrate() + "");
                    pvDcSize.setText(data.getCellsNumPerModule() + "");
                    pvKlV.setText(data.getComponentsNominalVoltage() + "");
                    pvDlI.setText(data.getNominalCurrentComponent() + "");
                    pvNccEfficiency.setText(data.getModuleRatio() + "");
                    pvFillFactor.setText(data.getFillFactor() + "");
                    if (pvList.get(tag).getSize() == Integer.MIN_VALUE) {
                        pvCap.setText("0.0");
                    } else {
                        pvCap.setText(pvList.get(tag).getSize() * data.getStandardPower() + "");
                    }
                    pvCj.setClearIconVisible(false);
                    pvXh.setClearIconVisible(false);
                    pvGl.setClearIconVisible(false);
                    pvType.setClearIconVisible(false);
                    pvVTemp.setClearIconVisible(false);
                    pvITemp.setClearIconVisible(false);
                    pvGlTemp.setClearIconVisible(false);
                    pvMaxGlPotintV.setClearIconVisible(false);
                    pvMaxGlPintI.setClearIconVisible(false);
                    firstYear.setClearIconVisible(false);
                    secondyear.setClearIconVisible(false);
                    pvDlI.setClearIconVisible(false);
                    pvKlV.setClearIconVisible(false);
                    pvNccEfficiency.setClearIconVisible(false);
                    pvFillFactor.setClearIconVisible(false);
                    pvDcSize.setClearIconVisible(false);
                    pvList.get(tag).setData(data);
                }
            }
        } else {
            pvCj.setText("");
            pvXh.setText("");
            pvGl.setText("");
            pvType.setText("");
            pvVTemp.setText("");
            pvITemp.setText("");
            pvGlTemp.setText("");
            pvMaxGlPotintV.setText("");
            pvMaxGlPintI.setText("");
            firstYear.setText("");
            secondyear.setText("");
            pvDcSize.setText("");
            pvKlV.setText("");
            pvDlI.setText("");
            pvCreateDate.setText("");
            pvNccEfficiency.setText("");
            pvFillFactor.setText("");
        }
        if (zdyTag) {
            zdyType.setVisibility(View.VISIBLE);
            noZdytype.setVisibility(View.GONE);
            pvCj.setBackgroundResource(R.drawable.login_ed_bg);
            pvXh.setBackgroundResource(R.drawable.login_ed_bg);
            pvGl.setBackgroundResource(R.drawable.login_ed_bg);
            pvType.setBackgroundResource(R.drawable.login_ed_bg);
            pvVTemp.setBackgroundResource(R.drawable.login_ed_bg);
            pvITemp.setBackgroundResource(R.drawable.login_ed_bg);
            pvGlTemp.setBackgroundResource(R.drawable.login_ed_bg);
            pvMaxGlPotintV.setBackgroundResource(R.drawable.login_ed_bg);
            pvMaxGlPintI.setBackgroundResource(R.drawable.login_ed_bg);
            firstYear.setBackgroundResource(R.drawable.login_ed_bg);
            secondyear.setBackgroundResource(R.drawable.login_ed_bg);
            pvDcSize.setBackgroundResource(R.drawable.login_ed_bg);
            pvKlV.setBackgroundResource(R.drawable.login_ed_bg);
            pvDlI.setBackgroundResource(R.drawable.login_ed_bg);
            pvCreateDate.setBackgroundResource(R.drawable.login_ed_bg);
            pvNccEfficiency.setBackgroundResource(R.drawable.login_ed_bg);
            pvFillFactor.setBackgroundResource(R.drawable.login_ed_bg);
            pvCj.setEnabled(true);
            pvXh.setEnabled(true);
            pvGl.setEnabled(true);
            pvType.setEnabled(true);
            pvVTemp.setEnabled(true);
            pvITemp.setEnabled(true);
            pvGlTemp.setEnabled(true);
            pvMaxGlPotintV.setEnabled(true);
            pvMaxGlPintI.setEnabled(true);
            firstYear.setEnabled(true);
            secondyear.setEnabled(true);
            pvDcSize.setEnabled(true);
            pvKlV.setEnabled(true);
            pvDlI.setEnabled(true);
            pvCreateDate.setEnabled(true);
            pvNccEfficiency.setEnabled(true);
            pvFillFactor.setEnabled(true);
        } else {
            zdyType.setVisibility(View.GONE);
            noZdytype.setVisibility(View.VISIBLE);
            pvCj.setBackgroundResource(R.drawable.edtv_no_ed_bg);
            pvXh.setBackgroundResource(R.drawable.edtv_no_ed_bg);
            pvGl.setBackgroundResource(R.drawable.edtv_no_ed_bg);
            pvType.setBackgroundResource(R.drawable.edtv_no_ed_bg);
            pvVTemp.setBackgroundResource(R.drawable.edtv_no_ed_bg);
            pvITemp.setBackgroundResource(R.drawable.edtv_no_ed_bg);
            pvGlTemp.setBackgroundResource(R.drawable.edtv_no_ed_bg);
            pvMaxGlPotintV.setBackgroundResource(R.drawable.edtv_no_ed_bg);
            pvMaxGlPintI.setBackgroundResource(R.drawable.edtv_no_ed_bg);
            firstYear.setBackgroundResource(R.drawable.edtv_no_ed_bg);
            secondyear.setBackgroundResource(R.drawable.edtv_no_ed_bg);
            pvDcSize.setBackgroundResource(R.drawable.edtv_no_ed_bg);
            pvKlV.setBackgroundResource(R.drawable.edtv_no_ed_bg);
            pvDlI.setBackgroundResource(R.drawable.edtv_no_ed_bg);
            pvNccEfficiency.setBackgroundResource(R.drawable.edtv_no_ed_bg);
            pvFillFactor.setBackgroundResource(R.drawable.edtv_no_ed_bg);
            pvCj.setEnabled(false);
            pvXh.setEnabled(false);
            pvGl.setEnabled(false);
            pvType.setEnabled(false);
            pvVTemp.setEnabled(false);
            pvITemp.setEnabled(false);
            pvGlTemp.setEnabled(false);
            pvMaxGlPotintV.setEnabled(false);
            pvMaxGlPintI.setEnabled(false);
            firstYear.setEnabled(false);
            secondyear.setEnabled(false);
            pvDcSize.setEnabled(false);
            pvKlV.setEnabled(false);
            pvDlI.setEnabled(false);
            pvNccEfficiency.setEnabled(false);
            pvFillFactor.setEnabled(false);
        }
        //批量设置最后一个，清空pvList中存放的posistion信息
        if (tag == pvList.size() - 1) {
            initPvListPosition();
        }
    }

    /**
     * 清空pvList中存放的posistion信息
     */
    private void initPvListPosition() {
        for (Pv pv : pvList) {
            pv.setCjPosition(0);
            pv.setXhPosition(0);
            pv.setGlPosition(0);
        }
    }

    /**
     * 初始化原始数据
     */
    private void initData() {
        pvList = new ArrayList<>();
        for (int i = 0; i < pvSize; i++) {
            Pv pv = new Pv();
            pv.setName("PV" + (i + 1));
            pv.setExpand(false);
            sjStrings = new ArrayList<>();
            sjStrings.add(getString(R.string.zdy_str));
            xhStrings = new ArrayList<>();
            xhStrings.add(getString(R.string.zdy_str));
            glStrings = new ArrayList<>();
            glStrings.add(getString(R.string.zdy_str));
            pv.setSjStrings(sjStrings);
            pv.setXhStrings(xhStrings);
            pv.setGlStrings(glStrings);
            pvList.add(pv);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                DialogUtil.showChooseDialog(this, "", getString(R.string.sure_to_cancel_setting), getString(R.string.sure),
                        getString(R.string.cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
                break;
            case R.id.btn_modification:
                groupStringSize.requestFocus();
                int sizetemp = 0;
                try {
                    sizetemp = Integer.valueOf(groupStringSize.getText().toString().trim());
                } catch (NumberFormatException e) {
                    Log.e(TAG, "onClick: " + e.toString() );
                }
                if (TextUtils.isEmpty(groupStringSize.getText().toString().trim())) {
                    DialogUtil.showErrorMsg(this, getString(R.string.setting_gruopstring_null_str));
                    return;
                } else if (pvSize == sizetemp) {
                    return;
                } else {
                    try {
                        pvSize = Integer.valueOf(groupStringSize.getText().toString().trim());
                    } catch (NumberFormatException e) {
                        Log.e(TAG, "onClick: " + e.toString() );
                    }
                    if (pvSize <= 0 || pvSize > maxSize) {
                        DialogUtil.showErrorMsg(this, getString(R.string.group_string_install_notice_str) + "1-" + maxSize + getString(R.string.pv_unit_str));
                        return;
                    }
                }
                initData();
                List<PvFactory> pvFactoryList = initModuleOptionInfo.getPvFactoryList();
                if (pvFactoryList != null) {
                    for (Pv pv : pvList) {
                        for (int i = 0; i < pvFactoryList.size(); i++) {
                            pv.getSjStrings().add(pvFactoryList.get(i).getFactotyName());
                        }
                    }
                }
                pvContainer.removeAllViews();
                for (int i = 0; i < pvSize; i++) {
                    addPvView(i);
                }
                break;
            case R.id.btn_all_modification:
                groupStringSize.requestFocus();
                if (pvList == null || pvList.size() == 0) {
                    DialogUtil.showErrorMsg(this, getString(R.string.not_have_group_string_str));
                    return;
                }
                if (pvList.size() == 1) {
                    DialogUtil.showErrorMsg(this, getString(R.string.pvsize_one_not_all_modification));
                    return;
                }
                Pv pv = pvList.get(0);
                if (checkPvAllSet(pv)) {
                    DialogUtil.showChooseDialog(this, "", getString(R.string.pv_setting_modifaall_notice), getString(R.string.sure),
                            getString(R.string.cancel), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    allModification();
                                }
                            });
                } else {
                    DialogUtil.showErrorMsg(this, getString(R.string.cannot_all_modification_notice));
                }

                break;
            case R.id.btn_cancel:
                DialogUtil.showChooseDialog(this, "", getString(R.string.sure_to_cancel_setting), getString(R.string.sure),
                        getString(R.string.cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
                break;
            case R.id.btn_sure:
                savePvData();
                break;
            case R.id.iv_open_or_close:
                int position = (int) v.getTag();
                LinearLayout childView = (LinearLayout) v.getTag(R.id.tag_child_view);
                if (pvList.get(position).isExpand) {
                    pvList.get(position).setExpand(false);
                    ((ImageView) v).setImageResource(R.drawable.domain_zk_icon);
                    childView.setVisibility(View.GONE);
                } else {
                    pvList.get(position).setExpand(true);
                    ((ImageView) v).setImageResource(R.drawable.domain_zd_icon);
                    childView.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }

    private void savePvData() {
        //检测是否每个组串都设置了
        if (checkPvAllSet2()) {
            Intent intent = new Intent();
            PvListInfo pvListInfo = new PvListInfo();
            PvBean2[] pvBeanArray = new PvBean2[pvList.size()];
            for (int i = 0; i < pvList.size(); i++) {
                Pv p = pvList.get(i);
                PvData.DataBean pv = p.getData();
                pvBeanArray[i] = new PvBean2();
                pvBeanArray[i].setCellsNumPerModule(pv.getCellsNumPerModule() + "");
                pvBeanArray[i].setManufacturer(pv.getManufacturer());
                pvBeanArray[i].setComponentsNominalVoltage(pv.getComponentsNominalVoltage() + "");
                pvBeanArray[i].setCurrentTempCoef(pv.getCurrentTempCoef() + "");
                pvBeanArray[i].setFillFactor(pv.getFillFactor() + "");
                pvBeanArray[i].setFirstDegradationDrate(pv.getFirstDegradationDrate() + "");
                pvBeanArray[i].setSecondDegradationDrate(pv.getSecondDegradationDrate() + "");
                pvBeanArray[i].setIsDefault(true);
                pvBeanArray[i].setMaxPowerPointCurrent(pv.getMaxPowerPointCurrent() + "");
                pvBeanArray[i].setMaxPowerPointVoltage(pv.getMaxPowerPointVoltage() + "");
                pvBeanArray[i].setMaxPowerTempCoef(pv.getMaxPowerTempCoef() + "");
                pvBeanArray[i].setNominalCurrentComponent(pv.getNominalCurrentComponent() + "");
                pvBeanArray[i].setStandardPower(pv.getStandardPower() + "");
                pvBeanArray[i].setModulesNumPerString(p.getSize() + "");
                pvBeanArray[i].setModuleProductionDate(p.getModuleProductionDate());
                pvBeanArray[i].setModuleVersion(pv.getModuleVersion());
                pvBeanArray[i].setModuleType(pv.getModuleType());
                pvBeanArray[i].setVoltageTempCoef(pv.getVoltageTempCoef() + "");
                pvBeanArray[i].setModuleRatio(pv.getModuleRatio() + "");
            }
            for (int i = 0; i < pvList.size(); i++) {
                intTagLis.add(i + 1);
                stringCap.add((int) (pvList.get(i).getSize() * pvList.get(i).getData().getStandardPower()));
            }
            pvListInfo.setPvList(pvBeanArray);
            Bundle bundle = new Bundle();
            bundle.putSerializable("pvList", pvListInfo);
            bundle.putIntegerArrayList("stringCap", (ArrayList<Integer>) stringCap);
            bundle.putIntegerArrayList("intTagList", (ArrayList<Integer>) intTagLis);
            intent.putExtra("bundle", bundle);
            intent.putExtra("esnList", esnList);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            DialogUtil.showErrorMsg(GroupStringConfigActivity.this, getString(R.string.all_pv_set_notice_str));
        }
    }

    //检测是否每个组串都设置了
    private boolean checkPvAllSet2() {
        boolean isAllSet = true;
        for (int i = 0; i < pvList.size(); i++) {
            Pv pv = pvList.get(i);
            View childAt = pvContainer.getChildAt(i);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.pvpSize = (MyEditText) childAt.findViewById(R.id.ed_zczj_size);
            if (getString(R.string.zdy_str).equals(pv.getPvcj())) {
                isAllSet = zdyCheckAllSet(childAt, viewHolder, i);
            } else {
                if (pv.getSize() == Integer.MIN_VALUE) {
                    viewHolder.pvpSize.setError(getString(R.string.intput_fw_0_50));
                    isAllSet = false;
                } else if (pv.getSize() > 200 || pv.getSize() < 0) {
                    viewHolder.pvpSize.setError(getString(R.string.intput_fw_0_50));
                    isAllSet = false;
                }
                if (TextUtils.isEmpty(pv.getPvXh())) {
                    isAllSet = false;
                }
                if (TextUtils.isEmpty(pv.getPvcj())) {
                    isAllSet = false;
                }
                if (TextUtils.isEmpty(pv.getPvGl())) {
                    isAllSet = false;
                }
                if (pv.getModuleProductionDate() == Long.MIN_VALUE) {
                    isAllSet = false;
                }
                if (pv.getData() == null) {
                    isAllSet = false;
                }
            }
        }
        return isAllSet;
    }

    /**
     * 检测自定义时各参数是否设置完全
     *
     * @param childAt
     * @param viewHolder
     */
    private boolean zdyCheckAllSet(View childAt, ViewHolder viewHolder, int position) {
        viewHolder.pvpSize = (MyEditText) childAt.findViewById(R.id.ed_zczj_size);
        viewHolder.pvCj = (MyEditText) childAt.findViewById(R.id.ed_zj_cs);
        viewHolder.pvXh = (MyEditText) childAt.findViewById(R.id.ed_zj_xh);
        viewHolder.pvGl = (MyEditText) childAt.findViewById(R.id.ed_zj_gl);
        viewHolder.pvVTemp = (MyEditText) childAt.findViewById(R.id.ed_zj_dywdxs);
        viewHolder.pvITemp = (MyEditText) childAt.findViewById(R.id.ed_zj_dlwdxs);
        viewHolder.pvGlTemp = (MyEditText) childAt.findViewById(R.id.ed_zj_fzglwdxs);
        viewHolder.pvMaxGlPotintV = (MyEditText) childAt.findViewById(R.id.ed_zj_zdglddy);
        viewHolder.pvMaxGlPintI = (MyEditText) childAt.findViewById(R.id.ed_zj_zdgldl);
        viewHolder.firstYear = (MyEditText) childAt.findViewById(R.id.ed_zj_snsjl);
        viewHolder.secondyear = (MyEditText) childAt.findViewById(R.id.ed_zj_znsjl);
        viewHolder.pvDcSize = (MyEditText) childAt.findViewById(R.id.ed_zj_dcpsm);
        viewHolder.pvKlV = (MyEditText) childAt.findViewById(R.id.ed_zj_kldy);
        viewHolder.pvDlI = (MyEditText) childAt.findViewById(R.id.ed_zj_dldl);
        viewHolder.pvCreateDate = (TextView) childAt.findViewById(R.id.ed_zj_tcrq);
        viewHolder.pvcap = (TextView) childAt.findViewById(R.id.ed_zc_capicity);
        viewHolder.pvNccEfficiency = (MyEditText) childAt.findViewById(R.id.ed_zj_bczjzhxl);
        viewHolder.pvFillFactor = (MyEditText) childAt.findViewById(R.id.ed_zj_tcyz);
        String pvpSize = viewHolder.pvpSize.getText().toString().trim();
        String pvCj = viewHolder.pvCj.getText().toString().trim();
        String pvXh = viewHolder.pvXh.getText().toString().trim();
        String pvGl = viewHolder.pvGl.getText().toString().trim();
        String pvVTemp = viewHolder.pvVTemp.getText().toString().trim();
        String pvITemp = viewHolder.pvITemp.getText().toString().trim();
        String pvGlTemp = viewHolder.pvGlTemp.getText().toString().trim();
        String pvMaxGlPotintV = viewHolder.pvMaxGlPotintV.getText().toString().trim();
        String pvMaxGlPintI = viewHolder.pvMaxGlPintI.getText().toString().trim();
        String firstYear = viewHolder.firstYear.getText().toString().trim();
        String secondyear = viewHolder.secondyear.getText().toString().trim();
        String pvDcSize = viewHolder.pvDcSize.getText().toString().trim();
        String pvKlV = viewHolder.pvKlV.getText().toString().trim();
        String pvDlI = viewHolder.pvDlI.getText().toString().trim();
        String pvNccEfficiency = viewHolder.pvNccEfficiency.getText().toString().trim();
        String pvFillFactor = viewHolder.pvFillFactor.getText().toString().trim();
        String pvCreateDate = viewHolder.pvCreateDate.getText().toString().trim();
        try {
            if (TextUtils.isEmpty(pvpSize)) {
                viewHolder.pvpSize.setError(getString(R.string.intput_fw_0_50));
                return false;
            } else if (Integer.valueOf(pvpSize) > 200 || Integer.valueOf(pvpSize) < 0) {
                viewHolder.pvpSize.setError(getString(R.string.intput_fw_0_50));
                return false;
            }
            if (TextUtils.isEmpty(pvCj)) {
                viewHolder.pvCj.setError(getString(R.string.please_input_factory_name));
                return false;
            }
            if (TextUtils.isEmpty(pvXh)) {
                viewHolder.pvXh.setError(getString(R.string.please_input_pv_module));
                return false;
            }
            if (TextUtils.isEmpty(pvGl)) {
                viewHolder.pvGl.setError(getString(R.string.input_fw_gl));
                return false;
            } else if (Double.valueOf(pvGl) > 500 || Double.valueOf(pvGl) < 0) {
                viewHolder.pvGl.setError(getString(R.string.input_fw_gl));
                return false;
            }
            if (TextUtils.isEmpty(pvVTemp)) {
                viewHolder.pvVTemp.setError(getString(R.string.input_fw_1));
                return false;
            } else if (Double.valueOf(pvVTemp) > 0 || Double.valueOf(pvVTemp) < -1) {
                viewHolder.pvVTemp.setError(getString(R.string.input_fw_1));
                return false;
            }
            if (TextUtils.isEmpty(pvITemp)) {
                viewHolder.pvITemp.setError(getString(R.string.input_fw_0_0_2));
                return false;
            } else if (Double.valueOf(pvITemp) > 0.2 || Double.valueOf(pvITemp) < 0) {
                viewHolder.pvITemp.setError(getString(R.string.input_fw_0_0_2));
                return false;
            }
            if (TextUtils.isEmpty(pvGlTemp)) {
                viewHolder.pvGlTemp.setError(getString(R.string.input_fw_1));
                return false;
            } else if (Double.valueOf(pvGlTemp) > 0 || Double.valueOf(pvGlTemp) < -1) {
                viewHolder.pvGlTemp.setError(getString(R.string.input_fw_1));
                return false;
            }
            if (TextUtils.isEmpty(pvMaxGlPotintV)) {
                viewHolder.pvMaxGlPotintV.setError(getString(R.string.input_fw_1000_1000));
                return false;
            } else if (Double.valueOf(pvMaxGlPotintV) > 1000 || Double.valueOf(pvMaxGlPotintV) < -1000) {
                viewHolder.pvMaxGlPotintV.setError(getString(R.string.input_fw_1000_1000));
                return false;
            }
            if (TextUtils.isEmpty(pvMaxGlPintI)) {
                viewHolder.pvMaxGlPintI.setError(getString(R.string.input_fw_1000_1000));
                return false;
            } else if (Double.valueOf(pvMaxGlPintI) > 1000 || Double.valueOf(pvMaxGlPintI) < -1000) {
                viewHolder.pvMaxGlPintI.setError(getString(R.string.input_fw_1000_1000));
                return false;
            }
            if (TextUtils.isEmpty(firstYear)) {
                viewHolder.firstYear.setError(getString(R.string.input_fw_0_100));
                return false;
            } else if (Double.valueOf(firstYear) > 100 || Double.valueOf(firstYear) < 0) {
                viewHolder.firstYear.setError(getString(R.string.input_fw_0_100));
                return false;
            }
            if (TextUtils.isEmpty(secondyear)) {
                viewHolder.secondyear.setError(getString(R.string.input_fw_0_100));
                return false;
            } else if (Double.valueOf(secondyear) > 100 || Double.valueOf(secondyear) < 0) {
                viewHolder.secondyear.setError(getString(R.string.input_fw_0_100));
                return false;
            }
            if (TextUtils.isEmpty(pvDcSize)) {
                viewHolder.pvDcSize.setError(getString(R.string.input_fw_0_200));
                return false;
            } else if (Double.valueOf(pvDcSize) > 200 || Double.valueOf(pvDcSize) < 0) {
                viewHolder.pvDcSize.setError(getString(R.string.input_fw_0_200));
                return false;
            }
            if (TextUtils.isEmpty(pvKlV)) {
                viewHolder.pvKlV.setError(getString(R.string.intput_fw_0_80));
                return false;
            } else if (Double.valueOf(pvKlV) > 80 || Double.valueOf(pvKlV) < 0) {
                viewHolder.pvKlV.setError(getString(R.string.intput_fw_0_80));
                return false;
            }
            if (TextUtils.isEmpty(pvDlI)) {
                viewHolder.pvDlI.setError(getString(R.string.input_fw_0_20));
                return false;
            } else if (Double.valueOf(pvDlI) > 20 || Double.valueOf(pvDlI) < 0) {
                viewHolder.pvDlI.setError(getString(R.string.input_fw_0_20));
                return false;
            }
            if (TextUtils.isEmpty(pvNccEfficiency)) {
                viewHolder.pvNccEfficiency.setError(getString(R.string.input_fw_0_100));
                return false;
            } else if (Double.valueOf(pvNccEfficiency) > 100 || Double.valueOf(pvNccEfficiency) < 0) {
                viewHolder.pvNccEfficiency.setError(getString(R.string.input_fw_0_100));
                return false;
            }
            if (TextUtils.isEmpty(pvFillFactor)) {
                viewHolder.pvFillFactor.setError(getString(R.string.input_fw_0_1));
                return false;
            } else if (Double.valueOf(pvFillFactor) > 85 || Double.valueOf(pvFillFactor) < 65) {
                viewHolder.pvFillFactor.setError(getString(R.string.input_fw_0_1));
                return false;
            }
            if (TextUtils.isEmpty(pvCreateDate)) {
                viewHolder.pvCreateDate.setHint(getString(R.string.please_input_date));
                return false;
            }
            Pv pv = pvList.get(position);
            pv.setSize(Integer.valueOf(pvpSize));
            pv.setPvGl(pvGl);
            pv.setManufacturer(pvCj);
            pv.setModuleVersion(pvXh);
            pv.setStandardPower(pvGl);
            pv.setVoltageTempCoef(pvVTemp);
            pv.setCurrentTempCoef(pvITemp);
            pv.setMaxPowerTempCoef(pvGlTemp);
            pv.setMaxPowerPointVoltage(pvMaxGlPotintV);
            pv.setMaxPowerPointCurrent(pvMaxGlPintI);
            pv.setFirstDegradationDrate(firstYear);
            pv.setSecondDegradationDrate(secondyear);
            pv.setCellsNumPerModule(pvDcSize);
            pv.setComponentsNominalVoltage(pvKlV);
            pv.setNominalCurrentComponent(pvDlI);
            pv.setModuleType(pv.getModuleType());
            pv.setModuleRatio(pvNccEfficiency);
            pv.setFillFactor(pvFillFactor);
            pv.setModuleProductionDate((long) viewHolder.pvCreateDate.getTag());
            PvData.DataBean dataBean = new PvData.DataBean();
            dataBean.setManufacturer(pvCj);
            dataBean.setModuleVersion(pvXh);
            dataBean.setStandardPower(Double.valueOf(pvGl));
            dataBean.setVoltageTempCoef(Double.valueOf(pvVTemp));
            dataBean.setCurrentTempCoef(Double.valueOf(pvITemp));
            dataBean.setMaxPowerTempCoef(Double.valueOf(pvGlTemp));
            dataBean.setMaxPowerPointVoltage(Double.valueOf(pvMaxGlPotintV));
            dataBean.setMaxPowerPointCurrent(Double.valueOf(pvMaxGlPintI));
            dataBean.setFirstDegradationDrate(Double.valueOf(firstYear));
            dataBean.setSecondDegradationDrate(Double.valueOf(secondyear));
            Double aDouble = Double.valueOf(pvDcSize);
            int v = (int) (aDouble / 1);
            dataBean.setCellsNumPerModule(v);
            dataBean.setComponentsNominalVoltage(Double.valueOf(pvKlV));
            dataBean.setNominalCurrentComponent(Double.valueOf(pvDlI));
            dataBean.setModuleType(pv.getModuleType());
            dataBean.setModuleRatio(Double.valueOf(pvNccEfficiency));
            dataBean.setFillFactor(Double.valueOf(pvFillFactor));
            dataBean.setCreateTime((long) viewHolder.pvCreateDate.getTag());
            pv.setData(dataBean);
            viewHolder.pvcap.setText(Integer.valueOf(pvpSize) * Double.valueOf(pvGl) + "");
            pv.setPvGl(Integer.valueOf(pvpSize) * Double.valueOf(pvGl) + "");
        } catch (NumberFormatException e) {
            Log.e(TAG, "zdyCheckAllSet: " + e.toString() );
        }
        return true;
    }

    private void allModification() {
        Pv pv = pvList.get(0);
        for (int i = 1; i < pvList.size(); i++) {
            if (getString(R.string.zdy_str).equals(pv.getPvcj())) {
                View childAt = pvContainer.getChildAt(i);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.spinnerType = (Spinner) childAt.findViewById(R.id.spinner_search_option_zjtype);
                viewHolder.pvpSize = (MyEditText) childAt.findViewById(R.id.ed_zczj_size);
                viewHolder.pvCj = (MyEditText) childAt.findViewById(R.id.ed_zj_cs);
                viewHolder.pvXh = (MyEditText) childAt.findViewById(R.id.ed_zj_xh);
                viewHolder.pvGl = (MyEditText) childAt.findViewById(R.id.ed_zj_gl);
                viewHolder.pvVTemp = (MyEditText) childAt.findViewById(R.id.ed_zj_dywdxs);
                viewHolder.pvITemp = (MyEditText) childAt.findViewById(R.id.ed_zj_dlwdxs);
                viewHolder.pvGlTemp = (MyEditText) childAt.findViewById(R.id.ed_zj_fzglwdxs);
                viewHolder.pvMaxGlPotintV = (MyEditText) childAt.findViewById(R.id.ed_zj_zdglddy);
                viewHolder.pvMaxGlPintI = (MyEditText) childAt.findViewById(R.id.ed_zj_zdgldl);
                viewHolder.firstYear = (MyEditText) childAt.findViewById(R.id.ed_zj_snsjl);
                viewHolder.secondyear = (MyEditText) childAt.findViewById(R.id.ed_zj_znsjl);
                viewHolder.pvDcSize = (MyEditText) childAt.findViewById(R.id.ed_zj_dcpsm);
                viewHolder.pvKlV = (MyEditText) childAt.findViewById(R.id.ed_zj_kldy);
                viewHolder.pvDlI = (MyEditText) childAt.findViewById(R.id.ed_zj_dldl);
                viewHolder.pvCreateDate = (TextView) childAt.findViewById(R.id.ed_zj_tcrq);
                viewHolder.pvcap = (TextView) childAt.findViewById(R.id.ed_zc_capicity);
                viewHolder.pvNccEfficiency = (MyEditText) childAt.findViewById(R.id.ed_zj_bczjzhxl);
                viewHolder.pvFillFactor = (MyEditText) childAt.findViewById(R.id.ed_zj_tcyz);
                PvData.DataBean data = pv.getData();
                viewHolder.pvpSize.setText(pv.getSize() + "");
                viewHolder.pvCj.setText(pv.getManufacturer());
                viewHolder.pvXh.setText(pv.getModuleVersion());
                viewHolder.pvGl.setText(pv.getStandardPower() + "");
                viewHolder.pvVTemp.setText(pv.getVoltageTempCoef() + "");
                viewHolder.pvITemp.setText(pv.getCurrentTempCoef() + "");
                viewHolder.pvGlTemp.setText(pv.getMaxPowerTempCoef() + "");
                viewHolder.pvMaxGlPotintV.setText(pv.getMaxPowerPointVoltage() + "");
                viewHolder.pvMaxGlPintI.setText(pv.getMaxPowerPointCurrent() + "");
                viewHolder.firstYear.setText(pv.getFirstDegradationDrate() + "");
                viewHolder.secondyear.setText(pv.getSecondDegradationDrate() + "");
                viewHolder.pvDcSize.setText(pv.getCellsNumPerModule() + "");
                viewHolder.pvKlV.setText(pv.getComponentsNominalVoltage() + "");
                viewHolder.pvDlI.setText(pv.getNominalCurrentComponent() + "");
                viewHolder.pvCreateDate.setText(Utils.getFormatTimeYYMMDD(pv.getModuleProductionDate()));
                viewHolder.pvCreateDate.setTag(pv.getModuleProductionDate());
                viewHolder.pvcap.setText(pv.getSize() * data.getStandardPower() + "");
                viewHolder.pvNccEfficiency.setText(pv.getModuleRatio());
                viewHolder.pvFillFactor.setText(pv.getFillFactor());
                try {
                    int typeId = Integer.valueOf(pv.getModuleType());
                    if (typeId > 0 && typeId < 27) {
                        viewHolder.spinnerType.setSelection(typeId - 1, true);
                    }
                } catch (NumberFormatException e) {
                    Log.e(TAG, "allModification: " + e.toString() );
                }
            } else {
                View childAt = pvContainer.getChildAt(i);
                Pv pv1 = pvList.get(i);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.spinnerCj = (MySpinner) childAt.findViewById(R.id.spinner_search_option_zjcs);
                viewHolder.spinnerXh = (Spinner) childAt.findViewById(R.id.spinner_search_option_zjxh);
                viewHolder.spinnerGl = (Spinner) childAt.findViewById(R.id.spinner_search_option_zjgl);
                viewHolder.pvpSize = (MyEditText) childAt.findViewById(R.id.ed_zczj_size);
                viewHolder.pvcap = (TextView) childAt.findViewById(R.id.ed_zc_capicity);
                viewHolder.pvCreateDate = (TextView) childAt.findViewById(R.id.ed_zj_tcrq);
                for (int j = 0; j < pv.getSjStrings().size(); j++) {
                    if (pv.getPvcj().equals(pv.getSjStrings().get(j))) {
                        pv1.setPvcj(pv.getPvcj());
                        pv1.setCjPosition(j);
                    }
                }
                for (int j = 0; j < pv.getXhStrings().size(); j++) {
                    if (pv.getPvXh().equals(pv.getXhStrings().get(j))) {
                        pv1.setPvXh(pv.getPvXh());
                        pv1.setXhPosition(j);
                    }
                }
                for (int j = 0; j < pv.getGlStrings().size(); j++) {
                    if (pv.getPvGl().equals(pv.getGlStrings().get(j))) {
                        pv1.setPvGl(pv.getPvGl());
                        pv1.setGlPosition(j);
                    }
                }
                viewHolder.pvpSize.setText(pv.getSize() + "");
                viewHolder.pvCreateDate.setText(Utils.getFormatTimeYYMMDD(pv.getModuleProductionDate()));
                viewHolder.pvCreateDate.setTag(pv.getModuleProductionDate());
                pv1.setSize(pv.getSize());
                pv1.setModuleProductionDate(pv.getModuleProductionDate());
                viewHolder.spinnerCj.setSelection(pv1.getCjPosition(), true);
            }
        }
    }

    private boolean checkPvAllSet(Pv pv) {
        if (getString(R.string.zdy_str).equals(pv.getPvcj())) {
            View childAt = pvContainer.getChildAt(0);
            ViewHolder viewHolder = new ViewHolder();
            return zdyCheckAllSet(childAt, viewHolder, 0);
        } else {
            if (TextUtils.isEmpty(pv.getPvcj())) {
                return false;
            }
            if (TextUtils.isEmpty(pv.getPvXh())) {
                return false;
            }

            if (TextUtils.isEmpty(pv.getPvGl())) {
                return false;
            }
            if (pv.getSize() == Integer.MIN_VALUE) {
                return false;
            }
            if (pv.getModuleProductionDate() == Long.MIN_VALUE) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void requestData() {
        ESNArray esnArray = new ESNArray();
        esnArray.setEsnList(esnList);
        Gson gson = new Gson();
        String s = gson.toJson(esnArray);
        devManagementPresenter.doRequestInitModuleOption(s);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    class ESNArray {
        String[] devIdList;

        public String[] getEsnList() {
            return devIdList;
        }

        public void setEsnList(String[] esnList) {
            this.devIdList = esnList;
        }
    }

    @Override
    public void getData(BaseEntity baseEntity) {
        if (baseEntity == null) {
            ToastUtil.showMessage(getString(R.string.net_error));
            return;
        }
        if (baseEntity instanceof InitModuleOptionInfo) {
            initModuleOptionInfo = (InitModuleOptionInfo) baseEntity;
            initGroupStringOptionData();
        } else if (baseEntity instanceof PvDataInfo) {
            PvDataInfo pvDataInfo = (PvDataInfo) baseEntity;
            try {
                changePvMode(Integer.valueOf(pvDataInfo.getTag()), false, pvDataInfo);
            } catch (NumberFormatException e) {
                Log.e(TAG, "getData: " + e.toString() );
            }
        }
    }

    //当选择的只有一个设置时返回的初始默认值
    List<InitModuleOptionInfo.CurrentModule> currentModuleList;

    /**
     * 请求数据回来后
     */
    private void initGroupStringOptionData() {
        List<PvFactory> pvFactoryList = initModuleOptionInfo.getPvFactoryList();
        //当选择的只有一个设置时返回的初始默认值
        currentModuleList = initModuleOptionInfo.getCurrentModuleList();
        if (esnList.length == 1 && currentModuleList != null && currentModuleList.size() > 0) {
            if (pvFactoryList != null) {
                for (Pv pv : pvList) {
                    for (int i = 0; i < pvFactoryList.size(); i++) {
                        pv.getSjStrings().add(pvFactoryList.get(i).getFactotyName());
                    }
                }
            }
        } else {
            if (pvFactoryList != null) {
                for (Pv pv : pvList) {
                    for (int i = 0; i < pvFactoryList.size(); i++) {
                        pv.getSjStrings().add(pvFactoryList.get(i).getFactotyName());
                    }
                }
            }
        }
        //缓存数据反填
        if (pvListInfo != null) {
            PvBean2[] pvListTemp = pvListInfo.getPvList();
            if (pvListTemp != null && pvListTemp.length > 0) {
                pvContainer.removeAllViews();
                pvSize = pvListTemp.length;
                groupStringSize.setText(pvSize + "");
                initData();
                for (int i = 0; i < pvSize; i++) {
                    addPvView(i);
                }
                //初始化一个设置组串的默认值
                if (pvFactoryList != null) {
                    for (Pv pv : pvList) {
                        for (int i = 0; i < pvFactoryList.size(); i++) {
                            pv.getSjStrings().add(pvFactoryList.get(i).getFactotyName());
                        }
                    }
                }
                for (int i = 0; i < pvListTemp.length; i++) {
                    PvBean2 pvBean2 = pvListTemp[i];
                    View childAt = pvContainer.getChildAt(i);
                    Pv pv1 = pvList.get(i);
                    ViewHolder viewHolder = new ViewHolder();
                    viewHolder.spinnerCj = (MySpinner) childAt.findViewById(R.id.spinner_search_option_zjcs);
                    viewHolder.spinnerXh = (Spinner) childAt.findViewById(R.id.spinner_search_option_zjxh);
                    viewHolder.spinnerGl = (Spinner) childAt.findViewById(R.id.spinner_search_option_zjgl);
                    viewHolder.pvpSize = (MyEditText) childAt.findViewById(R.id.ed_zczj_size);
                    viewHolder.pvcap = (TextView) childAt.findViewById(R.id.ed_zc_capicity);
                    viewHolder.pvCreateDate = (TextView) childAt.findViewById(R.id.ed_zj_tcrq);
                    for (int j = 0; j < pv1.getSjStrings().size(); j++) {
                        if (pvBean2.getManufacturer().equals(pv1.getSjStrings().get(j))) {
                            pv1.setPvcj(pvBean2.getManufacturer());
                            pv1.setCjPosition(j);
                        }
                    }
                    //自定义不清楚数据
                    if (pv1.getCjPosition() == 0) {
                        pv1.setNoClearData(true);
                        viewHolder.spinnerType = (Spinner) childAt.findViewById(R.id.spinner_search_option_zjtype);
                        viewHolder.pvpSize = (MyEditText) childAt.findViewById(R.id.ed_zczj_size);
                        viewHolder.pvCj = (MyEditText) childAt.findViewById(R.id.ed_zj_cs);
                        viewHolder.pvXh = (MyEditText) childAt.findViewById(R.id.ed_zj_xh);
                        viewHolder.pvGl = (MyEditText) childAt.findViewById(R.id.ed_zj_gl);
                        viewHolder.pvVTemp = (MyEditText) childAt.findViewById(R.id.ed_zj_dywdxs);
                        viewHolder.pvITemp = (MyEditText) childAt.findViewById(R.id.ed_zj_dlwdxs);
                        viewHolder.pvGlTemp = (MyEditText) childAt.findViewById(R.id.ed_zj_fzglwdxs);
                        viewHolder.pvMaxGlPotintV = (MyEditText) childAt.findViewById(R.id.ed_zj_zdglddy);
                        viewHolder.pvMaxGlPintI = (MyEditText) childAt.findViewById(R.id.ed_zj_zdgldl);
                        viewHolder.firstYear = (MyEditText) childAt.findViewById(R.id.ed_zj_snsjl);
                        viewHolder.secondyear = (MyEditText) childAt.findViewById(R.id.ed_zj_znsjl);
                        viewHolder.pvDcSize = (MyEditText) childAt.findViewById(R.id.ed_zj_dcpsm);
                        viewHolder.pvKlV = (MyEditText) childAt.findViewById(R.id.ed_zj_kldy);
                        viewHolder.pvDlI = (MyEditText) childAt.findViewById(R.id.ed_zj_dldl);
                        viewHolder.pvCreateDate = (TextView) childAt.findViewById(R.id.ed_zj_tcrq);
                        viewHolder.pvcap = (TextView) childAt.findViewById(R.id.ed_zc_capicity);
                        viewHolder.pvNccEfficiency = (MyEditText) childAt.findViewById(R.id.ed_zj_bczjzhxl);
                        viewHolder.pvFillFactor = (MyEditText) childAt.findViewById(R.id.ed_zj_tcyz);
                        viewHolder.pvpSize.setText(pvBean2.getModulesNumPerString() + "");
                        viewHolder.pvCj.setText(pvBean2.getManufacturer());
                        viewHolder.pvXh.setText(pvBean2.getModuleVersion());
                        viewHolder.pvGl.setText(pvBean2.getStandardPower() + "");
                        viewHolder.pvVTemp.setText(pvBean2.getVoltageTempCoef() + "");
                        viewHolder.pvITemp.setText(pvBean2.getCurrentTempCoef() + "");
                        viewHolder.pvGlTemp.setText(pvBean2.getMaxPowerTempCoef() + "");
                        viewHolder.pvMaxGlPotintV.setText(pvBean2.getMaxPowerPointVoltage() + "");
                        viewHolder.pvMaxGlPintI.setText(pvBean2.getMaxPowerPointCurrent() + "");
                        viewHolder.firstYear.setText(pvBean2.getFirstDegradationDrate() + "");
                        viewHolder.secondyear.setText(pvBean2.getSecondDegradationDrate() + "");
                        viewHolder.pvDcSize.setText(pvBean2.getCellsNumPerModule() + "");
                        viewHolder.pvKlV.setText(pvBean2.getComponentsNominalVoltage() + "");
                        viewHolder.pvDlI.setText(pvBean2.getNominalCurrentComponent() + "");
                        viewHolder.pvCreateDate.setText(Utils.getFormatTimeYYMMDD(pvBean2.getModuleProductionDate()));
                        viewHolder.pvCreateDate.setTag(pvBean2.getModuleProductionDate());
                        viewHolder.pvcap.setText("0.0");
                        viewHolder.pvNccEfficiency.setText(pvBean2.getModuleRatio());
                        viewHolder.pvFillFactor.setText(pvBean2.getFillFactor());
                        try {
                            int typeId = Integer.valueOf(pvBean2.getModuleType());
                            if (typeId > 0 && typeId < 27) {
                                viewHolder.spinnerType.setSelection(typeId - 1, true);
                            }
                        } catch (NumberFormatException e) {
                            Log.e(TAG, "initGroupStringOptionData: " + e.getMessage() );
                        }
                    } else {//非自定义
                        if (pvFactoryList != null){
                            for (PvFactory p : pvFactoryList) {
                                if (pvBean2.getManufacturer().equals(p.getFactotyName())) {
                                    pvModelList = p.getPvModelList();
                                    if (pvModelList != null && pvModelList.size() > 0) {
                                        pv1.getXhStrings().clear();
                                        for (int j = 0; j < pvModelList.size(); j++) {
                                            pv1.getXhStrings().add(pvModelList.get(j).getModelName());
                                        }
                                    }
                                }
                            }
                        }
                        for (int j = 0; j < pv1.getXhStrings().size(); j++) {
                            if (pvBean2.getModuleVersion().equals(pv1.getXhStrings().get(j))) {
                                pv1.setPvXh(pvBean2.getModuleVersion());
                                pv1.setXhPosition(j);
                            }
                        }
                        if (pvModelList != null){
                            for (PvFactory.PvModel model : pvModelList) {
                                if (pvBean2.getModuleVersion().equals(model.getModelName())) {
                                    pvPowerList = model.getPvPowerList();
                                    if (pvPowerList != null && pvPowerList.size() > 0) {
                                        pv1.getGlStrings().clear();
                                        for (PvFactory.PvPower power : pvPowerList) {
                                            pv1.getGlStrings().add(power.getPower());
                                        }
                                    }
                                }
                            }
                        }
                        for (int j = 0; j < pv1.getGlStrings().size(); j++) {
                            if ((pvBean2.getStandardPower() + "").equals(pv1.getGlStrings().get(j))) {
                                pv1.setPvGl(pvBean2.getStandardPower() + "");
                                pv1.setGlPosition(j);
                            }
                        }
                        viewHolder.pvpSize.setText(pvBean2.getModulesNumPerString() + "");
                        viewHolder.pvCreateDate.setText(Utils.getFormatTimeYYMMDD(pvBean2.getModuleProductionDate()));
                        try {
                            pv1.setSize(Integer.valueOf(pvBean2.getModulesNumPerString()));
                        } catch (NumberFormatException e) {
                            Log.e(TAG, "initGroupStringOptionData: " + e.getMessage());
                        }
                        pv1.setModuleProductionDate(pvBean2.getModuleProductionDate());
                        viewHolder.spinnerCj.setSelection(pv1.getCjPosition(), true);
                        pv1.setPvXh(pvBean2.getModuleVersion());
                        pv1.setPvcj(pvBean2.getManufacturer());
                        pv1.setPvGl(pvBean2.getStandardPower() + "");
                    }
                }
            }
        }
    }

    @Override
    public void getHistorySignalData(List<DevHistorySignalData> dataList) {

    }

    @Override
    public void getgetHistoryData(List<SignalData> dataList) {

    }

    @Override
    public void getOptHistoryData(List<List<SignalData>> lists) {

    }

    class ViewHolder {
        MySpinner spinnerCj;
        Spinner spinnerXh, spinnerGl, spinnerType;
        MyEditText pvpSize;
        TextView pvName;
        ImageView closeOropenImage;
        LinearLayout childItemView;
        MyEditText pvCj, pvXh, pvGl, pvType, pvVTemp, pvITemp, pvGlTemp, pvMaxGlPotintV, pvMaxGlPintI, firstYear, secondyear, pvDcSize,
                pvKlV, pvDlI, pvFillFactor, pvNccEfficiency;
        TextView pvCreateDate, pvcap;
        RelativeLayout zdyType, noZdytype;
    }

    /**
     * 获取组串类型
     *
     * @param id
     * @return
     */
    public String getModuleTypeNameById(String id) {
        if (id == null) {
            return "";
        }
        String moduleTypeName = getString(R.string.unknown_device_type);
        switch (id) {
            case Constant.ModuleType.POLYCRYSTAL:
                moduleTypeName = getString(R.string.polycrystal);
                break;
            case Constant.ModuleType.MONOCRYSTAL:
                moduleTypeName = getString(R.string.monocrystal);
                break;
            case Constant.ModuleType.BLACK_MONOCRYSTAL:
                moduleTypeName = getString(R.string.black_monocrystal);
                break;
            case Constant.ModuleType.BLACK_POLYCRYSTAL:
                moduleTypeName = getString(R.string.black_polycrystal);
                break;
            case Constant.ModuleType.DOULE_GLASS_MOUDLE:
                moduleTypeName = getString(R.string.doule_glass_module);
                break;
            case Constant.ModuleType.DOULE_GLASS_POLYCRYSTAL:
                moduleTypeName = getString(R.string.double_glass_polycrystal);
                break;
            case Constant.ModuleType.DOULE_GLASS_MONOCRYSTAL:
                moduleTypeName = getString(R.string.double_glass_monocrystal);
                break;
            case Constant.ModuleType.WHITE_DOULE_GLASS_MOUDLE:
                moduleTypeName = getString(R.string.white_doule_glass_moudle);
                break;
            case Constant.ModuleType.TRANSPARENTT_DOULE_GLASS_MOUDLE:
                moduleTypeName = getString(R.string.transparent_doule_galss_moudle);
                break;
            case Constant.ModuleType.DOULE_GLASS_1500_MOUDLE:
                moduleTypeName = getString(R.string.doule_galss_1500_module);
                break;
            case Constant.ModuleType.DOULE_GLASS_1501_MOUDLE:
                moduleTypeName = getString(R.string.doule_galss_1501_module);
                break;
            case Constant.ModuleType.DOULE_GLASS_1502_MOUDLE:
                moduleTypeName = getString(R.string.doule_galss_1502_module);
                break;
            case Constant.ModuleType.DOULE_GLASS_1503_MOUDLE:
                moduleTypeName = getString(R.string.doule_galss_1503_module);
                break;
            case Constant.ModuleType.DOULE_GLASS_1504_MOUDLE:
                moduleTypeName = getString(R.string.doule_galss_1504_module);
                break;
            case Constant.ModuleType.DOULE_GLASS_1505_MOUDLE:
                moduleTypeName = getString(R.string.doule_galss_1505_module);
                break;
            case Constant.ModuleType.DOULE_GLASS_1506_MOUDLE:
                moduleTypeName = getString(R.string.doule_galss_1506_module);
                break;
            case Constant.ModuleType.DOULE_GLASS_1507_MOUDLE:
                moduleTypeName = getString(R.string.doule_galss_1507_module);
                break;
            case Constant.ModuleType.DOULE_GLASS_1508_MOUDLE:
                moduleTypeName = getString(R.string.doule_galss_1508_module);
                break;
            case Constant.ModuleType.DOULE_GLASS_1509_MOUDLE:
                moduleTypeName = getString(R.string.doule_galss_1509_module);
                break;
            case Constant.ModuleType.JAP_VER_MONOCRYSTAL:
                moduleTypeName = getString(R.string.jap_ver_monocrystal);
                break;
            case Constant.ModuleType.FOUR_WIRE_MONOCRYSTAL:
                moduleTypeName = getString(R.string.four_wire_monocrystal);
                break;
            case Constant.ModuleType.MONOCRYSTAL_FOUR_GRID_60:
                moduleTypeName = getString(R.string.monocrystal_four_grid_60);
                break;
            case Constant.ModuleType.MONOCRYSTAL_FOUR_GRID_72:
                moduleTypeName = getString(R.string.monocrystal_four_grid_72);
                break;
            case Constant.ModuleType.POLYCRYSTAL_FOUR_GRID_60:
                moduleTypeName = getString(R.string.polycrystal_four_grid_60);
                break;
            case Constant.ModuleType.POLYCRYSTAL_FOUR_GRID_72:
                moduleTypeName = getString(R.string.polycrystal_four_grid_72);
                break;
            case Constant.ModuleType.EFFICIENT_MOUDLE:
                moduleTypeName = getString(R.string.efficient_moudle);
                break;
            default:
                break;
        }
        return moduleTypeName;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (groupStringSize != null) {
                groupStringSize.requestFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
