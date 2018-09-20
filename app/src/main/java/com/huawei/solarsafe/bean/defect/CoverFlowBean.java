package com.huawei.solarsafe.bean.defect;

import android.view.View;

/**
 * Created by p00319 on 2017/1/5.
 */

public class CoverFlowBean {

    /**
     * 图标id
     */
    private int imageRes;
    /**
     * item标题
     */
    private String title;
    /**
     * 第一条信息标题
     */
    private String info1;
    /**
     * 第一条信息数值
     */
    private String num1;
    /**
     * 第二条信息标题
     */
    private String info2;
    /**
     * 第二条信息数值
     */
    private String num2;
    /**
     * 第三条信息标题
     */
    private String info3;
    /**
     * 第三条信息数值
     */
    private String num3;
    /**
     * 第四条信息标题
     */
    private String info4;
    /**
     * 第四条信息数值
     */
    private String num4;
    /**
     * 点击事件
     */
    private View.OnClickListener listener;

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setInfo1(String info1) {
        this.info1 = info1;
    }

    public String getInfo1() {
        return info1;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }

    public String getInfo2() {
        return info2;
    }

    public void setInfo3(String info3) {
        this.info3 = info3;
    }

    public String getInfo3() {
        return info3;
    }

    public void setNum1(String num1) {
        this.num1 = num1;
    }

    public String getNum1() {
        return num1;
    }

    public void setNum2(String num2) {
        this.num2 = num2;
    }

    public String getNum2() {
        return num2;
    }

    public void setNum3(String num3) {
        this.num3 = num3;
    }

    public String getNum3() {
        return num3;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getInfo4() {
        return info4;
    }

    public void setInfo4(String info4) {
        this.info4 = info4;
    }

    public String getNum4() {
        return num4;
    }

    public void setNum4(String num4) {
        this.num4 = num4;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public View.OnClickListener getListener() {
        return listener;
    }
}
