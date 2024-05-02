package com.project.layer.Controllers.Requests;

import com.project.layer.Persistence.Entity.UserId;
import com.project.layer.Persistence.Repository.IUserRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
@AllArgsConstructor
//@NoArgsConstructor
public class RegisterRequest {
    //private final IUserRepository userRepository;

    @NotBlank
    @JsonProperty("idUser")
    private String idUser;

    @NotBlank
    @JsonProperty("idDocTypeFk")
    private String idDocTypeFk;

    @NotBlank(message = "please as the username")
    @JsonProperty("username")
    private String username;

    @NotBlank
    @JsonProperty("role")
    private String role;

    @NotBlank
    @JsonProperty("firstName")
    private String firstName;

    @NotBlank
    @JsonProperty("lastName")
    private String lastName;

    @NotBlank
    @JsonProperty("email")
    @Email(message = "El correo electrónico debe ser válido")
    private String email;

    @Pattern(regexp = "\\d{10}", message = "El número de teléfono debe tener 10 dígitos")
    @JsonProperty("phone")
    private String phone;

    public boolean hasEmptyParameters() {
        return idDocTypeFk == null || idDocTypeFk.isEmpty() ||
                idUser == null || idUser.isEmpty() ||
                email == null || email.isEmpty() ||
                username == null || username.isEmpty()||
                firstName == null || firstName.isEmpty() ||
                phone == null || phone.isEmpty() ||
                role == null || role.isEmpty();
    }

    //public boolean doesUserExist(UserId userId) {
    //    return userRepository.existsById(userId);
    //}

}

