package com.pinnet.energy.utils;

import android.text.TextUtils;

import com.pinnet.energy.model.entity.ResponseBean;
import com.pinnet.energy.model.http.exception.ApiException;
import com.pinnet.energy.model.http.exception.EmptyException;
import com.pinnet.energy.model.http.exception.MostLoginException;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author P00701
 * @date 2018/8/31
 */
public class RxUtils {

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<ResponseBean<T>, T> handleResult() {   //compose判断结果
        return new FlowableTransformer<ResponseBean<T>, T>() {
            @Override
            public Flowable<T> apply(Flowable<ResponseBean<T>> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<ResponseBean<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(ResponseBean<T> baseBean) {
                        if (!baseBean.getError()) {
                            return createData(baseBean.getData());
                        } else if (!TextUtils.isEmpty(baseBean.getFailCode())) {
                            return handleErrorResult(baseBean);
                        } else {
                            return Flowable.error(new ApiException("服务器返回error"));
                        }
                    }
                });
            }
        };
    }

    /**
     * 统一返回结果处理
     *
     * @return
     */
    public static FlowableTransformer<ResponseBean, ResponseBean> handleResultBase() {   //compose判断结果
        return new FlowableTransformer<ResponseBean, ResponseBean>() {
            @Override
            public Flowable<ResponseBean> apply(Flowable<ResponseBean> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<ResponseBean, Flowable<ResponseBean>>() {
                    @Override
                    public Flowable<ResponseBean> apply(ResponseBean baseBean) {
                        if (!baseBean.getError()) {
                            return createDataBase(baseBean);
                        } else if (!TextUtils.isEmpty(baseBean.getFailCode())) {
                            return handleErrorResult(baseBean);
                        } else {
                            return Flowable.error(new ApiException("服务器返回error"));
                        }
                    }
                });
            }
        };
    }


    /**
     * 统一返回结果处理
     * 针对返回data 为空的情况
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<ResponseBean<T>, T> handleResultNoParent() {   //compose判断结果
        return new FlowableTransformer<ResponseBean<T>, T>() {
            @Override
            public Flowable<T> apply(Flowable<ResponseBean<T>> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<ResponseBean<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(ResponseBean<T> baseBean) {
                        if (!baseBean.getError()) {
                            if (baseBean.getData() == null) {
                                return Flowable.error(new EmptyException("数据为空"));
                            }
                            return createData(baseBean.getData());
                        } else if (!TextUtils.isEmpty(baseBean.getFailCode())) {
                            return handleErrorResult(baseBean);
                        } else {
                            return Flowable.error(new ApiException("服务器返回error"));
                        }
                    }
                });
            }
        };
    }

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<ResponseBean<T>, T> handleListResult() {   //compose判断结果
        return new FlowableTransformer<ResponseBean<T>, T>() {
            @Override
            public Flowable<T> apply(Flowable<ResponseBean<T>> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<ResponseBean<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(ResponseBean<T> baseBean) {
                        if (!baseBean.getError()) {
                            if (baseBean.getData() == null || ((List) baseBean.getData()).size() == 0) {
                                return Flowable.error(new EmptyException("数据为空"));
                            }
                            return createData(baseBean.getData());
                        } else if (!TextUtils.isEmpty(baseBean.getFailCode())) {
                            return handleErrorResult(baseBean);
                        } else {
                            return Flowable.error(new ApiException("服务器返回error"));
                        }
                    }
                });
            }
        };
    }

    /**
     * 处理错误返回结果
     *
     * @param baseBean
     * @return
     */
    private static Flowable handleErrorResult(ResponseBean baseBean) {
        if ("307".equals(baseBean.getFailCode())) {
            return Flowable.error(new MostLoginException("多端登录", 307));
        } else if ("306".equals(baseBean.getFailCode())) {
            return Flowable.error(new MostLoginException("登录过期", 306));
        }
        String errorMessage = baseBean.getMessage();
//        switch (baseBean.getFailCode()) {
//            case "1004":
//                errorMessage = "手机号未注册";
//                break;
//            case "1007":
//                errorMessage = "请输入正确的手机号码";
//                break;
//            case "1005":
//                errorMessage = "密码错误";
//                break;
//            case "1003":
//                errorMessage = "手机号已经注册过了";
//                break;
//            case "1001":
//                errorMessage = "验证码已过期";
//                break;
//            default:
//                break;
//        }
        return Flowable.error(new ApiException(errorMessage));
    }

    /**
     * 生成Flowable
     *
     * @param <T>
     * @return
     */
    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                try {

                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }

    /**
     * 生成Flowable
     *
     * @return
     */
    public static Flowable<ResponseBean> createDataBase(final ResponseBean t) {
        return Flowable.create(new FlowableOnSubscribe<ResponseBean>() {
            @Override
            public void subscribe(FlowableEmitter<ResponseBean> emitter) throws Exception {
                try {

                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }
}
