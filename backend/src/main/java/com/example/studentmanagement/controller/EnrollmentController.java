package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.Course;
import com.example.studentmanagement.entity.Enrollment;
import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.repository.CourseRepository;
import com.example.studentmanagement.repository.EnrollmentRepository;
import com.example.studentmanagement.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @PostMapping("/enroll/{studentId}/{courseId}")
    public ResponseEntity<?> enrollStudent(@PathVariable Long studentId, @PathVariable Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        
        return ResponseEntity.ok(enrollmentRepository.save(enrollment));
    }

    @PutMapping("/marks/{enrollmentId}")
    public ResponseEntity<?> updateMarks(
            @PathVariable Long enrollmentId,
            @RequestParam Double internal,
            @RequestParam Double external
    ) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow();
        enrollment.setInternalMarks(internal);
        enrollment.setExternalMarks(external);
        // The grade and total will be calculated automatically by @PreUpdate in the entity
        return ResponseEntity.ok(enrollmentRepository.save(enrollment));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Enrollment>> getStudentEnrollments(@PathVariable Long studentId) {
        return ResponseEntity.ok(enrollmentRepository.findByStudentId(studentId));
    }
}
