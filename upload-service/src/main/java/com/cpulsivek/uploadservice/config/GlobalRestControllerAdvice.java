package com.cpulsivek.uploadservice.config;

import com.cpulsivek.uploadservice.dto.ExceptionDto;
import com.cpulsivek.uploadservice.exception.DuplicateException;
import com.cpulsivek.uploadservice.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @ExceptionHandler(value = {UnauthorizedException.class})
  public ResponseEntity<ExceptionDto> handleUnauthorizedException(
      UnauthorizedException unauthorizedException, HttpServletRequest httpServletRequest) {
    return new ResponseEntity<>(
        new ExceptionDto(
            httpServletRequest.getRequestURI(),
            unauthorizedException.getMessage(),
            OffsetDateTime.now()),
        HttpStatus.FORBIDDEN);
  }
}
