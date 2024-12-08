package com.assignment.rapifuzz.service;

import com.assignment.rapifuzz.dto.request.IncidentDTO;
import com.assignment.rapifuzz.entity.Incident;
import com.assignment.rapifuzz.entity.User;
import com.assignment.rapifuzz.enums.Status;
import com.assignment.rapifuzz.repository.IncidentRepository;
import com.assignment.rapifuzz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final UserRepository userRepository;

    @Autowired
    public IncidentService(IncidentRepository incidentRepository, UserRepository userRepository) {
        this.incidentRepository = incidentRepository;
        this.userRepository = userRepository;
    }

    public Incident createIncident(IncidentDTO incidentDTO) {
        User user = userRepository.findById(incidentDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Incident incident = buildInicidentDetails(incidentDTO,user);
        return incidentRepository.save(incident);
    }

    private Incident buildInicidentDetails(IncidentDTO incidentDTO,User user) {
        Incident incident=new Incident();
        incident.setIncidentId(generateIncidentId());
        incident.setEnterpriseOrGovernment(incidentDTO.getEnterpriseOrGovernment());
        incident.setReporterName(generateIncidentId());
        incident.setIncidentDetails(generateIncidentId());
        incident.setPriority(incidentDTO.getPriority());
        incident.setStatus(incidentDTO.getStatus());
        incident.setUser(user);
        incident.setCreatedAt(LocalDateTime.now());
        incident.setUpdatedAt(LocalDateTime.now());
        return incident;
    }

    private String generateIncidentId() {
        return "RMG" + (int)(Math.random() * 100000) + "2022";
    }

    public List<Incident> getIncidentsByUserId(Long userId) {
        List<Incident> incidents = incidentRepository.findByUserId(userId);
        if (incidents.isEmpty()) {
            throw new RuntimeException("No incidents found for the user");
        }
        return incidents;
    }

    public Incident updateIncident(String incidentId, Long userId,Incident updatedIncident) {
        Incident incident = incidentRepository.findByIncidentId(incidentId)
                .orElseThrow(() -> new RuntimeException("Incident not found"));
        if (Status.CLOSED.equals(incident.getStatus())) {
            throw new RuntimeException("Cannot edit a closed incident");
        }
        if(!Objects.equals(incident.getUser().getId(), userId)){
            throw new RuntimeException("you are not eligible to edit this incident");
        }
        incident.setIncidentDetails(updatedIncident.getIncidentDetails());
        incident.setPriority(updatedIncident.getPriority());
        incident.setStatus(updatedIncident.getStatus());
        incident.setUpdatedAt(LocalDateTime.now());
        return incidentRepository.save(incident);
    }
}
