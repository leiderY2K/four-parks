package com.project.layer.Persistence.Entity;

import java.io.Serializable;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
@AttributeOverrides({
    @AttributeOverride(
        name = "idParkingSpace",
        column = @Column(name = "IDPARKINGSPACE")
    ),
    @AttributeOverride(
        name = "idParking",
        column = @Column(name = "FK_IDPARKING")
    ),
    @AttributeOverride(
        name = "idCity",
        column = @Column(name = "FK_IDCITY")
    ),
    @AttributeOverride(
        name = "idVehicleType",
        column = @Column(name = "FK_IDVEHICLETYPE")
    )
})
public class ParkingSpaceId implements Serializable {
    private String idParkingSpace;
    private int idParking;
    private String idCity;
    private String idVehicleType;

}
