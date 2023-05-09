package com.talkeasy.server.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private ErrorResponse errorResponse;

    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super("정보를 찾을 수 없습니다");
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        setErrResponse();
    }

    public ResourceNotFoundException(String data) {
        super(data);
        setErrResponse(data);
    }

    public ErrorResponse getErrResponse() {
        return errorResponse;
    }

    private void setErrResponse() {
        String message = String.format("%s가 '%s' 인 %s 가 존재하지 않습니다.", fieldName, fieldValue, resourceName);

        errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, message);
    }

    private void setErrResponse(String data) {
        errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, data);
    }


}