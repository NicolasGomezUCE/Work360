package co.edu.ucentral.ReservationManagement.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "users") // Nota: La versión camelCase sería "Users" o "User" si se usa convención Spring
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    public enum Role {
        admin, op, client
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    // Relación: Un usuario puede tener muchas reservas (OneToMany)
    // No la incluimos aquí para simplificar, pero se vería:
    // @OneToMany(mappedBy = "user")
    // private Set<Reservation> reservations;
}