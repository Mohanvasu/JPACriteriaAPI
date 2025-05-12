package com.mohanvasu.JPACriteriaAPI.service;

import com.mohanvasu.JPACriteriaAPI.entity.Student;
import com.mohanvasu.JPACriteriaAPI.repository.CriteriaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CriteriaServiceImpl implements CriteriaService {

    private final CriteriaRepository criteriaRepository;

    @Override
    public Student getStudentById(Long id) {
        Optional<Student> studentOptional;
        try{
            studentOptional = criteriaRepository.findById(id);
            return studentOptional.isPresent() ? studentOptional.get() : null;
        }catch (Exception e){
            throw new RuntimeException("Error occurred while fetching student by id", e);
        }
    }

    @Override
    public List<Student> getAllStudents() {
        return List.of();
    }
}
