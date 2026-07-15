package com.tpdev.joysList.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
        @Value("${jwt.secret}")
        private String secretKey;



        public String extractUsername(String token) {
            return extractAllClaims(token).getSubject();
        }

        public Long extractUserId(String token) {
            return extractClaim(token, claims -> claims.get("userId", Long.class));
        }

        public String extractRole(String token) {
            return extractClaim(token, claims -> claims.get("role", String.class));
        }

        public boolean validateToken(String token, UserDetails userDetails) {
            try {
                String username = extractUsername(token);

                return username.equals(userDetails.getUsername())
                        && !isTokenExpired(token);
            } catch (JwtException | IllegalArgumentException e) {
                return false;
            }
        }

        // Helpers

        private boolean isTokenExpired(String token) {
            return extractExpiration(token).before(new Date());
        }

        private Date extractExpiration(String token) {
            return extractClaim(token, Claims::getExpiration);
        }

        private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        }

        private Claims extractAllClaims(String token) {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }

        private Key getSigningKey() {
            byte[] keyBytes = Decoders.BASE64.decode(secretKey);
            return Keys.hmacShaKeyFor(keyBytes);
        }
}
