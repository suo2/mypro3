package com.huawei.solarsafe.utils.customview.sdlv;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

class SDMenuItemView extends BaseLayout {
    protected ImageView mImageView;
    protected TextView mTextView;

    protected SDMenuItemView(Context context, MenuItem menuItem) {
        super(context, menuItem);
    }

    @Override
    protected void build() {
        LayoutParams layoutParams = new LayoutParams(mMenuItem.width, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER;
        setLayoutParams(layoutParams);
        addView(createBG());

        if (!TextUtils.isEmpty(mMenuItem.text) && mMenuItem.icon != null) {
            mImageView = createImageView();
            mTextView = createTextView();
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(mImageView);
            linearLayout.addView(mTextView);
            linearLayout.setGravity(Gravity.CENTER);
            addView(linearLayout);
        } else if (mMenuItem.icon != null) {
            mImageView = createImageView();
            addView(mImageView);
        } else if (!TextUtils.isEmpty(mMenuItem.text)) {
            mTextView = createTextView();
            addView(mTextView);
        } else {
            addView(createEmptyView());
        }
    }

    private ImageView createBG() {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageDrawable(mMenuItem.background);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    private TextView createTextView() {
        TextView textView = new TextView(getContext());
        textView.setText(mMenuItem.text);
        textView.setTextSize(mMenuItem.textSize);
        textView.setTextColor(mMenuItem.textColor);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    private View createEmptyView() {
        View view = new View(getContext());
        return view;
    }

    protected ImageView createImageView() {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageDrawable(mMenuItem.icon);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        return imageView;
    }

    @Override
    public TextView getTextView() {
        return mTextView;
    }

    @Override
    public ImageView getImageView() {
        return mImageView;
    }
}


