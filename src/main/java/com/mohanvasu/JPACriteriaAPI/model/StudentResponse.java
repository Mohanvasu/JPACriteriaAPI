package com.mohanvasu.JPACriteriaAPI.model;

import com.mohanvasu.JPACriteriaAPI.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class StudentResponse {
    private List<Student> studentList;
    private Integer allStudentListCount;
}
