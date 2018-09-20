package com.huawei.solarsafe.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

/**
 * Created by p00322 on 2017/2/24.
 * description: 解决点击相同item不响应问题
 */
public class MySpinner extends Spinner {
    public MySpinner(Context context) {
        super(context);
    }

    public MySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MySpinner(Context context, int mode) {
        super(context, mode);
    }

    @Override
    public void setSelection(int position, boolean animate) {
        boolean sameSelected = position == getSelectedItemPosition();
        super.setSelection(position, animate);
            if (sameSelected) {
                if( getSelectedView() != null){
                   getOnItemSelectedListener().onItemSelected(this, getSelectedView(),
                        position, getSelectedItemId());
                }
        }
    }
}
