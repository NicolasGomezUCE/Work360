package co.edu.ucentral.ReservationManagement.repositories;

import co.edu.ucentral.ReservationManagement.model.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Repositorio para la entidad Space
@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {
    // Buscar todos los espacios activos en un sitio específico (uso de índice recomendado)
    List<Space> findBySiteIdAndActiveTrue(Long siteId);

    // Buscar todos los espacios por sitio y tipo (uso de índice recomendado)
    List<Space> findBySiteIdAndTypeAndActiveTrue(Long siteId, Space.SpaceType type);
}
