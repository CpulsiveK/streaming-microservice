package com.cpulsivek.uploadservice.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtImpl implements Jwt {
  private final Environment env;

  @Autowired
  public JwtImpl(Environment env) {
    this.env = env;
  }

  @Override
  public String extractEmail(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  @Override
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  @Override
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  @Override
  public Claims extractAllClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
  }

  @Override
  public Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  @Override
  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractEmail(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  @Override
  public String generateToken(String username) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, username);
  }

  @Override
  public String createToken(Map<String, Object> claims, String username) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
        .signWith(getSignKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  @Override
  public Key getSignKey() {
    byte[] keyBytes = Decoders.BASE64.decode(env.getProperty("jwt_secret"));
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
