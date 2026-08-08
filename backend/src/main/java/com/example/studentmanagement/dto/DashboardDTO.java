package com.example.studentmanagement.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardDTO {
    private long totalStudents;
    private long activePlacements;
    private double averageCgpa;
    private long upcomingEvents;
}
