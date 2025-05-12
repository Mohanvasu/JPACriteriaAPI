package com.mohanvasu.JPACriteriaAPI.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Page {
    private int offset;
    private int limit;
}
