package com.huawei.solarsafe.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.huawei.solarsafe.MyApplication;
import com.huawei.solarsafe.R;
import com.huawei.solarsafe.bean.Constant;
import com.huawei.solarsafe.bean.CurrencyType;
import com.huawei.solarsafe.bean.GlobalConstants;
import com.huawei.solarsafe.bean.UnitType;
import com.huawei.solarsafe.bean.common.ResetBean;
import com.huawei.solarsafe.bean.device.DevTypeConstant;
import com.huawei.solarsafe.bean.station.kpi.StationList;

import com.huawei.solarsafe.bean.stationmagagement.DeviceData;
import com.huawei.solarsafe.bean.stationmagagement.SubDev;

import com.huawei.solarsafe.net.NetRequest;
import com.huawei.solarsafe.net.NewFileCallBack;
import com.huawei.solarsafe.utils.customview.DialogUtil;
import com.huawei.solarsafe.utils.customview.LoadingDialog;
import com.huawei.solarsafe.utils.language.WappLanguage;
import com.huawei.solarsafe.view.personal.NewChangePswActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;

import static com.huawei.solarsafe.MyApplication.isFinishedByUser;

/**
 * Created by p00322 on 2017/1/4.
 */
public class Utils {
    private static final String CONSTANTS_STRING_EMPTY = "";
    private static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT_YYYYMMDDHHMMSS_NO_DIV = "yyyyMMddHHmmss";
    private static final String DATE_FORMAT_YYYYMMDDHHMMSS_ = "yyyyMMdd";
    public static final double LATLNG_DIFF = 0.000001;
    public static final double LATITUDE_MAX = 90;
    public static final double LATITUDE_MIN = -90;
    public static final double LONGITUDE_MAX = 180;
    public static final double LONGITUDE_MIN = -180;
    private static final String CONSTANTS_STRING_ZERO = "0";
    private static final String TAG = "Utils";
    private static HashMap<String, String> map;

    static {
        map = new HashMap<>();
        //添加过滤数字'0' - '9'
        for (int i = 0; i <= 9; i++) {
            map.put(String.valueOf(i), String.valueOf(i));
        }

        //添加过滤大写英文字母'A' - 'Z'
        for (int i = 65; i < 91; ++i) {
            map.put(String.valueOf((char) i), String.valueOf((char) i));
        }

        // 添加过滤小写英文字母'a' - 'z'
        for (int i = 97; i < 123; ++i) {
            map.put(String.valueOf((char) i), String.valueOf((char) i));
        }

        //添加过滤特殊字符
        map.put(":", ":");
        map.put("/", "/");
        map.put(" ", " ");
        map.put(".", ".");
        map.put("\\", "\\");
        //添加项目所用到的指定中文路径过滤汉字
        map.put("智", "智");
        map.put("能", "能");
        map.put("光", "光");
        map.put("伏", "伏");
        map.put("管", "管");
        map.put("理", "理");
        map.put("帮", "帮");
        map.put("助", "助");
        map.put("文", "文");
        map.put("档", "档");
        map.put("日", "日");
        map.put("文", "文");
        map.put("中", "中");
        map.put("英", "英");
        map.put("查", "查");
        map.put("看", "看");
        map.put("电", "电");
        map.put("站", "站");
        map.put("信", "信");
        map.put("息", "息");
        map.put("运", "运");
        map.put("维", "维");
        map.put("移", "移");
        map.put("动", "动");
        map.put("设", "设");
        map.put("备", "备");
        map.put("报", "报");
        map.put("表", "表");
        map.put("用", "用");
        map.put("户", "户");
        map.put("个", "个");
        map.put("人", "人");
        map.put("信", "信");
        map.put("息", "息");
        map.put("首", "首");
        map.put("页", "页");
        map.put("一", "一");
        map.put("键", "键");
        map.put("开", "开");
        map.put("界", "界");
        map.put("面", "面");
        map.put("介", "介");
        map.put("绍", "绍");
        //添加项目所用到的指定日文路径过滤汉字  個人情報を
        map.put("発", "発");
        map.put("電", "電");
        map.put("所", "所");
        map.put("情", "情");
        map.put("報", "報");
        map.put("を", "を");
        map.put("確", "確");
        map.put("認", "認");
        map.put("す", "す");
        map.put("る", "る");
        map.put("こ", "こ");
        map.put("と", "と");
        map.put("運", "運");
        map.put("営", "営");
        map.put("保", "保");
        map.put("守", "守");
        map.put("移", "移");
        map.put("動", "動");
        map.put("の", "の");
        map.put("設", "設");
        map.put("備", "備");
        map.put("レ", "レ");
        map.put("ポ", "ポ");
        map.put("ー", "ー");
        map.put("ト", "ト");
        map.put("ユ", "ユ");
        map.put("ザ", "ザ");
        map.put("ッ", "ッ");
        map.put("プ", "プ");
        map.put("ペ", "ペ");
        map.put("ジ", "ジ");
        map.put("イ", "イ");
        map.put("ン", "ン");
        map.put("タ", "タ");
        map.put("フ", "フ");
        map.put("ェ", "ェ");
        map.put("ス", "ス");
        map.put("の", "の");
        map.put("紹", "紹");
        map.put("介", "介");
        map.put("デ", "デ");
        map.put("収", "収");
        map.put("集", "集");
        map.put("装", "装");
        map.put("置", "置");
    }

