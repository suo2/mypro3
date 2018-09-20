package com.huawei.solarsafe.net;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.utils.AESUtil;
import com.huawei.solarsafe.utils.LocalData;
import com.huawei.solarsafe.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.OkHttpRequestBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.builder.PostStringBuilder;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Response;

/**
 * 网络请求相关方法
 * Created by P00028 on 2016/11/28.
 */
public class NetRequest {

    public static final String TAG = NetRequest.class.getSimpleName();

    public static final String NETERROR = "netError";
    public static String IP;

    private NetRequest() {
    }

    private static class RequestHolder {
        public static final NetRequest INSTANCE = new NetRequest();
    }

    public static NetRequest getInstance() {
        if (!TextUtils.isEmpty(LocalData.getInstance().getIp())) {
            //默认https
            IP = GlobalConstants.protocol + LocalData.getInstance().getIp();
        }
        return RequestHolder.INSTANCE;
    }

    /**
     * 异步Get方法
     *
     * @param url      请求的URL（需要自己手动添加IP）
     * @param args     请求的参数
     * @param callback 请求返回回调
     */
    public void asynGet(String url, Map<String, String> args, final Callback callback) {
        GetBuilder builder = OkHttpUtils.get().url(url);
        configHeader(builder);
        if (args != null && args.size() > 0) {
            for (Map.Entry<String, String> entry : args.entrySet()) {
                builder.addParams(entry.getKey(), entry.getValue());
            }
        }
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        builder.tag(url).build().execute(callback);
    }

    /**
     * 异步Post方法 提交Json数据
     *
     * @param url      请求的URL（需要自己手动添加IP）
     * @param args     请求的参数
     * @param callback 请求返回回调
     */
    public void asynPostJson(String url, Map args, Callback callback) {
        PostStringBuilder builder = OkHttpUtils.postString().url(url);
        configHeader(builder);
        String jsonString = "{}";
        if (args != null && args.size() > 0) {
            Gson gson = new Gson();
            jsonString = gson.toJson(args);
        }
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        builder.content(jsonString).mediaType(MediaType.parse("application/json; charset=utf-8"))
                .tag(url).build().execute(callback);
    }

    /**
     * 异步Post方法 提交Json数据
     *
     * @param url      请求的URL（需要自己手动添加IP）
     * @param args     请求的参数
     * @param callback 请求返回回调
     */
    public void asynPost(String url, Map<String, Long> args, Callback callback) {
        PostStringBuilder builder = OkHttpUtils.postString().url(url);
        configHeader(builder);
        String jsonString = "{}";
        if (args != null && args.size() > 0) {
            Gson gson = new Gson();
            jsonString = gson.toJson(args);
        }
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        builder.content(jsonString).mediaType(MediaType.parse("application/json; charset=utf-8"))
                .tag(url).build().execute(callback);
    }

    /**
     * 异步Post方法 提交Json字符串数据
     *
     * @param url      请求的URL（无需填写IP）
     * @param jString  Json字符串
     * @param callback 请求返回回调
     */
    public void asynPostJsonString(@NonNull String url, @NonNull String jString,
                                   @NonNull Callback callback) {
        PostStringBuilder builder = OkHttpUtils.postString().url(IP + url);
        configHeader(builder);
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        builder.content(jString)
                .mediaType(MediaType.parse("application/json; charset=utf-8"));
        builder.tag(IP + url).build().execute(callback);
    }

    /**
     * 异步Post方法 提交Json字符串数据 带有标志位
     *
     * @param id       标志位 用来标志请求
     * @param url      请求的URL（无需填写IP）
     * @param jString  Json字符串
     * @param callback 请求返回回调
     */
    public void asynPostJsonString(@NonNull int id, @NonNull String url, @NonNull String jString,
                                   @NonNull Callback callback) {
        PostStringBuilder builder = OkHttpUtils.postString().url(IP + url).id(id);
        configHeader(builder);
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        builder.content(jString)
                .mediaType(MediaType.parse("application/json; charset=utf-8"));
        builder.tag(IP + url).build().execute(callback);
    }

    /**
     * 上传文件方法
     *
     * @param url      请求URL
     * @param file     要上传的文件
     * @param params   请求参数
     * @param callback 请求返回回调
     */
    public void postFileWithParams(String url, File file, Map<String, String> params,
                                   Callback callback) {
        if (callback == null) {
            return;
        }
        if (file == null) {
            return;
        }
        PostFormBuilder builder = OkHttpUtils.post();
        configHeader(builder);
        if (url.contains("/user/uploadImg")) {
            //兼容user/uploadImg接口
            builder.addFile("imgFile", file.getName(), file).url(NetRequest.IP + url);
        } else {
            builder.addFile("file", file.getName(), file).url(NetRequest.IP + url);
        }
        if (params != null && params.size() > 0) {
            builder.params(params);
        }
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        builder.tag(IP + url).build().execute(callback);
    }

    public void postFileWithParams(String url, String fileName, File file, Map<String, String> params, Callback callback) {
        if (callback == null) {
            return;
        }
        if (file == null) {
            return;
        }
        PostFormBuilder builder = OkHttpUtils.post();
        configHeader(builder);
        builder.addFile(fileName, file.getName(), file).url(NetRequest.IP + url);
        if (params != null && params.size() > 0) {
            builder.params(params);
        }
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        builder.tag(IP + url).build().execute(callback);
    }

    /**
     * 下载文件方法
     *
     * @param callback 需要传入连个数据，文件的dir和文件名称
     */
    public void downFile(String url, Callback callback) {
        // 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
        GetBuilder getBuilder = OkHttpUtils.get().url(url);
        configHeader(getBuilder);
        getBuilder.tag(url).build().execute(callback);
    }

    /**
     * 为请求添加请求头
     *
     * @param builder 请求Builder
     */
    private static void configHeader(OkHttpRequestBuilder builder) {
        if (builder == null) {
            return;
        }
        builder.addHeader("appDeviceType", "ANDROID");
        builder.addHeader("language", Utils.getLanguageOther());
        builder.addHeader("Prefer_Lang", Utils.getLanguageOther());
        builder.addHeader("Timezone", LocalData.getInstance().getTimezone());
        builder.addHeader("XSRF-TOKEN", AESUtil.decrypt(GlobalConstants.token));
    }

    /**
     * 处理请求回来到的token值
     * @param response
     */
    public static void handleToken(Response response){
        GlobalConstants.token = AESUtil.encrypt(response.header("XSRF-TOKEN"));
    }
}
