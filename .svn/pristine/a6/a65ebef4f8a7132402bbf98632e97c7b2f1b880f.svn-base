package com.huawei.solarsafe.utils.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by p00507
 * on 2017/9/21.
 */

public class QuickSideBarTipsView extends RelativeLayout  {
    private QuickSideBarTipsItemView mTipsView;

    public QuickSideBarTipsView(Context context) {
        this(context, null);
    }

    public QuickSideBarTipsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickSideBarTipsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mTipsView = new QuickSideBarTipsItemView(context,attrs);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(mTipsView,layoutParams);
    }


    public void setText(String text,int poistion, float y){
        mTipsView.setText(text);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mTipsView.getLayoutParams();
        layoutParams.topMargin = (int)(y - getWidth()/2.8);
        mTipsView.setLayoutParams(layoutParams);
    }
}
