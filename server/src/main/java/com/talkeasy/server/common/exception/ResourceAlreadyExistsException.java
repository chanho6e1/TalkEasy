package com.talkeasy.server.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ResourceAlreadyExistsException extends RuntimeException {
    private ErrorResponse errorResponse;

    public ResourceAlreadyExistsException(Object data) {
        super();
        setErrResponse(data);
    }

    public ErrorResponse getErrResponse() {
        return errorResponse;
    }

    private void setErrResponse(Object data) {
        errorResponse = new ErrorResponse(HttpStatus.CONFLICT, data);
    }


}