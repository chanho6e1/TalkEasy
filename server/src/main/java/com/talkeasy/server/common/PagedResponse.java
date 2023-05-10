package com.talkeasy.server.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
public class PagedResponse<T> {
    private int status;
    private Object data; //콘텐츠
//    private List<T> data; //콘텐츠

    //    private int page; //현 페이지
//    private int size; //각 페이지의 콘텐츠 수
//    private long totalElements; //총 콘텐츠 수
    private int totalPages; //총 페이지 수
//    private boolean last; //마지막인지 여부


//    public PagedResponse(HttpStatus httpStatus, List<T> data, int totalPages) {
    public PagedResponse(HttpStatus httpStatus, Object data, int totalPages) {
        int status = Optional.ofNullable(httpStatus)
                .orElse(HttpStatus.OK)
                .value();
        this.status = status;
        this.data = data;
        this.totalPages = totalPages;
    }

}