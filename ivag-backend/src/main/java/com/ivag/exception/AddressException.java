package com.ivag.exception;

public class AddressException extends Exception {
    public AddressException() {
    }

    public AddressException(String message) {
        super(message);
    }

    public AddressException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddressException(Throwable cause) {
        super(cause);
    }

    public AddressException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
