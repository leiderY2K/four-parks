package com.project.layer.Persistence.Entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
    
    @EmbeddedId
    private ParkingSpaceId parkingSpaceId; 

}
