package hibera.api.rtmwebappapi.Auth;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import hibera.api.rtmwebappapi.Auth.service.AuthenticationService;
import hibera.api.rtmwebappapi.domain.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AuthenticationController.class})
@ExtendWith(SpringExtension.class)
class AuthenticationControllerDiffblueTest {
  @Autowired
  private AuthenticationController authenticationController;

  @MockBean
  private AuthenticationService authenticationService;

  /**
   * Method under test: {@link AuthenticationController#register(RegisterRequest)}
   */
  @Test
  void testRegister() throws Exception {
    AuthenticationResponse buildResult = AuthenticationResponse.builder()
            .accessToken("ABC123")
            .refreshToken("ABC123")
            .build();
    when(authenticationService.register(Mockito.<RegisterRequest>any())).thenReturn(buildResult);

    RegisterRequest registerRequest = new RegisterRequest();
    registerRequest.setEmail("jane.doe@example.org");
    registerRequest.setFirstName("Jane");
    registerRequest.setLastName("Doe");
    registerRequest.setPassword("iloveyou");
    registerRequest.setRole(Role.ADMIN);
    String content = (new ObjectMapper()).writeValueAsString(registerRequest);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content);
    MockMvcBuilders.standaloneSetup(authenticationController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andExpect(
                    MockMvcResultMatchers.content().string("{\"access_token\":\"ABC123\",\"refresh_token\":\"ABC123\"}"));
  }

  /**
   * Method under test:
   * {@link AuthenticationController#authenticate(AuthenticationRequest)}
   */
  @Test
  void testAuthenticate() throws Exception {
    AuthenticationResponse buildResult = AuthenticationResponse.builder()
            .accessToken("ABC123")
            .refreshToken("ABC123")
            .build();
    when(authenticationService.authenticate(Mockito.<AuthenticationRequest>any())).thenReturn(buildResult);

    AuthenticationRequest authenticationRequest = new AuthenticationRequest();
    authenticationRequest.setEmail("jane.doe@example.org");
    authenticationRequest.setPassword("iloveyou");
    String content = (new ObjectMapper()).writeValueAsString(authenticationRequest);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/auth/authenticate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content);
    MockMvcBuilders.standaloneSetup(authenticationController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
            .andExpect(
                    MockMvcResultMatchers.content().string("{\"access_token\":\"ABC123\",\"refresh_token\":\"ABC123\"}"));
  }

  /**
   * Method under test:
   * {@link AuthenticationController#refreshToken(HttpServletRequest, HttpServletResponse)}
   */
  @Test
  void testRefreshToken() throws Exception {
    doNothing().when(authenticationService)
            .refreshToken(Mockito.<HttpServletRequest>any(), Mockito.<HttpServletResponse>any());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/auth/refresh-token");
    MockMvcBuilders.standaloneSetup(authenticationController)
            .build()
            .perform(requestBuilder)
            .andExpect(MockMvcResultMatchers.status().isOk());
  }
}