    /**
     * 关闭软键盘
     */
    public static void closeSoftKeyboard(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && ((Activity) context).getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * @param v
     * @param event
     * @return 点击输入框以外的地方
     */
    public static boolean isShouldHideInput(View v, MotionEvent event, int scrollPosition) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1] - scrollPosition;

            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() >= left && event.getX() <= right
                    && event.getY() >= top && event.getY() <= bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return true;
    }


    public static void startActivityWithBundle(Activity activity, Class destAct,
                                               ResetBean bean) {
        Intent intent = new Intent(activity, destAct);
        Bundle b = new Bundle();
        if (bean != null) {
            b.putSerializable("newStation", bean.getStationInfos());
            b.putString("needReset", bean.getNeedReset());
            b.putString("reason", bean.getReason());
        }
        intent.putExtra("b", b);
        activity.startActivity(intent);
    }


    public static ResetBean getDataFromIntent(Intent intent) {

        //【安全特性】null check 【修改人】zhaoyufeng
        if (intent != null) {
            //【安全特性】获取Intent数据时，未进行异常捕获，如果攻击者传入异常数据，会造成拒绝服务漏洞  【修改人】zhaoyufeng
            try {
                Bundle b = intent.getBundleExtra("b");
                StationList stationInfos = (StationList) b.getSerializable("newStation");
                String needReset = b.getString("needReset");
                String reason = b.getString("reason");
                if (TextUtils.isEmpty(needReset)) {
                    needReset = "no";
                }
                ResetBean bean = new ResetBean();
                bean.setStationInfos(stationInfos);
                bean.setNeedReset(needReset);
                bean.setReason(reason);
                return bean;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }


    //添加口令时效期判断   修改人：江东
    public static void handNeedReset(final Activity context, ResetBean bean) {
        if (bean == null) return;
        if (!TextUtils.isEmpty(bean.getNeedReset())) {
            switch (bean.getNeedReset()) {
                case "no":    //正常不做处理
                    break;
                case "later": //口令即将过期，需要弹窗提示
                    DialogUtil.showChooseDialog(context, context.getString(R.string.alarm_reverse),
                            context.getString(R.string.kouling_tips), context.getString(R.string.yes),
                            context.getString(R.string.no), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    SysUtils.startActivity(context, NewChangePswActivity.class);
                                }
                            }, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });
                    break;
                case "now":  //强制修改
                    int res;
                    if (!TextUtils.isEmpty(bean.getReason())) {
                        switch (bean.getReason()) {
                            case "overdue":
                                res = R.string.overdue;
                                break;
                            case "newUser":
                                res = R.string.newUser;
                                break;
                            case "resetUser":
                                res = R.string.resetUser;
                                break;
                            default:
                                res = R.string.kouling_now_tips;
                                break;
                        }
                    } else {
                        res = R.string.kouling_now_tips;
                    }
                    DialogUtil.showErrorMsgWithClick(context,
                            context.getString(res), context.getString(R.string.yes), true,
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Bundle b = new Bundle();
                                    b.putBoolean(GlobalConstants.ISFORCECHANGE, true);
                                    SysUtils.startActivity(context, NewChangePswActivity.class, b);
                                    if (GlobalConstants.shouldCloseLoginActivity) {
                                        GlobalConstants.shouldCloseLoginActivity = false;
                                        isFinishedByUser = true;
                                        context.finish();
                                    }
                                }
                            });
                    break;
            }
        }
    }

    /**
     * 判断是否是Double无穷小 或无穷大
     *
     * @param doubleValue
     * @return
     */
    public static boolean isDoubleMinValue(Double doubleValue) {
        return Double.doubleToLongBits(doubleValue) == Double.doubleToLongBits(Double.MIN_VALUE)
                || doubleValue.isInfinite();
    }

    /**
     * 判断经纬度是否为空
     *
     * @param latitude
     * @param longitude
     * @return 为空返回true，否则返回false
     */
    public static boolean judgeLatlngIsNull(double latitude, double longitude) {
        if (Utils.isDoubleMinValue(latitude) || Utils.isDoubleMinValue(longitude)) {
            return true;
        }
        return false;
    }

    public static boolean judgeLatlngIsInvalid(double latitude, double longitude) {
        if (latitude < LATITUDE_MIN || latitude > LATITUDE_MAX || longitude < LONGITUDE_MIN || longitude >
                LONGITUDE_MAX) {
            return true;
        }
        return false;
    }

    /**
     * // 转换文件大小
     *
     * @param fileS
     * @return
     */
    public static String formetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 解析毫秒时间为yyyy格式的时间（包含国际化）
     *
     * @param time
     * @return
     */
    public static String getFormatTimeYYYY(long time) {
        if (time <= 0) {
            return CONSTANTS_STRING_EMPTY;
        }
        return WappLanguage.getFormatTimeYYYY(time);
    }

    /**
     * 解析毫秒时间为yyyy/MM格式的时间(包含国际化)
     *
     * @param time
     * @return
     */
    public static String getFormatTimeYYYYMM(long time) {
        if (time <= 0) {
            return CONSTANTS_STRING_EMPTY;
        }
        return WappLanguage.getFormatTimeYYMM(time);
    }

    /**
     * 解析毫秒时间为MM/dd格式的时间(包含国际化)
     *
     * @param time
     * @return
     */
    public static String getFormatTimeMMDD(long time) {
        if (time <= 0) {
            return CONSTANTS_STRING_EMPTY;
        }

        return WappLanguage.getFormatTimeMMdd(time);
    }

    /**
     * 解析毫秒时间为MM/dd格式的时间(包含国际化和时区)
     *
     * @param time
     * @return
     */
    public static String getFormatTimeMMDD(long time, String timeZone) {
        if (time <= 0) {
            return CONSTANTS_STRING_EMPTY;
        }
        return WappLanguage.getFormatTimeMMdd(time, timeZone);
    }

    /**
     * 解析毫秒时间为yyyy/MM/dd格式的时间(包含国际化)
     *
     * @param time
     * @return
     */
    public static String getFormatTimeYYMMDD(long time) {
        if (time <= 0) {
            return CONSTANTS_STRING_EMPTY;
        }
        return WappLanguage.getFormatTimeYYMMdd(time);
    }

    public static String getFormatTimeYYMMDDHHMMSS(long time) {
        if (time <= 0) {
            return CONSTANTS_STRING_EMPTY;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YYYYMMDDHHMMSS, Locale.getDefault());
        Date date = new Date(time);
        return formatter.format(date);
    }

    public static String getTimeYYMMDDHHMMSS(long time) {
        if (time <= 0) {
            return CONSTANTS_STRING_EMPTY;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YYYYMMDDHHMMSS);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        Date date = new Date(time);
        return formatter.format(date);
    }

    public static String getFormatTime(long time) {
        if (time <= 0) {
            return CONSTANTS_STRING_EMPTY;
        }
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_YYYYMMDDHHMMSS_, Locale.getDefault());
        Date date = new Date(time);
        return format.format(date);
    }

    /**
     * 将时间格式为yyyy-MM-dd 的时间转换成long
     *
     * @param time
     * @return
     */
    public static long getMillisFromYYMMDD(String time) {
        return WappLanguage.getMillisFromYYMMDD(time);
    }


    /**
     * 将时间格式为yyyy/MM/dd HH:mm:ss的时间转换成毫秒(包含国际化)
     *
     * @param time
     * @return
     */
    public static long getMillisFromYYMMDDHHmmss(String time) {
        return WappLanguage.getMillisFromMMDDHHmmss(time);
    }


    /**
     * 解析毫秒时间为yyyy-MM-dd HH:mm:ss格式的时间
     *
     * @param time
     * @return
     */
    public static String getFormatTimeYYMMDDHHmmss2(long time) {
        if (time < 0) {
            return CONSTANTS_STRING_EMPTY;
        }
        return WappLanguage.getFormatTimeYYMMddHHssmm(time);
    }

    /**
     * 解析毫秒时间
     *
     * @param time
     * @param timeZone
     * @return
     */
    public static String getFormatTimeYYMMDDHHmmss2(long time, String timeZone) {
        if (time < 0) {
            return CONSTANTS_STRING_EMPTY;
        }
        return WappLanguage.getFormatTimeYYMMddHHssmm(time, timeZone);
    }

    /**
     * 解析毫秒时间为yyyy-MM-dd格式的时间
     *
     * @param time
     * @return
     */
    public static String getFormatTimeYYMMDD2(long time) {
        if (time <= 0) {
            return CONSTANTS_STRING_EMPTY;
        }
        return WappLanguage.getFormatTimeYYMMdd(time);
    }


    /**
     * dp转换成px
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dp2Px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    /**
     * 获取屏幕宽高，像素密度,状态栏高度
     *
     * @return 第一位是宽，第二位是高, 第三位是像素密度，第四位状态栏高度
     */
    public static float[] getScreenWH(Context context) {
        DisplayMetrics metric = context.getResources().getDisplayMetrics();
        float width = metric.widthPixels;     // 屏幕宽度（像素）
        float height = metric.heightPixels;   // 屏幕高度（像素）
        float density = metric.density;      // 屏幕密度（0.75 / 1.0 / 1.5）
        int statusBarHeight1 = -1;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight1 = context.getResources().getDimensionPixelSize(resourceId);
        }
        return new float[]{width, height, density, statusBarHeight1};
    }

    /**
     * 判断当前系统语言是否是中文
     *
     * @param context
     * @return
     */
    public static boolean isZh(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        boolean lanIsZh = locale.getLanguage().equals(new Locale("zh", "", "").getLanguage());
        return lanIsZh;
    }


    /**
     * 根据指定的图像路径和大小来获取缩略图 此方法有2点好处：
     * 1、使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
     * 第二次获取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图
     * 2、缩略图对于原图像来讲没有拉伸，这里使用了ThumbnailUtils，使用这个工具生成的图像不会被拉伸
     *
     * @param imagePath 图像的路径
     * @param width     指定输出图像的宽度
     * @param height    指定输出图像的高度
     * @return 生成的缩略图
     */
    public static Bitmap getImageThumbnail(String imagePath, int width, int height) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取图片的宽高
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false;
        // 计算缩放比
        int h = options.outHeight;
        int w = options.outWidth;
        int beWidth = w / width;
        int beHeight = h / height;
        int be = 1;
        if (beWidth < beHeight) {
            be = beWidth;
        } else {
            be = beHeight;
        }
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        // 重新读入图片，读取缩放后的bitmap
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    /**
     * @param path 图片文件的路径
     * @return 图片的旋转角度
     */
    public static int getPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface
                    .ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            Log.e(TAG, "getPictureDegree: " + e.getMessage());
        }
        return degree;
    }

    /**
     * @param angle  pic旋转的角度
     * @param bitmap 旋转的图片
     * @return
     */
    public static Bitmap rotaingPic(int angle, Bitmap bitmap) {
        if (bitmap == null)
            return null;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Bitmap result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return result;
    }

    public static String getLanguage() {
        String country = MyApplication.getContext().getResources().getConfiguration().locale.getCountry();
        String language = MyApplication.getContext().getResources().getConfiguration().locale.getLanguage();
        return language + "_" + country;
    }

    public static String getLanguageOther() {
        String country = MyApplication.getContext().getResources().getConfiguration().locale.getCountry();
        if ("CN".equals(country)) {
            country = "CN";
        } else if ("JP".equals(country)) {
            country = "JP";
        } else if("IT".equals(country)) {
            country = "IT";
        }else if("NL".equals(country)){
            country = "NL";
        }else {
            country = "UK";
        }

        String language = MyApplication.getContext().getResources().getConfiguration().locale.getLanguage();
        return language + "_" + country;
    }

    /**
     * 输出数字的格式,如:1,234,567.89
     *
     * @param value  BigDecimal 要格式化的数字
     * @param format String 格式 "###,###.00"
     * @returnString
     */
    public static String numberFormat(BigDecimal value, String format) {

        if (value == null) {
            return CONSTANTS_STRING_ZERO;
        }

        int characterIndex = -1;
        characterIndex = format.indexOf(".");
        int scale = 0;
        if (characterIndex > 0) {
            // 不保留小数
            if (format.length() - characterIndex - 1 == 0) {
                scale = 0;
            }
            // 保留一位小数·
            else if (format.length() - characterIndex - 1 == 1) {
                scale = 1;
            }
            //保留三位小数
            else if (format.length() - characterIndex - 1 == 3) {
                scale = 3;
            }
            // 保留两位小数
            else {
                scale = 2;
            }
        }
        String round = round(value.doubleValue(), scale);
        // 不分割，去分隔符
        if (format.indexOf(",") < 0) {
            round = round.replaceAll(",", "");
        }
        return round;
    }

    /**
     * 保留小数四舍五入
     *
     * @param value
     * @param scale 保留的小数位数
     * @return
     */
    public static String round(double value, int scale) {
        final NumberFormat format = NumberFormat.getNumberInstance(Locale.ENGLISH);
        format.setMaximumFractionDigits(scale);
        format.setMinimumFractionDigits(scale);
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(value);
    }

    /**
     * 保留小数四舍五入
     *
     * @param value
     * @param scale 保留的小数位数
     * @return
     */
    public static Double roundDouble(double value, int scale) {
        final NumberFormat format = NumberFormat.getNumberInstance(Locale.ENGLISH);
        format.setMaximumFractionDigits(scale);
        format.setMinimumFractionDigits(scale);
        format.setRoundingMode(RoundingMode.HALF_UP);
        return Double.valueOf(format.format(value));
    }

    /**
     * 保留小数四舍五入
     *
     * @param value
     * @param scale
     * @return
     */
    public static String round(long value, int scale) {
        final NumberFormat format = NumberFormat.getNumberInstance(Locale.ENGLISH);
        format.setMaximumFractionDigits(scale);
        format.setMinimumFractionDigits(scale);
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(value);
    }

    /**
     * 处理功率单位转换，kW/MW/GW/TW
     *
     * @param value
     * @return
     */
    public static String handlePowerUnit(double value) {
        if (Utils.isDoubleMinValue(value)) {
            return GlobalConstants.INVALID_DATA;
        }
        if (Double.doubleToLongBits(value) >= Double.doubleToLongBits(GlobalConstants.TW_TO_KW)) {
            double gw = value / GlobalConstants.TW_TO_KW;
            String gwFormat = NumberFormatPresident.numberFormat(gw, NumberFormatPresident.FORMAT_COMMA_WITH_THREE);
            return gwFormat + GlobalConstants.TW_TEXT;
        }
        if (Double.doubleToLongBits(value) >= Double.doubleToLongBits(GlobalConstants.GW_TO_KW)) {
            double gw = value / GlobalConstants.GW_TO_KW;
            String gwFormat = NumberFormatPresident.numberFormat(gw, NumberFormatPresident.FORMAT_COMMA_WITH_THREE);
            return gwFormat + GlobalConstants.GW_TEXT;
        }
        if (Double.doubleToLongBits(value) >= Double.doubleToLongBits(GlobalConstants.MW_TO_KW)) {
            double mw = value / GlobalConstants.MW_TO_KW;
            String mwFormat = NumberFormatPresident.numberFormat(mw, NumberFormatPresident.FORMAT_COMMA_WITH_THREE);
            return mwFormat + GlobalConstants.MW_TEXT;
        } else {
            String kwFormat = NumberFormatPresident.numberFormat(value, NumberFormatPresident.FORMAT_COMMA_WITH_THREE);
            return kwFormat + GlobalConstants.KW_TEXT;
        }
    }

    /**
     * 处理功率单位转换，kWp/MW/GW/TW
     *
     * @param value
     * @return
     */
    public static String handlePowerUnitNew(double value) {
        if (Utils.isDoubleMinValue(value)) {
            return GlobalConstants.INVALID_DATA;
        }
        if (Double.doubleToLongBits(value) >= Double.doubleToLongBits(GlobalConstants.TW_TO_KW)) {
            double gw = value / GlobalConstants.TW_TO_KW;
            String gwFormat = NumberFormatPresident.numberFormat(gw, NumberFormatPresident.FORMAT_COMMA_WITH_THREE);
            return gwFormat + GlobalConstants.TW_TEXT;
        }
        if (Double.doubleToLongBits(value) >= Double.doubleToLongBits(GlobalConstants.GW_TO_KW)) {
            double gw = value / GlobalConstants.GW_TO_KW;
            String gwFormat = NumberFormatPresident.numberFormat(gw, NumberFormatPresident.FORMAT_COMMA_WITH_THREE);
            return gwFormat + GlobalConstants.GW_TEXT;
        }
        if (Double.doubleToLongBits(value) >= Double.doubleToLongBits(GlobalConstants.MW_TO_KW)) {
            double mw = value / GlobalConstants.MW_TO_KW;
            String mwFormat = NumberFormatPresident.numberFormat(mw, NumberFormatPresident.FORMAT_COMMA_WITH_THREE);
            return mwFormat + GlobalConstants.MW_TEXT;
        } else {
            String kwFormat = NumberFormatPresident.numberFormat(value, NumberFormatPresident.FORMAT_COMMA_WITH_THREE);
            return kwFormat + GlobalConstants.KWP_TEXT;
        }
    }

    /**
     * 处理功率单位转换，kW/MW/GW/TW
     *
     * @param value
     * @return
     */

    public static String getPowerUnit(double value) {
        if (Utils.isDoubleMinValue(value)) {
            return GlobalConstants.INVALID_DATA;
        }
        if (Double.doubleToLongBits(value) >= Double.doubleToLongBits(GlobalConstants.TW_TO_KW)) {
            return GlobalConstants.TW_TEXT;
        }
        if (Double.doubleToLongBits(value) >= Double.doubleToLongBits(GlobalConstants.GW_TO_KW)) {
            return GlobalConstants.GW_TEXT;
        }
        if (Double.doubleToLongBits(value) >= Double.doubleToLongBits(GlobalConstants.MW_TO_KW)) {
            return GlobalConstants.MW_TEXT;
        } else {
            return GlobalConstants.KW_TEXT;
        }
    }

    /**
     * 处理功率时单位转换，kWh/MWh/GWh/TWh
     *
     * @param value
     * @return
     */

    public static String getPowerHourUnit(double value) {
        if (Utils.isDoubleMinValue(value)) {
            return GlobalConstants.INVALID_DATA;
        }
        if (Double.doubleToLongBits(value) >= Double.doubleToLongBits(GlobalConstants.TW_TO_KW)) {
            return GlobalConstants.TWH_TEXT;
        }
        if (Double.doubleToLongBits(value) >= Double.doubleToLongBits(GlobalConstants.GW_TO_KW)) {
            return GlobalConstants.GWH_TEXT;
        }
        if (Double.doubleToLongBits(value) >= Double.doubleToLongBits(GlobalConstants.MW_TO_KW)) {
            return GlobalConstants.MWH_TEXT;
        } else {
            return GlobalConstants.KWH_TEXT;
        }
    }

    /**
     * 处理功率时单位转换，kWh/MWh/GWh/TWh
     *
     * @param value
     * @return
     */

    public static double getPowerHourUnitValue(double value) {
        if (Utils.isDoubleMinValue(value)) {
            return value;
        }
        if (Double.doubleToLongBits(value) >= Double.doubleToLongBits(GlobalConstants.TW_TO_KW)) {
            return value/GlobalConstants.TW_TO_KW;
        }
        if (Double.doubleToLongBits(value) >= Double.doubleToLongBits(GlobalConstants.GW_TO_KW)) {
            return value/GlobalConstants.GW_TO_KW;
        }
        if (Double.doubleToLongBits(value) >= Double.doubleToLongBits(GlobalConstants.MW_TO_KW)) {
            return value/GlobalConstants.MW_TO_KW;
        } else {
            return value;
        }
    }

    /**
     * 获取功率单位转换需要缩小的倍数
     *
     * @param value
     * @return
     */
    public static long getPowerUnitConversionMultiple(double value) {

        if (Utils.isDoubleMinValue(value)) {
            return 1;
        }
        if (Double.doubleToLongBits(value) >= Double.doubleToLongBits(GlobalConstants.TW_TO_KW)) {
            return 1000000*1000;
        }
        if (Double.doubleToLongBits(value) >= Double.doubleToLongBits(GlobalConstants.GW_TO_KW)) {
            return 1000000;
        }
        if (Double.doubleToLongBits(value) >= Double.doubleToLongBits(GlobalConstants.MW_TO_KW)) {
            return 1000;
        } else {
            return 1;
        }
    }

    /**
     * 数值较大时获取转换后的进制单位
     *
     * @param value   原数值
     * @param context 上下文
     * @return 进制单位
     */

    public static String getNumberUnit(float value, Context context) {
        String country = getLanguageOther();//只针对中国、日本、欧美，其它国家默认欧美
        if (country.contains("CN") || country.contains("JP")) {
            if (value >= 100000000) {
                return context.getResources().getString(R.string.billion_wan);
            } else if (value >= 10000 && value < 100000000) {
                return context.getResources().getString(R.string.million_wan);
            } else {
                return "";
            }
        } else {
            if (value >= 1000000000) {
                return context.getResources().getString(R.string.billion_unit) + " ";
            } else if (value >= 1000000 && value < 1000000000) {
                return context.getResources().getString(R.string.million_unit) + " ";
            } else if (value >= 1000 && value < 1000000) {
                return context.getResources().getString(R.string.thousand_unit) + " ";
            } else {
                return "";
            }
        }
    }

    public static String getNumberUnit(double value, Context context) {
        String country = getLanguageOther();//只针对中国、日本、欧美，其它国家默认欧美
        if (country.contains("CN") || country.contains("JP")) {
            if (value >= 100000000) {
                return context.getResources().getString(R.string.billion_wan);
            } else if (value >= 10000 && value < 100000000) {
                return context.getResources().getString(R.string.million_wan);
            } else {
                return "";
            }
        } else {
            if (value >= 1000000000) {
                return context.getResources().getString(R.string.billion_unit);
            } else if (value >= 1000000 && value < 1000000000) {
                return context.getResources().getString(R.string.million_unit);
            } else if (value >= 1000 && value < 1000000) {
                return context.getResources().getString(R.string.thousand_unit);
            } else {
                return "";
            }
        }
    }

    public static double getNumberUnitConversionValue(double value) {
        String country = getLanguageOther();//只针对中国、日本、欧美，其它国家默认欧美
        if (country.contains("CN") || country.contains("JP")) {
            if (value >= 100000000) {
                return value / 100000000;
            } else if (value >= 10000 && value < 100000000) {
                return value / 10000;
            } else {
                return value;
            }
        } else {
            if (value >= 1000000000) {
                return value / 1000000000;
            } else if (value >= 1000000 && value < 1000000000) {
                return value / 1000000;
            } else if (value >= 1000 && value < 1000000) {
                return value / 1000;
            } else {
                return value;
            }
        }

    }

    /**
     * 获取单位转换需要缩小的倍数
     *
     * @param value
     * @return
     */
    public static long getNumberUnitConversionMultiple(float value) {
        String country = getLanguageOther();//只针对中国、日本、欧美，其它国家默认欧美
        if (country.contains("CN") || country.contains("JP")) {
            if (value >= 100000000) {
                return 100000000;
            } else if (value >= 10000 && value < 100000000) {
                return 10000;
            } else {
                return 1;
            }
        } else {
            if (value >= 1000000000) {
                return 1000000000;
            } else if (value >= 1000000 && value < 1000000000) {
                return 1000000;
            } else if (value >= 1000 && value < 1000000) {
                return 1000;
            } else {
                return 1;
            }
        }
    }


    /**
     * 对KWH数据进行单位转换
     *
     * @param value   数据
     * @param context
     * @return 转换后的数据
     */
    public static String getUnitConversionkWhValue(double value, Context context) {
        if (Utils.isDoubleMinValue(value)) {
            return GlobalConstants.INVALID_DATA;
        }
        String country = getLanguageOther();//只针对中国、日本、欧美，其它国家默认欧美
        if (country.contains("CN") || country.contains("JP")) {
            if (value >= 100000000) {
                value = value / 100000000;
                String str = NumberFormatPresident.numberFormat(value, NumberFormatPresident.FORMAT_COMMA_WITH_THREE);
                return str + context.getResources().getString(R.string.power_unit_billionkwh);
            } else if (value >= 10000 && value < 100000000) {
                value = value / 10000;
                String str = NumberFormatPresident.numberFormat(value, NumberFormatPresident.FORMAT_COMMA_WITH_THREE);
                return str + context.getResources().getString(R.string.power_unit_wankwh);
            } else {
                String str = NumberFormatPresident.numberFormat(value, NumberFormatPresident.FORMAT_COMMA_WITH_THREE);
                return str + context.getResources().getString(R.string.power_unit_kwh);
            }
        } else {
            if (value >= 1000000000) {
                value = value / 1000000000;
                String str = NumberFormatPresident.numberFormat(value, NumberFormatPresident.FORMAT_COMMA_WITH_THREE);
                return str + context.getResources().getString(R.string.billion_kw_unit);
            } else if (value >= 1000000 && value < 1000000000) {
                value = value / 1000000;
                String str = NumberFormatPresident.numberFormat(value, NumberFormatPresident.FORMAT_COMMA_WITH_THREE);
                return str + context.getResources().getString(R.string.million_kw_unit);
            } else if (value >= 1000 && value < 1000000) {
                value = value / 1000;
                String str = NumberFormatPresident.numberFormat(value, NumberFormatPresident.FORMAT_COMMA_WITH_THREE);
                return str + context.getResources().getString(R.string.thousand_kw_unit);
            } else {
                String str = NumberFormatPresident.numberFormat(value, NumberFormatPresident.FORMAT_COMMA_WITH_THREE);
                return str + context.getResources().getString(R.string.power_unit_kwh);
            }
        }
    }

    public static String getUnitConversionkWhUnit(double value, Context context) {

        if (Utils.isDoubleMinValue(value)) {
            return GlobalConstants.INVALID_DATA;
        }
        String country = getLanguageOther();//只针对中国、日本、欧美，其它国家默认欧美
        if (country.contains("CN") || country.contains("JP")) {
            if (value >= 100000000) {
                return context.getResources().getString(R.string.power_unit_billionkwh);
            } else if (value >= 10000 && value < 100000000) {
                return context.getResources().getString(R.string.power_unit_wankwh);
            } else {

                return context.getResources().getString(R.string.power_unit_kwh);
            }
        } else {
            if (value >= 1000000000) {
                return context.getResources().getString(R.string.billion_kw_unit);
            } else if (value >= 1000000 && value < 1000000000) {

                return context.getResources().getString(R.string.million_kw_unit);
            } else if (value >= 1000 && value < 1000000) {

                return context.getResources().getString(R.string.thousand_kw_unit);
            } else {
                return context.getResources().getString(R.string.power_unit_kwh);
            }
        }
    }

    /**
     * 获取本地保存的货币单位
     *
     * @return
     */
    public static String getCurrencyType() {
        int currencyType = Integer.parseInt(LocalData.getInstance().getCrrucy());
        CurrencyType currencyEnum = CurrencyType.parseString(currencyType);
        return currencyEnum.getCurrencyFlag();
    }

    /**
     * 判断是否是Integer无穷小
     *
     * @param intData
     * @return
     */
    public static boolean isIntegerMinValue(Integer intData) {
        return intData == Integer.MIN_VALUE
                || intData == Integer.MAX_VALUE;
    }

    /**
     * 我的模块，下载图片
     * @param FileName
     * @param imageView
     */
    public static void downloadPic(String FileName,String userId, long time,final ImageView imageView){
        String url = NetRequest.IP + "/user/getImage?userId=" + userId + "&time=" + time;
        String dir="";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            dir = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        deletePicDirectory();
        final String mFilePath =dir + File.separator + "fusionHome" + File.separator
                + "Picture" + File.separator + "My";
        File fileDir = new File(mFilePath);
        if(!fileDir.exists()){
            fileDir.mkdirs();
        }
        NetRequest.getInstance().downFile(url, new NewFileCallBack(mFilePath,FileName+time) {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e(TAG, "onError: " + e.getMessage());
                super.onError(call, e, id);
            }

            @Override
            public void onResponse(File file, int i) {
                imageView.setImageURI(Uri.fromFile(file));
            }
        });
    }


    /**
     * 将嵌套Map转为JsonString
     *
     * @param values
     * @param values2
     * @return
     * @throws Exception
     */
    public static String createReqArgs(Map<String, Map<String, String>> values,
                                       Map<String, List<Map<String, String>>> values2) {
        String result;
        JSONObject jsonObj = new JSONObject();
        try {
            if (values != null) {
                JSONArray jsonArray = new JSONArray();
                for (Map.Entry<String, Map<String, String>> oneEntry : values.entrySet()) {
                    String keyArray = oneEntry.getKey();
                    Map<String, String> valueArray = oneEntry.getValue();
                    JSONObject json = new JSONObject();
                    for (Map.Entry<String, String> v : valueArray.entrySet()) {
                        String key = v.getKey();
                        String value = v.getValue();
                        json.put(key, value);
                    }
                    jsonArray.put(json);
                    jsonObj.put(keyArray, json);
                }
            }
            if (values2 != null) {
                for (Map.Entry<String, List<Map<String, String>>> oneEntry : values2.entrySet()) {
                    JSONArray jsonArray = new JSONArray();
                    String key = oneEntry.getKey();
                    List<Map<String, String>> value = oneEntry.getValue();

                    for (Map<String, String> v : value) {
                        JSONObject jsonItem = new JSONObject();
                        for (Map.Entry<String, String> vItem : v.entrySet()) {
                            String vKey = vItem.getKey();
                            String vValue = vItem.getValue();
                            jsonItem.put(vKey, vValue);
                        }
                        jsonArray.put(jsonItem);
                    }
                    jsonObj.put(key, jsonArray);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "createReqArgs: " + e.getMessage());
        }
        result = jsonObj.toString();
        return result;
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param sPath 被删除目录的文件路径
     * @return 目录删除成功返回true， 否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        if (sPath == null) {
            return false;
        }
        // 如果sPath不以文件分隔符结尾 ，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        // 删除目录下的所有文件（包括子目录）
        File[] files = dirFile.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                // 删除子文件
                if (files[i].isFile()) {
                    File file = new File(files[i].getAbsolutePath());
                    flag = file.delete();
                    if (!flag) {
                        break;
                    }
                } // 删除子目录
                else {
                    flag = deleteDirectory(files[i].getAbsolutePath());
                    if (!flag) {
                        break;
                    }
                }
            }
        }
        if (!flag) {
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除临时存储的图片目录及目录下的文件
     */
    public static void deletePicDirectory() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String sPath;
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    sPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                } else {
                    sPath = MyApplication.getContext().getCacheDir().getAbsolutePath();
                }
                sPath = sPath + File.separator + "fusionHome" + File.separator + "Picture";
                deleteDirectory(sPath);

            }
        }).start();
    }

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true， 否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            boolean deleteRes = file.delete();
            if (!deleteRes) {
                return false;
            }
            flag = true;
        }
        return flag;
    }

    /**
     * 邮箱验证
     *
     * @param email
     * @return
     */
    public static boolean emailValidation(String email) {
        if(TextUtils.isEmpty(email)){
            return false;
        }
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
//        String regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return email.matches(regex);
    }
    public static boolean isCorrectPhoneNumber(String mobiles){

        /*
        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
        联通：130、131、132、152、155、156、185、186
        电信：133、153、180、189、（1349卫通）、17*
        总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
        */
        String telRegex = "[1][3456879]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);

    }
    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        /*
        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
        联通：130、131、132、152、155、156、185、186
        电信：133、153、180、189、（1349卫通）、17*
        总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
        */
        //String telRegex = "[1][34587]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "(?:\\(?[0\\+]?\\d{1,3}\\)?)[\\s-]?(?:0|\\d{1,4})[\\s-]?(?:(?:13\\d{9})|(?:\\d{7,8}))";
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    /**
     * 相比isMobileNO方法可以验证座机号码，以及不用验证手机号第2位与服务端对齐
     */
    public static boolean isFormatPhone(String phone) {
        if (isMobileNO(phone)) {
            return true;
        }
//        String telRegex = "((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-" +
//                "(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})))";
        String telRegex = "(?:\\(?[0\\+]\\d{2,3}\\)?)[\\s-]?(?:(?:\\(0{1,3}\\))?[0\\d]{1,4})[\\s-](?:[\\d]{7,8}|[\\d]{3,4}[\\s-][\\d]{3,4})";
        if (TextUtils.isEmpty(phone)) return false;
        else return phone.matches(telRegex);
    }

    /**
     * 密码格式：6-12位，字母或数字组成
     */
    public static boolean isPswValidation(String password) {
        //String regexPsw = "^[a-zA-Z0-9]{6,12}$";
        //return password.matches(regexPsw);
        if ((password.length() > 64) || (password.length() < 6)) {
            return false;
        } else {
            return true;
        }
    }
    /**
     * 密码格式：8-32位，不能为纯大写小写字母或数字
     */
    public static boolean isPswCorrect(String password) {
        if(TextUtils.isEmpty(password)){
            return false;
        }
        if ((password.length() > 32) || (password.length() < 8)) {
            return false;
        }
        if(password.matches("[0-9]+")){
            return false;
        }
        if(password.matches("[a-z]+")){
            return false;
        }
        if(password.matches("[A-Z]+")){
            return false;
        }
        return true;
    }
    /**
     * 密码输入，只能输入字母数字特殊字符
     */
    public static boolean isCorrectSpecialCharacter(char character) {
        if(character == 0){
            return false;
        }
        if(character>='0' && character<='9'){
            return true;
        }
        if(character>='a' && character<='z'){
            return true;
        }
        if(character>='A' && character<='Z'){
            return true;
        }
        for(char charValue:GlobalConstants.specialCharacter.toCharArray()){
            if(charValue == character){
                return true;
            }
        }
        return false;
    }


    /**
     * 调度编号校验
     */
    public static boolean isDispatchNum(String dispatchNum) {
        String regx = "";
        if (TextUtils.isEmpty(dispatchNum)) {
            return false;
        } else {
            return dispatchNum.matches(regx);
        }
    }

    /**
     * 西经
     */
    private static final String WEST = "W";
    /**
     * 东经
     */
    private static final String EAST = "E";
    /**
     * 北纬
     */
    private static final String NORTH = "N";
    /**
     * 南纬
     */
    private static final String SOUTH = "S";


    /**
     * @param num
     * @return 经纬度将小数转换为度分秒
     */
    public static String convertToSexagesimal(double num) {

        int du = (int) Math.floor(Math.abs(num));    //获取整数部分
        double temp = getdPoint(Math.abs(num)) * 60;
        int fen = (int) Math.floor(temp); //获取整数部分
        double tempMiao = getdPoint(temp) * 60;
        int miao = Integer.valueOf(round(tempMiao, 0));
        if (num < 0)
            return "-" + du + "°" + fen + "′" + miao + "″";
        return du + "°" + fen + "′" + miao + "″";

    }

    /**
     * 将double类型的经纬度转换为带方向的字符串格式
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @return 带方向的字符串格式经纬度
     */
    public static String getLocationByDirectionType(double longitude, double latitude) {

        String longitudeStr = "";
        String latitudeStr = "";

        //处理经度
        int du1 = (int) Math.floor(Math.abs(longitude));    //获取整数部分
        double temp1 = getdPoint(Math.abs(longitude)) * 60;
        int fen1 = (int) Math.floor(temp1); //获取整数部分
        double tempMiao1 = getdPoint(temp1) * 60;
        int miao1 = Integer.valueOf(round(tempMiao1, 0));

        if (longitude >= 0.0) {
            longitudeStr = EAST + du1 + "°" + fen1 + "′" + miao1 + "″";
        } else {
            longitudeStr = WEST + du1 + "°" + fen1 + "′" + miao1 + "″";
        }

        //处理纬度
        int du2 = (int) Math.floor(Math.abs(latitude));    //获取整数部分
        double temp2 = getdPoint(Math.abs(latitude)) * 60;
        int fen2 = (int) Math.floor(temp2); //获取整数部分
        double tempMiao2 = getdPoint(temp2) * 60;
        int miao2 = Integer.valueOf(round(tempMiao2, 0));

        if (latitude >= 0.0) {
            latitudeStr = NORTH + du2 + "°" + fen2 + "′" + miao2 + "″";
        } else {
            latitudeStr = SOUTH + du2 + "°" + fen2 + "′" + miao2 + "″";
        }

        return longitudeStr + " , " + latitudeStr;
    }

    /**
     * 将double类型的经纬度转换为带方向的字符串格式
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @return 不带方向的字符串格式经纬度
     */
    public static String getLocationNotDirectionType(double longitude, double latitude) {

        String longitudeStr = "";
        String latitudeStr = "";

        //处理经度
        int du1 = (int) Math.floor(Math.abs(longitude));    //获取整数部分
        double temp1 = getdPoint(Math.abs(longitude)) * 60;
        int fen1 = (int) Math.floor(temp1); //获取整数部分
        double tempMiao1 = getdPoint(temp1) * 60;
        int miao1 = Integer.valueOf(round(tempMiao1, 0));

        longitudeStr = du1 + "°" + fen1 + "′" + miao1 + "″";

        //处理纬度
        int du2 = (int) Math.floor(Math.abs(latitude));    //获取整数部分
        double temp2 = getdPoint(Math.abs(latitude)) * 60;
        int fen2 = (int) Math.floor(temp2); //获取整数部分
        double tempMiao2 = getdPoint(temp2) * 60;
        int miao2 = Integer.valueOf(round(tempMiao2, 0));

        latitudeStr = du2 + "°" + fen2 + "′" + miao2 + "″";

        return longitudeStr + " , " + latitudeStr;
    }

    /**
     * @param num
     * @return 经纬度将小数转换为度
     */
    public static int getLongitudeLatitudeDegree(double num) {

        int du = (int) Math.floor(Math.abs(num));    //获取整数部分
        if (num < 0)
            return -du;

        return du;

    }

    /**
     * @param num
     * @return 经纬度将小数转换为分
     */
    public static int getLongitudeLatitudeBranch(double num) {

        double temp = getdPoint(Math.abs(num)) * 60;
        int fen = (int) Math.floor(temp); //获取整数部分
        return fen;
    }

    /**
     * @param num
     * @return 经纬度将小数转换为秒
     */
    public static int getLongitudeLatitudeSecond(double num) {
        double temp = getdPoint(Math.abs(num)) * 60;
        double tempMiao = getdPoint(temp) * 60;
        int miao = Integer.valueOf(round(tempMiao, 0));
        return miao;
    }

    /**
     * @param
     * @return 经纬度将度分秒转换为小数
     */

    public static double getLongitudeLatitude(int degree, int branch, int second) {
        if (degree < 0) {
            return degree - (branch * 1d) / 60 - (second * 1d) / 3600;
        } else {
            return degree + (branch * 1d) / 60 + (second * 1d) / 3600;
        }
    }


    //获取小数部分
    public static double getdPoint(double num) {
        double d = num;
        int fInt = (int) d;
        BigDecimal b1 = new BigDecimal(Double.toString(d));
        BigDecimal b2 = new BigDecimal(Integer.toString(fInt));
        double dPoint = Double.valueOf(b1.subtract(b2) + "");
        return dPoint;
    }

    //将当前时间戳转化成当天0:0:0的时间戳
    public static long getHandledTime(long sourceTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(sourceTime);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 字符串转换成Iint
     */
    public static int parseInt(String input) {
        if (TextUtils.isEmpty(input)) {
            return 0;
        }
        return Integer.valueOf(input);
    }

    /**
     * app中单位匹配
     *
     * @param jsonUnit
     * @return
     */
    public static String parseUnit(String jsonUnit) {
        JSONObject object = UnitType.getInstance().getObject();
        String unit = "";
        String[] keys = jsonUnit.split("\\.");
        if (keys != null && keys.length > 2) {
            try {
                unit = object.getString(keys[2]);
            } catch (JSONException e) {
                Log.e(TAG, "parseUnit: " + e.getMessage());
            }
        }
        return unit;
    }

    /**
     * 判断字符串是否包含特殊字符
     *
     * @param jsonUnit
     * @return
     */
    public static boolean checkSpecialCharacter(String jsonUnit) {
        if (jsonUnit.contains("null")) {
            return true;
        }
        if (jsonUnit.contains("<")) {
            return true;
        }
        if (jsonUnit.contains("'")) {
            return true;
        }
        if (jsonUnit.contains(">")) {
            return true;
        }
        if (jsonUnit.contains("&")) {
            return true;
        }
        if (jsonUnit.contains("，")) {
            return true;
        }
        if (jsonUnit.contains("\\")) {
            return true;
        }
        if (jsonUnit.contains("/")) {
            return true;
        }
        if (jsonUnit.contains("|")) {
            return true;
        }
        if (jsonUnit.contains("{")) {
            return true;
        }
        if (jsonUnit.contains("}")) {
            return true;
        }
        if (jsonUnit.contains("\"")) {
            return true;
        }
        return false;
    }

    public static String getModuleType(Context context, String moduleTypeID) {
        String moudleType = "";
        switch (moduleTypeID) {
            case Constant.ModuleType.POLYCRYSTAL:
                moudleType = context.getString(R.string.polycrystal);
                break;
            case Constant.ModuleType.MONOCRYSTAL:
                moudleType = context.getString(R.string.monocrystal);
                break;
            case Constant.ModuleType.BLACK_MONOCRYSTAL:
                moudleType = context.getString(R.string.n_monocrystal);
                break;
            case Constant.ModuleType.BLACK_POLYCRYSTAL:
                moudleType = context.getString(R.string.perc_polycrystal);
                break;
            case Constant.ModuleType.DOULE_GLASS_MOUDLE:
                moudleType = context.getString(R.string.single_monocrystal);
                break;
            case Constant.ModuleType.DOULE_GLASS_POLYCRYSTAL:
                moudleType = context.getString(R.string.double_polycrystal);
                break;
            case Constant.ModuleType.DOULE_GLASS_MONOCRYSTAL:
                moudleType = context.getString(R.string.monocrystal_four_grid_60);
                break;
            case Constant.ModuleType.WHITE_DOULE_GLASS_MOUDLE:
                moudleType = context.getString(R.string.monocrystal_four_grid_72);
                break;
            case Constant.ModuleType.TRANSPARENTT_DOULE_GLASS_MOUDLE:
                moudleType = context.getString(R.string.polycrystal_four_grid_60);
                break;
            case Constant.ModuleType.DOULE_GLASS_1500_MOUDLE:
                moudleType = context.getString(R.string.polycrystal_four_grid_72);
                break;
            case Constant.ModuleType.DOULE_GLASS_1501_MOUDLE:
                moudleType = context.getString(R.string.doule_galss_1501_module);
                break;
            case Constant.ModuleType.DOULE_GLASS_1502_MOUDLE:
                moudleType = context.getString(R.string.doule_galss_1502_module);
                break;
            case Constant.ModuleType.DOULE_GLASS_1503_MOUDLE:
                moudleType = context.getString(R.string.doule_galss_1503_module);
                break;
            case Constant.ModuleType.DOULE_GLASS_1504_MOUDLE:
                moudleType = context.getString(R.string.doule_galss_1504_module);
                break;
            case Constant.ModuleType.DOULE_GLASS_1505_MOUDLE:
                moudleType = context.getString(R.string.doule_galss_1505_module);
                break;
            case Constant.ModuleType.DOULE_GLASS_1506_MOUDLE:
                moudleType = context.getString(R.string.doule_galss_1506_module);
                break;
            case Constant.ModuleType.DOULE_GLASS_1507_MOUDLE:
                moudleType = context.getString(R.string.doule_galss_1507_module);
                break;
            case Constant.ModuleType.DOULE_GLASS_1508_MOUDLE:
                moudleType = context.getString(R.string.doule_galss_1508_module);
                break;
            case Constant.ModuleType.DOULE_GLASS_1509_MOUDLE:
                moudleType = context.getString(R.string.doule_galss_1509_module);
                break;
            case Constant.ModuleType.JAP_VER_MONOCRYSTAL:
                moudleType = context.getString(R.string.jap_ver_monocrystal);
                break;
            case Constant.ModuleType.FOUR_WIRE_MONOCRYSTAL:
                moudleType = context.getString(R.string.four_wire_monocrystal);
                break;
            case Constant.ModuleType.MONOCRYSTAL_FOUR_GRID_60:
                moudleType = context.getString(R.string.monocrystal_four_grid_60);
                break;
            case Constant.ModuleType.MONOCRYSTAL_FOUR_GRID_72:
                moudleType = context.getString(R.string.monocrystal_four_grid_72);
                break;
            case Constant.ModuleType.POLYCRYSTAL_FOUR_GRID_60:
                moudleType = context.getString(R.string.polycrystal_four_grid_60);
                break;
            case Constant.ModuleType.POLYCRYSTAL_FOUR_GRID_72:
                moudleType = context.getString(R.string.polycrystal_four_grid_72);
                break;
            case Constant.ModuleType.EFFICIENT_MOUDLE:
                moudleType = context.getString(R.string.efficient_moudle);
                break;
        }
        return moudleType;
    }

    public static boolean isIPAddress(String ipaddr) {
        boolean flag = false;
        Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
        Matcher m = pattern.matcher(ipaddr);
        flag = m.matches();
        return flag;
    }

    //测量Listview的高度
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        if (adapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            //每一项高度
            listItem.measure(0, 0);
            //总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * adapter.getCount());
        listView.setLayoutParams(params);
    }

    //将String转化为List类型
    public static List<String> stringToList(String strs) {
        String str[] = strs.split(",");
        return Arrays.asList(str);
    }


    /**
     * 判断闰年
     *
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {
        return ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0);
    }

    /**
     * 限制输入框小数的位数
     */
    public static InputFilter numberFilter(final int number) {
        InputFilter lengthFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                // source:当前输入的字符
                // start:输入字符的开始位置
                // end:输入字符的结束位置
                // dest：当前已显示的内容
                // dstart:当前光标开始位置
                // dent:当前光标结束位置
                if (dest.length() == 0 && source.toString().equals(".")) {
                    return "0.";
                }
                String dValue = dest.toString();
                String[] splitArray = dValue.split("\\.");
                if (splitArray.length > 1) {
                    String dotValue = splitArray[1];
                    if (dotValue.length() == number) {
                        return "";
                    }
                }
                return null;
            }
        };
        return lengthFilter;
    }

    /**
     * 限制输入框整数位数
     */
    public static InputFilter numberNumFilter(final int number) {
        InputFilter lengthFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                // source:当前输入的字符
                // start:输入字符的开始位置
                // end:输入字符的结束位置
                // dest：当前已显示的内容
                // dstart:当前光标开始位置
                // dent:当前光标结束位置
                if (dest.length() == 0 && source.toString().equals(".")) {//限制第一位输入.
                    return "";
                }
                String dValue = dest.toString();
                if (dValue.length() > 0) {
                    if (dValue.length() == number) {
                        return "";
                    }
                }
                return null;
            }
        };
        return lengthFilter;
    }

    /**
     * 限制输入框整数时第一位输入0
     */
    public static InputFilter numberZeroFilter() {
        InputFilter lengthFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                // source:当前输入的字符
                // start:输入字符的开始位置
                // end:输入字符的结束位置
                // dest：当前已显示的内容
                // dstart:当前光标开始位置
                // dent:当前光标结束位置
                if (dest.length() == 0 && source.toString().equals("0")) {
                    return "";
                }
                return null;
            }
        };
        return lengthFilter;
    }

    /**
     * @param s
     * @param s1
     * @return 删除String中的某一部分
     */
    public static List<String> getNewStringToList(String s, String s1)//s是需要删除某个子串的字符串,s1是需要删除的子串
    {
        int postion = s.indexOf(s1);
        int length = s1.length();
        int Length = s.length();
        List<String> newList = new ArrayList<>();
        newList.add(s.substring(0, postion));
        newList.add(s.substring(postion + length, Length));
        return newList;
    }

    /**
     * @param s
     * @param s1
     * @return 删除String中的某一部分
     */
    public static String getOneString(String s, String s1)//s是需要截取某个子串的字符串,s1是需要截取子串的开始端
    {
        int postion = s.indexOf(s1);
        String newString = s.substring(postion, s.length());
        return newString;//返回已经的字符串
    }

    /**
     * @param s
     * @param s1
     * @return 截取String中的某一部分
     */
    public static String getSomeString(String s, String s1, String s2)//s是需要截取某个子串的字符串,s1开始端的字符,s2结束端的字符
    {
        int beginIndex = s.indexOf(s1)+s1.length();
        int endIndex = s.lastIndexOf(s2);
        return s.substring(beginIndex, endIndex);
    }
    /**
     * @param s
     * @param s1
     * @return
     *截取String中的固定长度某一部分
     */
    public static String getLenghtString(String s, String s1,int lenght)//s是需要截取某个子串的字符串,s1开始端的字符,s2结束端的字符
    {
        int beginIndex = s.indexOf(s1)+s1.length();
        return s.substring(beginIndex,beginIndex + lenght);
    }
    /**
     * @param data
     * @param max
     * @param min
     * @param maxContain
     * @param minContain
     * @param editText1
     * @param isInt      是否为整数
     * @return check参数范围
     */
    public static boolean showCheckQuest(Double data, Double max, Double min, boolean maxContain, boolean minContain, EditText editText1, boolean isInt) {
        if (isInt) {
            String sMin = min + "";
            String sMax = max + "";
            String[] splitMin = sMin.split("\\.");
            String[] splitMax = sMax.split("\\.");
            if (maxContain && minContain) {
                if (data < min || data > max) {
                    editText1.setError(String.format(MyApplication.getContext().getResources().getString(R.string.parameter_range_2), splitMin[0], splitMax[0]));
                    return false;
                }
            } else if (maxContain && !minContain) {
                if (data < min || data > max || data == min) {
                    editText1.setError(String.format(MyApplication.getContext().getResources().getString(R.string.parameter_range_min_2), splitMin[0], splitMax[0]));
                    return false;
                }
            } else if (!maxContain && minContain) {
                if (data < min || data > max || data == max) {
                    editText1.setError(String.format(MyApplication.getContext().getResources().getString(R.string.parameter_range_max_2), splitMin[0], splitMax[0]));
                    return false;
                }
            } else {
                if (data < min || data > max || data == max || data == min) {
                    editText1.setError(String.format(MyApplication.getContext().getResources().getString(R.string.parameter_range_maxmin_2), splitMin[0], splitMax[0]));
                    return false;
                }
            }
        } else {
            String roundMax = Utils.round(max, 3);
            String roundMin = Utils.round(min, 3);
            if (maxContain && minContain) {
                if (data < min || data > max) {
                    editText1.setError(String.format(MyApplication.getContext().getResources().getString(R.string.parameter_range_1), roundMin, roundMax));
                    return false;
                }
            } else if (maxContain && !minContain) {
                if (data < min || data > max || data == min) {
                    editText1.setError(String.format(MyApplication.getContext().getResources().getString(R.string.parameter_range_4), roundMin, roundMax));
                    return false;
                }
            } else if (!maxContain && minContain) {
                if (data < min || data > max || data == max) {
                    editText1.setError(String.format(MyApplication.getContext().getResources().getString(R.string.parameter_range_max_1), roundMin, roundMax));
                    return false;
                }
            } else {
                if (data < min || data > max || data.equals(max) || data.equals(min)) {
                    editText1.setError(String.format(MyApplication.getContext().getResources().getString(R.string.parameter_range_maxmin_1), roundMin, roundMax));
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 获取本地软件版本号名称
     */
    public static String getLocalVersionName(Context ctx) {
        String localVersion;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "1.0.0";
        }
        return localVersion;
    }

    // 获取当前APP名称
    public static String getAppName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo;
        try {
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (Exception e) {
            return "";
        }
        return String.valueOf(packageManager.getApplicationLabel(applicationInfo));
    }

    private LoadingDialog loadingDialog;

    public void showLoading(Context context) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(context);
            loadingDialog.show();
        } else if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    public void dismissLoading() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }

    /**
     * @return 禁止表情输入
     */
    public static InputFilter getEmojiFilter() {
        InputFilter emojiFilter = new InputFilter() {
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                    Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    ToastUtil.showMessage(MyApplication.getContext().getString(R.string.not_input_emoji));
                    return "";
                }
                return null;
            }
        };
        return emojiFilter;
    }


    /**
     * 禁止EditText输入空格
     */
    public static InputFilter getSpaceFilter() {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.equals(" ")) {
                    return "";
                } else {
                    return null;
                }
            }
        };

        return filter;
    }

    public static InputFilter getLonLatFilter(){
        InputFilter inputFilter=new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source.toString().equals("-")&&dest.length()!=0){
                    return "";
                }
                if (source.toString().equals(".")){
                    if (dest.toString().contains(".")||dest.length()==0){
                        return "";
                    }
                }

                return null;
            }
        };
        return inputFilter;
    }

    /**
     * 校验文件路径，解决codex的  Path Manipulation 问题
     *
     * @param oldPath
     * @return
     */
    public static String getSecureFilePath(String oldPath) {
        if (TextUtils.isEmpty(oldPath)) {
            return "";
        }

        String newPath = "";
        for (int i = 0; i < oldPath.length(); i++) {
            if (map.get(oldPath.charAt(i) + "") != null) {
                newPath += map.get(oldPath.charAt(i) + "");
            }
        }
        return newPath;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        try {
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            long temp = time2 - time1;
            if (temp < 0) {
                return 31;
            }
            long between_days = (temp) / (1000 * 3600 * 24);
            return Integer.parseInt(String.valueOf(between_days));
        } catch (ParseException e) {
            return 1;
        }
    }

    public static void logout(int failCode, final JSONObject jsonObject) {
        //返回错误
        switch (failCode) {
            case 305:
            case 306:
                //及时清除敏感数据
                Utils.clearData();
                MyApplication.getOkHttpClient().dispatcher().cancelAll();
                MyApplication.reLogin(MyApplication.getContext().getResources().getString(R.string.long_time_no_login));
                return;
            case 307:
                //及时清除敏感数据
                Utils.clearData();
                MyApplication.getOkHttpClient().dispatcher().cancelAll();
                MyApplication.reLogin(MyApplication.getContext().getResources().getString(R.string.other_device_login));
                return;
            case 405:
                MyApplication.is405=true;
                //及时清除敏感数据
                Utils.clearData();
                MyApplication.getOkHttpClient().dispatcher().cancelAll();
                MyApplication.reLogin(MyApplication.getContext().getResources().getString(R.string.infomation_changed));
                return;
            case 10042:
                //及时清除敏感数据
                Utils.clearData();
                MyApplication.getOkHttpClient().dispatcher().cancelAll();
                MyApplication.reLogin(MyApplication.getContext().getResources().getString(R.string.error_pwd_in_short_times));
                return;
            case 444:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MyApplication.getApplication().getCurrentActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    DialogUtil.showErrorMsg(MyApplication.getApplication().getCurrentActivity(), jsonObject.getString("data"));
                                } catch (JSONException e) {
                                    Log.e(TAG, "run: " + e.getMessage());
                                }
                            }
                        });
                    }
                }).start();
                break;
            default:
                break;
        }
    }

    /**
     * 判断设备是否有Google服务
     *
     * @param context
     * @return
     */
    public static boolean isGooglePlayServiceAvailable(Context context) {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        return status == ConnectionResult.SUCCESS;
    }

    /**
     * 判断设备是否安装有高德地图
     *
     * @param context
     * @return
     */
    public static boolean isInstalledAMap(Context context) {
        String packageName = "com.autonavi.minimap";
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 及时清除敏感信息
     */
    public static void clearData() {
        LocalData.getInstance().setAutomaticLogin(false);
        LocalData.getInstance().setLoginName("");
        LocalData.getInstance().setLoginPsw("");
        LocalData.getInstance().setLat("");
        LocalData.getInstance().setLon("");
        LocalData.getInstance().setIsShowGuide(false);
        GlobalConstants.userId = Integer.MIN_VALUE;
    }


    /**
     * 判断某个界面是否在前台
     *
     * @param context   Context
     * @param className 界面的类名
     * @return 是否在前台显示
     */
    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className))
            return false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName()))
                return true;
        }
        return false;
    }

    /**
     * 计算天数差
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static int diffTime(String startTime, String endTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);
            Calendar fromCalendar = Calendar.getInstance();
            fromCalendar.setTime(startDate);
            fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
            fromCalendar.set(Calendar.MINUTE, 0);
            fromCalendar.set(Calendar.SECOND, 0);
            fromCalendar.set(Calendar.MILLISECOND, 0);

            Calendar toCalendar = Calendar.getInstance();
            toCalendar.setTime(endDate);
            toCalendar.set(Calendar.HOUR_OF_DAY, 0);
            toCalendar.set(Calendar.MINUTE, 0);
            toCalendar.set(Calendar.SECOND, 0);
            toCalendar.set(Calendar.MILLISECOND, 0);
            return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
        } catch (ParseException e) {
            Log.e(TAG, "diffTime: " + e.getMessage());
        }
        return -1;
    }

    public static boolean checkInPutIsOk(EditText editText, String intro, int max, String tips) {
        String toString = editText.getText().toString();
        if (TextUtils.isEmpty(toString)) {
            ToastUtil.showMessage(intro);
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
            editText.findFocus();
            return true;
        } else if (toString.length() > max) {
            editText.setError(tips);
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
            editText.findFocus();
            return true;
        }
        return false;
    }
        /**
     * 判断下挂设备名称是否重复，不包括数采、户用逆变器、品联数采
     * @param deviceData
     * @return
     */
    public static boolean deviceNameIsSame(List<DeviceData> deviceData){

        if(deviceData == null || deviceData.size()==0){
            return false;
        }
        for(DeviceData data:deviceData){
            if(data.getSubDevs() == null || data.getSubDevs().length <2){ //设备至少两个比较才有意义
                continue;
            }else{
                SubDev[] subDevs = data.getSubDevs();
                for(int i=0;i<subDevs.length-1;i++){
                    if (subDevs[i].getDevTypeId().equals(DevTypeConstant.SMART_LOGGER_TYPE)||(subDevs[i].getDevTypeId().equals(DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE))||(subDevs[i].getDevTypeId().equals(DevTypeConstant.PINNET_DC))){
                        continue;
                    }
                    for(int j=i+1;j<subDevs.length;j++){
                        if (subDevs[j].getDevTypeId().equals(DevTypeConstant.SMART_LOGGER_TYPE)||(subDevs[j].getDevTypeId().equals(DevTypeConstant.HOUSEHOLD_INVERTER_DEV_TYPE))||(subDevs[j].getDevTypeId().equals(DevTypeConstant.PINNET_DC))){
                            continue;
                        }
                        if(subDevs[i].getBusiName() != null && subDevs[j].getBusiName() != null){
                            if(subDevs[i].getBusiName().equals(subDevs[j].getBusiName())){
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    public static boolean checkInPutIsOk(EditText editText, int max, String tips) {
        String toString = editText.getText().toString();
        if (TextUtils.isEmpty(toString)) {
            return false;
        }
        if (toString.length() > max) {
            editText.setError(tips);
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
            editText.findFocus();
            return true;
        }
        return false;
    }

    /**
     * 拨打电话
     */
    public static void callPhone(Activity context, String phone) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        //url:统一资源定位符
        //uri:统一资源标示符（更广）
        intent.setData(Uri.parse("tel:" + phone));
        //开启系统拨号器
        context.startActivity(intent);
    }

    /**
     * @return
     * 夏令时的时间戳
     */
    public static long summerTime(long data){
        long curtime = 0;
        try {
            Calendar curCalendar = Calendar.getInstance();
            curCalendar.setTimeInMillis(data);
            // 以毫秒为单位指示夏令时的偏移量。
            int curDstOffest= curCalendar.get(Calendar.DST_OFFSET);
            //经过加减最终得到了一个准确的时间
            curtime = curCalendar.getTimeInMillis() + curDstOffest;

        } catch (Exception e) {
            Log.e("summerTime",e.toString());
        }
        return curtime;
    }
}
