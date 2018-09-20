package com.huawei.solarsafe.utils.customview.sdlv;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

class ItemBackGroundLayout extends ViewGroup implements View.OnClickListener {
    /* 下一个View的距离 */
    private int mMarginLeft = 0;
    private int mMarginRight = 0;

    private Map<View, Integer> mViewPositionMap;
    private int mDirection = -1;

    private OnMenuItemClickListener mOnMenuItemClickListener;

    public ItemBackGroundLayout(Context context) {
        this(context, null);
    }

    public ItemBackGroundLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemBackGroundLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mViewPositionMap = new HashMap<>();
        setVisibility(GONE);
    }

    protected void addMenuItem(MenuItem menuItem, int position) {
        int count = getChildCount();
        BaseLayout parent = new SDMenuItemView(getContext(), menuItem);
        parent.build();
        addView(parent, count);
        mViewPositionMap.put(parent, position);
        parent.setOnClickListener(this);
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        mOnMenuItemClickListener = onMenuItemClickListener;
    }

    public void setDirection(int direction) {
        mDirection = direction;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int total = getChildCount();
        for (int i = 0; i < total; i++) {
            BaseLayout view = (BaseLayout) getChildAt(i);
            MenuItem menuItem = view.mMenuItem;
            measureChild(view, MeasureSpec.makeMeasureSpec(menuItem.width, MeasureSpec.EXACTLY), heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int total = getChildCount();
        mMarginLeft = 0;
        mMarginRight = getMeasuredWidth();
        for (int i = 0; i < total; i++) {
            BaseLayout view = (BaseLayout) getChildAt(i);
            MenuItem menuItem = view.mMenuItem;
            if (menuItem.direction == MenuItem.DIRECTION_LEFT) {
                view.layout(mMarginLeft, t, menuItem.width + mMarginLeft, b);
                mMarginLeft += menuItem.width;
            } else {
                view.layout(mMarginRight - menuItem.width, t, mMarginRight, b);
                mMarginRight -= menuItem.width;
            }
        }
    }

    protected boolean hasMenuItemViews() {
        return mViewPositionMap.size() != 0;
    }

    @Override
    public void onClick(View v) {
        Integer position = mViewPositionMap.get(v);
        if (mOnMenuItemClickListener != null) {
            mOnMenuItemClickListener.onClick(position, mDirection, v);
        }

    }

    protected interface OnMenuItemClickListener {
        void onClick(int position, int direction, View view);
    }
}
