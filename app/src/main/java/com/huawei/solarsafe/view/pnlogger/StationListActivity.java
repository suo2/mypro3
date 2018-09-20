package com.huawei.solarsafe.view.pnlogger;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.pnlogger.PntStation;
import com.huawei.solarsafe.model.pnlogger.ShowSecondMode;
import com.huawei.solarsafe.presenter.pnlogger.StationListPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class StationListActivity extends BaseActivity<StationListPresenter> implements IStationListView, AdapterView
        .OnItemClickListener, View.OnClickListener {
    private static final String TAG = "StationListActivity";
    private List<PntStation> stations;
    private TextView tvRefresh;
    private ListView mStationList;
    private Context mContext;
    private StationAdapter stationAdapter;
    private Button nextStep;
    private ImageView ivAddStation;
    private String stationCode = "";
    private ImageView ivShuCai;
    private ImageView ivDianzhan;
    private ImageView ivFinish;
    private TextView tvShuCai;
    private TextView tvDianzhan;
    private TextView tvFinish;
    private TextView tvTitle;
    private ImageView ivArrow1;
    private ImageView ivArrow2;
    private ImageView ivCanale;
    private LinearLayout llBack;
    private EditText etSearch;
    private LoadingDialog loadingDialog;
    //记录当前点击的条目的位置
    private int mCurrentPos = -1;
    private List<PntStation> searchStation;

    /**
     * 数采是否可连接
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_list2);
        mContext = this;
        initView();
        getEsns();
        presenter.queryDevBindStatus(getEsns());
    }

    private List<String> getEsns() {
        String esns = LocalData.getInstance().getEsn();
        //【安全特性】DTS2018032000703 使用DroidFuzz工具向StationListActivity发送消息，APP出现空指针异常，导致APP Crash
        //【修改人】zhaoyufeng
        ArrayList<String> list = new ArrayList<>();
        if (!TextUtils.isEmpty(esns)) {
            String[] arrayEsns = esns.split("\\|");
            int size = arrayEsns.length;
            for (int i = 0; i < size; i++) {
                list.add(arrayEsns[i]);
            }
        }
        return list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.refreshListData();
        cleanEtSearch();
        nofifyDataChange(stations);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_station_list2;
    }

    @Override
    protected void initView() {
        tvRefresh = (TextView) findViewById(R.id.tv_refresh);
        mStationList = (ListView) findViewById(R.id.station_list);
        etSearch = (EditText) findViewById(R.id.et_search);
        llBack = (LinearLayout) findViewById(R.id.ll_back);
        llBack.setOnClickListener(this);
        ivAddStation = (ImageView) findViewById(R.id.iv_add);
        ivCanale = (ImageView) findViewById(R.id.iv_cancle_station);
        ivCanale.setOnClickListener(this);
        ivShuCai = (ImageView) findViewById(R.id.iv_shucai);
        ivDianzhan = (ImageView) findViewById(R.id.iv_xzdz);
        ivFinish = (ImageView) findViewById(R.id.iv_finish);
        ivArrow1 = (ImageView) findViewById(R.id.iv_arrow1);
        ivArrow2 = (ImageView) findViewById(R.id.iv_arrow2);
        nextStep = (Button) findViewById(R.id.next_step);
        tvShuCai = (TextView) findViewById(R.id.tv_shucai);
        tvDianzhan = (TextView) findViewById(R.id.tv_xzdz);
        tvFinish = (TextView) findViewById(R.id.tv_finish);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(R.string.select_station_title);
        ivAddStation.setOnClickListener(this);
        nextStep.setOnClickListener(this);
        stationAdapter = new StationAdapter();
        tvRefresh.setOnClickListener(this);
        mStationList.setAdapter(stationAdapter);
        mStationList.setOnItemClickListener(this);
        //进入界面时，不弹出软件盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        tvTitle.requestFocus();
        //初始化
        tvShuCai.setTextColor(getResources().getColor(R.color.shucai_color));
        tvDianzhan.setTextColor(getResources().getColor(R.color.shucai_color_on));
        tvFinish.setTextColor(getResources().getColor(R.color.shucai_color));
        ivShuCai.setImageResource(R.drawable.shucai);
        ivDianzhan.setImageResource(R.drawable.xzdz_on);
        ivFinish.setImageResource(R.drawable.wancheng1);
        ivArrow1.setImageResource(R.drawable.arrow_on);
        ivArrow2.setImageResource(R.drawable.arrow);
        searchStation = new ArrayList<>();
        //搜索功能
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nextStep.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(s) && s.length() == 0) {
                    presenter.refreshListData();
                    nofifyDataChange(stations);
                } else {
                    if (stations == null) {
                        stations = new ArrayList<>();
                    }
                    searchStation.clear();
                    mCurrentPos = -1;
                    String search = etSearch.getText().toString().trim();
                    //当输入为空时，隐藏按钮
                    if (TextUtils.isEmpty(search)) {
                        ivCanale.setVisibility(View.INVISIBLE);
                        nofifyDataChange(stations, true);
                    } else {
                        ivCanale.setVisibility(View.VISIBLE);
                        if (stations != null && stations.size() > 0) {
                            //遍历listview的数据集合
                            for (int i = 0; i < stations.size(); i++) {
                                //得到每一个电站对象
                                PntStation station = stations.get(i);
                                //得到每个电站的名称及地址
                                String name = station.getStationName();
                                String addr = station.getStationAddr();
                                //如果搜索的内容包含在电站名中，即可添加进行显示
                                if ((!TextUtils.isEmpty(name) && name.contains(search)) || (!TextUtils.isEmpty(addr) && addr.contains(search))) {
                                    searchStation.add(station);
                                }
                            }
                            stationAdapter.setDatas(searchStation);
                        } else {
                            stationAdapter.setDatas(stations);
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void nofifyDataChange(List<PntStation> datas, boolean isSearch) {
        stations = datas;
        stationAdapter.setDatas(datas);
        if (!isSearch) {
            boolean isDataEmpty = stations == null || stations.isEmpty();
            tvRefresh.setVisibility(isDataEmpty ? View.VISIBLE : View.GONE);
            mStationList.setVisibility(isDataEmpty ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void nofifyDataChange(List<PntStation> datas) {
        nofifyDataChange(datas, false);
    }

    @Override
    public void itemClick() {
    }

    @Override
    public void toastFail(String msg) {
        ToastUtil.showMessage(msg);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PntStation station = (PntStation) parent.getAdapter().getItem(position);
        stationCode = station.getStationCode();
        //显示下一步按钮
        nextStep.setVisibility(View.VISIBLE);
        //记录点击的事件
        mCurrentPos = position;
        Log.e(TAG, "onItemClick: temp====" + mCurrentPos);
        //刷新界面
        if (TextUtils.isEmpty(etSearch.getText().toString())) {
            stationAdapter.setDatas(stations);
        } else {
            stationAdapter.setDatas(searchStation);
        }
        presenter.onItemClick();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_add:
                Intent intent = new Intent(this, com.huawei.solarsafe.view.stationmanagement.CreateStationActivity.class);
                intent.putExtra(com.huawei.solarsafe.view.stationmanagement.CreateStationActivity.KEY_IS_FROM_PNLOGGER, true);
                startActivity(intent);
                break;
            case R.id.tv_refresh:
                presenter.refreshListData();
                break;
            case R.id.next_step:
                int devBindStatus = LocalData.getInstance().getDevBindStatus();
                if (devBindStatus == ShowSecondMode.JOIN_STATION) {
                    //数采接入管理系统且已接入电站
                    DialogUtil.showErrorMsg(this, getString(R.string.this_data_logger_not_reconnect_station));
                    onResume();
                } else if (devBindStatus == ShowSecondMode.NO_JOIN) {
                    //数采还未接入管理系统,该页面不会出现此情况
                    Log.e(TAG, "数采还未接入管理系统,该页面不会出现此情况");
                    ToastUtil.showMessage(getString(R.string.pnt_not_exit));
                    onResume();
                } else if (devBindStatus == Integer.MIN_VALUE) {
                    //获取数采绑定状态异常,该页面不会出现此情况
                    Log.e(TAG, "获取数采绑定状态异常,该页面不会出现此情况");
                    ToastUtil.showMessage(getString(R.string.get_status_error));
                    onResume();
                } else {
                    //数采接入管理系统且未接入电站
                    Intent intent2 = new Intent(this, ConnectStationActivity.class);
                    //被选择电站的stationCode
                    intent2.putExtra("stationCode", stationCode);
                    startActivity(intent2);
                }
                break;
            case R.id.iv_cancle_station:
                //清除搜索框及搜索内容
                cleanEtSearch();
                break;
            case R.id.ll_back:
                finish();
                // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                break;
            default:
                break;
        }
    }

    /**
     * 清除搜索框及搜索内容
     */
    private void cleanEtSearch() {
        mCurrentPos = -1;
        etSearch.setText("");
        nextStep.setVisibility(View.GONE);
        ivCanale.setVisibility(View.INVISIBLE);
    }

    private class StationAdapter extends BaseAdapter {

        private List<PntStation> datas;

        public void setDatas(List<PntStation> temp) {
            if (temp == null) {
                return;
            }
            Log.e(TAG, "setDatas: temp" + temp.size());
            if (datas == null) {
                datas = new ArrayList<>();
            }
            datas.clear();
            datas.addAll(temp);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return datas == null ? 0 : datas.size();
        }

        @Override
        public PntStation getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.station_item_layout, null, false);
                holder.stationName = (TextView) convertView.findViewById(R.id.station_name);
                holder.stationAddr = (TextView) convertView.findViewById(R.id.station_addr);
                holder.llList = (LinearLayout) convertView.findViewById(R.id.ll_list);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            PntStation pntStation = datas.get(position);
            if (pntStation != null) {
                holder.stationName.setText(pntStation.getStationName());
                holder.stationAddr.setText(pntStation.getStationAddr());
            }
            // 只有当更新的位置等于当前位置时，更改颜色
            if (mCurrentPos == position) {
                holder.llList.setBackgroundColor(Color.GRAY);
            } else {
                holder.llList.setBackgroundColor(Color.TRANSPARENT);
            }
            return convertView;
        }

        private class ViewHolder {
            public TextView stationName, stationAddr;
            public LinearLayout llList;
        }
    }

    @Override
    protected StationListPresenter setPresenter() {
        return new StationListPresenter();
    }
}
