package com.mohanvasu.JPACriteriaAPI.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Operand {
    @NotBlank
    private String field;
    private Operator operator;
    @NotBlank
    private String value;
}
