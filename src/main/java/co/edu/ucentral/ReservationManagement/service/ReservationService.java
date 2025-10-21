package co.edu.ucentral.ReservationManagement.service;

import co.edu.ucentral.ReservationManagement.model.dto.ReservationRequest;
import co.edu.ucentral.ReservationManagement.model.dto.ReservationResponse;
import co.edu.ucentral.ReservationManagement.model.entity.Reservation;
import co.edu.ucentral.ReservationManagement.model.entity.Space;
import co.edu.ucentral.ReservationManagement.model.entity.User;
import co.edu.ucentral.ReservationManagement.repositories.ReservationRepository;
import co.edu.ucentral.ReservationManagement.repositories.SpaceRepository;
import co.edu.ucentral.ReservationManagement.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Instant;
import java.util.List;

@Service

public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final SpaceRepository spaceRepository;
    private final UserRepository userRepository;

    // Simulación de usuario autenticado (se usaría Spring Security en realidad)
    private static final Long MOCK_USER_ID = 1L;

    public ReservationService(ReservationRepository reservationRepository, SpaceRepository spaceRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.spaceRepository = spaceRepository;
        this.userRepository = userRepository;
    }

    // POST /api/reservations
    @Transactional
    public ReservationResponse createReservation(ReservationRequest request) {
        Space space = spaceRepository.findById(request.getSpaceId())
                .orElseThrow(() -> new IllegalArgumentException("Space not found."));

        User user = userRepository.findById(MOCK_USER_ID)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        LocalDateTime start = request.getStartDatetime();
        LocalDateTime end = request.getEndDatetime();

        if (start.isAfter(end) || start.isEqual(end)) {
            throw new IllegalArgumentException("Invalid date range.");
        }

        // 1. **Validación de Solapamiento (Crucial)**
        List<Reservation.Status> activeStatuses = List.of(Reservation.Status.pending, Reservation.Status.confirmed);
        List<Reservation> overlapping = reservationRepository.findBySpaceIdAndStatusInAndStartDatetimeLessThanAndEndDatetimeGreaterThan(
                space.getId(),
                activeStatuses,
                end,
                start
        );

        if (!overlapping.isEmpty()) {
            throw new IllegalStateException("Space is not available for the requested time.");
        }

        // 2. Crear y guardar la reserva
        Reservation newReservation = new Reservation();
        newReservation.setUser(user);
        newReservation.setSpace(space);
        newReservation.setStartDatetime(start);
        newReservation.setEndDatetime(end);
        newReservation.setStatus(Reservation.Status.pending); // Estado inicial

        // Simulación de cálculo de precio (ej: $10/hora)
        long hours = java.time.Duration.between(start, end).toHours();
        BigDecimal price = BigDecimal.valueOf(hours).multiply(BigDecimal.TEN);
        newReservation.setPrice(price);

        newReservation.setCreatedAt(Instant.now());

        Reservation savedReservation = reservationRepository.save(newReservation);

        // 3. Devolver DTO de respuesta
        return new ReservationResponse(
                savedReservation.getId(),
                savedReservation.getStatus().name(),
                savedReservation.getPrice()
        );
    }

    // GET /api/reservations/{id}
    public Reservation getReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found."));
    }

    // DELETE /api/reservations/{id}
    @Transactional
    public void cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found."));

        // **Política de Cancelación Simulada**
        LocalDateTime now = LocalDateTime.now();

        if (reservation.getStatus() != Reservation.Status.confirmed && reservation.getStatus() != Reservation.Status.pending) {
            throw new IllegalStateException("Only pending or confirmed reservations can be cancelled.");
        }

        // Ejemplo: No se permite cancelar 24 horas antes del inicio
        if (reservation.getStartDatetime().minusHours(24).isBefore(now)) {
            throw new IllegalStateException("Cancellation not allowed within 24 hours of start time.");
        }

        reservation.setStatus(Reservation.Status.cancelled);
        reservation.setCancelledAt(now);
        reservation.setCancellationReason("User requested cancellation outside the restricted period.");

        reservationRepository.save(reservation);

        // Lógica adicional: Reembolso, log de auditoría, etc.
    }
}