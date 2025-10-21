package co.edu.ucentral.ReservationManagement.repositories;

import co.edu.ucentral.ReservationManagement.model.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Repositorio para la entidad AuditLog
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    // Buscar logs de auditoría por la entidad afectada y su ID (uso de índice)
    List<AuditLog> findByEntityAndEntityId(String entity, Long entityId);

    // Buscar logs creados después de una fecha
    List<AuditLog> findByCreatedAtAfter(java.time.Instant createdAt);
}
