package com.mohanvasu.JPACriteriaAPI.controller;

import com.mohanvasu.JPACriteriaAPI.entity.Student;
import com.mohanvasu.JPACriteriaAPI.model.ApiResponse;
import com.mohanvasu.JPACriteriaAPI.service.CriteriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/api/criteria")
@RequiredArgsConstructor
public class CriteriaController {

    private final CriteriaService criteriaService;

    //use path variable to pass id
    @GetMapping("/fetchStudentById/{id}")
    public ResponseEntity<ApiResponse> getStudentById(@PathVariable Long id) {
        Student student = criteriaService.getStudentById(id);
        return new ResponseEntity<>(new ApiResponse(String.valueOf(HttpStatus.OK.value()),"Success", student), HttpStatus.OK);
    }

    @PostMapping("/fetchStudentsByPayload")
    public ResponseEntity<ApiResponse> getStudentBasedOnPayload(@RequestBody PostPayload postPayload) {

    }
}
