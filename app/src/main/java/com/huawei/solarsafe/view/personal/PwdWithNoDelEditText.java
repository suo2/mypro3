package com.huawei.solarsafe.view.personal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityEvent;
import android.widget.EditText;

/**
 * Created by P00746 on 2018/5/18.
 */

public class PwdWithNoDelEditText extends EditText {
    public PwdWithNoDelEditText(Context context) {
        super(context);
    }

    public PwdWithNoDelEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PwdWithNoDelEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        event.setPassword(true);
    }

}
