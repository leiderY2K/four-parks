package com.project.layer.Services.JWT;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.layer.Persistence.Entity.UserId;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtService {

    @Value("${jwt.secret.expiration-minutes}")
    private long EXPIRATION_MINUTES;

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    public String getToken(Map<String, Object> extraClaims, UserDetails user) {
        return Jwts
            .builder()
            .claims(extraClaims)
            .subject(user.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis()+1000*EXPIRATION_MINUTES*24))
            .signWith(getKey())
            .compact();
    }


    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

	public String getUserNameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
	}

    public String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")){
            return authHeader.substring(7);
        }

        return null;
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUserNameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims(String token){
        return Jwts
            .parser()
            .verifyWith(getKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public <T> T getClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Date getExpiration(String token){
        return getClaim(token,Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }

    public UserId getUserIdFromToken(String token){
        
        @SuppressWarnings("unchecked")
        Map<String, Object> userIdMap = getClaim(token, claims -> claims.get("userId", Map.class));
        UserId userId = null;
        if (userIdMap != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            userId = objectMapper.convertValue(userIdMap, UserId.class);
        }
        return userId;
    }
    

}
