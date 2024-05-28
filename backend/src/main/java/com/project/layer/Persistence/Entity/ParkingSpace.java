package com.project.layer.Persistence.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
    
    @OneToOne
    @JoinColumn(name = "FK_IDVEHICLETYPE", referencedColumnName = "IDVEHICLETYPE", nullable = false)
    private VehicleType vehicleType;

}
