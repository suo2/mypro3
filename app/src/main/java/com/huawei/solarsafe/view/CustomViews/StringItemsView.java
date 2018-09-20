package com.huawei.solarsafe.view.CustomViews;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ddg on 2018/7/31.
 */

public class StringItemsView extends LinearLayout implements View.OnClickListener{

    private final static int VALUE_KEY = -100;
    private final static int POSITION_KEY = -200;
    private final int itemSelectColor= 0xffff9933;
    private final int itemNoSelectColor= 0xff333333;
    private Context mContext;
    private String[] items;
    private int count;
    private String selectItem;
    private int selectPosition = -1;

    public StringItemsView(Context context) {
        super(context);
        setOrientation(VERTICAL);
    }

    public StringItemsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    public void initItems(Context context, String[] items, String selectItem, int count){
        this.mContext = context;
        this.items = items;
        this.selectItem = selectItem;
        this.count = count;
        if(items == null || items.length <= 0 || count < 1 || judgeHasSameValue()){
            return;
        }
        init();
    }

    public void initItems(Context context, String[] items, int selectPosition, int count){
        this.mContext = context;
        this.items = items;
        this.selectPosition = selectPosition;
        this.count = count;
        if(items == null || items.length <= 0 || count < 1 || judgeHasSameValue()){
            return;
        }
        init();
    }

    private void init(){
        if(TextUtils.isEmpty(selectItem) && selectPosition == -1){
            this.selectItem = items[0];
            this.selectPosition = 0;
        }else if(!TextUtils.isEmpty(selectItem) && selectPosition == -1){
            if(judgeHasValue(selectItem) == -1){
                this.selectItem = items[0];
                this.selectPosition = 0;
            }else{
                this.selectPosition = judgeHasValue(selectItem);
            }
        }else if(TextUtils.isEmpty(selectItem) && selectPosition != -1){
            if(selectPosition < 0 || selectPosition > items.length - 1){
                selectPosition = 0;
                selectItem = items[0];
            }else{
                selectItem = items[selectPosition];
            }
        }
        LinearLayout linearLayout = null;
        for(int i=0;i<items.length;i++){
            if(i % count == 0){ //每行第一项
                linearLayout = createHorizontalLinearLayout();
                TextView itemView = createTextView(items[i], i);
                linearLayout.addView(itemView);
                LayoutParams layoutParams = (LayoutParams) itemView.getLayoutParams();
                layoutParams.width = 0;
                layoutParams.weight = 1;
                layoutParams.height = (int) mContext.getResources().getDimension(R.dimen.size_40dp);
                layoutParams.gravity = Gravity.CENTER_VERTICAL;
                if(count == 1){
                    addView(linearLayout);
                }else{
                    if(i == items.length - 1){
                        for(int j=0;j<count - 1;j++){
                            View view = new View(mContext);
                            linearLayout.addView(view);
                            LayoutParams viewParams = (LayoutParams) view.getLayoutParams();
                            viewParams.width = 0;
                            viewParams.weight = 1;
                            viewParams.height = (int) mContext.getResources().getDimension(R.dimen.size_40dp);
                            viewParams.leftMargin = (int) mContext.getResources().getDimension(R.dimen.size_10dp);
                            if(j == count - 2){
                                addView(linearLayout);
                            }
                        }
                    }
                }
            }else if(i % count == count - 1){ // 每行最后一项
                TextView itemView = createTextView(items[i], i);
                linearLayout.addView(itemView);
                LayoutParams layoutParams = (LayoutParams) itemView.getLayoutParams();
                layoutParams.width = 0;
                layoutParams.weight = 1;
                layoutParams.height = (int) mContext.getResources().getDimension(R.dimen.size_40dp);
                layoutParams.leftMargin = (int) mContext.getResources().getDimension(R.dimen.size_10dp);
                layoutParams.gravity = Gravity.CENTER_VERTICAL;
                addView(linearLayout);
            }else{ //中间项
                TextView itemView = createTextView(items[i], i);
                linearLayout.addView(itemView);
                LayoutParams layoutParams = (LayoutParams) itemView.getLayoutParams();
                layoutParams.width = 0;
                layoutParams.weight = 1;
                layoutParams.height = (int) mContext.getResources().getDimension(R.dimen.size_40dp);
                layoutParams.leftMargin = (int) mContext.getResources().getDimension(R.dimen.size_10dp);
                layoutParams.gravity = Gravity.CENTER_VERTICAL;
                if(i == items.length - 1){
                    for(int j=0;j<count - i%count - 1;j++){
                        View view = new View(mContext);
                        linearLayout.addView(view);
                        LayoutParams viewParams = (LayoutParams) view.getLayoutParams();
                        viewParams.width = 0;
                        viewParams.weight = 1;
                        viewParams.height = (int) mContext.getResources().getDimension(R.dimen.size_40dp);
                        viewParams.leftMargin = (int) mContext.getResources().getDimension(R.dimen.size_10dp);
                        if(j == count - i%count - 2){
                            addView(linearLayout);
                        }
                    }
                }
            }
        }
    }

