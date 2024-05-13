package com.project.layer.Controllers.Requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingSpaceRequest {
    public Integer idParking;

    public String idCity;

    public String vehicleType;

    public Integer amount;

    public Boolean isUncovered;
}
