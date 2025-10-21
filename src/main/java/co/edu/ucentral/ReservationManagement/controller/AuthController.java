package co.edu.ucentral.ReservationManagement.controller;

import co.edu.ucentral.ReservationManagement.model.dto.LoginRequest;
import co.edu.ucentral.ReservationManagement.model.dto.LoginResponse;
import co.edu.ucentral.ReservationManagement.model.dto.RegisterRequest;
import co.edu.ucentral.ReservationManagement.model.dto.RegisterResponse;
import co.edu.ucentral.ReservationManagement.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        RegisterResponse response = authService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED); // 201
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response); // 200
    }
}