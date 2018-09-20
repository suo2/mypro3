package com.pinnet.energy.widget;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.solarsafe.R;

import butterknife.BindView;

/**
 * Created by P00701 on 2018/8/31.
 */

public class CustomSearchview extends LinearLayout {
    private Context mContext;
    private EditText etSearch;
    private ImageView ivDelete;
    /**
     * 输入表情前的光标位置
     */
    private int cursorPos;
    /**
     * 输入表情前EditText中的文本
     */
    private String inputAfterText;
    /**
     * 是否重置了EditText的内容
     */
    private boolean resetText;

    public CustomSearchview(Context context) {
        super(context);
        initLayout(context);
    }

    public CustomSearchview(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    public CustomSearchview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
    }


    private void initLayout(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.item_custom_searchview, this);
        etSearch = (EditText) findViewById(R.id.et_search);
        ivDelete = (ImageView) findViewById(R.id.iv_delete);
        initListener();
    }


    public void clearSearchViewFocus() {
        etSearch.clearFocus();
    }


    private void initListener() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“GO”键*/
                //if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    /*隐藏软键盘*/
                InputMethodManager imm = (InputMethodManager) v
                        .getContext().getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(
                            v.getApplicationWindowToken(), 0);
                }
                if (mListener != null) {
                    etSearch.clearFocus();
                    mListener.onSearch(v.getText().toString());
                }
                return true;
            }
        });
        ivDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setText("");
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!resetText) {
                    cursorPos = etSearch.getSelectionEnd();
                    /**
                     这里用s.toString()而不直接用s是因为如果用s，
                     那么，inputAfterText和s在内存中指向的是同一个地址，s改变了，
                     inputAfterText也就改变了，那么表情过滤就失败了
                     *
                     */
                    inputAfterText = s.toString();
                }
            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                if (!resetText) {
                    if (before != 0) {
                        return;
                    }

                    /** 表情符号的字符长度最小为2 */
                    if (count >= 2) {
                        CharSequence input = text.subSequence(cursorPos, cursorPos + count);

                        /** 若是表情符号就将文本还原为输入表情符号之前的内容并进行提示 */
                        if (containsEmoji(input.toString())) {
                            resetText = true;
                            Toast.makeText(mContext, R.string.not_support_emoji, Toast.LENGTH_SHORT).show();
                            setText(inputAfterText);
                            CharSequence s = getText();
                            if (s instanceof Spannable) {
                                Spannable spanText = (Spannable) s;
                                Selection.setSelection(spanText, s.length());
                            }
                        }
                    }
                } else {
                    resetText = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    ivDelete.setVisibility(View.GONE);
                } else {
                    ivDelete.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public String getText() {
        if (etSearch == null) {
            return "";
        }
        return etSearch.getText().toString();
    }

    public void setText(String content) {
        if (etSearch == null) {
            return;
        }
        etSearch.setText(content);
    }

    public void setHint(String content) {
        if (etSearch == null) {
            return;
        }
        etSearch.setHint(content);
    }

    public void reset(Activity activity) {
        if (etSearch != null) {
            etSearch.clearFocus();
            etSearch.setText("");
        }
        hideKeyboard(activity);
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    private ICustomSearchListener mListener;

    public interface ICustomSearchListener {
        void onSearch(String query);
    }

    public void setOnICustomSearchListener(ICustomSearchListener listener) {
        mListener = listener;
    }

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji，如果不能匹配,则该字符是Emoji表情
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD));
    }
}
