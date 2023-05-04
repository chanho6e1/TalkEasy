package com.talkeasy.server.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@Getter
@NoArgsConstructor
//@AllArgsConstructor
public class CommonResponse<T> {

    private int status;
    private T data;

    public CommonResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    public CommonResponse(T data) {
        this.data = data;
    }


    public static <T> CommonResponse<T> of(HttpStatus httpStatus, T data) {
        int status = Optional.ofNullable(httpStatus)
                .orElse(HttpStatus.OK)
                .value();
        return new CommonResponse<>(status, data);
    }

    public static CommonResponse<?> fail(HttpStatus httpStatus) {
        return new CommonResponse<>(httpStatus.value(), null);
    }


}