package com.example.studentmanagement.service;

import com.example.studentmanagement.entity.Student;
import java.util.List;

public interface StudentService {
    
    Student addStudent(Student student);
    
    Student updateStudent(Long id, Student student);
    
    void deleteStudent(Long id);
    
    Student getStudentById(Long id);
    
    List<Student> getAllStudents();
}
