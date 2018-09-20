package com.huawei.solarsafe.view.CustomViews;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * <pre>
 *     author: Tzy
 *     time  : $date$
 *     desc  : 聚光灯自定义界面自定义TextView,更换字体
 * </pre>
 */
public class SpotLightTextView extends TextView {
    public SpotLightTextView(Context context) {
        super(context);
        init(context);
    }

    public SpotLightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SpotLightTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public SpotLightTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context){
        AssetManager assetManager=context.getAssets();
        Typeface typeface=Typeface.createFromAsset(assetManager,"sentymaruko.ttf");
        setTypeface(typeface);
    }
}
