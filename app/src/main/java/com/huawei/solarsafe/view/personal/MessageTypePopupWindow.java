package com.huawei.solarsafe.view.personal;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.Constant;

/**
 * Created by P00213 on 2016/10/21.
 */
public class MessageTypePopupWindow {
    private Context mContext;
    //全部消息
    private LinearLayout ll_all_message;
    //巡检
    private LinearLayout ll_patrol;
    //缺陷票
    private LinearLayout ll_defect_alarm;
    //公告
    private LinearLayout ll_notice;
    //设备升级
    private LinearLayout ll_update;
    //popupwindow高度
    private int pw_height;
    /**
     * PopupWindow菜单
     */
    private PopupWindow mPopupWindow;

    public MessageTypePopupWindow(Context context) {
        this.mContext = context;
        initPopupWindw();
    }

    private void initPopupWindw() {
        //默认中文布局
        View view = LayoutInflater.from(mContext).inflate(R.layout.message_type_popupwindow, null);

        mPopupWindow = new PopupWindow(view, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setOutsideTouchable(true);
        /** 为其设置背景，使得其内外焦点都可以获得 */
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow.setFocusable(true);
        pw_height = view.getHeight();
        ll_all_message = (LinearLayout) view.findViewById(R.id.ll_all_message);
        ll_patrol = (LinearLayout) view.findViewById(R.id.ll_patrol);
        ll_defect_alarm = (LinearLayout) view.findViewById(R.id.ll_defect_alarm);
        ll_notice = (LinearLayout) view.findViewById(R.id.ll_notice);
        ll_update = (LinearLayout) view.findViewById(R.id.ll_update);

        ll_all_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSelectFinish != null) {
                    onSelectFinish.selectItem(mContext.getString(R.string.all_msg), Constant.InforMationType.ALL);
                }
            }
        });

        ll_patrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSelectFinish != null) {
                    onSelectFinish.selectItem(mContext.getString(R.string.mobile_patrol_inspection), Constant.InforMationType.PATROL);
                }
            }
        });

        ll_defect_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSelectFinish != null) {
                    onSelectFinish.selectItem(mContext.getString(R.string.failed_order), Constant.InforMationType.DEFECT);
                }
            }
        });
        ll_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSelectFinish != null) {
                    onSelectFinish.selectItem(mContext.getString(R.string.announcement), Constant.InforMationType.NOTICE);
                }
            }
        });

        ll_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onSelectFinish != null) {
                    onSelectFinish.selectItem(mContext.getString(R.string.dev_update), Constant.InforMationType.UPDATE);
                }
            }
        });
    }

    public void show(View view) {
        //popupwindow相对view位置x轴偏移量
        View viewTemp = mPopupWindow.getContentView();
        viewTemp.measure(0, 0);
        int width = viewTemp.getMeasuredWidth();
        int xOffset = (view.getWidth() - width) / 2;
        mPopupWindow.showAsDropDown(view, xOffset, 0);
    }

    /**
     * 退出popupwindow
     */
    public void dissmiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    /**
     * popupwindow是否正在显示
     */
    public boolean isShowing() {
        if (mPopupWindow != null) {
            return mPopupWindow.isShowing();
        }
        return false;
    }

    //popupwindow选择回调
    public interface OnSelectFinish {
        void selectItem(String name, int messageType);
    }

    private OnSelectFinish onSelectFinish;

    public void setOnSelectFinish(OnSelectFinish onSelectFinish) {
        this.onSelectFinish = onSelectFinish;
    }
}
