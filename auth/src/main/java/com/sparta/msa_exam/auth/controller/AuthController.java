package com.sparta.msa_exam.auth.controller;

import com.sparta.msa_exam.auth.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @GetMapping("/signIn")
    public ResponseEntity<?> createAuthToken(@RequestParam String userId) {
        return ResponseEntity.ok(new AuthResponse(authService.createAccessToken(userId)));
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    static class AuthResponse {
        private String accessToken;

    }
}
