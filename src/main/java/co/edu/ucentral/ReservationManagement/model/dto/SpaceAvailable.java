package co.edu.ucentral.ReservationManagement.model.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class SpaceAvailable {
    private Long id;
    private String name;
    private Integer capacity;
    private String availabilityStatus; // Ejemplo: "Available", "Booked"

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public SpaceAvailable(Long id, String name, Integer capacity, String availabilityStatus) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.availabilityStatus = availabilityStatus;
    }

    public SpaceAvailable() {
    }
}
