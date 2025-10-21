package co.edu.ucentral.ReservationManagement.model.dto;

import lombok.*;

// RegisterRequest.java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String name;
    private String email;
    private String password;

}
