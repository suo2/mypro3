package com.huawei.solarsafe.view.devicemanagement;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.device.DevBean;
import com.huawei.solarsafe.bean.device.YhqDevBeanList;
import com.huawei.solarsafe.utils.customview.FlowLineView;

import java.util.ArrayList;

/**
 * Created by P00229 on 2017/8/11.
 */

public class OptimizerViewUtil {
    private static ArrayList<DevBean> yhqList;
    private View mainView;


    public static void setYhqList(ArrayList<DevBean> yhqList) {
        OptimizerViewUtil.yhqList = yhqList;
    }

    public View getMainView() {
        return mainView;
    }

    public void setMainView(View mainView) {
        this.mainView = mainView;
    }

    /**
     * 动态显示优化器个数View
     *
     * @param mainView
     * @param size     优化器的个数
     * @param yhqList  优化器集合
     */
    public static void showOptimizerBySize(Context context, View mainView,View llView, int size, ArrayList<DevBean> yhqList) {
        FlowLineView[] flowLineViews = new FlowLineView[7];
        flowLineViews[0] = (FlowLineView) llView.findViewById(R.id.view_flow_line_to_inv_last_1);
        flowLineViews[1] = (FlowLineView) llView.findViewById(R.id.view_flow_line_to_inv_last_2);
        flowLineViews[2] = (FlowLineView) llView.findViewById(R.id.view_flow_line_to_inv_last_3);
        flowLineViews[3] = (FlowLineView) llView.findViewById(R.id.view_flow_line_to_inv_last_4);
        flowLineViews[4] = (FlowLineView) llView.findViewById(R.id.view_flow_line_to_inv_last_5);
        flowLineViews[5] = (FlowLineView) llView.findViewById(R.id.view_flow_line_to_inv_last_6);
        flowLineViews[6] = (FlowLineView) llView.findViewById(R.id.view_flow_line_to_inv_last_7);
        //判断PV1板的优化器个数，隐藏与显示布局
        if (size > 0 && size <= 4) {
            for (int i = 1; i < flowLineViews.length; i++) {
                hintFlowView(flowLineViews[i]);
            }
        } else if (size > 4 && size <= 8) {
            for (int i = 1; i < flowLineViews.length; i++) {
                hintFlowView(flowLineViews[i]);
            }
        } else if (size > 8 && size <= 12) {
            for (int i = 2; i < flowLineViews.length; i++) {
                hintFlowView(flowLineViews[i]);
            }
        } else if (size > 12 && size <= 16) {
            for (int i = 3; i < flowLineViews.length; i++) {
                hintFlowView(flowLineViews[i]);
            }
        } else if (size > 16 && size <= 20) {
            for (int i = 4; i < flowLineViews.length; i++) {
                hintFlowView(flowLineViews[i]);
            }
        } else if (size > 20 && size <= 24) {
            for (int i = 5; i < flowLineViews.length; i++) {
                hintFlowView(flowLineViews[i]);
            }
        } else if (size > 24 && size <= 28) {
            for (int i = 6; i < flowLineViews.length; i++) {
                hintFlowView(flowLineViews[i]);
            }
        } else if (size > 28 && size <= 30) {
            for (int i = 7; i < flowLineViews.length; i++) {
                hintFlowView(flowLineViews[i]);
            }
        }
        FlowLineView[] lineViews = new FlowLineView[23];
        ImageView[] imageViews = new ImageView[30];
        TextView[] wTexts = new TextView[30];
        TextView[] nTexts = new TextView[30];
        LinearLayout container2 = (LinearLayout) mainView.findViewById(R.id.ll_pv_container_2);
        LinearLayout container3 = (LinearLayout) mainView.findViewById(R.id.ll_pv_container_3);
        LinearLayout container4 = (LinearLayout) mainView.findViewById(R.id.ll_pv_container_4);
        LinearLayout container5 = (LinearLayout) mainView.findViewById(R.id.ll_pv_container_5);
        LinearLayout container6 = (LinearLayout) mainView.findViewById(R.id.ll_pv_container_6);
        LinearLayout container7 = (LinearLayout) mainView.findViewById(R.id.ll_pv_container_7);
        LinearLayout container8 = (LinearLayout) mainView.findViewById(R.id.ll_pv_container_8);
        FlowLineView flowLineView1 = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_1);
        FlowLineView flowLineView2 = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_2);
        FlowLineView flowLineView3 = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_3);
        FlowLineView flowLineView4 = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_4);
        FlowLineView flowLineView5 = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_5);
        FlowLineView flowLineView6 = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_6);
        FlowLineView flowLineView7 = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_7);
        lineViews[0] = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_string1);
        lineViews[1] = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_string2);
        lineViews[2] = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_string3);
        lineViews[3] = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_string4);
        lineViews[4] = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_string4_2);
        lineViews[5] = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_string3_2);
        lineViews[6] = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_string2_2);
        lineViews[7] = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_string2_3);
        lineViews[8] = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_string3_3);
        lineViews[9] = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_string4_3);
        lineViews[10] = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_string4_4);
        lineViews[11] = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_string3_4);
        lineViews[12] = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_string2_4);
        lineViews[13] = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_string2_5);
        lineViews[14] = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_string3_5);
        lineViews[15] = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_string4_5);
        lineViews[16] = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_string4_6);
        lineViews[17] = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_string3_6);
        lineViews[18] = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_string2_6);
        lineViews[19] = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_string2_7);
        lineViews[20] = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_string3_7);
        lineViews[21] = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_string4_7);
        lineViews[22] = (FlowLineView) mainView.findViewById(R.id.view_flow_line_to_string4_8);
        imageViews[0] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv1);
        imageViews[1] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv2);
        imageViews[2] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv3);
        imageViews[3] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv4);
        imageViews[4] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv4_2);
        imageViews[5] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv3_2);
        imageViews[6] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv2_2);
        imageViews[7] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv1_2);
        imageViews[8] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv1_3);
        imageViews[9] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv2_3);
        imageViews[10] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv3_3);
        imageViews[11] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv4_3);
        imageViews[12] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv4_4);
        imageViews[13] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv3_4);
        imageViews[14] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv2_4);
        imageViews[15] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv1_4);
        imageViews[16] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv1_5);
        imageViews[17] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv2_5);
        imageViews[18] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv3_5);
        imageViews[19] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv4_5);
        imageViews[20] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv4_6);
        imageViews[21] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv3_6);
        imageViews[22] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv2_6);
        imageViews[23] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv1_6);
        imageViews[24] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv1_7);
        imageViews[25] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv2_7);
        imageViews[26] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv3_7);
        imageViews[27] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv4_7);
        imageViews[28] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv4_8);
        imageViews[29] = (ImageView) mainView.findViewById(R.id.iv_cascade_pv3_8);
        wTexts[0] = (TextView) mainView.findViewById(R.id.tv_w_1);
        wTexts[1] = (TextView) mainView.findViewById(R.id.tv_w_2);
        wTexts[2] = (TextView) mainView.findViewById(R.id.tv_w_3);
        wTexts[3] = (TextView) mainView.findViewById(R.id.tv_w_4);
        wTexts[4] = (TextView) mainView.findViewById(R.id.tv_w_5);
        wTexts[5] = (TextView) mainView.findViewById(R.id.tv_w_6);
        wTexts[6] = (TextView) mainView.findViewById(R.id.tv_w_7);
        wTexts[7] = (TextView) mainView.findViewById(R.id.tv_w_8);
        wTexts[8] = (TextView) mainView.findViewById(R.id.tv_w_9);
        wTexts[9] = (TextView) mainView.findViewById(R.id.tv_w_10);
        wTexts[10] = (TextView) mainView.findViewById(R.id.tv_w_11);
        wTexts[11] = (TextView) mainView.findViewById(R.id.tv_w_12);
        wTexts[12] = (TextView) mainView.findViewById(R.id.tv_w_13);
        wTexts[13] = (TextView) mainView.findViewById(R.id.tv_w_14);
        wTexts[14] = (TextView) mainView.findViewById(R.id.tv_w_15);
        wTexts[15] = (TextView) mainView.findViewById(R.id.tv_w_16);
        wTexts[16] = (TextView) mainView.findViewById(R.id.tv_w_17);
        wTexts[17] = (TextView) mainView.findViewById(R.id.tv_w_18);
        wTexts[18] = (TextView) mainView.findViewById(R.id.tv_w_19);
        wTexts[19] = (TextView) mainView.findViewById(R.id.tv_w_20);
        wTexts[20] = (TextView) mainView.findViewById(R.id.tv_w_21);
        wTexts[21] = (TextView) mainView.findViewById(R.id.tv_w_22);
        wTexts[22] = (TextView) mainView.findViewById(R.id.tv_w_23);
        wTexts[23] = (TextView) mainView.findViewById(R.id.tv_w_24);
        wTexts[24] = (TextView) mainView.findViewById(R.id.tv_w_25);
        wTexts[25] = (TextView) mainView.findViewById(R.id.tv_w_26);
        wTexts[26] = (TextView) mainView.findViewById(R.id.tv_w_27);
        wTexts[27] = (TextView) mainView.findViewById(R.id.tv_w_28);
        wTexts[28] = (TextView) mainView.findViewById(R.id.tv_w_29);
        wTexts[29] = (TextView) mainView.findViewById(R.id.tv_w_30);

        nTexts[0] = (TextView) mainView.findViewById(R.id.tv_n_1);
        nTexts[1] = (TextView) mainView.findViewById(R.id.tv_n_2);
        nTexts[2] = (TextView) mainView.findViewById(R.id.tv_n_3);
        nTexts[3] = (TextView) mainView.findViewById(R.id.tv_n_4);
        nTexts[4] = (TextView) mainView.findViewById(R.id.tv_n_5);
        nTexts[5] = (TextView) mainView.findViewById(R.id.tv_n_6);
        nTexts[6] = (TextView) mainView.findViewById(R.id.tv_n_7);
        nTexts[7] = (TextView) mainView.findViewById(R.id.tv_n_8);
        nTexts[8] = (TextView) mainView.findViewById(R.id.tv_n_9);
        nTexts[9] = (TextView) mainView.findViewById(R.id.tv_n_10);
        nTexts[10] = (TextView) mainView.findViewById(R.id.tv_n_11);
        nTexts[11] = (TextView) mainView.findViewById(R.id.tv_n_12);
        nTexts[12] = (TextView) mainView.findViewById(R.id.tv_n_13);
        nTexts[13] = (TextView) mainView.findViewById(R.id.tv_n_14);
        nTexts[14] = (TextView) mainView.findViewById(R.id.tv_n_15);
        nTexts[15] = (TextView) mainView.findViewById(R.id.tv_n_16);
        nTexts[16] = (TextView) mainView.findViewById(R.id.tv_n_17);
        nTexts[17] = (TextView) mainView.findViewById(R.id.tv_n_18);
        nTexts[18] = (TextView) mainView.findViewById(R.id.tv_n_19);
        nTexts[19] = (TextView) mainView.findViewById(R.id.tv_n_20);
        nTexts[20] = (TextView) mainView.findViewById(R.id.tv_n_21);
        nTexts[21] = (TextView) mainView.findViewById(R.id.tv_n_22);
        nTexts[22] = (TextView) mainView.findViewById(R.id.tv_n_23);
        nTexts[23] = (TextView) mainView.findViewById(R.id.tv_n_24);
        nTexts[24] = (TextView) mainView.findViewById(R.id.tv_n_25);
        nTexts[25] = (TextView) mainView.findViewById(R.id.tv_n_26);
        nTexts[26] = (TextView) mainView.findViewById(R.id.tv_n_27);
        nTexts[27] = (TextView) mainView.findViewById(R.id.tv_n_28);
        nTexts[28] = (TextView) mainView.findViewById(R.id.tv_n_29);
        nTexts[29] = (TextView) mainView.findViewById(R.id.tv_n_30);

        //每个优化器添加点击事件
        for (int i = 0; i < size; i++) {
            addOnclickListenner(context, imageViews[i], i + 1 + "", yhqList.get(i));
            nTexts[i].setText(yhqList.get(i).getDevName());
            wTexts[i].setVisibility(View.INVISIBLE);
            //优化器运行状态
            if (yhqList.get(i).getDevRuningState() == 1) {
                imageViews[i].setImageResource(R.drawable.youhuaqi);
            }else if(yhqList.get(i).getDevRuningState() == 2){
                imageViews[i].setImageResource(R.drawable.opimity_disconnect);
            } else {
                imageViews[i].setImageResource(R.drawable.guzhang);
            }
        }
        if (size <= 4 && size > 0) {
            container2.setVisibility(View.GONE);
            container3.setVisibility(View.GONE);
            container4.setVisibility(View.GONE);
            container5.setVisibility(View.GONE);
            container6.setVisibility(View.GONE);
            container7.setVisibility(View.GONE);
            container8.setVisibility(View.GONE);
            flowLineView1.setVisibility(View.GONE);
            flowLineView2.setVisibility(View.GONE);
            flowLineView3.setVisibility(View.GONE);
            flowLineView4.setVisibility(View.GONE);
            flowLineView5.setVisibility(View.GONE);
            flowLineView6.setVisibility(View.GONE);
            flowLineView7.setVisibility(View.GONE);
        } else if (size > 4 && size <= 8) {
            container2.setVisibility(View.VISIBLE);
            container3.setVisibility(View.GONE);
            container4.setVisibility(View.GONE);
            container5.setVisibility(View.GONE);
            container6.setVisibility(View.GONE);
            container7.setVisibility(View.GONE);
            container8.setVisibility(View.GONE);
            flowLineView1.setVisibility(View.VISIBLE);
            flowLineView2.setVisibility(View.GONE);
            flowLineView3.setVisibility(View.GONE);
            flowLineView4.setVisibility(View.GONE);
            flowLineView5.setVisibility(View.GONE);
            flowLineView6.setVisibility(View.GONE);
            flowLineView7.setVisibility(View.GONE);
        } else if (size > 8 && size <= 12) {
            container2.setVisibility(View.VISIBLE);
            container3.setVisibility(View.VISIBLE);
            container4.setVisibility(View.GONE);
            container5.setVisibility(View.GONE);
            container6.setVisibility(View.GONE);
            container7.setVisibility(View.GONE);
            container8.setVisibility(View.GONE);
            flowLineView1.setVisibility(View.VISIBLE);
            flowLineView2.setVisibility(View.VISIBLE);
            flowLineView3.setVisibility(View.GONE);
            flowLineView4.setVisibility(View.GONE);
            flowLineView5.setVisibility(View.GONE);
            flowLineView6.setVisibility(View.GONE);
            flowLineView7.setVisibility(View.GONE);
        } else if (size > 12 && size <= 16) {
            container2.setVisibility(View.VISIBLE);
            container3.setVisibility(View.VISIBLE);
            container4.setVisibility(View.VISIBLE);
            container5.setVisibility(View.GONE);
            container6.setVisibility(View.GONE);
            container7.setVisibility(View.GONE);
            container8.setVisibility(View.GONE);
            flowLineView1.setVisibility(View.VISIBLE);
            flowLineView2.setVisibility(View.VISIBLE);
            flowLineView3.setVisibility(View.VISIBLE);
            flowLineView4.setVisibility(View.GONE);
            flowLineView5.setVisibility(View.GONE);
            flowLineView6.setVisibility(View.GONE);
            flowLineView7.setVisibility(View.GONE);
        } else if (size > 16 && size <= 20) {
            container2.setVisibility(View.VISIBLE);
            container3.setVisibility(View.VISIBLE);
            container4.setVisibility(View.VISIBLE);
            container5.setVisibility(View.VISIBLE);
            container6.setVisibility(View.GONE);
            container7.setVisibility(View.GONE);
            container8.setVisibility(View.GONE);
            flowLineView1.setVisibility(View.VISIBLE);
            flowLineView2.setVisibility(View.VISIBLE);
            flowLineView3.setVisibility(View.VISIBLE);
            flowLineView4.setVisibility(View.VISIBLE);
            flowLineView5.setVisibility(View.GONE);
            flowLineView6.setVisibility(View.GONE);
            flowLineView7.setVisibility(View.GONE);
        } else if (size > 20 && size <= 24) {
            container2.setVisibility(View.VISIBLE);
            container3.setVisibility(View.VISIBLE);
            container4.setVisibility(View.VISIBLE);
            container5.setVisibility(View.VISIBLE);
            container6.setVisibility(View.VISIBLE);
            container7.setVisibility(View.GONE);
            container8.setVisibility(View.GONE);
            flowLineView1.setVisibility(View.VISIBLE);
            flowLineView2.setVisibility(View.VISIBLE);
            flowLineView3.setVisibility(View.VISIBLE);
            flowLineView4.setVisibility(View.VISIBLE);
            flowLineView5.setVisibility(View.VISIBLE);
            flowLineView6.setVisibility(View.GONE);
            flowLineView7.setVisibility(View.GONE);
        } else if (size > 24 && size <= 28) {
            container2.setVisibility(View.VISIBLE);
            container3.setVisibility(View.VISIBLE);
            container4.setVisibility(View.VISIBLE);
            container5.setVisibility(View.VISIBLE);
            container6.setVisibility(View.VISIBLE);
            container7.setVisibility(View.VISIBLE);
            container8.setVisibility(View.GONE);
            flowLineView1.setVisibility(View.VISIBLE);
            flowLineView2.setVisibility(View.VISIBLE);
            flowLineView3.setVisibility(View.VISIBLE);
            flowLineView4.setVisibility(View.VISIBLE);
            flowLineView5.setVisibility(View.VISIBLE);
            flowLineView6.setVisibility(View.VISIBLE);
            flowLineView7.setVisibility(View.GONE);
        } else if (size > 28 && size <= 30) {
            container2.setVisibility(View.VISIBLE);
            container3.setVisibility(View.VISIBLE);
            container4.setVisibility(View.VISIBLE);
            container5.setVisibility(View.VISIBLE);
            container6.setVisibility(View.VISIBLE);
            container7.setVisibility(View.VISIBLE);
            container8.setVisibility(View.VISIBLE);
            flowLineView1.setVisibility(View.VISIBLE);
            flowLineView2.setVisibility(View.VISIBLE);
            flowLineView3.setVisibility(View.VISIBLE);
            flowLineView4.setVisibility(View.VISIBLE);
            flowLineView5.setVisibility(View.VISIBLE);
            flowLineView6.setVisibility(View.VISIBLE);
            flowLineView7.setVisibility(View.VISIBLE);
        }
        if (size > 0 && size <= 30) {
            if (size <= 4) {
                for (int i = size; i < lineViews.length; i++) {
                    hintLineView(lineViews[i]);
                }
            } else if (size > 4 && size <= 8) {
                for (int i = size - 1; i < lineViews.length; i++) {
                    hintLineView(lineViews[i]);
                }
            } else if (size > 8 && size <= 12) {
                for (int i = size - 2; i < lineViews.length; i++) {
                    hintLineView(lineViews[i]);
                }
            } else if (size > 12 && size <= 16) {
                for (int i = size - 3; i < lineViews.length; i++) {
                    hintLineView(lineViews[i]);
                }
            } else if (size > 16 && size <= 20) {
                for (int i = size - 4; i < lineViews.length; i++) {
                    hintLineView(lineViews[i]);
                }
            } else if (size > 20 && size <= 24) {
                for (int i = size - 5; i < lineViews.length; i++) {
                    hintLineView(lineViews[i]);
                }
            } else if (size > 24 && size <= 28) {
                for (int i = size - 6; i < lineViews.length; i++) {
                    hintLineView(lineViews[i]);
                }
            } else if (size > 28 && size <= 30) {
                for (int i = size - 7; i < lineViews.length; i++) {
                    hintLineView(lineViews[i]);
                }
            }
            for (int i = size; i < imageViews.length; i++) {
                hintImageView(imageViews[i]);
            }
            for (int i = size; i < wTexts.length; i++) {
                hintTextViewW(wTexts[i]);
            }
            for (int i = size; i < nTexts.length; i++) {
                hintTextViewN(nTexts[i]);
            }
        }
    }

    private static void hintTextViewN(TextView nText) {
        nText.setVisibility(View.INVISIBLE);
    }

    private static void hintTextViewW(TextView wText) {
        wText.setVisibility(View.INVISIBLE);
    }

    private static void hintImageView(ImageView imageView) {
        imageView.setVisibility(View.INVISIBLE);
    }

    private static void hintLineView(FlowLineView lineView) {
        lineView.setVisibility(View.INVISIBLE);
    }

    /**
     * 优化器点击事件
     *
     * @param context
     * @param imageView1
     * @param s
     * @param devBean
     */
    private static void addOnclickListenner(final Context context, ImageView imageView1, final String s, final DevBean devBean) {
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YhqDevBeanList yhqDevBeanList = new YhqDevBeanList();
                yhqDevBeanList.setYhqDevList(yhqList);
                Intent intent = new Intent(context, DeviceInfoActivityNew.class);
                intent.putExtra("devId", devBean.getDevId());
                intent.putExtra("devEsn", devBean.getDevEsn());
                intent.putExtra("devBeanList", yhqDevBeanList);
                intent.putExtra("dexNum", Integer.valueOf(s) - 1);
                intent.putExtra("historicalData",HouseholdInvDataActivityNew.historicalData);
                intent.putExtra("tag", "3");
                context.startActivity(intent);
            }
        });
    }

    private static void hintFlowView(FlowLineView flowLineView) {
        flowLineView.setVisibility(View.GONE);
    }
}
