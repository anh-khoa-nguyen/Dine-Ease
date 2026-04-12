package com.dineease.exception;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dineease.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<ApiError.FieldErrorDetail> details = ex.getBindingResult().getFieldErrors().stream()
            .map(this::toFieldError)
            .collect(Collectors.toList());

        ApiError body = new ApiError("Bad Request", "Dữ liệu đầu vào không hợp lệ", details, request.getRequestURI(), Instant.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        ApiError body = new ApiError("Not Found", ex.getMessage(), null, request.getRequestURI(), Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiError> handleConflict(DuplicateResourceException ex, HttpServletRequest request) {
        ApiError body = new ApiError("Conflict", ex.getMessage(), null, request.getRequestURI(), Instant.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {
        ApiError body = new ApiError("Unauthorized", "Email hoặc mật khẩu không đúng", null, request.getRequestURI(), Instant.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthentication(AuthenticationException ex, HttpServletRequest request) {
        ApiError body = new ApiError("Unauthorized", ex.getMessage(), null, request.getRequestURI(), Instant.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        ApiError body = new ApiError("Forbidden", "Bạn không có quyền truy cập tài nguyên này", null, request.getRequestURI(), Instant.now());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobalException(Exception ex, HttpServletRequest request) {
        log.error("Unhandled error: ", ex);
        ApiError body = new ApiError("Internal Server Error", "Đã xảy ra lỗi hệ thống. Vui lòng thử lại sau.", null, request.getRequestURI(), Instant.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    private ApiError.FieldErrorDetail toFieldError(FieldError fe) {
        return new ApiError.FieldErrorDetail(fe.getField(), fe.getDefaultMessage() != null ? fe.getDefaultMessage() : "Invalid");
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex, HttpServletRequest request) {
        ApiError body = new ApiError("Bad Request", ex.getMessage(), null, request.getRequestURI(), Instant.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiError> handleIllegalState(IllegalStateException ex, HttpServletRequest request) {
        ApiError body = new ApiError("Bad Request", ex.getMessage(), null, request.getRequestURI(), Instant.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body); // Trả về 400
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        ApiError body = new ApiError(
            "Bad Request", 
            ex.getMessage(), 
            null, 
            request.getRequestURI(), 
            Instant.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
}