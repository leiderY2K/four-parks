package com.project.layer.Persistence.Entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
@Embeddable
public class UserDebtId {
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "IDUSER", referencedColumnName = "IDUSER"),
            @JoinColumn(name = "FK_IDDOCTYPE", referencedColumnName = "FK_IDDOCTYPE")
    })
    private User clientDebt ;
    
}
