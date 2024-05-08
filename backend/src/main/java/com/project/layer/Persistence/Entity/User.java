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

    @Column(name = "FIRSTNAME", nullable = false)
    private String firstName;

    @Column(name = "LASTNAME", nullable = false)
    private String lastName;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PHONE", length = 10, nullable = false)
    private String phone;
}
