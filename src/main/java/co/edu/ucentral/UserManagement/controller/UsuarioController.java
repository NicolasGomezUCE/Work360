package co.edu.ucentral.UserManagement.controller;

import co.edu.ucentral.UserManagement.model.Usuario;
import co.edu.ucentral.UserManagement.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // POST /usuarios: Crear un nuevo usuario.
    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);
            return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // POST /auth/login: Autenticar un usuario.
    @PostMapping("/auth/login")
    public ResponseEntity<?> autenticarUsuario(@RequestBody Map<String, String> loginRequest) {
        String correo = loginRequest.get("correo");
        String password = loginRequest.get("password");

        if (correo == null || password == null) {
            return new ResponseEntity("Correo y contraseña son requeridos.", HttpStatus.BAD_REQUEST);
        }

        return usuarioService.autenticarUsuario(correo, password)
                .map(usuario -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Autenticación exitosa.");
                    response.put("userId", String.valueOf(usuario.getId()));
                    response.put("rol", usuario.getRol());
                    // In a real application, you'd generate and return a JWT here.
                    return new ResponseEntity<>(response, HttpStatus.OK);
                })
                .orElse(new ResponseEntity("Credenciales inválidas.", HttpStatus.UNAUTHORIZED));
    }

    // PUT /usuarios/{id}: Modificar información del usuario.
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetails) {
        try {
            Usuario updatedUsuario = usuarioService.actualizarUsuario(id, usuarioDetails);
            return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // DELETE /usuarios/{id}: Eliminar usuario.
    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        try {
            usuarioService.eliminarUsuario(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    // POST /usuarios/{id}/roles: Asignar roles.
    @PostMapping("/usuarios/{id}/roles")
    public ResponseEntity<Usuario> asignarRol(@PathVariable Long id, @RequestBody Map<String, String> roleRequest) {
        String newRole = roleRequest.get("rol");
        if (newRole == null || newRole.isEmpty()) {
            return new ResponseEntity("El rol es requerido.", HttpStatus.BAD_REQUEST);
        }
        try {
            Usuario updatedUsuario = usuarioService.asignarRol(id, newRole);
            return new ResponseEntity<>(updatedUsuario, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // GET /usuarios/{id}: Consultar perfil de usuario.
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        return usuarioService.getUsuarioById(id)
                .map(usuario -> new ResponseEntity<>(usuario, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}