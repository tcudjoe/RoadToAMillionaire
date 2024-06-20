package hibera.api.rtmwebappapi.Auth;

import hibera.api.rtmwebappapi.Auth.service.JwtAuthenticationFilter;
import hibera.api.rtmwebappapi.Auth.service.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class JwtAuthenticationFilterTest {

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDoFilterInternal_ValidJwt() throws ServletException, IOException {
        String jwt = "validJwtToken";
        String username = "testUser";
        UserDetails userDetails = mock(UserDetails.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        when(jwtProvider.validateToken(jwt)).thenReturn(true);
        when(jwtProvider.getUsernameFromJwt(jwt)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        when(userDetails.getAuthorities()).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtProvider, times(1)).validateToken(jwt);
        verify(jwtProvider, times(1)).getUsernameFromJwt(jwt);
        verify(userDetailsService, times(1)).loadUserByUsername(username);
        verify(filterChain, times(1)).doFilter(request, response);

        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        assertEquals(userDetails, authentication.getPrincipal());
        assertNull(authentication.getCredentials());
        assertEquals(userDetails.getAuthorities(), authentication.getAuthorities());
    }

    @Test
    public void testDoFilterInternal_InvalidJwt() throws ServletException, IOException {
        String jwt = "invalidJwtToken";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwt);
        when(jwtProvider.validateToken(jwt)).thenReturn(false);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtProvider, times(1)).validateToken(jwt);
        verify(jwtProvider, never()).getUsernameFromJwt(any());
        verify(userDetailsService, never()).loadUserByUsername(any());
        verify(filterChain, times(1)).doFilter(request, response);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void testDoFilterInternal_NoJwt() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtProvider, never()).validateToken(any());
        verify(jwtProvider, never()).getUsernameFromJwt(any());
        verify(userDetailsService, never()).loadUserByUsername(any());
        verify(filterChain, times(1)).doFilter(request, response);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
