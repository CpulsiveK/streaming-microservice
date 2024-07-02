package com.cpulsivek.uploadservice.service.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

@Service
public class JwtAuthFilter extends OncePerRequestFilter {
  private final Jwt jwt;

  @Autowired
  public JwtAuthFilter(Jwt jwt) {
    this.jwt = jwt;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      FilterChain filterChain)
      throws ServletException, IOException {
    if (httpServletRequest.getRequestURI().equals("/actuator/prometheus")) {
      filterChain.doFilter(httpServletRequest, httpServletResponse);
      return;
    }

    String authHeader = httpServletRequest.getHeader("Authorization");
    String token = null;

    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      token = authHeader.substring(7);
    }

    if (Boolean.FALSE.equals(jwt.validateToken(token)))
      throw new AccessDeniedException("Access denied asshole");

    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
}
