package co.edu.ucentral.ReservationManagement.repositories;

import co.edu.ucentral.ReservationManagement.model.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Repositorio para la entidad Invoice
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    // Buscar una factura por su número (campo único)
    Optional<Invoice> findByNumber(String number);

    // Buscar una factura por su reserva asociada (relación 1:1)
    Optional<Invoice> findByReservationId(Long reservationId);
}
