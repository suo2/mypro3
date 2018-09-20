package com.huawei.solarsafe.view.homepage.station.verticalviewpager;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by p00322 on 2017/1/20.
 */
public class VerticalViewPager extends ViewPager {
    private boolean scrollble = false;
    public VerticalViewPager(Context context) {
        super(context);
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private MotionEvent swapTouchEvent(MotionEvent event) {
        float width = getWidth();
        float height = getHeight();
        event.setLocation((event.getY() / height) * width, (event.getX() / width) * height);
        return event;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!scrollble) {
            return false;
        } else {
            return super.onInterceptTouchEvent(swapTouchEvent(MotionEvent.obtain(event)));
        }
    }

    @Override

    public boolean onTouchEvent(MotionEvent ev) {
        if (!scrollble) {
            return false;
        } else {
            return super.onTouchEvent(swapTouchEvent(MotionEvent.obtain(ev)));
        }
    }

    public void setScrollble(boolean scrollble) {
        this.scrollble = scrollble;
    }
}
