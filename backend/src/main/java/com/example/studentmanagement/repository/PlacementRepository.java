package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.Placement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlacementRepository extends JpaRepository<Placement, Long> {
    long countByStatus(Placement.PlacementStatus status);
}
