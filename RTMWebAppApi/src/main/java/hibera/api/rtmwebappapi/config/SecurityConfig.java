package hibera.api.rtmwebappapi.config;

import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static hibera.api.rtmwebappapi.domain.Permissions.*;
import static hibera.api.rtmwebappapi.domain.Role.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig extends AbstractHttpConfigurer<SecurityConfig, HttpSecurity> {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((req) ->
                                req.requestMatchers(mvcMatcherBuilder.pattern("/api/v1/auth/**"), mvcMatcherBuilder.pattern("/oauth2/**"))
                                        .permitAll()

                                        .requestMatchers(mvcMatcherBuilder.pattern("/api/v1/admin/**")).hasRole(ADMIN.name())
                                        .requestMatchers(mvcMatcherBuilder.pattern(GET, "/api/v1/admin/**")).hasAuthority(ADMIN_READ.name())
                                        .requestMatchers(mvcMatcherBuilder.pattern(POST, "/api/v1/admin/**")).hasAuthority(ADMIN_CREATE.name())
                                        .requestMatchers(mvcMatcherBuilder.pattern(PUT, "/api/v1/admin/**")).hasAuthority(ADMIN_UPDATE.name())
                                        .requestMatchers(mvcMatcherBuilder.pattern(DELETE, "/api/v1/admin/**")).hasAuthority(ADMIN_DELETE.name())

                                        .requestMatchers(mvcMatcherBuilder.pattern("/api/v1/management/**")).hasAnyRole(ADMIN.name(), MANAGER.name())
                                        .requestMatchers(mvcMatcherBuilder.pattern(GET, "/api/v1/management/**")).hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                                        .requestMatchers(mvcMatcherBuilder.pattern(POST, "/api/v1/management/**")).hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                                        .requestMatchers(mvcMatcherBuilder.pattern(PUT, "/api/v1/management/**")).hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                                        .requestMatchers(mvcMatcherBuilder.pattern(DELETE, "/api/v1/management/**")).hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())

                                        .requestMatchers(mvcMatcherBuilder.pattern("/api/v1/user/**")).hasAnyRole(ADMIN.name(), MANAGER.name(), USER.name())
                                        .requestMatchers(mvcMatcherBuilder.pattern(GET, "/api/v1/user/**")).hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name(), USER_READ.name())
                                        .requestMatchers(mvcMatcherBuilder.pattern(POST, "/api/v1/user/**")).hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name(), USER_CREATE.name())
                                        .requestMatchers(mvcMatcherBuilder.pattern(PUT, "/api/v1/user/**")).hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name(), USER_UPDATE.name())
                                        .requestMatchers(mvcMatcherBuilder.pattern(DELETE, "/api/v1/user/**")).hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name(), USER_DELETE.name())

                                        .anyRequest()
                                        .authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler(
                                        (request, response, authentication) ->
                                                SecurityContextHolder.clearContext()
                                )
                )
                .oauth2Login(AbstractHttpConfigurer::disable)
        ;

        return http.build();
    }
}
