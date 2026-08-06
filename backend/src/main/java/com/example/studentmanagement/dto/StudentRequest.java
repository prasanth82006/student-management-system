package com.example.studentmanagement.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class StudentRequest {
    
    @NotBlank(message = "First name is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain only letters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must contain only letters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
    private String phone;

    @NotBlank(message = "Department is required")
    private String department;

    @NotBlank(message = "Course is required")
    private String course;

    @NotNull(message = "Enrollment year is required")
    @Min(value = 2000, message = "Year must be after 2000")
    @Max(value = 2100, message = "Year must be before 2100")
    private Integer year;

    @NotNull(message = "CGPA is required")
    @DecimalMin(value = "0.0", message = "CGPA cannot be negative")
    @DecimalMax(value = "10.0", message = "CGPA cannot exceed 10.0")
    private Double cgpa;
}
