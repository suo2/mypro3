package com.pinnet.energy.model.http.exception;

/**
 * @author P00701
 * @date 2018/8/31
 */
public class EmptyException extends Exception {

    private int code;

    public EmptyException(String msg) {
        super(msg);
    }

    public EmptyException(String msg, int code) {
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
