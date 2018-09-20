package com.huawei.solarsafe.view.devicemanagement;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;

/**
 * Created by P00708 on 2017/12/18.
 */

public class DeviceTypeItemView extends LinearLayout implements View.OnClickListener{


    private String[] arrayItem;
    private String selectItem;
    private final int itemSelectColor= 0xffff9933;
    private final int itemNoSelectColor= 0xff333333;
    private SelectItemView selectItemView;
    private int defaultDisplayItemCount=0;
    public DeviceTypeItemView(Context context){
        super(context);
        setOrientation(VERTICAL);
    }
    public DeviceTypeItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }
    public void initItemData(String[] arrayItem,String defaultItem,int defaultDisplayItemCount){
        if(arrayItem == null || defaultItem == null){
            return;
        }
        removeAllViews();
        this.arrayItem = arrayItem;
        this.selectItem = defaultItem;
        this.defaultDisplayItemCount = defaultDisplayItemCount;
        if(getMeasuredWidth() != 0){
            initItemView();
        }
    }

    public void setSelectItem(String selectItem){
        if(selectItem == null){
            return;
        }
        this.selectItem = selectItem;
        changeDefaultState();
    }
    public String getSelectItem(){
        return this.selectItem;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(arrayItem == null || arrayItem.length==0){
            return;
        }
        initItemView();

    }

    private TextView createItemView(String itemData){
        if(itemData == null){
            itemData ="";
        }
        TextView itemView = new TextView(getContext());
        int paddingLeftRightSize = (int)getContext().getResources().getDimension(R.dimen.size_10dp);
        if(itemData.equals(selectItem)){
            itemView.setTextColor(itemSelectColor);
            itemView.setBackgroundResource(R.drawable.device_filter_pop_device_select_type_bg);
        }else{
            itemView.setTextColor(itemNoSelectColor);
            itemView.setBackgroundResource(R.drawable.device_filter_pop_no_device_select_type_bg);
        }
        itemView.setText(itemData);
        Paint paint = itemView.getPaint();
        int textWidth = (int) paint.measureText(itemData);
        itemView.setTag(R.id.tag_device_type_content,itemData);
        itemView.setTag(R.id.tag_device_type_length,textWidth+paddingLeftRightSize*2);
        itemView.setGravity(Gravity.CENTER);
        itemView.setMaxLines(4);
        itemView.setEllipsize(TextUtils.TruncateAt.END);
        itemView.setOnClickListener(this);
        return itemView;

    }
    private LinearLayout createHorizontalLinearLayout(){

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(HORIZONTAL);
        int paddingSize = (int)getContext().getResources().getDimension(R.dimen.size_5dp);
        linearLayout.setPadding(0,paddingSize,0,paddingSize);
        return  linearLayout;
    }

    @Override
    public void onClick(View view) {
        String tag = (String) view.getTag(R.id.tag_device_type_content);
        if(tag == null){
            return;
        }
        if(!tag.equals(selectItem)){
            selectItem = tag;
            changeDefaultState();
        }
        if(selectItemView != null){
            selectItemView.selectItem(selectItem);
        }
    }
    private void changeDefaultState(){

        if(getChildCount() == 0){
            return;
        }
        for(int i=0;i<getChildCount();i++){
            LinearLayout linearLayout = (LinearLayout) getChildAt(i);
            if(linearLayout.getChildCount() !=0){
                for(int j=0;j<linearLayout.getChildCount();j++){
                    if( linearLayout.getChildAt(j) instanceof TextView){
                        TextView itemView = (TextView) linearLayout.getChildAt(j);
                        String tag = (String) itemView.getTag(R.id.tag_device_type_content);
                        if(tag != null && tag.equals(selectItem)){
                            itemView.setTextColor(itemSelectColor);
                            itemView.setBackgroundResource(R.drawable.device_filter_pop_device_select_type_bg);
                        }else{
                            itemView.setTextColor(itemNoSelectColor);
                            itemView.setBackgroundResource(R.drawable.device_filter_pop_no_device_select_type_bg);
                        }
                    }
                }
            }
        }
    }

    private void  initItemView(){
        if(getChildCount() == 0){
            LinearLayout linearLayout = createHorizontalLinearLayout();
            int marginsWidth = (int)getContext().getResources().getDimension(R.dimen.size_10dp);
            for(int i=0;i<arrayItem.length;i++){
                TextView textView = createItemView(arrayItem[i]);
                if(linearLayout.getChildCount()==2){
                    linearLayout = createHorizontalLinearLayout();
                }
                switch (linearLayout.getChildCount()){

                    case 0:
                        linearLayout.addView(textView);
                        LayoutParams layoutParams = (LayoutParams) textView.getLayoutParams();
                        layoutParams.weight =1f;
                        layoutParams.width=0;
                        layoutParams.height = marginsWidth*4;
                        layoutParams.gravity = Gravity.CENTER_VERTICAL;
                        textView.setLayoutParams(layoutParams);
                        if(textView.getText() != null && textView.getText().length()>25){
                            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,11);
                        }else{
                            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
                        }

                        if(i==arrayItem.length-1){
                            View view = new View(getContext());
                            view.setBackgroundResource(R.color.transparent);
                            linearLayout.addView(view);
                            LayoutParams params = (LayoutParams) view.getLayoutParams();
                            params.weight =1f;
                            params.width=0;
                            params.height = marginsWidth*4;
                            params.gravity = Gravity.CENTER_VERTICAL;
                            params.setMargins(marginsWidth,0,0,0);
                            view.setLayoutParams(params);
                            addView(linearLayout);
                        }
                        break;

                    case 1:
                        linearLayout.addView(textView);
                        LayoutParams layoutParam = (LayoutParams) textView.getLayoutParams();
                        layoutParam.weight =1f;
                        layoutParam.width=0;
                        layoutParam.height = marginsWidth*4;
                        layoutParam.gravity = Gravity.CENTER_VERTICAL;
                        layoutParam.setMargins(marginsWidth,0,0,0);
                        textView.setLayoutParams(layoutParam);
                        if(textView.getText() != null && textView.getText().length()>25){
                            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,11);
                        }else{
                            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
                        }
                        addView(linearLayout);
                        break;
                    default:
                        break;
                }
                if(i+1>defaultDisplayItemCount){

                    if(i%2 ==0){
                        linearLayout.setVisibility(GONE);
                    }else{
                        if(linearLayout.getChildAt(1) != null){
                            linearLayout.getChildAt(1).setVisibility(INVISIBLE);
                        }
                    }

                }
            }

        }
    }

    public void setSelectItemView(SelectItemView selectItemView) {
        this.selectItemView = selectItemView;
    }

    public interface SelectItemView{
        void selectItem(String item);
    }
    /**
     * 得到item选择的值
     */
    public String getItemSelectValue(){
        return selectItem;
    }
    /**
     * 得到item选择的位置
     */
    public int getItemSelectPosition(){

        if(arrayItem == null){
            return 0;
        }
        for(int i=0;i<arrayItem.length;i++){
            if(arrayItem[i].equals(selectItem)){
                return i;
            }
        }
        return 0;
    }

    /**
     * 显示所有item
     */
    public void displayAllItem(){
        for(int i=0;i<getChildCount();i++){
            LinearLayout linearLayout = (LinearLayout) getChildAt(i);
            linearLayout.setVisibility(VISIBLE);
            for(int j=0;j<linearLayout.getChildCount();j++){
                linearLayout.getChildAt(j).setVisibility(VISIBLE);
            }
        }
    }
    /**
     * 显示部分item
     */
    public void displayDefaultItem(){
        for(int i=0;i<getChildCount();i++){
            LinearLayout linearLayout = (LinearLayout) getChildAt(i);
            if((2*i+1)>defaultDisplayItemCount){
                linearLayout.setVisibility(GONE);
            }else{
                linearLayout.setVisibility(VISIBLE);
                if((2*i+2)<=defaultDisplayItemCount){
                    if(linearLayout.getChildAt(1) != null){
                        linearLayout.getChildAt(1).setVisibility(VISIBLE);
                    }
                }else{
                    if(linearLayout.getChildAt(1) != null){
                        linearLayout.getChildAt(1).setVisibility(INVISIBLE);
                    }
                }
            }
        }
    }

}
