package com.huawei.solarsafe.model.maintain.onlinediagnosis;

import com.huawei.solarsafe.model.BaseModel;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;

/**
 * Created by P00229 on 2017/2/21.
 */
public interface IOnlineDiagnosisModel extends BaseModel {
    String URL_DISPERSION = "/dispersion/listDispersion";
    String URL_DISPERSIONSTAT = "/dispersion/listStatDispersion";
    String URL_DISPERSIONDCSTAR = "/dispersion/listCombinerDCStatDispersion";
    String URL_DISPERSIONDC = "/dispersion/listCombinerDCDispersion";

    /**
     * 组串逆变器离散率展示
     *
     * @param params
     * @param callback
     */
    void requestDispersion(Map<String, String> params, Callback callback);

    /**
     * 组串逆变器离散率统计信息
     *
     * @param params
     * @param callback
     */
    void requestStatDispersion(Map<String, String> params, Callback callback);
    /**
     * 直流汇流箱离散率展示
     *
     * @param params
     * @param callback
     */
    void requestDCDispersion(Map<String, String> params, Callback callback);

    /**
     * 直流汇流离散率统计信息
     *
     * @param params
     * @param callback
     */
    void requestDCStatDispersion(Map<String, String> params, Callback callback);

}
