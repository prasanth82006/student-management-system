package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.data.domain.Page;
import jakarta.validation.Valid;

@CrossOrigin("*") // Enables CORS for frontend integration
@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // POST /api/students
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student createStudent(@Valid @RequestBody Student student) {
        return studentService.addStudent(student);
    }

    // GET /api/students
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Student> getAllStudents(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "department", required = false) String department
    ) {
        return studentService.getAllStudents(page, size, sortBy, sortDir, keyword, department);
    }

    // GET /api/students/{id}
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    // PUT /api/students/{id}
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Student updateStudent(@PathVariable Long id, @Valid @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

    // DELETE /api/students/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}
