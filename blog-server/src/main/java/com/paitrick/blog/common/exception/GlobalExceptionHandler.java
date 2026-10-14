package com.paitrick.blog.common.exception;

import com.paitrick.blog.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(BusinessException.class)
  ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException exception) {
    return ResponseEntity.badRequest().body(new ApiResponse<>(400, exception.getMessage(), null));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException exception) {
    String message = exception.getBindingResult().getFieldError().getDefaultMessage();
    return ResponseEntity.badRequest().body(new ApiResponse<>(400, message, null));
  }

  @ExceptionHandler(Exception.class)
  ResponseEntity<ApiResponse<Void>> handleUnexpectedException(Exception exception) {
    return ResponseEntity.internalServerError().body(new ApiResponse<>(500, "internal server error", null));
  }
}
