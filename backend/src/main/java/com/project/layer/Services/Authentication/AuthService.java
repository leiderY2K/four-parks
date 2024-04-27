package com.project.layer.Services.Authentication;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.layer.Persistence.Entity.User;
import com.project.layer.Persistence.Entity.UserAuthentication;
import com.project.layer.Persistence.Entity.UserId;
import com.project.layer.Persistence.Repository.IUserAuthRepository;
import com.project.layer.Persistence.Repository.IUserRepository;
import com.project.layer.Services.JWT.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserRepository userRepository;
    private final IUserAuthRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthLoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userAuthRepository.findByUsername(request.getUsername()).orElseThrow();
        
        UserId userId = ((UserAuthentication) user).getUserId();
        String role = ((UserAuthentication) user).getRole();
        String token = jwtService.getToken(user);
        return AuthLoginResponse.builder()
            .userId(userId)
            .role(role)
            .token(token)
            .build();             
    }

    public AuthResponse register(RegisterRequest request) {

        UserId userId = UserId.builder()
            .idUser(request.getIdUser())
            .idDocTypeFk(request.getIdDocTypeFk())
            .build();

        System.err.println(userId.toString());
        
        // Crear una instancia de User
        User newUser = User.builder()
            .userId(userId)
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .phone(request.getPhone())
            .build(); 
      
        // Guardar el usuario en la base de datos
        userRepository.save(newUser);

        // Crear una instancia de UserAuthentication para autenticación
        UserAuthentication userAuthentication = UserAuthentication.builder()
            .userId(userId)
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(request.getRole())
            .build();

        // Guardar el usuario en la base de datos
        userAuthRepository.save(userAuthentication);

        return AuthResponse.builder()
            .token(jwtService.getToken(userAuthentication))
            .build();
    }

}
