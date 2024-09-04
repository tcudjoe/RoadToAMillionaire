package hibera.api.rtmwebappapi.Auth;

import hibera.api.rtmwebappapi.Auth.service.AuthService;
import hibera.api.rtmwebappapi.Auth.service.JwtProvider;
import hibera.api.rtmwebappapi.Auth.service.mail.MailService;
import hibera.api.rtmwebappapi.domain.enums.Role;
import hibera.api.rtmwebappapi.users.User;
import hibera.api.rtmwebappapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private JwtProvider jwtProvider;

  @Mock
  private MailService emailService;

  @InjectMocks
  private AuthService authService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testRegister() {
    RegisterRequest request = new RegisterRequest();
    request.setEmail("test@example.com");
    request.setPassword("password");
    request.setFirstName("John");
    request.setLastName("Doe");

    User user = new User();
    user.setUsername(request.getEmail());
    user.setPassword("encodedPassword");
    user.setEmail(request.getEmail());
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setRole(Role.USER);
    user.setVerified(false);

    when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
    when(userRepository.save(any(User.class))).thenReturn(user);

    authService.register(request);

    verify(userRepository, times(1)).save(any(User.class));
    verify(emailService, times(1)).sendVerificationEmail(eq(request.getEmail()), anyString());
  }

  @Test
  public void testLogin() {
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setUsername("test@example.com");
    loginRequest.setPassword("password");

    Authentication authentication = mock(Authentication.class);

    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(authentication);
    when(jwtProvider.generateToken(any(Authentication.class)))
            .thenReturn("jwtToken");

    AuthenticationResponse response = authService.login(loginRequest);

    assertEquals("jwtToken", response.getAuthenticationToken());
    assertEquals("test@example.com", response.getUsername());
  }

  @Test
  public void testVerifyUser() {
    String token = "verificationToken";
    User user = new User();
    user.setVerificationToken(token);

    when(userRepository.findByVerificationToken(token)).thenReturn(user);

    String result = authService.verifyUser(token);

    assertEquals("Your email has been verified. You can now log in.", result);
    assertEquals(true, user.isVerified());
    assertEquals(null, user.getVerificationToken());
    verify(userRepository, times(1)).save(user);
  }
}
