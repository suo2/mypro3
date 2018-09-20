package com.huawei.solarsafe.view.homepage.station;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;

/**
 * Created by P00708 on 2018/6/26.
 */

public class LabelItemView extends RelativeLayout{

    private ImageView itemImg;
    private TextView  itemTx;

    public LabelItemView(Context context, AttributeSet attrs){
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.label_item_view_layout,this);
        itemImg = (ImageView) findViewById(R.id.label_item_img);
        itemTx = (TextView) findViewById(R.id.label_item_tx);
    }

    public LabelItemView(Context context,@DrawableRes int resId,@StringRes int residStr) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.label_item_view_layout,this);
        itemImg = (ImageView) findViewById(R.id.label_item_img);
        itemTx = (TextView) findViewById(R.id.label_item_tx);
        itemImg.setImageResource(resId);
        itemTx.setText(residStr);
    }
    public LabelItemView(Context context, Drawable drawable, @StringRes int residStr) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.label_item_view_layout,this);
        itemImg = (ImageView) findViewById(R.id.label_item_img);
        itemTx = (TextView) findViewById(R.id.label_item_tx);
        itemImg.setImageDrawable(drawable);
        itemTx.setText(residStr);
    }
    public void setLabelItemViewData( Drawable drawable,@StringRes int residStr){
        itemImg.setImageDrawable(drawable);
        itemTx.setText(residStr);
    }
}
