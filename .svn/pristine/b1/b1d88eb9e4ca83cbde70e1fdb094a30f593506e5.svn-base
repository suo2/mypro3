package com.huawei.solarsafe.view.stationmanagement.changestationinfo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.BaseEntity;
import com.huawei.solarsafe.bean.ResultInfo;
import com.huawei.solarsafe.bean.pnlogger.ItemsBean;
import com.huawei.solarsafe.bean.pnlogger.PriceBean;
import com.huawei.solarsafe.bean.pnlogger.PricetotalBean;
import com.huawei.solarsafe.bean.station.ChangeStationInfo;
import com.huawei.solarsafe.bean.stationmagagement.ChangeStationResultInfo;
import com.huawei.solarsafe.bean.stationmagagement.CreateStationArgs;
import com.huawei.solarsafe.bean.stationmagagement.GridPrice;
import com.huawei.solarsafe.bean.stationmagagement.GridPriceInfo;
import com.huawei.solarsafe.bean.stationmagagement.UpdataPriceReq;
import com.huawei.solarsafe.bean.stationmagagement.UpdateUseDefaultPrice;
import com.huawei.solarsafe.presenter.stationmanagement.ChangeStationPresenter;
import com.huawei.solarsafe.presenter.stationmanagement.CreateStationPresenter;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.ToastUtil;
import com.huawei.solarsafe.utils.Utils;
import com.huawei.solarsafe.utils.customview.DatePiker.DatePikerDialog;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.utils.dealelectricityprice.ElectricityPriceUtils;
import com.huawei.solarsafe.utils.dealelectricityprice.Period;
import com.huawei.solarsafe.view.stationmanagement.CreateBaseFragmnet;
import com.huawei.solarsafe.view.stationmanagement.ICreateStationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create Date: 2017/4/24
 * Create Author: P00171
 * Description :创建电站-电价设置Fragment
 */
public class PriceSettingFragment extends CreateBaseFragmnet implements View.OnClickListener, IChangeStationView, ICreateStationView {
    private Spinner spCurrencySetting;
    private LinearLayout llytDateContainer;
    private String[] currencys;
    private String[] timeList;
    private long selectTime;
    //默认电价
    private GridPrice gridPrice = new GridPrice();
    private ChangeStationInfo changeStationInfo;
    private CreateStationPresenter createStationPresenter;
    private ChangeStationPresenter changeStationPresenter;
    private String stationId;
    private String domainId;
    private LoadingDialog loadingDialog;
    private LinearLayout linearLayoutMorenPrice;
    private CheckBox checkBoxMorenPrice;
    private boolean isUseDefaultPrice;
    private long startDate, endDate;
    private boolean isEndle = false;
    private String locationTimeZone;
    private GridPrice defaultGridPrice;//默认电价

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

    public void setChangeStationInfo(ChangeStationInfo changeStationInfo) {
        this.changeStationInfo = changeStationInfo;
        if (changeStationInfo != null) {
            stationId = changeStationInfo.getStationCode();
            domainId = changeStationInfo.getDomainId() + "";
            showLoading();
            requestData();
        }
    }

