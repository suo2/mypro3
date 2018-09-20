package com.huawei.solarsafe.view.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by p00507
 * on 2017/7/21.
 */

public class MyBandListView extends ListView {
    public MyBandListView(Context context) {
        super(context);
    }

    public MyBandListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBandListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
