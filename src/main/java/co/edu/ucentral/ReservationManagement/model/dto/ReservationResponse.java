package co.edu.ucentral.ReservationManagement.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

// ReservationResponse.java
@Data
public class ReservationResponse {
    private Long id;
    private String status;
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ReservationResponse() {
    }

    public ReservationResponse(Long id, String status, BigDecimal price) {
        this.id = id;
        this.status = status;
        this.price = price;
    }
}