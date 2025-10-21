package co.edu.ucentral.ReservationManagement.model.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class SpaceAvailable {
    private Long id;
    private String name;
    private Integer capacity;
    private String availabilityStatus; // Ejemplo: "Available", "Booked"
}
