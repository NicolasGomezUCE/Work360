package co.edu.ucentral.ReservationManagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// RegisterResponse.java
@Data
public class RegisterResponse {
    private Long id;
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RegisterResponse(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public RegisterResponse() {
    }
}

