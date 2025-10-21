package co.edu.ucentral.ReservationManagement.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "audit_logs") // Versión camelCase: "AuditLogs"
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación ManyToOne con User (puede ser NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // Sin 'nullable = false'
    private User user;

    @Column(nullable = false)
    private String action;

    @Column(nullable = false)
    private String entity;

    @Column(name = "entity_id")
    private Long entityId;

    // JSON mapping (almacenado como texto/clob)
    @Column(columnDefinition = "json")
    private String data;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
}