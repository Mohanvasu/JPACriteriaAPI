package com.mohanvasu.JPACriteriaAPI.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class PageInfo {
    private int page;
    private int pageSize;
    private int totalPages;
    private long totalElements;
}
