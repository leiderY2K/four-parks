package com.project.layer.Persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "SCORESYSTEM")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScoreSystem {
    
    @EmbeddedId
    private ParkingId parkingId;

    @Column(name = "TARGETPOINTS", nullable = true)
    private Integer targetPoints;

    @Column(name = "TARGETVALUE", nullable = true)
    private Float targetValue;
    
}
