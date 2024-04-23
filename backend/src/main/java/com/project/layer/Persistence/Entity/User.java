package com.project.layer.Persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(UserId.class)
public class User {
    @Id
    @Column(name = "IDUSER", nullable = false)
    private String idUser;
    @Id
    @Column(name = "IDDOCTYPEFK", nullable = false)
    private String idDocTypeFk;

    @Column(name = "FIRSTNAME", nullable = false)
    private String firstName;

    @Column(name = "LASTNAME", nullable = false)
    private String lastName;

}
