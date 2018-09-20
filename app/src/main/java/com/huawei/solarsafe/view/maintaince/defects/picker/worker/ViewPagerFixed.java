package com.huawei.solarsafe.view.maintaince.defects.picker.worker;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * 修复ViewPager滑动与PhotoView冲突的问题
 */
public class ViewPagerFixed extends android.support.v4.view.ViewPager {
    private static final String TAG = "ViewPagerFixed";

    public ViewPagerFixed(Context context) {
        super(context);
    }

    public ViewPagerFixed(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            Log.e(TAG, "onTouchEvent: " + ex.toString() );
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            Log.e(TAG, "onInterceptTouchEvent: " + ex.toString());
        }
        return false;
    }
}