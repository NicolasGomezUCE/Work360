package co.edu.ucentral.ReservationManagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// ReservationRequest.java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {
    private Long spaceId;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
}
