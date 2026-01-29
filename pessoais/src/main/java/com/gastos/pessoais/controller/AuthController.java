package com.gastos.pessoais.controller;

import com.gastos.pessoais.dto.LoginRequest;
import com.gastos.pessoais.dto.LoginResponse;
import com.gastos.pessoais.model.Usuario;
import com.gastos.pessoais.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;

    public AuthController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.email());

        if (usuario == null || !usuario.getSenha().equals(loginRequest.senha())) {
            return ResponseEntity.badRequest().build();
        }

        // TODO: Implementar geração de token JWT aqui. Por enquanto, retornamos um mock.
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", usuario.getId());
        claims.put("email", usuario.getEmail());

        // Token mock para teste
        String token = "mock-jwt-token-" + usuario.getEmail();

        return ResponseEntity.ok(new LoginResponse(token, "Bearer", usuario.getId(), usuario.getEmail()));
    }
}
