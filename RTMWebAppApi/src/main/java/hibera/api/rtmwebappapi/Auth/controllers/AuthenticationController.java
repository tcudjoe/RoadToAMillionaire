package hibera.api.rtmwebappapi.Auth.controllers;

import hibera.api.rtmwebappapi.Auth.service.AuthService;
import hibera.api.rtmwebappapi.Auth.AuthenticationResponse;
import hibera.api.rtmwebappapi.Auth.LoginRequest;
import hibera.api.rtmwebappapi.Auth.RegisterRequest;
import hibera.api.rtmwebappapi.Auth.ResetPasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok(request);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/verify")
    public ResponseEntity verifyUser(@RequestParam("token") String token) {
        String result = authService.verifyUser(token);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/reset/password/request")
    public ResponseEntity<Object> resetPasswordRequest(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        authService.resetPasswordRequest(email);
        Map<String, String> response = new HashMap<>();
        response.put("message", "email for reset password has been sent");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset/password")
    public ResponseEntity<Object> resetPassword(@RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);
        Map<String, String> response = new HashMap<>();
        response.put("message", "password have been changed");
        return ResponseEntity.ok(response);
    }
}
