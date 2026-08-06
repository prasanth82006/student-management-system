package com.example.studentmanagement.service;

import com.example.studentmanagement.entity.Student;
import org.springframework.data.domain.Page;

public interface StudentService {
    
    Student addStudent(Student student);
    
    Student updateStudent(Long id, Student student);
    
    void deleteStudent(Long id);
    
    Student getStudentById(Long id);
    
    Page<Student> getAllStudents(int page, int size, String sortBy, String sortDir, String keyword, String department);
}
