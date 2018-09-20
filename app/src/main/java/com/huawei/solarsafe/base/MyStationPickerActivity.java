package com.huawei.solarsafe.base;

import android.content.Intent;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.MyStationBean;
import com.huawei.solarsafe.bean.common.LogCallBack;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 选择电站界面
 * <p>
 * 在onActivityResult中拿选择的电站集合
 * <p>
 * 2017/7/7
 *
 * @author p00587 ning
 */

public class MyStationPickerActivity extends BaseActivity {
    private MyStationBean root;
    private RecyclerView recyclerView;
    private boolean isFirst = true;
    private static final String TAG = "MyStationPickerActivity";

    @Override
    protected void initView() {
        arvTitle.setText(getString(R.string.station_choice_notice));
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("root", root);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

//        tv_title.setText(getString(R.string.station_choice_notice));
        Intent intent = getIntent();
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                isFirst = intent.getBooleanExtra("isFirst", true);
                root = (MyStationBean) intent.getSerializableExtra("root");
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        if (isFirst) {
            showLoading();
            root = null;
            requestStationList();
        } else {
            if (root == null) {
                requestStationList();
            } else {
                recyclerView.setAdapter(new SimpleItemAdapter());
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_station_picker_my;
    }

    private void requestStationList() {
        final Map<String, String> params = new HashMap<>();
        params.put("isSearchd", "true");
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        NetRequest.getInstance().asynPostJson(NetRequest.IP + "/domain/queryUserDomainStaRes", params, new LogCallBack() {
            @Override
            protected void onFailed(Exception e) {
                dismissLoading();
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
            }

            @Override
            protected void onSuccess(String data) {
                try {
                    final JSONArray jsonArray = new JSONObject(data).optJSONArray("data");
                    JSONObject min = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (!jsonObject.optString("model").equals("STATION")) {
                            if (min != null) {
                                if (Integer.parseInt(jsonObject.optString("id"))
                                        < Integer.parseInt(min.optString("id"))) {
                                    min = jsonObject;
                                }
                            } else {
                                min = jsonObject;
                            }
                        }
                    }
                    if (min == null) return;
                    MyStationBean station = new MyStationBean();
                    station.id = min.optString("id");
                    station.pid = min.optString("pid");
                    station.sort = min.optString("sort");
                    station.name = min.optString("name");
                    station.model = min.optString("model");
                    station.children = new ArrayList<>();
                    root = station;
                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            try {
                                findChildren(root, jsonArray);
                            } catch (JSONException e) {
                                Log.e(TAG, "onSuccess: " + e.toString() );
                            }finally {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dismissLoading();
                                        recyclerView.setAdapter(new SimpleItemAdapter());
                                    }
                                });
                            }
                        }
                    }.start();

                } catch (Exception e) {
                    Log.e(TAG, "onSuccess: " + e.toString() );
                    ToastUtil.showMessage("error occurred");
                }
            }
        });
    }

    private void findChildren(MyStationBean parent, JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            MyStationBean stationBean = new MyStationBean();
            stationBean.id = jsonObject.optString("id");
            stationBean.pid = jsonObject.optString("pid");
            stationBean.sort = jsonObject.optString("sort");
            stationBean.name = jsonObject.optString("name");
            stationBean.model = jsonObject.optString("model");
            if (!stationBean.model.equals("STATION")) {
                stationBean.children = new ArrayList<>();

                if (parent != null && parent.id.equals(String.valueOf(stationBean.pid))) {
                    stationBean.p = parent;
                    parent.children.add(stationBean);
                    findChildren(stationBean, jsonArray);
                }
            } else {
                if (parent != null && parent.id.equals(String.valueOf(stationBean.pid))) {
                    stationBean.p = parent;
                    parent.children.add(stationBean);
                }
            }
        }
    }

    private static int getSize(MyStationBean stationBean) {
        if (stationBean.children != null && stationBean.isExpanded) {
            int i = 0;
            for (MyStationBean station : stationBean.children) {
                i += getSize(station);
            }
            return ++i;
        } else {
            return 1;
        }
    }

    public static ArrayList<MyStationBean> collectCheckedStations(MyStationBean stationBean, ArrayList<MyStationBean> c) {
        if (stationBean.isChecked){
            c.add(stationBean);
        }
        if (stationBean.children != null) {
            for (MyStationBean station : stationBean.children) {
                collectCheckedStations(station, c);
            }
        }
        return c;
    }

    private static int pointer = 0;

    private static MyStationBean position2Station(MyStationBean stationBean, final int pos) {
        if (pos == pointer)
            return stationBean;
        if (stationBean.children != null && stationBean.isExpanded) {
            for (int j = 0; j < stationBean.children.size(); j++) {
                MyStationBean child = stationBean.children.get(j);
                ++pointer; // 要操作同一个数
                child = position2Station(child, pos);
                if (child != null)
                    return child;
            }
        }
        return null;
    }

    private class SimpleItemAdapter extends RecyclerView.Adapter<MyStationPickerActivity.SimpleItemAdapter.ViewHolder>
            implements View.OnClickListener {
        @Override
        public MyStationPickerActivity.SimpleItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyStationPickerActivity.SimpleItemAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_item_checked, parent, false));
        }

        @Override
        public void onBindViewHolder(MyStationPickerActivity.SimpleItemAdapter.ViewHolder holder, int position) {
            pointer = 0;
            MyStationBean stationBean = position2Station(root, position);
            if (stationBean == null) return;

            if (stationBean.isChecked) {
                holder.checkBox.setChecked(true);
            } else {
                holder.checkBox.setChecked(false);
            }

            if (stationBean.children != null) {
                holder.arrow.setVisibility(View.VISIBLE);
                if (!stationBean.isExpanded) {
                    holder.arrow.setImageResource(R.drawable.domain_zk_icon);
                } else {
                    holder.arrow.setImageResource(R.drawable.domain_zd_icon);
                }
            } else {
                holder.arrow.setVisibility(View.INVISIBLE);
            }
            if ("STATION".equals(stationBean.model)) {
                holder.domainOrstation.setImageResource(R.drawable.domain_station_check);
            } else {
                holder.domainOrstation.setImageResource(R.drawable.domain_check);
            }
            if ("Msg.&topdomain".equals(stationBean.name)) {
                holder.checkBox.setText(MyApplication.getContext().getString(R.string.topdomain));
            } else {
                holder.checkBox.setText(stationBean.name);
            }
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) holder.domainOrstation.getLayoutParams();
            lp.leftMargin = Utils.dp2Px(MyStationPickerActivity.this, 20);
            MyStationBean parent = stationBean.p;
            while (parent != null) {
                parent = parent.p;
                lp.leftMargin += Utils.dp2Px(MyStationPickerActivity.this, 20); // indent arrow's width
            }
            holder.domainOrstation.setLayoutParams(lp);

            holder.checkBox.setOnClickListener(this);
            holder.checkBox.setTag(stationBean);

            holder.itemView.setOnClickListener(this);
            holder.itemView.setTag(stationBean);
        }

        @Override
        public int getItemCount() {
            if (root != null)
                return getSize(root);

            return 0;
        }

        @Override
        public void onClick(View v) {
            final MyStationBean stationBean = (MyStationBean) v.getTag();
            if (v instanceof CheckBox) {
                stationBean.isChecked = !stationBean.isChecked;
                checkChildren(stationBean);
                if (!stationBean.isChecked) {
                    MyStationBean parent = stationBean.p;
                    while (parent != null) {
                        parent.isChecked = false;
                        parent = parent.p;
                    }
                }
            } else {
                stationBean.isExpanded = !stationBean.isExpanded;
            }
            notifyDataSetChanged();
        }

        private void checkChildren(MyStationBean r) {
            if (r.children != null) {
                for (int j = 0; j < r.children.size(); j++) {
                    r.children.get(j).isChecked = r.isChecked;
                    checkChildren(r.children.get(j));
                }
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView arrow;
            CheckBox checkBox;
            ImageView domainOrstation;

            public ViewHolder(View itemView) {
                super(itemView);
                arrow = (ImageView) itemView.findViewById(R.id.id_treenode_icon);
                checkBox = (CheckBox) itemView.findViewById(R.id.id_treenode_checkbox);
                domainOrstation = (ImageView) itemView.findViewById(R.id.domain_icon);
            }
        }
    }

    private LoadingDialog loadingDialog;

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
    }

    public void dismissLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.dismiss();
    }
}