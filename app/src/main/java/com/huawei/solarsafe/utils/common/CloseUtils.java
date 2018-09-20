package com.huawei.solarsafe.utils.common;

import java.io.Closeable;
import java.io.IOException;

/**
 * <pre>
 *     author: Tzy
 *     time  : 2018/3/30
 *     desc  : 关闭相关工具类
 * </pre>
 */

//        closeIO       : 关闭IO
//        closeIOQuietly: 安静关闭IO

public final class CloseUtils {

    private CloseUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 关闭IO
     *
     * @param closeables closeables
     */
    public static void closeIO(Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 安静关闭IO
     *
     * @param closeables closeables
     */
    public static void closeIOQuietly(Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
