package hibera.api.rtmwebappapi.Auth.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import hibera.api.rtmwebappapi.Auth.AuthenticationResponse;
import hibera.api.rtmwebappapi.Auth.LoginRequest;
import hibera.api.rtmwebappapi.Auth.RegisterRequest;
import hibera.api.rtmwebappapi.Auth.ResetPasswordRequest;
import hibera.api.rtmwebappapi.Auth.service.mail.MailService;
import hibera.api.rtmwebappapi.domain.enums.Role;
import hibera.api.rtmwebappapi.repository.UserRepository;
import hibera.api.rtmwebappapi.stripe.repository.StripeCustomerRepository;
import hibera.api.rtmwebappapi.stripe.service.StripeCustomerService;
import hibera.api.rtmwebappapi.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    @Autowired
    private StripeCustomerService customerService;
    @Autowired
    private StripeCustomerRepository customerRepository;

    public void register(RegisterRequest request) throws StripeException {
        User user = new User();
        user.setUsername(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhonenumber(request.getPhoneNumber());
        user.setUserCreationDate(LocalDateTime.now());
        user.setCity(request.getCity());
        user.setState(request.getState());
        user.setPostalCode(request.getPostalCode());
        user.setCompanyName(request.getCompanyName());
        user.setAddressLine1(request.getAddressLine1());
        user.setAddressLine2(request.getAddressLine2());
        user.setCompany(request.getCompanyId());
        user.setRole(Role.USER);

        // Create a Stripe customer
        Customer stripeCustomer = customerService.createCustomerInStripe(user);

        // Save the user to the db
        userRepository.save(user);

        // Save the Stripe customer to the db
        customerService.createStripeCustomerInDb(request, stripeCustomer, user);

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
