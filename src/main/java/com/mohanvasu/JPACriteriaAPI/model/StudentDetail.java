package com.mohanvasu.JPACriteriaAPI.model;
import lombok.Builder;

@Builder
public record StudentDetail(String firstName, String lastName, String email) {

}
