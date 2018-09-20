package com.huawei.solarsafe.utils.customview.sdlv;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

class Compat {
    protected static void setBackgroundDrawable(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }
}
