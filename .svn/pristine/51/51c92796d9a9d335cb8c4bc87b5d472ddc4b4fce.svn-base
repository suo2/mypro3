package com.huawei.solarsafe.view.maintaince.ivcurve;

import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.ivcurve.StationFaultList;
import com.huawei.solarsafe.bean.ivcurve.StationFaultListInfo;
import com.huawei.solarsafe.presenter.maintaince.ivcurve.CreatIVNewTeskPresenter;
import com.huawei.solarsafe.presenter.maintaince.ivcurve.CreatIVNewTeskView;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.view.CustomViews.MyBandListView;
import com.huawei.solarsafe.view.CustomViews.MyHorizontalScrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.huawei.solarsafe.R.id.ll_start_compare;

/**
 * Created by p00507
 * on 2017/7/20.
 */

public class StationFaultListFragment extends Fragment implements View.OnClickListener,CreatIVNewTeskView {
    private CreatIVNewTeskPresenter presenter;
    private MyHorizontalScrollView titleHorizontalScrollView;
    private MyHorizontalScrollView contentHorizontalScrollView;
    private MyBandListView listViewCode;
    private MyBandListView listViewContent;
    private CodeAdapter codeAdapter;
    private ContentAdapter contentAdapter;
    private List<String> codeList;
    private List<StationFaultListInfo> listInfo;
    private List<StationFaultListInfo> listInfoFialed;
    private ArrayList<StationFaultListInfo> toComparedlist;
    private int page = 1;
    private int pageSize = 20;
    private String taskId;
    private String stationCode;
    private StationFaultListInfo centerValue;
    //判断是否显示页面下方的开始对比按钮
    public boolean isToCompared = false;
    //用于记录选择的数量
    private int tureNum = 0;
    private TextView haveChoiceNum;
    private Button startCompared;
    private List<Boolean> boolens;
    private List<Integer> swichs = new ArrayList<>();
    private TextView checkboxOneline;
    private View lineOneline;
    private LinearLayout startCompare;
    private LinearLayout liftListLl;
    private PullToRefreshScrollView pullToRefreshScrollView;
    private int totalNum;
    private int tag1 = -1;
    private boolean otherFragment = false;
    private static final String TAG = "StationFaultListFragmen";

    public static StationFaultListFragment newInstance() {
        StationFaultListFragment fragment = new StationFaultListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CreatIVNewTeskPresenter();
        presenter.onViewAttached(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_station_fault_list, container, false);
        titleHorizontalScrollView = (MyHorizontalScrollView) view.findViewById(R.id.fault_title);
        contentHorizontalScrollView = (MyHorizontalScrollView) view.findViewById(R.id.fault_content_horsv);
        titleHorizontalScrollView.setmView(contentHorizontalScrollView);
        contentHorizontalScrollView.setmView(titleHorizontalScrollView);
        listViewCode = (MyBandListView) view.findViewById(R.id.fault_lift_listview);
        listViewContent = (MyBandListView) view.findViewById(R.id.fault_right_container_listview);
        haveChoiceNum = (TextView) view.findViewById(R.id.have_choice_num);
        startCompared = (Button) view.findViewById(R.id.start_compared);
        startCompare = (LinearLayout) view.findViewById(ll_start_compare);
        checkboxOneline = (TextView) view.findViewById(R.id.checkbox_oneline);
        lineOneline = view.findViewById(R.id.line_oneline);

        pullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.ivcurve_refresh_scrollview);
        pullToRefreshScrollView.getLoadingLayoutProxy(false, true);
        //BOTH 可以上拉、可以下拉
        pullToRefreshScrollView.setMode(PullToRefreshBase.Mode.BOTH);

