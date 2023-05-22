package com.talkeasy.server.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//접근권한이 없을때 예외
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ResourceForbiddenException extends RuntimeException {
    private ErrorResponse errorResponse;

        public ResourceForbiddenException(String data) {
        super(data);
        setErrResponse(data);
    }

    public ErrorResponse getErrResponse() {
        return errorResponse;
    }

    private void setErrResponse(String data) {
        errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN, data);
    }
}
