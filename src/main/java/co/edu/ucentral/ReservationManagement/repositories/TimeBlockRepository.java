package co.edu.ucentral.ReservationManagement.repositories;

import co.edu.ucentral.ReservationManagement.model.entity.TimeBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

// Repositorio para la entidad TimeBlock (opcional)
@Repository
public interface TimeBlockRepository extends JpaRepository<TimeBlock, Long> {
    // Buscar bloques de tiempo para un espacio entre dos fechas
    List<TimeBlock> findBySpaceIdAndStartDatetimeGreaterThanEqualAndEndDatetimeLessThanEqual(
            Long spaceId,
            LocalDateTime startDatetime,
            LocalDateTime endDatetime
    );
}
