package com.mohanvasu.JPACriteriaAPI.service;

import com.mohanvasu.JPACriteriaAPI.entity.Student;

import java.util.List;

public interface CriteriaService {
    Student getStudentById(Long id);
    List<Student> getAllStudents();
}
