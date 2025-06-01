package com.mohanvasu.JPACriteriaAPI.repository;

import com.mohanvasu.JPACriteriaAPI.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CriteriaRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
}
