package com.recipeai.ingredientservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Getter
    public static class ApiError {
        private final String timestamp = LocalDateTime.now().toString();
        private final int status;
        private final String error;
        private final String message;
        private final String path;
        private final List<String> details;

        public ApiError(int status, String message, String path, List<String> details) {
            HttpStatus s = HttpStatus.resolve(status);
            this.status = status;
            this.error = s != null ? s.getReasonPhrase() : "Error";
            this.message = message;
            this.path = path;
            this.details = details;
        }
    }

    // Couvre ResponseStatusException (404, 409, 503...) et les erreurs Spring MVC
    @ExceptionHandler(ErrorResponseException.class)
    public ResponseEntity<ApiError> handleStatus(ErrorResponseException ex, HttpServletRequest req) {
        int code = ex.getStatusCode().value();
        String detail = ex.getBody().getDetail();
        return ResponseEntity.status(code)
                .body(new ApiError(code, detail != null ? detail : "Erreur", req.getRequestURI(), List.of()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<String> details = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + " : " + f.getDefaultMessage())
                .toList();
        return ResponseEntity.badRequest()
                .body(new ApiError(400, "Donnees invalides", req.getRequestURI(), details));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleUnreadable(HttpMessageNotReadableException ex, HttpServletRequest req) {
        return ResponseEntity.badRequest()
                .body(new ApiError(400, "Corps de requete illisible ou mal forme", req.getRequestURI(), List.of()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleOther(Exception ex, HttpServletRequest req) {
        return ResponseEntity.status(500)
                .body(new ApiError(500, "Erreur interne du serveur", req.getRequestURI(), List.of()));
    }
}
