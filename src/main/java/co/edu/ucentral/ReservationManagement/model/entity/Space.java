package co.edu.ucentral.ReservationManagement.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "spaces") // Versión camelCase: "Spaces"
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Space {

    public enum SpaceType {
        meeting, office, desk
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación ManyToOne con Site
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SpaceType type;

    @Column(nullable = false)
    private Integer capacity;

    // JSON mapping - El soporte puede variar, pero String es el más seguro
    @Column(columnDefinition = "json")
    private String amenities;

    @Column(nullable = false)
    private Boolean active = true;
}