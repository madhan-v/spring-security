package com.arc.filters;

import com.arc.service.CustomUserDetailsService;
import com.arc.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Get the Authorization header from the request
        String authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);
        String token = null;
        String username = null;

        // Check if the Authorization header is present and starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // Extract the token from the header
            // Here you would typically extract the username from the token using a JWT utility class
            username = jwtUtil.extractUsername(token);
        }

        // If a username was extracted and there is no authentication in the security context, proceed with authentication
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details from your user service or repository
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            // Validate the token and set authentication in the security context if valid
            // For example:
            if (jwtUtil.validateToken(token, userDetails)) {
                 UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                         userDetails, null, userDetails.getAuthorities());
                 authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                 SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response); // Continue with the filter chain
    }
}
