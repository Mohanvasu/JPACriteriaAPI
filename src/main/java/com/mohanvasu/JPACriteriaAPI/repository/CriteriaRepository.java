package com.mohanvasu.JPACriteriaAPI.repository;

import com.mohanvasu.JPACriteriaAPI.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CriteriaRepository extends JpaRepository<Student, Long> {

}
