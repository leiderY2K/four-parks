package com.project.layer.Controllers;

import com.project.layer.Persistence.Entity.Role;
import com.project.layer.Persistence.Entity.UserAuthentication;
import com.project.layer.Persistence.Error.CustomException;
import com.project.layer.Persistence.Error.UserBlockedException;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.project.layer.Controllers.Requests.LoginRequest;
import com.project.layer.Controllers.Requests.PassRecoveryRequest;
import com.project.layer.Controllers.Requests.PassRequest;
import com.project.layer.Controllers.Requests.RegisterRequest;
import com.project.layer.Controllers.Requests.UserRequest;
import com.project.layer.Controllers.Requests.UnlockRequest;
import com.project.layer.Controllers.Responses.AuthResponse;
import com.project.layer.Controllers.Responses.UserResponse;
import com.project.layer.Services.Audit.AuditService;
import com.project.layer.Services.Authentication.AuthService;
import com.project.layer.Services.IpRequest.RequestService;
import com.project.layer.Services.JWT.JwtService;
import com.project.layer.Services.Mail.MailService;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Arrays;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;
    private final MailService mailservice;
    private final JwtService jwtService;
    private final AuditService auditService;
    private final RequestService requestService;

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request, HttpServletRequest ipUser) {
        try {
            // Se almacena la accion del usuario
            auditService.setAction(
                    authService.getUserAction(request.getUsername(), "Ingreso",
                            requestService.getClientIp(ipUser)));

            return ResponseEntity.ok(authService.login(request));
        } catch (BadCredentialsException e) {
            // Incrementar intentos fallidos
            try {
                // se almacena la acción de bloqueo de usuario revisar con cristian
                auditService
                        .setAction(authService.getUserAction(request.getUsername(),
                                "Usuario Bloqueado",
                                requestService.getClientIp(ipUser)));

                authService.incrementFailedAttempts(request.getUsername());
            } catch (UserBlockedException ex) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new AuthResponse(null, null, ex.getMessage()));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(null, null, "Credenciales incorrectas"));
        }
    }

    @PostMapping(value = "unlock")
    public ResponseEntity<AuthResponse> unlock(@RequestBody UnlockRequest request) {
        try {
            return ResponseEntity.ok(authService.unlock(request));
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(null, null, "No se pudo enviar correo"));
        }
    }

    @PostMapping(value = "pass-recovery")
    public ResponseEntity<AuthResponse> passRecovery(@RequestBody PassRecoveryRequest request) {
        try {
            AuthResponse recoveryPass = authService.passRecovery(request);

            System.out.println(recoveryPass.getContra());
            List<String> messages = Arrays.asList("PasswordChange", recoveryPass.getContra());
            mailservice.sendMail(request.getEmail(), "[Four-parks] Se ha iniciado una solicitud de contraseña",
                    messages);
            return ResponseEntity.ok(recoveryPass);
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(null, null, "No se pudo enviar correo"));
        }
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Validated RegisterRequest request,
            BindingResult bindingResult,
            HttpServletRequest ipUser) throws MessagingException, CustomException {

        String role = null;

        String token = jwtService.getTokenFromRequest(
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());

        if (token != null)
            role = jwtService.getClaim(token, claims -> claims.get("role", String.class));

        if (role == null)
            role = Role.CLIENT.name();

        // Verificar los roles
        if (!((role.equals(Role.CLIENT.name()) && request.getRole().equals(Role.CLIENT.name())) ||
                (role.equals(Role.ADMIN.name()) && request.getRole().equals(Role.CLIENT.name())) ||
                (role.equals(Role.MANAGER.name()) && request.getRole().equals(Role.ADMIN.name())))) {
            return new ResponseEntity<>(new AuthResponse(null, null, "Su rol no puede realizar esta acción"),
                    HttpStatus.BAD_REQUEST);
        }

        AuthResponse response = authService.register(request);

        List<String> messages = Arrays.asList("Register", response.getContra());
        mailservice.sendMail(request.getEmail(), "Bienvenido a four-parks Colombia", messages);

        // se establece la acción del usuario por el registro
        auditService.setAction(authService.getUserAction(request.getUsername(),
                "Registro",
                requestService.getClientIp(ipUser)));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "update")
    public ResponseEntity<UserResponse> update(@RequestBody @Validated UserRequest request) {

        String token = jwtService.getTokenFromRequest(
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());
        String role = jwtService.getClaim(token, claims -> claims.get("role", String.class));

        if (!role.equals(request.getRole())) {
            return new ResponseEntity<>(new UserResponse(null, null, "Los roles están mal"), HttpStatus.BAD_REQUEST);
        }

        UserResponse response = authService.update(request);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "change-pass")
    public ResponseEntity<AuthResponse> changePass(@RequestBody PassRequest request, HttpServletRequest ipUser) {
        try {
            
            //Se establece la acción de cambio de contraseña
            auditService.setAction(authService.getUserAction(request.getUsername(), 
            "Cambio de contraseña",
            requestService.getClientIp(ipUser)));

            return ResponseEntity.ok(authService.changePass(request));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse(null, null, "Credenciales incorrectas"));
        }

        
    }


    @GetMapping(value = "/get-auth-users")
        public List<UserAuthentication> getAuthUsers(){
        return authService.getAuthUsers();
    }
}
