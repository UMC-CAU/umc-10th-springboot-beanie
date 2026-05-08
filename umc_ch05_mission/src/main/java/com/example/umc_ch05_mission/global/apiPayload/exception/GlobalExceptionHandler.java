package com.example.umc_ch05_mission.global.apiPayload.exception;

import com.example.umc_ch05_mission.global.apiPayload.ApiResponse;
import com.example.umc_ch05_mission.global.apiPayload.code.BaseErrorCode;
import com.example.umc_ch05_mission.global.apiPayload.code.GeneralErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProjectException.class)
    public ResponseEntity<ApiResponse<Object>> handleProjectException(ProjectException ex) {
        BaseErrorCode errorCode = ex.getErrorCode();
        return ResponseEntity.status(errorCode.GetStatus())
                .body(ApiResponse.onFailure(errorCode));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.status(GeneralErrorCode.BAD_REQUEST.GetStatus())
                .body(ApiResponse.onFailure(GeneralErrorCode.BAD_REQUEST, errorMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(GeneralErrorCode.INTERNAL_SERVER_ERROR.GetStatus())
                .body(ApiResponse.onFailure(GeneralErrorCode.INTERNAL_SERVER_ERROR));
    }
}

