package com.assignment.rapifuzz.repository;

import com.assignment.rapifuzz.entity.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IncidentRepository extends JpaRepository<Incident, Long> {
    Optional<Incident> findByIncidentId(String incidentId);
    List<Incident> findByUserId(Long userId);
}