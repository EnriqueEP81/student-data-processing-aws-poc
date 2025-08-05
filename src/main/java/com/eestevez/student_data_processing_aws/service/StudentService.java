package com.eestevez.student_data_processing_aws.service;

import com.eestevez.student_data_processing_aws.dto.StudentDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final S3Client s3Client;
    private static final String BUCKET_NAME = "student-tests3";
    public List<StudentDto> getAllStudents(){
        return List.of(
                StudentDto.builder()
                        .name("Enrique")
                        .id(1)
                        .build(),
                StudentDto.builder()
                        .name("Juan")
                        .id(2)
                        .build()
        );
    }

    public void saveStudentsToS3(List<StudentDto> students) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(students);

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(BUCKET_NAME)
                        .key(String.format("students_%d.json", System.currentTimeMillis()))
                        .build(),
                RequestBody.fromString(json)
        );
    }
}
