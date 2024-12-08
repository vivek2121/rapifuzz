package com.assignment.rapifuzz.dto.request;

import com.assignment.rapifuzz.enums.Priority;
import com.assignment.rapifuzz.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncidentDTO {
    private Long userId;
    private String enterpriseOrGovernment;
    private String reporterName;
    private String incidentDetails;
    private Priority priority;
    private Status status;

}
