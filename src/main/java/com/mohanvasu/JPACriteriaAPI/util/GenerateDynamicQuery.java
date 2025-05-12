package com.mohanvasu.JPACriteriaAPI.util;

import com.mohanvasu.JPACriteriaAPI.entity.Student;
import com.mohanvasu.JPACriteriaAPI.model.Filter;
import com.mohanvasu.JPACriteriaAPI.model.Operand;
import com.mohanvasu.JPACriteriaAPI.model.PostPayload;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GenerateDynamicQuery {

    private final EntityManager entityManager;
    public void constructDynamicQuery(PostPayload postPayload){

    }

    private void constructWhereClause(Filter filter){
        //build a dynamic query from the filter
        List<Operand> operands = filter.getOperands();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();  //obtaining criteriaBuilder from entityManager
        CriteriaQuery<Student> query = cb.createQuery(Student.class); //creating a criteria query instance
        Root<Student> student = query.from(Student.class);  //root entity from which query starts
        List<Predicate> predicates = new ArrayList<>();

        //select student from student table
        query.select(student);
        for(Operand operand : operands){
            String field = operand.getField();
            String value = operand.getValue();
            Predicate predicate=null;
            switch (operand.getOperator()){
                case EQ :
                    predicate = cb.equal(student.get(operand.getField()), operand.getValue());
                    break;
                case GTE :
                    predicate = cb.greaterThanOrEqualTo(student.get(field), value);
                    break;
                case LTE :
                    predicate = cb.lessThanOrEqualTo(student.get(field), value);
                    break;
                case GT:
                    predicate = cb.greaterThan(student.get(field), value);
                    break;
                case LT:
                    predicate =cb.lessThan(student.get(field), value);
                    break;
                case BETWEEN:
                    String [] values = value.split(",");
                    predicate =cb.between(student.get(operand.getField()), values[0], values[1]);
                    break;
                case SUBSTRING:
                    //only support for string type values else throw bad request exception
                    predicate = cb.like(cb.lower(student.get(field)), "%" + value.toLowerCase() + "%");
                    break;
                default:
                    throw new IllegalArgumentException("Invalid operator : " + operand.getOperator());
            }
        }
    }
}
