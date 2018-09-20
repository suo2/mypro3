package com.huawei.solarsafe.utils.customview.sdlv;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class BaseLayout extends FrameLayout {
    protected MenuItem mMenuItem;

    protected BaseLayout(Context context, MenuItem menuItem) {
        super(context);
        mMenuItem = menuItem;
    }

    protected abstract void build();

    public abstract TextView getTextView();

    public abstract ImageView getImageView();
}
