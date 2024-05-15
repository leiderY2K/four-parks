package com.project.layer.Controllers.Requests;

import com.project.layer.Persistence.Entity.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.*;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

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


    @Length(min = 16, max = 16, message = "El numero de tarjeta debe tener 16 caracteres")
    @JsonProperty("serialCard")
    private String serialCard;

    @Length(min = 2, max = 2, message = "El mes de expiracion debe tener 2 caracteres")
    @NotBlank
    @JsonProperty("ExpiryMonthCard")
    private String expiryMonthCard;

    @Length(min = 2, max = 2, message = "El año de expiracion debe tener 2 caracteres")
    @NotBlank
    @JsonProperty("ExpiryYearCard")
    private String expiryYearCard;

    @Digits(integer = 3, fraction = 0, message = "El CVC debe ser numérico")
    @Length(min = 3, max = 4, message = "El cvv de la tarjeta debe tener  3 caracteres")
    @JsonProperty("cvc")
    private String cvv;

    public boolean hasEmptyParameters() {
        return idDocTypeFk == null || idDocTypeFk.isEmpty() ||
                idUser == null || idUser.isEmpty() ||
                email == null || email.isEmpty() ||
                username == null || username.isEmpty()||
                firstName == null || firstName.isEmpty() ||
                phone == null || phone.isEmpty() ||
                serialCard == null || serialCard.isEmpty() ||
                expiryMonthCard == null || expiryMonthCard.isEmpty() ||
                expiryYearCard == null || expiryYearCard.isEmpty() ||
                cvv == null || cvv.isEmpty() ||
                role == null || role.isEmpty();
    }

    public String getExpiryDate() {

        String expiryDayCard = String.valueOf(31);
        return  expiryDayCard  + "/"  +expiryMonthCard + "/" + expiryYearCard;
    }

}

