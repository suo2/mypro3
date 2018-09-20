package com.huawei.solarsafe.view.CustomViews;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * <pre>
 *     author: Tzy
 *     time  : $date$
 *     desc  : 登录界面引导页自定义TextView,更换字体和文本倾斜
 * </pre>
 */
public class GuideTextView extends TextView {
    public float rotateDegrees=0;//倾斜角度


    public GuideTextView(Context context) {
        super(context);
        init(context);
    }

    public GuideTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GuideTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        AssetManager assetManager=context.getAssets();
        Typeface typeface=Typeface.createFromAsset(assetManager,"hyi4gj.ttf");
        setTypeface(typeface);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //【安全特性】f:int division result cast to double or float   【修改人】zhaoyufeng
        canvas.rotate(rotateDegrees,0f,getMeasuredHeight()/2.0f);
        super.onDraw(canvas);
    }

    /**
     * 设置倾斜角度
     * @param rotateDegrees 倾斜角度
     */
    public void setRotateDegrees(float rotateDegrees) {
        this.rotateDegrees = rotateDegrees;
    }
}
