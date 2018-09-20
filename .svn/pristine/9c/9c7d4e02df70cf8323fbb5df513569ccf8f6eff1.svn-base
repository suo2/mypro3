package com.huawei.solarsafe.view.maintaince.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.base.MyStationPickerActivity;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.FillterMsg;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.MyStationBean;
import com.huawei.solarsafe.bean.device.DevHistorySignalData;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.bean.device.SignalData;
import com.huawei.solarsafe.database.FillterMsgDao;
import com.huawei.solarsafe.presenter.devicemanagement.DevManagementPresenter;
import com.huawei.solarsafe.utils.MySpinner;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DatePiker.DatePikerDialogForAllTime;
import com.huawei.solarsafe.utils.customview.EditeDialog;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.devicemanagement.IDevManagementView;
import com.huawei.solarsafe.view.maintaince.operation.MaintenanceActivityNew;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RealTimeAlarmFillterActivity extends BaseActivity implements View.OnClickListener, IDevManagementView {
    public static final String TAG = "RealTimeAlarmFillterActivity";
    private Button retset, save, sure;
    private EditText devName, alarmName;
    private MySpinner spinnerDevType, spinnerAlarmLevel, spinnerAlarmStaus;
    private TextView date1, date2, staionChoose;
    private long startTime, endTime;
    private String stationCodes = "";
    String type;
    FillterMsgDao fillterMsgDao;
    private DevManagementPresenter devManagementPresenter;
    private StringBuffer stringExtra;
    public Map<Integer, String> devTypeMap = new HashMap<>();
    MyStationBean root;
    //是否是第一次进入
    private boolean isFirst = true;
    private DatePikerDialogForAllTime dialogForAllTime1, dialogForAllTime2;
    private FillterMsg fillterMsg;
    private String[] strings = new String[7];
    private LocalBroadcastManager lbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lbm = LocalBroadcastManager.getInstance(MyApplication.getContext());
        fillterMsgDao = new FillterMsgDao(this);
        Intent intent = getIntent();
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                type = intent.getStringExtra("TYPE");
                fillterMsg = (FillterMsg) intent.getSerializableExtra("fillter");
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        } else {
            type = "";
            fillterMsg = null;
        }
        devManagementPresenter = new DevManagementPresenter();
        devManagementPresenter.onViewAttached(this);
        devManagementPresenter.doRequestDevType(new HashMap<String, String>());
        if(fillterMsg != null){
            if(!TextUtils.isEmpty(fillterMsg.getStationName())){
                staionChoose.setText(fillterMsg.getStationName());
            }
            if(!TextUtils.isEmpty(fillterMsg.getStationCodes())){
                stationCodes = fillterMsg.getStationCodes();
            }
            if(!TextUtils.isEmpty(fillterMsg.getDevName())){
                devName.setText(fillterMsg.getDevName());
            }
            if(!TextUtils.isEmpty(fillterMsg.getDevType())){
                for (Map.Entry entry : devTypeMap.entrySet()) {
                    if(fillterMsg.getDevType().equals(entry.getKey() + "")){
                        for (int i = 0; i < strings.length; i++) {
                            if(entry.getValue().equals(strings[i])){
                                spinnerDevType.setSelection(i);
                            }
                        }
                    }
                    break;
                }
            }
            if(!TextUtils.isEmpty(fillterMsg.getAlarmLevel())){
                spinnerAlarmLevel.setSelection(Integer.valueOf(fillterMsg.getAlarmLevel()));
            }
            if(!TextUtils.isEmpty(fillterMsg.getAlarmStatus())){
                spinnerAlarmStaus.setSelection(Integer.valueOf(fillterMsg.getAlarmStatus()));
            }
            if(!TextUtils.isEmpty(fillterMsg.getAlarmName())){
                alarmName.setText(fillterMsg.getAlarmName());
            }
            if(!TextUtils.isEmpty(fillterMsg.getStartTime()) && !"0".equals(fillterMsg.getStartTime())){
                date1.setText(Utils.getFormatTimeYYMMDDHHmmss2(Long.valueOf(fillterMsg.getStartTime())));
                startTime = Long.valueOf(fillterMsg.getStartTime());
            }
            if(!TextUtils.isEmpty(fillterMsg.getEndTime()) && !"0".equals(fillterMsg.getEndTime())){
                date2.setText(Utils.getFormatTimeYYMMDDHHmmss2(Long.valueOf(fillterMsg.getEndTime())));
                endTime = Long.valueOf(fillterMsg.getEndTime());
            }
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_station_filter;
    }

    @Override
    protected void initView() {
        tv_title.setText(getString(R.string.create_new_one));
        tv_left.setOnClickListener(this);
        retset = (Button) findViewById(R.id.reset);
        save = (Button) findViewById(R.id.save);
        sure = (Button) findViewById(R.id.sure);
        staionChoose = (TextView) findViewById(R.id.key_words);
        staionChoose.setOnClickListener(this);
        devName = (EditText) findViewById(R.id.device_name_keys);
        alarmName = (EditText) findViewById(R.id.alarm_name_keys);
        spinnerAlarmLevel = (MySpinner) findViewById(R.id.spinner_search_option_alarmlevel);
        spinnerDevType = (MySpinner) findViewById(R.id.spinner_search_option_devtype);
        strings[0] = getString(R.string.all_of);
        strings[1] = getString(R.string.String_inverter);
        strings[2] = getString(R.string.centralized_inverter);
        strings[3] = getString(R.string.dcjs_str);
        strings[4] = getString(R.string.packaged_substation);
        strings[5] = getString(R.string.meter_stations);
        strings[6] = getString(R.string.household_inverter);
        ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<String>(this, R.layout.report_spinner_item,
                strings);
        spinnerDevType.setAdapter(spinnerAdapter1);

        spinnerAlarmStaus = (MySpinner) findViewById(R.id.spinner_search_option_alarmstatus);
        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<String>(this, R.layout.report_spinner_item,
                new String[]{getString(R.string.all_of), getString(R.string.serious), getString(R.string.important), getString(R.string.subordinate), getString(R.string.suggestive)});
        ArrayAdapter<String> spinnerAdapter3 = new ArrayAdapter<String>(this, R.layout.report_spinner_item,
                new String[]{getString(R.string.all_of), getString(R.string.activation), getString(R.string.pvmodule_alarm_sured), getString(R.string.in_hand), getString(R.string.handled), getString(R.string.cleared), getString(R.string.restored)});
        spinnerAlarmLevel.setAdapter(spinnerAdapter2);
        spinnerAlarmStaus.setAdapter(spinnerAdapter3);
        devTypeMap = DevTypeConstant.getDevTypeMap(this);
        date1 = (TextView) findViewById(R.id.tv_date1);
        date2 = (TextView) findViewById(R.id.tv_date2);
        stringExtra = new StringBuffer();
        date1.setOnClickListener(this);
        date2.setOnClickListener(this);
        staionChoose.setOnClickListener(this);
        retset.setOnClickListener(this);
        save.setOnClickListener(this);
        sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                Intent intent = new Intent(this, CustomFillterActivity.class);
                intent.putExtra("TYPE", type);
                startActivity(intent);
                finish();
                break;
            case R.id.reset:
                reset();
                break;
            case R.id.save:
                String name = "";
                //将之前的删除
                if(fillterMsg != null){
                    name = fillterMsg.getFillterName();
                }
                final EditeDialog dialog = new EditeDialog(this).builder().setTitle(getString(R.string.name_for_condition))
                        .setNegativeButton(getString(R.string.cancel), true, null).setPositiveButton(getString(R.string.determine), new EditeDialog.OnNameCallback() {
                            @Override
                            public void nameCallbake(String name) {
                                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                                getStartTimeAndEndTime();
                                if (endTime != 0 && startTime > endTime) {
                                    ToastUtil.showMessage(getString(R.string.please_choice_time_true));
                                    return;
                                }
                                if(fillterMsg != null){
                                    fillterMsgDao.deleteMsgById(fillterMsg.getId());
                                }
                                Intent intent = new Intent();
                                FillterMsg fillterMsg = new FillterMsg();
                                //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                                fillterMsg.setUserId(GlobalConstants.userId + "");
                                fillterMsg.setFillterName(name);
                                fillterMsg.setStationName(staionChoose.getText().toString().trim());
                                fillterMsg.setStationCodes(stationCodes);
                                fillterMsg.setAlarmName(alarmName.getText().toString().trim());
                                fillterMsg.setAlarmStatus(spinnerAlarmStaus.getSelectedItemId() + "");
                                fillterMsg.setAlarmLevel(spinnerAlarmLevel.getSelectedItemId() + "");
                                fillterMsg.setDevName(devName.getText().toString().trim());
                                if (spinnerDevType.getSelectedItemId() == 0) {
                                    fillterMsg.setDevType("");
                                } else {
                                    if (spinnerDevType != null) {
                                        if (spinnerDevType.getSelectedItem() != null) {
                                            for (Map.Entry entry : devTypeMap.entrySet()) {
                                                if (spinnerDevType.getSelectedItem().toString().equals(entry.getValue())) {
                                                    fillterMsg.setDevType(entry.getKey() + "");
                                                }
                                            }
                                        }
                                    }
                                }
                                if (startTime!=0){
                                    fillterMsg.setStartTime(startTime + "");
                                }
                                if (endTime!=0){
                                    fillterMsg.setEndTime(endTime + "");
                                }
                                fillterMsg.setType(type);
                                fillterMsgDao.insert(fillterMsg);
                                intent.putExtra("fillter", fillterMsg);
                                intent.setAction(MaintenanceActivityNew.ACTION_FILLTER_MSG);
                                lbm.sendBroadcast(intent);
                                finish();
                            }
                        });
                dialog.setName(name);
                dialog.show();
                break;
            case R.id.sure:
                getStartTimeAndEndTime();
                if (endTime != 0 && startTime > endTime) {
                    ToastUtil.showMessage(getString(R.string.please_choice_time_true));
                    return;
                }
                doIntentMonitorActivity();
                break;
            case R.id.tv_date1:
                if (dialogForAllTime1 == null) {
                    dialogForAllTime1 = new DatePikerDialogForAllTime(RealTimeAlarmFillterActivity.this, date1);
                }
                dialogForAllTime1.showDialog();
                break;
            case R.id.tv_date2:
                if (dialogForAllTime2 == null) {
                    dialogForAllTime2 = new DatePikerDialogForAllTime(RealTimeAlarmFillterActivity.this, date2);
                }
                dialogForAllTime2.showDialog();
                break;
            case R.id.key_words:
                Intent intent1 = new Intent(this, MyStationPickerActivity.class);
                intent1.putExtra("root", root);
                intent1.putExtra("isFirst", isFirst);
                startActivityForResult(intent1, 100);
                break;
            default:
                break;
        }
    }

    private void doIntentMonitorActivity() {
        Intent intent = new Intent();
        FillterMsg fillterMsg = new FillterMsg();
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        fillterMsg.setUserId(GlobalConstants.userId + "");
        fillterMsg.setStationName(staionChoose.getText().toString().trim());
        fillterMsg.setStationCodes(stationCodes);
        fillterMsg.setAlarmName(alarmName.getText().toString().trim());
        fillterMsg.setAlarmStatus(spinnerAlarmStaus.getSelectedItemId() + "");
        fillterMsg.setAlarmLevel(spinnerAlarmLevel.getSelectedItemId() + "");
        fillterMsg.setDevName(devName.getText().toString().trim());
        if(spinnerDevType != null){
            if (spinnerDevType.getSelectedItemId() == 0) {
                fillterMsg.setDevType("");
            }else {
                    if (spinnerDevType.getSelectedItem() != null) {
                        for (Map.Entry entry : devTypeMap.entrySet()) {
                            if (spinnerDevType.getSelectedItem().toString().equals(entry.getValue())) {
                                fillterMsg.setDevType(entry.getKey() + "");
                            }
                        }
                    }
            }
        }

        getStartTimeAndEndTime();
        if (startTime!=0){
            fillterMsg.setStartTime(startTime + "");
        }
        if (endTime!=0){
            fillterMsg.setEndTime(endTime + "");
        }
        fillterMsg.setType(type);
        if(this.fillterMsg != null){
            fillterMsg.setFillterName(this.fillterMsg.getFillterName());
            fillterMsgDao.deleteMsgById(this.fillterMsg.getId());
            fillterMsgDao.insert(fillterMsg);
        }
        intent.putExtra("fillter", fillterMsg);
        intent.setAction(MaintenanceActivityNew.ACTION_FILLTER_MSG);
        lbm.sendBroadcast(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (data != null) {
                String names = "";
                if (stringExtra.length() != 0) {
                    stringExtra.replace(0, stringExtra.length(), "");
                }
                ArrayList<MyStationBean> myStationBeanArrayList = new ArrayList<>();
                try{

                    MyStationBean root = (MyStationBean) data.getSerializableExtra("root");
                    if (root == null) {
                        return;
                    }
                    myStationBeanArrayList = MyStationPickerActivity.collectCheckedStations(root, myStationBeanArrayList);
                    if (myStationBeanArrayList != null) {
                        for (MyStationBean s : myStationBeanArrayList) {
                            if ("STATION".equals(s.getModel())) {
                                if (s.isChecked()) {
                                    stationCodes = stationCodes + s.getId() + ",";
                                    stringExtra.append(s.getName() + ",");
                                }
                            }
                        }
                        if (stringExtra.length() != 0) {
                            names = stringExtra.toString().substring(0, stringExtra.length() - 1);
                        }
                        staionChoose.setText(names);
                    }
                }catch (Exception e){
                    Log.e(TAG, "onActivityResult: " + e.getMessage());
                }
            }
            isFirst = false;
        }
    }

    public void reset() {
        staionChoose.setText("");
        devName.setText("");
        spinnerDevType.setSelection(0);
        spinnerAlarmLevel.setSelection(0);
        spinnerAlarmStaus.setSelection(0);
        alarmName.setText("");
        startTime = 0;
        endTime = 0;
        stationCodes = "";
        date1.setText(getString(R.string.select_start_time));
        date2.setText(getString(R.string.select_end_time));


    }

    @Override
    public void requestData() {

    }

    @Override
    public void getData(BaseEntity baseEntity) {}

    @Override
    public void getHistorySignalData(List<DevHistorySignalData> dataList) {

    }

    @Override
    public void getgetHistoryData(List<SignalData> dataList) {

    }

    @Override
    public void getOptHistoryData(List<List<SignalData>> lists) {

    }

    private void getStartTimeAndEndTime() {
        if (date1.getTag() != null) {
            startTime = (long) date1.getTag();
        }
        if (date2.getTag() != null) {
            endTime = (long) date2.getTag();
        }
    }
}
