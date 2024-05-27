package com.project.layer.Controllers.Requests;

import java.util.Map;

import com.project.layer.Persistence.Entity.CoordinateID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingsRequest {
    public Map<Integer,CoordinateID> coordinates;
}
