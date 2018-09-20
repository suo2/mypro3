package com.huawei.solarsafe.view.pnlogger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.pnlogger.PntGetSecondName;
import com.huawei.solarsafe.bean.pnlogger.SecondName;
import com.huawei.solarsafe.bean.stationmagagement.SubDev;
import com.huawei.solarsafe.logger104.bean.SecondLineDevice;
import com.huawei.solarsafe.logger104.database.PntConnectDao;
import com.huawei.solarsafe.logger104.database.PntConnectInfoItem;
import com.huawei.solarsafe.logger104.database.PntDatabase;
import com.huawei.solarsafe.presenter.pnlogger.PVPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.SysUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.stationmanagement.changestationinfo.GroupStringConfigActivityChange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create Date:
 * Create Author: P00517
 * Description :B版数采组串配置
 */
public class BPVSettingActivity extends BaseActivity<PVPresenter> implements IPvDataView, View.OnClickListener,
        AdapterView.OnItemClickListener {
    private static final String TAG = "PVSettingActivity2";
    private TextView tvGoCfg, complete;
    private ListView devs;
    //设备名称的adapter
    private DevAdapter devAdapter;
    private Map<String, List<String>> maps = new HashMap<>();
    private PnlCache pnlCache = new PnlCache();
    /**
     * 已选择设备position集合
     */
    private List<Integer> selectedPoss = new ArrayList<>();
    /**
     * 需要配置PV的下联设备（已过滤）
     */
    private List<SecondLineDevice> info;

    /**
     * SecondActivity中所有的下联设备（未过滤）
     */
    private List<SecondLineDevice> allSecondLineDevices;

    private PntConnectDao dao;
    private LoadingDialog loadingDialog;
    private final int OneSeriesInverters = 1; //1组串式逆变器
    private final int FifteenDCBusBoxes = 15;//15直流汇流箱
    private final int ThirtyEightHomeInverters = 38;//38户用逆变器
    private Map<String, SecondName> existDeviceMap = new HashMap();//<esn, devId>
    private List noExistDeviceList = new ArrayList();//<secondLineDevice>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pvsetting;
    }

    @Override
    protected void initView() {
        pnlCache.clearPvInfo();
        pnlCache.clearPvName();
        tvGoCfg = (TextView) findViewById(R.id.tv_go_config);
        complete = (TextView) findViewById(R.id.complete);
        devs = (ListView) findViewById(R.id.devs);
        devAdapter = new DevAdapter();
        devs.setAdapter(devAdapter);
        devs.setOnItemClickListener(this);
        tvGoCfg.setOnClickListener(this);
        complete.setOnClickListener(this);
        tv_title.setText(R.string.set_pv_info_title);
        Intent intent = getIntent();
        if(intent != null) {
            try{
                Bundle bundle = intent.getBundleExtra("b");
                dao = PntConnectDao.getInstance();
                if (bundle != null) {
                    allSecondLineDevices = (List<SecondLineDevice>) bundle.getSerializable("allSecondLineDevices");
                    info = filterPvCfgSecondLineDevices(allSecondLineDevices);
                    getPvNames(info);
                    getAllPvNames(allSecondLineDevices);
                }
                presenter.getSecondName(LocalData.getInstance().getEsn());
            }catch (Exception e){
                Log.e(TAG, "initView: " + e.getMessage());
            }
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CheckBox cb = (CheckBox) view.findViewById(R.id.cb);
        cb.setChecked(!cb.isChecked());
    }

    private class DevAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {
        List<SecondLineDevice> data;

        public void setData(List<SecondLineDevice> temp) {
            if (temp == null) {
                data = null;
                notifyDataSetChanged();
                return;
            }
            if (data == null) {
                data = new ArrayList<>();
            }
            data.clear();
            data.addAll(temp);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder ;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(BPVSettingActivity.this).inflate(R.layout.pnt_dev_item_name, null,
                        false);
                holder.name = (TextView) convertView.findViewById(R.id.dev_name);
                holder.type = (TextView) convertView.findViewById(R.id.dev_type);
                holder.cb = (CheckBox) convertView.findViewById(R.id.cb);
                holder.cb.setOnCheckedChangeListener(this);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            SecondLineDevice temp = data.get(position);
            String name = temp.getDeviceName();
            name = TextUtils.isEmpty(name) ? temp.getDeviceESN() : name;
            holder.name.setText(name);
            String type = "";
            long typeId = PntDatabase.getInstance().getDevTypeIdbyId(temp.getSignPointFlag());
            if (typeId == 1) {
                type = getString(R.string.zuchuan_inverter);
            } else if (typeId == 15) {
                type = getString(R.string.dcjs_str);
            } else if (typeId == 38) {
                type = getString(R.string.household_inverter_str);
            }
            holder.type.setText(type);
            holder.cb.setTag(position);
            boolean isContains = selectedPoss.contains(position);
            holder.cb.setChecked(isContains);
            return convertView;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Integer pos = (Integer) buttonView.getTag();
            if (isChecked) {
                if (!selectedPoss.contains(pos))
                    selectedPoss.add(pos);
            } else {
                if (selectedPoss.contains(pos))
                    selectedPoss.remove(pos);
            }
        }

        private class ViewHolder {
            TextView name;
            TextView type;
            CheckBox cb;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void getSecondName(PntGetSecondName pntGetSecondName) {

        List<SecondName> secondNames = pntGetSecondName.getSecondDeviceList();
        existDeviceMap.clear();
        noExistDeviceList.clear();
        for (SecondLineDevice secondLineDevice : allSecondLineDevices) {
            boolean existDevice = false;
            SecondName exitsSecondName = null;
            for (SecondName secondName : secondNames) {
                if (secondName.getDevEsn().equals(secondLineDevice.getDeviceESN())) {
                    exitsSecondName = secondName;
                    existDevice = true;
                    if(TextUtils.isEmpty(secondLineDevice.getDeviceName())){
                        secondLineDevice.setDeviceName(secondName.getDevName());
                    }
                    break;
                }
            }
            if (existDevice) {
                //存在则放入存在Map
                existDeviceMap.put(secondLineDevice.getDeviceESN(), exitsSecondName);
            } else {
                //不存在则放入不存在List
                noExistDeviceList.add(secondLineDevice);
            }
        }
        if (noExistDeviceList.isEmpty()) {
            info = filterPvCfgSecondLineDevices(allSecondLineDevices);
            getPvNames(info);
        } else {
            ToastUtil.showMessage(getString(R.string.should_open_station_again));
        }


    }

    @Override
    public void success() { }

    @Override
    protected PVPresenter setPresenter() {
        return new PVPresenter();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_go_config:
                if (selectedPoss.isEmpty()) {
                    ToastUtil.showMessage(R.string.please_select_device);
                    return;
                }
                List<Long> types = new ArrayList<>();
                for (int i = 0; i < info.size(); i++) {
                    if (selectedPoss.contains(i)) {
                        long type = PntDatabase.getInstance().getDevTypeIdbyId(info.get(i).getSignPointFlag());
                        types.add(type);
                    }
                }
                for (int i = 0; i < types.size(); i++) {
                    if (i < types.size() - 1) {
                        if (types.get(i) != types.get(i + 1)) {
                            Toast.makeText(this, R.string.select_the_same_type, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                devAdapter.notifyDataSetChanged();
                Intent intent = new Intent(this, GroupStringConfigActivityChange.class);
                String[] devIds = new String[selectedPoss.size()];
                List<SubDev> subDevs = new ArrayList<>();
                for (int i = 0; i < devIds.length; i++) {
                    String esn = ((SecondLineDevice) devAdapter.getItem(selectedPoss.get(i))).getDeviceESN();
                    SecondName secondName = existDeviceMap.get(esn);
                    devIds[i] = secondName.getDevId();
                    //组装SubDev
                    SubDev subDev = new SubDev();
                    //匹配
                    //stationId
                    subDev.setStationCode(secondName.getStationCode());//可能为空
                    subDev.setId(Long.valueOf(secondName.getDevId()));
                    subDev.setAreaId(secondName.getTwoLevelDomain());//可能为空
                    subDev.setBusiCode(secondName.getBusiCode());
                    subDev.setEsnCode(secondName.getDevEsn());
                    subDev.setDevTypeId(Integer.valueOf(secondName.getDevTypeId()));
                    subDevs.add(subDev);
                }
                intent.putExtra("esnList", devIds);
                intent.putParcelableArrayListExtra("devList", (ArrayList<? extends Parcelable>) subDevs);
                startActivityForResult(intent, 100);
                break;
            case R.id.complete:
                handleMaps();
                pnlCache.putPvInfo(maps);
                DialogUtil.showChooseDialog(this, "", getString(R.string.sure_pnlogger_connect_station),
                        getString(R.string.sure), getString(R.string.cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {   //确定的回调
                                //保存所有的下联设备信息
                                savePvInfo();
                                //关联下联设备
                                updatePvInfo();
                                startActivity();
                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {   //取消的回调
                                //保存所有的下联设备信息
                                savePvInfo();
                                //关联下联设备
                                updatePvInfo();
                            }
                        });
                break;
            case R.id.tv_complete_config:
                break;
        }
    }

    private void startActivity() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog(BPVSettingActivity.this);
                }
                if (!loadingDialog.isShowing()) {
                    loadingDialog.show();
                } else {
                    super.onPreExecute();
                }
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.e(TAG, "doInBackground: ", e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                SysUtils.startActivity(BPVSettingActivity.this, StationListActivity.class);
            }
        }.execute();
    }

    /**
     * 更新下联设备组串信息
     */
    private void updatePvInfo() {
        //下联设备esn号   pv容量信息
        StringBuilder sb = new StringBuilder();
        Map<String, List<String>> pvInfos = pnlCache.getPvInfos();
        for (Map.Entry<String, List<String>> entry : pvInfos.entrySet()) {
            //下联设备的esn号
            String esn = entry.getKey();
            // pv容量信息
            List<String> pvInfo = entry.getValue();
            if (pvInfo != null && sb != null) {
                //下联设备的Pv信息
                for (int i = 0; i < pvInfo.size(); i++) {
                    if (i != pvInfo.size() - 1) {
                        sb.append(pvInfo.get(i) + "|");
                    } else {
                        sb.append(pvInfo.get(i));
                    }
                }
            }
            //更新组串信息
            dao.updatePvInfoByEsn(esn, sb.toString(), LocalData.getInstance().getIp() + "::" + LocalData.getInstance().getLoginName());
        }
    }

    private void handleMaps() {
        if (maps != null) {
            List<String> temp = new ArrayList<>();
            for (Map.Entry<String, List<String>> var : maps.entrySet()) {
                String key = var.getKey();
                List<String> value = var.getValue();
                if (value.size() == 0) {
                    temp.add(key);
                }
            }
            for (String var : temp) {
                maps.remove(var);
            }
        }
    }

    private void getPvNames(List<SecondLineDevice> info) {
        if (info != null) {
            if (info.size() == 0) {
                ToastUtil.showMessage(getString(R.string.no_to_set_pvinfo_dev));
                return;
            }
            devAdapter.setData(info);
            initMaps(info);
        }
    }

    private void getAllPvNames(List<SecondLineDevice> info) {
        if (info != null) {
            HashMap<String, String> names = new HashMap<>();
            int size = info.size();
            for (int i = 0; i < size; i++) {
                SecondLineDevice var = info.get(i);
                String esn = var.getDeviceESN();
                String name = var.getDeviceName();
                if (!TextUtils.isEmpty(name)) {
                    //缓存下联设备的名称
                    names.put(esn, name);
                }

            }
            String esn = LocalData.getInstance().getEsn();
            if (esn.split(";").length == 1) {
                names.put(esn, LocalData.getInstance().getDvName());
            }
            pnlCache.setPvNames(names);
            presenter.reqSetDevNames(pnlCache.getPvNames());
        }
    }

    private void initMaps(List<SecondLineDevice> info) {
        maps.clear();
        if (info != null) {
            for (SecondLineDevice var : info) {
                String esn = var.getDeviceESN();
                String pvString = dao.queryPvInfo(esn, LocalData.getInstance().getIp() + "::" + LocalData.getInstance().getLoginName());
                if (!TextUtils.isEmpty(pvString)) {
                    String[] pv = pvString.split("\\|");
                    List<String> result = new ArrayList<>();
                    if (pv.length != 0) {
                        int size = pv.length;
                        for (int i = 0; i < size; i++) {
                            String value = pv[i];
                            result.add(value);
                        }
                    }
                    maps.put(esn, result);
                }
            }
        }
    }

    /**
     * 保存设备信息
     */
    private void savePvInfo() {
        //保存数采信息进数据库
        //如果数据库中已经存在当前的数采设备，就更新数据
        ArrayList<String> esns = dao.queryEsns(LocalData.getInstance().getIp() + "::" + LocalData.getInstance().getLoginName());
        String adress = LocalData.getInstance().getPntAddr();
        String pntEsn = LocalData.getInstance().getEsn();
        String pntName = LocalData.getInstance().getDvName();
        if (esns.contains(pntEsn)) {
            dao.deleteDeviceInfoByEsn(pntEsn, LocalData.getInstance().getIp() + "::" + LocalData.getInstance().getLoginName());
        }
        //保存数采信息到数据库
        //设备名称，设备ESN，数采连接状态，设备保存时间，数采地址
        PntConnectInfoItem info = new PntConnectInfoItem(pntName, pntEsn, 0,
                System.currentTimeMillis(), TextUtils.isEmpty(adress) ? "" : adress,
                LocalData.getInstance().getIp() + "::" + LocalData.getInstance().getLoginName());
        dao.insert(info);

        //下联设备esn号   pv名称
        Map<String, String> pvNames = pnlCache.getPvNames();
        for (Map.Entry<String, String> entry : pvNames.entrySet()) {
            //下联设备的esn号
            String esn = entry.getKey();
            //获取下联设备的名称
            String name = pvNames.get(esn);
            //上联数采esn
            String preEsn = LocalData.getInstance().getEsn();
            //modbus地址
            int modbusLocation = 0;
            //设备类型
            String deviceType = null;
            for (int i = 0; i < allSecondLineDevices.size(); i++) {
                SecondLineDevice device = allSecondLineDevices.get(i);
                if (esn.equals(device.getDeviceESN())) {
                    modbusLocation = device.getModbusAddr();
                    deviceType = device.getSignPointFlag() + "";
                }
            }

            if (esns.contains(esn)) {
                dao.deleteDeviceInfoByEsn(esn, LocalData.getInstance().getIp() + "::" + LocalData.getInstance().getLoginName());
            }
            //保存数采信息到数据库
            //设备名称，设备ESN，设备保存时间，modbus地址,设备类型，上级数采esn号
            PntConnectInfoItem infoItem = new PntConnectInfoItem(name, esn, System.currentTimeMillis(),
                    modbusLocation, deviceType, preEsn, LocalData.getInstance().getIp() + "::" + LocalData.getInstance().getLoginName());
            dao.insert(infoItem);
        }
    }

    /**
     * 过滤出需要配置PV的下联设备
     *
     * @param src
     * @return
     */
    private List<SecondLineDevice> filterPvCfgSecondLineDevices(List<SecondLineDevice> src) {
        List<SecondLineDevice> result = new ArrayList<>();
        if (src != null) {
            for (SecondLineDevice dev : src) {
                long devTypeId = PntDatabase.getInstance().getDevTypeIdbyId(dev.getSignPointFlag());
                if (devTypeId == OneSeriesInverters || devTypeId == FifteenDCBusBoxes || devTypeId == ThirtyEightHomeInverters) {
                    result.add(dev);
                }
            }
        }
        return result;
    }
}
