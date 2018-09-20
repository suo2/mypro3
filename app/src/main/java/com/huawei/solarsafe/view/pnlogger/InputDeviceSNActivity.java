package com.huawei.solarsafe.view.pnlogger;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.devicemanagement.DevSwitchActivity;
import com.huawei.solarsafe.view.personmanagement.CreatePersonActivity;
import com.huawei.solarsafe.view.personmanagement.PersonDetailActivity;
import com.huawei.solarsafe.view.stationmanagement.NewEquipmentActivity;
import com.huawei.solarsafe.view.stationmanagement.changestationinfo.ChangeStationInfoActivity;

/**
 * <pre>
 *     author: Tzy
 *     time  : 2018/5/7
 *     desc  : 手动输入SN号界面
 * </pre>
 */
public class InputDeviceSNActivity extends BaseActivity implements View.OnClickListener {

    private android.widget.EditText etSN;//SN号输入框
    private android.widget.Button btnConfirm;//确定按钮

    //进入扫码界面的模块
    private final String SCAN_MODULE="scanModule";
    private int scanModule=0;//默认
    private final int CREATE_STATION_MODULE=1;//新建电站
    private final int CHANGE_STATION_MODULE=2;//修改电站
    private final int ONE_KEY_STATION_MODULE=3;//一键开站
    private final int CREATE_PERSON_MODULE=4;//新建业主
    private final int SELECT_PNT_MODULE=5;//选择数采
    private final int DEVICE_REPLACE_MODULE=6;//设备替换
    private final int PERSON_DETAIL_MODULE=7;//用户详情

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            scanModule=getIntent().getIntExtra("scanModule",0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_input_device_sn;
    }

    @Override
    protected void initView() {
        this.btnConfirm = (Button) findViewById(R.id.btnConfirm);
        this.etSN = (EditText) findViewById(R.id.etSN);

        tv_title.setText(getResources().getString(R.string.input_device_SN));

        btnConfirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnConfirm://确认按钮

                String strSN=etSN.getText().toString().trim();
                if (TextUtils.isEmpty(strSN)){
                    ToastUtil.showMessage(getResources().getString(R.string.please_input_dev_esn));
                }else{
                    //跳转新建电站界面
                    Intent intent=new Intent();
                    switch (scanModule){
                        case CREATE_STATION_MODULE:
                            intent.setClass(this, com.huawei.solarsafe.view.stationmanagement.CreateStationActivity.class);
                            break;
                        case CHANGE_STATION_MODULE:
                            intent.setClass(this, ChangeStationInfoActivity.class);
                            break;
                        case ONE_KEY_STATION_MODULE:
                            //跳转新增设备反馈界面
                            intent.setClass(this,NewEquipmentActivity.class);
                            break;
                        case CREATE_PERSON_MODULE:
                            //新建业主界面
                            intent.setClass(this, CreatePersonActivity.class);
                            break;
                        case SELECT_PNT_MODULE:
                            intent.setClass(this,SelectPntActivity.class);
                            break;
                        case DEVICE_REPLACE_MODULE:
                            intent.setClass(this, DevSwitchActivity.class);
                            break;
                        case PERSON_DETAIL_MODULE:
                            intent.setClass(this, PersonDetailActivity.class);
                            break;
                    }
                    intent.putExtra("SN",strSN);//SN号
                    Utils.closeSoftKeyboard(this);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }
}
