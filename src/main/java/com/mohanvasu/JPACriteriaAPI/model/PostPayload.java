package com.mohanvasu.JPACriteriaAPI.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
@Builder
public class PostPayload {
    private List<Sort> sort;
    @Valid
    @NotNull
    private Filter filter;
    private Page page;
}
