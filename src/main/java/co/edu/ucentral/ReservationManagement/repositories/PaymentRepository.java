package co.edu.ucentral.ReservationManagement.repositories;

import co.edu.ucentral.ReservationManagement.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Repositorio para la entidad Payment
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Buscar un pago por su reserva asociada (relación 1:1)
    Optional<Payment> findByReservationId(Long reservationId);

    // Buscar pagos por estado
    List<Payment> findByStatus(Payment.PaymentStatus status);
}
