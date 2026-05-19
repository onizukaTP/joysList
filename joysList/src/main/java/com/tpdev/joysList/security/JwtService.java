package com.tpdev.joysList.security;

import com.tpdev.joysList.entity.UserEntity;
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

        public String generateToken(UserEntity user) {
            return Jwts.builder()
                    .setSubject(user.getUsername())
                    .claim("role", user.getRole().name())
                    .setIssuedAt(new Date())
                    .setExpiration(
                            new Date(System.currentTimeMillis() + 1000 * 60 * 60)
                    ) // 1 hour
                    .signWith(getSigningKey())
                    .compact();
        }

        public String extractUsername(String token) {
            return extractAllClaims(token).getSubject();
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
