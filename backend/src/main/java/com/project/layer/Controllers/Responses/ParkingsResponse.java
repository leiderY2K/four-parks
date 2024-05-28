package com.project.layer.Controllers.Responses;

import java.util.List;
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
public class ParkingsResponse {
    Map<Integer, List<Parking>> parkingsLists;
    String message; 
}
