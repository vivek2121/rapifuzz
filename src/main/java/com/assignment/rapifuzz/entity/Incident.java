package com.assignment.rapifuzz.entity;

import com.assignment.rapifuzz.enums.Priority;
import com.assignment.rapifuzz.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "incidents")
@Data
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String incidentId;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String enterpriseOrGovernment;
    private String reporterName;
    private String incidentDetails;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
