package com.project.layer.Persistence.Entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
/*
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cardOwner", cascade = CascadeType.ALL)
    List<Card> cards;

}
 */
}