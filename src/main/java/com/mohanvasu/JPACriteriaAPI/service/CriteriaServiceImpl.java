package com.mohanvasu.JPACriteriaAPI.service;

import com.mohanvasu.JPACriteriaAPI.entity.Student;
import com.mohanvasu.JPACriteriaAPI.exception.DatabaseException;
import com.mohanvasu.JPACriteriaAPI.model.PostPayload;
import com.mohanvasu.JPACriteriaAPI.model.StudentDetail;
import com.mohanvasu.JPACriteriaAPI.model.StudentResponse;
import com.mohanvasu.JPACriteriaAPI.repository.CriteriaRepository;
import com.mohanvasu.JPACriteriaAPI.util.DynamicQueryUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CriteriaServiceImpl implements CriteriaService {

    private final CriteriaRepository criteriaRepository;
    private final DynamicQueryUtil dynamicQueryUtil;
    private final EntityManager entityManager;

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
    public StudentResponse fetchStudentDetails(PostPayload postPayload) throws ExecutionException, InterruptedException {

        CompletableFuture<List<Student>> studentListFuture = CompletableFuture
                .supplyAsync(
                        ()-> (dynamicQueryUtil.constructDynamicQuery(postPayload).getResultList())
                );

        //fetch all records without pagination
        CompletableFuture<List<Student>> allStudentListFuture = CompletableFuture.supplyAsync(() -> {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Student> studentQuery = cb.createQuery(Student.class);
            Root<Student> root = studentQuery.from(Student.class);
            studentQuery.select(root);

            List<Predicate> predicateFilter = dynamicQueryUtil.constructWhereClause(postPayload.getFilter(), root, cb);
            studentQuery.where(predicateFilter.toArray(new Predicate[0]));

            TypedQuery<Student> queryWithoutPagination = entityManager.createQuery(studentQuery);
            return queryWithoutPagination.getResultList();
        });

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(studentListFuture,allStudentListFuture);
        combinedFuture.join();

        List<Student> studentList = studentListFuture.get();
        List<Student> allStudentList = allStudentListFuture.get();


        return new StudentResponse(studentList,allStudentList.size());
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

    @Override
    public StudentDetail fetchStudentByEmail(String email) {
        Optional<Student> studentOptional = criteriaRepository.findByEmail(email);
        return studentOptional
                .map(student -> StudentDetail.builder()
                        .firstName(Optional.ofNullable(student.getFirstName())
                                .map(String::toUpperCase)
                                .orElse("NA"))
                                .lastName(student.getLastName())
                                .email(student.getEmail())
                                .build()
                        ).orElse(null);
        
    }

}
