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
@Table(name = "PARKINGSPACE")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSpace {
    
    @EmbeddedId
    private ParkingSpaceId parkingSpaceId;
 
    @Column(name = "ISUNCOVERED", nullable = false)
    private boolean isUncovered;

    @Column(name = "FK_IDVEHICLETYPE", nullable = false)
    private String idVehicleType;

    // Constructor que acepta todos los parámetros
    public ParkingSpace(int idParkingSpace, int idParking, String idCity, boolean isUncovered, String idVehicleType) {
        this.parkingSpaceId = new ParkingSpaceId(idParkingSpace, idParking, idCity);
        this.isUncovered = isUncovered;
        this.idVehicleType = idVehicleType;
    }
}
