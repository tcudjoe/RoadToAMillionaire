package hibera.api.rtmwebappapi.Auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import hibera.api.rtmwebappapi.Auth.model.AuthenticationRequest;
import hibera.api.rtmwebappapi.Auth.model.AuthenticationResponse;
import hibera.api.rtmwebappapi.Auth.model.RegisterRequest;
import hibera.api.rtmwebappapi.Auth.model.VerificationRequest;
import hibera.api.rtmwebappapi.config.JwtService;
import hibera.api.rtmwebappapi.domain.Role;
import hibera.api.rtmwebappapi.users.user.model.User;
import hibera.api.rtmwebappapi.users.user.repository.UserRepository;
import hibera.api.rtmwebappapi.tfa.TwoFactorAuthentication;
import hibera.api.rtmwebappapi.token.model.Token;
import hibera.api.rtmwebappapi.token.repository.TokenRepository;
import hibera.api.rtmwebappapi.token.model.TokenType;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final TwoFactorAuthentication tfaService;
    private final UserDetailsService userDetailsService;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .mfaEnabled(request.isMfaEnabled())
                .build();

        System.out.println("Role assigned to the user: " + request.getRole());

        if (request.isMfaEnabled()) {
            user.setSecret(tfaService.generateNewSecret());
        }

        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .secretImageUri(tfaService.generateQrCodeImageUri(user.getSecret()))
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .mfaEnabled(user.isMfaEnabled())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        if (user.isMfaEnabled()) {
            return AuthenticationResponse.builder()
                    .accessToken("")
                    .refreshToken("")
                    .mfaEnabled(true)
                    .role(user.getRole())  // Include the user's role in the response
                    .build();
        }

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);


        revokeAllUserTokens(user);

        saveUserToken(user, jwtToken);
        System.out.println("token: " + jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .mfaEnabled(false)
                .role(user.getRole())  // Include the user's role in the response
                .build();
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .type(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();

        tokenRepository.save(token);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail).orElseThrow();

            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .mfaEnabled(false)
                        .build();

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);

            }
        }
    }

    public Boolean isUserAuthenticated(String jwtToken) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtService.extractUsername(jwtToken));
        try {
            return jwtService.isTokenValid(jwtToken, userDetails);
        } catch (Exception e) {
            return false;
        }
    }

    public AuthenticationResponse verifyCode(VerificationRequest verificationRequest) {
        User user = userRepository
                .findByEmail(verificationRequest.getEmail())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("No user found %S" + verificationRequest.getEmail()))
                );

        if (tfaService.isOtpNotValid(user.getSecret(), verificationRequest.getCode())) {
            throw new BadCredentialsException("Code is not correct.");
        }

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .accessToken(jwtToken)
                .mfaEnabled(user.isMfaEnabled())
                .build();
    }

    public Boolean userHasRole(String roles, String jwtToken) {
        String[] roleArray = roles.split(",");
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtService.extractUsername(jwtToken));
        return Arrays.stream(roleArray).anyMatch(role -> userDetails.getAuthorities().contains(new SimpleGrantedAuthority(role)));
    }
}
