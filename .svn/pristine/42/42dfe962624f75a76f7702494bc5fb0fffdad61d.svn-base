package com.huawei.solarsafe.view.maintaince.main;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.huawei.solarsafe.bean.device.DevTypeListInfo;
import com.huawei.solarsafe.bean.device.SignalData;
import com.huawei.solarsafe.database.FillterMsgDao;
import com.huawei.solarsafe.presenter.devicemanagement.DevManagementPresenter;
import com.huawei.solarsafe.utils.MySpinner;
import com.huawei.solarsafe.utils.TimeUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.EditeDialog;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.CustomViews.pickerview.TimePickerView;
import com.huawei.solarsafe.view.devicemanagement.IDevManagementView;
import com.huawei.solarsafe.view.maintaince.operation.MaintenanceActivityNew;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DevAlarmFillterActivity extends BaseActivity implements View.OnClickListener, IDevManagementView {
    public static final String TAG = "DevAlarmFillterActivity";
    private Button retset, save, sure;
    private EditText devName, alarmName;
    private MySpinner spinnerDevType, spinnerAlarmLevel, spinnerAlarmStaus, spinnerAlarmType;
    private TextView date1, date2, staionChoose;
    private long startTime, endTime;
    private String stationId;
    private StringBuffer stringExtra;
    private StringBuffer stationCodes;
    private LinearLayout llCotent;
    String type;
    FillterMsgDao fillterMsgDao;
    private DevManagementPresenter devManagementPresenter;
    private List<DevTypeListInfo.DevType> devTypes;
    public Map<Integer, String> devTypeMap = new HashMap<>();
    private static String[] yearContent = null;
    private static String[] monthContent = null;
    private static String[] dayContent = null;
    private static String[] hourContent = null;
    private static String[] minuteContent = null;
    private static String[] secondContent = null;
    private int minYear = 2000;  //最小年份
    private long time;
    MyStationBean root;
    //是否是第一次进入
    private boolean isFirst = true;
    private TimePickerView timePickerView;
    private FillterMsg initFillterMsg;
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
            	initFillterMsg= (FillterMsg) intent.getSerializableExtra("fillter");
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        } else {
            type = "";
        }
        devManagementPresenter = new DevManagementPresenter();
        devManagementPresenter.onViewAttached(this);
        devManagementPresenter.doRequestDevType(new HashMap<String, String>());
        initContent();
    }

    public void initContent() {
        yearContent = new String[70];
        for (int i = 0; i < 70; i++)
            yearContent[i] = String.valueOf(i + minYear);

        monthContent = new String[12];
        for (int i = 0; i < 12; i++) {
            monthContent[i] = String.valueOf(i + 1);
            if (monthContent[i].length() < 2) {
                monthContent[i] = "0" + monthContent[i];
            }
        }

        dayContent = new String[31];
        for (int i = 0; i < 31; i++) {
            dayContent[i] = String.valueOf(i + 1);
            if (dayContent[i].length() < 2) {
                dayContent[i] = "0" + dayContent[i];
            }
        }
        hourContent = new String[24];
        for (int i = 0; i < 24; i++) {
            hourContent[i] = String.valueOf(i);
            if (hourContent[i].length() < 2) {
                hourContent[i] = "0" + hourContent[i];
            }
        }

        minuteContent = new String[60];
        for (int i = 0; i < 60; i++) {
            minuteContent[i] = String.valueOf(i);
            if (minuteContent[i].length() < 2) {
                minuteContent[i] = "0" + minuteContent[i];
            }
        }
        secondContent = new String[60];
        for (int i = 0; i < 60; i++) {
            secondContent[i] = String.valueOf(i);
            if (secondContent[i].length() < 2) {
                secondContent[i] = "0" + secondContent[i];
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dev_alarm_fillter;
    }

    @Override
    protected void initView() {
        llCotent= (LinearLayout) findViewById(R.id.llCotent);
        tv_title.setText(getString(R.string.create_new_one));
        tv_left.setOnClickListener(this);
        retset = (Button) findViewById(R.id.reset);
        save = (Button) findViewById(R.id.save);
        sure = (Button) findViewById(R.id.sure);
        stringExtra = new StringBuffer();
        stationCodes = new StringBuffer();
        staionChoose = (TextView) findViewById(R.id.key_words);
        staionChoose.setOnClickListener(this);
        devName = (EditText) findViewById(R.id.device_name_keys);
        alarmName = (EditText) findViewById(R.id.alarm_name_keys);
        spinnerAlarmLevel = (MySpinner) findViewById(R.id.spinner_search_option_alarmlevel);
        spinnerDevType = (MySpinner) findViewById(R.id.spinner_search_option_devtype);
        spinnerAlarmStaus = (MySpinner) findViewById(R.id.spinner_search_option_alarmstatus);
        spinnerAlarmType = (MySpinner) findViewById(R.id.spinner_search_option_alarmstype);
        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<String>(this, R.layout.report_spinner_item,
                new String[]{getString(R.string.all_of), getString(R.string.serious), getString(R.string.important), getString(R.string.subordinate), getString(R.string.suggestive)});
        ArrayAdapter<String> spinnerAdapter3 = new ArrayAdapter<String>(this, R.layout.report_spinner_item,
                new String[]{getString(R.string.all_of), getString(R.string.activation), getString(R.string.pvmodule_alarm_sured), getString(R.string.in_hand), getString(R.string.handled), getString(R.string.cleared), getString(R.string.restored)});
        ArrayAdapter<String> spinnerAdapter4 = new ArrayAdapter<String>(this, R.layout.report_spinner_item,
                new String[]{getString(R.string.all_of), getString(R.string.displacement_signal), getString(R.string.abnormal_alarm), getString(R.string.protection_event),
                        getString(R.string.communication_state), getString(R.string.informing_information)});
        spinnerAlarmLevel.setAdapter(spinnerAdapter2);
        spinnerAlarmStaus.setAdapter(spinnerAdapter3);
        spinnerAlarmType.setAdapter(spinnerAdapter4);
        devTypeMap = DevTypeConstant.getDevTypeMap(this);

        date1 = (TextView) findViewById(R.id.tv_date1);
        date2 = (TextView) findViewById(R.id.tv_date2);
        date1.setOnClickListener(this);
        date2.setOnClickListener(this);
        staionChoose.setOnClickListener(this);
        retset.setOnClickListener(this);
        save.setOnClickListener(this);
        sure.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        llCotent.setFocusable(true);
        llCotent.setFocusableInTouchMode(true);
        llCotent.requestFocus();

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
                if(initFillterMsg != null){
                    name = initFillterMsg.getFillterName();
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
                                if(initFillterMsg != null){
                                    fillterMsgDao.deleteMsgById(initFillterMsg.getId());
                                }
                                Intent intent = new Intent();
                                FillterMsg fillterMsg = new FillterMsg();
                                //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                                fillterMsg.setUserId(GlobalConstants.userId + "");
                                fillterMsg.setFillterName(name);
                                fillterMsg.setStationName(staionChoose.getText().toString().trim());
                                fillterMsg.setStationCodes(stationId);
                                fillterMsg.setAlarmName(alarmName.getText().toString().trim());
                                fillterMsg.setAlarmStatus(spinnerAlarmStaus.getSelectedItemId() + "");
                                fillterMsg.setAlarmLevel(spinnerAlarmLevel.getSelectedItemId() + "");
                                fillterMsg.setAlarmType(spinnerAlarmType.getSelectedItemId() + "");
                                fillterMsg.setDevName(devName.getText().toString().trim());
                                if (spinnerAlarmType!=null&&spinnerDevType.getSelectedItemId() == 0) {
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

                showTimePickerView(date1);
                break;
            case R.id.tv_date2:

                showTimePickerView(date2);
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
        fillterMsg.setStationCodes(stationId);
        fillterMsg.setAlarmName(alarmName.getText().toString().trim());
        fillterMsg.setAlarmStatus(spinnerAlarmStaus.getSelectedItemId() + "");
        fillterMsg.setAlarmLevel(spinnerAlarmLevel.getSelectedItemId() + "");
        fillterMsg.setAlarmType(spinnerAlarmType.getSelectedItemId() + "");
        fillterMsg.setDevName(devName.getText().toString().trim());
        if (spinnerDevType!=null&&spinnerDevType.getSelectedItemId() == 0) {
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
        getStartTimeAndEndTime();
        if (startTime!=0){
            fillterMsg.setStartTime(startTime + "");
        }
        if (endTime!=0){
            fillterMsg.setEndTime(endTime + "");
        }
        fillterMsg.setType(type);
        if(initFillterMsg != null){
            fillterMsg.setFillterName(initFillterMsg.getFillterName());
            fillterMsgDao.deleteMsgById(initFillterMsg.getId());
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
            if (!TextUtils.isEmpty(stringExtra)) {
                stringExtra.replace(0, stringExtra.length(), "");
            }
            if (!TextUtils.isEmpty(stationCodes)) {
                stationCodes.replace(0, stationCodes.length(), "");
            }
            if (data != null) {
                try{
                    ArrayList<MyStationBean> myStationBeanArrayList = new ArrayList<>();
                    MyStationBean root = (MyStationBean) data.getSerializableExtra("root");
                    if (root == null) {
                        return;
                    }
                    myStationBeanArrayList = MyStationPickerActivity.collectCheckedStations(root, myStationBeanArrayList);
                    if (myStationBeanArrayList != null) {
                        for (MyStationBean s : myStationBeanArrayList) {
                            if ("STATION".equals(s.getModel())) {
                                if (s.isChecked()) {
                                    if (stationCodes!=null)
                                    stationCodes.append(s.getId() + ",");
                                    if (stringExtra!=null)
                                    stringExtra.append(s.getName() + ",");
                                }
                            }
                        }
                        if (stationCodes != null && stationCodes.length() > 1) {
                            stationId = stationCodes.toString().substring(0, stationCodes.length() - 1);
                        }
                        if (stringExtra != null && stringExtra.length() > 1) {
                            staionChoose.setText(stringExtra.toString().substring(0, stringExtra.length() - 1));
                            staionChoose.setTextColor(Color.parseColor("#8C8C8C"));
                        }
                        isFirst = false;
                    }
                } catch (Exception e){
                    Log.e(TAG, "onActivityResult: " + e.getMessage());
                }
            }
        }
    }

    public void reset() {
        staionChoose.setText(getResources().getString(R.string.please_station_choice));
        staionChoose.setTextColor(Color.parseColor("#D3D3D3"));
        devName.setText("");
        if (spinnerDevType!=null)
        spinnerDevType.setSelection(0);
        spinnerAlarmLevel.setSelection(0);
        spinnerAlarmStaus.setSelection(0);
        spinnerAlarmType.setSelection(0);
        alarmName.setText("");
        date1.setTag(null);
        date2.setTag(null);
        date1.setText(getString(R.string.select_start_time));
        date2.setText(getString(R.string.select_end_time));
        date1.setTextColor(Color.parseColor("#D3D3D3"));
        date2.setTextColor(Color.parseColor("#D3D3D3"));

    }

    @Override
    public void requestData() {

    }

    @Override
    public void getData(BaseEntity baseEntity) {
        if (baseEntity == null)  return;
        if (baseEntity instanceof DevTypeListInfo) {
            DevTypeListInfo devTypeListInfo = (DevTypeListInfo) baseEntity;
            if (devTypeListInfo.getDevTypes() != null) {
                devTypes = devTypeListInfo.getDevTypes();
                String[] strings = new String[devTypes.size() + 1];
                strings[0] = getString(R.string.all_of);
                for (int i = 0; i < devTypes.size(); i++) {
                    String value = devTypeMap.get(devTypes.get(i).getId());
                    if(value == null){
                        strings[i + 1] = "";
                    }else{
                        strings[i + 1]  =value;
                    }
                }
                ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<String>(this, R.layout.report_spinner_item,
                        strings);
                if (spinnerDevType!=null)
                spinnerDevType.setAdapter(spinnerAdapter1);

                if (initFillterMsg!=null){
                    initViews();
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

    private void getStartTimeAndEndTime() {
        if (date1.getTag() != null) {
            startTime = Long.valueOf(date1.getTag().toString());
        }
        if (date2.getTag() != null) {
            endTime = Long.valueOf(date2.getTag().toString());
        }
    }

    /**
     * 显示时间选择器
     * @param tv 回调选择时间的文本框
     */
    private void showTimePickerView(final TextView tv){
        

        timePickerView=new TimePickerView.Builder(DevAlarmFillterActivity.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String strTime=sdf.format(date);

                tv.setText(strTime);
                tv.setTextColor(Color.parseColor("#8C8C8C"));
                tv.setTag(getSelectResult(strTime));
            }
        })
                .setTitleText(getResources().getString(R.string.choice_time))
                .setTitleColor(Color.BLACK)
                .setCancelColor(Color.parseColor("#FF9933"))
                .setSubmitColor(Color.parseColor("#FF9933"))
                .setTextColorCenter(Color.parseColor("#FF9933"))
                .setSubmitText(getResources().getString(R.string.confirm))
                .setCancelText(getResources().getString(R.string.cancel))
                .setOutSideCancelable(true)
                .isCyclic(true)
                .setLabel("","","","","","")
                .build();
        timePickerView.show();
    }

    public long getSelectResult(String timeString) {
        if (!TextUtils.isEmpty(timeString)) {
            time = Utils.getMillisFromYYMMDDHHmmss(timeString);
        }
        return time;
    }

    private void initViews(){
        stationId=initFillterMsg.getStationCodes();
        staionChoose.setText(initFillterMsg.getStationName());
        staionChoose.setTextColor(Color.parseColor("#8C8C8C"));
        isFirst = false;
        alarmName.setText(initFillterMsg.getAlarmName());
        spinnerAlarmStaus.setSelection(Integer.valueOf(initFillterMsg.getAlarmStatus()));
        spinnerAlarmLevel.setSelection(Integer.valueOf(initFillterMsg.getAlarmLevel()));
        spinnerAlarmType.setSelection(Integer.valueOf(initFillterMsg.getAlarmType()));
        devName.setText(initFillterMsg.getDevName());
        for (int i=0;i<devTypes.size();i++){
            if (initFillterMsg.getDevType().equals(String.valueOf(devTypes.get(i).getId()))){
                if (spinnerDevType!=null)
                spinnerDevType.setSelection(i+1);
                break;
            }
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        if (initFillterMsg.getStartTime()!=null){
            date1.setTag(initFillterMsg.getStartTime());
            date1.setText(TimeUtils.millis2String(Long.valueOf(initFillterMsg.getStartTime()),sdf));
            date1.setTextColor(Color.parseColor("#8C8C8C"));
        }
        if (initFillterMsg.getEndTime()!=null){
            date2.setTag(initFillterMsg.getEndTime());
            date2.setText(TimeUtils.millis2String(Long.valueOf(initFillterMsg.getEndTime()),sdf));
            date2.setTextColor(Color.parseColor("#8C8C8C"));
        }

    }

}
