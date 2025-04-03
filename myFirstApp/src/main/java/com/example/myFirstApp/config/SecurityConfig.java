package com.example.myFirstApp.config;
import java.util.Arrays;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.myFirstApp.util.JwtUtil;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final Logger logger = Logger.getLogger(SecurityConfig.class.getName());
    
    @Autowired
    private JwtUtil jwtUtil;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring security filter chain");
        
        http
            // Replace deprecated cors() with corsConfigurationSource directly
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Public endpoints
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers("/users/auth/login").permitAll()
                .requestMatchers("/test/public").permitAll()
                .requestMatchers("/error").permitAll()
                
                // Test endpoints
                .requestMatchers("/test/auth").authenticated()
                
                // Protected endpoints with role-based access
                .requestMatchers("/users/**").hasAnyAuthority("USER", "ADMIN")
                
                // Default rule
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    logger.warning("Access denied: " + accessDeniedException.getMessage() + 
                                   " for path: " + request.getRequestURI());
                    response.setStatus(403);
                    response.setContentType("application/json");
                    response.getWriter().write(
                        "{\"error\": \"Forbidden\", \"message\": \"" + accessDeniedException.getMessage() + "\"}"
                    );
                })
                .authenticationEntryPoint((request, response, authException) -> {
                    logger.warning("Auth failed: " + authException.getMessage() + 
                                   " for path: " + request.getRequestURI());
                    response.setStatus(401);
                    response.setHeader("WWW-Authenticate", "Bearer realm=\"example\"");
                    response.setContentType("application/json");
                    response.getWriter().write(
                        "{\"error\": \"Unauthorized\", \"message\": \"Full authentication is required to access this resource\"}"
                    );
                })
            )
            .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}