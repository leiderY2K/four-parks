package com.project.layer.Controllers.Requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.*;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.regex.Matcher;

@Data
@Builder
@AllArgsConstructor
//@NoArgsConstructor
public class RegisterRequest {

    @Length(min = 7, max = 12, message = "El numero de documento debe tener entre 7 y 12 caracteres")
    @JsonProperty("idUser")
    private String idUser;

    @NotBlank
    @JsonProperty("idDocTypeFk")
    private String idDocTypeFk;

    @Length(min = 6, max = 12, message = "El nombre de usuario debe tener  entre 6 y 10 caracteres")
    @JsonProperty("username")
    private String username;

    @NotBlank
    @JsonProperty("role")
    private String role;

    @Length(min = 3, message = "El nombre debe tener minimo 3 caracteres")
    @JsonProperty("firstName")
    private String firstName;

    @Length(min = 4, message = "El apellido debe tener minimo 4 caracteres")
    @JsonProperty("lastName")
    private String lastName;

    @Length(min = 14, message = "El correo electronico no es valido")
    @Email
    @JsonProperty("email")
    private String email;


    @Length(min = 10, max = 10, message = "El numero de telefono debe tener 10 caracteres")
    @NotBlank()
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

    public boolean isEmail(String tempEmail) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);
        return mather.find();
    }
}

