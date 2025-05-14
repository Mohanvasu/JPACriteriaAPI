package com.mohanvasu.JPACriteriaAPI.service;

import com.mohanvasu.JPACriteriaAPI.entity.Student;
import com.mohanvasu.JPACriteriaAPI.model.PostPayload;
import jakarta.validation.Valid;

import java.util.List;

public interface CriteriaService {
    Student getStudentById(Long id);
    List<Student> getAllStudents();
    List<Student> fetchStudentDetails(PostPayload postPayload);

    void addStudent(@Valid Student student);
}
