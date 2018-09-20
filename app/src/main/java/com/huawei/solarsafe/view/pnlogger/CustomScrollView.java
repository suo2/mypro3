/**
 *
 */
package com.huawei.solarsafe.view.pnlogger;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Create Date: 2016-5-10<br>
 * Create Author: PT02008<br>
 * Description:不抢占子View焦点的ScrollView
 */
public class CustomScrollView extends ScrollView {
    float deltaY;
    /**
     * 主要是用在用户手指离开MyScrollView，MyScrollView还在继续滑动，我们用来保存Y的距离，然后做比较
     */
    private int lastScrollY;

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int y = (int) ev.getY();
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            deltaY = y;
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            if (Math.abs(deltaY - y) < 10) {
                return false;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 重写onTouchEvent， 当用户的手在MyScrollView上面的时候，
     * 直接将MyScrollView滑动的Y方向距离回调给onScroll方法中，当用户抬起手的时候，
     * MyScrollView可能还在滑动，所以当用户抬起手我们隔5毫秒给handler发送消息，在handler处理
     * MyScrollView滑动的距离
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                handler.sendMessageDelayed(handler.obtainMessage(), 5);
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 用于用户手指离开MyScrollView的时候获取MyScrollView滚动的Y距离，然后回调给onScroll方法中
     */
    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            int scrollY = CustomScrollView.this.getScrollY();
            //此时的距离和记录下的距离不相等，在隔5毫秒给handler发送消息
            if (lastScrollY != scrollY) {
                lastScrollY = scrollY;
                handler.sendMessageDelayed(handler.obtainMessage(), 5);
            }
        }
    };
}