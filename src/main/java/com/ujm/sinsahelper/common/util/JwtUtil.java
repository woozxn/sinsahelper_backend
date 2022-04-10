package com.ujm.sinsahelper.common.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;

public class JwtUtil {

    public static final String TOKEN_TYPE = "Bearer";
    @Value("${jwt.key}")
    private String jwtKey;

    public String parseJwtToken(String token) {

        validateJwtToken(token);

        return Jwts.parser()
                .setSigningKey(jwtKey)
                .parseClaimsJwt(token)
                .getBody()
                .getSubject();
    }

    public JwtTokenDTO generateToken(String userEmail) {
        return JwtTokenDTO.builder()
                .tokenType(TOKEN_TYPE)
                .accessToken(Jwts.builder()
                        .signWith(SignatureAlgorithm.HS256, jwtKey)
                        .setSubject(userEmail)
                        .compact())
                .refreshToken(null)
                .build();
    }

    public void validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token);
        } catch (MalformedJwtException e) {
            throw new MalformedJwtException("MalformedJwtException!!");
        } catch (ExpiredJwtException e) {
            throw new RuntimeException();
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException("UnsupportedJwtException!!");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
    }
}
