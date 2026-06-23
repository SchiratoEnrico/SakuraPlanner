package com.sakuraplanner.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter that intercepts every incoming HTTP request to check for a valid JWT in the headers.
 * It extends OncePerRequestFilter to guarantee a single execution per request dispatch.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Extract the Authorization header
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 2. Check if the header exists and contains a Bearer token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // No token found: pass to the next filter
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extract the token string (skip the first 7 characters: "Bearer ")
        jwt = authHeader.substring(7);

        // 4. Extract the user email (subject claim) from the token
        userEmail = jwtService.extractUsername(jwt);

        // 5. If we have an email and the user is NOT already authenticated in the current security context
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // Fetch user details from the database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // 6. Validate the token against the user details
            if (jwtService.isTokenValid(jwt, userDetails)) {
                
                // 7. Token is valid Create an authentication object
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                
                // Attach details regarding the original HTTP request (e.g., IP address, session ID)
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                
                // 8. Update the Security Context. Now Spring knows this user is authenticated for this request.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        // 9. Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}