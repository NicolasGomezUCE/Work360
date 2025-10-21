package co.edu.ucentral.ReservationManagement.repositories;

import co.edu.ucentral.ReservationManagement.model.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Repositorio para la entidad Site
@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
    // Buscar un sitio por su nombre
    Optional<Site> findByName(String name);
}
