package com.project.layer.Controllers;

import com.project.layer.Persistence.Error.CustomException;
import com.project.layer.Persistence.Repository.IUserAuthRepository;
import com.project.layer.Persistence.Repository.IUserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.layer.Controllers.Requests.LoginRequest;
import com.project.layer.Controllers.Requests.RegisterRequest;
import com.project.layer.Controllers.Responses.AuthResponse;
import com.project.layer.Services.Authentication.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Controller
public class AuthController {
    
    private final AuthService authService;
    private final IUserRepository userRepository;
    private final IUserAuthRepository userAuthRepository;

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest request) throws CustomException {

        String tempEmail = request.getEmail();
        String tempUserId = request.getIdUser();
        String tempPhone = request.getPhone();
        String tempUsername = request.getUsername();

        if(request.hasEmptyParameters()){
            throw new CustomException(("Todos los campos son obligatorios"));
        }
        //if(request.isEmail(tempEmail)){
        //    throw new CustomException(("El email es invalido"));
        //}
        if(userRepository.findByEmail(tempEmail).isPresent()) {
            throw new CustomException(("El email ya se encuentra registrado"));
        }if(userAuthRepository.findByUsername(tempUserId).isPresent()){
            throw new CustomException(("El  id usuario ya se encuentra registrado"));
        }if(userRepository.findByPhone(tempPhone).isPresent()){
            throw new CustomException(("El  numero de telefono ya se encuentra registrado"));
        }if(userAuthRepository.findByUsername(tempUsername).isPresent()){
            throw new CustomException(("El  nombre de usuario ya se encuentra registrado"));
        }
        return ResponseEntity.ok(authService.register(request));
    }

}
