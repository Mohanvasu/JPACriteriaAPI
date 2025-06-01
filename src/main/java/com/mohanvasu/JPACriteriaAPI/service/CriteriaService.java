package com.mohanvasu.JPACriteriaAPI.service;

import com.mohanvasu.JPACriteriaAPI.entity.Student;
import com.mohanvasu.JPACriteriaAPI.model.PostPayload;
import com.mohanvasu.JPACriteriaAPI.model.StudentDetail;
import com.mohanvasu.JPACriteriaAPI.model.StudentResponse;
import jakarta.validation.Valid;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface CriteriaService {
    Student getStudentById(Long id);
    List<Student> getAllStudents();
    StudentResponse fetchStudentDetails(PostPayload postPayload) throws ExecutionException, InterruptedException;

    void addStudent(@Valid Student student);
    StudentDetail fetchStudentByEmail(String email);
}
