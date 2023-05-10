package com.talkeasy.server.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnAuthorizedException extends RuntimeException {
    private ErrorResponse errorResponse;

    public UnAuthorizedException() {
        super("로그인 해주세요.");
        setErrResponse("로그인 해주세요.");
    }

    public UnAuthorizedException(Object data) {
        super();
        setErrResponse(data);
    }

    public ErrorResponse getErrResponse() {
        return errorResponse;
    }

    private void setErrResponse(Object data) {
        errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, data);
    }
}