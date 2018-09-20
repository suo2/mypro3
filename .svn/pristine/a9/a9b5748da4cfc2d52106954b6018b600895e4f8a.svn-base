package com.huawei.solarsafe.view.maintaince.main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.base.MyStationPickerActivity;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.MyStationBean;
import com.huawei.solarsafe.bean.device.DispersionListInfo;
import com.huawei.solarsafe.bean.device.DispersionStatisticsInfo;
import com.huawei.solarsafe.bean.device.EquipmentDispersionInfo;
import com.huawei.solarsafe.presenter.maintaince.onlinediagnosis.OnlineDiagnosisPresenter;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DatePiker.DatePikerDialog;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.utils.mp.MPChartHelper;
import com.huawei.solarsafe.view.maintaince.operation.MaintenanceActivityNew;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static com.huawei.solarsafe.view.maintaince.operation.OnlineDiagnosisFilterPopupWindow.DC_BUS;
import static com.huawei.solarsafe.view.maintaince.operation.OnlineDiagnosisFilterPopupWindow.INVERTER_DEVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link OnlineDiagnosisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/**
 * 运维-在线诊断碎片
 */
public class OnlineDiagnosisFragment extends Fragment implements IOnlineDiagnosisView, AdapterView.OnItemClickListener {
    private View mainView;
    private BarChart barChart;
    protected String[] powers;
    protected final int[] colors = new int[]{Color.parseColor("#C12E34"), Color.parseColor("#FF00FF"), Color.parseColor("#E6B600"),
            Color.parseColor("#2B821D"), Color.parseColor("#0098D9"), Color.parseColor("#808080")};
    private ListView listView;
    private OnlineDiagnosisPresenter onlineDiagnosisPresenter;
    private DispersionStatisticsInfo dispersionStatisticsInfo;
    private DispersionListInfo dispersionListInfo;
    private OnlineDiagnosisAdapter adapter;
    private List<EquipmentDispersionInfo> dispersionInfos = new ArrayList<>();
    private DatePikerDialog datePikerDialog;
    private long handledTime;
    private int page = 1;
    private int pageSize = 50;
    private int dispersionRange = -1;    //0-5 【0.异常，1.20%以上，2.10-20%，3.5-10%，4.0-5%，5.未分析】; -1表示请求全部离散率信息
    private static final String TAG = "OnlineDiagnosisFragment";
    private int mScrollPosition;
    private final static int RECODE = 12;
    private String handledData;
    private LoadingDialog loadingDialog;
    private TextView online_tv_notion;
    MyStationBean root;
    //是否是第一次进入
    private boolean isFirst = true;
    private int dispersionRatio = INVERTER_DEVICE;//组串离散率
    Highlight checkedhighlight;//当前选中高亮值
    private boolean isSelectionOne = true;

    public void freshData() {
        handledTime = ((MaintenanceActivityNew) getActivity()).getHandledTime();
        page = 1;
        requestData();
    }

    public OnlineDiagnosisFragment() {

    }


    public static OnlineDiagnosisFragment newInstance() {
        OnlineDiagnosisFragment fragment = new OnlineDiagnosisFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onlineDiagnosisPresenter = new OnlineDiagnosisPresenter();
        onlineDiagnosisPresenter.onViewAttached(this);
        powers = new String[]{getString(R.string.exception), "20%" + getString(R.string.over), "10-20%", "5-10%", "0-5%", getString(R.string.not_analyzed)};
    }

