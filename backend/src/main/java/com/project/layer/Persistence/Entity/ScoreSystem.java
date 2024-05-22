package com.project.layer.Persistence.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "SCORESYSTEM")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreSystem {
    
    @EmbeddedId
    private ScoreSystemId scoreSystemId;

    @Column(name = "SCOREPOINTS", nullable = true)
    private Integer scorePoints;

    @Column(name = "TARGETPOINTS", nullable = true)
    private Integer targetPoints;

    @Column(name = "RESIDUE", nullable = true)
    private Float residue;

    @Column(name = "TARGETVALUE", nullable = true)
    private Float targetValue;
}
