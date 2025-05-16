package com.docker.backend.filter;

import com.docker.backend.constant.ApplicationConstants;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenValidatorFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(ApplicationConstants.JWT_HEADER);
        if (token != null) {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7); // "Banner " 접두어 제거
            }

            if (!token.isBlank()) {
                try {
                    Authentication authentication = jwtService.validateToken(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (JwtException ex) {
                    throw new BadCredentialsException("Invalid JWT token", ex);
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/auth/");
    }

}