package com.talkeasy.server.common;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SlicedResponse<T> {
    private Object content; //콘텐츠
    private int page; //현 페이지(없어도 될거 같음)
    private int size; //각 페이지의 콘텐츠 수
    private boolean first; //마지막인지 여부
    private boolean last; //마지막인지 여부
    private boolean hasNext; //다음 컨텐츠가 있는가


    public SlicedResponse(Object content, int page, int size, boolean first, boolean last, boolean hasNext) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.first = first;
        this.last = last;
        this.hasNext = hasNext;
    }
}