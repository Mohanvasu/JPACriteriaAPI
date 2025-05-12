package com.mohanvasu.JPACriteriaAPI.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Sort {
    private String field;
    private Direction direction;
}
