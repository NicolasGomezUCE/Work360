package co.edu.ucentral.UserManagement.repositories;

import co.edu.ucentral.UserManagement.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo); // For login
    boolean existsByCorreo(String correo); // To check if user already exists
}