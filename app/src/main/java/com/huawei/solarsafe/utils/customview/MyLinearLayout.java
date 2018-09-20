package com.huawei.solarsafe.utils.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;

/**
 * Created by p00587 on 2017/6/6.
 */

public class MyLinearLayout extends LinearLayout {

    private TextView tvContent;

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        View v = LayoutInflater.from(context).inflate(R.layout.my_linearlayout, this, true);

        TextView tvTitle = (TextView) v.findViewById(R.id.titleOfMy);
        tvContent = (TextView) v.findViewById(R.id.content);
        View divider = v.findViewById(R.id.divider);
        ImageView ivArrow = (ImageView) v.findViewById(R.id.iv_arrow);
        ivArrow.setColorFilter(getResources().getColor(R.color.gray));
        ImageView decoration = (ImageView)v.findViewById(R.id.decoration);


        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyLinearLayout);
        String title = a.getString(R.styleable.MyLinearLayout_titleOfMyLinearLayout);
        String content = a.getString(R.styleable.MyLinearLayout_contentOfMyLinearLayout);
        boolean dividerShow = a.getBoolean(R.styleable.MyLinearLayout_dividerShowOfMyLinearLayout, true);
        boolean expanded = a.getBoolean(R.styleable.MyLinearLayout_expandedOfMyLinearLayout, true);
        Drawable drawable = a.getDrawable(R.styleable.MyLinearLayout_decoratedDrawableOfMyLinearLayout);


        tvTitle.setText(title);
        tvContent.setText(content);
        if (!dividerShow) divider.setVisibility(INVISIBLE);
        if (!expanded) ivArrow.setVisibility(GONE);
        decoration.setImageDrawable(drawable);
        decoration.setColorFilter(getResources().getColor(R.color.colorPrimary));
        if (a.getBoolean(R.styleable.MyLinearLayout_isEditText, false)) {
            ivArrow.setVisibility(GONE);
        }

        if (a.getBoolean(R.styleable.MyLinearLayout_isPsw, false)) {
            tvContent.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }

        a.recycle();
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setContent(CharSequence content) {
        tvContent.setText(content);
    }

    public String getContent()  {
        return tvContent.getText().toString();
    }
}