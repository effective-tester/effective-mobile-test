package ru.effective.tms.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.effective.tms.backend.annotation.log.LogExecution;
import ru.effective.tms.backend.config.JwtConfiguration;
import ru.effective.tms.backend.exception.security.InvalidJwtTokenException;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtConfiguration jwtConfig;

    private final Key key;

    public String generateToken(Authentication authentication) {
        String email = authentication.getName();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtConfig.getExpiration());
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserEmailFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    @LogExecution
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (SecurityException ex) {
            throw new InvalidJwtTokenException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new InvalidJwtTokenException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new InvalidJwtTokenException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new InvalidJwtTokenException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new InvalidJwtTokenException("JWT claims string is empty");
        }
    }
}
