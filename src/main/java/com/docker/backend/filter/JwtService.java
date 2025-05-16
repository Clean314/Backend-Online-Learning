package com.docker.backend.filter;

import com.docker.backend.constant.ApplicationConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtService {

    private final Environment env;

    private SecretKey getSigningKey() {
        String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY,
                ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Authentication authentication) {
        return Jwts.builder()
                .issuer("online-learning")
                .subject("JWT Token")
                .claim("username", authentication.getName())
                .claim("authorities", authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 30000000)) // TODO: yml 분리
                .signWith(getSigningKey())
                .compact();
    }

    public Authentication validateToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String username = claims.get("username", String.class);
        String authorities = claims.get("authorities", String.class);
        return new UsernamePasswordAuthenticationToken(username, null,
                AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
    }
}
