package com.talkeasy.server.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T>{

    private String message;
    private T data;

    public CommonResponse(String message) {
        this(message, null);
    }

    public CommonResponse(T data) {
        this(null, data);
    }

//    public CommonResponse(String message, T data) {
//        this.message = message;
//        this.data = data;
//    }

    public static <T> CommonResponse<T> of(String message, T data) {
        return new CommonResponse<>(message, data);
    }

    public static CommonResponse<Object> of(String message) {
        return new CommonResponse<>(message);
    }

    public static CommonResponse<Object> fail(String message) {
        return new CommonResponse<>(message, new Object());
    }


}
