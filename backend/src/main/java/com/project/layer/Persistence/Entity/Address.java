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
public class Address {
    
    @Id
    @Column(name = "IDADDRESS", nullable = false)
    private int idAddress;

    @Column(name = "DESCADDRESS", nullable = false)
    private String descAddress;

    @Column(name = "COORDINATESX", nullable = false)
    private String coordinatesX;

    @Column(name = "COORDINATESY", nullable = false)
    private String coordinatesY;
}
