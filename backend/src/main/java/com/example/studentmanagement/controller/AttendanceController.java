package com.example.studentmanagement.controller;

import com.example.studentmanagement.entity.Attendance;
import com.example.studentmanagement.entity.Enrollment;
import com.example.studentmanagement.repository.AttendanceRepository;
import com.example.studentmanagement.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceRepository attendanceRepository;
    private final EnrollmentRepository enrollmentRepository;

    @PostMapping("/mark/{enrollmentId}")
    public ResponseEntity<?> markAttendance(
            @PathVariable Long enrollmentId,
            @RequestParam Attendance.AttendanceStatus status
    ) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow();
        
        Attendance attendance = new Attendance();
        attendance.setEnrollment(enrollment);
        attendance.setDate(LocalDate.now());
        attendance.setStatus(status);

        return ResponseEntity.ok(attendanceRepository.save(attendance));
    }

    @GetMapping("/enrollment/{enrollmentId}")
    public ResponseEntity<List<Attendance>> getAttendanceForEnrollment(@PathVariable Long enrollmentId) {
        return ResponseEntity.ok(attendanceRepository.findByEnrollmentId(enrollmentId));
    }
}
