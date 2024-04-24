package com.project.layer.Persistence.Model;


import com.project.layer.Persistence.Entity.User;
import com.project.layer.Persistence.Entity.UserDetail;
import com.project.layer.Persistence.Repository.IUserRepository;
import com.project.layer.Services.Access.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.bv.NullValidator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserRepository userRepository;
    private final JwtService jwtService;
    //private final PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest request) {
        return null;
    }

    public AuthResponse register(RegisterRequest request) {
        UserDetail user = UserDetail.builder()
                .user(request.getUser())
                .password(request.getPassword())
                .build();

        IUserRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
