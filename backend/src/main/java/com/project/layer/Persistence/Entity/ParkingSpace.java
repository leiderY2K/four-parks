package com.project.layer.Persistence.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PARKINGSPACE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSpace {
    
    @Id
    @Column(name = "IDPARKINGSPACE", nullable = false)
    private String ipParkingSpace;

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
    @Column(name = "FK_IDPARKINGTYPE", nullable = false)
    private String idParkingType;

    @Column(name = "ISAVAILABLE", nullable = false)
    private boolean isAvailable;    

}
