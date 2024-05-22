package com.project.layer.Services.Authentication;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.project.layer.Persistence.Entity.*;
import com.project.layer.Persistence.Error.CustomException;
import com.project.layer.Persistence.Error.UserBlockedException;
import com.project.layer.Persistence.Repository.ICardRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.layer.Controllers.Requests.LoginRequest;
import com.project.layer.Controllers.Requests.RegisterRequest;
import com.project.layer.Controllers.Requests.UnlockRequest;
import com.project.layer.Controllers.Responses.AuthResponse;
import com.project.layer.Persistence.Repository.IUserAuthRepository;
import com.project.layer.Persistence.Repository.IUserRepository;
import com.project.layer.Services.JWT.JwtService;
import com.project.layer.Services.Mail.MailService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserRepository userRepository;
    private final IUserAuthRepository userAuthRepository;
    private final ICardRepository cardRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final MailService mailservice;

    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse login(LoginRequest request) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException e) {
            // Lanzar la excepción para que sea capturada en el controlador
            throw new BadCredentialsException("Credenciales incorrectas", e);
        }
    
        // Si la autenticación es exitosa, restablecer los intentos fallidos
        UserAuthentication user = userAuthRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        resetFailedAttempts(user.getUsername());
    
        String token = jwtService.getToken(generateExtraClaims(user), user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    @Transactional
    public AuthResponse unlock(UnlockRequest request) throws MessagingException {
        UserAuthentication userAuth = userAuthRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        if(userAuth.getUsername().equals(request.getUsername())&&userAuth.getUserId().getIdUser().equals(request.getIdUser())){
            resetFailedAttempts(userAuth.getUsername());
            unBlockPassword(userAuth.getUsername());
            String randomPassword = generateRandomPassword();
            User user= userRepository.getReferenceById(userAuth.getUserId());
            List<String> messages = Arrays.asList("Register", randomPassword);
            mailservice.sendMail(user.getEmail(), "Bienvenido a four-parks Colombia", messages);
            return AuthResponse.builder()
                .token("Usuario desbloqueado.")
                .build();
        }
        return AuthResponse.builder()
                .token("Usuario no coincide.")
                .build();
    }

    public void incrementFailedAttempts(String username) {
        UserAuthentication user = userAuthRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        if(user.getAttempts()>=3){
            blockPassword(username);
            throw new UserBlockedException("Contraseña bloqueada. Por favor cambie la contraseña.");
        }
        userAuthRepository.incrementFailedAttempts(username);

    }

    public void blockPassword(String username) {
        userAuthRepository.blockPassword(username);
    }

    public void unBlockPassword(String username) {
        userAuthRepository.unBlockPassword(username);
    }
    public void resetFailedAttempts(String username) {
        userAuthRepository.resetFailedAttempts(username);
    }

    private Map<String, Object> generateExtraClaims(UserAuthentication user) {
        Map<String, Object> extraClaims = new HashMap<>();

        extraClaims.put("userId", user.getUserId());
        extraClaims.put("role", user.getRole());

        return extraClaims;
    }

    public void verifyRegisterRequest(RegisterRequest request) throws CustomException {
        if (request.hasEmptyParameters()) {
            throw new CustomException(("Todos los campos son obligatorios"));
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException(("El email ya se encuentra registrado"));
        }
        if (userAuthRepository.findByUsername(request.getIdUser()).isPresent()) {
            throw new CustomException(("El  id usuario ya se encuentra registrado"));
        }
        if (userRepository.findByPhone(request.getPhone()).isPresent()) {
            throw new CustomException(("El  numero de telefono ya se encuentra registrado"));
        }
        if (userAuthRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new CustomException(("El  nombre de usuario ya se encuentra registrado"));
        }
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
                .isBlocked(0)
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

        // Guardar la tarjeta del usuario en la base de datos
        cardRepository.save(newCard);

        return AuthResponse.builder()
                .token(jwtService.getToken(null, userAuthentication))
                .contra(randomPassword) // es para iniciar sesion mientras
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
