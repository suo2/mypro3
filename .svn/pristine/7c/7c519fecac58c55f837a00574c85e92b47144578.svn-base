package com.huawei.solarsafe.view.devicemanagement;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.common.LogCallBack;
import com.huawei.solarsafe.bean.device.DevLocationBean;
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
 * Created by p00507
 * on 2017/9/19.
 */
public class MyLocationPickerActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private String stationCode;
    private boolean isFirst;
    private DevLocationBean devRootBean;
    private DevLocationBean bedoreDevBean;
    private static final String TAG = "MyLocationPickerActivit";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_station_picker_my;
    }

    @Override
    protected void initView() {
        tv_title.setText(getString(R.string.location_choice_notice));
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
                intent.putExtra("devLocationBean", devRootBean);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        Intent intent = getIntent();
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                isFirst = intent.getBooleanExtra("isFirstLocation", true);
                stationCode = intent.getStringExtra("stationCode");
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        if (isFirst) {
            showLoading();
            bedoreDevBean = null;
            requestDevLocationList();
        } else {
            showLoading();
            requestDevLocationList();
        }

    }

    private void requestDevLocationList() {
        final Map<String, String> params = new HashMap<>();
        params.put("stationCode", stationCode);
        NetRequest.getInstance().asynPostJson(NetRequest.IP + "/devManager/queryLocationByStation", params, new LogCallBack() {
            @Override
            protected void onFailed(Exception e) {
                dismissLoading();
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
            }

            @Override
            protected void onSuccess(String data) {
                try {
                    dismissLoading();
                    JSONObject jsonObjectAll = new JSONObject(data).optJSONObject("data");
                    JSONArray jsonArray = jsonObjectAll.getJSONArray("list");
                    JSONObject min = null; // min id is root
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        if (TextUtils.isEmpty(jsonObject.optString("plocId"))) {
                            min = jsonObject;
                            break;
                        }
                    }

                    if (min == null) {
                        ToastUtil.showMessage(getResources().getString(R.string.no_data_tation));
                        return;
                    }
                    DevLocationBean devBean = new DevLocationBean();
                    devBean.setId(min.optString("id"));
                    devBean.setLocId(min.optString("locId"));
                    devBean.setDataFrom(min.optInt("dataFrom"));
                    devBean.setName(min.optString("name"));
                    devBean.setStationCode(min.optString("stationCode"));
                    devBean.setPlocId(min.optString("plocId"));
                    devBean.children = new ArrayList<>();
                    devRootBean = devBean;
                    // now we find the root
                    findChildren(devRootBean, jsonArray);

                    recyclerView.setAdapter(new MyLocationPickerActivity.SimpleItemAdapter());

                } catch (Exception e) {
                    Log.e(TAG, "onSuccess: " + e.toString() );
                    ToastUtil.showMessage("error occurred");
                }
            }
        });
    }

    private void findChildren(DevLocationBean parent, JSONArray jsonArray) throws JSONException {
        for (int i =  0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if(TextUtils.isEmpty(jsonObject.optString("plocId"))){
                continue;
            }
            DevLocationBean locationBean = new DevLocationBean();
            locationBean.setId(jsonObject.optString("id"));
            locationBean.setLocId(jsonObject.optString("locId"));
            locationBean.setDataFrom(jsonObject.optInt("dataFrom"));
            locationBean.setName(jsonObject.optString("name"));
            locationBean.setStationCode(jsonObject.optString("stationCode"));
            locationBean.setPlocId(jsonObject.optString("plocId"));
            if (!TextUtils.isEmpty(locationBean.getLocId())) {
                if (parent != null && parent.getLocId().equals(locationBean.getPlocId())) {
                    locationBean.children = new ArrayList<>();
                    locationBean.p = parent;
                    parent.children.add(locationBean);
                    findChildren(locationBean, jsonArray);
                }
            } else {
                if (parent != null && parent.getLocId().equals(locationBean.getPlocId())) {
                    locationBean.p = parent;
                    parent.children.add(locationBean);
                }
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

    public class SimpleItemAdapter extends RecyclerView.Adapter<MyLocationPickerActivity.SimpleItemAdapter.ViewHolder>  implements View.OnClickListener{


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_item_checked, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            pointer = 0;
            DevLocationBean devBean = position2Station(devRootBean, position);
            if (devBean == null) return;

            if (devBean.isChecked) {
                holder.checkBox.setChecked(true);
                if(devBean.children != null && devBean.children.size() != 0){
                    bedoreDevBean = devBean;
                }else if(bedoreDevBean == null){
                    bedoreDevBean = devBean;
                }
            } else {
                holder.checkBox.setChecked(false);
            }

            if (devBean.children != null && devBean.children.size() != 0) {
                holder.arrow.setVisibility(View.VISIBLE);
                if (!devBean.isExpanded) {
                    holder.arrow.setImageResource(R.drawable.domain_zd_icon);
                } else {
                    holder.arrow.setImageResource(R.drawable.domain_zk_icon);
                }
            } else {
                holder.arrow.setVisibility(View.INVISIBLE);
            }
            if (TextUtils.isEmpty(devBean.getPlocId())) {
                holder.domainOrstation.setImageResource(R.drawable.domain_station_check);

            } else {
                holder.domainOrstation.setImageResource(R.drawable.domain_check);
            }
            holder.checkBox.setText(devBean.getName());
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) holder.domainOrstation.getLayoutParams();
            lp.leftMargin = Utils.dp2Px(MyLocationPickerActivity.this, 20);
            DevLocationBean parent = devBean.p;
            while (parent != null) {
                parent = parent.p;
                lp.leftMargin += Utils.dp2Px(MyLocationPickerActivity.this, 20); // indent arrow's width
            }
            holder.domainOrstation.setLayoutParams(lp);

            holder.checkBox.setOnClickListener(this);
            holder.checkBox.setTag(devBean);

            holder.itemView.setOnClickListener(this);
            holder.itemView.setTag(devBean);
        }


        @Override
        public int getItemCount() {
            if (devRootBean != null)
                return getSize(devRootBean);

            return 0;
        }

        @Override
        public void onClick(View v) {
            final DevLocationBean devBean = (DevLocationBean) v.getTag();
            if (v instanceof CheckBox) {
                devBean.isChecked = !devBean.isChecked;
                //如果子类没被选择，将其父亲也取消选中
                if (!devBean.isChecked) {
                    DevLocationBean parent = devBean.p;
                    while (parent != null) {
                        parent.isChecked = false;
                        parent = parent.p;
                    }
                }
                if(bedoreDevBean != null && devBean.isChecked){
                    bedoreDevBean.isChecked = devBean.getLocId().equals(bedoreDevBean.getPlocId());
                    checkChildren(bedoreDevBean);
                    devBean.isChecked = true;
                }
                if(devBean.isChecked){
                    bedoreDevBean =devBean;
                }else {
                    if(devBean.getPlocId().equals(bedoreDevBean.getLocId())){
                        bedoreDevBean.isChecked = false;
                        checkChildren(bedoreDevBean);
                    }
                    bedoreDevBean = null;
                }
                checkChildren(devBean);
            } else {
                devBean.isExpanded = !devBean.isExpanded;
            }
            notifyDataSetChanged();
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

    private void checkChildren(DevLocationBean devBean) {
        if (devBean.children != null) {
            for (int j = 0; j < devBean.children.size(); j++) {
                devBean.children.get(j).isChecked = devBean.isChecked;
                checkChildren(devBean.children.get(j));
            }
        }
    }

    private int getSize(DevLocationBean devRootBean) {
        if (devRootBean.children != null && devRootBean.isExpanded) {
            int i = 0;
            for ( DevLocationBean station : devRootBean.children) {
                i += getSize(station);
            }
            return ++i;
        } else {
            return 1;
        }
    }
    private static int pointer = 0;

    private static DevLocationBean position2Station(DevLocationBean devBean, final int pos) {
        if (pos == pointer)
            return devBean;
        if (devBean.children != null && devBean.isExpanded) {
            for (int j = 0; j < devBean.children.size(); j++) {
                DevLocationBean child = devBean.children.get(j);
                ++pointer; // 要操作同一个数
                child = position2Station(child, pos);
                if (child != null)
                    return child;
            }
        }
        return null;
    }
    public static ArrayList<DevLocationBean> collectCheckedLocation(DevLocationBean locationnBean, ArrayList<DevLocationBean> location) {
        if (locationnBean.isChecked) location.add(locationnBean);
        if (locationnBean.children != null) {
            for (DevLocationBean station : locationnBean.children) {
                collectCheckedLocation(station, location);
            }
        }
        return location;
    }
}
