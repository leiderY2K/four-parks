package com.project.layer.Persistence.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Table(name = "USER")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @EmbeddedId
    private UserId userId;

    @Length(min = 3, message = "El nombre debe tener minimo 3 caracteres")
    @Column(name = "FIRSTNAME", nullable = false)
    private String firstName;

    @Column(name = "LASTNAME", nullable = false)
    private String lastName;

    @Email(message = "Ingrese una direccion de correo valida")
    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Size(min = 10, message = "El numero de telefono debe tener 10 caracteres")
    @Column(name = "PHONE", length = 10, nullable = false)
    private String phone;
}
