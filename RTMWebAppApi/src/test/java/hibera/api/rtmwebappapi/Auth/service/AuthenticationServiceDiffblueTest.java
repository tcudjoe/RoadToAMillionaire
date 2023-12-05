package hibera.api.rtmwebappapi.Auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import hibera.api.rtmwebappapi.Auth.AuthenticationRequest;
import hibera.api.rtmwebappapi.Auth.AuthenticationResponse;
import hibera.api.rtmwebappapi.Auth.RegisterRequest;
import hibera.api.rtmwebappapi.config.JwtService;
import hibera.api.rtmwebappapi.domain.Role;
import hibera.api.rtmwebappapi.domain.User;
import hibera.api.rtmwebappapi.repositories.UserRepository;
import hibera.api.rtmwebappapi.token.Token;
import hibera.api.rtmwebappapi.token.TokenRepository;
import hibera.api.rtmwebappapi.token.TokenType;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.CoyoteOutputStream;
import org.apache.catalina.connector.Response;
import org.apache.catalina.connector.ResponseFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.springframework.mock.web.MockHttpServletMapping;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthenticationService.class})
@ExtendWith(SpringExtension.class)
class AuthenticationServiceDiffblueTest {
  @MockBean
  private AuthenticationManager authenticationManager;

  @Autowired
  private AuthenticationService authenticationService;

  @MockBean
  private JwtService jwtService;

  @MockBean
  private PasswordEncoder passwordEncoder;

  @MockBean
  private TokenRepository tokenRepository;

  @MockBean
  private UserRepository userRepository;

  /**
   * Method under test:  {@link AuthenticationService#register(RegisterRequest)}
   */
  @Test
  void testRegister() {
    when(jwtService.generateRefreshToken(Mockito.<UserDetails>any())).thenReturn("ABC123");
    when(jwtService.generateToken(Mockito.<UserDetails>any())).thenReturn("ABC123");

    User user = new User();
    user.setEmail("jane.doe@example.org");
    user.setFirstName("Jane");
    user.setId(1L);
    user.setLastName("Doe");
    user.setMfaEnabled(true);
    user.setPassword("iloveyou");
    user.setRole(Role.ADMIN);
    user.setSecret("Secret");
    user.setTokens(new ArrayList<>());
    user.setUsername("janedoe");
    when(userRepository.save(Mockito.<User>any())).thenReturn(user);

    User user2 = new User();
    user2.setEmail("jane.doe@example.org");
    user2.setFirstName("Jane");
    user2.setId(1L);
    user2.setLastName("Doe");
    user2.setMfaEnabled(true);
    user2.setPassword("iloveyou");
    user2.setRole(Role.ADMIN);
    user2.setSecret("Secret");
    user2.setTokens(new ArrayList<>());
    user2.setUsername("janedoe");

    Token token = new Token();
    token.setExpired(true);
    token.setId(1L);
    token.setRevoked(true);
    token.setToken("ABC123");
    token.setType(TokenType.BEARER);
    token.setUser(user2);
    when(tokenRepository.save(Mockito.<Token>any())).thenReturn(token);
    when(tokenRepository.findAllValidTokensByUser(Mockito.<Long>any())).thenReturn(new ArrayList<>());
    when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");
    AuthenticationResponse actualRegisterResult = authenticationService
            .register(new RegisterRequest("Jane", "Doe", "jane.doe@example.org", "iloveyou", Role.ADMIN));
    verify(jwtService).generateRefreshToken(Mockito.<UserDetails>any());
    verify(jwtService).generateToken(Mockito.<UserDetails>any());
    verify(tokenRepository).findAllValidTokensByUser(Mockito.<Long>any());
    verify(userRepository).save(Mockito.<User>any());
    verify(tokenRepository).save(Mockito.<Token>any());
    verify(passwordEncoder).encode(Mockito.<CharSequence>any());
    assertEquals("ABC123", actualRegisterResult.getAccessToken());
    assertEquals("ABC123", actualRegisterResult.getRefreshToken());
  }

