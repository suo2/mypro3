package com.huawei.solarsafe.view.pnlogger;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.logger104.database.PntConnectDao;
import com.huawei.solarsafe.logger104.database.PntConnectInfoItem;
import com.huawei.solarsafe.model.pnlogger.ShowSecondMode;
import com.huawei.solarsafe.presenter.pnlogger.SelectPntPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.SysUtils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.utils.customview.swipe.SwipeLayout;
import com.huawei.solarsafe.utils.customview.swipe.util.BaseSwipeAdapter;
import com.huawei.solarsafe.view.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create Date: 2017/3/7
 * Create Author: P00171
 * Description :选择数采页面
 */
public class SelectPntActivity extends BaseActivity<SelectPntPresenter> implements ISelectPntView
        , View.OnClickListener, TextWatcher {
    private static final int QUERY_SECOND_DEVICE = 2;
    private ListView lvPntList;
    private Map<Integer, Boolean> isCheckMap = new HashMap<>();
    private PntAdapter pntAdpter;
    private List<PntConnectInfoItem> pntList;
    private ImageView ivShuCai;
    private ImageView ivDianzhan;
    private ImageView ivFinish;
    private TextView tvShuCai;
    private TextView tvDianzhan;
    private TextView tvFinish;
    private ImageView ivArrow1;
    private ImageView ivArrow2;
    private ImageView ivTime;
    private ImageView ivCanale;
    private ImageView ivScan;
    private Button btNext;
    private LinearLayout llBack;
    private TextView tvTitle;
    private EditText etSearchPnt;
    private List<PntConnectInfoItem> searchPnt;
    //记录被选中的条目的esn号
    private ArrayList<String> esns = new ArrayList<>();
    private PntConnectDao dao;
    private LoadingDialog loadingDialog;
    private String scanResult;
    private int pos;
    private PnlCache pnlCache = new PnlCache();
    private final String SCAN_MODULE="scanModule";
    private final int SELECT_PNT_MODULE=5;//选择数采

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnlogger_select_pnt);
        initView();
        presenter.getPntList();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pnlogger_select_pnt;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isCheckMap.clear();
        etSearchPnt.setText("");
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        PntAdapter adpter = new PntAdapter(dao.queryPntInfo(0,LocalData.getInstance().getIp() +"::" + LocalData.getInstance().getLoginName()));
        lvPntList.setAdapter(adpter);
        adpter.notifyDataSetChanged();
    }

    @Override
    protected void initView() {
        lvPntList = (ListView) findViewById(R.id.lv_pnt_list);
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        llBack.setOnClickListener(this);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(R.string.select_data_logger_title);
        btNext = (Button) findViewById(R.id.next_step);
        btNext.setOnClickListener(this);
        ivCanale = (ImageView) findViewById(R.id.iv_cancle);
        ivCanale.setOnClickListener(this);
        ivTime = (ImageView) findViewById(R.id.iv_time);
        ivTime.setOnClickListener(this);
        ivScan = (ImageView) findViewById(R.id.iv_shucai_setting);
        ivScan.setImageResource(R.drawable.icon_scan);
        ivScan.setVisibility(View.VISIBLE);
        ivScan.setOnClickListener(this);
        ivShuCai = (ImageView) findViewById(R.id.iv_shucai);
        ivDianzhan = (ImageView) findViewById(R.id.iv_xzdz);
        ivFinish = (ImageView) findViewById(R.id.iv_finish);
        ivArrow1 = (ImageView) findViewById(R.id.iv_arrow1);
        ivArrow2 = (ImageView) findViewById(R.id.iv_arrow2);
        tvShuCai = (TextView) findViewById(R.id.tv_shucai);
        tvDianzhan = (TextView) findViewById(R.id.tv_xzdz);
        tvFinish = (TextView) findViewById(R.id.tv_finish);
        etSearchPnt = (EditText) findViewById(R.id.et_search_pnt);
        //初始化
        tvShuCai.setTextColor(getResources().getColor(R.color.shucai_color_on));
        tvDianzhan.setTextColor(getResources().getColor(R.color.shucai_color));
        tvFinish.setTextColor(getResources().getColor(R.color.shucai_color));
        ivShuCai.setImageResource(R.drawable.shucai_on);
        ivDianzhan.setImageResource(R.drawable.xzdz2);
        ivFinish.setImageResource(R.drawable.wancheng1);
        ivArrow1.setImageResource(R.drawable.arrow);
        ivArrow2.setImageResource(R.drawable.arrow);
        searchPnt = new ArrayList<>();
        etSearchPnt.addTextChangedListener(this);
        dao = PntConnectDao.getInstance();
        //进入界面时，不弹出软件盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        tv_title.requestFocus();
    }

    @Override
    public void showPntList(List<PntConnectInfoItem> pntList) {
        if (pntAdpter == null) {
            this.pntList = pntList;
            pntAdpter = new PntAdapter(this.pntList);
            lvPntList.setAdapter(pntAdpter);
        } else {
            isCheckMap.clear();
            this.pntList.clear();
            this.pntList.addAll(pntList);
            pntAdpter.notifyDataSetChanged();
        }
    }

    @Override
    public void showSecondDev(PntConnectInfoItem deviceInfo) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("deviceInfo", deviceInfo);
        SysUtils.startActivity(this, ShowSecondActivity.class, bundle);
    }

    @Override
    public void showDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    @Override
    public void dismissDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void getDevBindStatus(int status) {
        switch (status) {
            case ShowSecondMode.NO_JOIN:
                dismissDialog();
                Toast.makeText(this, getString(R.string.pnt_not_exits) + getString(R.string.esn_num_is) + scanResult, Toast.LENGTH_SHORT).show();
                break;
            case ShowSecondMode.JOIN_NO_STATION:
                dismissDialog();
                //查询下联设备、
                Intent intent = new Intent(this, SecondDeviceActivity.class);
                intent.putExtra("esn", scanResult);
                startActivityForResult(intent, QUERY_SECOND_DEVICE);
                break;
            case ShowSecondMode.JOIN_STATION:
                dismissDialog();
                Toast.makeText(this, getString(R.string.pnt_connected_station) + getString(R.string.esn_num_is) + scanResult, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    protected SelectPntPresenter setPresenter() {
        return new SelectPntPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_step:
                esns.clear();
                //保存设备信息到本地
                saveDeviceInfo();
                Intent intent = new Intent(this, StationListActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_cancle:
                etSearchPnt.setText("");
                isCheckMap.clear();
                btNext.setVisibility(View.GONE);
                lvPntList.setAdapter(new PntAdapter(pntList));
                break;
            case R.id.ll_back:
                finish();
                break;
            case R.id.iv_shucai_setting://扫码
                //跳Zxing页面
                new IntentIntegrator(this)
                        .setOrientationLocked(false)
                        .setCaptureActivity(ZxingActivity.class)
                        .addExtra(SCAN_MODULE,SELECT_PNT_MODULE)
                        .initiateScan();
                break;
            default:
                break;
        }
    }

    /**
     * 保存设备信息到本地
     */
    private void saveDeviceInfo() {
        pnlCache.clearPvInfo();
        pnlCache.clearPvName();
        Map<String, String> pvName = new HashMap<>();
        Map<String, List<String>> pvInfo = new HashMap<>();
        List<String> info;
        for (Map.Entry<Integer, Boolean> entry : isCheckMap.entrySet()) {
            if (entry.getValue()) {
                //获取ESN号
                PntConnectInfoItem deviceInfo = (PntConnectInfoItem) lvPntList.getAdapter().getItem(entry.getKey());
                String esn = deviceInfo.getDeviceESN();
                pvName.put(esn, deviceInfo.getDeviceName());
                esns.add(esn);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < esns.size(); i++) {
            if (i != esns.size() - 1) {
                sb.append(esns.get(i) + "|");
            } else {
                sb.append(esns.get(i));
            }
        }
        //将ESN号保存到本地
        LocalData.getInstance().setEsn(sb.toString());
        //保存到本地 下联设备esn --- 下联设备名称
        for (int i = 0; i < esns.size(); i++) {
            info = new ArrayList<>();
            //数采设备esn
            String esn = esns.get(i);
            //得到下联设备
            //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
            List<PntConnectInfoItem> infoItems = dao.queryDeviceByPntESN(esn, LocalData.getInstance().getIp()+"::"+LocalData.getInstance().getLoginName());
            //得到下联设备pv容量
            //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
            String pvCapa = dao.queryPvInfo(esn, LocalData.getInstance().getIp()+"::"+LocalData.getInstance().getLoginName());
            //得到下联设备的esn号
            for (int j = 0; j < infoItems.size(); j++) {
                PntConnectInfoItem infoItem = infoItems.get(j);
                String deviceESN = infoItem.getDeviceESN();
                String deviceName = infoItem.getDeviceName();
                if (pvName.containsKey(deviceESN)) {
                    pvName.remove(deviceESN);
                }
                pvName.put(deviceESN, deviceName);
                if (!TextUtils.isEmpty(pvCapa)) {
                    String[] pvCapas = pvCapa.split("\\|");
                    for (int k = 0; k < pvCapas.length; k++) {
                        info.add(pvCapas[k]);
                        if (pvInfo.containsKey(deviceESN)) {
                            pvInfo.remove(deviceESN);
                        }
                        pvInfo.put(deviceESN, info);
                    }
                }
            }
        }
        pnlCache.putPvInfo(pvInfo);
        pnlCache.setPvNames(pvName);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        isCheckMap.clear();
        searchPnt.clear();
        String search = etSearchPnt.getText().toString().trim();
        //当输入为空时，隐藏按钮
        if (TextUtils.isEmpty(search)) {
            ivCanale.setVisibility(View.INVISIBLE);
            btNext.setVisibility(View.GONE);
            lvPntList.setAdapter(new PntAdapter(pntList));
        } else {
            ivCanale.setVisibility(View.VISIBLE);
            if (pntList != null && pntList.size() > 0) {
                //遍历listview的数据集合
                for (int i = 0; i < pntList.size(); i++) {
                    //得到每一个电站对象
                    PntConnectInfoItem deviceInfo = pntList.get(i);
                    //得到每个电站的名称及地址
                    String name = deviceInfo.getDeviceName();
                    //如果搜索的内容包含在电站名中，即可添加进行显示
                    if (!TextUtils.isEmpty(name) && name.contains(search)) {
                        searchPnt.add(deviceInfo);
                    }
                }
                lvPntList.setAdapter(new PntAdapter(searchPnt));
            }
        }
    }

    class PntAdapter extends BaseSwipeAdapter implements CompoundButton.OnCheckedChangeListener {
        private List<PntConnectInfoItem> pntList;

        public PntAdapter(List<PntConnectInfoItem> pntList) {
            this.pntList = pntList;
        }

        @Override
        public int getCount() {
            return pntList == null ? 0 : pntList.size();
        }

        @Override
        public PntConnectInfoItem getItem(int position) {
            return pntList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            isCheckMap.put((Integer) buttonView.getTag(), isChecked);
            if (isCheckMap.containsValue(true)) {
                btNext.setVisibility(View.VISIBLE);
            } else {
                btNext.setVisibility(View.GONE);
            }
        }

        @Override
        public int getSwipeLayoutResourceId(int position) {
            return R.id.swipeLayout;
        }

        @Override
        public View generateView(int position, ViewGroup parent) {
            return LayoutInflater.from(SelectPntActivity.this).inflate(R.layout.pnlogger_listitem_select_pnt, null);
        }

        @Override
        public void fillValues(int position, View convertView) {
            final PntConnectInfoItem deviceInfo = pntList.get(position);
            final SwipeLayout swipeLayout = (SwipeLayout) convertView.findViewById(R.id.swipeLayout);
            TextView tvName = (TextView) convertView.findViewById(R.id.tv_pnt_name);
            TextView tvAddress = (TextView) convertView.findViewById(R.id.tv_pnt_address);
            CheckBox cbSelect = (CheckBox) convertView.findViewById(R.id.cb_select);
            cbSelect.setOnCheckedChangeListener(this);
            tvName.setText(TextUtils.isEmpty(deviceInfo.getDeviceName()) ? deviceInfo.getDeviceESN() : deviceInfo.getDeviceName());
            cbSelect.setTag(position);
            Boolean isChecked = isCheckMap.get(position);
            cbSelect.setChecked(isChecked == null ? false : isChecked);
            tvAddress.setText(deviceInfo.getPntLocation());
            //listview的条目点击事件实现
            swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (swipeLayout.getOpenStatus().equals(SwipeLayout.Status.Open)) {
                        swipeLayout.setClickToClose(true);
                    } else {
                        LocalData.getInstance().setEsn(deviceInfo.getDeviceESN());
                        presenter.getScStatus(deviceInfo, LocalData.getInstance().getEsn());
                    }
                }
            });
            //设置sample1的拖拽显示方式，这个效果和默认一样， 另一种方式是SwipeLayout.ShowMode.LayDown，效果是sample2拖拽显示时的效果
            swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
            convertView.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                    dao.deleteDeviceInfoByEsn(deviceInfo.getDeviceESN(), LocalData.getInstance().getIp()+"::"+LocalData.getInstance().getLoginName());
                    pntList.clear();
                    pntList = dao.queryPntInfo(0,LocalData.getInstance().getIp() +"::" + LocalData.getInstance().getLoginName());
                    lvPntList.setAdapter(new PntAdapter(pntList));
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            //处理扫描二维码返回的结果
            if (requestCode == IntentIntegrator.REQUEST_CODE && resultCode == RESULT_OK) {
                IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if (intentResult != null) {
                    if (TextUtils.isEmpty(intentResult.getContents().trim())) {
                        Toast.makeText(this, R.string.scan_nothing, Toast.LENGTH_LONG).show();
                    } else {
                        scanResult = intentResult.getContents().trim();
                        //查询设备状态
                        presenter.getDevBindStatus(scanResult);
                    }
                }
            } else if (requestCode == QUERY_SECOND_DEVICE) {
                if (data != null) {
                    boolean sure = data.getBooleanExtra("sure", false);
                    if (sure) {
                        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                        List<String> esns = dao.queryEsns( LocalData.getInstance().getIp()+"::"+LocalData.getInstance().getLoginName());
                        if (esns.contains(scanResult)) {
                            //如果本地已经存在。便选择
                            for (int i = 0; i < pntList.size(); i++) {
                                if (!TextUtils.isEmpty(scanResult) && !TextUtils.isEmpty(pntList.get(i).getDeviceESN())){
                                    if (pntList.get(i).getDeviceESN().equals(scanResult)){
                                        pos = i;
                                    }
                                }
                            }
                            lvPntList.smoothScrollToPosition(pos);
                        } else {
                            dao.insert(new PntConnectInfoItem("", scanResult, 0, System.currentTimeMillis(), "",
                                    //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                                    LocalData.getInstance().getIp() + "::" + LocalData.getInstance().getLoginName()));
                        }
                        pntList.clear();
                        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                        pntList = dao.queryPntInfo(0,LocalData.getInstance().getIp() + "::" + LocalData.getInstance().getLoginName());
                        PntAdapter adapter = new PntAdapter(pntList);
                        lvPntList.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        } catch (Exception e){
            Log.e(TAG, "onActivityResult: " + e.getMessage());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        String strSN=intent.getStringExtra("SN");

        if (TextUtils.isEmpty(strSN)) {
            Toast.makeText(this, R.string.scan_nothing, Toast.LENGTH_LONG).show();
        } else {
            scanResult = strSN;
            //查询设备状态
            presenter.getDevBindStatus(scanResult);
        }
    }

    private static final String TAG = "SelectPntActivity";
}
