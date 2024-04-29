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
@Table(name = "CLIENT")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    
    @EmbeddedId
    private UserId userId;

    @Column(name = "SCORE", nullable = false)
    private int score;
}
