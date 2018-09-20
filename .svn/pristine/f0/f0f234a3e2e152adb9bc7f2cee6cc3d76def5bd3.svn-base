package com.huawei.solarsafe.utils.customview;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.device.HouseHoldResultBean;
import com.huawei.solarsafe.bean.device.HouseholdRequestResult;
import com.huawei.solarsafe.bean.device.HouseholdSetResult;
import com.huawei.solarsafe.bean.stationmagagement.SubDev;
import com.huawei.solarsafe.view.devicemanagement.HouseHoldSetResultAdpater;
import com.huawei.solarsafe.view.login.EmptyActivity;
import com.huawei.solarsafe.view.stationmanagement.SubDevAdapter;

import java.util.ArrayList;


public class DialogUtil {
    private static final String TAG = "DialogUtil";

    /**
     * 单按钮Dialog
     *
     * @param context
     * @param msg     内容显示
     */
    public static void showErrorMsg(Context context, String msg) {
        try {
            new AlertDialog(context).builder()
                    .setMsg(msg)
                    .setCancelable(false)
                    .setNegativeButton(context.getString(R.string.determine), true, null).show();
        } catch (Exception e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e("Exception", e.toString());
        }
    }

    /**
     * 单按钮Dialog
     *
     * @param context
     * @param msg             内容显示
     * @param btn             底部按钮内容
     * @param isDismiss       点击按钮后是否隐藏Diaolg，true：隐藏，false：不隐藏
     * @param onClickListener 按钮点击事件
     */
    public static void showErrorMsgWithClick(Context context, String msg, String btn, boolean isDismiss, OnClickListener onClickListener) {
        if (context == null) {
            return;
        }
        try {
            new AlertDialog(context).builder()
                    .setMsg(msg)
                    .setCancelable(false)
                    .setNegativeButton(btn, isDismiss, onClickListener).create().show();
        } catch (Exception e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e("fusionHome", "show Dialog failed", e);
        }
    }
	public static void showReloginMsgWithClick(String msg) {
        if (GlobalConstants.isLogout){
            Intent intent = new Intent(MyApplication.getContext(), EmptyActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("msg",msg);
            MyApplication.getContext().startActivity(intent);
        }
	}


    /**
     * @param context
     * @param title
     * @param msg
     * @param ok
     * @param cancel
     * @param onClickListener
     */
    public static void showChooseDialog(Context context, String title, String msg, String ok, String cancel, OnClickListener onClickListener) {
        try {
            if (TextUtils.isEmpty(ok)) {
                ok = context.getString(R.string.determine);
            }
            if (TextUtils.isEmpty(cancel)) {
                cancel = context.getString(R.string.cancel);
            }
            if (TextUtils.isEmpty(title)) {
                new AlertDialog(context).builder()
                        .setMsg(msg)
                        .setCancelable(false)
                        .setPositiveButton(ok, onClickListener).setNegativeButton(cancel, true, null).show();
            } else {
                new AlertDialog(context).builder().setTitle(title)
                        .setMsg(msg)
                        .setCancelable(false)
                        .setPositiveButton(ok, onClickListener).setNegativeButton(cancel, true, null).show();
            }
        } catch (Exception e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e("Exception", e.toString());
        }

    }

    public static void showChooseDialog(Context context, String title, String msg, String ok, String cancel, OnClickListener onOkClickListener, OnClickListener onCancelClickListener) {
        try {
            if (TextUtils.isEmpty(ok)) {
                ok = context.getString(R.string.determine);
            }
            if (TextUtils.isEmpty(cancel)) {
                cancel = context.getString(R.string.cancel);
            }
            if (TextUtils.isEmpty(title)) {
                new AlertDialog(context).builder()
                        .setMsg(msg)
                        .setCancelable(false)
                        .setPositiveButton(ok, onOkClickListener).setNegativeButton(cancel, true, onCancelClickListener).show();
            } else {
                new AlertDialog(context).builder().setTitle(title)
                        .setMsg(msg)
                        .setCancelable(false)
                        .setPositiveButton(ok, onOkClickListener).setNegativeButton(cancel, true, onCancelClickListener).show();
            }
        } catch (Exception e) {
            // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
            Log.e("Exception", e.toString());
        }
    }




    /**
     * 显示选择对话框 (取消在左,确定在右,取消文本颜色为灰)
     * @param context
     * @param title
     * @param msg
     * @param ok
     * @param cancel
     * @param onOkClickListener
     * @param onCancelClickListener
     */
    public static void showReverseChooseDialog(Context context,String title,String msg, String ok, String cancel, OnClickListener onOkClickListener, OnClickListener onCancelClickListener){
        try {
            if (TextUtils.isEmpty(ok)) {
                ok = context.getString(R.string.determine);
            }
            if (TextUtils.isEmpty(cancel)) {
                cancel = context.getString(R.string.cancel);
            }
            if (TextUtils.isEmpty(title)) {
                new AlertDialog(context).builder()
                        .setMsg(msg)
                        .setCancelable(false)
                        .setPositiveButton(cancel, true,onCancelClickListener)
                        .setNegativeButton(ok,onOkClickListener)
                        .setPositiveTextColor(context.getResources().getColor(R.color.actionsheet_gray))
                        .show();
            } else {
                new AlertDialog(context).builder().setTitle(title)
                        .setMsg(msg)
                        .setCancelable(false)
                        .setPositiveButton(cancel, true,onCancelClickListener)
                        .setNegativeButton(ok,onOkClickListener)
                        .setPositiveTextColor(context.getResources().getColor(R.color.actionsheet_gray))
                        .show();
            }
        } catch (Exception e) {
            Log.e(TAG, "showReverseChooseDialog: "+e.getMessage());
        }
    }

    /**
     * 证书验证弹窗
     * @param mContext  上下文
     * @param yes       用户确认后的监听
     * @param no        用户取消后的监听
     * @return           返回弹窗对象本身
     */
    public static Dialog showCerDialog(Context mContext, OnClickListener yes, OnClickListener no) {
        View mCerLayout = LayoutInflater.from(mContext).inflate(R.layout.cer_dialog, null);
        Button yesBtn = (Button) mCerLayout.findViewById(R.id.cer_dialog_yes);
        Button noBtn = (Button) mCerLayout.findViewById(R.id.cer_dialog_no);
        CheckBox cb = (CheckBox) mCerLayout.findViewById(R.id.cer_dialog_cb);
        cb.setVisibility(View.GONE);
        yesBtn.setOnClickListener(yes);
        noBtn.setOnClickListener(no);

        Dialog cerDialog = new Dialog(mContext);
        cerDialog.setCanceledOnTouchOutside(false);
        cerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        cerDialog.setContentView(mCerLayout,params);
        cerDialog.show();
        return cerDialog;
    }

    public static Dialog showPushCerDialog(Context mContext, OnClickListener yes, OnClickListener no){
        View mCerLayout = LayoutInflater.from(mContext).inflate(R.layout.push_cer_dialog, null);
        Button yesBtn = (Button) mCerLayout.findViewById(R.id.cer_dialog_yes);
        Button noBtn = (Button) mCerLayout.findViewById(R.id.cer_dialog_no);
        yesBtn.setOnClickListener(yes);
        noBtn.setOnClickListener(no);
        Dialog cerDialog = new Dialog(mContext);
        cerDialog.setCanceledOnTouchOutside(false);
        cerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        cerDialog.setContentView(mCerLayout,params);
        cerDialog.show();
        return cerDialog;
    }

    /**
     * root权限弹窗
     * @param mContext  上下文
     * @param yes       用户确认后的监听
     * @param no        用户取消后的监听
     * @return           返回弹窗对象本身
     */
    public static Dialog showRootDialog(Context mContext, OnClickListener yes, OnClickListener no,String tips,int tipsColor,int Visibility) {
        View mCerLayout = LayoutInflater.from(mContext).inflate(R.layout.cer_dialog, null);
        Button yesBtn = (Button) mCerLayout.findViewById(R.id.cer_dialog_yes);
        Button noBtn = (Button) mCerLayout.findViewById(R.id.cer_dialog_no);
        CheckBox cb = (CheckBox) mCerLayout.findViewById(R.id.cer_dialog_cb);
        TextView tipTv = (TextView) mCerLayout.findViewById(R.id.tv_tip);
        mCerLayout.findViewById(R.id.beizhu).setVisibility(Visibility);
        yesBtn.setOnClickListener(yes);
        noBtn.setOnClickListener(no);
        tipTv.setTextColor(tipsColor);
        tipTv.setText(tips);
        cb.setVisibility(View.GONE);

        Dialog cerDialog = new Dialog(mContext);
        cerDialog.setCanceledOnTouchOutside(false);
        cerDialog.setCancelable(false);
        cerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        cerDialog.setContentView(mCerLayout,params);
        cerDialog.show();
        return cerDialog;
    }

    /**
     * 运维位置上报弹窗
     * @param mContext  上下文
     * @param close       点击关闭后的监听
     * @param notice        点击通知后的监听
     * @param noTips    不再提示框的监听
     * @return           返回弹窗对象本身
     */
    public static Dialog showUploadLocationNoticeDialog(Context mContext, OnClickListener close, OnClickListener notice, CompoundButton.OnCheckedChangeListener noTips) {
        View mCerLayout = LayoutInflater.from(mContext).inflate(R.layout.upload_location_notice_dialog, null);
        Button closeBtn = (Button) mCerLayout.findViewById(R.id.btn_close);
        Button noticeBtn = (Button) mCerLayout.findViewById(R.id.btn_notice);
        CheckBox cb = (CheckBox) mCerLayout.findViewById(R.id.cer_dialog_cb);
        TextView titleTv = (TextView) mCerLayout.findViewById(R.id.tv_title);
        TextView tipTv = (TextView) mCerLayout.findViewById(R.id.tv_tip);
        closeBtn.setOnClickListener(close);
        noticeBtn.setOnClickListener(notice);
        cb.setOnCheckedChangeListener(noTips);

        Dialog cerDialog = new Dialog(mContext);
        cerDialog.setCanceledOnTouchOutside(false);
        cerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        cerDialog.setContentView(mCerLayout,params);
        cerDialog.show();
        return cerDialog;
    }

    /**
     * 设置运维位置上报弹窗
     * @param mContext
     * @param close 用户关闭监听
     * @param open  用户开启监听
     * @param notice   用户提示监听
     * @return
     */
    public static Dialog showSettingUploadLocationDialog(Context mContext, OnClickListener close, OnClickListener open, OnClickListener notice) {
        View mCerLayout = LayoutInflater.from(mContext).inflate(R.layout.setting_location_permission_dialog, null);
        TextView closeTv = (TextView) mCerLayout.findViewById(R.id.tv_close);
        TextView openTv = (TextView) mCerLayout.findViewById(R.id.tv_open);
        TextView noticeTv = (TextView) mCerLayout.findViewById(R.id.tv_notice);
        closeTv.setOnClickListener(close);
        openTv.setOnClickListener(open);
        noticeTv.setOnClickListener(notice);

        Dialog cerDialog = new Dialog(mContext);
        cerDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        cerDialog.setContentView(mCerLayout,params);
        cerDialog.show();
        return cerDialog;
    }


    public static void showIntroductionDialog(Context mContext, String content) {
        View mAboutLayout = LayoutInflater.from(mContext).inflate(R.layout.introduction, null);
        TextView contentTv = (TextView) mAboutLayout.findViewById(R.id.content);
        contentTv.setText(content);
        TextView cancle = (TextView) mAboutLayout.findViewById(R.id.tv_right);
        final Dialog aboutDialog = new Dialog(mContext);
        aboutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        aboutDialog.setContentView(mAboutLayout, params);
        aboutDialog.show();
        cancle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutDialog.dismiss();
            }
        });
    }

    public static void showSubDevsDialog(final Context mContext, SubDev[] subDevs) {
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        final View view = LayoutInflater.from(mContext).inflate(R.layout.sub_dev_view, null);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.sub_dev_view);
        final Dialog dialog = new Dialog(mContext, R.style.AlertDialogStyle);
        ListView listView = (ListView) view.findViewById(R.id.sub_dev_listview);
        dialog.setContentView(view);
        linearLayout.setLayoutParams(new FrameLayout.LayoutParams((int) (defaultDisplay.getWidth() * 0.85), (int) (defaultDisplay.getHeight() * 0.6)));
        dialog.setCanceledOnTouchOutside(true);
        listView.setAdapter(new SubDevAdapter(mContext, subDevs));
        dialog.show();
    }

    public static void showHouseHoldSetResultDialog(final Context mContext, HouseholdRequestResult result, String devName) {
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        final View view = LayoutInflater.from(mContext).inflate(R.layout.household_set_result_view, null);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.sub_dev_view);
        final Dialog dialog = new Dialog(mContext, R.style.AlertDialogStyle);
        TextView devNameTextView = (TextView) view.findViewById(R.id.dev_name);
        TextView successDetailText = (TextView) view.findViewById(R.id.result_detail);
        TextView successTextView = (TextView) view.findViewById(R.id.set_result);
        devNameTextView.setText(devName);
        ListView listView = (ListView) view.findViewById(R.id.set_result_listview);
        ArrayList<HouseHoldResultBean> houseHoldResultBeans = new ArrayList<>();
        String resultString = result.getResultString();
        HouseholdSetResult householdSetResult = result.getHouseholdSetResult();
        if (householdSetResult != null && householdSetResult.getData() != null && householdSetResult.getData().getResult() != null
                && householdSetResult.getData().getResult().size() > 0) {
            if (householdSetResult.getData().getResult().get(0).isDevResult()) {
                successTextView.setText(R.string.success);
                successTextView.setTextColor(mContext.getResources().getColor(R.color.color_result_green));
                HouseholdSetResult.DataBean.ResultBean resultBean = householdSetResult.getData().getResult().get(0);
                successDetailText.setText(resultBean.getMsg());

                initDataResult1(houseHoldResultBeans,resultString,resultBean,mContext);
                initDataResult2(houseHoldResultBeans,resultString,resultBean,mContext);
                initDataResult3(houseHoldResultBeans,resultString,resultBean,mContext);

                HouseHoldSetResultAdpater adpater = new HouseHoldSetResultAdpater(houseHoldResultBeans, mContext);
                listView.setAdapter(adpater);
            } else {
                successTextView.setText(R.string.fail);
                successTextView.setTextColor(mContext.getResources().getColor(R.color.red));
                successDetailText.setText(householdSetResult.getData().getResult().get(0).getMsg());
                successDetailText.setTextColor(mContext.getResources().getColor(R.color.red));
            }
        }
        TextView sure = (TextView) view.findViewById(R.id.sure);
        dialog.setContentView(view);
        linearLayout.setLayoutParams(new FrameLayout.LayoutParams((int) (defaultDisplay.getWidth() * 0.85), (int) (defaultDisplay.getHeight() * 0.6)));
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        sure.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ((Activity) mContext).setResult(Activity.RESULT_OK);
                ((Activity) mContext).finish();
            }
        });
    }

    private static void initDataResult1(ArrayList<HouseHoldResultBean> houseHoldResultBeans,String resultString, HouseholdSetResult.DataBean.ResultBean resultBean, Context mContext) {
        if (resultString.contains("gridStandardCode")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.grid_code_str));
            houseHoldResultBean.setSuccess(resultBean.isGridStandardCode());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("isolation")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.gl_set_str));
            houseHoldResultBean.setSuccess(resultBean.isolation());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("outputMode")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.output_mdoe_str));
            houseHoldResultBean.setSuccess(resultBean.isOutputMode());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("PQMode")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.pq_mode));
            houseHoldResultBean.setSuccess(resultBean.isPQMode());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("errAutoStart")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.dwgzhfzdkj_str));
            houseHoldResultBean.setSuccess(resultBean.isErrAutoStart());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("errAutoGridTime")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.dwgzhfbwsj));
            houseHoldResultBean.setSuccess(resultBean.isErrAutoGridTime());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("gridRecVolUpper")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.dwcldysx_str));
            houseHoldResultBean.setSuccess(resultBean.isGridRecVolUpper());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("gridRecVolLower")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.dwcldyxx));
            houseHoldResultBean.setSuccess(resultBean.isGridRecVolLower());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("gridRecFreUpper")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.dwclplsx));
            houseHoldResultBean.setSuccess(resultBean.isGridRecFreUpper());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("gridRecFreLower")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.dwclplxx));
            houseHoldResultBean.setSuccess(resultBean.isGridRecFreLower());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("reacPowerTriggerV")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.wgbczfdy_str));
            houseHoldResultBean.setSuccess(resultBean.isReacPowerTriggerV());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("reacPowerExitV")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.wgbctcdy));
            houseHoldResultBean.setSuccess(resultBean.isReacPowerExitV());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("insulaResisPro")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.jyzkbhd_str));
            houseHoldResultBean.setSuccess(resultBean.isInsulaResisPro());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("unbalanceVolPro")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.dwdtbphbhd_str));
            houseHoldResultBean.setSuccess(resultBean.isUnbalanceVolPro());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("phaseProPoint")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.xwbhd_str));
            houseHoldResultBean.setSuccess(resultBean.isPhaseProPoint());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("phaseAngleOffPro")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.xjpybh));
            houseHoldResultBean.setSuccess(resultBean.isPhaseAngleOffPro());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("TenMinuOVProPoint")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.sfzgybhd));
            houseHoldResultBean.setSuccess(resultBean.isTenMinuOVProPoint());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("TenMinuOVProTime")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.sfzgybhsj_str));
            houseHoldResultBean.setSuccess(resultBean.isTenMinuOVProTime());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("OVPro1")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.yjgybhd_str));
            houseHoldResultBean.setSuccess(resultBean.isOVPro1());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("OVProTime1")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.yjgybhsj_str));
            houseHoldResultBean.setSuccess(resultBean.isOVProTime1());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("OVPro2")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.ejgybhd_str));
            houseHoldResultBean.setSuccess(resultBean.isOVPro2());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("OVProTime2")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.ejgybhsj_str));
            houseHoldResultBean.setSuccess(resultBean.isOVProTime2());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("OVPro3")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.sjgybhd));
            houseHoldResultBean.setSuccess(resultBean.isOVPro3());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("OVProTime3")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.sjgybhsj_str));
            houseHoldResultBean.setSuccess(resultBean.isOVProTime3());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("OVPro4")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.sjgybhd_str));
            houseHoldResultBean.setSuccess(resultBean.isOVPro4());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("OVProTime4")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.sjgybhsj));
            houseHoldResultBean.setSuccess(resultBean.isOVProTime4());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("UVPro1")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.yjcybhd_str));
            houseHoldResultBean.setSuccess(resultBean.isUVPro1());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("UVProTime1")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.yjqybhsj_str));
            houseHoldResultBean.setSuccess(resultBean.isUVProTime1());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("UVPro2")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.ejqybhd_str));
            houseHoldResultBean.setSuccess(resultBean.isUVPro2());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("UVProTime2")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.ejqybhsj_str));
            houseHoldResultBean.setSuccess(resultBean.isUVProTime2());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
    }
    private static void initDataResult2(ArrayList<HouseHoldResultBean> houseHoldResultBeans,String resultString, HouseholdSetResult.DataBean.ResultBean resultBean, Context mContext) {
        if (resultString.contains("UVPro3")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.sjqybhd_str));
            houseHoldResultBean.setSuccess(resultBean.isUVPro3());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("UVProTime3")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.sjqybhsj_str));
            houseHoldResultBean.setSuccess(resultBean.isUVProTime3());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("UVPro4")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.ssjqybhd_str));
            houseHoldResultBean.setSuccess(resultBean.isUVPro4());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("UVProTime4")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.ssjqybhsj));
            houseHoldResultBean.setSuccess(resultBean.isUVProTime4());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("OFPro1")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.yjgpbhd_str));
            houseHoldResultBean.setSuccess(resultBean.isOFPro1());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("OFProTime1")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.yjgpbhsj_str));
            houseHoldResultBean.setSuccess(resultBean.isOFProTime1());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("OFPro2")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.ejgpbhd_str));
            houseHoldResultBean.setSuccess(resultBean.isOFPro2());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("OFProTime2")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.ejgpbhsj_str));
            houseHoldResultBean.setSuccess(resultBean.isOFProTime2());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("UFPro1")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.yjqpbhd_str));
            houseHoldResultBean.setSuccess(resultBean.isUFPro1());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("UFProTime1")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.yjqpbhsj));
            houseHoldResultBean.setSuccess(resultBean.isUFProTime1());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("UFPro2")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.ejqpbhd_str));
            houseHoldResultBean.setSuccess(resultBean.isUFPro2());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("UFProTime2")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.ejqpbhsj_str));
            houseHoldResultBean.setSuccess(resultBean.isUFProTime2());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("MPPTMPScanning")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.mppt_dfsm_str));
            houseHoldResultBean.setSuccess(resultBean.isMPPTMPScanning());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("MPPTScanInterval")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.mppt_smjssj_str));
            houseHoldResultBean.setSuccess(resultBean.isMPPTScanInterval());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("RCDEnhance")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.rcd_zq_str));
            houseHoldResultBean.setSuccess(resultBean.isRCDEnhance());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("reacPowerOutNight")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.yjwg_str));
            houseHoldResultBean.setSuccess(resultBean.isReacPowerOutNight());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("powerQuaOptMode")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.dnzlyh_mdoe_str));
            houseHoldResultBean.setSuccess(resultBean.isPowerQuaOptMode());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("PVModuleType")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.dcblx_str));
            houseHoldResultBean.setSuccess(resultBean.isPVModuleType());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("crySiliPVCompMode")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.jgdcbbc_mode_str));
            houseHoldResultBean.setSuccess(resultBean.isCrySiliPVCompMode());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("stringConnection")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.zcljfs_str));
            houseHoldResultBean.setSuccess(resultBean.isStringConnection());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("commuInterOff")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.txdlzdgj_str));
            houseHoldResultBean.setSuccess(resultBean.isCommuInterOff());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("commuResumOn")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.txhfzdkj_str));
            houseHoldResultBean.setSuccess(resultBean.isCommuResumOn());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("commuInterTime")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.txzdsj_str));
            houseHoldResultBean.setSuccess(resultBean.isCommuInterJudgeTime());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("softStartTime")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.kjrqdsj_str));
            houseHoldResultBean.setSuccess(resultBean.isSoftStartTime());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("AFCI")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName("AFCI");
            houseHoldResultBean.setSuccess(resultBean.isAFCI());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("arcDetecAdapMode")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.afci_jcsy_mdoe_str));
            houseHoldResultBean.setSuccess(resultBean.isArcDetecAdapMode());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("OVGRLinkOff")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.ovgr_glgj_str));
            houseHoldResultBean.setSuccess(resultBean.isOVGRLinkOff());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("dryContactFunc")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.gjd_gn_str));
            houseHoldResultBean.setSuccess(resultBean.isDryContactFunc());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("hibernateNight")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.yjxm_str));
            houseHoldResultBean.setSuccess(resultBean.isHibernateNight());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("PLCCommu")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.plc_tx_str));
            houseHoldResultBean.setSuccess(resultBean.isPLCCommu());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("delayedAct")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.ycsj_str));
            houseHoldResultBean.setSuccess(resultBean.isDelayedAct());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("stringIntelMonitor")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.zcznjk_str));
            houseHoldResultBean.setSuccess(resultBean.isStringIntelMonitor());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("stringDetecRefAsyCoeffi")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.zcjcckbdcxs_str));
            houseHoldResultBean.setSuccess(resultBean.isStringDetecRefAsyCoeffi());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("stringDetecStartPowPer")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.zcjcqdglbfb_str));
            houseHoldResultBean.setSuccess(resultBean.isStringDetecStartPowPer());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("LVRT")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName("LVRT");
            houseHoldResultBean.setSuccess(resultBean.isLVRT());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
    }
    private static void initDataResult3(ArrayList<HouseHoldResultBean> houseHoldResultBeans,String resultString, HouseholdSetResult.DataBean.ResultBean resultBean, Context mContext) {
        if (resultString.contains("LVRTThreshold")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.lvrt_cffz_str));
            houseHoldResultBean.setSuccess(resultBean.isLVRTThreshold());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("LVRTUndervolProShiled")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.lvrtqcybhpb_str));
            houseHoldResultBean.setSuccess(resultBean.isLVRTUndervolProShiled());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("LVRTReacPowCompPowFac")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.lvrt_wgbcyz_str));
            houseHoldResultBean.setSuccess(resultBean.isLVRTReacPowCompPowFac());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("highVolRideThro")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName("HVRT");
            houseHoldResultBean.setSuccess(resultBean.isHighVolRideThro());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("activeIsland")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.gdbh_str));
            houseHoldResultBean.setSuccess(resultBean.isActiveIsland());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("pasIsland")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.bdgd_str));
            houseHoldResultBean.setSuccess(resultBean.isPasIsland());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("volRiseSup")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.dyssyz_str));
            houseHoldResultBean.setSuccess(resultBean.isVolRiseSup());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("volRiseSupReacAdjPoint")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.dyssyzwgtjd_str));
            houseHoldResultBean.setSuccess(resultBean.isVolRiseSupReacAdjPoint());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("volRiseSupActAdjPoint")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.dyssyzygjed_str));
            houseHoldResultBean.setSuccess(resultBean.isVolRiseSupActAdjPoint());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("freChangeRatePro")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.plbhbf_str));
            houseHoldResultBean.setSuccess(resultBean.isFreChangeRatePro());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("freChangeRateProPoint")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.plbhlbhd_str));
            houseHoldResultBean.setSuccess(resultBean.isFreChangeRateProPoint());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("freChangeRateProTime")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.plbhlbhsj_str));
            houseHoldResultBean.setSuccess(resultBean.isFreChangeRateProTime());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("softStaTimeAftGridFail")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.dwgzkfrqdsj_str));
            houseHoldResultBean.setSuccess(resultBean.isSoftStaTimeAftGridFail());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("remotePowerScheduling")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.ycgldd_str));
            houseHoldResultBean.setSuccess(resultBean.isRemotePowerScheduling());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("shelduledInsHoldTime")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.ddzlwcsj_str));
            houseHoldResultBean.setSuccess(resultBean.isShelduledInsHoldTime());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("maxActPower")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.ygglmax_str));
            houseHoldResultBean.setSuccess(resultBean.isMaxActPower());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("shutAtZeroPowLim")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.xgl_gj_str));
            houseHoldResultBean.setSuccess(resultBean.isShutAtZeroPowLim());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("actPowerGradient")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.ygglbhtd_str));
            houseHoldResultBean.setSuccess(resultBean.isActPowerGradient());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("actPowerFixedDerateW")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.ygglgdzje_str));
            houseHoldResultBean.setSuccess(resultBean.isActPowerFixedDerateW());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("actPowerPercentDerate")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.ygglbfbje_str));
            houseHoldResultBean.setSuccess(resultBean.isActPowerPercentDerate());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("reactPowerGradient")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.wgglbhtd_str));
            houseHoldResultBean.setSuccess(resultBean.isReactPowerGradient());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("powerFactor")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.wugglbc_pf_str));
            houseHoldResultBean.setSuccess(resultBean.isPowerFactor());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("reactPowerCompensationQS")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.wgglbc_qs_str));
            houseHoldResultBean.setSuccess(resultBean.isReactPowerCompensationQS());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("trgFreOfOverFreDerat")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.gpjecfpl_str));
            houseHoldResultBean.setSuccess(resultBean.isTrgFreOfOverFreDerat());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("quitFreOfOverFreDerat")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.gpje_ext_str));
            houseHoldResultBean.setSuccess(resultBean.isQuitFreOfOverFreDerat());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
        if (resultString.contains("recGradOfOverFreDerat")) {
            HouseHoldResultBean houseHoldResultBean = new HouseHoldResultBean();
            houseHoldResultBean.setParamName(mContext.getString(R.string.gpje_hftd_str));
            houseHoldResultBean.setSuccess(resultBean.isRecGradOfOverFreDerat());
            houseHoldResultBeans.add(houseHoldResultBean);
        }
    }
}
