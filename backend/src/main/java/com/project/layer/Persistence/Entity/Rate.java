package com.project.layer.Persistence.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ADDRESS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rate {
    
    @Id
    @Column(name = "IDRATE", nullable = false)
    private int idRate;

    @Column(name = "COSTRATE", nullable = false)
    private int costRate;

    //TERMINAR!!!

}