    /**
     * 创建水平布局
     * @return
     */
    private LinearLayout createHorizontalLinearLayout(){
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(HORIZONTAL);
        linearLayout.setPadding(0,(int) mContext.getResources().getDimension(R.dimen.size_5dp),
                0,(int) mContext.getResources().getDimension(R.dimen.size_5dp));
        return linearLayout;
    }

    /**
     * 创建item
     * @param itemStr
     * @param position
     * @return
     */
    private TextView createTextView(String itemStr, int position){
        TextView textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setMaxLines(3);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setText(itemStr);
        textView.setTag(VALUE_KEY, itemStr);
        textView.setTag(POSITION_KEY, position);
        if(selectItem.equals(itemStr)){
            textView.setSelected(true);
            textView.setTextColor(itemSelectColor);
            textView.setBackgroundResource(R.drawable.device_filter_pop_device_select_type_bg);
        }else{
            textView.setSelected(false);
            textView.setTextColor(itemNoSelectColor);
            textView.setBackgroundResource(R.drawable.device_filter_pop_no_device_select_type_bg);
        }
        if(!TextUtils.isEmpty(textView.getText().toString()) && textView.getText().toString().length()>25){
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,11);
        }else{
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
        }
        textView.setOnClickListener(this);
        return textView;
    }
    //改变背景
    private void updateBackGround(){
        for(int i=0;i<this.getChildCount();i++){
            LinearLayout childLinearLayout = (LinearLayout) getChildAt(i);
            for(int j=0;j<childLinearLayout.getChildCount();j++){
                if(childLinearLayout.getChildAt(j) instanceof TextView){
                    TextView childTextView = (TextView) childLinearLayout.getChildAt(j);
                    if(childTextView.getText().toString().equals(selectItem)){
                        childTextView.setSelected(true);
                        childTextView.setTextColor(itemSelectColor);
                        childTextView.setBackgroundResource(R.drawable.device_filter_pop_device_select_type_bg);
                    }else{
                        childTextView.setSelected(false);
                        childTextView.setTextColor(itemNoSelectColor);
                        childTextView.setBackgroundResource(R.drawable.device_filter_pop_no_device_select_type_bg);
                    }
                }
            }
        }
    }

    /**
     * 判断String数组中是否有相同的元素
     * @return
     */
    private boolean judgeHasSameValue(){
        if(items == null){
            return false;
        }
        Set<String> set = new HashSet();
        for(String str : items){
            set.add(str);
        }
        if(set.size() == items.length){
            return false;
        }
        return true;
    }

    /**
     * 判断String数组中是否有某个字符串,有则返回下标，没有返回-1
     * @param value
     * @return
     */
    private int judgeHasValue(String value){
        for(int i = 0;i<items.length;i++){
            if(value.equals(items[i])){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onClick(View view) {
        this.selectItem = (String) view.getTag(VALUE_KEY);
        this.selectPosition = (int) view.getTag(POSITION_KEY);
        updateBackGround();
    }

    public String getSelectItem(){
        return selectItem;
    }

    public void setSelectItem(String selectItem){
        if(TextUtils.isEmpty(selectItem) || judgeHasValue(selectItem) == -1){
            this.selectItem = items[0];
            this.selectPosition = 0;
        }else{
            this.selectItem = selectItem;
            this.selectPosition = judgeHasValue(selectItem);
        }
        updateBackGround();
    }

    public int getSelectPostion(){
        return selectPosition;
    }

    public void setSelectPostion(int selectPosition){
        if(selectPosition < 0 || selectPosition > items.length - 1){
            this.selectItem = items[0];
            this.selectPosition = 0;
        }else{
            this.selectItem = items[selectPosition];
            this.selectPosition = selectPosition;
        }
        updateBackGround();
    }
}
