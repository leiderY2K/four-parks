package com.project.layer.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.layer.Services.Authentication.AuthResponse;
import com.project.layer.Services.Authentication.AuthService;
import com.project.layer.Services.Authentication.LoginRequest;
import com.project.layer.Services.Authentication.RegisterRequest;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Controller
public class AuthController {
    
    private final AuthService authService;

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        System.err.println(request.toString());
        return ResponseEntity.ok(authService.register(request));
    }

}
