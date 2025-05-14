package com.mohanvasu.JPACriteriaAPI.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Filter {
    @NotNull
    private Operator operator;
    @Valid
    @NotEmpty
    List<Operand> operands;
}
