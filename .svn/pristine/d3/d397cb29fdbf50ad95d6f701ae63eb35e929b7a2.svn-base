package com.huawei.solarsafe.view.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * <pre>
 *     author: Tzy
 *     time  : $date$
 *     desc  : 显示所有item的ExpandableListView,主要用于嵌套在ScrollView中,否则不能完整显示数据
 * </pre>
 */
public class ShowAllItemExpandableListView extends ExpandableListView {
    public ShowAllItemExpandableListView(Context context) {
        super(context);
    }

    public ShowAllItemExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShowAllItemExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, // 设计一个较大的值和AT_MOST模式
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
    }
}
