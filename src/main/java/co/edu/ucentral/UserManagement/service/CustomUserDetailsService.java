package co.edu.ucentral.UserManagement.service;

import co.edu.ucentral.UserManagement.model.Usuario;
import co.edu.ucentral.UserManagement.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections; // For roles if only one role

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        // Find the user by correo (email) which acts as the username for login
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo: " + correo));

        // Build Spring Security's UserDetails object from your Usuario entity
        return new org.springframework.security.core.userdetails.User(
                usuario.getCorreo(),        // Username (correo)
                usuario.getPassword(),      // Hashed password
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toUpperCase())) // Roles
        );
    }
}