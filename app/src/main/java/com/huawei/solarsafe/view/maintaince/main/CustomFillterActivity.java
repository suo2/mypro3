package com.huawei.solarsafe.view.maintaince.main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.FillterMsg;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.database.FillterMsgDao;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.sdlv.Menu;
import com.huawei.solarsafe.utils.customview.sdlv.MenuItem;
import com.huawei.solarsafe.utils.customview.sdlv.SlideAndDragListView;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.maintaince.operation.MaintenanceActivityNew;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomFillterActivity extends BaseActivity implements View.OnClickListener {
    private SlideAndDragListView slideAndDragListView;
    private FillterAdapter fillterAdapter;
    private LinearLayout llNoFillter;
    private TextView fillterNum;
    private FillterMsgDao fillterMsgDao;
    private ArrayList<FillterMsg> fillterMsgs;
    private String type;
    private String statusIds;
    private String alarmLeveIds;
    private String devTypeId;
    private Map<Integer,String> devTypeMap = new HashMap<>();
    private static final String TAG = "CustomFillterActivity";
    private LocalBroadcastManager lbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lbm = LocalBroadcastManager.getInstance(MyApplication.getContext());
        fillterMsgDao = new FillterMsgDao(this);
        //【解DTS单】 DTS2018012301675 修改人：江东
        Intent intent = getIntent();
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                String temp =intent.getStringExtra("TYPE");
                if (temp!=null){
                    type = temp;
                }else {
                    type ="";
                }
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        } else {
            type = "";
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_station_fillter;
    }

    @Override
    protected void initView() {
        fillterMsgs = new ArrayList<>();
        tv_title.setText(getString(R.string.fliter_condition));
        tv_right.setText(getString(R.string.create_new_one));
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setOnClickListener(this);
        tv_left.setOnClickListener(this);
        slideAndDragListView = (SlideAndDragListView) findViewById(R.id.listview);
        Menu menu = new Menu(true, 0);
        menu.addItem(new MenuItem.Builder().setDirection(MenuItem.DIRECTION_RIGHT).setWidth(Utils.dp2Px(this,60)).setBackground(new ColorDrawable(Color.RED)).setText(getResources().getString(R.string.delete)).setTextColor(Color.WHITE).setTextSize(15).build());
        menu.addItem(new MenuItem.Builder().setDirection(MenuItem.DIRECTION_RIGHT).setWidth(Utils.dp2Px(this,60)).setBackground(new ColorDrawable(Color.LTGRAY)).setText(getResources().getString(R.string.edit)).setTextColor(Color.WHITE).setTextSize(15).build());
        slideAndDragListView.setMenu(menu);
        slideAndDragListView.setOnMenuItemClickListener(new SlideAndDragListView.OnMenuItemClickListener() {
            @Override
            public int onMenuItemClick(View v, int itemPosition, int buttonPosition, int direction) {
                switch (direction) {
                    case MenuItem.DIRECTION_RIGHT:
                        switch (buttonPosition) {
                            case 0:
                                if (fillterMsgs != null && fillterMsgs.size() > 0) {
                                    FillterMsg fillterMsg = fillterMsgs.get(itemPosition);
                                    fillterMsgDao.deleteMsgById(fillterMsg.getId());
                                    fillterMsgs.remove(itemPosition);
                                    fillterAdapter.notifyDataSetChanged();
                                    fillterNum.setText(fillterMsgs.size() + "");
                                }
                                break;
                            case 1:
                                if (fillterMsgs != null && fillterMsgs.size() > 0) {
                                    FillterMsg fillterMsg = fillterMsgs.get(itemPosition);
                                    toIntentFillterDetail(fillterMsg);
                                }
                                break;
                        }
                        break;
                }
                return 0;
            }
        });
        slideAndDragListView.setOnItemClickListener(new SlideAndDragListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //返回电站列表
                Intent intent = new Intent();
                intent.putExtra("fillter", fillterMsgs.get(i));
                intent.setAction(MaintenanceActivityNew.ACTION_FILLTER_MSG);
                lbm.sendBroadcast(intent);
                finish();
            }
        });
        fillterAdapter = new FillterAdapter();
        slideAndDragListView.setAdapter(fillterAdapter);
        llNoFillter = (LinearLayout) findViewById(R.id.ll_no_fillter);
        devTypeMap = DevTypeConstant.getDevTypeMap(this);

    }

    private void toIntentFillterDetail(FillterMsg fillterMsg) {
        Intent toIntent;
        if (type.equals(NewDeviceWarnFragment.TAG)) {
            toIntent = new Intent(this, DevAlarmFillterActivity.class);
        } else {
            toIntent = new Intent(this, RealTimeAlarmFillterActivity.class);
        }
        toIntent.putExtra("TYPE", type);
        toIntent.putExtra("fillter", fillterMsg);
        startActivity(toIntent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        fillterMsgs = fillterMsgDao.queryMsg(GlobalConstants.userId + "", type);
        if (fillterMsgs.size() == 0) {
            slideAndDragListView.setVisibility(View.GONE);
            llNoFillter.setVisibility(View.VISIBLE);
        } else {
            slideAndDragListView.setVisibility(View.VISIBLE);
            llNoFillter.setVisibility(View.GONE);
        }
        fillterNum = (TextView) findViewById(R.id.fillter_num);
        fillterNum.setText(fillterMsgs.size() + "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                finish();
                break;
            case R.id.tv_right:
                Intent intent;
                if (type.equals(NewDeviceWarnFragment.TAG)) {
                    intent = new Intent(this, DevAlarmFillterActivity.class);
                } else {
                    intent = new Intent(this, RealTimeAlarmFillterActivity.class);
                }
                intent.putExtra("TYPE", type);
                startActivity(intent);
                finish();
                break;
        }
    }

    class FillterAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return fillterMsgs == null ? 0 : fillterMsgs.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(CustomFillterActivity.this).inflate(R.layout.station_fillter_item, null);
                viewHolder.name = (TextView) convertView.findViewById(R.id.fillter_name);
                viewHolder.fillter_dev_type = (TextView) convertView.findViewById(R.id.fillter_dev_type);
                viewHolder.fillter_alarm_leve = (TextView) convertView.findViewById(R.id.fillter_alarm_leve);
                viewHolder.fillter_alarm_status = (TextView) convertView.findViewById(R.id.fillter_alarm_status);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            FillterMsg fillterMsg = fillterMsgs.get(position);
            viewHolder.name.setText(fillterMsg.getFillterName());
            if(!TextUtils.isEmpty(fillterMsg.getAlarmStatus())){
                switch (fillterMsg.getAlarmStatus()){
                    case "1":
                        statusIds = getString(R.string.activation);
                        break;
                    case "2":
                        statusIds = getString(R.string.pvmodule_alarm_sured);
                        break;
                    case "3":
                        statusIds = getString(R.string.in_hand);
                        break;
                    case "4":
                        statusIds = getString(R.string.handled);
                        break;
                    case "5":
                        statusIds = getString(R.string.cleared);
                        break;
                    case "6":
                        statusIds = getString(R.string.restored);
                        break;
                    default:
                        statusIds = getString(R.string.all_of);
                        break;
                }
            }
            viewHolder.fillter_alarm_status.setText(statusIds);
            if(!TextUtils.isEmpty(fillterMsg.getAlarmLevel())){
                switch (fillterMsg.getAlarmLevel()){
                    case "1":
                        alarmLeveIds = getString(R.string.serious);
                        break;
                    case "2":
                        alarmLeveIds = getString(R.string.important);
                        break;
                    case "3":
                        alarmLeveIds = getString(R.string.subordinate);
                        break;
                    case "4":
                        alarmLeveIds = getString(R.string.suggestive);
                        break;
                    default:
                        alarmLeveIds = getString(R.string.all_of);
                        break;
                }
            }
            viewHolder.fillter_alarm_leve.setText(alarmLeveIds);
            for(Map.Entry entry:devTypeMap.entrySet()){
                if(fillterMsg.getDevType() != null && fillterMsg.getDevType().equals(entry.getKey()+"")){
                   devTypeId = (String) entry.getValue();
                }
            }
            if(TextUtils.isEmpty(devTypeId)){
                devTypeId = getString(R.string.all_of);
            }
            viewHolder.fillter_dev_type.setText(devTypeId);
            return convertView;
        }

        class ViewHolder {
            TextView name;
            TextView fillter_dev_type,fillter_alarm_leve,fillter_alarm_status;

        }
    }
}
