package com.huawei.solarsafe.view.maintaince.ivcurve;

import android.app.Dialog;
import android.app.NotificationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.ivcurve.FaultListBean;
import com.huawei.solarsafe.bean.ivcurve.FaultListInfo;
import com.huawei.solarsafe.bean.ivcurve.FaultStaticsBean;
import com.huawei.solarsafe.bean.ivcurve.FaultStaticsInfo;
import com.huawei.solarsafe.presenter.maintaince.ivcurve.CreatIVNewTeskPresenter;
import com.huawei.solarsafe.presenter.maintaince.ivcurve.CreatIVNewTeskView;
import com.huawei.solarsafe.utils.customview.LoadingDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by P00517 on 2017/7/21.
 */

public class TroubleListFragment extends Fragment implements CreatIVNewTeskView {
    private PullToRefreshListView listView;
    private long taskIVId;
    private CreatIVNewTeskPresenter presenter;
    private Map<String, String> param;
    private int page = 1;
    private int pageSize = 50;
    private List<FaultListInfo> faultListInfos;
    private TroubleListAdapter adapter;
    private View emptyView;
    int tag1 = -1;
    private int total = -1;
    private String stationCode;
    private List<FaultStaticsInfo> infos = new ArrayList<>();
    private List<String> tabs = new ArrayList<>();
    private int currentPos = -1;
    private static final String TAG = "TroubleListFragment";
    private Dialog disposeSuggestDialog;

    TextView tvErrorCode;
    TextView tvErrorName;
    TextView tvErrorSuggest;

    public TroubleListFragment() {
    }

    public static TroubleListFragment newInstance() {
        TroubleListFragment fragment = new TroubleListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter = new CreatIVNewTeskPresenter();
        presenter.onViewAttached(this);
        param = new HashMap<>();
        faultListInfos = new ArrayList<>();
        View view = View.inflate(getContext(), R.layout.fragment_iv_trouble_list, null);
        listView = (PullToRefreshListView) view.findViewById(R.id.lv_trouble);

        emptyView = View.inflate(getActivity(), R.layout.empty_view, null);
        listView.setEmptyView(emptyView);
        listView.getLoadingLayoutProxy(false, true);
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        // 下拉刷新
        listView.getLoadingLayoutProxy(true, false).setPullLabel(getString(R.string.pull_refresh));
        listView.getLoadingLayoutProxy(true, false).setRefreshingLabel(getString(R.string.updating));
        listView.getLoadingLayoutProxy(true, false).setReleaseLabel(getString(R.string.release_load));
        // 上拉加载更多，分页加载
        listView.getLoadingLayoutProxy(false, true).setPullLabel(getString(R.string.load_more));
        listView.getLoadingLayoutProxy(false, true).setRefreshingLabel(getString(R.string.updating));
        listView.getLoadingLayoutProxy(false, true).setReleaseLabel(getString(R.string.release_load));


        //分页加载数据
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                                          @Override
                                          public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                                              try {
                                                  NotificationManager manager = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
                                                  manager.cancelAll();
                                              } catch (Exception e) {
                                                  Log.e(TAG, "no notification NULLPOINTEXCEPTION", e);
                                              }
                                              // 刷新产品第一页
                                              faultListInfos.clear();
                                              page = 1;
                                              tag1 = 1;
                                              requestData();
                                          }

