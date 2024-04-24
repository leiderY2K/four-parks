package com.project.layer.Persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER_AUTHENTICATION")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(UserId.class)
public class UserAuthentication {
    
    @Id
    @OneToOne
    @JoinColumns({
        @JoinColumn(name = "IDUSER", referencedColumnName = "IDUSER"),
        @JoinColumn(name = "IDDOCTYPEFK", referencedColumnName = "IDDOCTYPEFK")
    })
    private User user;

    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;
}