package com.project.layer.Services.Authentication;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.project.layer.Persistence.Entity.*;
import com.project.layer.Persistence.Repository.ICardRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.layer.Controllers.Requests.LoginRequest;
import com.project.layer.Controllers.Requests.RegisterRequest;
import com.project.layer.Controllers.Responses.AuthResponse;
import com.project.layer.Persistence.Repository.IUserAuthRepository;
import com.project.layer.Persistence.Repository.IUserRepository;
import com.project.layer.Services.JWT.JwtService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserRepository userRepository;
    private final IUserAuthRepository userAuthRepository;
    private final ICardRepository cardRepository;
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

    public AuthResponse register(RegisterRequest request) throws MessagingException {

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

        // Generar una contraseña aleatoria
        String randomPassword = generateRandomPassword();

        // Crear una instancia de UserAuthentication para autenticación
        UserAuthentication userAuthentication = UserAuthentication.builder()
            .userId(userId)
            .username(request.getUsername())
            .password(passwordEncoder.encode(randomPassword))
            .attempts(0)
            .isBlocked(false)
            .role(Role.valueOf(request.getRole()))
            .build();

        // Guardar el usuario en la base de datos
        userAuthRepository.save(userAuthentication);

        User cardOwner = userRepository.getReferenceById(userId);


        // Cambiar el formato de fecha para pasárselo a la tabla
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        LocalDate expiryDate = LocalDate.parse(request.getExpiryDate(), formatter);


        // Crear una instancia de Card
        Card newCard = Card.builder()
                .cardOwner(cardOwner)
                .serialCard(request.getSerialCard())
                .ExpDateCard(expiryDate)
                .cvvCard(request.getCvv())
                .build();

        //Guardar la tarjeta del usuario en la base de datos
        cardRepository.save(newCard);

        return AuthResponse.builder()
            .token(jwtService.getToken(null, userAuthentication))
            .contra(randomPassword) //es para iniciar sesion mientras
            .build();
    }

    private String generateRandomPassword() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(caracteres.length());
            sb.append(caracteres.charAt(index));
        }
        return sb.toString();
    }

}
