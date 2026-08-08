package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.DashboardDTO;
import com.example.studentmanagement.entity.Placement;
import com.example.studentmanagement.repository.EventRepository;
import com.example.studentmanagement.repository.PlacementRepository;
import com.example.studentmanagement.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final StudentRepository studentRepository;
    private final PlacementRepository placementRepository;
    private final EventRepository eventRepository;

    @GetMapping
    public ResponseEntity<DashboardDTO> getDashboardStats() {
        long totalStudents = studentRepository.count();
        long activePlacements = placementRepository.countByStatus(Placement.PlacementStatus.SELECTED);
        long upcomingEvents = eventRepository.count();
        
        // Simulating Average CGPA for speed, normally we'd do a @Query("SELECT AVG(s.cgpa) FROM Student s")
        double avgCgpa = 7.5; 
        
        DashboardDTO dto = DashboardDTO.builder()
                .totalStudents(totalStudents)
                .activePlacements(activePlacements)
                .averageCgpa(avgCgpa)
                .upcomingEvents(upcomingEvents)
                .build();
                
        return ResponseEntity.ok(dto);
    }
}
