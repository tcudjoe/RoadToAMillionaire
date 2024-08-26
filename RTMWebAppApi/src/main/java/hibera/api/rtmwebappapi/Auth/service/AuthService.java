package hibera.api.rtmwebappapi.Auth.service;

import hibera.api.rtmwebappapi.Auth.service.mail.MailService;
import hibera.api.rtmwebappapi.domain.dto.AuthenticationResponse;
import hibera.api.rtmwebappapi.domain.dto.LoginRequest;
import hibera.api.rtmwebappapi.domain.dto.RegisterRequest;
import hibera.api.rtmwebappapi.domain.Role;
import hibera.api.rtmwebappapi.domain.User;
import hibera.api.rtmwebappapi.domain.dto.ResetPasswordRequest;
import hibera.api.rtmwebappapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private MailService emailService;

    public void register(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(Role.USER);

        // Generate a unique token
        String token = UUID.randomUUID().toString();

        // Save the token with the user (if needed)
        user.setVerificationToken(token);
        userRepository.save(user);

        // Send verification email
        emailService.sendVerificationEmail(user.getEmail(), token);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String authenticationToken = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(authenticationToken, loginRequest.getUsername());
    }

    public Optional<org.springframework.security.core.userdetails.User> getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return Optional.of(principal);
    }

    public String verifyUser(String token) {
        User user = userRepository.findByVerificationToken(token);

        if (user == null) {
            return "Invalid verification token.";
        }

        user.setVerified(true);
        user.setVerificationToken(null);
        userRepository.save(user);

        return "Your email has been verified. You can now log in.";
    }

    public void resetPasswordRequest(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found exception"));
        String token = UUID.randomUUID().toString();

        user.setResetPasswordToken(token);
        userRepository.save(user);

        emailService.sendPasswordResetEmail(user.getEmail(), token);
    }

    public void resetPassword(ResetPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found exception"));

        if (user.getResetPasswordToken().equals(request.getToken())) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setResetPasswordToken(null);
            userRepository.save(user);
        } else {
            throw new RuntimeException("token is not valid to reset password");
        }
    }


}
