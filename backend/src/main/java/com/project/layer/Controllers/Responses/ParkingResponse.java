package com.project.layer.Controllers.Responses;

import java.util.Map;

import com.project.layer.Persistence.Entity.Parking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingResponse {
    private Parking parking;
    private Map<String, Object> capacity;
}
