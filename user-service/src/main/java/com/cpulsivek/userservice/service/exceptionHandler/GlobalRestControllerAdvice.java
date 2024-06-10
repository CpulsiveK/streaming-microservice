package com.cpulsivek.userservice.service.exceptionHandler;

import com.cpulsivek.userservice.dto.ExceptionDto;
import com.cpulsivek.userservice.exception.DuplicateException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalRestControllerAdvice {
  @ExceptionHandler(value = {DuplicateException.class})
  public ResponseEntity<ExceptionDto> handleDuplicateException(
      DuplicateException duplicateException, HttpServletRequest httpServletRequest) {
    return new ResponseEntity<>(
        new ExceptionDto(
            httpServletRequest.getRequestURI(),
            duplicateException.getMessage(),
            OffsetDateTime.now()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {AuthenticationException.class})
  public ResponseEntity<ExceptionDto> handleAuthenticationException(
      AuthenticationException authenticationException, HttpServletRequest httpServletRequest) {
    return new ResponseEntity<>(
        new ExceptionDto(
            httpServletRequest.getRequestURI(),
            authenticationException.getMessage(),
            OffsetDateTime.now()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {UsernameNotFoundException.class})
  public ResponseEntity<ExceptionDto> handleUsernameNotFoundException(
      UsernameNotFoundException usernameNotFoundException, HttpServletRequest httpServletRequest) {
    return new ResponseEntity<>(
        new ExceptionDto(
            httpServletRequest.getRequestURI(),
            usernameNotFoundException.getMessage(),
            OffsetDateTime.now()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {HttpMediaTypeNotAcceptableException.class})
  public ResponseEntity<ExceptionDto> handleHttpMediaTypeNotAcceptableException(
      HttpMediaTypeNotAcceptableException httpMediaTypeNotAcceptableException,
      HttpServletRequest httpServletRequest) {
    return new ResponseEntity<>(
        new ExceptionDto(
            httpServletRequest.getRequestURI(),
            httpMediaTypeNotAcceptableException.getMessage(),
            OffsetDateTime.now()),
        HttpStatus.BAD_REQUEST);
  }
}
