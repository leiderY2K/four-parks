package com.project.layer.Controllers;

import com.project.layer.Persistence.Error.CustomException;
import com.project.layer.Persistence.Error.UserBlockedException;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.layer.Controllers.Requests.LoginRequest;
import com.project.layer.Controllers.Requests.PassRequest;
import com.project.layer.Controllers.Requests.RegisterRequest;
import com.project.layer.Controllers.Requests.UnlockRequest;
import com.project.layer.Controllers.Responses.AuthResponse;
import com.project.layer.Services.Authentication.AuthService;
import com.project.layer.Services.Mail.MailService;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;
    private final MailService mailservice;

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (BadCredentialsException e) {
            // Incrementar intentos fallidos
            try {
                authService.incrementFailedAttempts(request.getUsername());
            } catch (UserBlockedException ex) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new AuthResponse(ex.getMessage(), null));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("Credenciales incorrectas", null));
        }
    }

    @PostMapping(value = "unlock")
    public ResponseEntity<AuthResponse> unlock(@RequestBody UnlockRequest request) {
        try {
            return ResponseEntity.ok(authService.unlock(request));
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("No se pudo enviar correo", null));
        }
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest request,
            BindingResult bindingResult) throws MessagingException, CustomException {

        authService.verifyRegisterRequest(request);

        AuthResponse respuesta = authService.register(request);

        List<String> messages = Arrays.asList("Register", respuesta.getContra());
        mailservice.sendMail(request.getEmail(), "Bienvenido a four-parks Colombia", messages);
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping(value = "change-pass")
    public ResponseEntity<AuthResponse> changePass(@RequestBody PassRequest request) {
        try {
        return ResponseEntity.ok(authService.changePass(request));
    } catch (BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse("Credenciales incorrectas", null));
    }
    }

}
