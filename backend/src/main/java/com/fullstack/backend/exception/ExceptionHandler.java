package com.fullstack.backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Date;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ApiError.class)
    public final ResponseEntity<ApiErrorResponse> handleException(ApiError apiError) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setTimestamp(new Date());
        apiErrorResponse.setErrorCode(apiError.getMessage());
        apiErrorResponse.setHttpStatus(apiError.getHttpStatus());
        apiErrorResponse.setParams(apiError.getParams());
        return new ResponseEntity<>(apiErrorResponse, apiError.getHttpStatus());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ApiErrorResponse> springValidationHandler(RuntimeException ex) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setTimestamp(new Date());
        apiErrorResponse.setErrorCode(ExceptionEnum.BAD_REQUEST.errorCode);
        apiErrorResponse.setHttpStatus(ExceptionEnum.BAD_REQUEST.getHttpStatus());

        String[] params = {ex.getMessage()};
        apiErrorResponse.setParams(params);

        return new ResponseEntity<>(apiErrorResponse, ExceptionEnum.BAD_REQUEST.getHttpStatus());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public final ResponseEntity<ApiErrorResponse> handleAllExceptions(Exception ex) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setTimestamp(new Date());
        apiErrorResponse.setErrorCode(ExceptionEnum.INTERNAL_SERVER_ERROR.errorCode);
        apiErrorResponse.setHttpStatus(ExceptionEnum.INTERNAL_SERVER_ERROR.getHttpStatus());

        String[] params = {ex.getMessage()};
        apiErrorResponse.setParams(params);

        return new ResponseEntity<>(apiErrorResponse, ExceptionEnum.INTERNAL_SERVER_ERROR.getHttpStatus());
    }
}
