package com.huawei.solarsafe.view.report;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.MyStationBean;
import com.huawei.solarsafe.bean.common.LogCallBack;
import com.huawei.solarsafe.bean.device.StationBean;
import com.huawei.solarsafe.model.report.IReportModel;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

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
    protected static MyStationBean root;
    private RecyclerView recyclerView;
    private boolean isFirst = true;
    private static final String TAG = "MyStationPickerActivity";
    private HashMap<String,List<StationBean>> stationTree;
    private int threadCount=0;
    private ReentrantLock lock;

    @Override
    protected void initView() {
        tv_title.setText(getString(R.string.station_choice_notice));
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        stationTree = new HashMap<>();
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(root != null){
                    ArrayList<MyStationBean> myStationBeans = new ArrayList<>();
                    myStationBeans = collectCheckedStations(root, myStationBeans);
                    StringBuffer sbId = new StringBuffer();
                    String ids = "";
                    for (MyStationBean s : myStationBeans) {
                        if ("DEV_1".equals(s.getModel()) || "DEV_14".equals(s.getModel()) || "DEV_38".equals(s.getModel())) {
                            sbId.append(s.getId() + ",");
                        }
                    }
                    //宋平修改  58543
                    if (sbId.length() == 0) {
                        ToastUtil.showMessage(getResources().getString(R.string.please_select_device));
                        return;
                    }
                    ids = sbId.toString().substring(0, sbId.length() - 1);
                    Intent intent = new Intent();
                    intent.putExtra("ids",ids);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        tv_title.setText(getString(R.string.choose_inverter));
        Intent intent = getIntent();
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                isFirst = intent.getBooleanExtra("isFirst", true);
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        if (isFirst) {
            requestStationList();
        } else {
            recyclerView.setAdapter(new SimpleItemAdapter());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_station_picker_my;
    }

    private void requestStationList() {
        showLoading();
        lock = new ReentrantLock();
        Map<String, String> userMap = new HashMap<>();
        userMap.put("sId", "");
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        userMap.put("userId", String.valueOf(GlobalConstants.userId));
        userMap.put("devTypeIds", "1,14,38");//组串、集中、户用
        NetRequest.getInstance().asynPostJson(NetRequest.IP + IReportModel.URL_DEV_CHOICE_REPORT, userMap, new LogCallBack() {
            @Override
            protected void onFailed(Exception e) {
                dismissLoading();
                ToastUtil.showMessage(MyApplication.getContext().getString(R.string.net_error));
            }

            @Override
            protected void onSuccess(String data) {
                try {
                    final JSONArray jsonArray = new JSONObject(data).optJSONArray("data");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                int minPid = handlerStationData(jsonArray);
                                if(!stationTree.containsKey(""+minPid)){
                                    runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              dismissLoading();
                                          }
                                    });
                                    return;
                                }
                                MyStationBean station = new MyStationBean();
                                List<StationBean> treeData = stationTree.get(minPid+"");//获取树的根，没考虑多颗树的情况
                                station.id = treeData.get(0).getId();
                                station.pid =treeData.get(0).getPid();
                                station.sort =treeData.get(0).getSort();
                                station.name = treeData.get(0).getName();
                                station.model = treeData.get(0).getModel();
                                station.children = new ArrayList<>();
                                root = station;
                                handlerSecondTree(root);
                                lock.lock();
                                handler.sendEmptyMessage(0);
                            } catch (NullPointerException e) {
                                Log.e(TAG, "run: " + e.toString() );
                            }finally {
                                if(lock.isLocked()){
                                    lock.unlock();
                                }

                            }
                        }
                    }).start();

                } catch (Exception e) {
                    Log.e("MyStationPickerActivity","Exception" + e.toString());
                    dismissLoading();
                    ToastUtil.showMessage("error occurred");
                }
            }
        });
    }

    private void findChildren(MyStationBean parent) throws JSONException {
        if(!stationTree.containsKey(parent.id)){
            return;
        }
        List<StationBean> children = stationTree.get(parent.id);
        for (int i = 0; i < children.size(); i++) {
            if(children.get(i).getPid().equals(parent.getId())){
                MyStationBean childrenStation = new MyStationBean();
                childrenStation.id = children.get(i).getId();
                childrenStation.pid = children.get(i).getPid();
                childrenStation.sort = children.get(i).getSort();
                childrenStation.name = children.get(i).getName();
                childrenStation.model = children.get(i).getModel();
                childrenStation.p = parent;
                if(parent.children == null){
                    parent.children = new ArrayList<>();
                }
                parent.children.add(childrenStation);
                findChildren(childrenStation);
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
        if (stationBean.isChecked) c.add(stationBean);
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
                holder.domainOrstation.setVisibility(View.VISIBLE);
                holder.domainOrstation.setImageResource(R.drawable.domain_station_check);
            } else if ("DOMAIN".equals(stationBean.model) || "DOMAIN_NOT".equals(stationBean.model)) {
                holder.domainOrstation.setVisibility(View.VISIBLE);
                holder.domainOrstation.setImageResource(R.drawable.domain_check);
            } else {
                holder.domainOrstation.setVisibility(View.INVISIBLE);
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
    private int handlerStationData(final JSONArray jsonArray){
        int minPid=Integer.MAX_VALUE;
        if(jsonArray == null){
            dismissLoading();
            return minPid;
        }
        Type type = new TypeToken<List<StationBean>>() {
        }.getType();
        Gson gson = new Gson();
        List<StationBean> stationList = gson.fromJson(jsonArray.toString(),type);
        if(stationList == null || stationList.size()==0){
            dismissLoading();
            return minPid;
        }

        for(StationBean stationBean:stationList){
            String pid = stationBean.getPid();
            if(stationTree.containsKey(pid)){
                stationTree.get(pid).add(stationBean);
            }else{
                List<StationBean> listData = new ArrayList<>();
                listData.add(stationBean);
                stationTree.put(pid,listData);
            }
            try {
                int pidValue =Integer.valueOf(pid);
                if(pidValue<minPid){
                    minPid = pidValue;
                }
            }catch (NumberFormatException e){
                continue;
            }
        }
        return minPid;
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
    private void handlerSecondTree( final MyStationBean parent){

        if(!stationTree.containsKey(parent.id)){
            return;
        }
        final List<StationBean> children = stationTree.get(parent.id);
        for (int i = 0; i < children.size(); i++) {
            final int position = i;
                if(children.get(position).getPid().equals(parent.getId())){
                    MyStationBean childrenStation = new MyStationBean();
                    childrenStation.id = children.get(position).getId();
                    childrenStation.pid = children.get(position).getPid();
                    childrenStation.sort = children.get(position).getSort();
                    childrenStation.name = children.get(position).getName();
                    childrenStation.model = children.get(position).getModel();
                    childrenStation.p = parent;
                    if(parent.children == null){
                        parent.children = new ArrayList<>();
                    }
                    parent.children.add(childrenStation);
                    int count;
                    if(stationTree.containsKey(childrenStation.id) && (count = stationTree.get(childrenStation.id).size())>10){
                        threadCount += count;
                        handlerThirdTree(childrenStation);
                    }else{
                        handlerSecondTree(childrenStation);
                    }

                }
        }
    }
    private void handlerThirdTree( final MyStationBean parent){
        if(!stationTree.containsKey(parent.id)){
            return;
        }
        MyCacheThreadPool myCacheThreadPool = MyCacheThreadPool.createMyMyCacheThreadPool();
        final List<StationBean> children = stationTree.get(parent.id);
        for (int i = 0; i < children.size(); i++) {
            final int position = i;
            if (children.get(position).getPid().equals(parent.getId())) {
                final MyStationBean childrenStation = new MyStationBean();
                childrenStation.id = children.get(position).getId();
                childrenStation.pid = children.get(position).getPid();
                childrenStation.sort = children.get(position).getSort();
                childrenStation.name = children.get(position).getName();
                childrenStation.model = children.get(position).getModel();
                childrenStation.p = parent;
                if (parent.children == null) {
                    parent.children = new ArrayList<>();
                }
                parent.children.add(childrenStation);
                myCacheThreadPool.startExecute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            findChildren(childrenStation);
                            lock.lock();
                            threadCount --;
                            handler.sendEmptyMessage(0);
                        } catch (JSONException e) {
                            Log.e(TAG, "run: " + e.getMessage());
                        }finally {
                            lock.unlock();
                        }
                    }
                });
            }
        }
    }

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(threadCount == 0){
                dismissLoading();
                recyclerView.setAdapter(new SimpleItemAdapter());
            }
        }
    };
}