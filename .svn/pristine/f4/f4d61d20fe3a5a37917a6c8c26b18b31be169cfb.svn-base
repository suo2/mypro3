package com.huawei.solarsafe.utils.customview;


import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.huawei.solarsafe.R;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class EditeDialog {
	private Context context;
	private Dialog dialog;
	private LinearLayout lLayout_bg;
	private TextView txt_title;
	private TextView txt_msg;
	private EditText editText;
	private Button btn_neg;
	private Button btn_pos;
	private ImageView img_line;
	private Display display;
	private boolean showTitle = false;
	private boolean showMsg = false;
	private boolean showPosBtn = false;
	private boolean showNegBtn = false;
	private String name;

	public EditeDialog(Context context) {
		this.context = context;
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
	}

	@SuppressWarnings("deprecation")
	public EditeDialog builder() {
		// 获取Dialog布局
		View view = LayoutInflater.from(context).inflate(R.layout.view_editdialog, null);

		// 获取自定义Dialog布局中的控件
		lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
		txt_title = (TextView) view.findViewById(R.id.txt_title);
		editText = (EditText) view.findViewById(R.id.filter_name);
		txt_title.setVisibility(View.VISIBLE);
		btn_neg = (Button) view.findViewById(R.id.btn_neg);
		btn_neg.setVisibility(View.GONE);
		btn_pos = (Button) view.findViewById(R.id.btn_pos);
		btn_pos.setVisibility(View.GONE);
		img_line = (ImageView) view.findViewById(R.id.img_line);
		img_line.setVisibility(View.GONE);

		// 定义Dialog布局和参数
		dialog = new Dialog(context, R.style.AlertDialogStyle);
		dialog.setContentView(view);

		// 调整dialog背景大小
		lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
				.getWidth() * 0.85), LayoutParams.WRAP_CONTENT));

		return this;
	}

	public EditeDialog setTitle(String title) {
		showTitle = true;
		if ("".equals(title)) {
			txt_title.setText(context.getString(R.string.title));
		} else {
			txt_title.setText(title);
		}
		return this;
	}

	public EditeDialog setMsg(String msg) {
		showMsg = true;
		if ("".equals(msg)) {
			txt_msg.setText(context.getString(R.string.content));
		} else {
			txt_msg.setText(msg);
		}
		return this;
	}

	public EditeDialog setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}

	public interface OnNameCallback{
		void nameCallbake(String name);
	}

	public EditeDialog setPositiveButton(String text,
			final OnNameCallback listener) {
		showPosBtn = true;
		if ("".equals(text)) {
			btn_pos.setText(context.getString(R.string.determine));
		} else {
			btn_pos.setText(text);
		}
		btn_pos.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (listener != null) {
					final String name = editText.getText().toString();
					listener.nameCallbake(name);
				}
				dialog.dismiss();
			}
		});
		return this;
	}

	public EditeDialog setNegativeButton(String text, final boolean isDismiss, final OnClickListener listener) {
		showNegBtn = true;
		if ("".equals(text)) {
			btn_neg.setText(context.getString(R.string.cancel));
		} else {
			btn_neg.setText(text);
		}
		btn_neg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
				if(listener!=null)
				listener.onClick(v);
				if(isDismiss)
				dialog.dismiss();
			}
		});
		return this;
	}

	private void setLayout() {
		if (!showTitle && !showMsg) {
			txt_title.setText(context.getString(R.string.notifyTitle));
			txt_title.setVisibility(View.VISIBLE);
		}

		if (showTitle) {
			txt_title.setVisibility(View.VISIBLE);
		}

		if (showMsg) {
			txt_msg.setVisibility(View.VISIBLE);
		}

		if (!showPosBtn && !showNegBtn) {
			btn_pos.setText(context.getString(R.string.determine));
			btn_pos.setVisibility(View.VISIBLE);
			btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
			btn_pos.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
		}

		if (showPosBtn && showNegBtn) {
			btn_pos.setVisibility(View.VISIBLE);
			btn_pos.setBackgroundResource(R.drawable.alertdialog_right_selector);
			btn_neg.setVisibility(View.VISIBLE);
			btn_neg.setBackgroundResource(R.drawable.alertdialog_left_selector);
			img_line.setVisibility(View.VISIBLE);
		}

		if (showPosBtn && !showNegBtn) {
			btn_pos.setVisibility(View.VISIBLE);
			btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
		}

		if (!showPosBtn && showNegBtn) {
			btn_neg.setVisibility(View.VISIBLE);
			btn_neg.setBackgroundResource(R.drawable.alertdialog_single_selector);
		}
	}

	/**
	 * 显示dialog
	 */
	public void show() {
		setLayout();
		if(null!=dialog){
			dialog.show();
		}

	}

	/**
	 * 隐藏dialog
	 */
	public void dismiss() {
		if(null!=dialog){
			dialog.dismiss();
		}
	}

	public void setName(String name) {
		this.name = name;
		if(!TextUtils.isEmpty(name)){
			editText.setText(name);
		}
	}
}
