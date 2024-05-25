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
@Table(name = "CLIENTSCORE")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientScore {
    
    @EmbeddedId
    private ClientScoreId scoreSystemId;

    @Column(name = "SCOREPOINTS", nullable = true)
    private Integer scorePoints;

    @Column(name = "residue", nullable = true)
    private Float residue;
}
