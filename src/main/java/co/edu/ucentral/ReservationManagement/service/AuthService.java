package co.edu.ucentral.ReservationManagement.service;

import co.edu.ucentral.ReservationManagement.model.dto.LoginRequest;
import co.edu.ucentral.ReservationManagement.model.dto.LoginResponse;
import co.edu.ucentral.ReservationManagement.model.dto.RegisterRequest;
import co.edu.ucentral.ReservationManagement.model.dto.RegisterResponse;
import co.edu.ucentral.ReservationManagement.model.entity.User;
import co.edu.ucentral.ReservationManagement.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    // Simulación de registro
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists.");
        }

        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        // Encriptar la contraseña
        newUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        // Asignar rol por defecto (ej: client)
        newUser.setRole(User.Role.client);
        newUser.setCreatedAt(Instant.now());

        User savedUser = userRepository.save(newUser);

        return new RegisterResponse(savedUser.getId(), savedUser.getEmail());
    }

    // Simulación de login y generación de token
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials."));

        // Verificar la contraseña encriptada
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials.");
        }

        // --- Lógica de JWT/Token Simulado ---
        String accessToken = UUID.randomUUID().toString(); // Simulación de token
        long expiresIn = 3600; // 1 hora en segundos

        return new LoginResponse(accessToken, expiresIn);
    }
}
