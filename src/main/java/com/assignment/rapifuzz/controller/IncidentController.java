package com.assignment.rapifuzz.controller;

import com.assignment.rapifuzz.dto.request.IncidentDTO;
import com.assignment.rapifuzz.dto.response.ResponseMessageDTO;
import com.assignment.rapifuzz.entity.Incident;
import com.assignment.rapifuzz.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incident")
public class IncidentController {

    private final IncidentService incidentService;

    @Autowired
    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createIncident(@RequestBody IncidentDTO incident) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(incidentService.createIncident(incident));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessageDTO("Failed to create incident", "BAD_REQUEST"));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getIncidentsByUserId(@PathVariable Long userId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(incidentService.getIncidentsByUserId(userId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessageDTO(e.getMessage(), "NOT_FOUND"));
        }
    }

    @PutMapping("/{incidentId}/user/{userId}")
    public ResponseEntity<?> editIncident(@PathVariable String incidentId,@PathVariable Long userId,
                                          @RequestBody Incident updatedIncident) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(incidentService.updateIncident(incidentId, userId,updatedIncident));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessageDTO(e.getMessage(), "BAD_REQUEST"));
        }
    }
}
