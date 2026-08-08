package com.example.studentmanagement.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "placements")
public class Placement extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String jobRole;

    private Double packageAmount; // in LPA

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlacementStatus status;

    private LocalDate interviewDate;

    public enum PlacementStatus {
        PENDING, SELECTED, REJECTED
    }
}
