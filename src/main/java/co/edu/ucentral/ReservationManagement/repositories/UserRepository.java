package co.edu.ucentral.ReservationManagement.repositories;

import co.edu.ucentral.ReservationManagement.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// Repositorio para la entidad User
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Buscar un usuario por su correo electrónico (campo único)
    Optional<User> findByEmail(String email);

    // Buscar usuarios por su rol
    List<User> findByRole(User.Role role);
}
