package com.fullstack.backend.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionEnum {

    INTERNAL_SERVER_ERROR("API_001", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST("API_002", HttpStatus.BAD_REQUEST),
    NOT_FOUND("API_003", HttpStatus.NOT_FOUND),
    CONFLICT("API_004", HttpStatus.CONFLICT);


    ExceptionEnum(String errorCode, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    String errorCode;
    HttpStatus httpStatus;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
