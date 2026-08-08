package com.example.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "enrollments")
public class Enrollment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    private Double internalMarks;
    private Double externalMarks;
    private Double totalMarks;
    private String grade;
    
    // Automatically calculate total marks and grade before saving
    @PrePersist
    @PreUpdate
    public void calculateMarks() {
        if (internalMarks != null && externalMarks != null) {
            this.totalMarks = internalMarks + externalMarks;
            this.grade = calculateGrade(this.totalMarks);
        }
    }

    private String calculateGrade(Double total) {
        if (total >= 90) return "A+";
        if (total >= 80) return "A";
        if (total >= 70) return "B";
        if (total >= 60) return "C";
        if (total >= 50) return "D";
        return "F";
    }
}
