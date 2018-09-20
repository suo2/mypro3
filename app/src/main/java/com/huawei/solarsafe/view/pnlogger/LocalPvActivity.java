package com.huawei.solarsafe.view.pnlogger;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.pnlogger.PntGetSecondName;
import com.huawei.solarsafe.logger104.database.PntConnectDao;
import com.huawei.solarsafe.logger104.database.PntConnectInfoItem;
import com.huawei.solarsafe.presenter.pnlogger.LocalPVPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.view.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocalPvActivity extends BaseActivity<LocalPVPresenter> implements IPvDataView {
    private GridView pvContainer;
    private PntConnectDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_location_pv;
    }

    @Override
    protected void initView() {
        pvContainer = (GridView) findViewById(R.id.pv_container);
        pvContainer.setAdapter(new GridAdapter());
        dao = PntConnectDao.getInstance();
        ArrayList<PntConnectInfoItem> pntConnectInfoItems = dao.queryDeviceByPntESN(LocalData.getInstance().getEsn()
                //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
                , LocalData.getInstance().getIp()+"::"+LocalData.getInstance().getLoginName());
        for (int i = 0; i < pntConnectInfoItems.size(); i++) {
            String pvInfoString = pntConnectInfoItems.get(i).getPvInfo();
            if (TextUtils.isEmpty(pvInfoString)) {
                continue;
            }
            String[] pvValues = pvInfoString.split("\\|");
            List<String> pvDatas = Arrays.asList(pvValues);
            GridAdapter adapter = new GridAdapter();
            adapter.setData(pvDatas);
            pvContainer.setAdapter(adapter);
        }
    }

    private class GridAdapter extends BaseAdapter {
        List<String> data;

        public void setData(List<String> temp) {
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
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(LocalPvActivity.this).inflate(R.layout.pnt_pv_item, null, false);
                holder.name = (TextView) convertView.findViewById(R.id.pv_name);
                holder.value = (TextView) convertView.findViewById(R.id.pv_value);
                holder.value.setEnabled(false);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String temp = data.get(position);
            if (!TextUtils.isEmpty(temp)) {
                holder.name.setText("PV" + (position + 1));
                holder.value.setText(temp);
            }
            return convertView;
        }

        private class ViewHolder {
            public TextView name, value;
        }
    }



    @Override
    public void getSecondName(PntGetSecondName pntGetSecondName) {

    }

    @Override
    public void success() {
        finish();
    }

    @Override
    protected LocalPVPresenter setPresenter() {
        return new LocalPVPresenter();
    }
}
