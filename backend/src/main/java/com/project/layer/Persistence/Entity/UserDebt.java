package com.project.layer.Persistence.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USERDEBT")
@Data
@Builder
@AllArgsConstructor
public class UserDebt {

    @EmbeddedId
    private UserDebtId debt;

    @Column(name = "DEBTAMOUNT", nullable = true)
    private Integer debtAmount;

    @Column(name = "OWES", nullable = true)
    private boolean owes;

    public UserDebt() {
        this.owes = false; // Inicializa owes como false por defecto
    }
}
