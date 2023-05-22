package com.talkeasy.server.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorResponse {

    private int status;
//    private String message;
    private Object data;

    public ErrorResponse(Object data) {
        this.data = data;
    }
    public ErrorResponse(HttpStatus httpStatus, Object data) {
        this.status = httpStatus.value();
        this.data = data;
    }

}