  /**
   * Method under test:  {@link AuthenticationService#authenticate(AuthenticationRequest)}
   */
  @Test
  void testAuthenticate() throws AuthenticationException {
    when(jwtService.generateRefreshToken(Mockito.<UserDetails>any())).thenReturn("ABC123");
    when(jwtService.generateToken(Mockito.<UserDetails>any())).thenReturn("ABC123");

    User user = new User();
    user.setEmail("jane.doe@example.org");
    user.setFirstName("Jane");
    user.setId(1L);
    user.setLastName("Doe");
    user.setMfaEnabled(true);
    user.setPassword("iloveyou");
    user.setRole(Role.ADMIN);
    user.setSecret("Secret");
    user.setTokens(new ArrayList<>());
    user.setUsername("janedoe");
    Optional<User> ofResult = Optional.of(user);
    when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);

    User user2 = new User();
    user2.setEmail("jane.doe@example.org");
    user2.setFirstName("Jane");
    user2.setId(1L);
    user2.setLastName("Doe");
    user2.setMfaEnabled(true);
    user2.setPassword("iloveyou");
    user2.setRole(Role.ADMIN);
    user2.setSecret("Secret");
    user2.setTokens(new ArrayList<>());
    user2.setUsername("janedoe");

    Token token = new Token();
    token.setExpired(true);
    token.setId(1L);
    token.setRevoked(true);
    token.setToken("ABC123");
    token.setType(TokenType.BEARER);
    token.setUser(user2);
    when(tokenRepository.save(Mockito.<Token>any())).thenReturn(token);
    when(tokenRepository.findAllValidTokensByUser(Mockito.<Long>any())).thenReturn(new ArrayList<>());
    when(authenticationManager.authenticate(Mockito.<Authentication>any()))
            .thenReturn(new BearerTokenAuthenticationToken("ABC123"));
    AuthenticationResponse actualAuthenticateResult = authenticationService
            .authenticate(new AuthenticationRequest("jane.doe@example.org", "iloveyou"));
    verify(jwtService).generateRefreshToken(Mockito.<UserDetails>any());
    verify(jwtService).generateToken(Mockito.<UserDetails>any());
    verify(userRepository).findByEmail(Mockito.<String>any());
    verify(tokenRepository).findAllValidTokensByUser(Mockito.<Long>any());
    verify(tokenRepository).save(Mockito.<Token>any());
    verify(authenticationManager).authenticate(Mockito.<Authentication>any());
    assertEquals("ABC123", actualAuthenticateResult.getAccessToken());
    assertEquals("ABC123", actualAuthenticateResult.getRefreshToken());
  }

  /**
   * Method under test:  {@link AuthenticationService#authenticate(AuthenticationRequest)}
   */
  @Test
  void testAuthenticate2() throws AuthenticationException {
    when(jwtService.generateRefreshToken(Mockito.<UserDetails>any())).thenReturn("ABC123");
    when(jwtService.generateToken(Mockito.<UserDetails>any())).thenReturn("ABC123");
    User user = mock(User.class);
    when(user.getId()).thenReturn(1L);
    doNothing().when(user).setEmail(Mockito.<String>any());
    doNothing().when(user).setFirstName(Mockito.<String>any());
    doNothing().when(user).setId(anyLong());
    doNothing().when(user).setLastName(Mockito.<String>any());
    doNothing().when(user).setMfaEnabled(Mockito.<Boolean>any());
    doNothing().when(user).setPassword(Mockito.<String>any());
    doNothing().when(user).setRole(Mockito.<Role>any());
    doNothing().when(user).setSecret(Mockito.<String>any());
    doNothing().when(user).setTokens(Mockito.<List<Token>>any());
    doNothing().when(user).setUsername(Mockito.<String>any());
    user.setEmail("jane.doe@example.org");
    user.setFirstName("Jane");
    user.setId(1L);
    user.setLastName("Doe");
    user.setMfaEnabled(true);
    user.setPassword("iloveyou");
    user.setRole(Role.ADMIN);
    user.setSecret("Secret");
    user.setTokens(new ArrayList<>());
    user.setUsername("janedoe");
    Optional<User> ofResult = Optional.of(user);
    when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);

    User user2 = new User();
    user2.setEmail("jane.doe@example.org");
    user2.setFirstName("Jane");
    user2.setId(1L);
    user2.setLastName("Doe");
    user2.setMfaEnabled(true);
    user2.setPassword("iloveyou");
    user2.setRole(Role.ADMIN);
    user2.setSecret("Secret");
    user2.setTokens(new ArrayList<>());
    user2.setUsername("janedoe");

    Token token = new Token();
    token.setExpired(true);
    token.setId(1L);
    token.setRevoked(true);
    token.setToken("ABC123");
    token.setType(TokenType.BEARER);
    token.setUser(user2);
    when(tokenRepository.save(Mockito.<Token>any())).thenReturn(token);
    when(tokenRepository.findAllValidTokensByUser(Mockito.<Long>any())).thenReturn(new ArrayList<>());
    when(authenticationManager.authenticate(Mockito.<Authentication>any()))
            .thenReturn(new BearerTokenAuthenticationToken("ABC123"));
    AuthenticationResponse actualAuthenticateResult = authenticationService
            .authenticate(new AuthenticationRequest("jane.doe@example.org", "iloveyou"));
    verify(jwtService).generateRefreshToken(Mockito.<UserDetails>any());
    verify(jwtService).generateToken(Mockito.<UserDetails>any());
    verify(user).getId();
    verify(user).setEmail(Mockito.<String>any());
    verify(user).setFirstName(Mockito.<String>any());
    verify(user).setId(anyLong());
    verify(user).setLastName(Mockito.<String>any());
    verify(user).setMfaEnabled(Mockito.<Boolean>any());
    verify(user).setPassword(Mockito.<String>any());
    verify(user).setRole(Mockito.<Role>any());
    verify(user).setSecret(Mockito.<String>any());
    verify(user).setTokens(Mockito.<List<Token>>any());
    verify(user).setUsername(Mockito.<String>any());
    verify(userRepository).findByEmail(Mockito.<String>any());
    verify(tokenRepository).findAllValidTokensByUser(Mockito.<Long>any());
    verify(tokenRepository).save(Mockito.<Token>any());
    verify(authenticationManager).authenticate(Mockito.<Authentication>any());
    assertEquals("ABC123", actualAuthenticateResult.getAccessToken());
    assertEquals("ABC123", actualAuthenticateResult.getRefreshToken());
  }

  /**
   * Method under test:  {@link AuthenticationService#authenticate(AuthenticationRequest)}
   */
  @Test
  void testAuthenticate3() throws AuthenticationException {
    when(jwtService.generateRefreshToken(Mockito.<UserDetails>any())).thenReturn("ABC123");
    when(jwtService.generateToken(Mockito.<UserDetails>any())).thenReturn("ABC123");
    User user = mock(User.class);
    when(user.getId()).thenReturn(1L);
    doNothing().when(user).setEmail(Mockito.<String>any());
    doNothing().when(user).setFirstName(Mockito.<String>any());
    doNothing().when(user).setId(anyLong());
    doNothing().when(user).setLastName(Mockito.<String>any());
    doNothing().when(user).setMfaEnabled(Mockito.<Boolean>any());
    doNothing().when(user).setPassword(Mockito.<String>any());
    doNothing().when(user).setRole(Mockito.<Role>any());
    doNothing().when(user).setSecret(Mockito.<String>any());
    doNothing().when(user).setTokens(Mockito.<List<Token>>any());
    doNothing().when(user).setUsername(Mockito.<String>any());
    user.setEmail("jane.doe@example.org");
    user.setFirstName("Jane");
    user.setId(1L);
    user.setLastName("Doe");
    user.setMfaEnabled(true);
    user.setPassword("iloveyou");
    user.setRole(Role.ADMIN);
    user.setSecret("Secret");
    user.setTokens(new ArrayList<>());
    user.setUsername("janedoe");
    Optional<User> ofResult = Optional.of(user);
    when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);

    User user2 = new User();
    user2.setEmail("jane.doe@example.org");
    user2.setFirstName("Jane");
    user2.setId(1L);
    user2.setLastName("Doe");
    user2.setMfaEnabled(true);
    user2.setPassword("iloveyou");
    user2.setRole(Role.ADMIN);
    user2.setSecret("Secret");
    user2.setTokens(new ArrayList<>());
    user2.setUsername("janedoe");

    Token token = new Token();
    token.setExpired(true);
    token.setId(1L);
    token.setRevoked(true);
    token.setToken("ABC123");
    token.setType(TokenType.BEARER);
    token.setUser(user2);

    User user3 = new User();
    user3.setEmail("jane.doe@example.org");
    user3.setFirstName("Jane");
    user3.setId(1L);
    user3.setLastName("Doe");
    user3.setMfaEnabled(true);
    user3.setPassword("iloveyou");
    user3.setRole(Role.ADMIN);
    user3.setSecret("Secret");
    user3.setTokens(new ArrayList<>());
    user3.setUsername("janedoe");

    Token token2 = new Token();
    token2.setExpired(true);
    token2.setId(1L);
    token2.setRevoked(true);
    token2.setToken("ABC123");
    token2.setType(TokenType.BEARER);
    token2.setUser(user3);

    ArrayList<Token> tokenList = new ArrayList<>();
    tokenList.add(token2);
    when(tokenRepository.saveAll(Mockito.<Iterable<Token>>any())).thenReturn(new ArrayList<>());
    when(tokenRepository.save(Mockito.<Token>any())).thenReturn(token);
    when(tokenRepository.findAllValidTokensByUser(Mockito.<Long>any())).thenReturn(tokenList);
    when(authenticationManager.authenticate(Mockito.<Authentication>any()))
            .thenReturn(new BearerTokenAuthenticationToken("ABC123"));
    AuthenticationResponse actualAuthenticateResult = authenticationService
            .authenticate(new AuthenticationRequest("jane.doe@example.org", "iloveyou"));
    verify(jwtService).generateRefreshToken(Mockito.<UserDetails>any());
    verify(jwtService).generateToken(Mockito.<UserDetails>any());
    verify(user).getId();
    verify(user).setEmail(Mockito.<String>any());
    verify(user).setFirstName(Mockito.<String>any());
    verify(user).setId(anyLong());
    verify(user).setLastName(Mockito.<String>any());
    verify(user).setMfaEnabled(Mockito.<Boolean>any());
    verify(user).setPassword(Mockito.<String>any());
    verify(user).setRole(Mockito.<Role>any());
    verify(user).setSecret(Mockito.<String>any());
    verify(user).setTokens(Mockito.<List<Token>>any());
    verify(user).setUsername(Mockito.<String>any());
    verify(userRepository).findByEmail(Mockito.<String>any());
    verify(tokenRepository).findAllValidTokensByUser(Mockito.<Long>any());
    verify(tokenRepository).save(Mockito.<Token>any());
    verify(tokenRepository).saveAll(Mockito.<Iterable<Token>>any());
    verify(authenticationManager).authenticate(Mockito.<Authentication>any());
    assertEquals("ABC123", actualAuthenticateResult.getAccessToken());
    assertEquals("ABC123", actualAuthenticateResult.getRefreshToken());
  }

  /**
   * Method under test:
   * {@link AuthenticationService#refreshToken(HttpServletRequest, HttpServletResponse)}
   */
  @Test
  void testRefreshToken() throws IOException {
    MockHttpServletRequest request = new MockHttpServletRequest();
    Response response = new Response();
    authenticationService.refreshToken(request, response);
    HttpServletResponse response2 = response.getResponse();
    assertTrue(response2 instanceof ResponseFacade);
    assertTrue(request.getInputStream() instanceof DelegatingServletInputStream);
    assertTrue(request.getHttpServletMapping() instanceof MockHttpServletMapping);
    assertTrue(request.getSession() instanceof MockHttpSession);
    assertEquals("", request.getContextPath());
    assertEquals("", request.getMethod());
    assertEquals("", request.getRequestURI());
    assertEquals("", request.getServletPath());
    assertEquals("HTTP/1.1", request.getProtocol());
    assertEquals("http", request.getScheme());
    assertEquals("localhost", request.getLocalName());
    assertEquals("localhost", request.getRemoteHost());
    assertEquals("localhost", request.getServerName());
    assertEquals(80, request.getLocalPort());
    assertEquals(80, request.getRemotePort());
    assertEquals(80, request.getServerPort());
    assertEquals(DispatcherType.REQUEST, request.getDispatcherType());
    assertFalse(request.isAsyncStarted());
    assertFalse(request.isAsyncSupported());
    assertFalse(request.isRequestedSessionIdFromURL());
    assertTrue(request.isActive());
    assertTrue(request.isRequestedSessionIdFromCookie());
    assertTrue(request.isRequestedSessionIdValid());
    assertSame(response.getOutputStream(), response2.getOutputStream());
  }

  /**
   * Method under test:
   * {@link AuthenticationService#refreshToken(HttpServletRequest, HttpServletResponse)}
   */
  @Test
  void testRefreshToken2() throws IOException {
    HttpServletRequestWrapper request = mock(HttpServletRequestWrapper.class);
    when(request.getHeader(Mockito.<String>any())).thenReturn("https://example.org/example");
    Response response = new Response();
    authenticationService.refreshToken(request, response);
    verify(request).getHeader(Mockito.<String>any());
    HttpServletResponse response2 = response.getResponse();
    assertTrue(response2 instanceof ResponseFacade);
    assertSame(response.getOutputStream(), response2.getOutputStream());
  }

  /**
   * Method under test:  {@link AuthenticationService#refreshToken(HttpServletRequest, HttpServletResponse)}
   */
  @Test
  void testRefreshToken3() throws IOException {
    when(jwtService.generateToken(Mockito.<UserDetails>any())).thenReturn("ABC123");
    when(jwtService.isTokenValid(Mockito.<String>any(), Mockito.<UserDetails>any())).thenReturn(true);
    when(jwtService.extractUsername(Mockito.<String>any())).thenReturn("janedoe");
    User user = mock(User.class);
    when(user.getId()).thenReturn(1L);
    doNothing().when(user).setEmail(Mockito.<String>any());
    doNothing().when(user).setFirstName(Mockito.<String>any());
    doNothing().when(user).setId(anyLong());
    doNothing().when(user).setLastName(Mockito.<String>any());
    doNothing().when(user).setMfaEnabled(Mockito.<Boolean>any());
    doNothing().when(user).setPassword(Mockito.<String>any());
    doNothing().when(user).setRole(Mockito.<Role>any());
    doNothing().when(user).setSecret(Mockito.<String>any());
    doNothing().when(user).setTokens(Mockito.<List<Token>>any());
    doNothing().when(user).setUsername(Mockito.<String>any());
    user.setEmail("jane.doe@example.org");
    user.setFirstName("Jane");
    user.setId(1L);
    user.setLastName("Doe");
    user.setMfaEnabled(true);
    user.setPassword("iloveyou");
    user.setRole(Role.ADMIN);
    user.setSecret("Secret");
    user.setTokens(new ArrayList<>());
    user.setUsername("janedoe");
    Optional<User> ofResult = Optional.of(user);
    when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);

    User user2 = new User();
    user2.setEmail("jane.doe@example.org");
    user2.setFirstName("Jane");
    user2.setId(1L);
    user2.setLastName("Doe");
    user2.setMfaEnabled(true);
    user2.setPassword("iloveyou");
    user2.setRole(Role.ADMIN);
    user2.setSecret("Secret");
    user2.setTokens(new ArrayList<>());
    user2.setUsername("janedoe");

    Token token = new Token();
    token.setExpired(true);
    token.setId(1L);
    token.setRevoked(true);
    token.setToken("ABC123");
    token.setType(TokenType.BEARER);
    token.setUser(user2);

    User user3 = new User();
    user3.setEmail("jane.doe@example.org");
    user3.setFirstName("Jane");
    user3.setId(1L);
    user3.setLastName("Doe");
    user3.setMfaEnabled(true);
    user3.setPassword("iloveyou");
    user3.setRole(Role.ADMIN);
    user3.setSecret("Authorization");
    user3.setTokens(new ArrayList<>());
    user3.setUsername("janedoe");
    Token token2 = mock(Token.class);
    doNothing().when(token2).setExpired(Mockito.<Boolean>any());
    doNothing().when(token2).setId(Mockito.<Long>any());
    doNothing().when(token2).setRevoked(Mockito.<Boolean>any());
    doNothing().when(token2).setToken(Mockito.<String>any());
    doNothing().when(token2).setType(Mockito.<TokenType>any());
    doNothing().when(token2).setUser(Mockito.<User>any());
    token2.setExpired(true);
    token2.setId(1L);
    token2.setRevoked(true);
    token2.setToken("ABC123");
    token2.setType(TokenType.BEARER);
    token2.setUser(user3);

    ArrayList<Token> tokenList = new ArrayList<>();
    tokenList.add(token2);
    when(tokenRepository.saveAll(Mockito.<Iterable<Token>>any())).thenReturn(new ArrayList<>());
    when(tokenRepository.save(Mockito.<Token>any())).thenReturn(token);
    when(tokenRepository.findAllValidTokensByUser(Mockito.<Long>any())).thenReturn(tokenList);
    HttpServletRequestWrapper request = mock(HttpServletRequestWrapper.class);
    when(request.getHeader(Mockito.<String>any())).thenReturn("Bearer ");
    authenticationService.refreshToken(request, new MockHttpServletResponse());
    verify(jwtService).extractUsername(Mockito.<String>any());
    verify(jwtService).generateToken(Mockito.<UserDetails>any());
    verify(jwtService).isTokenValid(Mockito.<String>any(), Mockito.<UserDetails>any());
    verify(user).getId();
    verify(user).setEmail(Mockito.<String>any());
    verify(user).setFirstName(Mockito.<String>any());
    verify(user).setId(anyLong());
    verify(user).setLastName(Mockito.<String>any());
    verify(user).setMfaEnabled(Mockito.<Boolean>any());
    verify(user).setPassword(Mockito.<String>any());
    verify(user).setRole(Mockito.<Role>any());
    verify(user).setSecret(Mockito.<String>any());
    verify(user).setTokens(Mockito.<List<Token>>any());
    verify(user).setUsername(Mockito.<String>any());
    verify(userRepository).findByEmail(Mockito.<String>any());
    verify(token2, atLeast(1)).setExpired(Mockito.<Boolean>any());
    verify(token2).setId(Mockito.<Long>any());
    verify(token2, atLeast(1)).setRevoked(Mockito.<Boolean>any());
    verify(token2).setToken(Mockito.<String>any());
    verify(token2).setType(Mockito.<TokenType>any());
    verify(token2).setUser(Mockito.<User>any());
    verify(tokenRepository).findAllValidTokensByUser(Mockito.<Long>any());
    verify(request).getHeader(Mockito.<String>any());
    verify(tokenRepository).save(Mockito.<Token>any());
    verify(tokenRepository).saveAll(Mockito.<Iterable<Token>>any());
  }

  /**
   * Method under test:  {@link AuthenticationService#refreshToken(HttpServletRequest, HttpServletResponse)}
   */
  @Test
  void testRefreshToken4() throws IOException {
    when(jwtService.isTokenValid(Mockito.<String>any(), Mockito.<UserDetails>any())).thenReturn(false);
    when(jwtService.extractUsername(Mockito.<String>any())).thenReturn("janedoe");
    User user = mock(User.class);
    doNothing().when(user).setEmail(Mockito.<String>any());
    doNothing().when(user).setFirstName(Mockito.<String>any());
    doNothing().when(user).setId(anyLong());
    doNothing().when(user).setLastName(Mockito.<String>any());
    doNothing().when(user).setMfaEnabled(Mockito.<Boolean>any());
    doNothing().when(user).setPassword(Mockito.<String>any());
    doNothing().when(user).setRole(Mockito.<Role>any());
    doNothing().when(user).setSecret(Mockito.<String>any());
    doNothing().when(user).setTokens(Mockito.<List<Token>>any());
    doNothing().when(user).setUsername(Mockito.<String>any());
    user.setEmail("jane.doe@example.org");
    user.setFirstName("Jane");
    user.setId(1L);
    user.setLastName("Doe");
    user.setMfaEnabled(true);
    user.setPassword("iloveyou");
    user.setRole(Role.ADMIN);
    user.setSecret("Secret");
    user.setTokens(new ArrayList<>());
    user.setUsername("janedoe");
    Optional<User> ofResult = Optional.of(user);
    when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(ofResult);

    User user2 = new User();
    user2.setEmail("jane.doe@example.org");
    user2.setFirstName("Jane");
    user2.setId(1L);
    user2.setLastName("Doe");
    user2.setMfaEnabled(true);
    user2.setPassword("iloveyou");
    user2.setRole(Role.ADMIN);
    user2.setSecret("Authorization");
    user2.setTokens(new ArrayList<>());
    user2.setUsername("janedoe");
    Token token = mock(Token.class);
    doNothing().when(token).setExpired(Mockito.<Boolean>any());
    doNothing().when(token).setId(Mockito.<Long>any());
    doNothing().when(token).setRevoked(Mockito.<Boolean>any());
    doNothing().when(token).setToken(Mockito.<String>any());
    doNothing().when(token).setType(Mockito.<TokenType>any());
    doNothing().when(token).setUser(Mockito.<User>any());
    token.setExpired(true);
    token.setId(1L);
    token.setRevoked(true);
    token.setToken("ABC123");
    token.setType(TokenType.BEARER);
    token.setUser(user2);

    ArrayList<Token> tokenList = new ArrayList<>();
    tokenList.add(token);
    HttpServletRequestWrapper request = mock(HttpServletRequestWrapper.class);
    when(request.getHeader(Mockito.<String>any())).thenReturn("Bearer ");
    authenticationService.refreshToken(request, mock(HttpServletResponse.class));
    verify(jwtService).extractUsername(Mockito.<String>any());
    verify(jwtService).isTokenValid(Mockito.<String>any(), Mockito.<UserDetails>any());
    verify(user).setEmail(Mockito.<String>any());
    verify(user).setFirstName(Mockito.<String>any());
    verify(user).setId(anyLong());
    verify(user).setLastName(Mockito.<String>any());
    verify(user).setMfaEnabled(Mockito.<Boolean>any());
    verify(user).setPassword(Mockito.<String>any());
    verify(user).setRole(Mockito.<Role>any());
    verify(user).setSecret(Mockito.<String>any());
    verify(user).setTokens(Mockito.<List<Token>>any());
    verify(user).setUsername(Mockito.<String>any());
    verify(userRepository).findByEmail(Mockito.<String>any());
    verify(token).setExpired(Mockito.<Boolean>any());
    verify(token).setId(Mockito.<Long>any());
    verify(token).setRevoked(Mockito.<Boolean>any());
    verify(token).setToken(Mockito.<String>any());
    verify(token).setType(Mockito.<TokenType>any());
    verify(token).setUser(Mockito.<User>any());
    verify(request).getHeader(Mockito.<String>any());
  }
}
