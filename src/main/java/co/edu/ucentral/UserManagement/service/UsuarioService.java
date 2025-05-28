package co.edu.ucentral.UserManagement.service;

import co.edu.ucentral.UserManagement.model.Usuario;
import co.edu.ucentral.UserManagement.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder; // Inject PasswordEncoder

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registrarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado.");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); // Hash password
        usuario.setFechaRegistro(LocalDate.now()); // Set registration date using LocalDate.now()
        usuario.setRol("USUARIO"); // Default role
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> autenticarUsuario(String correo, String password) {
        Optional<Usuario> optionalUser = usuarioRepository.findByCorreo(correo);
        if (optionalUser.isPresent()) {
            Usuario usuario = optionalUser.get();
            if (passwordEncoder.matches(password, usuario.getPassword())) {
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }

    public Usuario actualizarUsuario(Long id, Usuario usuarioDetails) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(usuarioDetails.getNombre());
            usuario.setDireccion(usuarioDetails.getDireccion());
            usuario.setTelefono(usuarioDetails.getTelefono());
            // Do not update password here directly without re-hashing or separate endpoint
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    public Usuario asignarRol(Long id, String newRole) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setRol(newRole);
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }
}