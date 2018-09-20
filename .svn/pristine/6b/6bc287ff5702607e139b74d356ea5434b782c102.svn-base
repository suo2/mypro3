package com.huawei.solarsafe.net;

import android.util.Log;

import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zWX527779 on 2018/2/8.
 */

public abstract class NewFileCallBack extends FileCallBack {
    private static final String TAG = "NewFileCallBack";

    public NewFileCallBack(String destFileDir, String destFileName) {
        super(destFileDir, destFileName);
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        Log.e(TAG, "onError: " + e.getMessage());
    }


    @Override
    public File parseNetworkResponse(Response response, int id) throws Exception {
//        NetRequest.handleToken(response);
        return super.parseNetworkResponse(response, id);
    }
}

