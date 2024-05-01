package com.project.layer.Services.Authentication;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.layer.Controllers.Requests.LoginRequest;
import com.project.layer.Controllers.Requests.RegisterRequest;
import com.project.layer.Controllers.Responses.AuthResponse;
import com.project.layer.Persistence.Entity.Role;
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

    public AuthResponse login(LoginRequest request) {
        
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserAuthentication user = userAuthRepository.findByUsername(request.getUsername()).get();
        
        String token = jwtService.getToken(generateExtraClaims(user), user);
        return AuthResponse.builder()
            .token(token)
            .build();             
    }

    private Map<String, Object> generateExtraClaims(UserAuthentication user) {
        Map<String, Object> extraClaims = new HashMap<>();

        extraClaims.put("userId", user.getUserId());
        extraClaims.put("role", user.getRole());

        return extraClaims;
    }

    public AuthResponse register(RegisterRequest request) {

        UserId userId = UserId.builder()
            .idUser(request.getIdUser())
            .idDocType(request.getIdDocTypeFk())
            .build();
        
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
            .attempts(0)
            .isBlocked(false)
            .role(Role.valueOf(request.getRole()))
            .build();

        // Guardar el usuario en la base de datos
        userAuthRepository.save(userAuthentication);

        return AuthResponse.builder()
            .token(jwtService.getToken(null, userAuthentication))
            .build();
    }

}
