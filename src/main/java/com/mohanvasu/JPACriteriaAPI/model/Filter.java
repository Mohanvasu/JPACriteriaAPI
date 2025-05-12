package com.mohanvasu.JPACriteriaAPI.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Filter {
    private Operator operator;
    List<Operand> operands;
}
