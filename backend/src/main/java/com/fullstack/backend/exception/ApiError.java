package com.fullstack.backend.exception;

import org.springframework.http.HttpStatus;

public class ApiError extends RuntimeException {

    private String[] params;
    private HttpStatus httpStatus;

    public ApiError(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ApiError(String message, HttpStatus httpStatus, String... params) {
        super(message);
        this.httpStatus = httpStatus;
        this.params = params;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
