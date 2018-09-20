package com.huawei.solarsafe.model.personal;

import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.net.NetRequest;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by p00507
 * on 2017/4/10.
 */
public class PimformationModel implements PimformationModelIple{
    private NetRequest request = NetRequest.getInstance();

    @Override
    public void getCompanyImformation( Callback callback) {
        Map<String, String> params = new HashMap<>();
        //【安全特性编号】I1 OR_SmartPVMS60_PVMS830_0004_F01 部分敏感信息APP不做长期存储 【修改人】zhaoyufeng
        params.put("userId", GlobalConstants.userId+"");
        request.asynPostJson(NetRequest.IP + URL_IMFORMATION , params,callback);
    }
}
