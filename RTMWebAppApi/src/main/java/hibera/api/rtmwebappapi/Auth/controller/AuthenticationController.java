package hibera.api.rtmwebappapi.Auth.controller;

import hibera.api.rtmwebappapi.Auth.model.AuthenticationRequest;
import hibera.api.rtmwebappapi.Auth.model.AuthenticationResponse;
import hibera.api.rtmwebappapi.Auth.model.RegisterRequest;
import hibera.api.rtmwebappapi.Auth.service.AuthenticationService;
import hibera.api.rtmwebappapi.Auth.model.VerificationRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        var response = service.register(request);
        if (request.isMfaEnabled()) {
            System.out.println("User registration successful with MFA enabled");
            return ResponseEntity.ok(response);
        }
        System.out.println("User registration successful without MFA enabled");
        return ResponseEntity.accepted().build();

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        System.out.println("User authentication successful");
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        System.out.println("User token refresh successful");
        service.refreshToken(request, response);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(
            @RequestBody VerificationRequest verificationRequest
    ) {
        return ResponseEntity.ok(service.verifyCode(verificationRequest));
    }

    @GetMapping("/isLoggedIn")
    public Boolean isUserAuthenticated(@RequestParam String token) {
        return service.isUserAuthenticated(token);
    }

    @GetMapping("/hasRole")
    public Boolean userHasRole(@RequestParam String roles, @RequestParam String token) {
        return service.userHasRole(roles, token);
    }
}
