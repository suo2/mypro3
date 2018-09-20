package com.huawei.solarsafe.view.stationmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.stationmagagement.DevInfo;
import com.huawei.solarsafe.bean.stationmagagement.DevInfoListBean;
import com.huawei.solarsafe.presenter.stationmanagement.NewDeviceAccessPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.view.BaseActivity;
import com.huawei.solarsafe.view.MainActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.huawei.solarsafe.bean.device.DevTypeConstant.MULTIPURPOSE_DEV_STR;

/**
 * <pre>
 *     author: Tzy
 *     time  : 2018/2/2
 *     desc  : 新接入设备界面
 * </pre>
 */

public class NewDeviceAccessActivity extends BaseActivity implements INewDeviceAccessView, View.OnClickListener {

    private NewDeviceAccessPresenter newDeviceAccessPresenter;
    private TextView tvMessageAlert;
    private CheckBox cbSelectAllDevice;
    private Button btnAddToNewPowerStation,btnAddToExistingPowerStation,btnCancel,btnConfirm;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private AllNewDeviceAdapter allNewDeviceAdapter;
    private ArrayList<DevInfo> checkedNewDevice;//被选中的新设备集合
    private ArrayList<DevInfo> allNewDevice;//全部设备集合
    private Intent intent;
    private String messageAlert;
    private LinearLayout llSelectAllDevice,llBtn;
    private View div;
    private LinearLayout llEmpty;
    private boolean isOneKeyOpenStation;//是否是一键开站
    List<String> rightsList;
    private static final String TAG = "NewDeviceAccessActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkedNewDevice=new ArrayList<>();
        allNewDevice=new ArrayList<>();
        intent=getIntent();
        if (intent != null){
            try {
                Bundle bundle=intent.getExtras();
                ArrayList<DevInfo> tempArrayList= (ArrayList<DevInfo>) bundle.getSerializable("checkedNewDevice");
                if (tempArrayList!=null){
                    checkedNewDevice.addAll(tempArrayList);
                }
                isOneKeyOpenStation=bundle.getBoolean("isOneKeyOpenStation",false);
            } catch (Exception e){
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        }
        if (isOneKeyOpenStation){//判断是否是通过首页"新设备接入"开站

            //获取用户权限
            rightsList = new ArrayList<>();
            String strRights = LocalData.getInstance().getRightString();
            rightsList = Utils.stringToList(strRights);
            if (MainActivity.RIGHTS_LIST_SWITCH) {
                //根据权限列表，显示有权限的模块
                if (rightsList.contains("app_plantManagement")) {//判断用户是否有电站管理权限
                    btnAddToNewPowerStation.setVisibility(View.VISIBLE);
                } else {
                    btnAddToNewPowerStation.setVisibility(View.GONE);
                }
            }

            btnAddToExistingPowerStation.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.GONE);
            btnConfirm.setVisibility(View.GONE);
        }else{
            btnAddToNewPowerStation.setVisibility(View.GONE);
            btnAddToExistingPowerStation.setVisibility(View.GONE);
            btnCancel.setVisibility(View.VISIBLE);
            btnConfirm.setVisibility(View.VISIBLE);
        }
        //初始化控制器
        newDeviceAccessPresenter=new NewDeviceAccessPresenter();
        //绑定视图
        newDeviceAccessPresenter.onViewAttached(this);
        allNewDeviceAdapter=new AllNewDeviceAdapter(NewDeviceAccessActivity.this);
        initList();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_device_access;
    }

    @Override
    protected void initView() {
        tv_left.setText(getResources().getString(R.string.back));
        tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        arvTitle.setText(getResources().getString(R.string.new_device_access));
        tv_right.setVisibility(View.GONE);
        llSelectAllDevice= (LinearLayout) findViewById(R.id.llSelectAllDevice);
        llSelectAllDevice.setOnClickListener(this);
        tvMessageAlert= (TextView) findViewById(R.id.tvMessageAlert);
        cbSelectAllDevice= (CheckBox) findViewById(R.id.cbSelectAllDevice);
        btnAddToNewPowerStation= (Button) findViewById(R.id.btnAddToNewPowerStation);
        btnAddToExistingPowerStation= (Button) findViewById(R.id.btnAddToExistingPowerStation);
        btnAddToNewPowerStation.setOnClickListener(this);
        btnAddToExistingPowerStation.setOnClickListener(this);
        btnCancel= (Button) findViewById(R.id.btnCancel);
        btnConfirm= (Button) findViewById(R.id.btnConfirm);
        btnCancel.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        llBtn= (LinearLayout) findViewById(R.id.llBtn);
        div=findViewById(R.id.div);
        llEmpty= (LinearLayout) findViewById(R.id.llEmpty);
        messageAlert=getResources().getString(R.string.new_device_access_message_alert);
    }

    /**
     * 初始化列表
     */
    private void initList(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(allNewDeviceAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        newDeviceAccessPresenter.doGetNewDeviceInfos();
    }

    /**
     * 获取新接入设备信息回调
     * @param baseEntity 回调的数据
     */
    @Override
    public void getNewDeviceInfos(BaseEntity baseEntity) {
        if (baseEntity!=null){
            DevInfoListBean devInfoListBean= (DevInfoListBean) baseEntity;
            allNewDevice=devInfoListBean.getData();
            String[] tempStrs=messageAlert.split("X");
            tvMessageAlert.setText(tempStrs[0]+allNewDevice.size()+tempStrs[1]);
            allNewDeviceAdapter.refreshData(allNewDevice);
            //设置全选按钮状态
            setCbSelectAllDeviceState();
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            div.setVisibility(View.VISIBLE);
            llBtn.setVisibility(View.VISIBLE);
            llEmpty.setVisibility(View.GONE);
        }else{
            checkedNewDevice.clear();
            allNewDevice.clear();
            allNewDeviceAdapter.refreshData(allNewDevice);
            //设置全选按钮状态
            setCbSelectAllDeviceState();
            swipeRefreshLayout.setVisibility(View.GONE);
            div.setVisibility(View.GONE);
            llBtn.setVisibility(View.GONE);
            llEmpty.setVisibility(View.VISIBLE);
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    /**
     * 实现点击事件
     * @param view 发生点击事件控件
     */
    @Override
    public void onClick(View view) {
        int viewId=view.getId();
        Bundle bundle=new Bundle();
        bundle.putSerializable("checkedNewDevice",checkedNewDevice);
        switch (viewId){
            case R.id.btnAddToNewPowerStation://添加到新的电站
                if (checkedNewDevice.isEmpty()){
                    ToastUtil.showMessage(getResources().getString(R.string.please_select_device));
                }else if(isCheckedCommunicationManager()){//不支持通管机开站
                    ToastUtil.showMessage(getString(R.string.do_not_support_open_station));
                }else{
                    bundle.putBoolean("isOneKeyOpenStation",true);
                    Intent intent1=new Intent(NewDeviceAccessActivity.this,CreateStationActivity.class);
                    intent1.putExtras(bundle);
                    startActivity(intent1);
                    checkedNewDevice.clear();
                }
                break;
            case R.id.btnAddToExistingPowerStation://添加到现有电站
                if (checkedNewDevice.isEmpty()){
                    ToastUtil.showMessage(getResources().getString(R.string.please_select_device));
                }else{
                    bundle.putBoolean("isOneKeyOpenStation",true);
                    Intent intent2=new Intent(NewDeviceAccessActivity.this,SingerSelectPowerStationActivity.class);
                    intent2.putExtras(bundle);
                    startActivity(intent2);
                    checkedNewDevice.clear();
                }
                break;
            case R.id.btnCancel://取消
                finish();
                break;
            case R.id.btnConfirm://确定
                if(isCheckedCommunicationManager()){//不支持通管机开站
                    ToastUtil.showMessage(getString(R.string.do_not_support_open_station));
                }else{
                    intent.putExtras(bundle);
                    setResult(RESULT_OK,intent);
                    finish();
                }
                break;
            case R.id.llSelectAllDevice:
                if (cbSelectAllDevice.isChecked()){
                    cbSelectAllDevice.setChecked(false);
                    checkedNewDevice.clear();
                }else{
                    cbSelectAllDevice.setChecked(true);
                    checkedNewDevice.clear();
                    checkedNewDevice.addAll(allNewDeviceAdapter.getData());
                }
                allNewDeviceAdapter.notifyDataSetChanged();
                break;
        }
    }

    class AllNewDeviceAdapter extends RecyclerView.Adapter<AllNewDeviceAdapter.MViewHolder>{
        Context context;
        ArrayList<DevInfo> arrayList;

        public AllNewDeviceAdapter(Context context){
            this.context=context;
            arrayList=new ArrayList<>();
        }

        @Override
        public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_new_device_access_activity_all_device,parent,false);
            return new MViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MViewHolder holder, int position) {
            final DevInfo tempDevInfo=arrayList.get(position);
            holder.cbNewDevice.setText(tempDevInfo.getDev().getBusiName());
            //初始化item的选中状态
            if (checkedNewDevice.size()==0){
                holder.cbNewDevice.setChecked(false);
            }else{
                for (DevInfo devInfo:checkedNewDevice){
                    if (tempDevInfo.getDev().getEsnCode().equals(devInfo.getDev().getEsnCode())){
                        holder.cbNewDevice.setChecked(true);
                        break;
                    }else{
                        holder.cbNewDevice.setChecked(false);
                    }
                }
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.cbNewDevice.isChecked()){
                        holder.cbNewDevice.setChecked(false);
                        Iterator<DevInfo> devInfoIterator=checkedNewDevice.iterator();
                        while (devInfoIterator.hasNext()){
                            DevInfo devInfo=devInfoIterator.next();
                            if (tempDevInfo.getDev().getEsnCode().equals(devInfo.getDev().getEsnCode())){
                                devInfoIterator.remove();
                            }
                        }
                    }else{
                        holder.cbNewDevice.setChecked(true);
                        checkedNewDevice.add(tempDevInfo);
                    }
                    setCbSelectAllDeviceState();
                }
            });
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public ArrayList<DevInfo> getData(){
            return arrayList;
        }

        class MViewHolder extends RecyclerView.ViewHolder {
            CheckBox cbNewDevice;

            public MViewHolder(View itemView) {
                super(itemView);

                cbNewDevice= (CheckBox) itemView.findViewById(R.id.cbNewDevice);
            }
        }

        public void refreshData(ArrayList<DevInfo> newData){
            arrayList.clear();
            arrayList.addAll(newData);
            notifyDataSetChanged();
        }
    }

    /**
     * 设置全选按钮状态
     */
    public void  setCbSelectAllDeviceState(){
        //判断全选按钮状态
        if (checkedNewDevice.size()!=0 && checkedNewDevice.size()==allNewDevice.size()){
            cbSelectAllDevice.setChecked(true);
        }else{
            cbSelectAllDevice.setChecked(false);
        }
    }

    /**
     * 是否选中通管机
     * @return
     */
    public boolean isCheckedCommunicationManager(){
        for (DevInfo devInfo:checkedNewDevice){
            if (MULTIPURPOSE_DEV_STR.equals(devInfo.getDev().getDevTypeId().toString())){
                return true;
            }
        }
        return false;
    }
}
