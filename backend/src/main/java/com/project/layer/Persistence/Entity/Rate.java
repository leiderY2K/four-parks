package com.project.layer.Persistence.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "RATE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rate {
    
    @EmbeddedId
    private RateId rateId;

    @Column(name = "ISUNCOVERED", nullable = false)
    private boolean isUncovered;

    @Column(name = "HOURCOST", nullable = false)
    private int hourCost;

    @Column(name = "CANCELLATIONCOST", nullable = false)
    private int cancellationCost;

}
