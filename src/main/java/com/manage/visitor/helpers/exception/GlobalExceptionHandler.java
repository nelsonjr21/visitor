package com.manage.visitor.helpers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.manage.visitor.helpers.http.ApiResponse;
import com.manage.visitor.helpers.http.ErrorCode;

import lombok.extern.slf4j.Slf4j;

/*
 * centralize exception handling, data binding, and model enhancement
 * catch exceptions thrown anywhere in REST controllers and return a clean
 * JSON response.
 * It avoids to write the same try/catch or @ExceptionHandler in each
 * controller.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ApiResponse> handleBadRequestException(CommonException e) {
    HttpStatus status = mapStatus(e.getCode());

    log.error(
        "Handled BadRequestException code={} status={} message={}",
        e.getCode(),
        status.value(),
        e.getMessage());

    ApiResponse<Void> body =
        new ApiResponse<>(Integer.toString(status.value()), e.getMessage(), null);

    return ResponseEntity.status(HttpStatus.OK).body(body);
  }

  @ExceptionHandler(DataNotFoundException.class)
  public ResponseEntity<ApiResponse> handleDataNotFoundException(CommonException e) {
    HttpStatus status = mapStatus(e.getCode());

    log.error(
        "Handled DataNotFoundException code={} status={} message={}",
        e.getCode(),
        status.value(),
        e.getMessage());

    ApiResponse<Void> body =
        new ApiResponse<>(Integer.toString(status.value()), e.getMessage(), null);

    return ResponseEntity.status(HttpStatus.OK).body(body);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ApiResponse> handleBadCredentialsException(BadCredentialsException e) {
    HttpStatus status = mapStatus(ErrorCode.UNAUTHORIZED);

    log.error(
        "Handled BadCredentialsException code={} status={} message={}",
        ErrorCode.UNAUTHORIZED,
        status.value(),
        e.getMessage());

    ApiResponse<Void> body =
        new ApiResponse<>(Integer.toString(status.value()), e.getMessage(), null);

    return ResponseEntity.status(HttpStatus.OK).body(body);
  }

  @ExceptionHandler(AppException.class)
  public ResponseEntity<ApiResponse> handleAppException(CommonException e) {
    HttpStatus status = mapStatus(e.getCode());

    log.error(
        "Handled AppException code={} status={} message={}",
        e.getCode(),
        status.value(),
        e.getMessage());

    ApiResponse<Void> body =
        new ApiResponse<>(Integer.toString(status.value()), e.getMessage(), null);

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
  }

  private HttpStatus mapStatus(ErrorCode code) {
    return switch (code) {
      case VALIDATION_ERROR -> HttpStatus.BAD_REQUEST;
      case NOT_FOUND -> HttpStatus.NOT_FOUND;
      case CONFLICT -> HttpStatus.CONFLICT;
      case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
      case INTERNAL_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
      case BAD_REQUEST -> HttpStatus.BAD_REQUEST;
    };
  }
}
