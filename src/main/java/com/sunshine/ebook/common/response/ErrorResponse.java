package com.sunshine.ebook.common.response;

/**
 * Created by LMG on 2017/3/10.
 */
public class ErrorResponse {

    public static final int UNKNOWN = 0;
    private final int code;
    private final String reason;

    public ErrorResponse() {
        this(UNKNOWN, "Unknown");
    }

    public ErrorResponse(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public int getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }

}
