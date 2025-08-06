package com.hub.foro.forohub.auth;

import com.hub.foro.forohub.usuario.Usuario;
import com.hub.foro.forohub.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        Usuario usuario = usuarioRepository.findByCorreoElectronico(request.correoElectronico())
                .orElse(null);
        if (usuario == null || !usuario.getContrasena().equals(request.contrasena())) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
        String token = jwtUtil.generateToken(usuario.getCorreoElectronico());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
