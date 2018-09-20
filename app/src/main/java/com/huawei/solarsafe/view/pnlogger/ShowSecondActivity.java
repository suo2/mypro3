package com.huawei.solarsafe.view.pnlogger;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.pnlogger.PntGetSecondName;
import com.huawei.solarsafe.logger104.database.PntConnectInfoItem;
import com.huawei.solarsafe.presenter.pnlogger.ShowSecondPresenter;
import com.huawei.solarsafe.utils.SysUtils;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Create Date: 2017/3/7
 * Create Author: P00171
 * Description :
 */
public class ShowSecondActivity extends BaseActivity<ShowSecondPresenter> implements IShowSecondView, View
        .OnClickListener {
    private ListView lvSecond;
    private List<PntConnectInfoItem> secondDeviceInfos;
    private SecondAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.refreshData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pnlogger_show_second;
    }

    @Override
    protected void initView() {
        lvSecond = (ListView) findViewById(R.id.lv_second);
        tv_title.setText(R.string.show_second_dev);
    }

    @Override
    public void getDevBindStatus(int status, String esn) {
    }

    @Override
    public void getSecondName(PntGetSecondName pntGetSecondName) {

    }

    @Override
    public void toast(String msg) {
        ToastUtil.showMessage(msg);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }

    @Override
    public void freshData(ArrayList<PntConnectInfoItem> data) {
        if (data != null) {
            if (adapter == null) {
                this.secondDeviceInfos = data;
                adapter = new SecondAdapter(this.secondDeviceInfos);
                lvSecond.setAdapter(adapter);
            } else {
                this.secondDeviceInfos.clear();
                this.secondDeviceInfos.addAll(secondDeviceInfos);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected ShowSecondPresenter setPresenter() {
        return new ShowSecondPresenter();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cfg_pv:
                SysUtils.startActivity(this, LocalPvActivity.class);
                break;
            default:
                break;
        }
    }

    class SecondAdapter extends BaseAdapter {
        private Context context;
        private List<PntConnectInfoItem> secondDeviceInfos;

        public SecondAdapter(List<PntConnectInfoItem> secondDeviceInfos) {
            this.secondDeviceInfos = secondDeviceInfos;
            context = ShowSecondActivity.this;
        }

        @Override
        public int getCount() {
            return secondDeviceInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return secondDeviceInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PntConnectInfoItem info = secondDeviceInfos.get(position);
            ViewHolde holde;
            if (convertView == null) {
                holde = new ViewHolde();
                convertView = LayoutInflater.from(context).inflate(R.layout.pnlogger_listitem_show_second, null);
                holde.tvName = (TextView) convertView.findViewById(R.id.tv_name);
                holde.tvEsn = (TextView) convertView.findViewById(R.id.tv_esn);
                holde.btnCfgPv = (Button) convertView.findViewById(R.id.btn_cfg_pv);
                holde.devType = (TextView) convertView.findViewById(R.id.tv_dev_type);
                holde.modbusAddr = (TextView) convertView.findViewById(R.id.tv_modbus_addr);
                holde.btnCfgPv.setOnClickListener(ShowSecondActivity.this);
                convertView.setTag(holde);
            } else {
                holde = (ViewHolde) convertView.getTag();
            }
            if (info != null) {
                holde.tvName.setText(info.getDeviceName());
                holde.tvEsn.setText(info.getDeviceESN());
                holde.devType.setText(info.getDeviceType());
                holde.modbusAddr.setText(info.getModbusLocation() + "");
                String pvInfo = info.getPvInfo();
                if (!TextUtils.isEmpty(pvInfo)) {
                    holde.btnCfgPv.setVisibility(View.VISIBLE);
                } else {
                    holde.btnCfgPv.setVisibility(View.GONE);
                }
            }
            return convertView;
        }

        class ViewHolde {
            TextView tvName;
            TextView tvEsn;
            TextView devType, modbusAddr;
            Button btnCfgPv;
        }
    }
}
