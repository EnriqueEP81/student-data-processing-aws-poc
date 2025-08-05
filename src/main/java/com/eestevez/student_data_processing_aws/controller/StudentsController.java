package com.eestevez.student_data_processing_aws.controller;

import com.eestevez.student_data_processing_aws.dto.StudentDto;
import com.eestevez.student_data_processing_aws.service.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@AllArgsConstructor
public class StudentsController {

    private final StudentService studentService;

    @GetMapping
    public List<StudentDto> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping
    public void loadStudents(@RequestBody List<StudentDto> students) {
        try {
            studentService.saveStudentsToS3(students);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Students message could not be sent to S3");
        }

    }
}
