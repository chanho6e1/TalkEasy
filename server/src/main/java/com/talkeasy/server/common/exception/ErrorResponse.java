package com.talkeasy.server.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorResponse {

    private String message;
    private Object data;

    public ErrorResponse(String message) {
        this.message = message;
    }

}
