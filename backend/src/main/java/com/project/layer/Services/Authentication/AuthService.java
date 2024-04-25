package com.project.layer.Services.Authentication;

import org.springframework.stereotype.Service;

import com.project.layer.Persistence.Entity.Role;
import com.project.layer.Persistence.Entity.User;
import com.project.layer.Persistence.Entity.UserAuthentication;
import com.project.layer.Persistence.Entity.UserId;
import com.project.layer.Persistence.Repository.IUserAuthRepository;
import com.project.layer.Persistence.Repository.IUserRepository;
import com.project.layer.Services.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserRepository userRepository;
    private final IUserAuthRepository userAuthRepository;

    private final JwtService jwtService;

    public AuthResponse login(LoginRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
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
            .password(request.getPassword())
            .role(Role.CLIENT)
            .build();

        // Guardar el usuario en la base de datos
        userAuthRepository.save(userAuthentication);

        return AuthResponse.builder()
            .token(jwtService.getToken(userAuthentication))
            .build();
    }

}
