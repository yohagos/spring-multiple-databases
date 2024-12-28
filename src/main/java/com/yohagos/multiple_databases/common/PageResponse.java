package com.yohagos.multiple_databases.common;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {
    private List<T> content;
    private int pageNumber;
    private int sizeOfEachPage;
    private long totalElement;
    private int totalPage;
    private boolean firstPage;
    private boolean lastPage;
}