                                          @Override
                                          public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                                              if (listView.getChildCount() <= 0) {
                                                  return;
                                              }
                                              page++;
                                              if (faultListInfos.size() >= total && total != -1) {
                                                  Toast.makeText(getActivity(), R.string.no_more_data, Toast.LENGTH_SHORT).show();
                                              }
                                              tag1 = 1;
                                              requestData();
                                          }
                                      }
        );
        requestData();
        return view;
    }

    @Override
    public void requestData() {
        if(tag1 != 1){
            showLoading();
        }
        taskIVId = getTaskId();
        param.put("page", page + "");
        param.put("pageSize", pageSize + "");
        param.put("taskId", taskIVId + "");
        presenter.requestFaultList(param);
    }

    @Override
    public void getData(Object object) {
        dismissLoading();
        listView.onRefreshComplete();
        if (object == null) {
            return;
        }
        if (object instanceof FaultListBean) {
            listView.setMode(PullToRefreshBase.Mode.BOTH);
            FaultListBean faultListBean = (FaultListBean) object;
            if (faultListBean.isSuccess()){
                faultListInfos.addAll(faultListBean.getList());
                if (faultListInfos.size() == 0){
                    listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                }
                notifyList();
                if (total == -1) {
                    total = faultListBean.getTotal();
                }
            }
        }else if (object instanceof FaultStaticsBean){
            FaultStaticsBean bean = (FaultStaticsBean) object;
            infos = bean.getList();
            if (infos!=null&&infos.size()>0){
                for (int i = 0; i < infos.size(); i++) {
                    if (currentPos == infos.get(i).getErrorCode()){
                        showDisposeSuggestDialog(infos.get(i));
                    }
                }
            }
        }
    }

    @Override
    public void getDataFailed(String msg) {
        dismissLoading();
        if (tag1 == 1){
            listView.onRefreshComplete();
        }
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 设置数据，刷新列表
     */
    public void notifyList() {
        if (adapter == null) {
            adapter = new TroubleListAdapter();
            listView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    public long getTaskId() {
        return taskIVId;
    }

    public void setTaskId(long taskIVId) {
        this.taskIVId = taskIVId;
    }

    private LoadingDialog loadingDialog;

    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getActivity());
        }
        loadingDialog.show();
    }

    public void dismissLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getActivity());
        }
        loadingDialog.dismiss();
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public void setTabs(List<String> tabs) {
        this.tabs = tabs;
    }

    class TroubleListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return faultListInfos.size();
        }

        @Override
        public Object getItem(int i) {
            return faultListInfos.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            Holder holder;
            if (view == null) {
                holder = new Holder();
                view = View.inflate(getContext(), R.layout.item_iv_trouble_list, null);
                holder.tvStationName = (TextView) view.findViewById(R.id.tv_trouble_station_name);
                holder.tvStationCode = (TextView) view.findViewById(R.id.tv_trouble_station_num);
                holder.tvInverterName = (TextView) view.findViewById(R.id.tv_trouble_inverter_name);
                holder.tvInverterSn = (TextView) view.findViewById(R.id.tv_trouble_inverter_sn);
                holder.tvString = (TextView) view.findViewById(R.id.tv_trouble_string);
                holder.tvOptimizerNum = (TextView) view.findViewById(R.id.tv_trouble_optimizer_num);
                holder.tvDesc = (TextView) view.findViewById(R.id.tv_trouble_desc);
                holder.btDealSuggest = (Button) view.findViewById(R.id.bt_trouble_suggest);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }
            final FaultListInfo faultListInfo = faultListInfos.get(i);
            holder.tvStationName.setText("null".equals(faultListInfo.getStationName() +"") ? "N/A" : faultListInfo.getStationName());
            holder.tvInverterName.setText(faultListInfo.getInveterName());
            if (i+1 > 9){
                holder.tvStationCode.setText((i+1)+"");
            }else {
                holder.tvStationCode.setText("0"+(i+1));
            }
            holder.tvInverterSn.setText(faultListInfo.getInveterEsn());
            holder.tvString.setText(faultListInfo.getPvIndex());
            holder.tvOptimizerNum.setText("null".equals(faultListInfo.getPromtCode() + "") ? "-" : faultListInfo.getPromtCode());
            holder.tvDesc.setText(getActivity().getResources().getString(R.string.trouble_desc) + faultListInfo.getErrorDetail());

            holder.btDealSuggest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentPos = faultListInfo.getErrorCode();
                    Map<String, String> param1 = new HashMap<>();
                    param1.put("page", page + "");
                    param1.put("pageSize", pageSize + "");
                    //宋平修改 单号50279
                    param1.put("stationCode", faultListInfo.getStationCode());
                    param1.put("taskId", taskIVId + "");
                    presenter.requestFaultStaticsList(param1);
                }
            });

            return view;
        }

        class Holder {
            private TextView tvStationName;
            private TextView tvStationCode;
            private TextView tvInverterName;
            private TextView tvInverterSn;
            private TextView tvString;
            private TextView tvOptimizerNum;
            private TextView tvDesc;
            private Button btDealSuggest;
        }
    }

    /**
     * 显示处理建议对话框
     * @param faultStaticsInfo
     */
    private void showDisposeSuggestDialog(FaultStaticsInfo faultStaticsInfo) {

        if (disposeSuggestDialog==null){
            disposeSuggestDialog=new Dialog(getActivity(),R.style.zxing_help_dilog);
            disposeSuggestDialog.setContentView(R.layout.window_error_statics);
            disposeSuggestDialog.setCanceledOnTouchOutside(true);

            tvErrorCode = (TextView) disposeSuggestDialog.findViewById(R.id.tv_window_error_code);
            tvErrorName = (TextView) disposeSuggestDialog.findViewById(R.id.tv_window_error_name);
            tvErrorSuggest = (TextView) disposeSuggestDialog.findViewById(R.id.tv_window_error_suggest);
            tvErrorSuggest.setMovementMethod(ScrollingMovementMethod.getInstance());
            Button btSure = (Button) disposeSuggestDialog.findViewById(R.id.bt_window_error_sure);

            btSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    disposeSuggestDialog.dismiss();
                }
            });
        }

        tvErrorCode.setText(faultStaticsInfo.getErrorCode() + "");
        tvErrorName.setText(faultStaticsInfo.getErrorDescription() + "");
        tvErrorSuggest.setText(faultStaticsInfo.getSugs());

        if (!disposeSuggestDialog.isShowing()){
            disposeSuggestDialog.show();
        }
    }

    public void clearData(){
        faultListInfos.clear();
    }
}
