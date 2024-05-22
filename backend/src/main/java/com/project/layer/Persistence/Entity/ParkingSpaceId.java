package com.project.layer.Persistence.Entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Embeddable
public class ParkingSpaceId implements Serializable {
    
    @Column(name = "IDPARKINGSPACE")
    private Integer idParkingSpace;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "FK_IDPARKING", referencedColumnName = "IDPARKING"),
        @JoinColumn(name = "FK_IDCITY", referencedColumnName = "FK_IDCITY")
    })
    private Parking parking;

}
