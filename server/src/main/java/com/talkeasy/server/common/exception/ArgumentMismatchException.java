package com.talkeasy.server.common.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
//잘못된 인자 예외
@ResponseStatus(HttpStatus.BAD_REQUEST)
@NoArgsConstructor
public class ArgumentMismatchException extends RuntimeException  {

    private ErrorResponse errorResponse;

    public ArgumentMismatchException(String message,  Object data) {
        super();
        setErrResponse(message, data);
    }

    public ArgumentMismatchException(String message) {
        super();
        setErrResponse(message);
    }

    public ErrorResponse getErrResponse() {
        return errorResponse;
    }

    private void setErrResponse(String message) {
        errorResponse = new ErrorResponse(message);
    }

     private void setErrResponse(String message, Object data) {
        errorResponse = new ErrorResponse(message, data);
    }

}
