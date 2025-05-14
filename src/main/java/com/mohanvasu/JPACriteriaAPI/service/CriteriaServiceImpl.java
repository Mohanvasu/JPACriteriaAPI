package com.mohanvasu.JPACriteriaAPI.service;

import com.mohanvasu.JPACriteriaAPI.entity.Student;
import com.mohanvasu.JPACriteriaAPI.exception.DatabaseException;
import com.mohanvasu.JPACriteriaAPI.model.PostPayload;
import com.mohanvasu.JPACriteriaAPI.repository.CriteriaRepository;
import com.mohanvasu.JPACriteriaAPI.util.DynamicQueryUtil;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CriteriaServiceImpl implements CriteriaService {

    private final CriteriaRepository criteriaRepository;
    private final DynamicQueryUtil dynamicQueryUtil;

    @Override
    public Student getStudentById(Long id) {
        Optional<Student> studentOptional;
        try{
            studentOptional = criteriaRepository.findById(id);
            return studentOptional.isPresent() ? studentOptional.get() : null;
        }catch (Exception e){
            throw new DatabaseException("Error occurred while fetching student by id :"+ e.getMessage());
        }
    }

    @Override
    public List<Student> getAllStudents() {
        return List.of();
    }

    @Override
    public List<Student> fetchStudentDetails(PostPayload postPayload) {
        TypedQuery<Student> query = dynamicQueryUtil.constructDynamicQuery(postPayload);
        List<Student> studentList = new ArrayList<>();
        try {
            studentList = query.getResultList();
        } catch (Exception e) {
            throw new DatabaseException("Error occurred while fetching student by id :"+ e.getMessage());
        }
        return studentList;
    }

    @Override
    public void addStudent(Student student) {
        log.info("Adding student {}", student.getFirstName());
        try {
            criteriaRepository.save(student);
        } catch (Exception e) {
            throw new DatabaseException("Error occurred while adding student :"+ e.getMessage());
        }
    }
}
