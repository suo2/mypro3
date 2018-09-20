package com.huawei.solarsafe.view.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * Created by P00507
 * on 2017/6/12.
 */

public class MyHorizontalScrollView extends HorizontalScrollView {
    private View mView;
    public MyHorizontalScrollView(Context context) {
        super(context);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //我滚到哪里，进来的view就滚到哪里
        if(mView != null){
            mView.scrollTo(l,t);
        }
    }

    public void setmView(View mView) {
        this.mView = mView;
    }
}