    public void setGridPrice(GridPrice gridPrice) {
        this.gridPrice = gridPrice;
        initGridPrice();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createStationPresenter = new CreateStationPresenter();
        changeStationPresenter = new ChangeStationPresenter();
        createStationPresenter.setView(this);
        changeStationPresenter.setView(this);
        if (Integer.valueOf(LocalData.getInstance().getTimezone()) > 0 || Integer.valueOf(LocalData.getInstance().getTimezone()) == 0) {
            locationTimeZone = "+" + LocalData.getInstance().getTimezone();
        } else {
            locationTimeZone = LocalData.getInstance().getTimezone();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_station_price_setting, container, false);
        currencys = new String[]{getString(R.string.rmb_name), getString(R.string.dollar_name), getString(R.string.yen_name), getString(R.string.euro_name), getString(R.string.pound_name)};
        timeList = new String[26];
        timeList[0] = getString(R.string.choice_time);
        for (int i = 1; i <= 25; i++) {
            timeList[i] = (i - 1) + ":00:00";
        }
        spCurrencySetting = (Spinner) view.findViewById(R.id.sp_currency_setting);
        ArrayAdapter currencyAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, currencys);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCurrencySetting.setAdapter(currencyAdapter);
        llytDateContainer = (LinearLayout) view.findViewById(R.id.llyt_date_container);
        linearLayoutMorenPrice = (LinearLayout) view.findViewById(R.id.ll_moren_price);
        checkBoxMorenPrice = (CheckBox) view.findViewById(R.id.cb_moren_price);
        checkBoxMorenPrice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showLoading();
                    isUseDefaultPrice = true;
                    showLoading();
                    requestSysPreice();
                } else {
                    isUseDefaultPrice = false;
                    showLoading();
                    requestUserPreice();
                }
                canEdt(isEndle);
            }
        });
        checkBoxMorenPrice.setEnabled(false);
        addDateView();
        //初始化最大最小日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
        startDate = calendar.getTimeInMillis();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        endDate = calendar.getTimeInMillis();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initGridPrice() {
        if (gridPrice != null) {
            List<GridPrice.DataBean> data = gridPrice.getData();
            if (data != null) {
                llytDateContainer.removeAllViews();
                if (data.size() == 0) {
                    addDateView();
                } else {
                    for (GridPrice.DataBean databean : data) {
                        addDateViewInit(databean);
                    }
                }
            } else {
                llytDateContainer.removeAllViews();
                addDateView();
            }
        }
        int childCount = llytDateContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = llytDateContainer.getChildAt(i);
            DateViewHolder dateViewHolder = (DateViewHolder) childAt.getTag();
            dateViewHolder.ivDateAdd = (ImageView) childAt.findViewById(R.id.iv_date_add);
            dateViewHolder.ivDateSub = (ImageView) childAt.findViewById(R.id.iv_date_sub);
            dateViewHolder.ivDateAdd.setVisibility(View.INVISIBLE);
            dateViewHolder.ivDateSub.setVisibility(View.INVISIBLE);
            dateViewHolder.tvStartDate.setEnabled(false);
            dateViewHolder.tvEndDate.setEnabled(false);
            int childCount1 = dateViewHolder.llytTimeContainer.getChildCount();
            for (int j = 0; j < childCount1; j++) {
                View childAt1 = dateViewHolder.llytTimeContainer.getChildAt(j);
                TimeViewHolder timeViewHolder = (TimeViewHolder) childAt1.getTag();
                timeViewHolder.ivTimeAdd = (ImageView) childAt1.findViewById(R.id.iv_time_add);
                timeViewHolder.ivTimeSub = (ImageView) childAt1.findViewById(R.id.iv_time_sub);
                timeViewHolder.ivTimeAdd.setVisibility(View.INVISIBLE);
                timeViewHolder.ivTimeSub.setVisibility(View.INVISIBLE);
                timeViewHolder.spStartTime.setEnabled(false);
                timeViewHolder.spEndTime.setEnabled(false);
                timeViewHolder.etPrice.setEnabled(false);
            }
        }
        canEdt(isEndle);
    }

    private void addDateViewInit(GridPrice.DataBean gridPriceDate) {
        View view = LayoutInflater.from(llytDateContainer.getContext()).inflate(R.layout.item_create_station_price_setting_date, null);
        DateViewHolder holder = new DateViewHolder();
        holder.tvStartDate = (TextView) view.findViewById(R.id.tv_start_date);
        holder.tvEndDate = (TextView) view.findViewById(R.id.tv_end_date);
        holder.tvStartDate.setTag(getHandledTime(System.currentTimeMillis()));
        holder.tvEndDate.setTag(getHandledTime(System.currentTimeMillis()));
        holder.ivDateAdd = (ImageView) view.findViewById(R.id.iv_date_add);
        holder.ivDateSub = (ImageView) view.findViewById(R.id.iv_date_sub);
        holder.llytTimeContainer = (LinearLayout) view.findViewById(R.id.llyt_time_container);
        view.setTag(holder);
        llytDateContainer.addView(view);
        if (llytDateContainer.indexOfChild(view) == 0) {
            holder.ivDateSub.setVisibility(View.INVISIBLE);
        } else if (llytDateContainer.indexOfChild(view) > 0) {
            //隐藏前一个添加按钮，保证添加按钮只出现在最后Item
            View preView = llytDateContainer.getChildAt(llytDateContainer.indexOfChild(view) - 1);
            DateViewHolder preHolder = (DateViewHolder) preView.getTag();
            preHolder.ivDateAdd.setVisibility(View.INVISIBLE);
        }
        List<GridPrice.DataBean.PriceItemBean> priceItems = gridPriceDate.getPriceItem();
        for (GridPrice.DataBean.PriceItemBean priceItem : priceItems) {
            addTimeView(holder.llytTimeContainer, priceItem);
        }
        //删除添加日期
        holder.ivDateSub.setOnClickListener(this);
        holder.ivDateAdd.setOnClickListener(this);
        //设置Tag，用于添加删除
        holder.ivDateSub.setTag(R.id.tag_child_view, view);
        holder.ivDateSub.setTag(R.id.tag_parent_view, llytDateContainer);
        holder.ivDateAdd.setTag(R.id.tag_child_view, view);
        holder.ivDateAdd.setTag(R.id.tag_parent_view, llytDateContainer);
        //设置点击事件
        holder.tvStartDate.setOnClickListener(this);
        holder.tvEndDate.setOnClickListener(this);
        GridPrice.DataBean.PriceBean price = gridPriceDate.getPrice();
        long startData = price.getBeginDate() - Long.valueOf(LocalData.getInstance().getTimezone()) * 3600l * 1000l;
        long endData = price.getEndDate() - Long.valueOf(LocalData.getInstance().getTimezone()) * 3600l * 1000l;
        holder.tvStartDate.setText(Utils.getFormatTimeMMDD(startData, locationTimeZone));
        holder.tvEndDate.setText(Utils.getFormatTimeMMDD(endData, locationTimeZone));
        holder.tvStartDate.setTag(price.getBeginDate());
        holder.tvEndDate.setTag(price.getEndDate());
    }

    private void addTimeView(LinearLayout llytTimeContainer, GridPrice.DataBean.PriceItemBean priceItem) {
        View view = LayoutInflater.from(llytTimeContainer.getContext()).inflate(R.layout.item_create_station_price_setting_time, null);
        TimeViewHolder holder = new TimeViewHolder();
        holder.spStartTime = (Spinner) view.findViewById(R.id.sp_start_time);
        ArrayAdapter timeAdapter = new ArrayAdapter(getContext(), R.layout.item_text_spinner, timeList);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spStartTime.setAdapter(timeAdapter);
        holder.spEndTime = (Spinner) view.findViewById(R.id.sp_end_time);
        timeAdapter = new ArrayAdapter(getContext(), R.layout.item_text_spinner, timeList);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spEndTime.setAdapter(timeAdapter);
        holder.ivTimeAdd = (ImageView) view.findViewById(R.id.iv_time_add);
        holder.ivTimeSub = (ImageView) view.findViewById(R.id.iv_time_sub);
        holder.etPrice = (EditText) view.findViewById(R.id.et_price);
        holder.tvPriceUnit = (TextView) view.findViewById(R.id.tv_price_unit);
        view.setTag(holder);
        llytTimeContainer.addView(view);
        if (llytTimeContainer.indexOfChild(view) == 0) {
            holder.ivTimeSub.setVisibility(View.INVISIBLE);
        } else if (llytTimeContainer.indexOfChild(view) > 0) {
            //隐藏前一个添加按钮，保证添加按钮只出现在最后Item
            View preView = llytTimeContainer.getChildAt(llytTimeContainer.indexOfChild(view) - 1);
            TimeViewHolder preHolder = (TimeViewHolder) preView.getTag();
            preHolder.ivTimeAdd.setVisibility(View.INVISIBLE);
        }
        //删除添加时间
        holder.ivTimeSub.setOnClickListener(this);
        holder.ivTimeAdd.setOnClickListener(this);
        //设置Tag，用于添加删除
        holder.ivTimeSub.setTag(R.id.tag_child_view, view);
        holder.ivTimeSub.setTag(R.id.tag_parent_view, llytTimeContainer);
        holder.ivTimeAdd.setTag(R.id.tag_child_view, view);
        holder.ivTimeAdd.setTag(R.id.tag_parent_view, llytTimeContainer);
        //添加默认电价
        holder.spStartTime.setSelection(priceItem.getBeginHour() + 1);
        holder.spEndTime.setSelection(priceItem.getEndHour() + 1);
        holder.tvPriceUnit.setText(getResources().getString(R.string.power_price) + "（" + Utils.getCurrencyType() + "）");
        holder.etPrice.setText(priceItem.getPrice() + "");
    }

    private void addDateView() {
        View view = LayoutInflater.from(llytDateContainer.getContext()).inflate(R.layout.item_create_station_price_setting_date, null);
        DateViewHolder holder = new DateViewHolder();
        holder.tvStartDate = (TextView) view.findViewById(R.id.tv_start_date);
        holder.tvEndDate = (TextView) view.findViewById(R.id.tv_end_date);
        holder.tvStartDate.setTag(getHandledTime(System.currentTimeMillis()));
        holder.tvEndDate.setTag(getHandledTime(System.currentTimeMillis()));
        holder.ivDateAdd = (ImageView) view.findViewById(R.id.iv_date_add);
        holder.ivDateSub = (ImageView) view.findViewById(R.id.iv_date_sub);
        holder.llytTimeContainer = (LinearLayout) view.findViewById(R.id.llyt_time_container);
        view.setTag(holder);
        llytDateContainer.addView(view);
        if (llytDateContainer.indexOfChild(view) == 0) {
            holder.ivDateSub.setVisibility(View.INVISIBLE);
        } else if (llytDateContainer.indexOfChild(view) > 0) {
            //隐藏前一个添加按钮，保证添加按钮只出现在最后Item
            View preView = llytDateContainer.getChildAt(llytDateContainer.indexOfChild(view) - 1);
            DateViewHolder preHolder = (DateViewHolder) preView.getTag();
            preHolder.ivDateAdd.setVisibility(View.INVISIBLE);
        }
        addTimeView(holder.llytTimeContainer);
        //删除添加日期
        holder.ivDateSub.setOnClickListener(this);
        holder.ivDateAdd.setOnClickListener(this);
        //设置Tag，用于添加删除
        holder.ivDateSub.setTag(R.id.tag_child_view, view);
        holder.ivDateSub.setTag(R.id.tag_parent_view, llytDateContainer);
        holder.ivDateAdd.setTag(R.id.tag_child_view, view);
        holder.ivDateAdd.setTag(R.id.tag_parent_view, llytDateContainer);
        //设置点击事件
        holder.tvStartDate.setOnClickListener(this);
        holder.tvEndDate.setOnClickListener(this);
    }

    private void addTimeView(LinearLayout llytTimeContainer) {
        View view = LayoutInflater.from(llytTimeContainer.getContext()).inflate(R.layout.item_create_station_price_setting_time, null);
        TimeViewHolder holder = new TimeViewHolder();
        holder.spStartTime = (Spinner) view.findViewById(R.id.sp_start_time);
        ArrayAdapter timeAdapter = new ArrayAdapter(getContext(), R.layout.item_text_spinner, timeList);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spStartTime.setAdapter(timeAdapter);
        holder.spEndTime = (Spinner) view.findViewById(R.id.sp_end_time);
        timeAdapter = new ArrayAdapter(getContext(), R.layout.item_text_spinner, timeList);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spEndTime.setAdapter(timeAdapter);
        holder.ivTimeAdd = (ImageView) view.findViewById(R.id.iv_time_add);
        holder.ivTimeSub = (ImageView) view.findViewById(R.id.iv_time_sub);
        holder.etPrice = (EditText) view.findViewById(R.id.et_price);
        holder.tvPriceUnit = (TextView) view.findViewById(R.id.tv_price_unit);
        view.setTag(holder);
        llytTimeContainer.addView(view);
        if (llytTimeContainer.indexOfChild(view) == 0) {
            holder.ivTimeSub.setVisibility(View.INVISIBLE);
        } else if (llytTimeContainer.indexOfChild(view) > 0) {
            //隐藏前一个添加按钮，保证添加按钮只出现在最后Item
            View preView = llytTimeContainer.getChildAt(llytTimeContainer.indexOfChild(view) - 1);
            TimeViewHolder preHolder = (TimeViewHolder) preView.getTag();
            preHolder.ivTimeAdd.setVisibility(View.INVISIBLE);
        }
        //删除添加时间
        holder.ivTimeSub.setOnClickListener(this);
        holder.ivTimeAdd.setOnClickListener(this);
        //设置Tag，用于添加删除
        holder.ivTimeSub.setTag(R.id.tag_child_view, view);
        holder.ivTimeSub.setTag(R.id.tag_parent_view, llytTimeContainer);
        holder.ivTimeAdd.setTag(R.id.tag_child_view, view);
        holder.ivTimeAdd.setTag(R.id.tag_parent_view, llytTimeContainer);

        holder.tvPriceUnit.setText(getResources().getString(R.string.power_price) + "（" + Utils.getCurrencyType() + "）");
    }

    private void removeDateView(View childView) {
        int index = llytDateContainer.indexOfChild(childView);
        if (index > 0) {
            View preView = llytDateContainer.getChildAt(index - 1);
            DateViewHolder holder = (DateViewHolder) preView.getTag();
            holder.ivDateAdd.setVisibility(View.VISIBLE);
        }
        llytDateContainer.removeView(childView);
    }

    private void removeTimeView(LinearLayout llytTimeContainer, View childView) {
        int index = llytTimeContainer.indexOfChild(childView);
        if (index > 0) {
            View preView = llytTimeContainer.getChildAt(index - 1);
            TimeViewHolder holder = (TimeViewHolder) preView.getTag();
            holder.ivTimeAdd.setVisibility(View.VISIBLE);
        }
        llytTimeContainer.removeView(childView);
    }

    @Override
    public void onClick(View v) {
        LinearLayout parentView;
        View childView;
        switch (v.getId()) {
            case R.id.iv_date_sub:
                //删除日期
                childView = (View) v.getTag(R.id.tag_child_view);
                removeDateView(childView);
                break;
            case R.id.iv_date_add:
                //添加日期
                addDateView();
                break;
            case R.id.iv_time_sub:
                //删除时间
                parentView = (LinearLayout) v.getTag(R.id.tag_parent_view);
                childView = (View) v.getTag(R.id.tag_child_view);
                removeTimeView(parentView, childView);
                break;
            case R.id.iv_time_add:
                //添加时间
                parentView = (LinearLayout) v.getTag(R.id.tag_parent_view);
                int totalHours = 0;
                for (int i = 0; i < parentView.getChildCount(); i++) {
                    View timeChildView = parentView.getChildAt(i);
                    TimeViewHolder timeViewHolder = (TimeViewHolder) timeChildView.getTag();
                    String startTime = timeViewHolder.spStartTime.getSelectedItem().toString().split(":")[0];
                    String endTime = timeViewHolder.spEndTime.getSelectedItem().toString().split(":")[0];
                    try {
                        int startTimeInt = Integer.valueOf(startTime);
                        int endTimeInt = Integer.valueOf(endTime);
                        totalHours += endTimeInt - startTimeInt;
                    } catch (NumberFormatException ex) {
                        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
                        Log.e("PriceSettingFragment", "NumberFormatException");
                    }
                }
                if (totalHours == 24) {
                    DialogUtil.showErrorMsg(getActivity(), getResources().getString(R.string.hour24_full_str));
                    return;
                }
                parentView = (LinearLayout) v.getTag(R.id.tag_parent_view);
                addTimeView(parentView);
                break;
            case R.id.tv_start_date:
            case R.id.tv_end_date:
                showDateDialog((TextView) v);
                break;
        }
    }

    /**
     * @param args
     * @return 数据的校验
     */
    @Override
    public boolean validateAndSetArgs(CreateStationArgs args) {
        List<PricetotalBean> pricetotalBeanList = args.getPricetotal();
        if (pricetotalBeanList == null) {
            pricetotalBeanList = new ArrayList<>();
            args.setPricetotal(pricetotalBeanList);
        } else {
            pricetotalBeanList.clear();
        }
        String currencyId;
        Object tag;
        currencyId = getCurrencyId();
        if (currencyId == null) {
            ToastUtil.showMessage(getString(R.string.select_currency_setting));
            return false;
        }
        //wujing修改
        long tempstartDate = 0;
        long temendDate = 0;
        ArrayList<Period> periodsDate = new ArrayList<>();
        for (int i = 0; i < llytDateContainer.getChildCount(); i++) {
            PricetotalBean pricetotalBean = new PricetotalBean();
            pricetotalBeanList.add(pricetotalBean);
            PriceBean priceBean = new PriceBean();
            pricetotalBean.setPrice(priceBean);
            View dateChildView = llytDateContainer.getChildAt(i);
            DateViewHolder dateViewHolder = (DateViewHolder) dateChildView.getTag();
            tag = dateViewHolder.tvStartDate.getTag();
            if (tag == null || (long) tag == getHandledTime(System.currentTimeMillis())) {
                ToastUtil.showMessage(getString(R.string.select_start_date));
                return false;
            }
            long startDate = getHandledTime2001((long) tag);
            tag = dateViewHolder.tvEndDate.getTag();
            if (tag == null || (long) tag == getHandledTime(System.currentTimeMillis())) {
                ToastUtil.showMessage(getString(R.string.select_end_date));
                return false;
            }
            long endDate = getHandledEndTime2001((long) tag);
            priceBean.setBeginDate(startDate);
            priceBean.setEndDate(endDate);
            if (endDate < startDate) {
                ToastUtil.showMessage(R.string.correct_set_data_range);
                return false;
            }
            Period periodDate = new Period();
            periodDate.setStartTime(startDate);
            periodDate.setEndTime(endDate);
            periodsDate.add(periodDate);
            //校验日期是否有交叉
            if (i == 0) {
                tempstartDate = startDate;
                temendDate = endDate;
            } else {
                if (startDate >= tempstartDate && startDate <= temendDate) {
                    DialogUtil.showErrorMsg(getActivity(), getResources().getString(R.string.date_jx_notice));
                    return false;
                }
                if (endDate >= tempstartDate && endDate <= temendDate) {
                    DialogUtil.showErrorMsg(getActivity(), getResources().getString(R.string.date_jx_notice));
                    return false;
                }
                if (startDate <= tempstartDate && endDate >= temendDate) {
                    DialogUtil.showErrorMsg(getActivity(), getResources().getString(R.string.date_jx_notice));
                    return false;
                }
            }
            priceBean.setCurrency(currencyId);
            List<ItemsBean> itemsBeanList = new ArrayList<>();
            pricetotalBean.setItems(itemsBeanList);
            int totalHours = 0;
            ArrayList<Period> periods = new ArrayList<>();
            for (int j = 0; j < dateViewHolder.llytTimeContainer.getChildCount(); j++) {
                ItemsBean itemsBean = new ItemsBean();
                View timeChildView = dateViewHolder.llytTimeContainer.getChildAt(j);
                TimeViewHolder timeViewHolder = (TimeViewHolder) timeChildView.getTag();
                int startSelectPos = timeViewHolder.spStartTime.getSelectedItemPosition();
                if (startSelectPos == 0) {
                    ToastUtil.showMessage(getString(R.string.select_start_time));
                    return false;
                }
                String startTime = timeViewHolder.spStartTime.getSelectedItem().toString().split(":")[0];
                itemsBean.setBeginHour(startTime);
                int endSelectPos = timeViewHolder.spEndTime.getSelectedItemPosition();
                if (endSelectPos == 0) {
                    ToastUtil.showMessage(getString(R.string.select_end_time));
                    return false;
                }
                String endTime = timeViewHolder.spEndTime.getSelectedItem().toString().split(":")[0];
                itemsBean.setEndHour(endTime);
                int startTimeInt = Integer.valueOf(startTime);
                int endTimeInt = Integer.valueOf(endTime);
                if (startTimeInt >= endTimeInt) {
                    ToastUtil.showMessage(R.string.check_time_range);
                    return false;
                }
                Period period = new Period();
                period.setStartTime(startTimeInt);
                period.setEndTime(endTimeInt);
                periods.add(period);
                totalHours += endTimeInt - startTimeInt;
                String priceTxt = timeViewHolder.etPrice.getText().toString().trim();
                //电价设置范围1~3
                String regex = "^((([1-9]\\d?)|0)(\\.\\d{0,2})?)|100$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(priceTxt);
                if (priceTxt.isEmpty()) {
                    ToastUtil.showMessage(getString(R.string.input_price));
                    return false;
                } else if (!(matcher.matches())) {
                    ToastUtil.showMessage(getString(R.string.input_corret_price));
                    return false;
                } else {
                    try {
                        float price = Float.valueOf(priceTxt);
                        if (price < 0 || price > 10) {
                            ToastUtil.showMessage(R.string.input_corret_price);
                            return false;
                        }
                        itemsBean.setPrice(String.valueOf(price));
                    } catch (NumberFormatException e) {
                        ToastUtil.showMessage(R.string.input_corret_price);
                        return false;
                    }
                }
                itemsBeanList.add(itemsBean);
            }
            //判断电价设置时间是否有交叉
            if (ElectricityPriceUtils.timeCross(periods)) {
                ToastUtil.showMessage(R.string.check_time_range);
                return false;
            }
            if (totalHours != 24) {
                ToastUtil.showMessage(R.string.check_time_range);
                return false;
            }
        }
        //判断日期是否交叉
        if (ElectricityPriceUtils.dateCross(periodsDate)) {
            DialogUtil.showErrorMsg(getActivity(), getResources().getString(R.string.date_jx_notice));
            return false;
        }
        //判断日期是否设置满一年
        if (!ElectricityPriceUtils.priceSetHaveOneYear(periodsDate)) {
            DialogUtil.showErrorMsg(getActivity(), getResources().getString(R.string.set_price_one_year_str));
            return false;
        }
        return true;
    }

    /**
     * 获取并网类型提交数据
     *
     * @return
     */
    private String getCurrencyId() {
        String currencyId;
        String currencyTxt = spCurrencySetting.getSelectedItem().toString();
        if (currencyTxt.equals(getString(R.string.rmb_name))) {
            currencyId = "1";
        } else if (currencyTxt.equals(getString(R.string.dollar_name))) {
            currencyId = "2";
        } else if (currencyTxt.equals(getString(R.string.yen_name))) {
            currencyId = "3";
        } else if (currencyTxt.equals(getString(R.string.euro_name))) {
            currencyId = "4";
        } else if (currencyTxt.equals(getString(R.string.pound_name))) {
            currencyId = "5";
        } else {
            currencyId = null;
        }
        return currencyId;
    }

    /**
     * 显示日期选择框
     */
    public void showDateDialog(final TextView tvDate) {
        selectTime = (long) tvDate.getTag();
        DatePikerDialog datePikerDialog = new DatePikerDialog(getActivity(), selectTime, new DatePikerDialog.OnDateFinish() {
            @Override
            public void onDateListener(long date) {
                tvDate.setText(Utils.getFormatTimeMMDD(date));
                tvDate.setTag(date);
                selectTime = date;
            }

            @Override
            public void onSettingClick() {

            }
        });
        datePikerDialog.show(100, startDate, endDate);
    }

    @Override
    public void requestData() {
        HashMap<String, String> params = new HashMap<>();
        //自定义电价
        if (changeStationInfo.getUseDefaultPrice() == 1) {
            checkBoxMorenPrice.setChecked(false);
            params.put("domainId", domainId);
            params.put("stationId", stationId);
            isUseDefaultPrice = false;
            if (defaultGridPrice != null && defaultGridPrice.getData() != null && defaultGridPrice.getData().size() != 0) {
                linearLayoutMorenPrice.setVisibility(View.VISIBLE);
            } else {
                linearLayoutMorenPrice.setVisibility(View.GONE);
            }
        } else if (changeStationInfo.getUseDefaultPrice() == 0) {//系统默认电价
            checkBoxMorenPrice.setChecked(true);
            isUseDefaultPrice = true;
            params.put("secondDomain", changeStationInfo.getSecDomainId() + "");
            params.put("stationId", "system");
            linearLayoutMorenPrice.setVisibility(View.VISIBLE);
        }
        createStationPresenter.requestGridPreice(params);
    }

    /**
     * 获取系统默认电价
     */
    public void requestSysPreice() {
        HashMap<String, String> params = new HashMap<>();
        params.put("secondDomain", changeStationInfo.getSecDomainId() + "");
        params.put("stationId", "system");
        createStationPresenter.requestGridPreice(params);
    }

    /**
     * 获取用户自定义电价
     */
    public void requestUserPreice() {
        HashMap<String, String> params = new HashMap<>();
        params.put("domainId", domainId);
        params.put("stationId", stationId);
        createStationPresenter.requestGridPreice(params);
    }

    @Override
    public void preStep() {

    }

    @Override
    public void cancelStep() {

    }

    @Override
    public void nextStep() {

    }

    @Override
    public void getData(BaseEntity baseEntity) {
        if (isAdded()) {
            dismissLoading();
            if (baseEntity == null) {
                return;
            }
            if (baseEntity instanceof GridPriceInfo) {
                GridPriceInfo gridPriceInfo = (GridPriceInfo) baseEntity;
                GridPrice gridPrice = gridPriceInfo.getGridPrice();
                if (gridPrice != null && gridPrice.getData() != null) {
                    setGridPrice(gridPrice);
                }
            } else if (baseEntity instanceof ChangeStationResultInfo) {
                changeStationInfo.setUseDefaultPrice(1);
            } else if (baseEntity instanceof ResultInfo) {
                ResultInfo resultInfo = (ResultInfo) baseEntity;
                if (resultInfo.isSuccess()) {
                    DialogUtil.showErrorMsg(getActivity(), getResources().getString(R.string.save_success));
                } else {
                    DialogUtil.showErrorMsg(getActivity(), getResources().getString(R.string.save_failed));
                }
            }
        }

    }

    @Override
    public void createStationSuccess() {

    }

    @Override
    public void createStationFail(int failCode,String data) {

    }

    @Override
    public void uploadResult(boolean ifSuccess, String key) {

    }

    public void setDefaultGridPrice(GridPrice defaultGridPrice) {
        this.defaultGridPrice = defaultGridPrice;
    }

    class DateViewHolder {
        TextView tvStartDate;
        TextView tvEndDate;
        ImageView ivDateAdd;
        ImageView ivDateSub;
        LinearLayout llytTimeContainer;
    }

    class TimeViewHolder {
        Spinner spStartTime;
        Spinner spEndTime;
        ImageView ivTimeAdd;
        ImageView ivTimeSub;
        EditText etPrice;
        TextView tvPriceUnit;//电价提示文本(带单位)
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

    //将当前时间戳转化成当天0:0:0的时间戳
    private long getHandledTime2001(long sourceTime) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+0"));
        cal.setTimeInMillis(sourceTime);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        Calendar cal2001 = Calendar.getInstance();
        cal2001.set(2001, month, day);
        cal2001.set(Calendar.MILLISECOND, 0);
        cal2001.set(Calendar.SECOND, 0);
        cal2001.set(Calendar.MINUTE, 0);
        cal2001.set(Calendar.HOUR_OF_DAY, 0);
        return cal2001.getTimeInMillis();
    }

    //将当前时间戳转化成当天23:59:59:999的时间戳
    private long getHandledEndTime2001(long sourceTime) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+0"));
        cal.setTimeInMillis(sourceTime);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        Calendar cal2001 = Calendar.getInstance();
        cal2001.set(2001, month, day);
        cal2001.set(Calendar.MILLISECOND, 999);
        cal2001.set(Calendar.SECOND, 59);
        cal2001.set(Calendar.MINUTE, 59);
        cal2001.set(Calendar.HOUR_OF_DAY, 23);
        return cal2001.getTimeInMillis();
    }

    /**
     * 更新数据
     */
    public void updateInfo() {
        UpdataPriceReq updataPriceReq = new UpdataPriceReq();
        List<UpdataPriceReq.PriceTotalBean> pricetotalBeanList = new ArrayList<>();
        Object tag;
        //wujing修改
        long tempstartDate = 0;
        long temendDate = 0;
        for (int i = 0; i < llytDateContainer.getChildCount(); i++) {
            UpdataPriceReq.PriceTotalBean pricetotalBean = new UpdataPriceReq.PriceTotalBean();
            pricetotalBeanList.add(pricetotalBean);
            UpdataPriceReq.PriceTotalBean.PriceBean priceBean = new UpdataPriceReq.PriceTotalBean.PriceBean();
            pricetotalBean.setPrice(priceBean);
            View dateChildView = llytDateContainer.getChildAt(i);
            DateViewHolder dateViewHolder = (DateViewHolder) dateChildView.getTag();
            tag = dateViewHolder.tvStartDate.getTag();
            if (tag == null || (long) tag == getHandledTime(System.currentTimeMillis())) {
                ToastUtil.showMessage(getString(R.string.select_start_date));
                return;
            }
            long startDate = getHandledTime2001((long) tag);
            tag = dateViewHolder.tvEndDate.getTag();
            if (tag == null || (long) tag == getHandledTime(System.currentTimeMillis())) {
                ToastUtil.showMessage(getString(R.string.select_end_date));
                return;
            }
            //宋平修改
            long endDate = getHandledTime2001((long) tag);
            priceBean.setBeginDate(startDate);
            priceBean.setEndDate(endDate);
            priceBean.setStationCode(changeStationInfo.getStationCode());
            priceBean.setDomainId(changeStationInfo.getDomainId() + "");
            if (endDate < startDate) {
                ToastUtil.showMessage(R.string.correct_set_data_range);
                return;
            }
            if (endDate < startDate) {
                ToastUtil.showMessage(R.string.correct_set_data_range);
                return;
            }
            if (i == 0) {
                tempstartDate = startDate;
                temendDate = endDate;
            } else {
                if (startDate >= tempstartDate && startDate <= temendDate) {
                    DialogUtil.showErrorMsg(getActivity(), getResources().getString(R.string.date_jx_notice));
                    return;
                }
                if (endDate >= tempstartDate && endDate <= temendDate) {
                    DialogUtil.showErrorMsg(getActivity(), getResources().getString(R.string.date_jx_notice));
                    return;
                }
                if (startDate <= tempstartDate && endDate >= temendDate) {
                    DialogUtil.showErrorMsg(getActivity(), getResources().getString(R.string.date_jx_notice));
                    return;
                }
            }
            List<UpdataPriceReq.PriceTotalBean.ItemsBean> itemsBeanList = new ArrayList<>();
            pricetotalBean.setItems(itemsBeanList);
            int totalHours = 0;
            for (int j = 0; j < dateViewHolder.llytTimeContainer.getChildCount(); j++) {
                UpdataPriceReq.PriceTotalBean.ItemsBean itemsBean = new UpdataPriceReq.PriceTotalBean.ItemsBean();
                View timeChildView = dateViewHolder.llytTimeContainer.getChildAt(j);
                TimeViewHolder timeViewHolder = (TimeViewHolder) timeChildView.getTag();
                int startSelectPos = timeViewHolder.spStartTime.getSelectedItemPosition();
                if (startSelectPos == 0) {
                    ToastUtil.showMessage(getString(R.string.select_start_time));
                    return;
                }
                String startTime = timeViewHolder.spStartTime.getSelectedItem().toString().split(":")[0];
                itemsBean.setBeginHour(startTime);
                int endSelectPos = timeViewHolder.spEndTime.getSelectedItemPosition();
                if (endSelectPos == 0) {
                    ToastUtil.showMessage(getString(R.string.select_end_time));
                    return;
                }
                String endTime = timeViewHolder.spEndTime.getSelectedItem().toString().split(":")[0];
                itemsBean.setEndHour(endTime);
                int startTimeInt = Integer.valueOf(startTime);
                int endTimeInt = Integer.valueOf(endTime);
                if (startTimeInt >= endTimeInt) {
                    ToastUtil.showMessage(R.string.check_time_range);
                    return;
                }
                totalHours += endTimeInt - startTimeInt;
                String priceTxt = timeViewHolder.etPrice.getText().toString().trim();
                //电价设置范围1~3
                String regex = "^((([1-9]\\d?)|0)(\\.\\d{0,2})?)|100$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(priceTxt);
                if (priceTxt.isEmpty()) {
                    ToastUtil.showMessage(getString(R.string.input_price));
                    return;
                } else if (!(matcher.matches())) {
                    ToastUtil.showMessage(getString(R.string.input_corret_price));
                    return;
                } else {
                    try {
                        float price = Float.valueOf(priceTxt);
                        if (price < 0 || price > 10) {
                            ToastUtil.showMessage(R.string.input_corret_price);
                            return;
                        }
                        itemsBean.setPrice(String.valueOf(price));
                    } catch (NumberFormatException e) {
                        ToastUtil.showMessage(R.string.input_corret_price);
                        return;
                    }
                }
                itemsBeanList.add(itemsBean);
            }
            if (totalHours != 24) {
                ToastUtil.showMessage(R.string.check_time_range);
                return;
            }
        }
        Gson gson = new Gson();
        updataPriceReq.setPriceTotal(pricetotalBeanList);
        updataPriceReq.setStationCode(changeStationInfo.getStationCode());
        String s = gson.toJson(updataPriceReq);
        //设置自定义电价请求
        changeStationPresenter.requestUpdatePrice(s);
        UpdateUseDefaultPrice updateUseDefaultPrice = new UpdateUseDefaultPrice();
        updateUseDefaultPrice.setType("defaultPrice");
        UpdateUseDefaultPrice.StationBean station = new UpdateUseDefaultPrice.StationBean();
        station.setId(changeStationInfo.getId() + "");
        if (isUseDefaultPrice) {
            station.setUseDefaultPrice("0");
        } else {
            station.setUseDefaultPrice("1");
        }
        updateUseDefaultPrice.setStation(station);
        //改变设置默认电价状态
        changeStationPresenter.requestStationUpdate(gson.toJson(updateUseDefaultPrice));

    }

    public void canEdt(boolean canEdit) {
        isEndle = canEdit;
        if (canEdit) {
            checkBoxMorenPrice.setEnabled(true);
        } else {
            checkBoxMorenPrice.setEnabled(false);
        }
        if (canEdit && !isUseDefaultPrice) {
            int childCount = llytDateContainer.getChildCount();
            if (childCount == 1) {
                View childAt = llytDateContainer.getChildAt(0);
                DateViewHolder dateViewHolder = (DateViewHolder) childAt.getTag();
                dateViewHolder.ivDateAdd.setVisibility(View.VISIBLE);
                dateViewHolder.ivDateSub.setVisibility(View.INVISIBLE);
                dateViewHolder.tvStartDate.setEnabled(true);
                dateViewHolder.tvEndDate.setEnabled(true);
                int childCount1 = dateViewHolder.llytTimeContainer.getChildCount();
                if (childCount1 == 1) {
                    View childAt1 = dateViewHolder.llytTimeContainer.getChildAt(0);
                    TimeViewHolder timeViewHolder = (TimeViewHolder) childAt1.getTag();
                    timeViewHolder.ivTimeAdd.setVisibility(View.VISIBLE);
                    timeViewHolder.ivTimeSub.setVisibility(View.INVISIBLE);
                    timeViewHolder.spStartTime.setEnabled(true);
                    timeViewHolder.spEndTime.setEnabled(true);
                    timeViewHolder.etPrice.setEnabled(true);
                } else {
                    for (int j = 0; j < childCount1; j++) {
                        View childAt1 = dateViewHolder.llytTimeContainer.getChildAt(j);
                        TimeViewHolder timeViewHolder = (TimeViewHolder) childAt1.getTag();
                        timeViewHolder.ivTimeAdd = (ImageView) childAt1.findViewById(R.id.iv_time_add);
                        timeViewHolder.ivTimeSub = (ImageView) childAt1.findViewById(R.id.iv_time_sub);
                        timeViewHolder.spStartTime.setEnabled(true);
                        timeViewHolder.spEndTime.setEnabled(true);
                        timeViewHolder.etPrice.setEnabled(true);
                        if (j == 0) {
                            timeViewHolder.ivTimeSub.setVisibility(View.INVISIBLE);
                            timeViewHolder.ivTimeAdd.setVisibility(View.INVISIBLE);
                        } else if (j == childCount1 - 1) {
                            timeViewHolder.ivTimeSub.setVisibility(View.VISIBLE);
                            timeViewHolder.ivTimeAdd.setVisibility(View.VISIBLE);
                        } else {
                            timeViewHolder.ivTimeSub.setVisibility(View.VISIBLE);
                            timeViewHolder.ivTimeAdd.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            } else {
                for (int i = 0; i < childCount; i++) {
                    View childAt = llytDateContainer.getChildAt(i);
                    DateViewHolder dateViewHolder = (DateViewHolder) childAt.getTag();
                    dateViewHolder.ivDateAdd = (ImageView) childAt.findViewById(R.id.iv_date_add);
                    dateViewHolder.ivDateSub = (ImageView) childAt.findViewById(R.id.iv_date_sub);
                    dateViewHolder.tvStartDate.setEnabled(true);
                    dateViewHolder.tvEndDate.setEnabled(true);
                    if (i == 0) {
                        dateViewHolder.ivDateSub.setVisibility(View.INVISIBLE);
                        dateViewHolder.ivDateAdd.setVisibility(View.INVISIBLE);
                    } else if (i == childCount - 1) {
                        dateViewHolder.ivDateSub.setVisibility(View.VISIBLE);
                        dateViewHolder.ivDateAdd.setVisibility(View.VISIBLE);
                    } else {
                        dateViewHolder.ivDateSub.setVisibility(View.VISIBLE);
                        dateViewHolder.ivDateAdd.setVisibility(View.INVISIBLE);
                    }
                    int childCount1 = dateViewHolder.llytTimeContainer.getChildCount();
                    if (childCount1 == 1) {
                        View childAt1 = dateViewHolder.llytTimeContainer.getChildAt(0);
                        TimeViewHolder timeViewHolder = (TimeViewHolder) childAt1.getTag();
                        timeViewHolder.ivTimeAdd.setVisibility(View.VISIBLE);
                        timeViewHolder.ivTimeSub.setVisibility(View.INVISIBLE);
                        timeViewHolder.spStartTime.setEnabled(true);
                        timeViewHolder.spEndTime.setEnabled(true);
                        timeViewHolder.etPrice.setEnabled(true);
                    } else {
                        for (int j = 0; j < childCount1; j++) {
                            View childAt1 = dateViewHolder.llytTimeContainer.getChildAt(j);
                            TimeViewHolder timeViewHolder = (TimeViewHolder) childAt1.getTag();
                            timeViewHolder.ivTimeAdd = (ImageView) childAt1.findViewById(R.id.iv_time_add);
                            timeViewHolder.ivTimeSub = (ImageView) childAt1.findViewById(R.id.iv_time_sub);
                            timeViewHolder.spStartTime.setEnabled(true);
                            timeViewHolder.spEndTime.setEnabled(true);
                            timeViewHolder.etPrice.setEnabled(true);
                            if (j == 0) {
                                timeViewHolder.ivTimeSub.setVisibility(View.INVISIBLE);
                                timeViewHolder.ivTimeAdd.setVisibility(View.INVISIBLE);
                            } else if (j == childCount1 - 1) {
                                timeViewHolder.ivTimeSub.setVisibility(View.VISIBLE);
                                timeViewHolder.ivTimeAdd.setVisibility(View.VISIBLE);
                            } else {
                                timeViewHolder.ivTimeSub.setVisibility(View.VISIBLE);
                                timeViewHolder.ivTimeAdd.setVisibility(View.INVISIBLE);
                            }
                        }
                    }

                }
            }
        } else {
            int childCount = llytDateContainer.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = llytDateContainer.getChildAt(i);
                DateViewHolder dateViewHolder = (DateViewHolder) childAt.getTag();
                dateViewHolder.ivDateAdd = (ImageView) childAt.findViewById(R.id.iv_date_add);
                dateViewHolder.ivDateSub = (ImageView) childAt.findViewById(R.id.iv_date_sub);
                dateViewHolder.ivDateAdd.setVisibility(View.INVISIBLE);
                dateViewHolder.ivDateSub.setVisibility(View.INVISIBLE);
                dateViewHolder.tvStartDate.setEnabled(false);
                dateViewHolder.tvEndDate.setEnabled(false);
                int childCount1 = dateViewHolder.llytTimeContainer.getChildCount();
                for (int j = 0; j < childCount1; j++) {
                    View childAt1 = dateViewHolder.llytTimeContainer.getChildAt(j);
                    TimeViewHolder timeViewHolder = (TimeViewHolder) childAt1.getTag();
                    timeViewHolder.ivTimeAdd = (ImageView) childAt1.findViewById(R.id.iv_time_add);
                    timeViewHolder.ivTimeSub = (ImageView) childAt1.findViewById(R.id.iv_time_sub);
                    timeViewHolder.ivTimeAdd.setVisibility(View.INVISIBLE);
                    timeViewHolder.ivTimeSub.setVisibility(View.INVISIBLE);
                    timeViewHolder.spStartTime.setEnabled(false);
                    timeViewHolder.spEndTime.setEnabled(false);
                    timeViewHolder.etPrice.setEnabled(false);
                }
            }
        }
    }
}

