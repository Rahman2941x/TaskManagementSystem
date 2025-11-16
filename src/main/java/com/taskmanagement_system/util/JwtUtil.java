package com.taskmanagement_system.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final long EXPIRATION_TOKE=1000 *60*30;
        private final String SECRET="My-Super-Secret-Key-That-Is-AtLeast-32Bytes-Long-123455678866";
        private final SecretKey key= Keys.hmacShaKeyFor(SECRET.getBytes());



    public String generateToken(String userName){
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TOKE))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUserName(String token) {
         return  extractClaims(token).getSubject();
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String userName, UserDetails userDetails,String token) {
       return (userName.equals(userDetails.getUsername()) && !isTokeExpired(token));

    }

    private boolean isTokeExpired(String token) {
         return  extractClaims(token).getExpiration().before(new Date());
    }
    }

