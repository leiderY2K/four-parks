package com.project.layer.Persistence.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PARKINGTYPE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingType {
    
    @Id
    @Column(name = "IDPARKINGTYPE", nullable = false)
    private int idParkingType;

    @Column(name= "DESCPARKINGTYPE", nullable = false)
    private String descParkingType;

}
