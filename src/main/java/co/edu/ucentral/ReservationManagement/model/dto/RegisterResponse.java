package co.edu.ucentral.ReservationManagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// RegisterResponse.java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
    private Long id;
    private String email;
}

