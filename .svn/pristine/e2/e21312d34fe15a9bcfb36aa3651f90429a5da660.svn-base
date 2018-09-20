package com.huawei.solarsafe.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.text.TextUtils;

import com.huawei.solarsafe.R;
import com.huawei.solarsafe.view.MainActivity;
import com.huawei.solarsafe.view.maintaince.todotasks.TodoTaskActivity;
import com.huawei.solarsafe.view.personal.DeviceUpdateDetailActivity;
import com.huawei.solarsafe.view.personal.InforMationActivity;

import java.security.SecureRandom;
import java.util.List;

import static android.R.attr.key;

/**
 * This class is to notify the user of messages with NotificationManager.
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class Notifier {
    public static final String TAG = "Notifier";

    private Context context;
    private String keyId;
    private NotificationManager notificationManager;
    private String id ="funsolarsafe";
    private String ChannelName = "funsolarsafe_name";
    private int importance =NotificationManager.IMPORTANCE_HIGH;
    public Notifier(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void notifyMsg(String msg, int msgType) {
        Intent intent;
        if (msgType == 3) {
            intent = new Intent(context, InforMationActivity.class);
        } else {
            intent = new Intent(context, TodoTaskActivity.class);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, getKey(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        int defaults = Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND;
        Notification.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            builder = new Notification.Builder(context,id);
        }else{
            builder = new Notification.Builder(context);
        }
        builder.setContentIntent(pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            builder.setSmallIcon(R.mipmap.fusionhome_fillet);
            builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.fusionhome_fillet));
        } else {
            builder.setSmallIcon(R.mipmap.fusionhome_fillet);
        }
        builder.setContentTitle(context.getString(R.string.get_one_new_message));
        builder.setWhen(System.currentTimeMillis());
        builder.setDefaults(defaults);
        builder.setAutoCancel(true);
        builder.setTicker(context.getString(R.string.get_one_new_message));
        builder.setContentText(msg);
        Notification notification = builder.build();
        int key = new SecureRandom().nextInt(10);
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(id,ChannelName,importance);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(Color.RED);
            channel.setShowBadge(true);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(key, notification);
    }

    /**
     * 接受到升级推送，点击进入设备升级通知详情界面
     *
     * @param msg
     * @param keyId
     */
    public void notifyMsgForUpdate(String msg, long keyId) {
        int key = new SecureRandom().nextInt(10);
        Intent intent = new Intent(context, DeviceUpdateDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("keyId", keyId);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, key, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        initBuiler(msg, pendingIntent);
    }

    /**
     * @param msg
     * @param pendingIntent 通知栏的初始化
     */
    private void initBuiler(String msg, PendingIntent pendingIntent) {
        int defaults = Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND;
        Notification.Builder builder = null;
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            builder = new Notification.Builder(context,id);
        }else{
            builder = new Notification.Builder(context);
        }
        builder.setContentIntent(pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            builder.setSmallIcon(R.mipmap.fusionhome_fillet);
            builder.setLargeIcon(Icon.createWithResource(context, R.mipmap.fusionhome_fillet));
        } else {
            builder.setSmallIcon(R.mipmap.fusionhome_fillet);
        }
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(id,ChannelName,importance);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(Color.RED);
            channel.setShowBadge(true);
            notificationManager.createNotificationChannel(channel);
        }
        builder.setContentTitle(context.getString(R.string.get_one_new_message));
        builder.setWhen(System.currentTimeMillis());
        builder.setDefaults(defaults);
        builder.setAutoCancel(true);
        builder.setTicker(context.getString(R.string.get_one_new_message));
        builder.setContentText(msg);
        Notification noti = builder.build();
        notificationManager.notify(key, noti);
    }


    private int getKey() {
        int result = 0;
        if (!TextUtils.isEmpty(keyId)) {
            int length = keyId.length() - 1;
            int count = 0;
            for (int i = length; i > length - 3; i--) {
                char temp = keyId.charAt(i);
                count += temp * temp;
            }
            result = count;
        }
        return result;
    }

    /**
     * @param messageStr
     * @param keyId      告警推送
     */
    public void notifyMsgForDevAlarm(String messageStr, long keyId) {
        //有告警权限才能点击进入告警推送
        String strRights = LocalData.getInstance().getRightString();
        List<String> rightsList = Utils.stringToList(strRights);
        for (String right : rightsList){
            if ("app_equipmentAlarm".equals(right)) {
                int key = new SecureRandom().nextInt(10);
                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("keyId", keyId + "");
                PendingIntent pendingIntent = PendingIntent.getActivity(context, key, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                initBuiler(messageStr, pendingIntent);
                break;
            }
        }
    }
}
