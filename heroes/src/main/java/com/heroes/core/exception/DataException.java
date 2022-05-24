package com.heroes.core.exception;

public class DataException extends Exception {

    private static final long serialVersionUID = 1L;

    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED_ACCESS = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int SERVER_ERROR = 500;

    private final int code;
    private String status;
    private boolean showStackTrace = true;

    public DataException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public DataException(int code, String message) {
        super(message);
        this.code = code;
    }

    public DataException(int code, String message, boolean showStackTrace) {
        super(message);
        this.code = code;
        this.showStackTrace = showStackTrace;
    }

    public DataException(int code, String message, String status, boolean showStackTrace) {
        super(message);
        this.code = code;
        this.status = status;
        this.showStackTrace = showStackTrace;
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public boolean isShowStackTrace() {
        return showStackTrace;
    }

}
