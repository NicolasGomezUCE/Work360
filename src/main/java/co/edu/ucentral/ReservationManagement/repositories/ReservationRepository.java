package co.edu.ucentral.ReservationManagement.repositories;

import co.edu.ucentral.ReservationManagement.model.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

// Repositorio para la entidad Reservation
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // Búsqueda por espacio y rango de fechas (uso de índice recomendado)
    List<Reservation> findBySpaceIdAndStartDatetimeBetween(
            Long spaceId,
            LocalDateTime startDatetime,
            LocalDateTime endDatetime
    );

    // Búsqueda de reservas activas (pending o confirmed) para un espacio en un rango de fechas.
    // Esto es crucial para la lógica de no solapamiento.
    List<Reservation> findBySpaceIdAndStatusInAndStartDatetimeLessThanAndEndDatetimeGreaterThan(
            Long spaceId,
            List<Reservation.Status> statuses,
            LocalDateTime endDatetime,
            LocalDateTime startDatetime
    );

    // Buscar todas las reservas de un usuario
    List<Reservation> findByUserIdOrderByStartDatetimeDesc(Long userId);
}
