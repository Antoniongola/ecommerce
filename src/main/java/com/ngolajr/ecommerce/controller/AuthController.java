package com.ngolajr.ecommerce.controller;

import com.ngolajr.ecommerce.model.dto.AuthResponse;
import com.ngolajr.ecommerce.model.dto.LoginDto;
import com.ngolajr.ecommerce.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody LoginDto auth){
        if(authService.login(auth) != null)
            return ResponseEntity.ok(authService.login(auth));

        return ResponseEntity.ofNullable(null);
    }
}
