package com.ivag.exception;

public class InnerCategoryException extends Exception {
    public InnerCategoryException() {
    }

    public InnerCategoryException(String message) {
        super(message);
    }

    public InnerCategoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public InnerCategoryException(Throwable cause) {
        super(cause);
    }

    public InnerCategoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
