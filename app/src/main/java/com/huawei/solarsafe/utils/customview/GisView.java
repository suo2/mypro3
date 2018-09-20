package com.huawei.solarsafe.utils.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.patrol.PatrolItem;

/**
 * Created by p00213 on 2017/1/9.
 */
public class GisView extends FrameLayout implements View.OnClickListener
{
    public static final int STATUS_OK = 1;
    public static final int STATUS_NOOK = 2;
    public static final int STATUS_NOCHECK = 3;
    private ImageView mOkImg;
    private ImageView mNookImg;
    private ImageView mNoCheckImg;
    private TextView mOkTxt;
    private TextView mNookTxt;
    private TextView mNoCheckTxt;
    private TextView mTitle;
    private int mStauts;
    private PatrolItem mItem;

    public GisView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.gis_item, this);
        mTitle = (TextView) findViewById(R.id.tv_title);
        mOkImg = (ImageView) findViewById(R.id.iv_ok);
        mNookImg = (ImageView) findViewById(R.id.iv_nook);
        mNoCheckImg = (ImageView) findViewById(R.id.iv_onCheck);
        mOkTxt = (TextView) findViewById(R.id.tv_ok);
        mNookTxt = (TextView) findViewById(R.id.tv_nook);
        mNoCheckTxt = (TextView) findViewById(R.id.tv_onCheck);
        mOkImg.setOnClickListener(this);
        mNookImg.setOnClickListener(this);
        mNoCheckImg.setOnClickListener(this);
        mOkTxt.setOnClickListener(this);
        mNookTxt.setOnClickListener(this);
        mNoCheckTxt.setOnClickListener(this);
        mStauts = STATUS_OK;
    }

    public GisView(Context context)
    {
        this(context, null);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_ok:
            case R.id.tv_ok:
                if (mStauts != STATUS_OK)
                {
                    mOkImg.setImageResource(R.drawable.ic_check);
                    mNookImg.setImageResource(R.drawable.ic_uncheck);
                    mNoCheckImg.setImageResource(R.drawable.ic_uncheck);
                    mStauts = STATUS_OK;
                }
                break;
            case R.id.iv_nook:
            case R.id.tv_nook:
                if (mStauts != STATUS_NOOK)
                {
                    mNookImg.setImageResource(R.drawable.ic_check);
                    mOkImg.setImageResource(R.drawable.ic_uncheck);
                    mNoCheckImg.setImageResource(R.drawable.ic_uncheck);
                    mStauts = STATUS_NOOK;
                }
                break;
            case R.id.iv_onCheck:
            case R.id.tv_onCheck:
                if (mStauts != STATUS_NOCHECK)
                {
                    mNoCheckImg.setImageResource(R.drawable.ic_check);
                    mOkImg.setImageResource(R.drawable.ic_uncheck);
                    mNookImg.setImageResource(R.drawable.ic_uncheck);
                    mStauts = STATUS_NOCHECK;
                }
                break;
            default:
                break;
        }
    }

    public int getStauts()
    {
        return mStauts;
    }

    public String getTitle()
    {
        return mTitle.getText().toString();
    }

    public void setTitle(String title)
    {
        mTitle.setText(title);
    }

    public PatrolItem getItem()
    {
        mItem.setAnnexItemResult(mStauts);
        mItem.setAnnexItemName(mTitle.getText().toString());
        return mItem;
    }

    public void setItem(PatrolItem item)
    {
        mTitle.setText(item.getAnnexItemName());
        mStauts = item.getAnnexItemResult();
        initChoose(mStauts);
        mItem = item;
    }

    private void initChoose(int status)
    {
        switch (mStauts)
        {
            case STATUS_OK:
                mOkImg.setImageResource(R.drawable.ic_check);
                break;
            case STATUS_NOOK:
                mNookImg.setImageResource(R.drawable.ic_check);
                break;
            case STATUS_NOCHECK:
                mNoCheckImg.setImageResource(R.drawable.ic_check);
                break;

            default:
                break;
        }
    }

    public void setStautsEditable(boolean editable)
    {
        mOkImg.setClickable(editable);
        mNookImg.setClickable(editable);
        mNoCheckImg.setClickable(editable);
        mOkTxt.setClickable(editable);
        mNookTxt.setClickable(editable);
        mNoCheckTxt.setClickable(editable);
    }
}