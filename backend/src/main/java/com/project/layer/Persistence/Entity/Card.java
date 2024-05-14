package com.project.layer.Persistence.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Table(name="CARD")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDCARD", nullable = false)
    private String idCard;

    @Column(name = "SERIALCARD", nullable = false)
    private String serialCard;

    @Column(name = "EXPIRYDATECARD", nullable = false)
    private LocalDate ExpDateCard;

    @Column(name = "CVVCARD", nullable = false)
    private String cvvCard;


    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "FK_CLIENT_IDUSER", referencedColumnName = "IDUSER"),
            @JoinColumn(name = "FK_CLIENT_IDDOCTYPE", referencedColumnName = "FK_IDDOCTYPE")
    })
    private User cardOwner;
}