package com.sns.api.exception;

import lombok.Getter;

@Getter
public class SnsApplicationException extends RuntimeException{

    private ErrorCode errorCode;
    private String message;

    public SnsApplicationException(ErrorCode errorCode,String message) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public SnsApplicationException(ErrorCode errorCode) {
        this.message = null;
        this.errorCode = errorCode;
    }



    @Override
    public String getMessage() {
        if (message == null) {
            return errorCode.getMessage();
        } else {
            return String.format("%s. %s", errorCode.getMessage(), message);
        }

    }
}
