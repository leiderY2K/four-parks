package com.project.layer.Persistence.Entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Embeddable
public class UserDebtId {
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "FK_CLIENT_IDUSER", referencedColumnName = "IDUSER"),
            @JoinColumn(name = "FK_CLIENT_IDDOCTYPE", referencedColumnName = "FK_IDDOCTYPE")
    })
    private User clientDebt ;
}
