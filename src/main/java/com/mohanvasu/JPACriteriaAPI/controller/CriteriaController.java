package com.mohanvasu.JPACriteriaAPI.controller;

import com.mohanvasu.JPACriteriaAPI.entity.Student;
import com.mohanvasu.JPACriteriaAPI.model.*;
import com.mohanvasu.JPACriteriaAPI.service.CriteriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@RestController()
@RequestMapping("/api/criteria")
@RequiredArgsConstructor
public class CriteriaController {

    private final CriteriaService criteriaService;

    //use path variable to pass id
    @GetMapping("/fetchStudentById/{id}")
    public ResponseEntity<ApiResponse2> getStudentById(@PathVariable Long id) {
        Student student = criteriaService.getStudentById(id);
        return new ResponseEntity<>(new ApiResponse2(String.valueOf(HttpStatus.OK.value()),"Record fetched successfully", student), HttpStatus.OK);
    }

    //endpoint to insert student details
    @PutMapping("/addStudent")
    public ResponseEntity<ApiResponse2> addStudent(@RequestBody @Valid Student student) {
        criteriaService.addStudent(student);
        return new ResponseEntity<>(new ApiResponse2(String.valueOf(HttpStatus.OK.value()),"Record fetched successfully", student), HttpStatus.OK);
    }

    @PostMapping("/fetchStudentsByPayload")
    public ResponseEntity<ApiResponse> getStudentBasedOnPayload(@RequestBody @Valid PostPayload postPayload) throws ExecutionException, InterruptedException {
        StudentResponse studentResponse = criteriaService.fetchStudentDetails(postPayload);
        int pageRequest = postPayload.getPage().getOffset();
        int pageLimit = postPayload.getPage().getPageSize();

        if(studentResponse.getStudentList().isEmpty()){
            return new ResponseEntity<>(ApiResponse.builder().data(new ArrayList<>()).message("No student details fetched").status(String.valueOf(HttpStatus.OK)).build(),HttpStatus.OK);
        }else{
            int totalRecords = studentResponse.getAllStudentListCount();
            int totalPages = (totalRecords / pageLimit) + 1;
            PageInfo pageInfo = PageInfo
                    .builder()
                    .page(pageRequest)
                    .pageSize(pageLimit)
                    .totalPages(totalPages)
                    .totalElements(totalRecords)
                    .build();
            return new ResponseEntity<>(ApiResponse
                    .builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Records fetched successfully")
                    .data(studentResponse.getStudentList())
                    .pageInfo(pageInfo)
                    .build(),HttpStatus.OK);
        }
    }
    @GetMapping("/fetchStudentsByEmail/{email}")
    public ResponseEntity<ApiResponse2> getStudentDetailsByEmail(@PathVariable String email){
        StudentDetail studentDetail = criteriaService.fetchStudentByEmail(email);
        if(Objects.nonNull(studentDetail)){
            return new ResponseEntity<>(
                    ApiResponse2.builder()
                            .status(String.valueOf(HttpStatus.OK))
                            .message("Record fetched success")
                            .data(studentDetail).build()
            ,HttpStatus.OK);
        }
        return new ResponseEntity<>(
                ApiResponse2.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Record fetched success")
                        .data(null).build()
                ,HttpStatus.OK);
    }
}
