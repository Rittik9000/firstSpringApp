package com.example.myFirstApp.config;

import com.example.myFirstApp.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        
        String header = request.getHeader("Authorization");
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        
        System.out.println("Request: " + method + " " + requestURI);
        System.out.println("Auth header: " + (header != null ? "present" : "not present"));
        
        if (header == null || !header.startsWith("Bearer ")) {
            System.out.println("No valid Authorization header found");
            chain.doFilter(request, response);
            return;
        }
        
        try {
            String token = header.substring(7); // Remove "Bearer "
            System.out.println("Token validation attempt");
            
            if (jwtUtil.validateToken(token)) {
                String email = jwtUtil.extractEmail(token);
                String role = jwtUtil.extractRole(token);
                
                System.out.println("Token valid for email: " + email + ", role: " + role);
                
                UserDetails userDetails = User.builder()
                    .username(email)
                    .password("")
                    .authorities(new SimpleGrantedAuthority(role))
                    .build();
                
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
                
                SecurityContextHolder.getContext().setAuthentication(auth);
                System.out.println("Authentication set in SecurityContext");
            } else {
                System.out.println("Token validation failed");
            }
        } catch (Exception e) {
            System.out.println("Error in JWT filter: " + e.getMessage());
            e.printStackTrace();
        }
        
        chain.doFilter(request, response);
    }
}