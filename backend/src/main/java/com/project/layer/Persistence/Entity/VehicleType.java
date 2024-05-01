package com.project.layer.Persistence.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "VEHICLETYPE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleType {
    
    @Id
    @Column(name = "IDVEHICLETYPE",nullable = false)
    private String idVehicleType;

    @Column(name = "DESCVEHICLETYPE")
    private String descVehicleType;

}
