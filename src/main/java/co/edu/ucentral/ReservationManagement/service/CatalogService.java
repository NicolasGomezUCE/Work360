package co.edu.ucentral.ReservationManagement.service;


import co.edu.ucentral.ReservationManagement.model.dto.SpaceAvailable;
import co.edu.ucentral.ReservationManagement.model.entity.*;
import co.edu.ucentral.ReservationManagement.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogService {

    private final SiteRepository siteRepository;
    private final SpaceRepository spaceRepository;
    private final ReservationRepository reservationRepository;

    public CatalogService(SiteRepository siteRepository, SpaceRepository spaceRepository, ReservationRepository reservationRepository) {
        this.siteRepository = siteRepository;
        this.spaceRepository = spaceRepository;
        this.reservationRepository = reservationRepository;
    }

    // GET /api/sites
    public List<Site> getAllSites() {
        return siteRepository.findAll();
    }

    // GET /api/sites/{id}/spaces?type=&date=&from=&to=
    public List<SpaceAvailable> getSpacesAndAvailability(
            Long siteId,
            Space.SpaceType type,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime) {

        // 1. Encontrar espacios que coincidan con los criterios (site, type, active=true)
        List<Space> spaces = spaceRepository.findBySiteIdAndTypeAndActiveTrue(siteId, type);

        // 2. Verificar la disponibilidad para cada espacio
        List<Reservation.Status> activeStatuses = List.of(Reservation.Status.pending, Reservation.Status.confirmed);

        return spaces.stream()
                .map(space -> {
                    // Verificar si hay alguna reserva solapada en el rango de tiempo
                    List<Reservation> overlappingReservations = reservationRepository.findBySpaceIdAndStatusInAndStartDatetimeLessThanAndEndDatetimeGreaterThan(
                            space.getId(),
                            activeStatuses,
                            endDateTime, // B.end > A.start
                            startDateTime // A.end > B.start
                    );

                    boolean isAvailable = overlappingReservations.isEmpty();

                    // Mapear a un DTO que incluya la información de disponibilidad (simulando "slots")
                    return new SpaceAvailable(
                            space.getId(),
                            space.getName(),
                            space.getCapacity(),
                            isAvailable ? "Available" : "Booked" // Simplificación: Solo decimos si está ocupado o no en el rango
                    );
                })
                .collect(Collectors.toList());
    }
}