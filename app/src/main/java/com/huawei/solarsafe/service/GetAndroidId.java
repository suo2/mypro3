package com.huawei.solarsafe.service;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

/**
 * Created by p00322 on 2017/2/17.
 */
public class GetAndroidId {
    private static String sID = null;
    private static final String INSTALLATION = "INSTALLATION";

    public synchronized static String id(Context context) {
        if (sID == null) {
            File installation = new File(context.getFilesDir(), INSTALLATION);
            try {
                if (!installation.exists())
                    writeInstallationFile(installation);
                sID = readInstallationFile(installation);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return sID + Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile f = null;
        byte[] bytes = null;
        try {
            f = new RandomAccessFile(installation, "r");
            bytes = new byte[(int) f.length()];
            f.readFully(bytes);
        } catch (Exception e) {
            Log.e("GetAndroidId",e.getMessage());
        } finally {
            if (f != null) {
                f.close();
            }
        }
        if(bytes == null){
            return "";
        }
        return new String(bytes);
    }

    private static void writeInstallationFile(File installation) throws IOException {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(installation);
            String id = UUID.randomUUID().toString();
            out.write(id.getBytes());
        } catch (Exception e) {
            Log.e("writeInstallationFile",e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
