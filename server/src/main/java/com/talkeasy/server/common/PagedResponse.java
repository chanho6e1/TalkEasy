package com.talkeasy.server.common;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PagedResponse<T> {
    private Object content; //콘텐츠
    private int page; //현 페이지
    private int size; //각 페이지의 콘텐츠 수
    private long totalElements; //총 콘텐츠 수
    private int totalPages; //총 페이지 수
    private boolean last; //마지막인지 여부

    public PagedResponse(Object content, int page, int size, long totalElements, int totalPages, boolean last) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }
}