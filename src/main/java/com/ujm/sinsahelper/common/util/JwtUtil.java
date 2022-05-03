package com.ujm.sinsahelper.common.util;

import com.ujm.sinsahelper.domain.AuthRole;
import com.ujm.sinsahelper.domain.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;

@Slf4j
public class JwtUtil {

    private static final String TOKEN_TYPE = "Bearer";
    private static final String ROLE_KEY = "Role";

    @Value("${jwt.key}")
    private String jwtKey;

    public Authentication getAuthentication(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(jwtKey)
                .parseClaimsJws(token)
                .getBody();

        String userEmail = claims.getSubject();
        String authRole = claims.get(ROLE_KEY, String.class);

        return new UsernamePasswordAuthenticationToken(userEmail, "", Collections.singleton(new SimpleGrantedAuthority(authRole)));
    }

    public JwtTokenDTO generateToken(Member member) {

        return JwtTokenDTO.builder()
                .tokenType(TOKEN_TYPE)
                .accessToken(Jwts.builder()
                        .signWith(SignatureAlgorithm.HS256, jwtKey)
                        .setSubject(member.getEmail())
                        .claim(ROLE_KEY, member.getAuthRole().name())
                        .setExpiration(new Date((new Date()).getTime() + 1000 * 60 * 60))
                        .compact())
                .refreshToken(null)
                .build();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token);
            return true;
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
