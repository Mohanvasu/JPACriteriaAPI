package com.mohanvasu.JPACriteriaAPI.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Operand {
    private String field;
    private Operator operator;
    private String value;
}
