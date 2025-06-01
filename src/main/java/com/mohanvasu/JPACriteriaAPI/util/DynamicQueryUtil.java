package com.mohanvasu.JPACriteriaAPI.util;

import com.mohanvasu.JPACriteriaAPI.entity.Student;
import com.mohanvasu.JPACriteriaAPI.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DynamicQueryUtil {

    private final EntityManager entityManager;

    public TypedQuery<Student> constructDynamicQuery(PostPayload postPayload){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();  //obtaining criteriaBuilder from entityManager
        CriteriaQuery<Student> query = cb.createQuery(Student.class); //creating a criteria query instance
        Root<Student> student = query.from(Student.class);  //root entity from which query starts
        query.select(student); //select student from student table
        List<Predicate> predicateFilter = constructWhereClause(postPayload.getFilter(),student,cb);
        query.where(predicateFilter.toArray(new Predicate[0]));
        if(!postPayload.getSort().isEmpty()) {
            List<Order> orders = constructOrderByClause(postPayload.getSort(), student);
            query.orderBy(orders);
        }
        Page page = postPayload.getPage();
        TypedQuery<Student> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((page.getOffset()-1)* page.getPageSize());
        typedQuery.setMaxResults(page.getPageSize());
        return typedQuery;
    }

    public List<Predicate> constructWhereClause(Filter filter,Root<Student> student,CriteriaBuilder cb){
        //build a dynamic query from the filter
        List<Operand> operands = filter.getOperands();
        List<Predicate> predicates = new ArrayList<>();
        for(Operand operand : operands){
            String field = operand.getField();
            String value = operand.getValue();
            Predicate predicate;
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
            predicates.add(predicate);
        }
        return predicates;
    }
    private List<Order> constructOrderByClause(List<Sort> sorts,Root<Student> student){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        List<Order> orderList = new ArrayList<>();
        for(Sort sort : sorts){
            String field = sort.getField();
            if(sort.getDirection().equals(Direction.ASC)){
                orderList.add(cb.asc(student.get(field)));
            }else{
                orderList.add(cb.desc(student.get(field)));
            }
        }
        return orderList;
    }
}
