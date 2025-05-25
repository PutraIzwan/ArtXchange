package com.artxchange.artxchange.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class FirebaseAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Check both header and parameter for token
        String idToken = request.getParameter("idToken");
        if (idToken == null) {
            idToken = request.getHeader("Authorization");
        }

        if (idToken != null && !idToken.isEmpty()) {
            try {
                FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);

                // Create authentication and set security context
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                decodedToken.getEmail(),
                                null,
                                null
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Continue with the filter chain
                filterChain.doFilter(request, response);
                return;

            } catch (Exception e) {
                logger.error("Firebase token verification failed", e);
            }
        }

        // If no valid token, continue chain (will be caught by security config)
        filterChain.doFilter(request, response);
    }
}