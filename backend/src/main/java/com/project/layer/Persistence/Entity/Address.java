package com.project.layer.Persistence.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ADDRESS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(CoordinateID.class)
public class Address {

    @Id
    @Column(name = "COORDINATESX", nullable = false)
    private float coordinatesX;
    @Id
    @Column(name = "COORDINATESY", nullable = false)
    private float coordinatesY;
    
    @Column(name = "DESCADDRESS", nullable = false)
    private String descAddress;

}
