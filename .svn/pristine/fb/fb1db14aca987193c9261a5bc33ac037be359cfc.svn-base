package com.huawei.solarsafe.view.maintaince.ivcurve;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.MyStationBean;
import com.huawei.solarsafe.bean.common.LogCallBack;
import com.huawei.solarsafe.bean.device.StationBean;
import com.huawei.solarsafe.model.maintain.ivcurve.IVcurveModelImple;
import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.presenter.maintaince.ivcurve.CreatIVNewTeskPresenter;
import com.huawei.solarsafe.presenter.maintaince.ivcurve.CreatIVNewTeskView;
import com.huawei.solarsafe.utils.FullyLinearLayoutManager;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.CustomScrollView;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.devicemanagement.HouseholdDataSettingActivity;
import com.huawei.solarsafe.view.report.MyCacheThreadPool;

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
 * Created by P00507
 * on 2017/7/19.
 */

public class CreatIVNewTeskActivity extends BaseActivity implements View.OnClickListener, CreatIVNewTeskView {
    private Button button;
    private CreatIVNewTeskPresenter presenter;
    public MyStationBean root;
    private RecyclerView recyclerView;
    private int pointer = 0;
    private SimpleItemAdapter adapter;
    private RadioGroup rgAutoMode;
    private RadioGroup rgClean;
    private RadioGroup rgScanModel;
    private LinearLayout llIrradiationStrength;
    private LinearLayout llIrradiationStrengthBack;
    private EditText etIrradiationStrength;
    private EditText etIrradiationStrengthBack;
    private Button cancelDiagosis;
    private Button startDiagosis;
    private CustomScrollView customScrollView;
    private StringBuffer sbId;
    private String ids;
    private int numId = 0;
    //选择设备的个数
    private TextView numDevData;
    private EditText taskName;
    private String taskNameString;
    //是否清洗组件 "1"表示已清洗，"2"表示未清洗
    private String cleanStyle = "2";
    //扫描模式
    private String scanPointNum = "64";
    //自动模式和手动模式的字段"0"表示自动，"1"表示手动
    private String isPredicted = "0";
    //平面辐照强度
    private String totalRadiation = "";
    //背面辐照强度
    private String batterySurfaceTemp = "";
    private static final String TAG = "CreatIVNewTeskActivity";
    private RelativeLayout titleRelative;
    private ReentrantLock lock;
    private HashMap<String,List<StationBean>> stationTree;
    private int threadCount=0;
    private com.huawei.solarsafe.utils.customview.AlertDialog alertDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.creat_iv_tesk_activity;
    }

    @Override
    protected void initView() {
        tv_left.setOnClickListener(this);
//        tv_title.setVisibility(View.VISIBLE);
        arvTitle.setText(getString(R.string.new_iv_scan_tesk));
//        tv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText(getString(R.string.operation_ideal));
        tv_right.setOnClickListener(this);
        titleRelative = (RelativeLayout) findViewById(R.id.rl_alarm_type_more);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) titleRelative.getLayoutParams();
        params.width = (int) getResources().getDimension(R.dimen.size_120dp);
        titleRelative.setLayoutParams(params);
        adapter = new SimpleItemAdapter();
        sbId = new StringBuffer();
        customScrollView = new CustomScrollView(this);
        recyclerView = (RecyclerView) findViewById(R.id.rv_ivcurve);
        numDevData = (TextView) findViewById(R.id.num_dev_data);
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    customScrollView.setScroll(false);
                }
            }
        });
        llIrradiationStrength = (LinearLayout) findViewById(R.id.ll_irradiation_strength);
        llIrradiationStrengthBack = (LinearLayout) findViewById(R.id.ll_irradiation_strength_back);
        etIrradiationStrength = (EditText) findViewById(R.id.tv_irradiation_strength);
        etIrradiationStrength.setFilters(new InputFilter[]{Utils.numberFilter(4)});
        etIrradiationStrengthBack = (EditText) findViewById(R.id.tv_irradiation_strength_back);
        etIrradiationStrengthBack.setFilters(new InputFilter[]{Utils.numberFilter(4)});
        cancelDiagosis = (Button) findViewById(R.id.button_cancel_diagosis);
        startDiagosis = (Button) findViewById(R.id.button_start_diagosis);
        cancelDiagosis.setOnClickListener(this);
        startDiagosis.setOnClickListener(this);
        taskName = (EditText) findViewById(R.id.et_tesk_name);
        taskName.setFilters(new InputFilter[]{Utils.getEmojiFilter()});//禁止表情输入
        rgAutoMode = (RadioGroup) findViewById(R.id.rg_auto_mode);
        rgAutoMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.auto_mode:
                        llIrradiationStrength.setVisibility(View.GONE);
                        llIrradiationStrengthBack.setVisibility(View.GONE);
                        isPredicted = "0";
                        break;
                    case R.id.manual_mode:
                        llIrradiationStrength.setVisibility(View.VISIBLE);
                        llIrradiationStrengthBack.setVisibility(View.VISIBLE);
                        isPredicted = "1";
                        break;

                }
            }
        });
        rgClean = (RadioGroup) findViewById(R.id.rg_clean);
        rgClean.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.not_clean:
                        cleanStyle = "2";
                        break;
                    case R.id.have_clean:
                        cleanStyle = "1";
                        break;
                }
            }
        });
        rgScanModel = (RadioGroup) findViewById(R.id.rg_scan_model);
        rgScanModel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.fast_scan:
                        scanPointNum = "64";
                        break;
                    case R.id.accurate_scan:
                        scanPointNum = "128";
                        break;
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CreatIVNewTeskPresenter();
        presenter.onViewAttached(this);
        downloadStationIVDevFile();
        if(!Utils.isZh(this)){
            changeTxTitleSize();
            tv_right.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        }
    }


    @Override
    public void getData(Object object) {
        final Map<String, String> stringMap = new HashMap<String, String>();
        stringMap.put("esnCodes", ids);
        stringMap.put("taskName", taskNameString);
        stringMap.put("cleanStyle", cleanStyle);
        stringMap.put("isPredicted", isPredicted);
        stringMap.put("scanPointNum", scanPointNum);
        stringMap.put("totalRadiation", totalRadiation);
        stringMap.put("batterySurfaceTemp", batterySurfaceTemp);
        String msg = (String) object;
        if ("noSetModuleInfo".equals(msg)) {
            dismissLoading();
            AlertDialog.Builder builder = new AlertDialog.Builder(CreatIVNewTeskActivity.this, R.style.MyDialogTheme);
            builder.setTitle(getString(R.string.hint));
            builder.setMessage(getString(R.string.no_set_module));
            builder.setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();
        } else if ("false".equals(msg)) {
            dismissLoading();
            AlertDialog.Builder builder1 = new AlertDialog.Builder(CreatIVNewTeskActivity.this, R.style.MyDialogTheme);
            builder1.setTitle(getString(R.string.hint));
            builder1.setMessage(getString(R.string.not_to_check));
            builder1.setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showLoading();
                    presenter.creatTaskIV(stringMap);
                }
            });
            builder1.setNegativeButton(getString(R.string.cancel_defect), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder1.show();
        } else if ("null".equals(msg)) {
            dismissLoading();
            ToastUtil.showMessage(getString(R.string.error_info_web));
        } else if ("CheckFailed".equals(msg)) {
            dismissLoading();
            ToastUtil.showMessage(getString(R.string.check_failed));
        } else if ("true".equals(msg)) {
            presenter.creatTaskIV(stringMap);
        } else if ("CreatIVNewTeskFailed".equals(msg)) {
            dismissLoading();
            ToastUtil.showMessage(getString(R.string.request_failed));
        } else if ("CreatIVNewTeskSuccess".equals(msg)) {
            dismissLoading();
            ToastUtil.showMessage(getString(R.string.creat_task_success));
            finish();
        }

    }

    @Override
    public void getDataFailed(String msg) {
        dismissLoading();
        ToastUtil.showMessage(msg);
    }

    @Override
    public void requestData() {

    }

    private class SimpleItemAdapter extends RecyclerView.Adapter<SimpleItemAdapter.ViewHolder>
            implements View.OnClickListener {
        @Override
        public SimpleItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SimpleItemAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_item_checked, parent, false));
        }

        @Override
        public void onBindViewHolder(SimpleItemAdapter.ViewHolder holder, int position) {
            pointer = 0; // caution: reset the pointer first !
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
                //防止点击chickbox时有位置变化
                holder.arrow.setVisibility(View.GONE);
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
            if ("Msg.&topdomain".equals(stationBean.name) || "托管域".equals(stationBean.name)) {
                holder.checkBox.setText(MyApplication.getContext().getString(R.string.topdomain));
            } else {
                holder.checkBox.setText(stationBean.name);
            }
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) holder.domainOrstation.getLayoutParams();
            lp.leftMargin = Utils.dp2Px(getApplication(), 20);
            MyStationBean parent = stationBean.p;
            while (parent != null) {
                parent = parent.p;
                lp.leftMargin += Utils.dp2Px(getApplication(), 20); // indent arrow's width
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
                //点击的是设备
                if ("DEVICE".equals(stationBean.getModel()) && stationBean.isChecked) {
                    numId++;
                } else if ("DEVICE".equals(stationBean.getModel()) && !stationBean.isChecked) {
                    numId--;
                }
                //点击的是父类
                checkChildren(stationBean);
                if (!stationBean.isChecked) {
                    MyStationBean parent = stationBean.p;
                    while (parent != null) {
                        parent.isChecked = false;
                        parent = parent.p;
                    }
                }
                if (numId > 25) {
                    stationBean.setChecked(false);
                    ((CheckBox) v).setChecked(false);
                    if ("DEVICE".equals(stationBean.getModel())) {
                        numId--;
                    } else {
                        checkChildrenFalse(stationBean);
                    }
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(CreatIVNewTeskActivity.this, R.style.MyDialogTheme);
                    builder1.setTitle(getString(R.string.hint));
                    builder1.setMessage(getString(R.string.more_dev));
                    builder1.setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder1.show();
                }
                numDevData.setText(numId + "");
            } else {
                // item view
                stationBean.isExpanded = !stationBean.isExpanded;
            }
            notifyDataSetChanged();
        }

        boolean myBoolean;

        private void checkChildren(MyStationBean r) {
            if (r.children != null) {
                for (int j = 0; j < r.children.size(); j++) {
                    myBoolean = r.children.get(j).isChecked;
                    r.children.get(j).isChecked = r.isChecked;
                    if ("DEVICE".equals(r.children.get(j).getModel())) {
                        if (r.isChecked && !myBoolean) {
                            numId++;
                        } else if (!r.isChecked) {
                            numId--;
                        }
                    } else {
                        checkChildren(r.children.get(j));
                    }
                }
            }
        }

        private void checkChildrenFalse(MyStationBean r) {
            if (r.children != null) {
                for (int j = 0; j < r.children.size(); j++) {
                    if ("DEVICE".equals(r.children.get(j).getModel())) {
                        numId--;
                    } else {
                        checkChildrenFalse(r.children.get(j));
                    }
                    r.children.get(j).setChecked(false);
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

    private MyStationBean position2Station(MyStationBean stationBean, final int pos) {
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

    private int getSize(MyStationBean stationBean) {
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

    public ArrayList<MyStationBean> collectCheckedStations(MyStationBean stationBean, ArrayList<MyStationBean> c) {
        if (stationBean == null) {
            return c;
        }
        if (stationBean.isChecked) c.add(stationBean);
        if (stationBean.children != null) {
            for (MyStationBean station : stationBean.children) {
                collectCheckedStations(station, c);
            }
        }
        return c;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_left:
                cancelStep();
                break;
            case R.id.tv_right:
                AlertDialog.Builder builder = new AlertDialog.Builder(CreatIVNewTeskActivity.this, R.style.MyDialogTheme);
                View view = LayoutInflater.from(this).inflate(R.layout.activity_operation_ideal, null);
                builder.setView(view);
                button = (Button) view.findViewById(R.id.operation_ideal_sure);
                final AlertDialog dialog = builder.show();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.button_cancel_diagosis:
                cancelStep();
                break;
            case R.id.button_start_diagosis:
                ArrayList<MyStationBean> myStationBeans = new ArrayList<>();
                myStationBeans = collectCheckedStations(root, myStationBeans);
                AlertDialog.Builder builder2 = new AlertDialog.Builder(CreatIVNewTeskActivity.this, R.style.MyDialogTheme);
                builder2.setTitle(getString(R.string.hint));
                builder2.setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                if (sbId.length() != 0) {
                    sbId.replace(0, sbId.length(), "");
                }
                for (MyStationBean s : myStationBeans) {
                    if ("DEVICE".equals(s.getModel())) {
                        sbId.append(s.getId() + ",");
                    }
                }
                taskNameString = taskName.getText().toString();
                if (TextUtils.isEmpty(taskNameString)) {
                    builder2.setMessage(getString(R.string.task_name));
                    builder2.show();
                    return;
                }
                if (sbId.length() == 0) {
                    builder2.setMessage(getString(R.string.please_select_device));
                    builder2.show();
                    return;
                }
                if ("1".equals(isPredicted)) {
                    totalRadiation = etIrradiationStrength.getText().toString().trim();
                    batterySurfaceTemp = etIrradiationStrengthBack.getText().toString().trim();
                    if (TextUtils.isEmpty(totalRadiation)) {
                        builder2.setMessage(getString(R.string.please_irradiation_strength));
                        builder2.show();
                        return;
                    }
                    if (TextUtils.isEmpty(batterySurfaceTemp)) {
                        builder2.setMessage(getString(R.string.please_irradiation_strength_back));
                        builder2.show();
                        return;
                    }
                    double totalR = Double.valueOf(totalRadiation);
                    double batteryS = Double.valueOf(batterySurfaceTemp);
                    if (totalR > 1500 || totalR < 0) {
                        builder2.setMessage(getString(R.string.irradiation_strength_data));
                        builder2.show();
                        return;
                    }
                    if (batteryS > 100 || batteryS < 0) {
                        builder2.setMessage(getString(R.string.irradiation_strength_back_data));
                        builder2.show();
                        return;
                    }
                }
                if ("0".equals(isPredicted)) {
                    totalRadiation = "";
                    batterySurfaceTemp = "";
                }
                ids = sbId.toString().substring(0, sbId.length() - 1);
                Map<String, String> map = new HashMap<>();
                map.put("esnCodes", ids);
                presenter.requestIVDevList(map);
                showLoading();
                break;
        }
    }

    private LoadingDialog loadingDialog;

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
            loadingDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        }
        loadingDialog.show();
    }

    public void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
    /**
     * 去下载IV曲线树结构的资源
     */
    private void downloadStationIVDevFile() {
        showLoading();
        stationTree = new HashMap<>();
        lock = new ReentrantLock();
        Map<String, String> userMap = new HashMap<>();
        NetRequest.getInstance().asynPostJson(NetRequest.IP + IVcurveModelImple.LISTDEV, userMap, new LogCallBack() {
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
                                if (!stationTree.containsKey("" + minPid)) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            dismissLoading();
                                        }
                                    });
                                    return;
                                }
                                MyStationBean station = new MyStationBean();
                                List<StationBean> treeData = stationTree.get(minPid + "");//获取树的根，没考虑多颗树的情况
                                station.id = treeData.get(0).getId();
                                station.pid = treeData.get(0).getPid();
                                station.sort = treeData.get(0).getSort();
                                station.name = treeData.get(0).getName();
                                station.model = treeData.get(0).getModel();
                                station.children = new ArrayList<>();
                                root = station;
                                handlerSecondTree(root);
                                lock.lock();
                                handler.sendEmptyMessage(0);
                            } catch (NullPointerException e) {
                                Log.e(TAG, "run: " + e.toString());
                            } finally {
                                if(lock.isLocked()){
                                    lock.unlock();
                                }
                            }
                        }
                    }).start();
                } catch (Exception e) {
                    Log.e("MyStationPickerActivity", "Exception" + e.toString());
                    dismissLoading();
                    ToastUtil.showMessage("error occurred");
                }
            }
        });
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
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(threadCount == 0){
                dismissLoading();
                recyclerView.setAdapter(adapter);
            }
        }
    };

    @Override
    public void onBackPressed() {
        cancelStep();
    }

    /**
     * 取消方法
     */
    public void cancelStep() {
        try {
            alertDialog = new com.huawei.solarsafe.utils.customview.AlertDialog(CreatIVNewTeskActivity.this).builder()
                    .setMsg(getString(R.string.cancel_save_str))
                    .setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.cancel), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.sure), false, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    });

            alertDialog.show();
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

    }
}
