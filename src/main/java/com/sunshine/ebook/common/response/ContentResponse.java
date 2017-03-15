package com.sunshine.ebook.common.response;

/**
 * Created by LMG on 2017/3/10.
 */
public class ContentResponse {

    public static final int UNKNOWN = 0;
    private final int code;
    private final String reason;

    public ContentResponse() {
        this(UNKNOWN, "Unknown");
    }

    public ContentResponse(int code, String reason) {
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
