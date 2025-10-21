package co.edu.ucentral.ReservationManagement.controller;

import co.edu.ucentral.ReservationManagement.model.dto.ReservationRequest;
import co.edu.ucentral.ReservationManagement.model.dto.ReservationResponse;
import co.edu.ucentral.ReservationManagement.model.entity.Reservation;
import co.edu.ucentral.ReservationManagement.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // POST /api/reservations
    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest request) {
        ReservationResponse response = reservationService.createReservation(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED); // 201
    }

    // GET /api/reservations/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable Long id) {
        Reservation reservation = reservationService.getReservation(id);
        return ResponseEntity.ok(reservation); // 200
    }

    // DELETE /api/reservations/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        // En un escenario real, se debe verificar que el usuario autenticado es dueño de la reserva o un admin/op
        reservationService.cancelReservation(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}