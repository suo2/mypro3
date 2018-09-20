package com.pinnet.energy.model.http.exception;

/**
 * @author P00701
 * @date 2018/8/31
 */

public class MostLoginException extends Exception {
    private int code;

    public MostLoginException(String msg) {
        super(msg);
    }

    public MostLoginException(String msg, int code) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
