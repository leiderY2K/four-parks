package com.project.layer.Services.Authentication;

import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.project.layer.Persistence.Entity.*;
import com.project.layer.Persistence.Error.CustomException;
import com.project.layer.Persistence.Error.UserBlockedException;
import com.project.layer.Persistence.Repository.ICardRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.project.layer.Controllers.Requests.LoginRequest;
import com.project.layer.Controllers.Requests.PassRecoveryRequest;
import com.project.layer.Controllers.Requests.PassRequest;
import com.project.layer.Controllers.Requests.RegisterRequest;
import com.project.layer.Controllers.Requests.UnlockRequest;
import com.project.layer.Controllers.Requests.UserRequest;
import com.project.layer.Controllers.Responses.AuthResponse;
import com.project.layer.Controllers.Responses.UserResponse;
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

    private final AuthenticationManager authenticationManager;

    @Transactional
    public User getUser(UserId userId) {
        return userRepository.findById(userId).get();
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException e) {
            // Lanzar la excepción para que sea capturada en el controlador
            throw new BadCredentialsException("Credenciales incorrectas", e);
        }

        // Si la autenticación es exitosa, restablecer los intentos fallidos
        UserAuthentication user = userAuthRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        resetFailedAttempts(user.getUsername());

        String token = jwtService.getToken(generateExtraClaims(user), user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    // Metodo que retorna los parametros para la auditoria

    @Transactional
    public UserAction getUserAction(String username, String descAction, String ipUser) {

        // obtenemos la fecha actual en la que se hizo el registro
        LocalDate currentDate = LocalDate.now();

        // Convertir LocalDate a java.sql.Date
        Date dateAction = Date.valueOf(currentDate);

        // obtenemos la informacion del usuario

        // Revisamos los datos del login
        UserAuthentication userAuthentication = userAuthRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // una vez confirmado traemos al usuario
        User user = userRepository
                .findByUserId(userAuthentication.getUserId().getIdUser(), userAuthentication.getUserId().getIdDocType())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        UserAction userAction = UserAction.builder()
                .dateAction(dateAction)
                .descAction(descAction)
                .ipUser(ipUser)
                .userActionId(user)
                .build();
        return userAction;
    }

    @Transactional
    public AuthResponse unlock(UnlockRequest request) throws MessagingException {
        UserAuthentication userAuth = userAuthRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        if (userAuth.getUsername().equals(request.getUsername())
                && userAuth.getUserId().getIdUser().equals(request.getIdUser())) {
            resetFailedAttempts(userAuth.getUsername());
            unBlockPassword(userAuth.getUsername());
            String randomPassword = generateRandomPassword();
            User user = userRepository.getReferenceById(userAuth.getUserId());
            updatePass(userAuth.getUsername(), passwordEncoder.encode(randomPassword));
            return AuthResponse.builder()
                    .token("Usuario desbloqueado.")
                    .build();
        }
        return AuthResponse.builder()
                .token("Usuario no coincide.")
                .build();
    }

    public void incrementFailedAttempts(String username) {
        UserAuthentication user = userAuthRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        if (user.getAttempts() >= 3) {
            blockPassword(username);
            throw new UserBlockedException("Se ha bloqueado el usuario por multiples intentos de inicio de sesión.");
        }
        userAuthRepository.incrementFailedAttempts(username);

    }

    public void updatePass(String username, String pass) {
        userAuthRepository.updatePass(username, pass);
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

    @Modifying
    @Transactional
    public AuthResponse register(RegisterRequest request) throws MessagingException {

        UserId userId = UserId.builder()
                .idUser(request.getIdUser())
                .idDocType(request.getIdDocTypeFk())
                .build();

        if (userRepository.existsById(userId))
            return new AuthResponse(null, null, "¡El usuario ya existe!");

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

        if (userAuthentication.getRole().equals(Role.CLIENT)) {

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

        }

        return AuthResponse.builder()
                .token(jwtService.getToken(null, userAuthentication))
                .contra(randomPassword) // es para iniciar sesion mientras
                .message("El registro fue exitoso")
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

    public AuthResponse changePass(PassRequest request) {

        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getOldPass()));
        } catch (BadCredentialsException e) {
            // Lanzar la excepción para que sea capturada en el controlador
            throw new BadCredentialsException("Credenciales incorrectas", e);
        }
        UserAuthentication userAuth = userAuthRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        /*
         * System.out.println("Contra base de datos :    "+ userAuth.getPassword());
         * System.out.println("Contra base de datos :    "+ userAuth.getPassword());
         * System.out.println("Contra traida de front :    "+
         * passwordEncoder.encode(request.getOldPass()));
         * if(userAuth.getUsername().equals(request.getUsername())&&userAuth.getPassword
         * ().equals(passwordEncoder.encode(request.getOldPass()))){
         */
        updatePass(userAuth.getUsername(), passwordEncoder.encode(request.getNewPass()));
        return AuthResponse.builder()
                .token("Contraseña modificada con exito.")
                .build();
        /*
         * }
         * return AuthResponse.builder()
         * .token("Contraseña actual no coincide.")
         * .build();
         * }
         */
    }

    @Modifying
    @Transactional
    public UserResponse update(@Validated UserRequest request) {

        UserAuthentication userAuth = userAuthRepository.findByUsername(request.getUsername()).get();
        User user = userRepository.findByUserId(userAuth.getUserId().getIdUser(), userAuth.getUserId().getIdDocType())
                .get();

        if (user != null) {

            // Obtener todas las variables declaradas en la clase User
            Field[] userAuthFields = userAuth.getClass().getDeclaredFields();
            Field[] userFields = user.getClass().getDeclaredFields();

            List<Field> fields = new ArrayList<>();
            fields.addAll(Arrays.asList(userAuthFields));
            fields.addAll(Arrays.asList(userFields));

            for (Field field : fields) {
                // Hacer que el campo sea accesible, incluso si es privado
                field.setAccessible(true);

                try {
                    // Obtener el valor del campo en el objeto parkingChanges
                    Object value = field.get(request);

                    // Si el valor no es nulo, establecerlo en el objeto user original
                    if (value != null) {
                        field.set(user, value);
                    }
                } catch (IllegalAccessException e) {
                    return new UserResponse(null, null, "No se puede modificar el campo " + field);
                    // Manejar la excepción si se produce un error al acceder al campo
                }
            }

            userRepository.save(user);
            return new UserResponse(user, null, "Se realizó la modificación");
        } else {
            return new UserResponse(null, null, "No se encontró ningún estacionamiento con el ID proporcionado");
        }
    }
    @Transactional
    public AuthResponse passRecovery(PassRecoveryRequest request) throws MessagingException {
        Optional<User> optUser = userRepository.findByEmail(request.getEmail());
        if (optUser.isPresent()) {
            User user = optUser.orElse(null);
            UserAuthentication userAuth = userAuthRepository.findById(user.getUserId().getIdUser())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
            resetFailedAttempts(userAuth.getUsername());
            unBlockPassword(userAuth.getUsername());
            String randomPassword = generateRandomPassword();
            updatePass(userAuth.getUsername(), passwordEncoder.encode(randomPassword));
            return AuthResponse.builder()
                    .token("La nueva contraseña se ha enviado exitosamente a su correo.")
                    .contra(randomPassword)
                    .build();
        }
        return AuthResponse.builder()
                .token("El correo no coincide con el de ningún usuario registrado")
                .build();
    }

    public List<UserAuthentication> getAuthUsers(){
        return userAuthRepository.getAuthUsers();
    }

}