        // 下拉刷新
        pullToRefreshScrollView.getLoadingLayoutProxy(true, false).setPullLabel(getString(R.string.pull_refresh));
        pullToRefreshScrollView.getLoadingLayoutProxy(true, false).setRefreshingLabel(getString(R.string.updating));
        pullToRefreshScrollView.getLoadingLayoutProxy(true, false).setReleaseLabel(getString(R.string.release_load));
        // 上拉加载更多，分页加载
        pullToRefreshScrollView.getLoadingLayoutProxy(false, true).setPullLabel(getString(R.string.load_more));
        pullToRefreshScrollView.getLoadingLayoutProxy(false, true).setRefreshingLabel(getString(R.string.updating));
        pullToRefreshScrollView.getLoadingLayoutProxy(false, true).setReleaseLabel(getString(R.string.release_load));
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                try {
                    NotificationManager manager = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
                    manager.cancelAll();
                } catch (Exception e) {
                    Log.e(TAG, "no notification NULLPOINTEXCEPTION", e);
                }
                page = 1;
                tag1 = 1;
                otherFragment = false;
                requestData();
                titleHorizontalScrollView.scrollTo(0,0);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                page ++;
                tag1 = 1;
                otherFragment = false;
                requestData();

            }
        });

        liftListLl = (LinearLayout) view.findViewById(R.id.lift_list_ll);
        startCompared.setOnClickListener(this);
        boolens = new ArrayList<>();
        codeList = new ArrayList<>();
        listInfo = new ArrayList<>();
        listInfoFialed = new ArrayList<>();
        toComparedlist = new ArrayList<>();
        initData();
        codeAdapter = new CodeAdapter();
        contentAdapter = new ContentAdapter();
        listViewContent.setAdapter(contentAdapter);
        return view;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public void setOtherFragment(boolean otherFragment) {
        this.otherFragment = otherFragment;
    }

    private void initData() {
        codeList.add(getString(R.string.centure_value));
        for (int i = 0; i < 10; i++) {
            codeList.add("0" +i);
        }
        for (int j = 10; j < 1000; j++) {
            codeList.add(j + "");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_compared:
                toComparedlist.clear();
                if(swichs.size() == 0){
                    ToastUtil.showMessage(getString(R.string.select_string));
                    return;
                }
                for (int i = 0; i < swichs.size(); i++) {
                    if(swichs.get(i) == 0){
                        toComparedlist.add(centerValue);
                    }else {
                        toComparedlist.add(listInfo.get(swichs.get(i)-1));
                    }
                }
                Intent intent = new Intent(getActivity(),IvCurveComparedActivity.class);
                intent.putExtra("toComparedlist" ,toComparedlist);
                intent.putExtra("taskId",taskId);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void getData(Object object) {
        dismissLoading();
        pullToRefreshScrollView.onRefreshComplete();
        StationFaultList stationFaultList = (StationFaultList) object;
        if(page == 1){
            listInfo.clear();
        }
        if(stationFaultList.getListInfos() == null){
            return;
        }
        totalNum = stationFaultList.getTotal();
        if(listInfo.size() >= totalNum){
            Toast.makeText(getActivity(), R.string.no_more_data, Toast.LENGTH_SHORT).show();
            page --;
            return;
        }
        listInfoFialed.clear();
        List<StationFaultListInfo> listFault = stationFaultList.getListInfos();
        for (int i = 0; i < listFault.size(); i++) {
            //id为0，表示中值，单独提出来
            if("0".equals(listFault.get(i).getId()+"")){
                centerValue = listFault.get(i);
                listFault.remove(i);
                i--;
            }
            //errorCode为20000时表示扫描失败，也单独提出来，和中值类似
            if("20000".equals(listFault.get(i).getErrorCode()+"")){
                listInfoFialed.add(listFault.get(i));
                listFault.remove(i);
                i--;
            }
        }

        if(page == 1 && listInfoFialed.size() != 0){
            listInfo.addAll(listInfoFialed);
        }
        if(listFault.size() != 0){
            listInfo.addAll(listFault);
        }

        if(listInfo.size() > 5){
            showButton.showButton(true);
        }else {
            showButton.showButton(false);
        }
        contentAdapter.notifyDataSetChanged();
        codeAdapter.setData();
        listViewCode.setAdapter(codeAdapter);
        codeAdapter.notifyDataSetChanged();
        showOrDissmiss();
    }

    @Override
    public void getDataFailed(String msg) {
        dismissLoading();
        ToastUtil.showMessage(msg);
    }

    /**
     * 是否显示左边的选择框
     */
    public void showOrDissmiss(){
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) liftListLl.getLayoutParams();
        if(isToCompared){
            params.width = Utils.dp2Px(getActivity(), 95);
            startCompare.setVisibility(View.VISIBLE);
            checkboxOneline.setVisibility(View.VISIBLE);
            lineOneline.setVisibility(View.VISIBLE);
        }else {
            params.width = Utils.dp2Px(getActivity(), 52);
            startCompare.setVisibility(View.GONE);
            checkboxOneline.setVisibility(View.GONE);
            lineOneline.setVisibility(View.GONE);
        }
        liftListLl.setLayoutParams(params);
        codeAdapter.setData();
        codeAdapter.notifyDataSetChanged();
    }

    @Override
    public void requestData() {
        if(tag1 == -1){
            showLoading();
        }
        tag1 = -1;
        if(otherFragment){
            page = 1;
        }
        Map<String,String> map = new HashMap<>();
        map.put("stationCode",stationCode);
        map.put("page",page+"");
        map.put("pageSize",pageSize+"");
        map.put("taskId",taskId);
        presenter.requestGetStationFaultList(map);
    }

    public class CodeAdapter extends BaseAdapter{

        public void setData(){
            boolens.clear();
            swichs.clear();
            tureNum = 0;
            haveChoiceNum.setText(getString(R.string.selected_dev)+tureNum+"/"+(listInfo == null ? 0 : listInfo.size()));
//            haveChoiceNum.setText(String.format(getString(R.string.have_choice_num),tureNum));
            for (int i = 0; i < listInfo.size()+1; i++) {
                boolens.add(false);
            }
        }

        @Override
        public int getCount() {
            return listInfo.size()+1;
        }

        @Override
        public Object getItem(int position) {
            return codeList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHold = null;
            if(convertView == null){
                viewHold = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.code_listview_item,parent,false);
                viewHold.checkBox = (CheckBox) convertView.findViewById(R.id.code_irem_check);
                viewHold.lineView = convertView.findViewById(R.id.view_checkbox);
                viewHold.tvCode = (TextView) convertView.findViewById(R.id.tv_code);
                viewHold.ll_code_irem_check = (LinearLayout) convertView.findViewById(R.id.ll_code_irem_check);
                convertView.setTag(viewHold);
            } else {
                viewHold = (ViewHolder) convertView.getTag();
            }
            if(isToCompared){
                viewHold.lineView.setVisibility(View.VISIBLE);
                viewHold.ll_code_irem_check.setVisibility(View.VISIBLE);
            }else {
                viewHold.lineView.setVisibility(View.GONE);
                viewHold.ll_code_irem_check.setVisibility(View.GONE);
            }
            viewHold.tvCode.setText(codeList.get(position));
            //getView()方法会重复调用多次，导致 position == 0会出现多次，通过parent.getChildCount() == position来判断是否是真实的potion
            if(parent.getChildCount() == position && position == 0 && centerValue != null){
                //默认勾选中值，且不能取消
                if(!"null".equals(centerValue.getIsErrorExists()+"") && !swichs.contains(position)) {
                    tureNum++;
                    boolens.set(position, true);
                    viewHold.checkBox.setChecked(true);
                    viewHold.checkBox.setEnabled(false);
                    swichs.add(position);
                }
            }

            if (position==0){
                viewHold.checkBox.setVisibility(View.GONE);
            }else{
                if ("20000".equals(listInfo.get(position -1).getErrorCode())
                        || "10012".equals(listInfo.get(position -1).getErrorCode())
                        || "10013".equals(listInfo.get(position -1).getErrorCode())
                        || "10014".equals(listInfo.get(position -1).getErrorCode())
                        || "10002".equals(listInfo.get(position -1).getErrorCode())){
                    viewHold.checkBox.setVisibility(View.GONE);
                }else{
                    viewHold.checkBox.setVisibility(View.VISIBLE);
                }
            }

            final ViewHolder finalViewHold = viewHold;
            viewHold.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position == 0 ){
                        if("null".equals(centerValue.getIsErrorExists()+"")){
                            finalViewHold.checkBox.setChecked(false);
                            boolens.set(position, false);
                        }
                    }else {
                        if(tureNum < 8 && !boolens.get(position)){
                            tureNum++;
                            boolens.set(position, true);
                            finalViewHold.checkBox.setChecked(true);
                            swichs.add(position);
                        }else if(boolens.get(position)|| tureNum == 8) {
                            boolens.set(position, false);
                            finalViewHold.checkBox.setChecked(false);
                            for (int j = 0; j < swichs.size(); j++) {
                                if (swichs.get(j) == position) {
                                    swichs.remove(j);
                                    tureNum--;
                                }
                            }
                            finalViewHold.checkBox.setChecked(false);
                        }
                    }
                    haveChoiceNum.setText(getString(R.string.selected_dev)+tureNum+"/"+(listInfo == null ? 0 : listInfo.size()));
                }
            });
            haveChoiceNum.setText(getString(R.string.selected_dev)+tureNum+"/"+(listInfo == null ? 0 : listInfo.size()));
            //解决上下滑动勾选错乱的问题
            if(boolens.size() != 0){
                if (boolens.get(position)) {
                    viewHold.checkBox.setChecked(true);
                } else {
                    viewHold.checkBox.setChecked(false);
                }
            }
            return convertView;
        }
        class ViewHolder {
            CheckBox checkBox;
            View lineView;
            TextView tvCode;
            LinearLayout ll_code_irem_check;
        }
    }
    public class ContentAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return listInfo.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            if(position == 0){
                return centerValue;
            }
            return listInfo.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ContentViewHold contentHold = null;
            if(convertView == null){
                contentHold = new ContentViewHold();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.content_listview_item,parent,false);
                contentHold.tvCodeLeft = (TextView) convertView.findViewById(R.id.tv_code_left);
                contentHold.tvExceptionType = (TextView) convertView.findViewById(R.id.exception_type);
                contentHold.tvInverterName = (TextView) convertView.findViewById(R.id.inverter_name);
                contentHold.tvZuchuan = (TextView) convertView.findViewById(R.id.zuchuan_contentlist);
                contentHold.tvOptimizerCode = (TextView) convertView.findViewById(R.id.optimizer_code);
                contentHold.tvVoc = (TextView) convertView.findViewById(R.id.voc);
                contentHold.tvIsc = (TextView) convertView.findViewById(R.id.isc);
                contentHold.tvFf = (TextView) convertView.findViewById(R.id.ff);
                contentHold.tvPmax = (TextView) convertView.findViewById(R.id.p_max);
                contentHold.tvVm = (TextView) convertView.findViewById(R.id.vm);
                contentHold.tvIm = (TextView) convertView.findViewById(R.id.im);
                contentHold.tvVmVoc = (TextView) convertView.findViewById(R.id.vm_voc);
                contentHold.tvImIsc = (TextView) convertView.findViewById(R.id.im_isc);
                convertView.setTag(contentHold);
            }else {
                contentHold = (ContentViewHold) convertView.getTag();
            }
            if(position == 0){
                contentHold.tvCodeLeft.setText("--");
                contentHold.tvExceptionType.setText("--");

                initCenterValue(contentHold);

            }else {
                final StationFaultListInfo stationFaultListInfo = listInfo.get(position -1);
                if("20000".equals(stationFaultListInfo.getErrorCode() + "") || TextUtils.isEmpty(stationFaultListInfo.getErrorCode())){
                    contentHold.tvExceptionType.setText(getString(R.string.scan_failed));
                    contentHold.tvExceptionType.setTextColor(getResources().getColor(R.color.actionsheet_red));
                    contentHold.tvCodeLeft.setText("--");
                    contentHold.tvCodeLeft.setClickable(false);
                    contentHold.tvCodeLeft.getPaint().setFlags(0); //取消下划线
                }else {
                    if("10000".equals(stationFaultListInfo.getErrorCode() + "")){
                        contentHold.tvExceptionType.setText(MyApplication.getContext().getResources().getString(R.string.onLine));
                    }else {
                        contentHold.tvExceptionType.setText(stationFaultListInfo.getErrorCode());
                    }
                    contentHold.tvCodeLeft.setText(getString(R.string.scan));
                    contentHold.tvCodeLeft.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                    contentHold.tvCodeLeft.getPaint().setAntiAlias(true);//抗锯齿
                    contentHold.tvCodeLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(),IvcurveInverterInfoActivity.class);
                            intent.putExtra("taskId",taskId);
                            intent.putExtra("invertEsn",stationFaultListInfo.getInveterEsn());
                            intent.putExtra("id",stationFaultListInfo.getId());
                            intent.putExtra("stringId",stationFaultListInfo.getPvIndex());
                            startActivity(intent);
                        }
                    });
                }
                contentHold.tvInverterName.setText(stationFaultListInfo.getInveterName());
                contentHold.tvZuchuan.setText(stationFaultListInfo.getPvIndex());
                contentHold.tvOptimizerCode.setText(stationFaultListInfo.getPromtCode());
                if("null".equals(stationFaultListInfo.getStringVoc()+"") || "--".equals(stationFaultListInfo.getStringVoc()+"")){
                    contentHold.tvVoc.setText("--");
                }else {
                    contentHold.tvVoc.setText(Utils.round(Double.valueOf(stationFaultListInfo.getStringVoc()),3));
                }
                if("null".equals(stationFaultListInfo.getStringIsc()+"") || "--".equals(stationFaultListInfo.getStringIsc()+"")){
                    contentHold.tvIsc.setText("--");
                }else {
                    contentHold.tvIsc.setText(Utils.round(Double.valueOf(stationFaultListInfo.getStringIsc()),3));
                }
                if("null".equals(stationFaultListInfo.getFillFactor()+"") || "--".equals(stationFaultListInfo.getFillFactor()+"")){
                    contentHold.tvFf.setText("--");
                }else {
                    contentHold.tvFf.setText(Utils.round(Double.valueOf(stationFaultListInfo.getFillFactor()),3));
                }
                if("null".equals(stationFaultListInfo.getStringPm()+"") || "--".equals(stationFaultListInfo.getStringPm()+"")){
                    contentHold.tvPmax.setText("--");
                }else {
                    contentHold.tvPmax.setText(Utils.round(Double.valueOf(stationFaultListInfo.getStringPm()),3));
                }
                if("null".equals(stationFaultListInfo.getStringVm()+"") || "--".equals(stationFaultListInfo.getStringVm()+"")){
                    contentHold.tvVm.setText("--");
                }else {
                    contentHold.tvVm.setText(Utils.round(Double.valueOf(stationFaultListInfo.getStringVm()),3));
                }
                if("null".equals(stationFaultListInfo.getStringIm()+"") || "--".equals(stationFaultListInfo.getStringIm()+"")){
                    contentHold.tvIm.setText("--");
                }else {
                    contentHold.tvIm.setText(Utils.round(Double.valueOf(stationFaultListInfo.getStringIm()),3));
                }
                if("null".equals(stationFaultListInfo.getStringVm()+"") || "null".equals(stationFaultListInfo.getStringVoc()+"")){
                    contentHold.tvVmVoc.setText("--");
                }else {
                    double vmVoc = Double.valueOf(stationFaultListInfo.getStringVm())/Double.valueOf(stationFaultListInfo.getStringVoc());
                    contentHold.tvVmVoc.setText(Utils.round(vmVoc,3));
                }
                if("null".equals(stationFaultListInfo.getStringIm()+"") || "--".equals(stationFaultListInfo.getStringIsc()+"")){
                    contentHold.tvImIsc.setText("--");
                }else {
                    double imIsc = Double.valueOf(stationFaultListInfo.getStringIm())/Double.valueOf(stationFaultListInfo.getStringIsc());
                    contentHold.tvImIsc.setText(Utils.round(imIsc,3));
                }
            }
            return convertView;
        }

        private void initCenterValue(ContentViewHold contentHold) {
            if(centerValue != null){
                if("null".equals(centerValue.getInveterName()+"") || "--".equals(centerValue.getInveterName()+"")){
                    contentHold.tvInverterName.setText("--");
                }else {
                    contentHold.tvInverterName.setText(centerValue.getInveterName());
                }
                if("null".equals(centerValue.getPvIndex()+"") || "--".equals(centerValue.getPvIndex()+"")){
                    contentHold.tvZuchuan.setText("--");
                }else {
                    contentHold.tvZuchuan.setText(centerValue.getPvIndex());
                }
                if("null".equals(centerValue.getPromtCode()+"") || "--".equals(centerValue.getPromtCode()+"")){
                    contentHold.tvOptimizerCode.setText("--");
                }else {
                    contentHold.tvOptimizerCode.setText(centerValue.getPromtCode());
                }
                if("null".equals(centerValue.getStringVoc()+"") || "--".equals(centerValue.getStringVoc()+"")){
                    contentHold.tvVoc.setText("--");
                }else {
                    contentHold.tvVoc.setText(Utils.round(Double.valueOf(centerValue.getStringVoc()),3));
                }
                if("null".equals(centerValue.getStringIsc()+"") || "--".equals(centerValue.getStringIsc()+"")){
                    contentHold.tvIsc.setText("--");
                }else {
                    contentHold.tvIsc.setText(Utils.round(Double.valueOf(centerValue.getStringIsc()),3));
                }
                if("null".equals(centerValue.getFillFactor()+"") || "--".equals(centerValue.getFillFactor()+"")){
                    contentHold.tvFf.setText("--");
                }else {
                    contentHold.tvFf.setText(Utils.round(Double.valueOf(centerValue.getFillFactor()),3));
                }
                if("null".equals(centerValue.getStringPm()+"") || "--".equals(centerValue.getStringPm()+"")){
                    contentHold.tvPmax.setText("--");
                }else {
                    contentHold.tvPmax.setText(Utils.round(Double.valueOf(centerValue.getStringPm()),3));
                }
                if("null".equals(centerValue.getStringVm()+"") || "--".equals(centerValue.getStringVm()+"")){
                    contentHold.tvVm.setText("--");
                }else {
                    contentHold.tvVm.setText(Utils.round(Double.valueOf(centerValue.getStringVm()),3));
                }
                if("null".equals(centerValue.getStringIm()+"") || "--".equals(centerValue.getStringIm()+"")){
                    contentHold.tvIm.setText("--");
                }else {
                    contentHold.tvIm.setText(Utils.round(Double.valueOf(centerValue.getStringIm()),3));
                }
                if("null".equals(centerValue.getStringVm()+"") || "null".equals(centerValue.getStringVoc()+"")){
                    contentHold.tvVmVoc.setText("--");
                }else {
                    double vmVoc = Double.valueOf(centerValue.getStringVm())/Double.valueOf(centerValue.getStringVoc());
                    contentHold.tvVmVoc.setText(Utils.round(vmVoc,3));
                }
                if("null".equals(centerValue.getStringIm()+"") || "--".equals(centerValue.getStringIsc()+"")){
                    contentHold.tvImIsc.setText("--");
                }else {
                    double imIsc = Double.valueOf(centerValue.getStringIm())/Double.valueOf(centerValue.getStringIsc());
                    contentHold.tvImIsc.setText(Utils.round(imIsc,3));
                }
            }
        }

        class ContentViewHold {
            TextView tvCodeLeft;
            TextView tvExceptionType;
            TextView tvInverterName;
            TextView tvZuchuan;
            TextView tvOptimizerCode;
            TextView tvVoc;
            TextView tvIsc;
            TextView tvFf;
            TextView tvPmax;
            TextView tvVm;
            TextView tvIm;
            TextView tvVmVoc;
            TextView tvImIsc;
        }
    }
    public interface ShowButton{
        void showButton(boolean b);
    }
    private ShowButton showButton;

    public void setShowButton(ShowButton showButton) {
        this.showButton = showButton;
    }
    private LoadingDialog loadingDialog;

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getActivity());
        }
        loadingDialog.show();
    }

    public void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

}
