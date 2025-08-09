package com.unir.digiturno.auth.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.unir.digiturno.auth.Services.UserService;
import com.unir.digiturno.auth.dto.LoginRequest;
import com.unir.digiturno.auth.dto.LoginResponse;
import com.unir.digiturno.auth.jwt.JwtUtil;
import com.unir.digiturno.auth.models.entities.User;
import com.unir.digiturno.auth.response.ApiResponse;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginRequest request) {
    if (request.getCorreo() == null || request.getContrasena() == null) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(false, "Correo o contraseña no proporcionados", 4001, null));
    }

    Optional<User> userOptional = userService.findByCorreo(request.getCorreo());

    if (userOptional.isEmpty()) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>(false, "Correo no encontrado", 4011, null));
    }

    User user = userOptional.get();

    if (!passwordEncoder.matches(request.getContrasena(), user.getContrasena())) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>(false, "Contraseña incorrecta", 4012, null));
    }

    String token = jwtUtil.generateToken(user.getCorreo());
    LoginResponse response = new LoginResponse(token, user);

    return ResponseEntity.ok(new ApiResponse<>(true, "Inicio de sesión exitoso", 2000, response));
}
}