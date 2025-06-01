package com.mohanvasu.JPACriteriaAPI.controller;

import com.mohanvasu.JPACriteriaAPI.entity.Student;
import com.mohanvasu.JPACriteriaAPI.model.ApiResponse;
import com.mohanvasu.JPACriteriaAPI.model.PostPayload;
import com.mohanvasu.JPACriteriaAPI.model.StudentDetail;
import com.mohanvasu.JPACriteriaAPI.service.CriteriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController()
@RequestMapping("/api/criteria")
@RequiredArgsConstructor
public class CriteriaController {

    private final CriteriaService criteriaService;

    //use path variable to pass id
    @GetMapping("/fetchStudentById/{id}")
    public ResponseEntity<ApiResponse> getStudentById(@PathVariable Long id) {
        Student student = criteriaService.getStudentById(id);
        return new ResponseEntity<>(new ApiResponse(String.valueOf(HttpStatus.OK.value()),"Record fetched successfully", student), HttpStatus.OK);
    }

    //endpoint to insert student details
    @PutMapping("/addStudent")
    public ResponseEntity<ApiResponse> addStudent(@RequestBody @Valid Student student) {
        criteriaService.addStudent(student);
        return new ResponseEntity<>(new ApiResponse(String.valueOf(HttpStatus.OK.value()),"Record fetched successfully", student), HttpStatus.OK);
    }

    @PostMapping("/fetchStudentsByPayload")
    public ResponseEntity<ApiResponse> getStudentBasedOnPayload(@RequestBody @Valid PostPayload postPayload) {
        List<Student> studentList = criteriaService.fetchStudentDetails(postPayload);

        if(studentList.isEmpty()){
            return new ResponseEntity<>(ApiResponse.builder().data(new ArrayList<>()).message("No student details fetched").status(String.valueOf(HttpStatus.OK)).build(),HttpStatus.OK);
        }
        return new ResponseEntity<>(ApiResponse.builder()
                .status(String.valueOf(HttpStatus.OK))
                .message("Record fetched successfully")
                .data(studentList)
                .build(),HttpStatus.OK);
    }
    @GetMapping("/fetchStudentsByEmail/{email}")
    public ResponseEntity<ApiResponse> getStudentDetailsByEmail(@PathVariable String email){
        StudentDetail studentDetail = criteriaService.fetchStudentByEmail(email);
        if(Objects.nonNull(studentDetail)){
            return new ResponseEntity<>(
                    ApiResponse.builder()
                            .status(String.valueOf(HttpStatus.OK))
                            .message("Record fetched success")
                            .data(studentDetail).build()
            ,HttpStatus.OK);
        }
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Record fetched success")
                        .data(null).build()
                ,HttpStatus.OK);
    }
}
