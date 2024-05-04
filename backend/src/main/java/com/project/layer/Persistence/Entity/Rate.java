package com.project.layer.Persistence.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    
    @Id
    @Column(name = "FK_IDPARKING", nullable = false)
    private int idParking;

    @Id
    @Column(name = "FK_IDCITY", nullable = false)
    private int idCity;

    @Id
    @Column(name = "FK_IDVEHICLETYPE", nullable = false)
    private String idVehicleType;

    @Id
    @Column(name = "ISCOVERED", nullable = false)
    private boolean isCovered;

    @Column(name = "HOURCOST", nullable = false)
    private int hourCost;

    @Column(name = "RESERVATIONCOST", nullable = false)
    private int reservationCost;

}