    //将时间转换成时间戳，并去除小时后面的
    public Long getData(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日");
        Date date;
        long l = 0;
        try {
            date = sdr.parse(time);
            l = date.getTime();
        } catch (Exception e) {
            Log.e(TAG, "getData: " + e.getMessage());
        }
        return l;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_onlinediagnosis, container, false);
        View view = inflater.inflate(R.layout.dispsionstate_item_view, null, false);
        RelativeLayout headGroup = (RelativeLayout) mainView.findViewById(R.id.head_view_rl);
        barChart = (BarChart) view.findViewById(R.id.chart_bottom);
        listView = (ListView) mainView.findViewById(R.id.listview);
        online_tv_notion = (TextView) view.findViewById(R.id.online_tv_notion);
        adapter = new OnlineDiagnosisAdapter();
        headGroup.addView(view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //加载下一页数据
                if (scrollState == SCROLL_STATE_IDLE) {
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        page++;
                        showDialog();
                        refreshListData();
                        isSelectionOne = false;
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View topChild = listView.getChildAt(0);
                int newScrollPosition ;
                if (topChild == null) {
                    newScrollPosition = 0;
                } else {
                    newScrollPosition = -topChild.getTop() + listView.getFirstVisiblePosition() * topChild.getHeight();
                }
                if (Math.abs(newScrollPosition - mScrollPosition) > 10) {
                    if (newScrollPosition < mScrollPosition) {
                        if (hideHeadViewListener != null) {
                            hideHeadViewListener.hideBackButton(true);
                        }

                    } else if (newScrollPosition > mScrollPosition) {
                        if (hideHeadViewListener != null) {
                            hideHeadViewListener.hideBackButton(false);
                        }
                        if (hideHeadViewListener != null) {
                            hideHeadViewListener.hideHeadView();
                        }
                    }
                }
                mScrollPosition = newScrollPosition;
            }
        });
        //离散率柱状图点击监听器
        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                float temp = e.getY();
                if (temp > 0) {
                    checkedhighlight = h;
                    dispersionRange = (int) e.getX();
                    page = 1;
                    showDialog();
                    isSelectionOne = true;
                    refreshListData();
                } else if (checkedhighlight != null) {
                    barChart.highlightValue(checkedhighlight);
                }

            }

            @Override
            public void onNothingSelected() {
                if (checkedhighlight != null) {
                    barChart.highlightValue(checkedhighlight);
                }
            }
        });
        return mainView;
    }

    /**
     * 加载下一页数据
     */
    private void refreshListData() {
        //离散率展示请求
        HashMap<String, String> params = new HashMap<>();
        params.put("stationIds", ids);
        params.put("pageSize", pageSize + "");
        params.put("statTime", handledTime + "");
        params.put("dispersionRange", dispersionRange + "");
        params.put("page", page + "");
        params.put("sort", "desc");//降序
        params.put("orderBy", "");
        switch (dispersionRatio) {
            case INVERTER_DEVICE:
                onlineDiagnosisPresenter.doRequestDispersion(params);
                break;
            case DC_BUS:
                onlineDiagnosisPresenter.doRequestDCDispersion(params);
                break;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void requestData() {
        showDialog();
        //离散率展示请求
        HashMap<String, String> params = new HashMap<>();
        params.put("stationIds", ids);
        params.put("pageSize", pageSize + "");
        params.put("statTime", handledTime + "");
        params.put("dispersionRange", dispersionRange + "");
        params.put("page", page + "");
        params.put("sort", "desc");//降序
        params.put("orderBy", "");

        //离散率统计请求
        HashMap<String, String> params1 = new HashMap<>();
        params1.put("stationIds", ids);
        params1.put("statTime", handledTime + "");
        switch (dispersionRatio) {
            case INVERTER_DEVICE:
                onlineDiagnosisPresenter.doRequestDispersion(params);
                onlineDiagnosisPresenter.doRequestStatDispersion(params1);
                break;
            case DC_BUS:
                onlineDiagnosisPresenter.doRequestDCDispersion(params);
                onlineDiagnosisPresenter.doRequestDCStatDispersion(params1);
                break;
        }
    }

    public void refreshData(int dispersionRatio, String stationIds, long handledTime) {
        this.dispersionRatio = dispersionRatio;
        this.ids = stationIds;
        this.handledTime = handledTime;
        dispersionRange = -1;
        page = 1;
        isSelectionOne = true;
    }

    @Override
    public void getData(BaseEntity baseEntity) {
        dismissDialog();
        if (baseEntity == null) {
            return;
        }
        if (baseEntity instanceof DispersionListInfo) {
            dispersionListInfo = (DispersionListInfo) baseEntity;
            if (dispersionListInfo.getDispersionInfos() == null) {
                dispersionInfos.clear();
                adapter.notifyDataSetChanged();
                return;
            }
            //清除上一次数据
            if (page == 1) {
                dispersionInfos.clear();
            }
            if (dispersionListInfo.getDispersionInfos().size() == 0 && page != 1) {
                page--;
                ToastUtil.showMessage(getString(R.string.no_more_data));
                return;
            }
            dispersionInfos.addAll(dispersionListInfo.getDispersionInfos());
            adapter.notifyDataSetChanged();
            if(isSelectionOne){
                listView.setSelection(0);
            }
            isSelectionOne = true;
        } else if (baseEntity instanceof DispersionStatisticsInfo) {
            boolean noData ;
            dispersionStatisticsInfo = (DispersionStatisticsInfo) baseEntity;
            List<String> x = new ArrayList<>();
            List<Integer> y = new ArrayList<>();
            for (String s : powers) {
                x.add(s);
            }
            int[] dispersions = dispersionStatisticsInfo.getDispersions();
            barChart.clear();
            barChart.setScaleYEnabled(false);//y轴缩放
            barChart.setScaleXEnabled(false);//x轴缩放
            barChart.setDoubleTapToZoomEnabled(false);//双击缩放
            if (dispersions != null) {
                for (int i : dispersions) {
                    y.add(i);
                }
                if (y.size() == 0 || 1 == new HashSet<Object>(y).size() && y.get(0) == 0) {
                    online_tv_notion.setVisibility(View.VISIBLE);
                    noData = true;
                } else {
                    online_tv_notion.setVisibility(View.GONE);
                    noData = false;
                }
                MPChartHelper.setBarChartIntData(barChart, x, y, "", colors, noData);//getString(R.string.num_piece)
            } else {
                //人为的假数据，只是当数据为空时，把表显示出来
                for (int i = 0; i < 6; i++) {
                    y.add(0);
                }
                online_tv_notion.setVisibility(View.VISIBLE);
                noData = true;
                MPChartHelper.setBarChartIntData(barChart, x, y, "", colors, noData);//getString(R.string.num_piece)
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getContext());
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


    //组串平均电压电流
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //携带参数，跳转到新界面，展示
        switch (dispersionRatio) {
            case INVERTER_DEVICE:
                Intent intent = new Intent(getActivity(), GroupPVActivity.class);
                EquipmentDispersionInfo dispersionInfo = dispersionInfos.get(position);
                intent.putExtra(GlobalConstants.KEY_DISPERSION_INFO, dispersionInfo);
                startActivity(intent);
                break;
            case DC_BUS:
                Intent intent1 = new Intent(getActivity(), DCGroupPVActivity.class);
                EquipmentDispersionInfo dispersionInfo1 = dispersionInfos.get(position);
                intent1.putExtra(GlobalConstants.KEY_DISPERSION_INFO, dispersionInfo1);
                startActivity(intent1);
                break;
        }
    }

    private StringBuffer sbName = new StringBuffer();
    private StringBuffer sbId = new StringBuffer();
    private String ids;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RECODE) {
            if (sbId.length() != 0) {
                sbId.replace(0, sbId.length(), "");
            }
            if (sbName.length() != 0) {
                sbName.replace(0, sbName.length(), "");
            }
            if (data == null) {
                ((MaintenanceActivityNew) getActivity()).setSelectStation("");
                ((MaintenanceActivityNew) getActivity()).setSelectStationIds(null);
                return;
            }

            ArrayList<MyStationBean> myStationBeanArrayList = new ArrayList<>();
            try {
                MyStationBean root = (MyStationBean) data.getSerializableExtra("root");
                if (root == null) {
                    return;
                }
                myStationBeanArrayList = MyStationPickerActivity.collectCheckedStations(root, myStationBeanArrayList);
                if (myStationBeanArrayList != null) {
                    for (MyStationBean s : myStationBeanArrayList) {
                        if (("DOMAIN_NOT".equals(s.getModel()) || "DOMAIN".equals(s.getModel())) && (s.getP() == null || !s.getP().isChecked()) && s.isChecked()) {
                            if("Msg.&topdomain".equals(s.getName())){
                                sbName.append(getString(R.string.topdomain) + ",");
                            }else {
                                sbName.append(s.getName() + ",");
                            }

                        }else if ("STATION".equals(s.getModel())) {
                            if (s.isChecked()) {
                                sbId.append(s.getId() + ",");
                                if(!s.getP().isChecked()){
                                    sbName.append(s.getName() + ",");
                                }
                            }
                        }
                    }
                    this.root = root;
                    if (sbId.length() == 0) {
                        ((MaintenanceActivityNew) getActivity()).setSelectStation("");
                        ((MaintenanceActivityNew) getActivity()).setSelectStationIds(null);
                        dispersionRange = -1;
                        page = 1;
                        requestData();
                        return;
                    }
                    if (sbId.length() > 1) {
                        ((MaintenanceActivityNew) getActivity()).setSelectStationIds(sbId.toString().substring(0, sbId.length() - 1));
                    }

                    if (sbName.length() > 1) {
                        ((MaintenanceActivityNew) getActivity()).setSelectStation(sbName.toString().substring(0, sbName.length() - 1));
                    }
                    isFirst = false;
                }
            } catch (Exception e){
                Log.e(TAG, "onActivityResult: " + e.getMessage());
            }
        }
    }

    class OnlineDiagnosisAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dispersionInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return dispersionInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.online_item, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.stationName = (TextView) convertView.findViewById(R.id.alarm_station_name);
                viewHolder.devName = (TextView) convertView.findViewById(R.id.alarm_target);
                viewHolder.dispersion = (TextView) convertView.findViewById(R.id.tv_series_inverters_status);
                viewHolder.seriesInvertersName = (TextView) convertView.findViewById(R.id.series_inverters_name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (dispersionRatio == INVERTER_DEVICE) {
                viewHolder.seriesInvertersName.setText(getResources().getString(R.string.str_inv_dispersion_ratio));
            } else {
                viewHolder.seriesInvertersName.setText(getResources().getString(R.string.dc_bus_dispersion_ratio));
            }
            final EquipmentDispersionInfo dispersionInfo = dispersionInfos.get(position);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setCornerRadius(100);
            String dispersion = dispersionInfo.getDispersion();
            //离散率转化：异常("null")和未分析("-1")
            boolean isNum = true;
            if (dispersionInfo.getDispersion().equals("null")) {
                dispersion = getString(R.string.exception);
                drawable.setColor(colors[0]);
                drawable.setStroke(1, colors[0]);
                isNum = false;
            } else if (dispersionInfo.getDispersion().equals("-1.0")) {
                dispersion = getString(R.string.not_analyzed);
                drawable.setColor(colors[5]);
                drawable.setStroke(1, colors[5]);
                isNum = false;
            } else {
                float dispersionValue;
                try {
                    dispersionValue = Float.valueOf(dispersionInfo.getDispersion());
                } catch (NumberFormatException e) {
                    dispersionValue = 0;
                }
                if (dispersionValue < 5) {
                    drawable.setColor(colors[4]);
                    drawable.setStroke(1, colors[4]);
                } else if (dispersionValue < 10) {
                    drawable.setColor(colors[3]);
                    drawable.setStroke(1, colors[3]);
                } else if (dispersionValue < 20) {
                    drawable.setColor(colors[2]);
                    drawable.setStroke(1, colors[2]);
                } else {
                    drawable.setColor(colors[1]);
                    drawable.setStroke(1, colors[1]);
                }
            }
            viewHolder.stationName.setText(dispersionInfo.getStationName());
            viewHolder.devName.setText(dispersionInfo.getDeviceName());
            viewHolder.dispersion.setText(isNum ? dispersion + "%" : dispersion);
            viewHolder.dispersion.setBackground(drawable);
            return convertView;
        }

        class ViewHolder {
            TextView stationName, devName, dispersion, seriesInvertersName;
        }
    }


    public void setDefaultStation() {
        sbName.replace(0, sbName.length(), "");
        sbId.replace(0, sbId.length(), "");
        root = null;
        isFirst = true;
    }

    public void selectStation() {
        Intent intent = new Intent(getActivity(), MyStationPickerActivity.class);
        intent.putExtra("root", root);
        intent.putExtra("isFirst", isFirst);
        startActivityForResult(intent, RECODE);
    }

    public void selectTime() {
        if (datePikerDialog == null) {
            //日期选择器
            datePikerDialog = new DatePikerDialog(getActivity(), Utils.getFormatTimeYYMMDD(((MaintenanceActivityNew) getActivity()).getHandledTime()), new DatePikerDialog
                    .OnDateFinish() {
                @Override
                public void onDateListener(long date) {
                    long time = getHandledTime(date);
                    ((MaintenanceActivityNew) getActivity()).setSelectTime(Utils.getFormatTimeYYMMDD(time), time);
                }

                @Override
                public void onSettingClick() {

                }
            });
            datePikerDialog.updateTime(((MaintenanceActivityNew) getActivity()).getHandledTime(), -1);
            datePikerDialog.show(R.id.radio_day, Utils.getMillisFromYYMMDD("2000/01/01"), getDayLaterTime(System.currentTimeMillis()));
        } else {
            if (!datePikerDialog.isShow()) {
                datePikerDialog.updateTime(((MaintenanceActivityNew) getActivity()).getHandledTime(), -1);
                datePikerDialog.show(R.id.radio_day, Utils.getMillisFromYYMMDD("2000/01/01"), getDayLaterTime(System.currentTimeMillis()));
            }
        }
    }
    //将当前时间戳转化成当天0:0:0的时间戳
    private long getHandledTime(long sourceTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(sourceTime);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        return cal.getTimeInMillis();
    }
    //将当前时间戳转化成当天23:59:59的时间戳
    private long getDayLaterTime(long sourceTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(sourceTime);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        return cal.getTimeInMillis();
    }

    private OnHideHeadViewListener hideHeadViewListener;

    public void setHideHeadViewListener(OnHideHeadViewListener hideHeadViewListener) {
        this.hideHeadViewListener = hideHeadViewListener;
    }

    public interface OnHideHeadViewListener {
        void hideHeadView();

        void hideBackButton(boolean isHide);
    }
}
