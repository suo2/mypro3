package com.huawei.solarsafe.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.bean.GlobalConstants;


public class ToastUtil {

	private static Handler handler = new Handler(Looper.getMainLooper());

	private static Toast toast = null;

	private static Object synObj = new Object();
	private static Toast toast1;

	public static void showMessage(final String msg) {
		showMessage(msg, Toast.LENGTH_SHORT);
	}

	/**
	 * 根据设置的文本显示
	 * @param msg
	 */
	public static void showMessage(final int msg) {
		showMessage(msg, Toast.LENGTH_SHORT);
	}

	/**
	 * 显示一个文本并且设置时长
	 * @param msg
	 * @param len
	 */
	public static void showMessage(final CharSequence msg, final int len) {
		if (TextUtils.isEmpty(msg) || msg.equals(GlobalConstants.HANDSHARKE_MSG) || MyApplication.is405) {
			// 【安全特性编号】I1  OR_SmartPVMS60_PVMS830_0004_F01_Android_S02  敏感信息检查-日志中不能包含敏感信息    【修改人】：zhaoyufeng
			return;
		}
		handler.post(new Runnable() {
			@Override
			public void run() {
				synchronized (synObj) { //加上同步是为了每个toast只要有机会显示出来
					if (toast != null) {
						toast.setText(msg);
						toast.setDuration(len);
					} else {
						toast = Toast.makeText(MyApplication.getApplication(), msg, len);
					}
					toast.show();
				}
			}
		});
	}

	/**
	 * 资源文件方式显示文本
	 * @param msg
	 * @param len
	 */
	public static void showMessage(final int msg, final int len) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				synchronized (synObj) {
					if (toast != null) {
						toast.setText(msg);
						toast.setDuration(len);
					} else {
						toast = Toast.makeText(MyApplication.getApplication(), msg, len);
					}
					toast.show();
				}
			}
		});
	}

	public static void showToastMsg(Context context,String msg){
		if (TextUtils.isEmpty(msg) || msg.equals(GlobalConstants.HANDSHARKE_MSG)) {
			return;
		}
		if (toast1 == null){
			toast1 = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		}else {
			toast1.setText(msg);
		}
		toast1.show();
	}
}
