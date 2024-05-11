package hibera.api.rtmwebappapi.controllers;

import hibera.api.rtmwebappapi.domain.dto.AuthenticationResponse;
import hibera.api.rtmwebappapi.domain.dto.LoginRequest;
import hibera.api.rtmwebappapi.domain.dto.RegisterRequest;
import hibera.api.rtmwebappapi.Auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok(request);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
