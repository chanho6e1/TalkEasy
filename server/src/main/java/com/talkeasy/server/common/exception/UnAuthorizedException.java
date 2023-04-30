package com.talkeasy.server.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnAuthorizedException extends RuntimeException {
    private ErrorResponse errorResponse;

    public UnAuthorizedException() {
        super();
        setErrResponse("로그인 해주세요.");
    }

    public UnAuthorizedException(String message) {
        super();
        setErrResponse(message);
    }

    public ErrorResponse getErrResponse() {
        return errorResponse;
    }

    private void setErrResponse(String message) {
        errorResponse = new ErrorResponse(message);
    }
}