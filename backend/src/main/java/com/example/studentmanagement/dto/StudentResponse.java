package com.example.studentmanagement.dto;

import lombok.Data;

@Data
public class StudentResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String department;
    private String course;
    private Integer year;
    private Double cgpa;
}
