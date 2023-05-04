package com.talkeasy.server.common.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
//잘못된 인자 예외
@ResponseStatus(HttpStatus.BAD_REQUEST)
@NoArgsConstructor
public class ArgumentMismatchException extends RuntimeException  {

    private ErrorResponse errorResponse;

    public ArgumentMismatchException(Object data) {
        super();
        setErrResponse(data);
    }

    public ErrorResponse getErrResponse() {
        return errorResponse;
    }


     private void setErrResponse(Object data) {
        errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, data);
    }

}
