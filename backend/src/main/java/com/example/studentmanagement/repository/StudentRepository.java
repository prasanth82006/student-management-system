package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    // Custom method to check if an email already exists in the database
    boolean existsByEmail(String email);

    // Search by first name or last name with pagination
    Page<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName, Pageable pageable);

    // Search by department with pagination
    Page<Student> findByDepartmentIgnoreCase(String department, Pageable pageable);
}
