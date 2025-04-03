package com.jendykiewicz.zadanie.controller;

import com.jendykiewicz.zadanie.client.GithubClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

//globalny przechwyt błędów z kontrolerów
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<Map<String, Object>> handleGithubUserNotFound(GithubClient.GithubUserNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "status", HttpStatus.NOT_FOUND.value(),
                        "message", ex.getMessage()
                ));
    }
}
